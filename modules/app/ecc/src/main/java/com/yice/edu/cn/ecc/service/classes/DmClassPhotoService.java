package com.yice.edu.cn.ecc.service.classes;

import cn.hutool.core.collection.CollUtil;
import com.yice.edu.cn.common.pojo.dm.classes.DmClassPhoto;
import com.yice.edu.cn.common.pojo.dm.schoolActive.DmAttachmentFile;
import com.yice.edu.cn.ecc.feignClient.classes.DmClassPhotoFeign;
import com.yice.edu.cn.ecc.feignClient.dmClassMeeting.DmClassMeetingFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmClassPhotoService {
    @Autowired
    private DmClassPhotoFeign dmClassPhotoFeign;
    @Autowired
    private DmClassMeetingFeign dmClassMeetingFeign;

    public DmClassPhoto findDmClassPhotoById(String id) {
        return dmClassPhotoFeign.findDmClassPhotoById(id);
    }

    public DmClassPhoto saveDmClassPhoto(DmClassPhoto dmClassPhoto) {
        return dmClassPhotoFeign.saveDmClassPhoto(dmClassPhoto);
    }

    public List<DmClassPhoto> findDmClassPhotoListByCondition(DmClassPhoto dmClassPhoto) {
        List<DmClassPhoto> list = dmClassPhotoFeign.findDmClassPhotoListByCondition(dmClassPhoto);
        if(list.size() > 3){
            list = list.subList(0,3);
        }
        list.forEach(i -> i.setType(0));
        List<DmAttachmentFile> dmClassMeetingImgs = dmClassMeetingFeign.findDmClassMeetingImgsByclassId(dmClassPhoto.getClassId());
        DmClassPhoto classPhoto = null;
        if(CollUtil.isNotEmpty(dmClassMeetingImgs)){
            for (DmAttachmentFile img : dmClassMeetingImgs) {
                classPhoto = new DmClassPhoto();
                classPhoto.setId(img.getReferenceId());
                classPhoto.setImgUrl(img.getFilePath());
                classPhoto.setType(1);
                list.add(classPhoto);
            }

        }
        return list;
    }

    public DmClassPhoto findOneDmClassPhotoByCondition(DmClassPhoto dmClassPhoto) {
        return dmClassPhotoFeign.findOneDmClassPhotoByCondition(dmClassPhoto);
    }

    public long findDmClassPhotoCountByCondition(DmClassPhoto dmClassPhoto) {
        return dmClassPhotoFeign.findDmClassPhotoCountByCondition(dmClassPhoto);
    }

    public void updateDmClassPhoto(DmClassPhoto dmClassPhoto) {
        dmClassPhotoFeign.updateDmClassPhoto(dmClassPhoto);
    }

    public void deleteDmClassPhoto(String id) {
        dmClassPhotoFeign.deleteDmClassPhoto(id);
    }

    public void deleteDmClassPhotoByCondition(DmClassPhoto dmClassPhoto) {
        dmClassPhotoFeign.deleteDmClassPhotoByCondition(dmClassPhoto);
    }
}
