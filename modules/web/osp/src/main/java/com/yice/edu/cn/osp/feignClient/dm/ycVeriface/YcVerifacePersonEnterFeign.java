package com.yice.edu.cn.osp.feignClient.dm.ycVeriface;

import com.yice.edu.cn.common.pojo.dm.ycVeriface.personEnter.YcVerifacePersonEnter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "YcVerifacePersonEnterFeign",path = "/ycVerifacePersonEnter")
public interface YcVerifacePersonEnterFeign {
    @GetMapping("/findYcVerifacePersonEnterById/{id}")
    YcVerifacePersonEnter findYcVerifacePersonEnterById(@PathVariable("id") String id);
    @PostMapping("/saveYcVerifacePersonEnter")
    YcVerifacePersonEnter saveYcVerifacePersonEnter(YcVerifacePersonEnter ycVerifacePersonEnter);
    @PostMapping("/findYcVerifacePersonEnterListByCondition")
    List<YcVerifacePersonEnter> findYcVerifacePersonEnterListByCondition(YcVerifacePersonEnter ycVerifacePersonEnter);
    @PostMapping("/findOneYcVerifacePersonEnterByCondition")
    YcVerifacePersonEnter findOneYcVerifacePersonEnterByCondition(YcVerifacePersonEnter ycVerifacePersonEnter);
    @PostMapping("/findYcVerifacePersonEnterCountByCondition")
    long findYcVerifacePersonEnterCountByCondition(YcVerifacePersonEnter ycVerifacePersonEnter);
    @PostMapping("/updateYcVerifacePersonEnter")
    void updateYcVerifacePersonEnter(YcVerifacePersonEnter ycVerifacePersonEnter);
    @GetMapping("/deleteYcVerifacePersonEnter/{id}")
    void deleteYcVerifacePersonEnter(@PathVariable("id") String id);
    @PostMapping("/deleteYcVerifacePersonEnterByCondition")
    void deleteYcVerifacePersonEnterByCondition(YcVerifacePersonEnter ycVerifacePersonEnter);
    @PostMapping("/findYcVerifacePersonEnterByPersonIdList")
    List<YcVerifacePersonEnter> findYcVerifacePersonEnterByPersonIdList(YcVerifacePersonEnter ycVerifacePersonEnter);
    @PostMapping("/batchSaveYcVerifacePersonEnter")
    void batchSaveYcVerifacePersonEnter(List<YcVerifacePersonEnter> ycVerifacePersonEnter);

}
