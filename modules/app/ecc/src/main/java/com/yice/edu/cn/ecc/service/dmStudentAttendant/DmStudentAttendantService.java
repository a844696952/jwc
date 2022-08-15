package com.yice.edu.cn.ecc.service.dmStudentAttendant;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.dm.dmStudentAttendant.DmStudentAttendant;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.ecc.feignClient.dd.DdFeign;
import com.yice.edu.cn.ecc.feignClient.dmStudentAttendant.DmStudentAttendantFeign;
import com.yice.edu.cn.ecc.feignClient.student.StudentFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DmStudentAttendantService {
    @Autowired
    private DmStudentAttendantFeign dmStudentAttendantFeign;
    @Autowired
    private DdFeign ddFeign;
    @Autowired
    private StudentFeign studentFeign;

    public DmStudentAttendant findDmStudentAttendantById(String id) {
        return dmStudentAttendantFeign.findDmStudentAttendantById(id);
    }

    public DmStudentAttendant saveDmStudentAttendant(DmStudentAttendant dmStudentAttendant) {
        return dmStudentAttendantFeign.saveDmStudentAttendant(dmStudentAttendant);
    }

    public List<DmStudentAttendant> findDmStudentAttendantListByCondition(DmStudentAttendant dmStudentAttendant) {
        List<DmStudentAttendant> list = dmStudentAttendantFeign.findDmStudentAttendantListByCondition(dmStudentAttendant);
        List<Dd> ddList = findAttendantType();
        if(list != null && list.size() >0){
            //将DmStudentAttendant对象中的学生id数组转换成学生对象列表
            list.forEach(item->{
                List<DmStudentAttendant.StudentClazz> studentClazzList = new ArrayList<>();
                String[] studentIds = item.getStudentList();
                List<String> studentIdList = new ArrayList<>();
                List<String> studentNameList = new ArrayList<>();
                if(studentIds != null){
                    List<Student> studentList = studentFeign.findStudentByIds(java.util.Arrays.asList(studentIds));
                    if(studentList != null && studentList.size() >0){
                        studentList.forEach(student ->{
                            if(student != null){
                                DmStudentAttendant.StudentClazz studentClazz = new DmStudentAttendant.StudentClazz(student.getId(),student.getName());
                                studentClazzList.add(studentClazz);
                                studentIdList.add(student.getId());
                                studentNameList.add(student.getName());
                            }
                        });
                    }
                }
//                for (String studentId : studentIdList) {
//                    Student student = studentFeign.findStudentById(studentId);
//                    DmStudentAttendant.StudentClazz studentClazz = new DmStudentAttendant.StudentClazz(studentId,student.getName());
//                    studentClazzList.add(studentClazz);
//                }
                item.setStudentList(studentIdList.toArray(new String[studentIdList.size()]));
                item.setStudentIds(listToString(studentIdList,','));
                item.setStudentNames(listToString(studentNameList,','));
                item.setStuList(studentClazzList);
            });

            List<String> ddIds = list.stream().map(DmStudentAttendant::getDdId).collect(Collectors.toList());
            for (Dd dd : ddList) {
                if(!ddIds.contains(dd.getId())){
                    DmStudentAttendant attendant = copyData(dmStudentAttendant,dd);
                    list.add(attendant);
                }
            }
        }else{
            for (Dd dd : ddList) {
                DmStudentAttendant attendant = copyData(dmStudentAttendant,dd);
                list.add(attendant);
            }
        }

        Collections.sort(list, new Comparator<DmStudentAttendant>(){
            public int compare(DmStudentAttendant o1, DmStudentAttendant o2) {
                //按照CityModel的city_code字段进行降序排列
                if(Integer.valueOf(o1.getDdId()) < Integer.valueOf(o2.getDdId())){
                    return -1;
                }
                if(Integer.valueOf(o1.getDdId()) == Integer.valueOf(o2.getDdId())){
                    return 0;
                }
                return 1;
            }
        });

        return list;
    }

    public DmStudentAttendant findOneDmStudentAttendantByCondition(DmStudentAttendant dmStudentAttendant) {
        dmStudentAttendant.setWeekName(getWeekOfDate(new Date()));
        return dmStudentAttendantFeign.findOneDmStudentAttendantByCondition(dmStudentAttendant);
    }

    public long findDmStudentAttendantCountByCondition(DmStudentAttendant dmStudentAttendant) {
        return dmStudentAttendantFeign.findDmStudentAttendantCountByCondition(dmStudentAttendant);
    }

    public void updateDmStudentAttendant(DmStudentAttendant dmStudentAttendant) {
        dmStudentAttendantFeign.updateDmStudentAttendant(dmStudentAttendant);
    }

    public void updateDmStudentAttendantForNotNull(DmStudentAttendant dmStudentAttendant) {
        dmStudentAttendantFeign.updateDmStudentAttendantForNotNull(dmStudentAttendant);
    }

    public void deleteDmStudentAttendant(String id) {
        dmStudentAttendantFeign.deleteDmStudentAttendant(id);
    }

    public void deleteDmStudentAttendantByCondition(DmStudentAttendant dmStudentAttendant) {
        dmStudentAttendantFeign.deleteDmStudentAttendantByCondition(dmStudentAttendant);
    }

    public List<Dd> findAttendantType(){
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.ATTENTDANTTYPE);
        return ddFeign.findDdListByCondition(dd);
    }

    private DmStudentAttendant copyData(DmStudentAttendant dmStudentAttendant,Dd dd){
        DmStudentAttendant studentAttendant = null;
        try {
            studentAttendant = (DmStudentAttendant) dmStudentAttendant.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        studentAttendant.setDdId(dd.getId());
        studentAttendant.setWeekName(dd.getName());
        return studentAttendant;
    }

    /**
     * 获取当前星期几
     * @param dt
     * @return
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;

        return weekDays[w];
    }

    /**
     *  List转换为字符串并加入分隔符
     * @param list,separator
     * @return
     */
    public String listToString(List list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

}
