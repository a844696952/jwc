package com.yice.edu.cn.osp.service.xw.dj.partyMemberFile;

import com.yice.edu.cn.common.pojo.xw.dj.partyMemberFile.XwDjAttachmentFile;
import com.yice.edu.cn.osp.feignClient.xw.dj.partyMemberFile.XwDjAttachmentFileFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XwDjAttachmentFileService {
    @Autowired
    private XwDjAttachmentFileFeign xwDjAttachmentFileFeign;

    public XwDjAttachmentFile findXwDjAttachmentFileById(String id) {
        return xwDjAttachmentFileFeign.findXwDjAttachmentFileById(id);
    }

    public XwDjAttachmentFile saveXwDjAttachmentFile(XwDjAttachmentFile xwDjAttachmentFile) {
        return xwDjAttachmentFileFeign.saveXwDjAttachmentFile(xwDjAttachmentFile);
    }

    public List<XwDjAttachmentFile> findXwDjAttachmentFileListByCondition(XwDjAttachmentFile xwDjAttachmentFile) {
        return xwDjAttachmentFileFeign.findXwDjAttachmentFileListByCondition(xwDjAttachmentFile);
    }

    public XwDjAttachmentFile findOneXwDjAttachmentFileByCondition(XwDjAttachmentFile xwDjAttachmentFile) {
        return xwDjAttachmentFileFeign.findOneXwDjAttachmentFileByCondition(xwDjAttachmentFile);
    }

    public long findXwDjAttachmentFileCountByCondition(XwDjAttachmentFile xwDjAttachmentFile) {
        return xwDjAttachmentFileFeign.findXwDjAttachmentFileCountByCondition(xwDjAttachmentFile);
    }

    public void updateXwDjAttachmentFile(XwDjAttachmentFile xwDjAttachmentFile) {
        xwDjAttachmentFileFeign.updateXwDjAttachmentFile(xwDjAttachmentFile);
    }

    public void deleteXwDjAttachmentFile(String id) {
        xwDjAttachmentFileFeign.deleteXwDjAttachmentFile(id);
    }

    public void deleteXwDjAttachmentFileByCondition(XwDjAttachmentFile xwDjAttachmentFile) {
        xwDjAttachmentFileFeign.deleteXwDjAttachmentFileByCondition(xwDjAttachmentFile);
    }
}
