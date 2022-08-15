package com.yice.edu.cn.dm.service.dmClassMeeting;

import cn.hutool.core.collection.CollUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.dm.dmClassMeeting.DmClassMeeting;
import com.yice.edu.cn.common.pojo.dm.schoolActive.DmAttachmentFile;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.dm.dao.dmClassMeeting.IDmClassMeetingDao;
import com.yice.edu.cn.dm.dao.schoolActive.IDmAttachmentFileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DmClassMeetingService {
    @Autowired
    private IDmClassMeetingDao dmClassMeetingDao;
    @Autowired
    private IDmAttachmentFileDao dmAttachmentFileDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public DmClassMeeting findDmClassMeetingById(String id) {
        DmClassMeeting classMeeting = dmClassMeetingDao.findDmClassMeetingById(id);
        if(classMeeting != null){
            DmAttachmentFile dmAttachmentFile = new DmAttachmentFile();
            dmAttachmentFile.setReferenceId(id);
            List<DmAttachmentFile> fileList = dmAttachmentFileDao.findDmAttachmentFileListByCondition(dmAttachmentFile);
            classMeeting.setFileList(fileList);
        }
        return classMeeting;
    }
    @Transactional
    public Boolean saveDmClassMeeting(DmClassMeeting dmClassMeeting) {
        dmClassMeeting.setId(sequenceId.nextId());
        if(CollUtil.isNotEmpty(dmClassMeeting.getFileList())){
            dmClassMeeting.getFileList().forEach(f -> {
                f.setId(sequenceId.nextId());
                f.setSchoolId(dmClassMeeting.getSchoolId());
                f.setFileType(2);
                f.setReferenceId(dmClassMeeting.getId());
                dmAttachmentFileDao.saveDmAttachmentFile(f);
            });
        }
        dmClassMeetingDao.saveDmClassMeeting(dmClassMeeting);
        return true;
    }
    @Transactional(readOnly = true)
    public List<DmClassMeeting> findDmClassMeetingListByCondition(DmClassMeeting dmClassMeeting) {
        /*if(dmClassMeeting.getClassMeetingTime() != null){
            dmClassMeeting.setBeginTime(dmClassMeeting.getClassMeetingTime() + " 00:00:00");
            dmClassMeeting.setEndTime(dmClassMeeting.getClassMeetingTime() + " 23:59:59");
        }*/
        return dmClassMeetingDao.findDmClassMeetingListByCondition(dmClassMeeting);
    }
    @Transactional(readOnly = true)
    public DmClassMeeting findOneDmClassMeetingByCondition(DmClassMeeting dmClassMeeting) {
        return dmClassMeetingDao.findOneDmClassMeetingByCondition(dmClassMeeting);
    }
    @Transactional(readOnly = true)
    public long findDmClassMeetingCountByCondition(DmClassMeeting dmClassMeeting) {
        return dmClassMeetingDao.findDmClassMeetingCountByCondition(dmClassMeeting);
    }
    @Transactional
    public void updateDmClassMeeting(DmClassMeeting dmClassMeeting) {
        //先查出改班会所带的所有图片
        DmAttachmentFile file = new DmAttachmentFile();
        file.setReferenceId(dmClassMeeting.getId());
        List<DmAttachmentFile> oldfileList = dmAttachmentFileDao.findDmAttachmentFileListByCondition(file);
        List<DmAttachmentFile> newfileList = dmClassMeeting.getFileList();
        List<DmAttachmentFile> fileList1 = intersection(oldfileList,newfileList);
        List<DmAttachmentFile> fileList2 = intersection(newfileList,oldfileList);
        if(CollUtil.isEmpty(fileList1)){
            newfileList.forEach(f -> {
                f.setId(sequenceId.nextId());
                f.setFileType(2);
                f.setReferenceId(dmClassMeeting.getId());
                f.setSchoolId(dmClassMeeting.getSchoolId());
            });
            if(CollUtil.isNotEmpty(oldfileList)){
                oldfileList.forEach(o -> {
                    dmAttachmentFileDao.deleteDmAttachmentFile(o.getId());
                });
            }
            dmAttachmentFileDao.batchSaveDmAttachmentFile(newfileList);
        }else {
            fileList1.forEach(oldfileList::remove);
            if(CollUtil.isNotEmpty(oldfileList)){
                oldfileList.forEach(o -> {
                    dmAttachmentFileDao.deleteDmAttachmentFile(o.getId());
                });
            }
            fileList2.forEach(newfileList::remove);
            if(CollUtil.isNotEmpty(newfileList)){
                newfileList.forEach(f -> {
                    f.setId(sequenceId.nextId());
                    f.setFileType(2);
                    f.setReferenceId(dmClassMeeting.getId());
                    f.setSchoolId(dmClassMeeting.getSchoolId());
                });
                dmAttachmentFileDao.batchSaveDmAttachmentFile(newfileList);
            }
        }
        dmClassMeetingDao.updateDmClassMeeting(dmClassMeeting);
    }

    private List<DmAttachmentFile> intersection(List<DmAttachmentFile> first, List<DmAttachmentFile> second){
        return first.stream().filter(second::contains).collect(Collectors.toList());
    }

    @Transactional
    public void deleteDmClassMeeting(String id) {
        //删除与该班会相关的图片
        DmAttachmentFile dmAttachmentFile = new DmAttachmentFile();
        dmAttachmentFile.setReferenceId(id);
        //查询该班会对应的所有图片
        List<DmAttachmentFile> list = dmAttachmentFileDao.findDmAttachmentFileListByCondition(dmAttachmentFile);
        if(CollUtil.isNotEmpty(list)){
            //删除数据中的图片路径
            dmAttachmentFileDao.deleteDmAttachmentFileByCondition(dmAttachmentFile);
            //删除七牛云之中的存储
            list.forEach(f -> {
                QiniuUtil.deleteFile(f.getFilePath().substring(1));
            });
        }
        dmClassMeetingDao.deleteDmClassMeeting(id);
    }
    @Transactional
    public void deleteDmClassMeetingByCondition(DmClassMeeting dmClassMeeting) {
        dmClassMeetingDao.deleteDmClassMeetingByCondition(dmClassMeeting);
    }
    @Transactional
    public void batchSaveDmClassMeeting(List<DmClassMeeting> dmClassMeetings){
        dmClassMeetings.forEach(dmClassMeeting -> dmClassMeeting.setId(sequenceId.nextId()));
        dmClassMeetingDao.batchSaveDmClassMeeting(dmClassMeetings);
    }

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public List<DmAttachmentFile> findDmClassMeetingImgsByclassId(String classId) {
        return dmClassMeetingDao.findDmClassMeetingImgsByclassId(classId);
    }
}
