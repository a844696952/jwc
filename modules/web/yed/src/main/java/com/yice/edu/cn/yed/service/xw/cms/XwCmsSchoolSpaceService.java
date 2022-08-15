package com.yice.edu.cn.yed.service.xw.cms;

import cn.hutool.core.collection.CollUtil;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsSchoolSpace;
import com.yice.edu.cn.yed.feignClient.xw.cms.XwCmsSchoolSpaceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class XwCmsSchoolSpaceService {
    @Autowired
    private XwCmsSchoolSpaceFeign xwCmsSchoolSpaceFeign;

    public XwCmsSchoolSpace findXwCmsSchoolSpaceById(String id) {
        return xwCmsSchoolSpaceFeign.findXwCmsSchoolSpaceById(id);
    }

    public XwCmsSchoolSpace saveXwCmsSchoolSpace(XwCmsSchoolSpace xwCmsSchoolSpace) {
        return xwCmsSchoolSpaceFeign.saveXwCmsSchoolSpace(xwCmsSchoolSpace);
    }

    public XwCmsSchoolSpace findXwCmsSchoolSpaceByDomain(String secondDomain){
        return xwCmsSchoolSpaceFeign.findXwCmsSchoolSpaceByDomain(secondDomain);
    }

    public List<XwCmsSchoolSpace> findXwCmsSchoolSpaceListByCondition(XwCmsSchoolSpace xwCmsSchoolSpace) {
        List<XwCmsSchoolSpace> xwCmsSchoolSpaceListByCondition = xwCmsSchoolSpaceFeign.findXwCmsSchoolSpaceListByCondition(xwCmsSchoolSpace);
        if(CollUtil.isNotEmpty(xwCmsSchoolSpaceListByCondition)){
            xwCmsSchoolSpaceListByCondition.forEach(x->{
                if(Objects.nonNull(x.getDomain())){
                    x.setDomain(String.format(x.getDomain(),""));
                }else{
                    x.setDomain("");
                }
            });
        }
        return xwCmsSchoolSpaceListByCondition;
    }

    public XwCmsSchoolSpace findOneXwCmsSchoolSpaceByCondition(XwCmsSchoolSpace xwCmsSchoolSpace) {
        return xwCmsSchoolSpaceFeign.findOneXwCmsSchoolSpaceByCondition(xwCmsSchoolSpace);
    }

    public long findXwCmsSchoolSpaceCountByCondition(XwCmsSchoolSpace xwCmsSchoolSpace) {
        return xwCmsSchoolSpaceFeign.findXwCmsSchoolSpaceCountByCondition(xwCmsSchoolSpace);
    }

    public void updateXwCmsSchoolSpace(XwCmsSchoolSpace xwCmsSchoolSpace) {
        xwCmsSchoolSpaceFeign.updateXwCmsSchoolSpace(xwCmsSchoolSpace);
    }

    public void deleteXwCmsSchoolSpace(String id) {
        xwCmsSchoolSpaceFeign.deleteXwCmsSchoolSpace(id);
    }

    public void deleteXwCmsSchoolSpaceByCondition(XwCmsSchoolSpace xwCmsSchoolSpace) {
        xwCmsSchoolSpaceFeign.deleteXwCmsSchoolSpaceByCondition(xwCmsSchoolSpace);
    }

    public List<XwCmsSchoolSpace> findProvinceList(){
        return xwCmsSchoolSpaceFeign.findProvinceList();
    }

    public void updateXwCmsSchoolSpaceBySchoolId(XwCmsSchoolSpace xwCmsSchoolSpace){
        xwCmsSchoolSpaceFeign.updateXwCmsSchoolSpaceBySchoolId(xwCmsSchoolSpace);
    }
}
