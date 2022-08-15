package com.yice.edu.cn.bmp.feignClient.sturespMsg;

import com.yice.edu.cn.common.pojo.dm.sturespMsg.Sturespmsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",path = "/sturespmsg")
public interface SturespmsgFeign {
    @GetMapping("/findSturespmsgById/{id}")
    Sturespmsg findSturespmsgById(@PathVariable("id") String id);
    @PostMapping("/saveSturespmsg")
    Sturespmsg saveSturespmsg(Sturespmsg sturespmsg);
    @PostMapping("/findSturespmsgListByCondition")
    List<Sturespmsg> findSturespmsgListByCondition(Sturespmsg sturespmsg);
    @PostMapping("/findOneSturespmsgByCondition")
    Sturespmsg findOneSturespmsgByCondition(Sturespmsg sturespmsg);
    @PostMapping("/findSturespmsgCountByCondition")
    long findSturespmsgCountByCondition(Sturespmsg sturespmsg);
    @PostMapping("/updateSturespmsg")
    void updateSturespmsg(Sturespmsg sturespmsg);
    @GetMapping("/deleteSturespmsg/{id}")
    void deleteSturespmsg(@PathVariable("id") String id);
    @PostMapping("/deleteSturespmsgByCondition")
    void deleteSturespmsgByCondition(Sturespmsg sturespmsg);
}
