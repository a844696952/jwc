package com.yice.edu.cn.xw.controller.dj.partyMemberPhoto;


import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.common.pojo.validateClass.GroupTwo;
import com.yice.edu.cn.common.pojo.xw.dj.partyMerberPhoto.XwDjPhoto;
import com.yice.edu.cn.xw.service.dj.partyMemberPhoto.XwDjPhotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/xwDjPhoto")
@Api(value = "/xwDjPhoto",description = "党建相册表模块")
public class XwDjPhotoController {
    @Autowired
    private XwDjPhotoService xwDjPhotoService;


    @GetMapping("/findXwDjPhotoById/{id}")
    @ApiOperation(value = "通过id查找党建相册表", notes = "返回党建相册表对象")
    public XwDjPhoto findXwDjPhotoById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return xwDjPhotoService.findXwDjPhotoById(id);
    }

    @PostMapping("/saveXwDjPhoto")
    @ApiOperation(value = "保存党建相册表", notes = "返回党建相册表对象")
    public XwDjPhoto saveXwDjPhoto(
            @ApiParam(value = "党建相册表对象", required = true)
            @RequestBody  @Validated(value = GroupOne.class)  XwDjPhoto xwDjPhoto){
        xwDjPhotoService.saveXwDjPhoto(xwDjPhoto);
        return xwDjPhoto;
    }

    @PostMapping("/findXwDjPhotoListByCondition")
    @ApiOperation(value = "根据条件查找党建相册表列表", notes = "返回党建相册表列表")
    public List<XwDjPhoto> findXwDjPhotoListByCondition(
            @ApiParam(value = "党建相册表对象")
            @RequestBody XwDjPhoto xwDjPhoto){
        return xwDjPhotoService.findXwDjPhotoListByCondition(xwDjPhoto);
    }
    @PostMapping("/findXwDjPhotoCountByCondition")
    @ApiOperation(value = "根据条件查找党建相册表列表个数", notes = "返回党建相册表总个数")
    public long findXwDjPhotoCountByCondition(
            @ApiParam(value = "党建相册表对象")
            @RequestBody XwDjPhoto xwDjPhoto){
        return xwDjPhotoService.findXwDjPhotoCountByCondition(xwDjPhoto);
    }

    @PostMapping("/updateXwDjPhoto")
    @ApiOperation(value = "修改党建相册表", notes = "党建相册表对象必传")
    public void updateXwDjPhoto(
            @ApiParam(value = "党建相册表对象,对象属性不为空则修改", required = true)
            @RequestBody  @Validated(value = GroupTwo.class) XwDjPhoto xwDjPhoto){
        xwDjPhotoService.updateXwDjPhoto(xwDjPhoto);
    }

    @GetMapping("/deleteXwDjPhoto/{id}")
    @ApiOperation(value = "通过id删除党建相册表")
    public void deleteXwDjPhoto(
            @ApiParam(value = "党建相册表对象", required = true)
            @PathVariable String id){
        xwDjPhotoService.deleteXwDjPhoto(id);
    }
    @PostMapping("/deleteXwDjPhotoByCondition")
    @ApiOperation(value = "根据条件删除党建相册表")
    public void deleteXwDjPhotoByCondition(
            @ApiParam(value = "党建相册表对象")
            @RequestBody XwDjPhoto xwDjPhoto){
        xwDjPhotoService.deleteXwDjPhotoByCondition(xwDjPhoto);
    }

    @PostMapping("/findOneXwDjPhotoByCondition")
    @ApiOperation(value = "根据条件查找单个党建相册表,结果必须为单条数据", notes = "返回单个党建相册表,没有时为空")
    public XwDjPhoto findOneXwDjPhotoByCondition(
            @ApiParam(value = "党建相册表对象")
            @RequestBody XwDjPhoto xwDjPhoto){
        return xwDjPhotoService.findOneXwDjPhotoByCondition(xwDjPhoto);
    }

}
