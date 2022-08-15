package com.yice.edu.cn.xw.controller.dj.information;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.pojo.xw.dj.information.XwDjInformation;
import com.yice.edu.cn.xw.service.dj.information.XwDjInformationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/xwDjInformation")
@Api(value = "/xwDjInformation",description = "党建资讯模块")
public class XwDjInformationController {
    @Autowired
    private XwDjInformationService xwDjInformationService;

    @GetMapping("/findXwDjInformationById/{id}")
    @ApiOperation(value = "通过id查找党建资讯", notes = "返回党建资讯对象")
    public XwDjInformation findXwDjInformationById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return xwDjInformationService.findXwDjInformationById(id);
    }

    @PostMapping("/saveXwDjInformation")
    @ApiOperation(value = "保存党建资讯", notes = "返回党建资讯对象")
    public XwDjInformation saveXwDjInformation(
            @ApiParam(value = "党建资讯对象", required = true)
            @RequestBody XwDjInformation xwDjInformation
    ) {
        xwDjInformation.setInformationTitle(StrUtil.trimToNull(xwDjInformation.getInformationTitle()));
        xwDjInformation.setInformationContent(StrUtil.trimToNull(xwDjInformation.getInformationContent()));
        xwDjInformationService.createInformation(xwDjInformation);
        return xwDjInformation;
    }

    @PostMapping("/editXwDjInformation")
    @ApiOperation(value = "编辑党建资讯", notes = "党建资讯对象必传")
    public void editXwDjInformation(
            @ApiParam(value = "党建资讯对象,对象属性不为空则修改", required = true)
            @RequestBody XwDjInformation xwDjInformation) {
        xwDjInformation.setCreateDate(DateUtil.now());
        xwDjInformationService.editXwDjInformation(xwDjInformation);
    }

    @PostMapping("/findXwDjInformationListByCondition")
    @ApiOperation(value = "根据条件查找党建资讯列表", notes = "返回党建资讯列表")
    public List<XwDjInformation> findXwDjInformationListByCondition(
            @ApiParam(value = "党建资讯对象")
            @RequestBody XwDjInformation xwDjInformation){
        String title = StringUtils.isNotBlank(xwDjInformation.getInformationTitle()) ? "%" + xwDjInformation.getInformationTitle().trim() + "%" : null;
        xwDjInformation.setInformationTitle(title);
        return xwDjInformationService.findXwDjInformationListByCondition(xwDjInformation);
    }
    @PostMapping("/findXwDjInformationCountByCondition")
    @ApiOperation(value = "根据条件查找党建资讯列表个数", notes = "返回党建资讯总个数")
    public long findXwDjInformationCountByCondition(
            @ApiParam(value = "党建资讯对象")
            @RequestBody XwDjInformation xwDjInformation){
        String title = StringUtils.isNotBlank(xwDjInformation.getInformationTitle()) ? "%" + xwDjInformation.getInformationTitle().trim() + "%" : null;
        xwDjInformation.setInformationTitle(title);
        return xwDjInformationService.findXwDjInformationCountByCondition(xwDjInformation);
    }

    @PostMapping("/updateXwDjInformation")
    @ApiOperation(value = "修改党建资讯", notes = "党建资讯对象必传")
    public void updateXwDjInformation(
            @ApiParam(value = "党建资讯对象,对象属性不为空则修改", required = true)
            @RequestBody XwDjInformation xwDjInformation){
        xwDjInformationService.updateXwDjInformation(xwDjInformation);
    }

    @GetMapping("/deleteXwDjInformation/{id}")
    @ApiOperation(value = "通过id删除党建资讯")
    public void deleteXwDjInformation(
            @ApiParam(value = "党建资讯对象", required = true)
            @PathVariable String id){
        xwDjInformationService.deleteXwDjInformation(id);
    }
    @PostMapping("/deleteXwDjInformationByCondition")
    @ApiOperation(value = "根据条件删除党建资讯")
    public void deleteXwDjInformationByCondition(
            @ApiParam(value = "党建资讯对象")
            @RequestBody XwDjInformation xwDjInformation){
        xwDjInformationService.deleteXwDjInformationByCondition(xwDjInformation);
    }
    @PostMapping("/findOneXwDjInformationByCondition")
    @ApiOperation(value = "根据条件查找单个党建资讯,结果必须为单条数据", notes = "返回单个党建资讯,没有时为空")
    public XwDjInformation findOneXwDjInformationByCondition(
            @ApiParam(value = "党建资讯对象")
            @RequestBody XwDjInformation xwDjInformation){
        return xwDjInformationService.findOneXwDjInformationByCondition(xwDjInformation);
    }

    @GetMapping("/selectXwDjInformationById/{id}")
    @ApiOperation(value = "通过id查找党建资讯(包括附件)", notes = "返回党建资讯对象(包括附件)")
    public XwDjInformation selectXwDjInformationById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return xwDjInformationService.selectXwDjInformationById(id);
    }



}
