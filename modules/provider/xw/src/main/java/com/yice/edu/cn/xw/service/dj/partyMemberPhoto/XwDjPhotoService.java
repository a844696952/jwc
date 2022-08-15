package com.yice.edu.cn.xw.service.dj.partyMemberPhoto;

import cn.hutool.core.collection.CollectionUtil;
import com.yice.edu.cn.common.constants.CommonClassConstants;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberFile.XwDjAttachmentFile;
import com.yice.edu.cn.common.pojo.xw.dj.partyMerberPhoto.XwDjPhoto;
import com.yice.edu.cn.xw.dao.dj.partyMemberFile.IXwDjAttachmentFileDao;
import com.yice.edu.cn.common.util.jmessage.utils.StringUtils;
import com.yice.edu.cn.xw.dao.dj.partyMemberPhoto.IXwDjPhotoDao;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.applet.Main;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Service
public class XwDjPhotoService {
    @Autowired
    private IXwDjPhotoDao xwDjPhotoDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private IXwDjAttachmentFileDao xwDjAttachmentFileDao;

    @Transactional(readOnly = true)
    public XwDjPhoto findXwDjPhotoById(String id) {
        return xwDjPhotoDao.findXwDjPhotoByIdLeft(id);
    }
    @Transactional(rollbackFor = Exception.class)
    public void saveXwDjPhoto(XwDjPhoto xwDjPhoto) {
        xwDjPhoto.setId(sequenceId.nextId());
        xwDjPhotoDao.saveXwDjPhoto(xwDjPhoto);
        //保存相册相关的资源表
        if(CollectionUtil.isNotEmpty(xwDjPhoto.getFiles())){
            xwDjPhoto.getFiles().forEach(xwDjAttachmentFile -> {
                xwDjAttachmentFile.setId(sequenceId.nextId());
                xwDjAttachmentFile.setReferenceId(xwDjPhoto.getId());
            });
            xwDjAttachmentFileDao.batchSaveXwDjAttachmentFile(xwDjPhoto.getFiles());
        }

    }
    @Transactional(readOnly = true)
    public List<XwDjPhoto> findXwDjPhotoListByCondition(XwDjPhoto xwDjPhoto) {
        return xwDjPhotoDao.findXwDjPhotoListByCondition(xwDjPhoto);
    }
    @Transactional(readOnly = true)
    public XwDjPhoto findOneXwDjPhotoByCondition(XwDjPhoto xwDjPhoto) {
        return xwDjPhotoDao.findOneXwDjPhotoByCondition(xwDjPhoto);
    }
    @Transactional(readOnly = true)
    public long findXwDjPhotoCountByCondition(XwDjPhoto xwDjPhoto) {
        return xwDjPhotoDao.findXwDjPhotoCountByCondition(xwDjPhoto);
    }
    @Transactional(rollbackFor = Exception.class)
    public void updateXwDjPhoto(XwDjPhoto xwDjPhoto) {
        if(xwDjPhoto.getPhotoState() != Constant.STUDY_RESOURCE.STUDY_CLOSE){
            //以前只要关闭才有操作人，后来需求改动 发布也要操作人
            // xwDjPhoto.setOperatorId(null);
            List<XwDjAttachmentFile> fileList = xwDjPhoto.getFiles();
            XwDjAttachmentFile xwDjAttachmentFile = new XwDjAttachmentFile();
            xwDjAttachmentFile.setReferenceId(xwDjPhoto.getId());
            xwDjAttachmentFileDao.deleteXwDjAttachmentFileByCondition(xwDjAttachmentFile);
            if(CollectionUtil.isNotEmpty(fileList)){
                //更新资源
                fileList.forEach(file -> {
                    file.setId(sequenceId.nextId());
                    file.setReferenceId(xwDjPhoto.getId());
                });
                xwDjAttachmentFileDao.batchSaveXwDjAttachmentFile(fileList);
            }
        }
        xwDjPhotoDao.updateXwDjPhoto(xwDjPhoto);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteXwDjPhoto(String id) {
        xwDjPhotoDao.deleteXwDjPhoto(id);
        XwDjAttachmentFile xwDjAttachmentFile = new XwDjAttachmentFile();
        xwDjAttachmentFile.setReferenceId(id);
        xwDjAttachmentFileDao.deleteXwDjAttachmentFileByCondition(xwDjAttachmentFile);
    }
    @Transactional
    public void deleteXwDjPhotoByCondition(XwDjPhoto xwDjPhoto) {
        xwDjPhotoDao.deleteXwDjPhotoByCondition(xwDjPhoto);
    }
    @Transactional
    public void batchSaveXwDjPhoto(List<XwDjPhoto> xwDjPhotos){
        xwDjPhotos.forEach(xwDjPhoto -> xwDjPhoto.setId(sequenceId.nextId()));
        xwDjPhotoDao.batchSaveXwDjPhoto(xwDjPhotos);
    }




}
