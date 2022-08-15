package com.yice.edu.cn.xw.service.dj.information;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.xw.dj.information.XwDjInformation;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberFile.XwDjAttachmentFile;
import com.yice.edu.cn.xw.dao.dj.information.IXwDjInformationDao;
import com.yice.edu.cn.xw.dao.dj.partyMemberFile.IXwDjAttachmentFileDao;
import com.yice.edu.cn.xw.service.dj.partyMemberFile.XwDjAttachmentFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class XwDjInformationService {
    @Autowired
    private IXwDjInformationDao xwDjInformationDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private XwDjAttachmentFileService fileService;
    @Autowired
    private IXwDjAttachmentFileDao fileDao;

/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
@Transactional(readOnly = true, rollbackFor = Exception.class)
    public XwDjInformation findXwDjInformationById(String id) {
        return xwDjInformationDao.findXwDjInformationById(id);
    }

    /**
     * 新建资讯
     *
     * @param xwDjInformation 实体
     */
    @Transactional(rollbackFor = Exception.class)
    public void createInformation(XwDjInformation xwDjInformation) {
        //创建信息数据
        saveXwDjInformation(xwDjInformation);
        //创建附件数据
        createFile(xwDjInformation);
    }

    /**
     * 编辑资讯
     *
     * @param xwDjInformation 实体
     */
    @Transactional(rollbackFor = Exception.class)
    public void editXwDjInformation(XwDjInformation xwDjInformation) {
        //编辑信息数据
        updateXwDjInformation(xwDjInformation);
        //删除历史附件
        XwDjAttachmentFile file = new XwDjAttachmentFile();
        file.setReferenceId(xwDjInformation.getId());
        fileService.deleteXwDjAttachmentFileByCondition(file);
        //创建附件数据
        createFile(xwDjInformation);
    }

    private void createFile(XwDjInformation xwDjInformation) {
        if (CollectionUtil.isNotEmpty(xwDjInformation.getXwDjAttachmentFiles())) {
            xwDjInformation.getXwDjAttachmentFiles().forEach(file -> {
                file.setCreateTime(DateUtil.now());
                file.setSchoolId(xwDjInformation.getSchoolId());
                file.setId(sequenceId.nextId());
                file.setReferenceId(xwDjInformation.getId());
                file.setFileDesc("党建咨询信息附件");

            });
            fileService.batchSaveXwDjAttachmentFile(xwDjInformation.getXwDjAttachmentFiles());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveXwDjInformation(XwDjInformation xwDjInformation) {
        xwDjInformation.setId(sequenceId.nextId());
        xwDjInformation.setCreateDate(DateUtil.now());
        xwDjInformation.setCreateTime(DateUtil.now());
        xwDjInformationDao.saveXwDjInformation(xwDjInformation);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<XwDjInformation> findXwDjInformationListByCondition(XwDjInformation xwDjInformation) {
        return xwDjInformationDao.getListByCondition(xwDjInformation);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public XwDjInformation findOneXwDjInformationByCondition(XwDjInformation xwDjInformation) {
        return xwDjInformationDao.findOneXwDjInformationByCondition(xwDjInformation);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public long findXwDjInformationCountByCondition(XwDjInformation xwDjInformation) {
        return xwDjInformationDao.getCountByCondition(xwDjInformation);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateXwDjInformation(XwDjInformation xwDjInformation) {
        xwDjInformation.setOperateDate(DateUtil.now());
        xwDjInformationDao.updateXwDjInformation(xwDjInformation);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteXwDjInformation(String id) {
        xwDjInformationDao.deleteXwDjInformation(id);
        fileDao.deleteXwDjAttachmentFileByReferenceId(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteXwDjInformationByCondition(XwDjInformation xwDjInformation) {
        xwDjInformationDao.deleteXwDjInformationByCondition(xwDjInformation);
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchSaveXwDjInformation(List<XwDjInformation> xwDjInformations){
        xwDjInformations.forEach(xwDjInformation -> xwDjInformation.setId(sequenceId.nextId()));
        xwDjInformationDao.batchSaveXwDjInformation(xwDjInformations);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @Transactional(readOnly = true)
    public XwDjInformation selectXwDjInformationById(String id) {
        return xwDjInformationDao.selectXwDjInformationById(id);
    }


}
