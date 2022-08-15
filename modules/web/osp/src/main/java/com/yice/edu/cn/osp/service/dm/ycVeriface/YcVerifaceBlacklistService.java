package com.yice.edu.cn.osp.service.dm.ycVeriface;

import com.yice.edu.cn.common.pojo.dm.ycVeriface.blacklist.YcVerifaceBlacklist;
import com.yice.edu.cn.osp.feignClient.dm.ycVeriface.YcVerifaceBlacklistFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YcVerifaceBlacklistService {
    @Autowired
    private YcVerifaceBlacklistFeign ycVerifaceBlacklistFeign;

    public YcVerifaceBlacklist findYcVerifaceBlacklistById(String id) {
        return ycVerifaceBlacklistFeign.findYcVerifaceBlacklistById(id);
    }

    public YcVerifaceBlacklist saveYcVerifaceBlacklist(YcVerifaceBlacklist ycVerifaceBlacklist) {
        return ycVerifaceBlacklistFeign.saveYcVerifaceBlacklist(ycVerifaceBlacklist);
    }

    public List<YcVerifaceBlacklist> findYcVerifaceBlacklistListByCondition(YcVerifaceBlacklist ycVerifaceBlacklist) {
        return ycVerifaceBlacklistFeign.findYcVerifaceBlacklistListByCondition(ycVerifaceBlacklist);
    }

    public YcVerifaceBlacklist findOneYcVerifaceBlacklistByCondition(YcVerifaceBlacklist ycVerifaceBlacklist) {
        return ycVerifaceBlacklistFeign.findOneYcVerifaceBlacklistByCondition(ycVerifaceBlacklist);
    }

    public long findYcVerifaceBlacklistCountByCondition(YcVerifaceBlacklist ycVerifaceBlacklist) {
        return ycVerifaceBlacklistFeign.findYcVerifaceBlacklistCountByCondition(ycVerifaceBlacklist);
    }

    public void updateYcVerifaceBlacklist(YcVerifaceBlacklist ycVerifaceBlacklist) {
        ycVerifaceBlacklistFeign.updateYcVerifaceBlacklist(ycVerifaceBlacklist);
    }

    public void deleteYcVerifaceBlacklist(String id) {
        ycVerifaceBlacklistFeign.deleteYcVerifaceBlacklist(id);
    }

    public void deleteYcVerifaceBlacklistByCondition(YcVerifaceBlacklist ycVerifaceBlacklist) {
        ycVerifaceBlacklistFeign.deleteYcVerifaceBlacklistByCondition(ycVerifaceBlacklist);
    }
}
