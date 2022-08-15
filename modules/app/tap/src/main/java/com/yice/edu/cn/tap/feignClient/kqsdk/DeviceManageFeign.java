package com.yice.edu.cn.tap.feignClient.kqsdk;

import com.yice.edu.cn.common.pojo.xw.teacherattendance.XwTeacherImageInput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value="kqsdk",contextId = "deviceManageFeign",path = "/deviceManage")
public interface DeviceManageFeign {
    @PostMapping("/setOneCardAndFace")
    XwTeacherImageInput setOneCardAndFace(@RequestBody XwTeacherImageInput input);

    @PostMapping("/setCardAndFace")
    List<XwTeacherImageInput> setCardAndFace(@RequestBody List<XwTeacherImageInput> inputList);
}
