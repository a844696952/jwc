package com.yice.edu.cn.jy.service.prepareLessons;

import cn.hutool.core.collection.CollectionUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItem;
import com.yice.edu.cn.jy.service.subjectSource.MaterialItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class ChapterMenuService {
		
	@Autowired
    private MaterialItemService materialItemService;

	@CreateCache(name = Constant.Redis.OSP_CHAPTER_MENU_CANCAT, expire = 2, timeUnit= TimeUnit.HOURS)
	private Cache<String, List<MaterialItem>> materialItemCache;


	/**
	 * 查寻所有章节的拼接
	 * */
	public List<MaterialItem> findAllMaterialItemByMaterialIdCancat(String materialId){
		List<MaterialItem> materialItemsResult = materialItemCache.get(materialId);
		if(materialItemsResult==null) {
			materialItemsResult = new ArrayList<>();
			if(Objects.isNull(materialId)){
				return materialItemsResult;
			}
			MaterialItem mi=new MaterialItem();
			mi.setMaterialId(materialId);
			mi.setParentId("-1");
			List<MaterialItem> materialItems = materialItemService.findMaterialItemListByCondition(mi);
			if(CollectionUtil.isNotEmpty(materialItems)) {
				for (MaterialItem materialItem:materialItems
					 ) {
					String materName = materialItem.getName();
					List<MaterialItem> itemList = findChildrenItemByParentId(materialItem.getId());
					if(CollectionUtil.isNotEmpty(itemList)){
						for (MaterialItem materialItem1:itemList
							 ) {
							MaterialItem m = new MaterialItem();
							m.setMaterialId(materialId);
							m.setName(materName.concat(materialItem1.getName()));
							m.setId(materialItem1.getId());
							materialItemsResult.add(m);
						}
					}
				}
			}
			materialItemCache.put(materialId, materialItemsResult);
		}
		return materialItemsResult;
	}


	
    public List<MaterialItem> findAllMaterialItemByMaterialId(String materialId){
		
		List<MaterialItem> materialItemsResult=null;
		MaterialItem mi=new MaterialItem();
		mi.setMaterialId(materialId);
		List<MaterialItem> materialItems = materialItemService.findMaterialItemListByCondition(mi);

		if(materialItems!=null) {
			materialItemsResult=materialItems.stream()
				    .filter(m->m.getLeaf()==2)
				    .collect(Collectors.mapping(m->{
				    	m.setChildren(getChilren(m.getId(),materialItems));
						return m;
				    }, toList()));
		}

        return materialItemsResult;
    }
	
	
    public List<MaterialItem> findFirstItemByMaterialId(String materialId){
		
		MaterialItem mi=new MaterialItem();
		mi.setMaterialId(materialId);
		mi.setLeaf(2);
		List<MaterialItem> materialItems = materialItemService.findMaterialItemListByCondition(mi);
		return materialItems;
    }
	
    
    public List<MaterialItem> findChildrenItemByParentId( String parentId){
		MaterialItem mi=new MaterialItem();
		mi.setParentId(parentId);
		List<MaterialItem> materialItems = materialItemService.findMaterialItemListByCondition(mi);
		return materialItems;
    }
	
	
	/**
	 * 
	* @Title: getChilren  
	* @Description: 获取子节点  
	* @param parentId  父Id
	* @param materialItems 列表
	* @return List<MaterialItem>    返回类型  
	* @throws
	 */
	private static List<MaterialItem> getChilren(String parentId,List<MaterialItem> materialItems){
		
		return materialItems.stream()
				.filter(m->Objects.equals(m.getParentId(), parentId))
				.collect(Collectors.mapping(m->{
					m.setChildren(getChilren(m.getId(),materialItems));
					return m;
			    },toList()));
	}
	
	/**测试*/
	public static void main(String[] args) {
		MaterialItem a=new MaterialItem();
		a.setId("1");
		a.setLeaf(2);
		MaterialItem a1=new MaterialItem();
		a1.setId("2");
		a1.setParentId("1");
		a1.setLeaf(1);
		MaterialItem a2=new MaterialItem();
		a2.setId("3");
		a2.setParentId("1");
		a2.setLeaf(1);
		
		MaterialItem a3=new MaterialItem();
		a3.setId("4");
		a3.setParentId("2");
		a3.setLeaf(1);
		
		MaterialItem a4=new MaterialItem();
		a4.setId("5");
		a4.setParentId("4");
		a4.setLeaf(1);
		
		List<MaterialItem> as=new ArrayList<>();
		as.add(a);
		as.add(a1);
		as.add(a2);
		as.add(a3);
		as.add(a4);
		
		
		List<MaterialItem> materialItemsResult=as.stream()
			    .filter(m->m.getLeaf()==2)
			    .collect(Collectors.mapping(m->{
			    	m.setChildren(getChilren(m.getId(),as));
					return m;
			    }, toList()));
		System.out.println(materialItemsResult);



		
	}
	
}
