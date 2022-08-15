package com.yice.edu.cn.tap.service.dy.schoolManage;

import cn.hutool.core.collection.CollectionUtil;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherPost;
import com.yice.edu.cn.common.pojo.mes.schoolManage.inspectRecord.MesInspectRecord;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesInstitution;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesUserAuthInstitution;
import com.yice.edu.cn.common.pojo.mes.schoolManage.query.MirQuery;
import com.yice.edu.cn.tap.feignClient.dy.schoolManage.MesInspectRecordFeign;
import com.yice.edu.cn.tap.feignClient.teacher.TeacherPostFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MesInspectRecordService {
    @Autowired
    private MesInspectRecordFeign mesInspectRecordFeign;


    @Autowired
    private TeacherPostFeign teacherPostFeign;


    public MesInspectRecord findMesInspectRecordById(String id) {
        return mesInspectRecordFeign.findMesInspectRecordById(id);
    }

    public Boolean saveMesInspectRecord(MesInspectRecord mesInspectRecord) {
        return mesInspectRecordFeign.saveMesInspectRecord(mesInspectRecord);
    }

    public List<MesInspectRecord> findMesInspectRecordListByCondition(MesInspectRecord mesInspectRecord) {
        return mesInspectRecordFeign.findMesInspectRecordListByCondition(mesInspectRecord);
    }

    public MesInspectRecord findOneMesInspectRecordByCondition(MesInspectRecord mesInspectRecord) {
        return mesInspectRecordFeign.findOneMesInspectRecordByCondition(mesInspectRecord);
    }

    public long findMesInspectRecordCountByCondition(MesInspectRecord mesInspectRecord) {
        return mesInspectRecordFeign.findMesInspectRecordCountByCondition(mesInspectRecord);
    }

    public void updateMesInspectRecord(MesInspectRecord mesInspectRecord) {
        mesInspectRecordFeign.updateMesInspectRecord(mesInspectRecord);
    }

    public void deleteMesInspectRecord(String id) {
        mesInspectRecordFeign.deleteMesInspectRecord(id);
    }

    public void deleteMesInspectRecordByCondition(MesInspectRecord mesInspectRecord) {
        mesInspectRecordFeign.deleteMesInspectRecordByCondition(mesInspectRecord);
    }

    public ResponseJson findMirAndClassId(MirQuery mirQuery, Teacher teacher) {

        TeacherPost teacherPost = new TeacherPost();
        teacherPost.setTeacherId(teacher.getId());
        teacherPost.setSort(5);
        List<TeacherPost> condition = teacherPostFeign.findTeacherPostListByCondition(teacherPost);
        if(CollectionUtil.isEmpty(condition)){
            return new ResponseJson(new ArrayList<>());
        }
        mirQuery.setTeacherPosts(condition);
        List<MesInspectRecord> records = mesInspectRecordFeign.findMirAndClassId(mirQuery);
        long count = mesInspectRecordFeign.findMirAndClassIdCount(mirQuery);
        return new ResponseJson(records,count);
    }

    public List<MesInstitution> findMesInstitutionOlList(String userId) {
        return mesInspectRecordFeign.findMesInstitutionOlList(userId);
    }

    public List<MesInstitution> findMesInstitutionTlListByParentId(MesUserAuthInstitution mesUserAuthInstitution) {
        return mesInspectRecordFeign.findMesInstitutionTlListByParentId(mesUserAuthInstitution);
    }

    public MesInspectRecord findReference(String id) {
        return mesInspectRecordFeign.findReference(id);
    }

}
