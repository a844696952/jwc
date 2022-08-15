package com.yice.edu.cn.jy.service.prepareLessons;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.prepareLessons.TextbookSetting;
import com.yice.edu.cn.jy.dao.prepareLessons.ITextbookSettingDao;
import com.yice.edu.cn.jy.dao.subjectSource.ISubjectMaterialDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 
* @ClassName: TextbookSettingService  
* @Description: 选择教材Service 
* @author xuchang  
* @date 2018年11月6日
 */
@Service
public class TextbookSettingService {
    @Autowired
    private ITextbookSettingDao textbookSettingDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private ISubjectMaterialDao subjectMaterialDao;

    public List<TextbookSetting> findTextbookSettingByTeacherId(String id) {
        return textbookSettingDao.findTextbookSettingByTeacherId(id);
    }
    
    @Transactional
    public int saveTextbookSetting(TextbookSetting textbookSetting) {
    	if(textbookSettingDao.findSettingCountbyTextbookId(textbookSetting.getTextbookId(),textbookSetting.getTeacherId())>0) {
    		return textbookSettingDao.updateTextbookSetting(textbookSetting);
    	}
        textbookSetting.setId(sequenceId.nextId());
        textbookSetting.setSubjectName(subjectMaterialDao.findSubjectMaterialById(textbookSetting.getSubjectMaterialId()).getName());
        int successRow=textbookSettingDao.saveTextbookSetting(textbookSetting);
        return successRow;
    }
    
    @Transactional
    public void deleteTextbookSetting(String id) {
        textbookSettingDao.deleteTextbookSetting(id);
    }
    
    public TextbookSetting findLastSetting(String teacherId) {
    	TextbookSetting entity=textbookSettingDao.findLastSettingbyTeacherId(teacherId);
    	return entity;
    }
    

}
