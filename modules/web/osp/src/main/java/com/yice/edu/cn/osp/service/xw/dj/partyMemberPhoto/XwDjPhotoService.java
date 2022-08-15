package com.yice.edu.cn.osp.service.xw.dj.partyMemberPhoto;

import com.yice.edu.cn.common.pojo.xw.dj.partyMerberPhoto.XwDjPhoto;
import com.yice.edu.cn.osp.feignClient.xw.dj.partyMemberPhoto.XwDjPhotoFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XwDjPhotoService {
    @Autowired
    private XwDjPhotoFeign xwDjPhotoFeign;

    public XwDjPhoto findXwDjPhotoById(String id) {
        return xwDjPhotoFeign.findXwDjPhotoById(id);
    }

    public XwDjPhoto saveXwDjPhoto(XwDjPhoto xwDjPhoto) {
        return xwDjPhotoFeign.saveXwDjPhoto(xwDjPhoto);
    }

    public List<XwDjPhoto> findXwDjPhotoListByCondition(XwDjPhoto xwDjPhoto) {
        return xwDjPhotoFeign.findXwDjPhotoListByCondition(xwDjPhoto);
    }

    public XwDjPhoto findOneXwDjPhotoByCondition(XwDjPhoto xwDjPhoto) {
        return xwDjPhotoFeign.findOneXwDjPhotoByCondition(xwDjPhoto);
    }

    public long findXwDjPhotoCountByCondition(XwDjPhoto xwDjPhoto) {
        return xwDjPhotoFeign.findXwDjPhotoCountByCondition(xwDjPhoto);
    }

    public void updateXwDjPhoto(XwDjPhoto xwDjPhoto) {
        xwDjPhotoFeign.updateXwDjPhoto(xwDjPhoto);
    }

    public void deleteXwDjPhoto(String id) {
        xwDjPhotoFeign.deleteXwDjPhoto(id);
    }

    public void deleteXwDjPhotoByCondition(XwDjPhoto xwDjPhoto) {
        xwDjPhotoFeign.deleteXwDjPhotoByCondition(xwDjPhoto);
    }
}
