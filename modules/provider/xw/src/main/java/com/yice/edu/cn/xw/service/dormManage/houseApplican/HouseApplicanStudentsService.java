package com.yice.edu.cn.xw.service.dormManage.houseApplican;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildingPersonInfo;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.HouseApplicanStudents;
import com.yice.edu.cn.xw.dao.dormManage.dorm.IDormPersonDao;
import com.yice.edu.cn.xw.dao.dormManage.houseApplican.IHouseApplicanStudentsDao;
import com.yice.edu.cn.xw.feignClient.jw.student.StudentFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.List;


@Service
public class HouseApplicanStudentsService {
    @Autowired
    private IHouseApplicanStudentsDao houseApplicanStudentsDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private StudentFeign studentFeign;
    @Autowired
    private IDormPersonDao dormPersonDao;

    @Transactional(readOnly = true)
    public HouseApplicanStudents findHouseApplicanStudentsById(String id) {
        return houseApplicanStudentsDao.findHouseApplicanStudentsById(id);
    }
    @Transactional
    public void saveHouseApplicanStudents(HouseApplicanStudents houseApplicanStudents) {
        houseApplicanStudents.setId(sequenceId.nextId());
        houseApplicanStudentsDao.saveHouseApplicanStudents(houseApplicanStudents);
    }
    @Transactional(readOnly = true)
    public List<HouseApplicanStudents> findHouseApplicanStudentsListByCondition(HouseApplicanStudents houseApplicanStudents) {
        return houseApplicanStudentsDao.findHouseApplicanStudentsListByCondition(houseApplicanStudents);
    }
    @Transactional(readOnly = true)
    public HouseApplicanStudents findOneHouseApplicanStudentsByCondition(HouseApplicanStudents houseApplicanStudents) {
        return houseApplicanStudentsDao.findOneHouseApplicanStudentsByCondition(houseApplicanStudents);
    }
    @Transactional(readOnly = true)
    public long findHouseApplicanStudentsCountByCondition(HouseApplicanStudents houseApplicanStudents) {
        return houseApplicanStudentsDao.findHouseApplicanStudentsCountByCondition(houseApplicanStudents);
    }
    @Transactional
    public void updateHouseApplicanStudents(HouseApplicanStudents houseApplicanStudents) {
        houseApplicanStudentsDao.updateHouseApplicanStudents1(houseApplicanStudents);
    }
    @Transactional
    public void deleteHouseApplicanStudents(String id) {
        houseApplicanStudentsDao.deleteHouseApplicanStudents(id);
    }
    @Transactional
    public void deleteHouseApplicanStudentsByCondition(HouseApplicanStudents houseApplicanStudents) {
        houseApplicanStudentsDao.deleteHouseApplicanStudentsByCondition(houseApplicanStudents);
    }
    @Transactional
    public void batchSaveHouseApplicanStudents(List<HouseApplicanStudents> houseApplicanStudentss){
        houseApplicanStudentss.forEach(houseApplicanStudents -> houseApplicanStudents.setId(sequenceId.nextId()));
        houseApplicanStudentsDao.batchSaveHouseApplicanStudents(houseApplicanStudentss);
    }

    @Transactional
    public List<HouseApplicanStudents> findHouseApplicanStudents(HouseApplicanStudents houseApplicanStudents) {
        return houseApplicanStudentsDao.findHouseApplicanStudents(houseApplicanStudents);
    }
    @Transactional
    public long findHouseApplicanStudentsCount(HouseApplicanStudents houseApplicanStudents) {
        return houseApplicanStudentsDao.findHouseApplicanStudentsCount(houseApplicanStudents);
    }

    @Transactional
    public HouseApplicanStudents lookHouseApplicanStudentsByhouseApplicanId(String houseApplicanId) {
        List<String> studentIdsList = houseApplicanStudentsDao.findStudentIdByhouseApplicanId(houseApplicanId)
                .stream().map(HouseApplicanStudents::getStudentId).collect(Collectors.toList());

        HouseApplicanStudents houseApplicanStudents = new HouseApplicanStudents();
        DormBuildingPersonInfo d = new DormBuildingPersonInfo();
        //根据学生id  返回已经入住 信息
        List<DormBuildingPersonInfo> list1 = new ArrayList<>();
        for (String s : studentIdsList){
            d.setPersonId(s);
            List<DormBuildingPersonInfo> student = dormPersonDao.findDormPersonInfoWithStudent(d);
            if (student!=null && student.size() > 0){
                list1.add(student.get(0));
            }
        }
        houseApplicanStudents.setDormBuildingPersonInfoList(list1);
        //住宿安排中的学生
        List list2 = new ArrayList();
        for (String s : studentIdsList){
            Student studentById = studentFeign.findStudentById(s);
            list2.add(studentById);
        }
        houseApplicanStudents.setStudentList(list2);

       /* HouseApplicanStudents houseApplicanStudents1 = new HouseApplicanStudents();
        for (int i = 0 ; i < houseApplicanStudents.getStudentList().size();i++){
            for (int j = 0 ; j < houseApplicanStudents.getDormBuildingPersonInfoList().size() ; j++){
                if (houseApplicanStudents.getStudentList().get(i).getId().equals(houseApplicanStudents.getDormBuildingPersonInfoList().get(j).getPersonId())){

                }else{

                }
            }
        }*/
        houseApplicanStudents.setHouseApplicanId(houseApplicanId);
        return houseApplicanStudents;
    }

    @Transactional
    public void saveHouseApplicanStudents1(HouseApplicanStudents houseApplicanStudents) {
        HouseApplicanStudents houseApplicanStudents1 = new HouseApplicanStudents();
        List<HouseApplicanStudents> list = houseApplicanStudents.getHouseApplicanStudentsList();
       for (HouseApplicanStudents s : list){
           houseApplicanStudents1.setHouseApplicanId(s.getHouseApplicanId());
           houseApplicanStudents1.setStudentId(s.getStudentId());
           houseApplicanStudents1.setType("2");
           houseApplicanStudents1.setDormitoryId(s.getDormitoryId());
           houseApplicanStudents1.setDormitoryName(s.getDormitoryName());
           houseApplicanStudents1.setFloorId(s.getFloorId());
           houseApplicanStudents1.setFloorName(s.getFloorName());
           houseApplicanStudents1.setBedRoomId(s.getBedRoomId());
           houseApplicanStudents1.setBedRoomName(s.getBedRoomName());
           houseApplicanStudents1.setBedCode(s.getBedCode());
           houseApplicanStudentsDao.saveHouseApplicanStudents1(houseApplicanStudents1);
       }

    }

    public List<HouseApplicanStudents> lookHouseApplicanStudents(HouseApplicanStudents houseApplicanStudents) {
        HouseApplicanStudents h = new HouseApplicanStudents();
        List<HouseApplicanStudents> list = houseApplicanStudentsDao.findStudentIdByhouseApplicanId1(houseApplicanStudents);
        List<String> collect = list.stream().map(HouseApplicanStudents::getStudentId).collect(Collectors.toList());
        for (int i = 0 ; i<list.size() ; i++){
            Student studentById = studentFeign.findStudentById(collect.get(i));
            list.get(i).setName(studentById.getName());
            list.get(i).setStudentNo(studentById.getStudentNo());
            list.get(i).setSex(studentById.getSex());
            list.get(i).setGradeName(studentById.getGradeName());
            list.get(i).setClassesNumber(studentById.getClassesNumber());
        }
        return list;
    }

    public long lookHouseApplicanStudentsCount(String houseApplicanId) {
        return houseApplicanStudentsDao.lookHouseApplicanStudentsCount(houseApplicanId);
    }
}
