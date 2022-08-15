package com.yice.edu.cn.ewb.service.prepareLessons;

import java.util.List;
import java.util.concurrent.TimeUnit;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItem;
import com.yice.edu.cn.ewb.feignClient.prepareLessons.ChapterMenuFeign;

@Service
public class ChapterMenuService {
		
	@Autowired
	private ChapterMenuFeign chapterMenuFeign;
	
	@CreateCache(name = Constant.Redis.OSP_CHAPTER_MENU, expire = 2, timeUnit=TimeUnit.HOURS)
	private Cache<String, List<MaterialItem>> materialItemCache;
	
    public List<MaterialItem> findAllMaterialItemByMaterialId(String materialId){
		List<MaterialItem> materialItemsResult=materialItemCache.get(materialId);
		if(materialItemsResult==null) {
			materialItemsResult = chapterMenuFeign.findAllMaterialItemByMaterialId(materialId);
			materialItemCache.put(materialId, materialItemsResult);
		}
        return materialItemsResult;
    }
	
	
    public List<MaterialItem> findFirstItemByMaterialId(String materialId){
		
		List<MaterialItem> materialItems = chapterMenuFeign.findFirstItemByMaterialId(materialId);
		return materialItems;
    }
	
    
    public List<MaterialItem> findChildrenItemByParentId( String parentId){
		List<MaterialItem> materialItems = chapterMenuFeign.findChildrenItemByParentId(parentId);
		return materialItems;
    }
	
	
}
