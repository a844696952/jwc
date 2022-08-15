package com.yice.edu.cn.xw.service.cms;

import cn.hutool.core.collection.CollectionUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsContent;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsContentFile;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsLayoutCondition;
import com.yice.edu.cn.common.util.date.DateUtils;
import com.yice.edu.cn.xw.dao.cms.IXwCmsContentDao;
import com.yice.edu.cn.xw.dao.cms.IXwCmsContentFileDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Service
public class XwCmsContentService {
    @Autowired
    private IXwCmsContentDao xwCmsContentDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private XwCmsContentFileService fileService;
    @Autowired
    private IXwCmsContentFileDao xwCmsContentFileDao;
    @Autowired
    private XwCmsHomeLayoutService xwCmsHomeLayoutService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
@Transactional(readOnly = true, rollbackFor = Exception.class)
    public XwCmsContent findXwCmsContentById(String id) {
    XwCmsContent detail = xwCmsContentDao.findXwCmsContentById(id);
    if (detail != null) {
        XwCmsContentFile fileModel = new XwCmsContentFile();
        fileModel.setReferenceId(detail.getId());
        List<XwCmsContentFile> list = fileService.findXwCmsContentFileListByCondition(fileModel);
        detail.setFiles(list);
    }
    return detail;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveXwCmsContent(XwCmsContent xwCmsContent) {
        xwCmsContent.setCreateTime(DateUtils.Nowss());
        xwCmsContent.setId(sequenceId.nextId());
        xwCmsContent.setUpdateDate(DateUtils.Nowss());
        if(!CollectionUtils.isEmpty(xwCmsContent.getFiles())){
            xwCmsContent.getFiles().stream().forEach(f->{
                f.setId(sequenceId.nextId());
                f.setReferenceId(xwCmsContent.getId());
                f.setSchoolId(xwCmsContent.getSchoolId());
                f.setCreateTime(DateUtils.Nowss());
            });
            xwCmsContentFileDao.batchSaveXwCmsContentFile(xwCmsContent.getFiles());
        }
        xwCmsContentDao.saveXwCmsContent(xwCmsContent);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<XwCmsContent> findXwCmsContentListByCondition(XwCmsContent xwCmsContent) {
        List<XwCmsContent> list = xwCmsContentDao.findXwCmsContentListByCondition(xwCmsContent);
        if (CollectionUtil.isNotEmpty(list)) {
            list.forEach(item -> item.setFiles(xwCmsContentFileDao.findFileByReferenceId(item.getId())));
        }
        return list;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public XwCmsContent findOneXwCmsContentByCondition(XwCmsContent xwCmsContent) {
        return xwCmsContentDao.findOneXwCmsContentByCondition(xwCmsContent);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public long findXwCmsContentCountByCondition(XwCmsContent xwCmsContent) {
        return xwCmsContentDao.findXwCmsContentCountByCondition(xwCmsContent);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateXwCmsContent(XwCmsContent xwCmsContent) {
        xwCmsContentFileDao.deleteFilesByReferenceId(xwCmsContent.getId());
        xwCmsContent.setUpdateDate(DateUtils.Nowss());
        xwCmsContentDao.updateXwCmsContent(xwCmsContent);
        if(!CollectionUtils.isEmpty(xwCmsContent.getFiles())){
            xwCmsContent.getFiles().stream().forEach(f->{
                f.setId(sequenceId.nextId());
                f.setReferenceId(xwCmsContent.getId());
                f.setCreateTime(DateUtils.Nowss());
                f.setSchoolId(xwCmsContent.getSchoolId());
            });
            xwCmsContentFileDao.batchSaveXwCmsContentFile(xwCmsContent.getFiles());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteXwCmsContent(String id) {
        XwCmsContent detail = findXwCmsContentById(id);
        if (detail != null) {
            xwCmsContentDao.deleteXwCmsContent(id);
            xwCmsContentFileDao.deleteFilesByReferenceId(detail.getId());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteXwCmsContentByCondition(XwCmsContent xwCmsContent) {
        xwCmsContentDao.deleteXwCmsContentByCondition(xwCmsContent);
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchSaveXwCmsContent(List<XwCmsContent> xwCmsContents){
        xwCmsContents.forEach(xwCmsContent -> xwCmsContent.setId(sequenceId.nextId()));
        xwCmsContentDao.batchSaveXwCmsContent(xwCmsContents);
    }


    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveXwCmsContentForLayout(XwCmsContent xwCmsContent) {
        if(Objects.isNull(xwCmsContent)){
            return false;
        }
        xwCmsContent.setUpdateDate(DateUtils.Nowss());
        if(StringUtils.isNotEmpty(xwCmsContent.getColumnId())){
            xwCmsContentDao.updateXwCmsContentForLayout(xwCmsContent);
        }else{
            xwCmsContent.setId(sequenceId.nextId());
            xwCmsContent.setColumnId(sequenceId.nextId());
            xwCmsContent.setType(1);
            xwCmsContentDao.saveXwCmsContent(xwCmsContent);
            XwCmsLayoutCondition xwCmsLayoutCondition = new XwCmsLayoutCondition();
            xwCmsLayoutCondition.setColumnId(xwCmsContent.getColumnId());
            xwCmsLayoutCondition.setFlag(true);
            xwCmsLayoutCondition.setSchoolId(xwCmsContent.getSchoolId());
            xwCmsLayoutCondition.setPosition(3);
            xwCmsLayoutCondition.setSortNumber(xwCmsContent.getSortNumber());
            xwCmsHomeLayoutService.addOrDeleteXwCmsHomeLayoutRow(xwCmsLayoutCondition);
        }
        return true;
    }
}
