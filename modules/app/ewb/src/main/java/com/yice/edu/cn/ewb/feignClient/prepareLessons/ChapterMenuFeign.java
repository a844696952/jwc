package com.yice.edu.cn.ewb.feignClient.prepareLessons;

import java.util.List;



import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItem;



@FeignClient(value="jy",contextId = "chapterMenuFeign",path="/materialItem")
public interface ChapterMenuFeign {
	
	@GetMapping("/findAllMaterialItemByMaterialId/{materialId}")
    public List<MaterialItem> findAllMaterialItemByMaterialId(@PathVariable("materialId") String materialId);
	
	@GetMapping("/findFirstItemByMaterialId/{materialId}")
    public List<MaterialItem> findFirstItemByMaterialId(@PathVariable("materialId") String materialId);
	
	@GetMapping("/findChildrenItemByParentId/{parentId}")
    public List<MaterialItem> findChildrenItemByParentId(@PathVariable("parentId") String parentId);
	
}
