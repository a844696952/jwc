package com.yice.edu.cn.ecc.feignClient.parentMsg;

import com.yice.edu.cn.common.pojo.dm.parentMsg.Parentmsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",path = "/parentmsg")
public interface ParentmsgFeign {
    @GetMapping("/findParentmsgById/{id}")
    Parentmsg findParentmsgById(@PathVariable("id") String id);
    @PostMapping("/saveParentmsg")
    Parentmsg saveParentmsg(Parentmsg parentmsg);
    @PostMapping("/findParentmsgListByCondition")
    List<Parentmsg> findParentmsgListByCondition(Parentmsg parentmsg);
    @PostMapping("/findOneParentmsgByCondition")
    Parentmsg findOneParentmsgByCondition(Parentmsg parentmsg);
    @PostMapping("/findParentmsgCountByCondition")
    long findParentmsgCountByCondition(Parentmsg parentmsg);
    @PostMapping("/updateParentmsg")
    void updateParentmsg(Parentmsg parentmsg);
    @GetMapping("/deleteParentmsg/{id}")
    void deleteParentmsg(@PathVariable("id") String id);
    @PostMapping("/deleteParentmsgByCondition")
    void deleteParentmsgByCondition(Parentmsg parentmsg);

    @PostMapping("/updateParentmsgByStuCardNo")
    void updateParentmsgByStuCardNo(Parentmsg parentmsg);
}
