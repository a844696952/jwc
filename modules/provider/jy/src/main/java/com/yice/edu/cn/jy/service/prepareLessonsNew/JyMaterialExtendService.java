package com.yice.edu.cn.jy.service.prepareLessonsNew;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jy.prepareLessonsNew.JyMaterialExtend;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.Material;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.SubjectMaterial;
import com.yice.edu.cn.jy.dao.prepareLessonsNew.IJyMaterialExtendDao;
import com.yice.edu.cn.jy.dao.subjectSource.IMaterialDao;
import com.yice.edu.cn.jy.dao.subjectSource.ISubjectMaterialDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JyMaterialExtendService {
    @Autowired
    private IJyMaterialExtendDao jyMaterialExtendDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private IMaterialDao materialDao;
    @Autowired
    private ISubjectMaterialDao iSubjectMaterialDao;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public JyMaterialExtend findJyMaterialExtendById(String id) {
        return jyMaterialExtendDao.findJyMaterialExtendById(id);
    }

    @Transactional
    public void saveJyMaterialExtend(JyMaterialExtend jyMaterialExtend) {
        jyMaterialExtend.setId(sequenceId.nextId());
        jyMaterialExtend.setDeleteStatus(Constant.DELETE_STATUS.EXIST);
        jyMaterialExtendDao.saveJyMaterialExtend(jyMaterialExtend);
    }

    //引用教材资源，同时要高亮显示已经被引用的
    @Transactional
    public List<Material> findMaterialListByCondition(JyMaterialExtend jyMaterialExtend) {
        if (jyMaterialExtend.getType().equals(Constant.TEXTBOOK.SCHOOL_TYPE)) {
            //得到当前年级所有教材资源
            List<Material> mList = materialDao.findMaterialListByCondition(jyMaterialExtend.getMaterial());
            //筛选已经引用的教材高亮显示
            List<JyMaterialExtend> meList = jyMaterialExtendDao.findJyMaterialExtendListByCondition(jyMaterialExtend);
            //将得到的学校资源中关联的教材id与资源中心的教材id对比，相同即被引用
            if (meList == null || meList.isEmpty()) {
                return mList;
            } else if ((meList != null && !meList.isEmpty()) && (mList != null && !mList.isEmpty())) {
                List<Material> materialList = mList.stream().map(skt1 -> {
                    meList.stream().forEach(skt2 -> {
                        if (skt1.getId().equals(skt2.getMaterialId())) {
                            skt1.setJyMaterialExtend(skt2);
                        }
                    });
                    return skt1;
                }).collect(Collectors.toList());
                return materialList;
            } else {
                return null;
            }
        } else {
            List<Material> resultList = findMaterialByTeacher(jyMaterialExtend);
            return resultList;
        }
    }

    //获取教师引用学校的资源
    private List<Material> findMaterialByTeacher(JyMaterialExtend jyMaterialExtend) {
        //得到当前年级的资源
        List<Material> mList = materialDao.findMaterialListByCondition(jyMaterialExtend.getMaterial());
        //得到当前学校且未取消引用的资源
        JyMaterialExtend js = new JyMaterialExtend();
        js.setType(Constant.TEXTBOOK.SCHOOL_TYPE);
        js.setSchoolId(jyMaterialExtend.getSchoolId());
        js.setDeleteStatus(Constant.DELETE_STATUS.EXIST);
        List<JyMaterialExtend> meList = jyMaterialExtendDao.findJyMaterialExtendListByCondition(js);
        //得到当前学校老师的资源
        List<JyMaterialExtend> teacherList = jyMaterialExtendDao.findJyMaterialExtendListByCondition(jyMaterialExtend);
        //筛选出学校资源，在学校的基础上筛选出教师资源
        if (meList == null || meList.isEmpty()) {
            return null;
        } else if ((meList != null && !meList.isEmpty())
                && (mList != null && !mList.isEmpty())) {
            List<Material> resultList = mList.stream()
                    .filter(skt1 -> meList.stream()
                            .filter(skt2 -> skt2.getDeleteStatus().equals(Constant.DELETE_STATUS.EXIST))
                            .map(skt2 -> skt2.getMaterialId())
                            .collect(Collectors.toList())
                            .contains(skt1.getId()))
                    .map(skt3 -> {
                        teacherList.stream().forEach(skt4 -> {
                            if (skt3.getId().equals(skt4.getMaterialId())) {
                                skt3.setJyMaterialExtend(skt4);
                            }
                        });
                        return skt3;
                    })
                    .collect(Collectors.toList());
            return resultList;
        } else {
            return null;
        }

    }

    @Transactional(readOnly = true)
    public List<JyMaterialExtend> findJyMaterialExtendListByCondition(JyMaterialExtend jyMaterialExtend) {
        //得到根据年级id得到资源中心年级教材
        List<Material> mList = materialDao.findMaterialListByCondition(jyMaterialExtend.getMaterial());
        //根据学校id和删除标志以及教材类型得到学校资源里面的教材资源
        jyMaterialExtend.setDeleteStatus(Constant.DELETE_STATUS.EXIST);
        List<JyMaterialExtend> meList = jyMaterialExtendDao.findJyMaterialExtendListByCondition(jyMaterialExtend);
        //将得到的学校资源中关联的教材id与资源中心的教材id对比，相同即为同一条数据，同时设置进去返回
        if ((meList != null && !meList.isEmpty()) && (mList != null && !mList.isEmpty())) {
            List<JyMaterialExtend> jmeList = meList.stream()
                    .filter(skt -> skt.getDeleteStatus().equals(Constant.DELETE_STATUS.EXIST))
                    .map(sky -> {
                        mList.stream().forEach(skt -> {
                            if (skt.getId().equals(sky.getMaterialId())) {
                                sky.setMaterial(skt);
                            }
                        });
                        return sky;
                    })
                    .filter(skt -> skt.getMaterial() != null)
                    .collect(Collectors.toList());

            if (jmeList != null || !jmeList.isEmpty()) {
                List<JyMaterialExtend> result = findClassNameAndSubjectName(jmeList);
                return result;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 查询教材信息对应的年级和科目
     *
     * @param list
     * @return
     */
    public List<JyMaterialExtend> findClassNameAndSubjectName(List<JyMaterialExtend> list) {
        List<JyMaterialExtend> result = list.stream().map(skt -> {
            String subjectMaterialId = skt.getMaterial().getSubjectMaterialId();
            //年级
            SubjectMaterial subjectMaterialClass = iSubjectMaterialDao.findSubjectMaterialById(subjectMaterialId);
            //科目
            SubjectMaterial subjectMaterial = iSubjectMaterialDao.findSubjectMaterialById(subjectMaterialClass.getParentId());
            skt.setGradeType(subjectMaterialClass.getName());
            skt.setSubjectName(subjectMaterial.getName());

            return skt;
        }).collect(Collectors.toList());

        return result;
    }

    @Transactional(readOnly = true)
    public JyMaterialExtend findOneJyMaterialExtendByCondition(JyMaterialExtend jyMaterialExtend) {
        return jyMaterialExtendDao.findOneJyMaterialExtendByCondition(jyMaterialExtend);
    }

    @Transactional(readOnly = true)
    public long findJyMaterialExtendCountByCondition(JyMaterialExtend jyMaterialExtend) {
        return jyMaterialExtendDao.findJyMaterialExtendCountByCondition(jyMaterialExtend);
    }

    @Transactional
    public void updateJyMaterialExtend(JyMaterialExtend jyMaterialExtend) {
        jyMaterialExtendDao.updateJyMaterialExtend(jyMaterialExtend);
    }

    @Transactional
    public void updateJyMaterialExtendForAll(JyMaterialExtend jyMaterialExtend) {
        jyMaterialExtendDao.updateJyMaterialExtendForAll(jyMaterialExtend);
    }

    @Transactional
    public void deleteJyMaterialExtend(String id) {
        jyMaterialExtendDao.deleteJyMaterialExtend(id);
    }

    @Transactional
    public void deleteJyMaterialExtendByCondition(JyMaterialExtend jyMaterialExtend) {
        jyMaterialExtendDao.deleteJyMaterialExtendByCondition(jyMaterialExtend);
    }

    @Transactional
    public void batchSaveJyMaterialExtend(List<JyMaterialExtend> jyMaterialExtends) {
        jyMaterialExtends.stream().forEach(skt1 -> {
            if (skt1.getId() == null && skt1.getMaterialId() != null) {
                //新增
                skt1.setId(sequenceId.nextId());
                skt1.setDeleteStatus(Constant.DELETE_STATUS.EXIST);
                jyMaterialExtendDao.saveJyMaterialExtend(skt1);
            } else if (skt1.getId() != null && skt1.getDeleteStatus() != null) {
                //修改，取消引用，也可能是取消引用之后再次引用
                updateJyMaterialExtend(skt1);
            }
        });
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
