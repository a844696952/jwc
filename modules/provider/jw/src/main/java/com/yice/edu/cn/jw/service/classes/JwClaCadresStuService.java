package com.yice.edu.cn.jw.service.classes;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jw.classes.JwClaCadresStu;
import com.yice.edu.cn.jw.dao.classes.IJwClaCadresStuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JwClaCadresStuService {
    @Autowired
    private IJwClaCadresStuDao jwClaCadresStuDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public JwClaCadresStu findJwClaCadresStuById(String id) {
        return jwClaCadresStuDao.findJwClaCadresStuById(id);
    }
    @Transactional
    public void saveJwClaCadresStu(JwClaCadresStu jwClaCadresStu) {
        jwClaCadresStu.setId(sequenceId.nextId());
        jwClaCadresStuDao.saveJwClaCadresStu(jwClaCadresStu);
    }
    @Transactional(readOnly = true)
    public List<JwClaCadresStu> findJwClaCadresStuListByCondition(JwClaCadresStu jwClaCadresStu) {
        return jwClaCadresStuDao.findJwClaCadresStuListByCondition(jwClaCadresStu);
    }
    @Transactional(readOnly = true)
    public long findJwClaCadresStuCountByCondition(JwClaCadresStu jwClaCadresStu) {
        return jwClaCadresStuDao.findJwClaCadresStuCountByCondition(jwClaCadresStu);
    }
    @Transactional
    public void updateJwClaCadresStu(JwClaCadresStu jwClaCadresStu) {
        jwClaCadresStuDao.updateJwClaCadresStu(jwClaCadresStu);
    }
    @Transactional
    public void deleteJwClaCadresStu(String id) {
        jwClaCadresStuDao.deleteJwClaCadresStu(id);
    }
    @Transactional
    public void deleteJwClaCadresStuByCondition(JwClaCadresStu jwClaCadresStu) {
        jwClaCadresStuDao.deleteJwClaCadresStuByCondition(jwClaCadresStu);
    }
    
    @Transactional
    public void updateStudentCadres(Map<String,String> map) {
    	
    	String claCadresId = map.get("claCadresId");
    	String claCadresName = map.get("claCadresName");
    	String classesId = map.get("classesId");
    	
    	JwClaCadresStu jwClaCadresStu = new JwClaCadresStu();
    	jwClaCadresStu.setClaCadresId(claCadresId);
    	jwClaCadresStuDao.deleteJwClaCadresStuByCondition(jwClaCadresStu);
    	
    	String studentIdsStr = map.get("studentIds");
    	String studentNameStr = map.get("studentNames");
    	if(studentIdsStr==null || studentNameStr==null) {
    		return;
    	}
    	
    	List<JwClaCadresStu> JwClaCadresStuList = new ArrayList<JwClaCadresStu>();
    	String[] studentsId= studentIdsStr.split(",");
    	String[] studentsName= studentNameStr.split(",");
    	String createTime = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
            for (int count = 0; count<studentsId.length;count++) {
            	JwClaCadresStu model = new JwClaCadresStu();
            	model.setId(sequenceId.nextId());
            	model.setCreateTime(createTime);
            	model.setSchoolId(map.get("schoolId"));
            	model.setDel(Constant.DELSIGN.NORMAL);
            	model.setClaCadresId(claCadresId);
            	model.setStudentId(studentsId[count]);
            	model.setStudentName(studentsName[count]);
            	model.setClaCadresName(claCadresName);
            	model.setClassesId(classesId);
            	
                JwClaCadresStuList.add(model);
            }
            jwClaCadresStuDao.batchSaveJwClaCadresStu(JwClaCadresStuList);
    }
    
    @Transactional(readOnly = true)
    public List<JwClaCadresStu> findJwClaCadresStuInfoListByClassesId(JwClaCadresStu jwClaCadresStu) {
        return jwClaCadresStuDao.findJwClaCadresStuInfoListByClassesId(jwClaCadresStu);
    }
    
    @Transactional(readOnly = true)
    public List<JwClaCadresStu> findStuAndCadresByClassesIdAndName(JwClaCadresStu jwClaCadresStu) {
        return jwClaCadresStuDao.findStuAndCadresByClassesIdAndName(jwClaCadresStu);
    }

    public long checkStudentIdentity(String studentId) {
        return jwClaCadresStuDao.checkStudentIdentity(studentId);
    }
}
