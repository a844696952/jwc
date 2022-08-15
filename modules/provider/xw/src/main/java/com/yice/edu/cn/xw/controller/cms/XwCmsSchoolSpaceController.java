package com.yice.edu.cn.xw.controller.cms;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsSchoolSpace;
import com.yice.edu.cn.xw.service.cms.XwCmsSchoolSpaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/xwCmsSchoolSpace")
@Api(value = "/xwCmsSchoolSpace",description = "CMS学校空间表模块")
public class XwCmsSchoolSpaceController {
    @Autowired
    private XwCmsSchoolSpaceService xwCmsSchoolSpaceService;

    @GetMapping("/findXwCmsSchoolSpaceById/{id}")
    @ApiOperation(value = "通过id查找CMS学校空间表", notes = "返回CMS学校空间表对象")
    public XwCmsSchoolSpace findXwCmsSchoolSpaceById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return xwCmsSchoolSpaceService.findXwCmsSchoolSpaceById(id);
    }

    @PostMapping("/saveXwCmsSchoolSpace")
    @ApiOperation(value = "保存CMS学校空间表", notes = "返回CMS学校空间表对象")
    public XwCmsSchoolSpace saveXwCmsSchoolSpace(
            @ApiParam(value = "CMS学校空间表对象", required = true)
            @RequestBody XwCmsSchoolSpace xwCmsSchoolSpace){
        xwCmsSchoolSpaceService.saveXwCmsSchoolSpace(xwCmsSchoolSpace);
        return xwCmsSchoolSpace;
    }

    @PostMapping("/findXwCmsSchoolSpaceListByCondition")
    @ApiOperation(value = "根据条件查找CMS学校空间表列表", notes = "返回CMS学校空间表列表")
    public List<XwCmsSchoolSpace> findXwCmsSchoolSpaceListByCondition(
            @ApiParam(value = "CMS学校空间表对象")
            @RequestBody XwCmsSchoolSpace xwCmsSchoolSpace){
        return xwCmsSchoolSpaceService.findXwCmsSchoolSpaceListByCondition(xwCmsSchoolSpace);
    }
    @PostMapping("/findXwCmsSchoolSpaceCountByCondition")
    @ApiOperation(value = "根据条件查找CMS学校空间表列表个数", notes = "返回CMS学校空间表总个数")
    public long findXwCmsSchoolSpaceCountByCondition(
            @ApiParam(value = "CMS学校空间表对象")
            @RequestBody XwCmsSchoolSpace xwCmsSchoolSpace){
        return xwCmsSchoolSpaceService.findXwCmsSchoolSpaceCountByCondition(xwCmsSchoolSpace);
    }

    @PostMapping("/updateXwCmsSchoolSpace")
    @ApiOperation(value = "修改CMS学校空间表", notes = "CMS学校空间表对象必传")
    public void updateXwCmsSchoolSpace(
            @ApiParam(value = "CMS学校空间表对象,对象属性不为空则修改", required = true)
            @RequestBody XwCmsSchoolSpace xwCmsSchoolSpace){
        xwCmsSchoolSpaceService.updateXwCmsSchoolSpace(xwCmsSchoolSpace);
    }

    @PostMapping("/updateXwCmsSchoolSpaceBySchoolId")
    @ApiOperation(value = "修改CMS学校空间表根据学校ID", notes = "CMS学校空间表对象必传")
    public void updateXwCmsSchoolSpaceBySchoolId(
            @ApiParam(value = "CMS学校空间表对象,对象属性不为空则修改", required = true)
            @RequestBody XwCmsSchoolSpace xwCmsSchoolSpace){
        xwCmsSchoolSpace.setOperateTime(DateUtil.format(DateTime.now(),"yyyy-MM-dd HH:mm:ss"));
        xwCmsSchoolSpaceService.updateXwCmsSchoolSpaceBySchoolId(xwCmsSchoolSpace);
    }

    @GetMapping("/deleteXwCmsSchoolSpace/{id}")
    @ApiOperation(value = "通过id删除CMS学校空间表")
    public void deleteXwCmsSchoolSpace(
            @ApiParam(value = "CMS学校空间表对象", required = true)
            @PathVariable String id){
        xwCmsSchoolSpaceService.deleteXwCmsSchoolSpace(id);
    }
    @PostMapping("/deleteXwCmsSchoolSpaceByCondition")
    @ApiOperation(value = "根据条件删除CMS学校空间表")
    public void deleteXwCmsSchoolSpaceByCondition(
            @ApiParam(value = "CMS学校空间表对象")
            @RequestBody XwCmsSchoolSpace xwCmsSchoolSpace){
        xwCmsSchoolSpaceService.deleteXwCmsSchoolSpaceByCondition(xwCmsSchoolSpace);
    }
    @PostMapping("/findOneXwCmsSchoolSpaceByCondition")
    @ApiOperation(value = "根据条件查找单个CMS学校空间表,结果必须为单条数据", notes = "返回单个CMS学校空间表,没有时为空")
    public XwCmsSchoolSpace findOneXwCmsSchoolSpaceByCondition(
            @ApiParam(value = "CMS学校空间表对象")
            @RequestBody XwCmsSchoolSpace xwCmsSchoolSpace){
        return xwCmsSchoolSpaceService.findOneXwCmsSchoolSpaceByCondition(xwCmsSchoolSpace);
    }

    @PostMapping("/findXwCmsSchoolSpaceByDomain/{secondDomain}")
    @ApiOperation(value = "检测当前域名是否存在", notes = "返回当前域名信息")
    public XwCmsSchoolSpace findXwCmsSchoolSpaceByDomain(
            @ApiParam(value = "检测当前域名是否存在")
            @PathVariable("secondDomain") String secondDomain){

        return xwCmsSchoolSpaceService.findXwCmsSchoolSpaceByDomain(secondDomain);
    }

    @PostMapping("/findProvinceList")
    @ApiOperation(value = "获取当前省分列表", notes = "返回省份列表")
    public List<XwCmsSchoolSpace> findProvinceList(){
        return xwCmsSchoolSpaceService.findProvinceList();
    }


}
