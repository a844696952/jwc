package com.yice.edu.cn.tap.controller.doc;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.handout.FileObj;
import com.yice.edu.cn.common.pojo.xw.document.Writing;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.tap.interceptor.LoginInterceptor;
import com.yice.edu.cn.tap.service.doc.WritingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/writing")
@Api(value = "/writing",description = "（发文管理）我发送的")
public class WritingController {
    @Autowired
    private WritingService writingService;

    @PostMapping("/saveWriting")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveWriting(
            @ApiParam(value = "对象", required = true)
            @RequestBody Writing writing){
        writing.setSchoolId(LoginInterceptor.mySchoolId());
        writing.setUserId(LoginInterceptor.myId());
        Writing s=writingService.saveWriting(writing);
        return new ResponseJson(s);
    }

    //修改
    @PostMapping("/update/updateWriting")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateWriting(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody Writing writing){
        writing.setWritingType(1);
        writingService.updateWriting(writing);
        return new ResponseJson();
    }

    @GetMapping("/look/lookWritingById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookWritingById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        Writing writing=writingService.getWritingRejectUpdate(id);
        return new ResponseJson(writing);
    }

    @PostMapping("/findWritingsByCondition")
    @ApiOperation(value = "根据条件查找,返回我发送的发文列表 （条件:当前状态（writingType字段 0为全部 1为未审核 2为已完成 3为待修改  时间段（SearchTimeZone[]字段） 公文类型（writingNumberType字段 1为公文  2为教育督导）））", notes = "返回响应对象")
    public ResponseJson findWritingsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Writing writing){
        if(writing.getWritingNumberType()!=null&&writing.getWritingNumberType()==3){
            writing.setWritingNumberType(null);
        }

        if(writing.getWritingType()!=null&&writing.getWritingType()==0){
            writing.setWritingType(null);
        }
        writing.setSchoolId(LoginInterceptor.mySchoolId());
        writing.setUserId(LoginInterceptor.myId());
        String[] zero = writing.getSearchTimeZone();
        if(zero!=null&&zero.length>0){
            writing.setStartTime(zero[0]);
            writing.setEndTime(zero[1]);
        }

        //清除前台的排序
        if("id".equals(writing.getPager().getSortField())||writing.getPager().getSortField()==null){
            writing.getPager().setSortField("");
            writing.setOrder("true");
        }
        List<Writing> data=writingService.findWritingListByCondition(writing);
        long count=writingService.findWritingCountByCondition(writing);
        return new ResponseJson(data,count);
    }


    @PostMapping("/ignore/uploadFile")
    public FileObj uploadHandout(MultipartFile file){
        FileObj fileObj = new FileObj();
        try{
            fileObj.setSuccess(true);
            fileObj.setPath(QiniuUtil.uploadFile(file, Constant.Upload.UPLOAD_WRITING));
        }catch (Exception e){
            fileObj.setSuccess(false);
            fileObj.setPath("文件格式错误");
        }
        return fileObj;
    }


    //判断组织架构树部门是否被修改
    @GetMapping("/look/getdepartmentUpdate/{id}")
    @ApiModelProperty(value = "判断组织架构树部门是否被修改",notes = "返回布尔型（true或false）")
    public ResponseJson getdepartmentUpdate(
            @ApiParam(value = "传递发文id",required = true)
            @PathVariable String id
    ){
        Boolean flag = writingService.getdepartmentUpdate(id);
        return new ResponseJson(flag);
    }

}
