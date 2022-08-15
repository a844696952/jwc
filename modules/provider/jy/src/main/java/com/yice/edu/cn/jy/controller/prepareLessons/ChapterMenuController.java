package com.yice.edu.cn.jy.controller.prepareLessons;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItem;
import com.yice.edu.cn.jy.service.prepareLessons.ChapterMenuService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
* @ClassName: ChapterMenuController  
* @Description: 加载章节菜单  
* @author xuchang  
* @date 2018年10月30日
 */
@RestController
@RequestMapping("/materialItem")
@Api(value = "/materialItem",description = "模块")
public class ChapterMenuController {
		
	@Autowired
	private ChapterMenuService chapterMenuService;
	
	
	@GetMapping("/findAllMaterialItemByMaterialId/{materialId}")
    @ApiOperation(value = "通过教材Id查找章节列表", notes = "返回对象")
    public List<MaterialItem> findAllMaterialItemByMaterialId(
            @ApiParam(value = "所属科目年级id", required = true)
            @PathVariable String materialId){
		
        return chapterMenuService.findAllMaterialItemByMaterialId(materialId);
    }
	
	@GetMapping("/findFirstItemByMaterialId/{materialId}")
    @ApiOperation(value = "通过教材Id查找第一级章节", notes = "返回对象")
    public List<MaterialItem> findFirstItemByMaterialId(
            @ApiParam(value = "所属科目年级id", required = true)
            @PathVariable String materialId){
		
		return chapterMenuService.findFirstItemByMaterialId(materialId);
    }
	
	@GetMapping("/findChildrenItemByParentId/{parentId}")
    @ApiOperation(value = "查找子章节", notes = "返回对象")
    public List<MaterialItem> findChildrenItemByParentId(
            @ApiParam(value = "父Id", required = true)
            @PathVariable String parentId){
		return chapterMenuService.findChildrenItemByParentId(parentId);
    }
	
	
}
