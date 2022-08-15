package com.yice.edu.cn.jw.service.teacher;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.general.nation.Nation;
import com.yice.edu.cn.common.pojo.general.region.Region;
import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import com.yice.edu.cn.common.pojo.jw.auth.TeacherRole;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.ScanPerson;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.jw.teacher.*;
import com.yice.edu.cn.common.pojo.ts.jMessage.IMReturnResult;
import com.yice.edu.cn.common.pojo.ts.jMessage.UserRigisterApiResult;
import com.yice.edu.cn.common.pojo.ts.jMessage.pojo.UserRegister;
import com.yice.edu.cn.common.util.jmessage.api.JMessageUserApi;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.jw.dao.auth.IPermDao;
import com.yice.edu.cn.jw.dao.auth.ITeacherRoleDao;
import com.yice.edu.cn.jw.dao.dd.IDdDao;
import com.yice.edu.cn.jw.dao.exam.examManage.IScanPersonDao;
import com.yice.edu.cn.jw.dao.nation.INationDao;
import com.yice.edu.cn.jw.dao.region.IRegionDao;
import com.yice.edu.cn.jw.dao.school.ISchoolDao;
import com.yice.edu.cn.jw.dao.teacher.ITeacherClassesDao;
import com.yice.edu.cn.jw.dao.teacher.ITeacherDao;
import com.yice.edu.cn.jw.dao.teacher.ITeacherPostDao;
import com.yice.edu.cn.jw.dao.teacher.ITeacherQuitDao;
import io.netty.util.internal.StringUtil;
import org.apache.xmlbeans.impl.common.ConcurrentReaderHashMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class TeacherService {
    @Autowired
    private ITeacherDao teacherDao;
    @Autowired
    private ITeacherQuitDao teacherQuitDao;
    @Autowired
    private ITeacherPostDao teacherPostDao;
    @Autowired
    private ITeacherClassesDao teacherClassesDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private INationDao nationDao;
    @Autowired
    private IRegionDao regionDao;

    @Autowired
    private ISchoolDao schoolDao;
    @Autowired
    private ITeacherRoleDao teacherRoleDao;
    @Autowired
    private IPermDao permDao;
    @Autowired
    private IScanPersonDao scanPersonDao;
    @Autowired
    private IDdDao ddDao;

    @Transactional(readOnly = true)
    public Teacher findTeacherById(String id) {
        return teacherDao.findTeacherById(id);
    }

    /**
     * 根据教师tel查询教师信息
     * @param tel
     * @return
     */
    @Transactional(readOnly = true)
    public Teacher findTeacherByTel(String tel){
        return teacherDao.teacherLoginByQRCode(tel);
    }

    @Transactional
    public void saveTeacher(Teacher teacher) {
        teacher.setId(sequenceId.nextId());
        teacher.setPersonType(Constant.PERSON_TYPE.TEACHER);
        teacher.setRegisterStatus("0");
        if(teacher.getImgUrl()==null||"".equals(teacher.getImgUrl()))
            teacher.setImgUrl(teacher.getSex().equals("4")?Constant.AVATAR.MAN:Constant.AVATAR.WOMEN);
        teacherDao.saveTeacher(teacher);
        saveDefaultPost(Arrays.asList(teacher));
        //JMessageUserApi.updateUserImgByRemote(teacher.getId(),Constant.RES_PRE+teacher.getImgUrl(),Constant.APP_TYPE.TAP_TYPE);
    }

    @Transactional(readOnly = true)
    public List<Teacher> findTeacherListByCondition(Teacher teacher) {
        return teacherDao.findTeacherListByCondition(teacher);
    }

    @Transactional(readOnly = true)
    public Teacher findOneTeacherByCondition(Teacher teacher) {
        return teacherDao.findOneTeacherByCondition(teacher);
    }
    @Transactional(readOnly = true)
    public long findTeacherCountByCondition(Teacher teacher) {
        return teacherDao.findTeacherCountByCondition(teacher);
    }
    @Transactional(readOnly = true)
    public long findTeacherCountByCondition4Like(Teacher teacher) {
        return teacherDao.findTeacherCountByCondition4Like(teacher);
    }
    @Transactional
    public void updateTeacher(Teacher teacher) {
        //获取旧的记录
        Teacher old = teacherDao.findTeacherById(teacher.getId());
        teacherDao.updateTeacher(teacher);
        if(teacher.getName()!=null&&!old.getName().equals(teacher.getName())){
            //判断名称是否改变 改了同步IM
            UserRegister userRegister = new UserRegister();
            userRegister.setShowName(teacher.getName());
            userRegister.setUserName(teacher.getId());
            JMessageUserApi.updateUser(userRegister,Constant.APP_TYPE.TAP_TYPE);
        }
        if(teacher.getImgUrl()!=null&&!teacher.getImgUrl().equals(old.getImgUrl())){
            //头像保存到im
            UserRegister userRegister = new UserRegister();
            userRegister.setUserName(teacher.getId());
            userRegister.setDefaultHeadImage(teacher.getImgUrl());
            JMessageUserApi.updateUser(userRegister,Constant.APP_TYPE.TAP_TYPE);
        }
    }
    @Transactional
    public void updateTeacherNew(Teacher teacher) {
        if(teacher.getImgUrl()==null||"".equals(teacher.getImgUrl()))
            teacher.setImgUrl(teacher.getSex().equals("4")?Constant.AVATAR.MAN:Constant.AVATAR.WOMEN);
        else if(Constant.AVATAR.MAN.equals(teacher.getImgUrl())||Constant.AVATAR.WOMEN.equals(teacher.getImgUrl())){
            teacher.setImgUrl(teacher.getSex().equals("4")?Constant.AVATAR.MAN:Constant.AVATAR.WOMEN);
        }
        //判断是否是离职操作
        if (teacher.getStatus().equals(Constant.STATUS.TEACHER_QUIT_LINE)) {
            //做备份
            teacher.setUpdateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constant.DATE_FORMATTER)));
            TeacherQuit q = new TeacherQuit();
            q.setTel(teacher.getTel());//删除号码对应的
            q.setSchoolId(teacher.getSchoolId());
            teacherQuitDao.deleteTeacherQuitByCondition(q);
            q.setTel(null);
            q.setDocumentNum(teacher.getDocumentNum());
            teacherQuitDao.deleteTeacherQuitByCondition(q);
            BeanUtils.copyProperties(teacher,q);
            //q = new TeacherQuit(teacher);
            teacherQuitDao.saveTeacherQuit(q);
            //删除关联关系
            //删除教师在学校的职务
            TeacherPost tp = new TeacherPost();
            tp.setTeacherId(teacher.getId());
            tp.setSchoolId(teacher.getSchoolId());
            teacherPostDao.deleteTeacherPostByCondition(tp);
            //删除教师班级
            TeacherClasses tc = new TeacherClasses();
            tc.setTeacherId(teacher.getId());
            tc.setSchoolId(teacher.getSchoolId());
//            List<TeacherClasses> ltc = teacherClassesDao.findTeacherClassesListByCondition(tc);
//            ltc.stream().forEach(c -> {
//                //删除教师班级课程
//                TeacherClassesCourse tcc = new TeacherClassesCourse();
//                tcc.setSchoolId(c.getSchoolId());
//                tcc.setTeacherClassesId(c.getId());
//                teacherClassesCourseDao.deleteTeacherClassesCourseByCondition(tcc);
//            });
            teacherClassesDao.deleteTeacherClassesByCondition(tc);
            //删除教师信息
            deleteTeacher(teacher.getId());
            teacher.setCode("200");
            teacher.setMsg("修改成功");
        } else {
            //校验修改的手机号或者身份证号是否出现重复
            Teacher t = new Teacher();
            t.setTel(teacher.getTel());
            t.setId(teacher.getId());
            if (teacherDao.findRepeatByCondition(t) > 0) {
                teacher.setCode("222");
                teacher.setMsg("联系方式已存在！");
                return ;
            }
            t = new Teacher();
            t.setId(teacher.getId());
            t.setDocumentNum(teacher.getDocumentNum());
            if (teacherDao.findRepeatByCondition(t) > 0) {
                teacher.setCode("222");
                teacher.setMsg("证件号码已存在！");
                return ;
            }
            t = new Teacher();
            t.setId(teacher.getId());
            t.setWorkNumber(teacher.getWorkNumber());
            t.setSchoolId(teacher.getSchoolId());
            if (teacherDao.findRepeatByCondition(t) > 0) {//同个学校内限制
                teacher.setCode("222");
                teacher.setMsg("工号已存在！");
                return ;
            }
            teacher.setCode("200");
            teacher.setMsg("修改成功");
            //获取旧的记录
            this.updateTeacher(teacher);
        }
    }

    @Transactional
    public void deleteTeacher(String id) {
        teacherDao.deleteTeacher(id);
    }

    @Transactional
    public void deleteTeacherByCondition(Teacher teacher) {
        teacherDao.deleteTeacherByCondition(teacher);
    }

    @Transactional
    public Map<String, Object> batchSaveTeacher(List<Teacher> teacherList) {
        //校验列表
        List<String> errors = new ArrayList<>();
        Map<String, Integer> repeatm = new ConcurrentReaderHashMap();
        String nd = DateUtil.now();
        //查询获取 所有的老师 简易信息 进行判断 减少数据库查询次数
        final Teacher tempT = new Teacher();
        tempT.setStatus(Constant.STATUS.TEACHER_ON_LINE);
        tempT.setPager(new Pager().setPaging(false).setIncludes("tel","work_number","document_num","school_id"));
        final List<Teacher> ots = this.findTeacherListByCondition(tempT);
        //查询获取所有的地域信息
        final Region tempR = new Region();
        tempR.setPager(new Pager().setPaging(false).setIncludes("id","name"));
        final List<Region> regions = regionDao.findRegionListByCondition(tempR);
        //查询获取所有的名族
        final Nation tempN = new Nation();
        tempN.setPager(new Pager().setPaging(false).setIncludes("id","name"));
        final List<Nation> nations = nationDao.findNationListByCondition(tempN);
        teacherList.stream().forEach(t -> {
            Pattern pattern;
            int i = teacherList.indexOf(t) + 1;//获取当前所在条数
            StringBuffer error = new StringBuffer();//异常提示
            if (StringUtil.isNullOrEmpty(t.getName())) {
                error.append("姓名不能为空；");
            }
            if (StringUtil.isNullOrEmpty(t.getSex())) {
                error.append("性别不能为空；");
            }
            if (!StringUtil.isNullOrEmpty(t.getSex()) && !(t.getSex().equals("4") || t.getSex().equals("5"))) {
                error.append("性别只能填写男或者女；");
            }
            Teacher teacher;
            if (StringUtil.isNullOrEmpty(t.getWorkNumber())) {
                error.append("工号必须填写；");
            } else {
                if (t.getWorkNumber().length()>50) {
                    error.append("工号长度不超过50位；");
                } else {
                    if (repeatm.get(t.getWorkNumber()) != null && repeatm.get(t.getWorkNumber()) > 0) {
                        error.append("工号重复提交；");
                    } else {
                        repeatm.put(t.getWorkNumber(), 1);
                        //联系方式查询数据库重复
                        if (ots.stream().anyMatch(ot->t.getSchoolId().equals(ot.getSchoolId())&&t.getWorkNumber().equals(ot.getWorkNumber()))) {
                            error.append("工号已存在;");
                        }
                    }
                }
            }
            if (StringUtil.isNullOrEmpty(t.getTel())) {
                error.append("联系方式必须填写；");
            } else {
                pattern = Pattern.compile("^[\\d]{11}$");
                if (!pattern.matcher(t.getTel()).matches()) {
                    error.append("联系方式必须为11位长度数值；");
                } else {
                    if (repeatm.get(t.getTel()) != null && repeatm.get(t.getTel()) > 0) {
                        error.append("联系方式重复提交；");
                    } else {
                        repeatm.put(t.getTel(), 1);
                        //联系方式查询数据库重复
                        if (ots.stream().anyMatch(ot->t.getTel().equals(ot.getTel()))) {
                            error.append("联系方式已存在;");
                        }
                    }
                }
            }
            if (!StringUtil.isNullOrEmpty(t.getQq())) {
                pattern = Pattern.compile("^[1-9][0-9]{4,13}$");
                if (!pattern.matcher(t.getTel()).matches()) {
                    error.append("QQ必须为4-20位长度数值；");
                }
            }
            if (!StringUtil.isNullOrEmpty(t.getWeixin()) && t.getWeixin().length() > 30) {
                error.append("微信号长度要小于30；");
            }
            if (!StringUtil.isNullOrEmpty(t.getEmail())) {
                pattern = Pattern.compile("^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$");
                if (!pattern.matcher(t.getEmail()).matches()) {
                    error.append("请正确填写邮箱；");
                }
            }
            if (StringUtil.isNullOrEmpty(t.getNationality())) {
                error.append("国籍不能为空；");
            } else {
                if (!(t.getNationality().equals("1") || t.getNationality().equals("2") || t.getNationality().equals("3"))) {
                    error.append("国籍必须为：中国/中国(港澳台)/国外；");
                }
            }
            if (!StringUtil.isNullOrEmpty(t.getNationName())) {
                final Nation nation = nations.stream().filter(on->on.getName().equals(t.getNationName())).findFirst().get();
                if (nation == null || nations.size() <= 0)
                    error.append("请正确填写民族；");
                else {
                    t.setNation(nation.getId());
                }
            }
            if (!StringUtil.isNullOrEmpty(t.getNativePlace()) && t.getNativePlace().length() > 50) {
                error.append("籍贯长度不能超过50；");
            }
            if (StringUtil.isNullOrEmpty(t.getDocumentType())) {
                error.append("证件类型必须填写；");
            } else {
                if (!(t.getDocumentType().equals("60") || t.getDocumentType().equals("61"))) {
                    error.append("请正确填写证件类型：身份证/护照；");
                } else {
                    if (t.getDocumentType().equals("60")) {
                        pattern = Pattern.compile("^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$");
                        if (!pattern.matcher(t.getDocumentNum()).matches()) {
                            error.append("请正确输入身份证号码；");
                        } else {
                            if (repeatm.get(t.getDocumentNum()) != null && repeatm.get(t.getDocumentNum()) > 0) {
                                error.append("身份证号码重复提交；");
                            } else {
                                repeatm.put(t.getDocumentNum(), 1);
                                //证件号码查询数据库重复
                                if (ots.stream().anyMatch(ot->t.getDocumentNum().equals(ot.getDocumentNum()))) {
                                    error.append("身份证号码已存在；");
                                }
                            }
                        }
                    } else if (t.getDocumentType().equals("61")) {
                        pattern = Pattern.compile("^[a-zA-Z0-9]{5,17}$");
                        if (!pattern.matcher(t.getDocumentNum()).matches()) {
                            error.append("请正确输入护照号码；");
                        } else {
                            if (repeatm.get(t.getDocumentNum()) != null && repeatm.get(t.getDocumentNum()) > 0) {
                                error.append("护照号码重复提交；");
                            } else {
                                repeatm.put(t.getDocumentNum(), 1);
                                //联系方式查询数据库重复
                                if (ots.stream().anyMatch(ot->ot.getDocumentNum().equals(t.getDocumentNum()))) {
                                    error.append("护照号码已存在；");
                                }
                            }
                        }
                    }
                }
            }
            if (StringUtil.isNullOrEmpty(t.getDocumentNum())) {
                error.append("证件号码不能为空；");
            }
            Region region;
            if (!StringUtil.isNullOrEmpty(t.getProvinceName())) {
                region = regions.stream().filter(r->r.getName().equals(t.getProvinceName())).findFirst().get();
                if (region == null || regions.size() <= 0)
                    error.append("省份不存在或名称错误；");
                else {
                    t.setProvinceId(region.getId());
                }
            }
            if (!StringUtil.isNullOrEmpty(t.getCityName())) {
                region = regions.stream().filter(r->r.getName().equals(t.getCityName())).findFirst().get();
                if (region == null || regions.size() <= 0)
                    error.append("城市不存在或名称错误；");
                else {
                    t.setCityId(region.getId());
                }
            }
            if (!StringUtil.isNullOrEmpty(t.getCountyName())) {
                region = regions.stream().filter(r->r.getName().equals(t.getCountyName())).findFirst().get();
                if (region == null || regions.size() <= 0) {
                    error.append("区域不存在或名称错误；");
                } else {
                    t.setCountyId(region.getId());
                }
            }
            if (!StringUtil.isNullOrEmpty(t.getTeacherNum())) {
                pattern = Pattern.compile("^[a-zA-Z0-9]{5,17}$");
                if (!pattern.matcher(t.getTeacherNum()).matches()) {
                    error.append("请正确输入教师资格证编号；");
                }
            }
            if (!StringUtil.isNullOrEmpty(t.getPoliticalFace())) {
                if ("null".equals(t.getPoliticalFace()))
                    t.setPoliticalFace(null);
                else if (!(t.getPoliticalFace().equals("6") || t.getPoliticalFace().equals("7") || t.getPoliticalFace().equals("8"))) {
                    error.append("政治面貌格式为：群众/团员/党员；");
                }
            }
            if (!StringUtil.isNullOrEmpty(t.getBirthDate())) {
                pattern = Pattern.compile("^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$");
                if (!pattern.matcher(t.getBirthDate()).matches()) {
                    error.append("请正确输入出生日期格式：2018-01-01；");
                }
            }
            if (!StringUtil.isNullOrEmpty(t.getTeacherAge())) {
                pattern = Pattern.compile("^[0-9]|[1-9][0-9]$");
                if (!pattern.matcher(t.getTeacherAge()).matches()) {
                    error.append("请正确输入教龄；");
                }
            }
            if (!StringUtil.isNullOrEmpty(t.getTopEdu())) {
                if ("null".equals(t.getTopEdu()))
                    t.setTopEdu(null);
                else if (!(t.getTopEdu().equals("30") || t.getTopEdu().equals("31") || t.getTopEdu().equals("32") || t.getTopEdu().equals("33") || t.getTopEdu().equals("34") || t.getTopEdu().equals("35"))) {
                    error.append("最高学历格式为：专科/本科/硕士/博士/MBA/EMBA；");
                }
            }
            if (!StringUtil.isNullOrEmpty(t.getGraduate()) && t.getGraduate().length() > 50) {
                error.append("毕业学校不能超过50个字；");
            }
            if (!StringUtil.isNullOrEmpty(t.getMajor()) && t.getMajor().length() > 50) {
                error.append("毕业专业不能超过50个字；");
            }
            if (!StringUtil.isNullOrEmpty(t.getBeginTime())) {
                pattern = Pattern.compile("^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$");
                if (!pattern.matcher(t.getBeginTime()).matches()) {
                    error.append("请正确输入来校工作日期格式：2018-01-01；");
                }
            }
            if (!StringUtil.isNullOrEmpty(t.getWorks()) && t.getWorks().length() > 500) {
                error.append("毕业专业不能超过50个字；");
            }
            if (!StringUtil.isNullOrEmpty(t.getMaritalStatus())) {
                if ("null".equals(t.getMaritalStatus()))
                    t.setMaritalStatus(null);
                else if (!(t.getMaritalStatus().equals("241") || t.getMaritalStatus().equals("242") || t.getMaritalStatus().equals("243")|| t.getMaritalStatus().equals("244")|| t.getMaritalStatus().equals("245"))) {
                    error.append("婚姻情况格式为：未婚/已婚/丧偶/离婚/其他；");
                }
            }
            //异常添加list
            if (error.length() > 0) {
                error.insert(0, "第" + i + "条,");
                errors.add(error.toString());
            } else {
                //没有异常则添加 id、状态、密码
                t.setId(sequenceId.nextId());
                if(ObjectUtil.isNotNull(t.getSex())){
                    t.setImgUrl(t.getSex().equals("4")?Constant.AVATAR.MAN:Constant.AVATAR.WOMEN);
                }
                t.setStatus(Constant.STATUS.TEACHER_ON_LINE);
                t.setPassword(DigestUtil.sha1Hex(DigestUtil.md5Hex(Constant.DEFAULT_PWD)));
                t.setRegisterStatus("0");
                t.setPersonType(Constant.PERSON_TYPE.TEACHER);
                t.setCreateTime(nd);
            }
        });
        ots.clear();
        regions.clear();
        nations.clear();
        Map<String, Object> result = new HashMap<>();
        if (errors.size() <= 0) {
            teacherDao.batchSaveTeacher(teacherList);
            saveDefaultPost(teacherList);
            result.put("code", "200");
        } else {
            result.put("code", "222");
            result.put("errors", errors);
        }
        return result;
    }

    @Transactional(readOnly = true)
    public Teacher login(Teacher teacher) {
        teacher.setPassword(DigestUtil.sha1Hex(teacher.getPassword()));
        Teacher t = teacherDao.teacherLogin(teacher);
        if (t == null) {
            return null;
        } else {
            School school = schoolDao.findSchoolById(t.getSchoolId());
            school.setDescr(null);
            school.setCulture(null);
            t.setSchool(school);
            return t;
        }
    }

    @Transactional(readOnly = true)
    public List<Perm> findTeacherTreeMenu(String id) {
        List<Perm> perms = permDao.findTeacherTreeMenuByTId(id);
        return ObjectKit.buildTree(perms,"-1");
    }


    @Transactional(readOnly = true)
    public List<String> findCheckedRolesByTeacherId(String id) {
        return teacherDao.findCheckedRolesByTeacherId(id);
    }

    @Transactional
    public void delsertTeacherRoles(TeacherRole teacherRole) {
        String schoolId = teacherRole.getSchoolId();
        teacherRole.setSchoolId(null);
        teacherRoleDao.deleteTeacherRoleByCondition(teacherRole);
        String roleIds = teacherRole.getRoleIds();
        if (roleIds != null) {
            String[] arr = roleIds.split(",");
            List<TeacherRole> trs = new ArrayList<>();
            for (String s : arr) {
                TeacherRole tr = new TeacherRole();
                tr.setId(sequenceId.nextId());
                tr.setTeacherId(teacherRole.getTeacherId());
                tr.setRoleId(s);
                tr.setSchoolId(schoolId);
                trs.add(tr);
            }
            teacherRoleDao.batchSaveTeacherRole(trs);
        }

    }

    @Transactional(readOnly = true)
    public List<Perm> findTeacherFuncPermsByTeacherId(String id) {
        return teacherDao.findFuncPermsByTeacherId(id);
    }

    @Transactional
    public void updateTeacherAdmin(Teacher teacher) {
        this.updateTeacher(teacher);
    }

    @Transactional(readOnly = true)
    public List<Teacher> findCorrespondencesByTeacher(Teacher teacher) {
        return  teacherDao.findTeacherCorrespondencesList(teacher);
    }

    @Transactional(readOnly = true)
    public Teacher findTeacherInfoById(String id) {
        final Teacher teacher = teacherDao.findTeacherById(id);
        final School school = schoolDao.findSchoolById(teacher.getSchoolId());
        teacher.setSchool(school);
        return teacher;
    }
    @Transactional(readOnly = true)
    public List<Teacher> findTeacherListInfoByCondition(Teacher teacher) {
        return teacherDao.findTeacherListInfoByCondition(teacher);
    }

    /**
     * 用于学校创建默认的管理员账号
     * 账号 yc+6位随机数 不可重复
     * 密码 随机6位数字
     */
    public void createDefaultAdmin(School school){
        Teacher teacher = new Teacher();
        long c;
        do{
            teacher.setAccount("yc"+RandomUtil.randomNumbers(6));
            c = teacherDao.findTeacherCountByCondition(teacher);
        }while (c>0);
        teacher.setSchoolId(school.getId());
        teacher.setSchoolName(school.getName());
        teacher.setPassword(DigestUtil.sha1Hex(DigestUtil.md5Hex(Constant.DEFAULT_PWD)));
        teacher.setStatus(Constant.STATUS.TEACHER_ADMIN);//管理员
        teacher.setName("管理员");//默认名称 管理员
        teacher.setSex("4");//默认 男
        this.saveTeacher(teacher);
    }
    @Transactional(readOnly = true)
    public List<Teacher> findTeachers4Yed(Teacher teacher) {
        return teacherDao.findTeachers4Yed(teacher);
    }
    @Transactional(readOnly = true)
    public long findTeachersCount4Yed(Teacher teacher) {
        return teacherDao.findTeachersCount4Yed(teacher);
    }
    @Transactional(readOnly = true)
    public Teacher findAdminBySchool(Teacher teacher) {
        return teacherDao.findAdminBySchool(teacher);
    }
    @Transactional
    public int batchUpdateTeacherRegisterStatus(Teacher teacher) {
        int rt = 0;
        teacher.setRegisterStatus("0");
        teacher.setPager(new Pager().setPageSize(500).setIncludes("name","password","id","sex","imgUrl"));
        int i = 0;
        long c = teacherDao.findTeacherCountByCondition(teacher);
        if(c==0)
            return rt;
        do{
            IMReturnResult<List<UserRigisterApiResult>> result = JMessageUserApi.registerJMessageUsers(teacherDao.findTeacherListByCondition(teacher).stream().map(t->{
                UserRegister userRegister = new UserRegister();
                userRegister.setUserName(t.getId());
                userRegister.setShowName(t.getName());
                userRegister.setPassword(DigestUtil.md5Hex(t.getId()));
                userRegister.setSex(Integer.parseInt(t.getSex())-3);
                userRegister.setUserType("2");
                userRegister.setDefaultHeadImage(t.getImgUrl());
                return userRegister;
            }).collect(Collectors.toList()),Constant.APP_TYPE.TAP_TYPE);
            if(result.getSuccess()){
                teacherDao.batchUpdateTeacherRegisterStatus(result.getData().stream().map(UserRigisterApiResult::getUsername).distinct().collect(Collectors.toList()), teacher.getSchoolId());
            }
            i++;
            c = teacherDao.findTeacherCountByCondition(teacher);
        }while(i<10 && c>0);
        return rt;
    }

    @Transactional(readOnly = true)
    public List<Teacher> findTeacherListByConditionRegister(Teacher teacher) {
        if(teacher.getName()!=null && !"".equals(teacher.getName().trim())){
            teacher.setName(teacher.getName().trim());
        }
        if(teacher.getPersonType()!=null){
            teacher.setPersonType(Constant.PERSON_TYPE.TEACHER);//限制只查询老师 后续有需求 可以开放 职工 没传的时候默认是教师
        }
        return teacherDao.findTeacherListByConditionRegister(teacher);
    }

    @Transactional(readOnly = true)
    public List<Teacher> findTeacherListSchoolId(Teacher teacher) {
        return teacherDao.findTeacherListSchoolId(teacher);

    }
    @Transactional
    public List<Perm> findTeacherAdminTreeMenu(String schoolId) {
        List<Perm> perms=permDao.findTeacherAdminTreeMenu(schoolId);
        return ObjectKit.buildTree(perms,"-1");
    }

    @Transactional(readOnly = true)
    public List<Teacher> findStudentTeachers(Teacher teacher) {
        return  teacherDao.findStudentTeachers(teacher);
    }
    @Transactional(readOnly = true)
    public Teacher rpmLogin(Teacher teacher) {
        teacher.setPager(new Pager().setIncludes("id","name","imgUrl","tel","schoolId","schoolName","status"));
        Teacher t = teacherDao.findOneTeacherByCondition(teacher);
        if(t==null)return null;
        ScanPerson scanPerson = new ScanPerson();
        scanPerson.setTeacherId(t.getId());
        ScanPerson sp = scanPersonDao.findOneScanPersonByCondition(scanPerson);
        if(sp==null)return null;
        return t;
    }
    @Transactional(readOnly = true)
    public List<Perm> findTeacherMenu4App(String id) {
        //移动端管理员不能登录
        //如果管理员能登陆的话 要开始管理员校验
//        Teacher teacher = this.findTeacherById(id);
//        if (Constant.STATUS.TEACHER_ADMIN.equals(teacher.getStatus())) {//说明是管理员
//            Perm perm = new Perm();
//            perm.setSchoolId(teacher.getSchoolId());
//            perm.setType(0);
//            return permDao.findPermListByCondition(perm);
//        } else {
//            return permDao.findTeacherTreeMenuByTId(id);
//        }
        return permDao.findTeacherTreeMenuByTId(id);
    }

    @Transactional(readOnly = true)
    public List<Perm> findDmssTeacherFuncPermsByTeacherId(String id) {
        //Teacher teacher = teacherDao.findTeacherById(id);
        return teacherDao.findDmssFuncPermsByTeacherId(id);
    }

    /**
     * 查询班主任所在班级以及授课班级
     * @param teacherId
     * @return
     */
    public List<JwClasses> findClass4AssociationByTeacherId(String teacherId) {
        List<JwClasses> classList = teacherPostDao.findClass4AssociationByTeacherId(teacherId);
        return classList;
    }
    //查找该教师当班主任的班级
    @Transactional(readOnly = true)
    public List<Teacher> findClassTeacherIsDirector(Teacher teacher){
        return teacherDao.findClassTeacherIsDirector(teacher);
    }

    /**
     * 给老师绑定 openId
     */
    public void bindOpenId2Teacher(Teacher teacher){
        //先删除 OpenId原先绑定的教师 在对现在教师进行绑定
        teacherDao.unBindOpenId2Teacher(teacher.getOpenId());
        //进行教师绑定
        Teacher t = new Teacher();
        t.setId(teacher.getId());
        t.setOpenId(teacher.getOpenId());
        teacherDao.updateTeacher(t);
    }
    @Transactional(readOnly = true)
    public Teacher ccLogin(LoginObj loginObj) {
        Teacher teacher = new Teacher();
        teacher.setTel(loginObj.getTel());
        teacher.setPassword(loginObj.getPassword());
        teacher.setPager(new Pager().setIncludes("id","name","imgUrl","tel","schoolId"));
        return teacherDao.findOneTeacherByCondition(teacher);
    }
    @Transactional
    public void updateSchoolName(School school) {
        teacherDao.updateSchoolName(school);
    }

    @Transactional(readOnly = true)
    public long findTeacherImgCountByCondition(Teacher teacher) {
        teacher.setPersonType(Constant.PERSON_TYPE.TEACHER);
        return teacherDao.findTeacherImgCountByCondition(teacher);
    }
    @Transactional(readOnly = true)
    public List<Teacher> findTeacherImgListByCondition(Teacher teacher) {
        teacher.setPersonType(Constant.PERSON_TYPE.TEACHER);
        return teacherDao.findTeacherImgListByCondition(teacher);
    }
    @Transactional(readOnly = true)
    public List<Teacher> findTeacherImgListByConditionWithoutType(Teacher teacher) {
       // teacher.setPersonType(Constant.PERSON_TYPE.TEACHER);
        return teacherDao.findTeacherImgListByCondition(teacher);
    }
    /**
     * 学校教师进行汇总
     * 得出总数，男老师数量，女老师数量
     * @param schoolId
     * @return
     */
    public Map<String,Long> findTeacherSummaryBySchool4Index(String schoolId){
        return teacherDao.findTeacherSummaryBySchool4Index(schoolId);
    }
    /**
     * 学校教师进行汇总
     * 按课程信息进行统计数据
     * 课程信息关联到字典表取字典课程名称
     * @param schoolId
     * @return
     */
    public List<Map<String,Object>> findCourseTeacherSummaryBySchool4Index(String schoolId){
        return teacherClassesDao.findCourseTeacherSummaryBySchool4Index(schoolId);
    }

    /**
     * 查询教师拥有的班牌权限
     * 列表
     * @param teacher
     * @return
     */
    @Transactional(readOnly = true)
    public List<Teacher> findTeacherManagerById(Teacher teacher) {
        return teacherDao.findTeacherManagerById(teacher);
    }

    /**
     * 保留默认职务
     * 目前默认职务是任何教师
     * @param teachers
     */
    private void saveDefaultPost(List<Teacher> teachers){
        final Dd dd = ddDao.findDdById(Constant.TEACHER_POST.ORDINARY_TEACHER);
        final String nt = DateUtil.now();
        Optional.ofNullable(teachers).ifPresent(list->{
            final List<TeacherPost> teacherPostList = list.stream().filter(t->Constant.STATUS.TEACHER_ON_LINE.equals(t.getStatus())).map(t->{
                TeacherPost teacherPost = new TeacherPost();
                teacherPost.setTeacherId(t.getId());
                teacherPost.setDdId(dd.getId());
                teacherPost.setName(dd.getName());
                teacherPost.setSort(dd.getSort());
                teacherPost.setId(sequenceId.nextId());
                teacherPost.setSchoolId(t.getSchoolId());
                teacherPost.setCreateTime(nt);
                return teacherPost;
            }).collect(Collectors.toList());
            if(teacherPostList!=null&&teacherPostList.size()>0)
                teacherPostDao.batchSaveTeacherPost(teacherPostList);
        });
    }
    /**
     * 查询教师列表
     * 内联班级信息
     * @param teacherVo
     * @return
     */
    public List<TeacherShow> findTeacherListInClassByCondition(TeacherVo teacherVo){
        return teacherDao.findTeacherListInClassByCondition(teacherVo);
    }
    /**
     * 查询教师列表数量
     * 内联班级信息
     * @param teacherVo
     * @return
     */
    public long findTeacherCountInClassByCondition(TeacherVo teacherVo){
        return teacherDao.findTeacherCountInClassByCondition(teacherVo);
    }

    /**
     * 查询教师列表 包含授课信息
     * @param teacherVo
     * @return
     */
    public List<TeacherShow> findTeacherListWithTeaching(TeacherVo teacherVo) {
        return teacherDao.findTeacherListWithTeaching(teacherVo);
    }

    /**
     * 查询教师授课信息
     * @param teacherVo
     * @return
     */
    public List<TeachingInfo> findTeachingInfoByCondition(TeacherVo teacherVo){
        return teacherDao.findTeachingInfoByCondition(teacherVo);
    }

    /**
     * 查询全校老师授课信息
     * @param schoolId
     * @return
     */
    public List<TeacherLessons> findTeachingIofoBySchoolId(String schoolId){
        return teacherDao.findTeachingIofoBySchoolId(schoolId);
    }
}
