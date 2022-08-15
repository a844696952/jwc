package com.yice.edu.cn.xw.service.dj;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.xw.dj.DjClassify;
import com.yice.edu.cn.common.pojo.xw.dj.XwDjStudyResource;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberFile.XwDjAttachmentFile;
import com.yice.edu.cn.xw.dao.dj.IDjClassifyDao;
import com.yice.edu.cn.xw.dao.dj.IXwDjStudyResourceDao;
import com.yice.edu.cn.xw.dao.dj.partyMemberFile.IXwDjAttachmentFileDao;
import com.yice.edu.cn.xw.service.dj.partyMemberFile.XwDjAttachmentFileService;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class XwDjStudyResourceService {
    @Autowired
    private IXwDjStudyResourceDao xwDjStudyResourceDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private IXwDjAttachmentFileDao xwDjAttachmentFileDao;
    @Autowired
    private IDjClassifyDao iDjClassifyDao;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public XwDjStudyResource findXwDjStudyResourceById(String id) {
        XwDjStudyResource xwDjStudyResource = xwDjStudyResourceDao.findXwDjStudyResourceById(id);

        xwDjStudyResource.setTypeName(iDjClassifyDao.findDjClassifyById(xwDjStudyResource.getType()).getClassifyName());

        XwDjAttachmentFile file = new XwDjAttachmentFile();
        file.setReferenceId(id);
        List<XwDjAttachmentFile> fileList = xwDjAttachmentFileDao.findXwDjAttachmentFileListByCondition(file);
        if (fileList.isEmpty()) {
            return xwDjStudyResource;
        } else {
            xwDjStudyResource.setFileList(fileList);
            return xwDjStudyResource;
        }


    }

    @Transactional
    public void saveXwDjStudyResource(XwDjStudyResource xwDjStudyResource) {
        //存储学习资源
        String xwDjStudyResourceId = sequenceId.nextId();
        xwDjStudyResource.setId(xwDjStudyResourceId);
        xwDjStudyResource.setStudyNumber(0);
        xwDjStudyResource.setTeacherNumber(0);
        xwDjStudyResource.setActivityType(Constant.DJ_STUDY_TYPE.TYPE);

        if (xwDjStudyResource.getState() == Constant.STUDY_RESOURCE.STUDY_ISSUE) {
            xwDjStudyResourceDao.saveXwDjStudyResource(xwDjStudyResource);
        } else if (xwDjStudyResource.getState() == Constant.STUDY_RESOURCE.STUDY_NO_ISSUE) {
            xwDjStudyResourceDao.saveXwDjStudyResourceDraft(xwDjStudyResource);
        } else {
            return;
        }
        //存储学习资源相关文件
        List<XwDjAttachmentFile> fileList = xwDjStudyResource.getFileList();
        if (!fileList.isEmpty()) {
            for (XwDjAttachmentFile file : fileList) {
                file.setId(sequenceId.nextId());
                file.setReferenceId(xwDjStudyResourceId);
                xwDjAttachmentFileDao.saveXwDjAttachmentFile(file);
            }
        }
    }

    @Transactional(readOnly = true)
    public List<XwDjStudyResource> findXwDjStudyResourceListByCondition(XwDjStudyResource xwDjStudyResource) {
        List<XwDjStudyResource> xw = xwDjStudyResourceDao.findXwDjStudyResourceListByCondition(xwDjStudyResource);
        if (!xw.isEmpty()) {
            xw.stream().forEach(skt -> {
                DjClassify djClassifyById = iDjClassifyDao.findDjClassifyById(skt.getType());
                skt.setTypeName(djClassifyById.getClassifyName());
            });
        }
        return xw;
    }

    @Transactional(readOnly = true)
    public XwDjStudyResource findOneXwDjStudyResourceByCondition(XwDjStudyResource xwDjStudyResource) {
        return xwDjStudyResourceDao.findOneXwDjStudyResourceByCondition(xwDjStudyResource);
    }

    @Transactional(readOnly = true)
    public long findXwDjStudyResourceCountByCondition(XwDjStudyResource xwDjStudyResource) {
        return xwDjStudyResourceDao.findXwDjStudyResourceCountByCondition(xwDjStudyResource);
    }

    @Transactional
    public void updateXwDjStudyResource(XwDjStudyResource xwDjStudyResource) {
        if (xwDjStudyResource.getState() == Constant.STUDY_RESOURCE.STUDY_CLOSE) {
            //关闭
            xwDjStudyResourceDao.updateXwDjStudyResource(xwDjStudyResource);
        } else if (xwDjStudyResource.getState() == Constant.STUDY_RESOURCE.STUDY_ISSUE) {
            List<XwDjAttachmentFile> allList;
            //修改发布
            //更新学习资源
            xwDjStudyResourceDao.updateXwDjStudyResource(xwDjStudyResource);
            //更新学习资源文件
            List<XwDjAttachmentFile> fileList = xwDjStudyResource.getFileList();
            //查询已有的学习资源
            XwDjAttachmentFile xwDjAttachmentFile = new XwDjAttachmentFile();
            xwDjAttachmentFile.setReferenceId(xwDjStudyResource.getId());
            allList = xwDjAttachmentFileDao.findXwDjAttachmentFileListByCondition(xwDjAttachmentFile);

            if (!fileList.isEmpty()) {
                //删除
                allList.stream()
                        .map(itme -> itme.getId())
                        .filter(itme -> !fileList.stream()
                                .map(skt -> skt.getId())
                                .collect(Collectors.toList())
                                .contains(itme))
                        .forEach(skr -> xwDjAttachmentFileDao.deleteXwDjAttachmentFile(skr));
                //更新
//                fileList.stream().forEach(item -> xwDjAttachmentFileDao.updateXwDjAttachmentFile(item));
                //插入
                fileList.stream().filter(itme -> itme.getId() == null).forEach(itme -> {
                    itme.setId(sequenceId.nextId());
                    itme.setReferenceId(xwDjStudyResource.getId());
                    xwDjAttachmentFileDao.saveXwDjAttachmentFile(itme);
                });
            } else {
                //删除全部
                allList.stream().map(skt -> skt.getId()).forEach(id -> xwDjAttachmentFileDao.deleteXwDjAttachmentFile(id));
            }

        } else if (xwDjStudyResource.getState() == Constant.STUDY_RESOURCE.STUDY_NO_ISSUE) {
            List<XwDjAttachmentFile> atList;
            //修改存草稿
            //更新学习资源
            xwDjStudyResourceDao.updateXwDjStudyResourceDraft(xwDjStudyResource);
            //更新学习资源文件
            List<XwDjAttachmentFile> fileList = xwDjStudyResource.getFileList();
            //查询已有的学习资源
            XwDjAttachmentFile xwDjAttachmentFile = new XwDjAttachmentFile();
            xwDjAttachmentFile.setReferenceId(xwDjStudyResource.getId());
            atList = xwDjAttachmentFileDao.findXwDjAttachmentFileListByCondition(xwDjAttachmentFile);

            if (!fileList.isEmpty()) {
                //删除
                atList.stream()
                        .map(itme -> itme.getId())
                        .filter(itme -> !fileList.stream()
                                .map(skt -> skt.getId())
                                .collect(Collectors.toList())
                                .contains(itme))
                        .forEach(skr -> xwDjAttachmentFileDao.deleteXwDjAttachmentFile(skr));
                //更新
//                fileList.stream().forEach(item -> xwDjAttachmentFileDao.updateXwDjAttachmentFile(item));
                //插入
                fileList.stream().filter(itme -> itme.getId() == null).forEach(itme -> {
                    itme.setId(sequenceId.nextId());
                    itme.setReferenceId(xwDjStudyResource.getId());
                    xwDjAttachmentFileDao.saveXwDjAttachmentFile(itme);
                });
            } else {
                //删除全部
                atList.stream().map(skt -> skt.getId()).forEach(id -> xwDjAttachmentFileDao.deleteXwDjAttachmentFile(id));
            }
        }

    }

    @Transactional
    public void deleteXwDjStudyResource(String id) {
        //删除学习资源
        xwDjStudyResourceDao.deleteXwDjStudyResource(id);
        //删除学习资源附件
        XwDjAttachmentFile xwDjAttachmentFile = new XwDjAttachmentFile();
        xwDjAttachmentFile.setReferenceId(id);
        xwDjAttachmentFileDao.deleteXwDjAttachmentFileByCondition(xwDjAttachmentFile);
    }

    @Transactional
    public void deleteXwDjStudyResourceByCondition(XwDjStudyResource xwDjStudyResource) {
        xwDjStudyResourceDao.deleteXwDjStudyResourceByCondition(xwDjStudyResource);
    }

    @Transactional
    public void batchSaveXwDjStudyResource(List<XwDjStudyResource> xwDjStudyResources) {
        xwDjStudyResources.forEach(xwDjStudyResource -> xwDjStudyResource.setId(sequenceId.nextId()));
        xwDjStudyResourceDao.batchSaveXwDjStudyResource(xwDjStudyResources);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
