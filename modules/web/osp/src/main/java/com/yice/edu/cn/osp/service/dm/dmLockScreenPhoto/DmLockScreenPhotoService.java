package com.yice.edu.cn.osp.service.dm.dmLockScreenPhoto;

import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import com.yice.edu.cn.common.pojo.dm.dmLockScreenPhoto.DmLockScreenPhoto;
import com.yice.edu.cn.osp.feignClient.dm.classCard.DmClassCardFeign;
import com.yice.edu.cn.osp.feignClient.dm.dmLockScreenPhoto.DmLockScreenPhotoFeign;
import com.yice.edu.cn.osp.service.dm.classCard.DmClassCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;

@Service
public class DmLockScreenPhotoService {
    @Autowired
    private DmLockScreenPhotoFeign dmLockScreenPhotoFeign;
    @Autowired
    private DmClassCardFeign dmClassCardFeign;
    @Autowired
    private DmClassCardService dmClassCardService;

    public DmLockScreenPhoto findDmLockScreenPhotoById(String id) {
        return dmLockScreenPhotoFeign.findDmLockScreenPhotoById(id);
    }

    public DmLockScreenPhoto saveDmLockScreenPhoto(DmLockScreenPhoto dmLockScreenPhoto) {
        return dmLockScreenPhotoFeign.saveDmLockScreenPhoto(dmLockScreenPhoto);
    }

    public List<DmLockScreenPhoto> findDmLockScreenPhotoListByCondition(DmLockScreenPhoto dmLockScreenPhoto) {
        return dmLockScreenPhotoFeign.findDmLockScreenPhotoListByCondition(dmLockScreenPhoto);
    }

    public DmLockScreenPhoto findOneDmLockScreenPhotoByCondition(DmLockScreenPhoto dmLockScreenPhoto) {
        return dmLockScreenPhotoFeign.findOneDmLockScreenPhotoByCondition(dmLockScreenPhoto);
    }

    public long findDmLockScreenPhotoCountByCondition(DmLockScreenPhoto dmLockScreenPhoto) {
        return dmLockScreenPhotoFeign.findDmLockScreenPhotoCountByCondition(dmLockScreenPhoto);
    }

    public void updateDmLockScreenPhoto(DmLockScreenPhoto dmLockScreenPhoto) {
        dmLockScreenPhotoFeign.updateDmLockScreenPhoto(dmLockScreenPhoto);
    }

    public void deleteDmLockScreenPhoto(String id) {
        dmLockScreenPhotoFeign.deleteDmLockScreenPhoto(id);
    }

    public void deleteDmLockScreenPhotoByCondition(DmLockScreenPhoto dmLockScreenPhoto) {
        dmLockScreenPhotoFeign.deleteDmLockScreenPhotoByCondition(dmLockScreenPhoto);
    }

    public void updateCurrentScreenPhoto(String id) {
        dmLockScreenPhotoFeign.updateCurrentScreenPhoto(id);
        //更改锁屏后发送极光推送到锁屏状态的班牌
        List<DmClassCard> dmClassCardList = dmClassCardFeign.getDmClassCardByTeacherId(myId(), "1");
        if(dmClassCardList != null && dmClassCardList.size()>0){
            String[] userNames = new String[dmClassCardList.size()];
            for (int i = 0; i < dmClassCardList.size(); i++) {
                userNames[i] = dmClassCardList.get(i).getUserName();
            }
            dmClassCardService.sendLockDmScreenMsg(userNames);
        }
    }

    public void cancelCurrentScreenPhoto(String id) {
        dmLockScreenPhotoFeign.cancelCurrentScreenPhoto(id);
    }

    public void batchdelete(DmLockScreenPhoto dmLockScreenPhoto) {
        dmLockScreenPhotoFeign.batchdelete(dmLockScreenPhoto);
    }
}
