package com.yice.edu.cn.osp.service.dm.ycVeriface;

import com.yice.edu.cn.common.pojo.dm.ycVeriface.factory.YcVerifaceFactory;
import com.yice.edu.cn.osp.feignClient.dm.ycVeriface.YcVerifaceFactoryFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YcVerifaceFactoryService {
    @Autowired
    private YcVerifaceFactoryFeign ycVerifaceFactoryFeign;

    public YcVerifaceFactory findYcVerifaceFactoryById(String id) {
        return ycVerifaceFactoryFeign.findYcVerifaceFactoryById(id);
    }

    public YcVerifaceFactory saveYcVerifaceFactory(YcVerifaceFactory ycVerifaceFactory) {
        return ycVerifaceFactoryFeign.saveYcVerifaceFactory(ycVerifaceFactory);
    }

    public List<YcVerifaceFactory> findYcVerifaceFactoryListByCondition(YcVerifaceFactory ycVerifaceFactory) {
        return ycVerifaceFactoryFeign.findYcVerifaceFactoryListByCondition(ycVerifaceFactory);
    }

    public YcVerifaceFactory findOneYcVerifaceFactoryByCondition(YcVerifaceFactory ycVerifaceFactory) {
        return ycVerifaceFactoryFeign.findOneYcVerifaceFactoryByCondition(ycVerifaceFactory);
    }

    public long findYcVerifaceFactoryCountByCondition(YcVerifaceFactory ycVerifaceFactory) {
        return ycVerifaceFactoryFeign.findYcVerifaceFactoryCountByCondition(ycVerifaceFactory);
    }

    public void updateYcVerifaceFactory(YcVerifaceFactory ycVerifaceFactory) {
        ycVerifaceFactoryFeign.updateYcVerifaceFactory(ycVerifaceFactory);
    }

    public void deleteYcVerifaceFactory(String id) {
        ycVerifaceFactoryFeign.deleteYcVerifaceFactory(id);
    }

    public void deleteYcVerifaceFactoryByCondition(YcVerifaceFactory ycVerifaceFactory) {
        ycVerifaceFactoryFeign.deleteYcVerifaceFactoryByCondition(ycVerifaceFactory);
    }
}
