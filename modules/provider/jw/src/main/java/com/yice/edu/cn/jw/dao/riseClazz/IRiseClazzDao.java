package com.yice.edu.cn.jw.dao.riseClazz;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import com.yice.edu.cn.common.pojo.dm.classCard.DmDeleteData;
import com.yice.edu.cn.common.pojo.dm.honourRoll.history.DmHonourRollHistory;
import com.yice.edu.cn.common.pojo.dm.honourRoll.history.DmHonourRollStudentHistory;

/**
 * 升班相关联的操作
 *
 */
@Mapper
public interface IRiseClazzDao {
	/**
	 * 根据学校id解除该学校所有的班牌和班级的绑定关系
	 * @param dmClassCard
	 */
    void clearDmAndClazzRelateBySchoolId(DmClassCard dmClassCard);
    /**
          * 根据班牌id修改所绑定的班级id
     * @param dmClassCard
     */
    void updateDmClazzRelateByDmId(DmClassCard dmClassCard);
    
    /**
	     * 根据班级id删除光荣榜的数据
	* @param dmDeleteData
	*/
    void deleteDmHonourRollByClassId(DmDeleteData dmDeleteData);
    
    /**
	     * 根据班级id删除光荣榜和学生关联表的数据
	* @param dmDeleteData
	*/
    void deleteDmHonourRollStudentByClassId(DmDeleteData dmDeleteData);
    
    List<DmHonourRollStudentHistory> selectHonourRollStudentByClazzIdList(DmDeleteData dmDeleteData);
    
    List<DmHonourRollHistory> selectHonourRollByClazzIdList(DmDeleteData dmDeleteData);
}
