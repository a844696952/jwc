package com.yice.edu.cn.dm.controller.ycVeriface;


import com.alibaba.fastjson.JSON;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcEnterBean;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcVerifaceOtherCheckBean;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcVerifacePersonBean;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcVerifaceResBean;
import com.yice.edu.cn.common.util.ycVerifaceSender.YcVerifaceSender;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/ycVerifaceOtherCheck")
@Api(value = "/ycVerifaceOtherCheck", description = "人脸识别校验（其他模块暂用）")
public class YcVerifaceOtherCheckController {

    @Value("#{'${spring.profiles.active}'=='prod'}")
    private Boolean isProd;

    @PostMapping("/checkPersonExistWithoutUserId")
    @ApiOperation(value = "校验人员头像是否存在并返回匹配Userid", notes = "返回真假")
    public YcVerifaceOtherCheckBean checkPersonExistWithoutUserId(
            @ApiParam(value = "对象", required = true)
            @RequestBody YcEnterBean ycEnterBean) {
        String schoolId = ycEnterBean.getSchoolId();
        List<YcEnterBean> firstEnterStu = new ArrayList<>();
        ycEnterBean.setIndex("0");
        firstEnterStu.add(ycEnterBean);
        String res = YcVerifaceSender.postRequest(isProd, YcVerifaceSender.MATCH_BATCH_N, schoolId, firstEnterStu);
        YcVerifaceResBean ycVerifaceResBean = JSON.parseObject(res, YcVerifaceResBean.class);
        List<Object> beans = ycVerifaceResBean.getBeans();
        YcVerifaceOtherCheckBean ycVerifaceOtherCheckBean = new YcVerifaceOtherCheckBean();
        for (Object o:beans){
            YcVerifacePersonBean ycVerifacePersonBean = JSON.parseObject(o.toString(), YcVerifacePersonBean.class);
            if (!ycVerifacePersonBean.getIndex().equals("0")){
                continue;
            }
            String result1 = ycVerifacePersonBean.getResult();
           /*if (result1.equals("stranger")){
                break;
            }else {
                result = result1;
            }*/

           switch (result1){
               case "index get failed!":
                   ycVerifaceOtherCheckBean.setSuccess(false);
                   ycVerifaceOtherCheckBean.setResultCode("1");
                   ycVerifaceOtherCheckBean.setResultMessage("图片校验位序错误");
                   break;
               case "index: image get failed!":
                   ycVerifaceOtherCheckBean.setSuccess(false);
                   ycVerifaceOtherCheckBean.setResultCode("2");
                   ycVerifaceOtherCheckBean.setResultMessage("图片上传失败");
                   break;
               case "No face detect":
                   ycVerifaceOtherCheckBean.setSuccess(false);
                   ycVerifaceOtherCheckBean.setResultCode("3");
                   ycVerifaceOtherCheckBean.setResultMessage("未检测到人脸");
                   break;
               case "Image data error":
                   ycVerifaceOtherCheckBean.setSuccess(false);
                   ycVerifaceOtherCheckBean.setResultCode("4");
                   ycVerifaceOtherCheckBean.setResultMessage("图片不完整");
                   break;
               case "Failure in face feature calculation!":
                   ycVerifaceOtherCheckBean.setSuccess(false);
                   ycVerifaceOtherCheckBean.setResultCode("5");
                   ycVerifaceOtherCheckBean.setResultMessage("人脸特征计算失败!");
                   break;
               case "stranger":
                   ycVerifaceOtherCheckBean.setSuccess(false);
                   ycVerifaceOtherCheckBean.setResultCode("6");
                   ycVerifaceOtherCheckBean.setResultMessage("陌生人!");
                   break;
              default:
                   ycVerifaceOtherCheckBean.setSuccess(true);
                   ycVerifaceOtherCheckBean.setResultCode("7");
                   ycVerifaceOtherCheckBean.setResultMessage(result1);
                   break;

           }
        }
        return ycVerifaceOtherCheckBean;

    }

    @PostMapping("/checkPersonExistByUserId")
    @ApiOperation(value = "校验人员头像与指定人员Id是否符合", notes = "返回真假")
    public YcVerifaceOtherCheckBean checkPersonExistByUserId(
            @ApiParam(value = "对象", required = true)
            @RequestBody YcEnterBean ycEnterBean) {
        String schoolId = ycEnterBean.getSchoolId();
        List<YcEnterBean> firstEnterStu = new ArrayList<>();
        ycEnterBean.setSchoolId(null);
        firstEnterStu.add(ycEnterBean);
        String res = YcVerifaceSender.postRequest(isProd, YcVerifaceSender.MATCH_BATCH_ID, schoolId, firstEnterStu);
        YcVerifaceResBean ycVerifaceResBean = JSON.parseObject(res, YcVerifaceResBean.class);
        List<Object> beans = ycVerifaceResBean.getBeans();
        boolean exist = false;
        YcVerifaceOtherCheckBean ycVerifaceOtherCheckBean = new YcVerifaceOtherCheckBean();
        for (Object o:beans){
            YcVerifacePersonBean ycVerifacePersonBean = JSON.parseObject(o.toString(), YcVerifacePersonBean.class);
            if (!ycVerifacePersonBean.getUserId().equals(ycEnterBean.getUserID())){
                continue;
            }
            String result1 = ycVerifacePersonBean.getResult();
           /* if (!result1.equals("is the same person")){
                break;
            }else {
                exist = true;
            }
            */
            switch (result1){
                case "userID get failed!":
                    ycVerifaceOtherCheckBean.setSuccess(false);
                    ycVerifaceOtherCheckBean.setResultCode("1");
                    ycVerifaceOtherCheckBean.setResultMessage("未指定用户");
                    break;
                case "image get failed!":
                    ycVerifaceOtherCheckBean.setSuccess(false);
                    ycVerifaceOtherCheckBean.setResultCode("2");
                    ycVerifaceOtherCheckBean.setResultMessage("图片上传失败");
                    break;
                case "No face detect":
                    ycVerifaceOtherCheckBean.setSuccess(false);
                    ycVerifaceOtherCheckBean.setResultCode("3");
                    ycVerifaceOtherCheckBean.setResultMessage("未检测到人脸");
                    break;
                case "Image data error":
                    ycVerifaceOtherCheckBean.setSuccess(false);
                    ycVerifaceOtherCheckBean.setResultCode("4");
                    ycVerifaceOtherCheckBean.setResultMessage("图片不完整");
                    break;
                case "Failure in face feature calculation!":
                    ycVerifaceOtherCheckBean.setSuccess(false);
                    ycVerifaceOtherCheckBean.setResultCode("5");
                    ycVerifaceOtherCheckBean.setResultMessage("人脸特征计算失败!");
                    break;
                case "this user is not registered!":
                    ycVerifaceOtherCheckBean.setSuccess(false);
                    ycVerifaceOtherCheckBean.setResultCode("6");
                    ycVerifaceOtherCheckBean.setResultMessage("用户未录入人像信息!");
                    break;
                case "not the same person":
                    ycVerifaceOtherCheckBean.setSuccess(false);
                    ycVerifaceOtherCheckBean.setResultCode("7");
                    ycVerifaceOtherCheckBean.setResultMessage("与指定用户人像不符");
                    break;
                case "is the same person":
                    ycVerifaceOtherCheckBean.setSuccess(true);
                    ycVerifaceOtherCheckBean.setResultCode("8");
                    ycVerifaceOtherCheckBean.setResultMessage("校验通过");
                    break;
                default:
                    ycVerifaceOtherCheckBean.setSuccess(false);
                    ycVerifaceOtherCheckBean.setResultCode("9");
                    ycVerifaceOtherCheckBean.setResultMessage("网络繁忙");
                    break;
            }
        }



        return ycVerifaceOtherCheckBean;
    }


}
