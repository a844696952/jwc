package com.yice.edu.cn.jw.feign.KqOriginalData;

import com.yice.edu.cn.common.pojo.xw.kq.KqOriginalData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "kqOriginalDataFeign",path = "/kqOriginalData")
public interface KqOriginalDataFeign {

    @PostMapping("/findKqOriginalDataListByCondition")
    public List<KqOriginalData> findKqOriginalDataListByCondition(KqOriginalData kqOriginalData);

}

