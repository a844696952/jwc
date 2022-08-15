package com.yice.edu.cn.bmp.feignClient.sturespMsg;


import com.yice.edu.cn.common.pojo.jw.parent.Parent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * chenshiping
 * @date
 */
@FeignClient(value="jw",path = "/parent")
public interface MyParentFeign {

    @PostMapping("/findOneParentByCondition")
     Parent findOneParentByCondition(Parent parent);
}
