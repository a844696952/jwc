package com.yice.edu.cn.jw.service.classSchedule;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.classSchedule.*;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.jw.teacher.*;
import com.yice.edu.cn.common.util.weekDayUtil.WeekDayUtil;
import com.yice.edu.cn.jw.dao.classSchedule.IClassScheduleDao;
import com.yice.edu.cn.jw.dao.classes.IJwClassesDao;
import com.yice.edu.cn.jw.dao.dd.IDdDao;
import com.yice.edu.cn.jw.dao.jwCourse.JwCourseDao;
import com.yice.edu.cn.jw.dao.school.ISchoolDao;
import com.yice.edu.cn.jw.service.teacher.TeacherClassesService;
import com.yice.edu.cn.jw.service.teacher.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ClassScheduleService {
    @Autowired
    private IClassScheduleDao classScheduleDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private IDdDao ddDao;
    @Autowired
    private ISchoolDao schoolDao;
    @Autowired
    private IJwClassesDao jwClassesDao;
    @Autowired
    private JwCourseDao jwCourseDao;
    @Autowired
    private TeacherClassesService teacherClassesService;
    @Autowired
    private ClassScheduleInitService classScheduleInitService;
    @Autowired
    private TeacherService teacherService;


    @Transactional(readOnly = true)
    public ClassSchedule findClassScheduleById(String id) {
        return classScheduleDao.findClassScheduleById(id);
    }

    @Transactional
    public void saveClassSchedule(ClassSchedule classSchedule) {
        classSchedule.setId(sequenceId.nextId());
        classScheduleDao.saveClassSchedule(classSchedule);
    }

    @Transactional(readOnly = true)
    public List<ClassSchedule> findClassScheduleListByCondition(ClassSchedule classSchedule) {
        return classScheduleDao.findClassScheduleListByCondition(classSchedule);
    }

    @Transactional(readOnly = true)
    public List<ClassSchedule> findClassScheduleListByConditions(ClassSchedule classSchedule) {
        return classScheduleDao.findClassScheduleListByConditions(classSchedule);
    }

    @Transactional(readOnly = true)
    public ClassSchedule findOneClassScheduleByCondition(ClassSchedule classSchedule) {
        return classScheduleDao.findOneClassScheduleByCondition(classSchedule);
    }

    @Transactional(readOnly = true)
    public long findClassScheduleCountByCondition(ClassSchedule classSchedule) {
        return classScheduleDao.findClassScheduleCountByCondition(classSchedule);
    }

    @Transactional
    public void updateClassSchedule(ClassSchedule classSchedule) {
        classScheduleDao.updateClassSchedule(classSchedule);
    }

    @Transactional
    public void deleteClassSchedule(String id) {
        classScheduleDao.deleteClassSchedule(id);
    }

    @Transactional
    public void deleteClassScheduleByCondition(ClassSchedule classSchedule) {
        classScheduleDao.deleteClassScheduleByCondition(classSchedule);
    }


    @Transactional
    public void batchSaveClassSchedule(List<ClassSchedule> classScheduleList){
        classScheduleList.forEach(f->f.setId(sequenceId.nextId()));
        classScheduleDao.batchSaveClassSchedule(classScheduleList);

    }

    @Transactional
    public void batchDeleteClassScheduleInScheduleId(List<String> scheduleIds){
        classScheduleDao.batchDeleteClassScheduleInScheduleId(scheduleIds);
    }
    @Transactional(readOnly = true)
    public List<ClassSchedule> batchSeleteClassScheduleInScheduleId(List<String> scheduleIds){
        return classScheduleDao.batchSeleteClassScheduleInScheduleId(scheduleIds);
    }

    @Transactional(readOnly = true)
    public List<Dd> findGradesBySchoolId(String id,String scheduleId) {
        School school = schoolDao.findSchoolById(id);
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.GRADE);
        dd.setLevelType(school.getTypeId());
        List<Dd> dds = ddDao.findDdListByCondition(dd);
        ClassSchedule classSchedule = new ClassSchedule();
        classSchedule.setSchoolId(id);
        classSchedule.setScheduleId(scheduleId);
        List<ClassSchedule> classSchedules= classScheduleDao.findClassScheduleListByConditions(classSchedule);
        //??????????????????????????????????????????
        List<ClassSchedule> collect1 = classSchedules.stream().filter(f -> f.getTeacherName() != null).collect(Collectors.toList());
        dds.forEach(d -> {
            JwClasses jwClasses = new JwClasses();
            jwClasses.setGradeId(d.getId());
            jwClasses.setSchoolId(id);
            jwClasses.setDel(Constant.DELSIGN.NORMAL);
            List<JwClasses> jwClassesList = jwClassesDao.findJwClassesListByCondition(jwClasses);
            jwClassesList.sort((jc1,jc2)->Integer.parseInt(jc1.getNumber())-Integer.parseInt(jc2.getNumber()));
            List<Dd> children = new ArrayList<>();
            jwClassesList.forEach(claz -> {
                Dd child = new Dd();
                child.setId(claz.getId());
                child.setName(claz.getGradeName() + "(" + claz.getNumber() + ")" + '???');
                child.setGradeId(claz.getGradeId());
                long c = classSchedules.stream().filter(f -> f.getClassId().equals(claz.getId())&&(f.getTeacherName()==null||f.getCourseName()==null)).count();
                int[] count = new int[1];
                //????????????????????????
                List<ClassSchedule> collect = collect1.stream().filter(f -> f.getClassId().equals(claz.getId())).collect(Collectors.toList());
                collect.stream().forEach(f->{
                    collect1.stream().forEach(fo1->{
                            if(f.getWeekId().equals(fo1.getWeekId())&&f.getNumberId().equals(fo1.getNumberId())&&f.getTeacherName().equals(fo1.getTeacherName())&&(!f.getClassId().equals(fo1.getClassId()))){
                                count[0]=count[0]+1;
                             }
                            }
                    );
                });
                c = c+count[0];
                child.setSort(collect.size());
                child.setTotalCount(c);
                children.add(child);
            });
            d.setChildren(children);
        });
        return dds;
    }

    @Transactional
    public List<ClassSchedule> getTeacherNameAndCourseAndCount(ClassSchedule classSchedule) {

        return classScheduleDao.getTeacherNameAndCourseAndCount(classSchedule);
    }


    @Transactional
    public void updateOrFind(ClassSchedule classSchedule) {
        deletebatchdelete(classSchedule);
        List<ClassSchedule> collect = classSchedule.getClassScheduleList().stream().filter(f -> f != null&&(f.getCourseId()!=null||f.getTeacherId()!=null)).collect(Collectors.toList());
        if (collect!=null&&collect.size()>0){
            batchSaveClassSchedule(collect);
        }

       /* List<ClassSchedule> classSchedules = new ArrayList<>();
        classSchedule.forEach(c -> {
            if (c.getCourseTeacherId().equals("delete")) {//???????????????????????????
                classScheduleDao.deleteClassSchedule(c.getId());//??????
            } else {
                if (c.getId() == null) {//?????????????????????????????????(id==null????????????)
                    ClassSchedule classSchedule1 = new ClassSchedule();
                    classSchedule1.setWeekId(c.getWeekId());
                    classSchedule1.setNumberId(c.getNumberId());
                    classSchedule1.setClassId(c.getClassId());
                    classSchedule1.setSchoolId(c.getSchoolId());

                    //?????????????????????????????????????????????????????????????????????????????????
                    List<ClassSchedule> lists =classScheduleDao.findClassScheduleListByCondition(classSchedule1);
                    if(lists.size()==0){
                        classSchedules.add(c);
                        c.setId(sequenceId.nextId());
                        classScheduleDao.saveClassSchedule(c);//??????????????????
                    }else{
                        c.setId(lists.get(0).getId());//??????????????????id
                        classScheduleDao.updateClassSchedule(c);
                    }
                } else {
                    classScheduleDao.updateClassSchedule(c);//???id??????????????????
                }
            }
        });*/
    }


    //????????????????????????????????????????????????
    @Transactional
    public void deletebatchdelete(ClassSchedule classSchedule) {
            classScheduleDao.deleteClassScheduleByCondition(classSchedule);
    }

    @Transactional
    public List<List<ClassSchedule>> findList(ClassSchedule classSchedule) {
        List<ClassSchedule> data = classScheduleDao.findClassScheduleListByConditions(classSchedule);
        ClassScheduleInit classScheduleInit = new ClassScheduleInit();
        classScheduleInit.setSchoolId(classSchedule.getSchoolId());
        long count = classScheduleInitService.findClassScheduleInitCountByCondition(classScheduleInit);
        if(count==0){
            count = Constant.CLASS_SCHEDULE.CLASS_SCHEDULE_NUMBER;
        }
        List<List<ClassSchedule>> list = new ArrayList();
        int jieshu =(int)count;//???????????????
        int xingqi = 7;//???????????????
        for (int i = 1; i <= jieshu; i++) {
            List<ClassSchedule> clist = new ArrayList<ClassSchedule>();
            for (int j = 1; j <= xingqi; j++) {
                ClassSchedule cs = null;
                for (ClassSchedule schedule : data) {
                    if (schedule.getWeekId() == j && schedule.getNumberId() == i) {
                        cs = schedule;
                        if(cs.getGradeName()!=null&&cs.getClassName()!=null){
                            cs.setGradeNameClassName(cs.getGradeName()+"("+cs.getClassName()+")???");
                        }else{
                            JwClasses  jwClasses =  jwClassesDao.findJwClassesById(cs.getClassId());
                            if(jwClasses!=null){
                                cs.setGradeName(jwClasses.getGradeName());
                                cs.setClassName(jwClasses.getNumber()+"???");
                                cs.setGradeNameClassName(jwClasses.getGradeName()+jwClasses.getNumber()+"???");
                            }
                        }


                    }
                }
                clist.add(cs);
            }
            list.add(clist);
        }
        return list;
    }

    //??????????????????
    @Transactional
    public List<ClassSchedule> todayClassScheduleList(ClassSchedule classSchedule){
        List<ClassSchedule> classSchedules = findClassScheduleListByConditions(classSchedule);
        classSchedules.forEach(cs->{
            if(cs.getGradeName()!=null&&cs.getClassName()!=null){
                cs.setGradeNameClassName(cs.getGradeName()+"("+cs.getClassName()+")???");
            }else{
                JwClasses  jwClasses =  jwClassesDao.findJwClassesById(cs.getClassId());
                if(jwClasses!=null){
                    cs.setGradeName(jwClasses.getGradeName());
                    cs.setClassName(jwClasses.getNumber()+"???");
                    cs.setGradeNameClassName(jwClasses.getGradeName()+jwClasses.getNumber()+"???");
                }

            }
            conversionSection(cs);
        });
        return  classSchedules;
    }


    //??????????????????????????????
    @Transactional
    public ResponseJson todayAndTomorrowClassSchede(ClassSchedule classSchedule){
        Date date = new Date();
        int s = date.getDay();
        if(s==0){
            s=7;
        }
        int m = s+1==8?1:s+1;
        classSchedule.setWeekId(s);
        //??????
        List<ClassSchedule> classSchedules = findClassScheduleListByConditions(classSchedule);

        //??????
        classSchedule.setWeekId(m);
        List<ClassSchedule> classSchedules1 = findClassScheduleListByConditions(classSchedule);


        return  new ResponseJson(classSchedules,classSchedules1);
    }

    public List<List<ClassSchedule>> hangzhuanlie(List<List<ClassSchedule>> classSchedule,String schoolId){
        ClassScheduleInit classScheduleInit = new ClassScheduleInit();
        classScheduleInit.setSchoolId(schoolId);
        long count = classScheduleInitService.findClassScheduleInitCountByCondition(classScheduleInit);
        if(count==0){
            count = Constant.CLASS_SCHEDULE.CLASS_SCHEDULE_NUMBER;
        }
        List<List<ClassSchedule>> lists = new ArrayList<>();
        for(int i =0;i<7;i++){
            List<ClassSchedule> scheduleList = new ArrayList<>();
            for(int j=0;j<count;j++){
                scheduleList.add(classSchedule.get(j).get(i));
            }
            lists.add(scheduleList);
        }
        return  lists;
    }
    //?????????????????????Id,weekId,numberId
    public ResponseJson getTeacherList(ClassSchedule classSchedule){
            List<Teacher> teachers = teacherClassesService.findTeacherByClassAndCourse(classSchedule.getClassId(),classSchedule.getCourseId());
            ClassSchedule classSchedule1 = new ClassSchedule();
            classSchedule1.setWeekId(classSchedule.getWeekId());
            classSchedule1.setNumberId(classSchedule.getNumberId());
            classSchedule1.setSchoolId(classSchedule.getSchoolId());
            List<ClassSchedule> classSchedules =   findClassScheduleListByConditions(classSchedule1);
            if(teachers.isEmpty()){
                TeacherClasses teacherClasses = new TeacherClasses();
                teacherClasses.setClassesId(classSchedule.getClassId());
                Teacher teacher = teacherClassesService.findHeadmasterByClasses(teacherClasses);
                if(teacher!=null)
                teachers.add(teacher);
            }
            teachers.forEach(f->{
                for(ClassSchedule l :classSchedules){
                    if(f.getId().equals(l.getTeacherId())&&!l.getClassId().equals(classSchedule.getClassId())){
                        //???????????????????????????????????????????????????
                        f.setType("1");
                        break;
                    }
                }

            });
            return new ResponseJson(teachers);
        }

    public List<Map<String,String>> findCourseTeacherByClass(TeacherClasses teacherClasses){
        return teacherClassesService.findCourseTeacherByClass(teacherClasses);
    }

    /**
     * flags???true????????????????????????
     * @param classSchedule
     * @param flags
     * @return
     */
    public List<List<ClassSchedule>> removeNullClassSchedule(List<List<ClassSchedule>> classSchedule,Boolean flags){
        for(int i = classSchedule.size()-1;i>=0;i--){
            for(int j = classSchedule.get(i).size()-1;j>=0;j--){
                if(!(classSchedule.get(i).get(j)!=null)){
                    classSchedule.get(i).remove(j);
                }else{
                    conversionSection(classSchedule.get(i).get(j));
                    classSchedule.get(i).get(j).setWeekId(null);
                    classSchedule.get(i).get(j).setNumberId(null);
                    classSchedule.get(i).get(j).setGradeName(null);
                    classSchedule.get(i).get(j).setClassName(null);
                    if(flags){
                        classSchedule.get(i).get(j).setTeacherName(null);
                    }

                }
            }


        }
        return classSchedule;
    }


    /**
     * flags???true????????????????????????
     * @param classSchedules
     * @param flags
     * @return
     */
    public static void romoveClassSchedule(List<ClassSchedule> classSchedules,Boolean flags){
        for(int i=classSchedules.size()-1;i>=0;i--){
            if(!(classSchedules.get(i).getCourseName()!=null&&classSchedules.get(i).getTeacherName()!=null)){
                classSchedules.remove(i);
            }else {
                classSchedules.get(i).setGradeName(null);
                classSchedules.get(i).setClassName(null);
                classSchedules.get(i).setFloor(null);
                if(flags){
                    classSchedules.get(i).setTeacherName(null);
                }
            }
        }

    }

    @Transactional(readOnly = true)
    public List<ClassSchedule> verifyImport(String classesId){
        return classScheduleDao.verifyImport(classesId);
    }

    @Transactional
    public Map<String,Object> conversionSchedule(ImportClassSchedule importClassSchedule){
        Map<String,Object> result = new HashMap<>();
        List<Schedule> schedules = importClassSchedule.getSchedules();
        List<String> error = importClassSchedule.getError();
        String gradeId = importClassSchedule.getGradeId();
        String classesId = importClassSchedule.getClassesId();

        List<ClassSchedule> classSchedules  = classScheduleDao.verifyImport(classesId);
        int classSchedulesSize = classSchedules.size();

        List<ClassSchedule> list = new ArrayList<>();
        for(int j=0;j<schedules.size();j++){
            String[] fieldNames =  getFiledName(schedules.get(j));
            for (int i=0;i<fieldNames.length;i++){
                ClassSchedule classSchedule = new ClassSchedule();
                String name = fieldNames[i];
                Object value = getFieldValueByName(name,schedules.get(j));
                if(!name.equals("num")){
                    if(value!=null){
                        String danyuange = (String) value;
                        String[] dygs = danyuange.replaceAll("\\r\\n",",").replaceAll("???",",").replaceAll("\\n",",").replaceAll(" ",",").split(",");
                        if(dygs.length<2){
                            error.add(WeekDayUtil.getWeek(i)+"???"+(j+1)+"?????????????????????????????????");
                        }else{
                            boolean teacherFlag = true;
                            boolean courseFlag = true;
                            /*boolean jwSpaceFlag = true;*/
                            for(int z=0;z<classSchedulesSize;z++){
                                //??????
                                if(dygs[0].equals(classSchedules.get(z).getCourseName())){
                                    classSchedule.setCourseId(classSchedules.get(z).getCourseId());
                                    classSchedule.setTypeId(classSchedules.get(z).getTypeId());
                                    courseFlag = false;
                                }
                                //??????
                                if(dygs[1].equals(classSchedules.get(z).getTeacherName())){
                                    classSchedule.setTeacherId(classSchedules.get(z).getId());
                                    teacherFlag = false;
                                }

                            }
                            if(courseFlag){
                                error.add(WeekDayUtil.getWeek(i)+"???"+(j+1)+"??????????????????");
                            }
                            if(teacherFlag){
                                error.add(WeekDayUtil.getWeek(i)+"???"+(j+1)+"??????????????????");
                            }
                            classSchedule.setNumberId(j+1);
                            classSchedule.setWeekId(i);
                            classSchedule.setClassId(classesId);
                            classSchedule.setSchoolId(importClassSchedule.getSchoolId());
                            list.add(classSchedule);
                        }
                    }
                }

            }
        }
        if(error!=null&&error.size()>0){
            result.put("code","222");
            result.put("error",error);
            return result;
        }else{
            //????????????????????????????????????
            ClassSchedule classSchedule = new ClassSchedule();
            classSchedule.setClassId(classesId);
            classScheduleDao.deleteClassScheduleByCondition(classSchedule);
            batchSaveClassSchedule(list);
            result.put("code","200");
            result.put("message","?????????????????????????????????");
        }
        return result;
    }


    public void  conversionSection(ClassSchedule cs){
        if(cs.getNumberId()==1){
            cs.setNumberName("???");
        }else if(cs.getNumberId()==2){
            cs.setNumberName("???");
        }else if(cs.getNumberId()==3){
            cs.setNumberName("???");
        }else if(cs.getNumberId()==4){
            cs.setNumberName("???");
        }else if(cs.getNumberId()==5){
            cs.setNumberName("???");
        }else if(cs.getNumberId()==6){
            cs.setNumberName("???");
        }else if(cs.getNumberId()==7){
            cs.setNumberName("???");
        }else if(cs.getNumberId()==8){
            cs.setNumberName("???");
        }else if(cs.getNumberId()==9){
            cs.setNumberName("???");
        }else if(cs.getNumberId()==10){
            cs.setNumberName("???");
        }else if(cs.getNumberId()==11) {
            cs.setNumberName("??????");
        }else if(cs.getNumberId()==12){
            cs.setNumberName("??????");
        }else if(cs.getNumberId()==13){
            cs.setNumberName("??????");
        }else if(cs.getNumberId()==14){
            cs.setNumberName("??????");
        }else if(cs.getNumberId()==15){
            cs.setNumberName("??????");
        }
    }
    /**
     * ?????????????????????
     * */
    private static String[] getFiledName(Object o){
        Field[] fields=o.getClass().getDeclaredFields();
        String[] fieldNames=new String[fields.length];
        for(int i=0;i<fields.length;i++){
            fieldNames[i]=fields[i].getName();
        }
        return fieldNames;
    }

    /**
     * ??????????????????????????????
     */
    public static Object getFieldValueByName(String fieldName,Object o ){
        try {
            String firstLetter = fieldName.substring(0,1).toUpperCase();
            String getter = "get"+firstLetter+fieldName.substring(1);
            Method method = o.getClass().getMethod(getter,new Class[]{});
            Object value = method.invoke(o,new Object[]{});
            return value;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Transactional(readOnly = true)
    public String findClassIdByUserName(String userName){
        return classScheduleDao.findClassIdByUserName(userName);
    }

    @Transactional
    public void deleteSchoolScheduleInClassId(List<String> classId){
        classScheduleDao.deleteSchoolScheduleInClassId(classId);
    }

    @Transactional(readOnly = true)
    public List<ClassSchedule> findClassScheduleGroupClassId(ClassSchedule classSchedule){
        return classScheduleDao.findClassScheduleGroupClassId(classSchedule);
    }

    @Transactional
    public Map<String,Object> conversionScheduleNew(ImportClassSchedule importClassSchedule){
        School school = schoolDao.findSchoolById(importClassSchedule.getSchoolId());
        Integer count = importClassSchedule.getCount();
        JwCourse jwCourse = new JwCourse();
        JwClasses jwClasses = new JwClasses();
        jwClasses.setSchoolId(importClassSchedule.getSchoolId());
        jwCourse.setSchoolId(importClassSchedule.getSchoolId());
        //???????????????????????????????????????
        List<JwCourse> jwCourseList = jwCourseDao.findJwCourseListByConditionKong(jwCourse);
        List<JwClasses> jwClassesList = jwClassesDao.findJwClassesListByCondition(jwClasses);
        List<String> classes = new ArrayList<>();
        for(List<String> s:importClassSchedule.getLists()){
            if(s.get(0).length()>2)
            classes.add(s.get(0).trim());
        }
        List<String> error = new ArrayList<>();
        List<Map<String,String>> classNmaeNumber = new ArrayList<>();
        classes.stream().forEach(f->{
            String[] className = new String[2];
            Map<String,String> map = new HashMap<>();
            if (school.getTypeId().equals("120")) {
                className[0] = f.substring(0,3);
            }else{
                className[0] =  f.substring(0,2);
            }
            Pattern pattern = Pattern.compile("[^0-9]");
            Matcher m = pattern.matcher(f);

            Boolean b = jwClassesList.stream().anyMatch(am->am.getGradeName().equals(className[0])&&am.getNumber().equals(m.replaceAll("").trim()));
            if(!b){
                error.add("????????????"+f);
            }
            map.put("gradeName",className[0]);
            map.put("number",m.replaceAll("").trim());
            classNmaeNumber.add(map);
        });
        Map<String,Object> result = new HashMap<>();
        if(error.size()>0){
            result.put("code","222");
            result.put("error",error);
            return result;
        }
        List<TeacherLessons> teacherList = teacherService.findTeachingIofoBySchoolId(importClassSchedule.getSchoolId());
        List<String> deleteClassSchedules = new ArrayList<>();
        List<ClassSchedule> classSchedules = new ArrayList<>();
        for(int i =0;i<classNmaeNumber.size();i++){
            int num = 1;
            Map<String, String> map1 = classNmaeNumber.get(i);
            for(int j =1;j<importClassSchedule.getLists().get(i).size();j++){
                ClassSchedule classSchedule = new ClassSchedule();
                classSchedule.setScheduleId(importClassSchedule.getScheduleId());
                classSchedule.setSchoolId(importClassSchedule.getSchoolId());
                if(!importClassSchedule.getLists().get(i).get(j).equals("0")){
                    String s = importClassSchedule.getLists().get(i).get(j);
                    boolean b = jwCourseList.stream().anyMatch(jclAm->jclAm.getAlias().equals(s));
                    if(b){
                        List<TeacherLessons> collect = teacherList.stream().filter(f -> f.getClassName().equals(map1.get("number")) &&
                                f.getGradeName().equals(map1.get("gradeName")) &&
                                f.getCourseName().equals(s)).collect(Collectors.toList());
                        if(collect==null||collect.size()==0){
                            classSchedule.setClassId(jwClassesList.stream().filter(f->f.getNumber().equals(map1.get("number"))&&f.getGradeName().equals(map1.get("gradeName"))).findFirst().get().getId());
                            error.add(importClassSchedule.getLists().get(i).get(0)+WeekDayUtil.returnWeek(j, count)+"???"+num+"??????,??????????????????"+s);
                        }else if(collect.size()>=2){
                            classSchedule.setClassId(jwClassesList.stream().filter(f->f.getNumber().equals(map1.get("number"))&&f.getGradeName().equals(map1.get("gradeName"))).findFirst().get().getId());
                            classSchedule.setCourseId(jwCourseList.stream().filter(f->f.getAlias().equals(s)).findFirst().map(JwCourse::getId).get());
                            error.add(importClassSchedule.getLists().get(i).get(0)+WeekDayUtil.returnWeek(j, count)+"???"+num+"??????,???????????????????????????????????????");
                        }else{
                            classSchedule.setClassId(collect.get(0).getClassId());
                            classSchedule.setCourseId(collect.get(0).getCourseId());
                            classSchedule.setTeacherId(collect.get(0).getId());
                        }
                    }else{
                        String weekName = WeekDayUtil.returnWeek(j, count);
                        error.add(importClassSchedule.getLists().get(i).get(0)+weekName+"???"+num+"???????????????????????????!");
                        classSchedule.setClassId(jwClassesList.stream().filter(f->f.getNumber().equals(map1.get("number"))&&f.getGradeName().equals(map1.get("gradeName"))).findFirst().get().getId());
                    }
                    classSchedule.setGradeName(importClassSchedule.getLists().get(i).get(0));
                    classSchedule.setWeekName(WeekDayUtil.returnWeek(j,count));
                    classSchedule.setWeekId( WeekDayUtil.returnWeekNumber(WeekDayUtil.returnWeek(j, count)));
                    classSchedule.setNumberId(num);
                    classSchedules.add(classSchedule);
                    deleteClassSchedules.add(classSchedule.getClassId());
                }
                if(num>=count){
                    num = 0;
                }
                    num++;
            }
        }
        List<String> collect1 = deleteClassSchedules.stream().distinct().collect(Collectors.toList());
        if(collect1==null||collect1.size()==0){
            error.add("????????????????????????!");
            result.put("code","222");
            result.put("error",error);
            return result;
        }
        ScheduleDel scheduleDel = new ScheduleDel();
        scheduleDel.setStringList(collect1);
        scheduleDel.setScheduleId(importClassSchedule.getScheduleId());
        classScheduleDao.deleteSchoolScheduleInClassIdAndScheduleId(scheduleDel);

        //?????????????????????
        ClassSchedule classSchedule = new ClassSchedule();
        classSchedule.setSchoolId(school.getId());
        List<ClassSchedule> classScheduleList = findClassScheduleListByConditions(classSchedule);
        //???????????????????????????
        List<ClassSchedule> collect = classScheduleList.stream().filter(f -> f.getTeacherId() != null).collect(Collectors.toList());
        //???????????????????????????????????????
        classSchedules.stream().filter(f->f.getTeacherId()!=null).forEach(f->{
            collect.stream().forEach(fe->{
                if(f.getWeekId().equals(fe.getWeekId())&&f.getNumberId().equals(fe.getNumberId())&&f.getTeacherId().equals(fe.getTeacherId())&&!f.getClassId().equals(fe.getClassId())){
                    error.add(f.getGradeName()+","+f.getWeekName()+",???"+f.getNumberId()+"?????????"+fe.getGradeName()+"???"+fe.getClassName()+"??????,"+WeekDayUtil.getWeek(fe.getWeekId())+",???"+fe.getNumberId()+"????????????????????????");
                }
            });
        });

        //?????????excel??????????????????????????????
        classSchedules.stream().filter(f->f.getTeacherId()!=null).forEach(f->{
            classSchedules.stream().filter(fi->fi.getTeacherId()!=null).forEach(fe->{
                if(f.getWeekId().equals(fe.getWeekId())&&f.getNumberId().equals(fe.getNumberId())&&f.getTeacherId().equals(fe.getTeacherId())&&(!f.getClassId().equals(fe.getClassId()))){
                    error.add(f.getGradeName()+","+f.getWeekName()+",???"+f.getNumberId()+"?????????"+fe.getGradeName()+","+fe.getWeekName()+",???"+fe.getNumberId()+"????????????????????????");
                }
            });
        });

        batchSaveClassSchedule(classSchedules);

        if(error.size()>0){
            result.put("code","222");
            result.put("error",error);
        }else{
            result.put("code","200");
            result.put("message","??????????????????????????????");
        }
        return result;
    }



}