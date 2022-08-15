package com.yice.edu.cn.ecc.service.parentMsg;

import com.yice.edu.cn.common.pojo.dm.parentMsg.Parentmsg;
import com.yice.edu.cn.ecc.feignClient.parentMsg.ParentmsgFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value="parentmsgService")
public class ParentmsgService {
    @Autowired
    private ParentmsgFeign parentmsgFeign;

    public Parentmsg findParentmsgById(String id) {
        return parentmsgFeign.findParentmsgById(id);
    }

    public Parentmsg saveParentmsg(Parentmsg parentmsg) {
        return parentmsgFeign.saveParentmsg(parentmsg);
    }

    public List<Parentmsg> findParentmsgListByCondition(Parentmsg parentmsg) {
        return parentmsgFeign.findParentmsgListByCondition(parentmsg);
    }

    public Parentmsg findOneParentmsgByCondition(Parentmsg parentmsg) {
        return parentmsgFeign.findOneParentmsgByCondition(parentmsg);
    }

    public long findParentmsgCountByCondition(Parentmsg parentmsg) {
        return parentmsgFeign.findParentmsgCountByCondition(parentmsg);
    }

    public void updateParentmsg(Parentmsg parentmsg) {
        parentmsgFeign.updateParentmsg(parentmsg);
    }

    public void deleteParentmsg(String id) {
        parentmsgFeign.deleteParentmsg(id);
    }

    public void deleteParentmsgByCondition(Parentmsg parentmsg) {
        parentmsgFeign.deleteParentmsgByCondition(parentmsg);
    }

    public void updateParentmsgByStuCardNo(Parentmsg parentmsg){
        parentmsgFeign.updateParentmsgByStuCardNo(parentmsg);
    }
}
