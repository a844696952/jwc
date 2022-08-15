package com.yice.edu.cn.osp.feignClient.xw.dj.partyMemberFile;

import com.yice.edu.cn.common.pojo.xw.dj.partyMemberFile.XwDjAttachmentFile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "xwDjAttachmentFileFeign",path = "/xwDjAttachmentFile")
public interface XwDjAttachmentFileFeign {
    @GetMapping("/findXwDjAttachmentFileById/{id}")
    XwDjAttachmentFile findXwDjAttachmentFileById(@PathVariable("id") String id);
    @PostMapping("/saveXwDjAttachmentFile")
    XwDjAttachmentFile saveXwDjAttachmentFile(XwDjAttachmentFile xwDjAttachmentFile);
    @PostMapping("/findXwDjAttachmentFileListByCondition")
    List<XwDjAttachmentFile> findXwDjAttachmentFileListByCondition(XwDjAttachmentFile xwDjAttachmentFile);
    @PostMapping("/findOneXwDjAttachmentFileByCondition")
    XwDjAttachmentFile findOneXwDjAttachmentFileByCondition(XwDjAttachmentFile xwDjAttachmentFile);
    @PostMapping("/findXwDjAttachmentFileCountByCondition")
    long findXwDjAttachmentFileCountByCondition(XwDjAttachmentFile xwDjAttachmentFile);
    @PostMapping("/updateXwDjAttachmentFile")
    void updateXwDjAttachmentFile(XwDjAttachmentFile xwDjAttachmentFile);
    @GetMapping("/deleteXwDjAttachmentFile/{id}")
    void deleteXwDjAttachmentFile(@PathVariable("id") String id);
    @PostMapping("/deleteXwDjAttachmentFileByCondition")
    void deleteXwDjAttachmentFileByCondition(XwDjAttachmentFile xwDjAttachmentFile);
}
