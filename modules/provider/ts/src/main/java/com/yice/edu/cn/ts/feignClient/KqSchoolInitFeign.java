package com.yice.edu.cn.ts.feignClient;

import com.yice.edu.cn.common.pojo.xw.kq.KqSchoolInit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "kqSchoolInitFeign",path = "/kqSchoolInit")
public interface KqSchoolInitFeign {
    @GetMapping("/findKqSchoolInitById/{id}")
    KqSchoolInit findKqSchoolInitById(@PathVariable("id") String id);
    @PostMapping("/saveKqSchoolInit")
    KqSchoolInit saveKqSchoolInit(KqSchoolInit kqSchoolInit);
    @PostMapping("/findKqSchoolInitListByCondition")
    List<KqSchoolInit> findKqSchoolInitListByCondition(KqSchoolInit kqSchoolInit);
    @PostMapping("/findOneKqSchoolInitByCondition")
    KqSchoolInit findOneKqSchoolInitByCondition(KqSchoolInit kqSchoolInit);
    @PostMapping("/findKqSchoolInitCountByCondition")
    long findKqSchoolInitCountByCondition(KqSchoolInit kqSchoolInit);
    @PostMapping("/updateKqSchoolInit")
    void updateKqSchoolInit(KqSchoolInit kqSchoolInit);
    @GetMapping("/deleteKqSchoolInit/{id}")
    void deleteKqSchoolInit(@PathVariable("id") String id);
    @PostMapping("/deleteKqSchoolInitByCondition")
    void deleteKqSchoolInitByCondition(KqSchoolInit kqSchoolInit);
}
