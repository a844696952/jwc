package com.yice.edu.cn.jy.dao.prepareLessons;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yice.edu.cn.common.pojo.jy.prepareLessons.TextbookSetting;

@Mapper
public interface ITextbookSettingDao {



    List<TextbookSetting> findTextbookSettingByTeacherId(@Param("teachId") String teachId);

    int saveTextbookSetting(TextbookSetting textbookSetting);

    int deleteTextbookSetting(@Param("id") String id);

	TextbookSetting findLastSettingbyTeacherId(@Param("teacherId") String teacherId);

	long findSettingCountbyTextbookId(@Param("textbookId") String textbookId,@Param("teacherId") String teacherId);
	
	int updateTextbookSetting(TextbookSetting textbookSetting);
}
