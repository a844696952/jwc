package com.yice.edu.cn.osp.service.kqsdk;

import com.yice.edu.cn.common.pojo.xw.teacherattendance.XwTeacherImageInput;
import com.yice.edu.cn.osp.feignClient.kqsdk.DeviceManageFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceManageService {
    @Autowired
    DeviceManageFeign deviceManageFeign;

    public XwTeacherImageInput setOneCardAndFace(XwTeacherImageInput input){
        return this.deviceManageFeign.setOneCardAndFace(input);
    }

    public List<XwTeacherImageInput> setCardAndFace(List<XwTeacherImageInput> inputList){
        return this.deviceManageFeign.setCardAndFace(inputList);
    }
}
