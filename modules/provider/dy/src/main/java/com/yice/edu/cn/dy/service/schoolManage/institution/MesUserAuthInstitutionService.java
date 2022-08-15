package com.yice.edu.cn.dy.service.schoolManage.institution;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesInstitution;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesUserAuthInstitution;
import com.yice.edu.cn.dy.dao.schoolManage.institution.IMesInstitutionDao;
import com.yice.edu.cn.dy.dao.schoolManage.institution.IMesUserAuthInstitutionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MesUserAuthInstitutionService {
    @Autowired
    private IMesUserAuthInstitutionDao mesUserAuthInstitutionDao;
    @Autowired
    private IMesInstitutionDao mesInstitutionDao;
    @Autowired
    private SequenceId sequenceId;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public MesUserAuthInstitution findMesUserAuthInstitutionById(String id) {
        return mesUserAuthInstitutionDao.findMesUserAuthInstitutionById(id);
    }

    @Transactional
    public void saveMesUserAuthInstitution(MesUserAuthInstitution mesUserAuthInstitution) {
        mesUserAuthInstitution.setId(sequenceId.nextId());
        mesUserAuthInstitutionDao.saveMesUserAuthInstitution(mesUserAuthInstitution);
    }

    @Transactional(readOnly = true)
    public List<MesUserAuthInstitution> findMesUserAuthInstitutionListByCondition(MesUserAuthInstitution mesUserAuthInstitution) {
        List<Department> departments = mesUserAuthInstitutionDao.findDepartmentTreeBySchoolId(mesUserAuthInstitution.getSchoolId());
        List<Student> allStudents = mesInstitutionDao.findStudentsBySchoolId(mesUserAuthInstitution.getSchoolId());
        if (mesUserAuthInstitution.getUserType() == 1) {
            //查看学生
            List<MesUserAuthInstitution> list = mesUserAuthInstitutionDao.findMesUserAuthInstitutionListStuByCondition(mesUserAuthInstitution);
            setStuIsExist(allStudents, list);
            return list;
        }
        List<MesUserAuthInstitution> list = mesUserAuthInstitutionDao.findMesUserAuthInstitutionListTeaByCondition(mesUserAuthInstitution);
        setTeaIsExist(departments, list);
        return list;
    }

    public void setTeaIsExist(List<Department> departments, List<MesUserAuthInstitution> list) {
        if (CollUtil.isEmpty(list)) {
            return;
        }
        if (CollUtil.isEmpty(departments)) {
            for (MesUserAuthInstitution userAuthInstitution : list) {
                userAuthInstitution.setIsExist(0);
            }
        } else {
            for (MesUserAuthInstitution userAuthInstitution : list) {
                for (Department department : departments) {
                    if (department.getId().equals(userAuthInstitution.getUserId())) {
                        userAuthInstitution.setIsExist(1);
                        break;
                    } else {
                        userAuthInstitution.setIsExist(0);
                    }
                }
            }
        }
    }

    public void setStuIsExist(List<Student> allStudents, List<MesUserAuthInstitution> list) {
        if (CollUtil.isEmpty(list)) {
            return;
        }
        if (CollUtil.isEmpty(allStudents)) {
            for (MesUserAuthInstitution userAuthInstitution : list) {
                userAuthInstitution.setIsExist(0);
            }
        } else {
            for (MesUserAuthInstitution userAuthInstitution : list) {
                for (Student student : allStudents) {
                    if (student.getId().equals(userAuthInstitution.getUserId())) {
                        userAuthInstitution.setIsExist(1);
                        break;
                    } else {
                        userAuthInstitution.setIsExist(0);
                    }
                }
            }
        }
    }

    @Transactional(readOnly = true)
    public MesUserAuthInstitution findOneMesUserAuthInstitutionByCondition(MesUserAuthInstitution mesUserAuthInstitution) {
        return mesUserAuthInstitutionDao.findOneMesUserAuthInstitutionByCondition(mesUserAuthInstitution);
    }

    @Transactional(readOnly = true)
    public long findMesUserAuthInstitutionCountByCondition(MesUserAuthInstitution mesUserAuthInstitution) {
        return mesUserAuthInstitutionDao.findMesUserAuthInstitutionCountByCondition(mesUserAuthInstitution);
    }

    @Transactional
    public void updateMesUserAuthInstitution(MesInstitution mesInstitution) {
        List<String> userIds = mesInstitution.getUserIds();
        if (CollUtil.isEmpty(userIds)) {
            userIds = new ArrayList<>();
        }
        MesUserAuthInstitution mesUserAuthInstitution = new MesUserAuthInstitution();
        Integer userType = mesInstitution.getUserType();
        mesUserAuthInstitution.setInstitutionId(mesInstitution.getId());
        mesUserAuthInstitution.setUserType(userType);
        List<MesUserAuthInstitution> mesUserAuthInstitutionListByCondition = mesUserAuthInstitutionDao.findMesUserAuthInstitutionListByCondition(mesUserAuthInstitution);
        if (mesUserAuthInstitutionListByCondition.size() > userIds.size()) {
            //删除
            List<String> list1 = new ArrayList<>();
            for (MesUserAuthInstitution userAuthInstitution : mesUserAuthInstitutionListByCondition) {
                boolean contains = CollectionUtil.contains(userIds, userAuthInstitution.getUserId());
                if (!contains) {
                    list1.add(userAuthInstitution.getUserId());
                }
            }
            List<MesInstitution> mesInstitutions;
            if (mesInstitution.getLevel() == 1) {
                MesInstitution mes = new MesInstitution();
                mes.setParentId(mesInstitution.getId());
                mesInstitutions = mesInstitutionDao.findMesInstitutionsByCondition(mes);
            } else {
                MesInstitution mes = new MesInstitution();
                mes.setId(mesInstitution.getParentId());
                mesInstitutions = mesInstitutionDao.findMesInstitutionsByCondition(mes);
            }
            mesInstitutions.add(mesInstitution);
            MesUserAuthInstitution userAuthInstitution = new MesUserAuthInstitution();
            for (String s : list1) {
                userAuthInstitution.setUserId(s);
                for (MesInstitution institution : mesInstitutions) {
                    userAuthInstitution.setInstitutionId(institution.getId());
                    mesUserAuthInstitutionDao.deleteMesUserAuthInstitutionByCondition(userAuthInstitution);
                }
            }
        } else if (mesUserAuthInstitutionListByCondition.size() == userIds.size()) {
            return;
        } else {
            //新增
            for (MesUserAuthInstitution userAuthInstitution : mesUserAuthInstitutionListByCondition) {
                userIds.remove(userAuthInstitution.getUserId());
            }
            List<MesInstitution> mesInstitutions = new ArrayList<>();
            if (mesInstitution.getLevel() == 1) {
                MesInstitution mes = new MesInstitution();
                mes.setParentId(mesInstitution.getId());
                mes.setSchoolId(mesInstitution.getSchoolId());
                mesInstitutions = mesInstitutionDao.findMesInstitutionsByCondition(mes);
            }
            mesInstitutions.add(mesInstitution);
            for (String userId : userIds) {
                for (MesInstitution institution : mesInstitutions) {
                    MesUserAuthInstitution mesUserAuthInstitution1 = new MesUserAuthInstitution();
                    mesUserAuthInstitution1.setId(sequenceId.nextId());
                    mesUserAuthInstitution1.setInstitutionId(institution.getId());
                    mesUserAuthInstitution1.setInstitutionParentId(institution.getParentId());
                    mesUserAuthInstitution1.setUserId(userId);
                    mesUserAuthInstitution1.setUserType(userType);
                    mesUserAuthInstitution1.setLevel(institution.getLevel());
                    mesUserAuthInstitution1.setSchoolId(institution.getSchoolId());
                    mesUserAuthInstitutionDao.saveMesUserAuthInstitution(mesUserAuthInstitution1);
                    if (institution.getLevel() == 2) {
                        MesInstitution mes = new MesInstitution();
                        mes.setParentId(institution.getParentId());
                        List<MesInstitution> list = mesInstitutionDao.findMesInstitutionsByCondition(mes);
                        MesUserAuthInstitution find = new MesUserAuthInstitution();
                        find.setInstitutionParentId(institution.getParentId());
                        find.setUserId(userId);
                        List<MesUserAuthInstitution> findList = mesUserAuthInstitutionDao.findMesUserAuthInstitutionListByCondition(find);
                        if ((findList.size()) == list.size()) {
                            //此用户拥有一级制度下的所有二级制度，自动添加一级制度的权限
                            mesUserAuthInstitution1.setId(sequenceId.nextId());
                            mesUserAuthInstitution1.setInstitutionId(institution.getParentId());
                            mesUserAuthInstitution1.setInstitutionParentId("-1");
                            mesUserAuthInstitution1.setUserId(userId);
                            mesUserAuthInstitution1.setUserType(userType);
                            mesUserAuthInstitution1.setLevel(1);
                            mesUserAuthInstitution1.setSchoolId(institution.getSchoolId());
                            mesUserAuthInstitutionDao.saveMesUserAuthInstitution(mesUserAuthInstitution1);
                        }
                    }
                }
            }
        }
    }

    @Transactional
    public void deleteMesUserAuthInstitution(String id) {
        mesUserAuthInstitutionDao.deleteMesUserAuthInstitution(id);
    }

    @Transactional
    public void deleteMesUserAuthInstitutionByCondition(MesUserAuthInstitution mesUserAuthInstitution) {
        mesUserAuthInstitutionDao.deleteMesUserAuthInstitutionByCondition(mesUserAuthInstitution);
    }

    @Transactional
    public void batchSaveMesUserAuthInstitution(List<MesUserAuthInstitution> mesUserAuthInstitutions) {
        mesUserAuthInstitutions.forEach(mesUserAuthInstitution -> mesUserAuthInstitution.setId(sequenceId.nextId()));
        mesUserAuthInstitutionDao.batchSaveMesUserAuthInstitution(mesUserAuthInstitutions);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public long haveCheckPermission(MesUserAuthInstitution mesUserAuthInstitution) {
        return mesUserAuthInstitutionDao.haveCheckPermission(mesUserAuthInstitution);
    }

}
