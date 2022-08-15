package com.yice.edu.cn.osp.feignClient.xw.dj.partyMemberPhoto;

import com.yice.edu.cn.common.pojo.xw.dj.partyMerberPhoto.XwDjPhoto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw", contextId = "xwDjPhotoFeign", path = "/xwDjPhoto")
public interface XwDjPhotoFeign {
    @GetMapping("/findXwDjPhotoById/{id}")
    XwDjPhoto findXwDjPhotoById(@PathVariable("id") String id);
    @PostMapping("/saveXwDjPhoto")
    XwDjPhoto saveXwDjPhoto(XwDjPhoto xwDjPhoto);
    @PostMapping("/findXwDjPhotoListByCondition")
    List<XwDjPhoto> findXwDjPhotoListByCondition(XwDjPhoto xwDjPhoto);
    @PostMapping("/findOneXwDjPhotoByCondition")
    XwDjPhoto findOneXwDjPhotoByCondition(XwDjPhoto xwDjPhoto);
    @PostMapping("/findXwDjPhotoCountByCondition")
    long findXwDjPhotoCountByCondition(XwDjPhoto xwDjPhoto);
    @PostMapping("/updateXwDjPhoto")
    void updateXwDjPhoto(XwDjPhoto xwDjPhoto);
    @GetMapping("/deleteXwDjPhoto/{id}")
    void deleteXwDjPhoto(@PathVariable("id") String id);
    @PostMapping("/deleteXwDjPhotoByCondition")
    void deleteXwDjPhotoByCondition(XwDjPhoto xwDjPhoto);
}
