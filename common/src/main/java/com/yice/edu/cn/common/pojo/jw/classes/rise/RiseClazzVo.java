package com.yice.edu.cn.common.pojo.jw.classes.rise;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherPost;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "升班参数",description="")
@Data
public class RiseClazzVo {
	@ApiModelProperty(value = "最低年级的班级数量",dataType = "Integer")
	private List<JwClasses> lowestClazzList;
	@ApiModelProperty(value = "电子班牌账号绑定集合",dataType = "List")
    private List<ElectronicClazzCard> ElectronicClazzCardList;
	@ApiModelProperty(value = "年段长设置",dataType = "List")
	private List<TeacherPost> gradeMasterList;

	private String creatorId;
	private String creatorName;
	private String schoolId;
}
