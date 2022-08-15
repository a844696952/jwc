package com.yice.edu.cn.osp.service.jy.handout;

import com.yice.edu.cn.common.pojo.jy.handout.HandoutFile;
import com.yice.edu.cn.osp.feignClient.jy.handout.HandoutFileFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HandoutFileService {
    @Autowired
    private HandoutFileFeign handoutFileFeign;

    public HandoutFile findHandoutFileById(String id) {
        return handoutFileFeign.findHandoutFileById(id);
    }

    public HandoutFile saveHandoutFile(HandoutFile handoutFile) {
        return handoutFileFeign.saveHandoutFile(handoutFile);
    }

    public List<HandoutFile> findHandoutFileListByCondition(HandoutFile handoutFile) {
        return handoutFileFeign.findHandoutFileListByCondition(handoutFile);
    }

    public HandoutFile findOneHandoutFileByCondition(HandoutFile handoutFile) {
        return handoutFileFeign.findOneHandoutFileByCondition(handoutFile);
    }

    public long findHandoutFileCountByCondition(HandoutFile handoutFile) {
        return handoutFileFeign.findHandoutFileCountByCondition(handoutFile);
    }

    public void updateHandoutFile(HandoutFile handoutFile) {
        handoutFileFeign.updateHandoutFile(handoutFile);
    }

    public void deleteHandoutFile(String id) {
        handoutFileFeign.deleteHandoutFile(id);
    }

    public void deleteHandoutFileByCondition(HandoutFile handoutFile) {
        handoutFileFeign.deleteHandoutFileByCondition(handoutFile);
    }
}
