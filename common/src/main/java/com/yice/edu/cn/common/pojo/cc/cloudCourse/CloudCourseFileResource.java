package com.yice.edu.cn.common.pojo.cc.cloudCourse;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("云课堂上课资源表")
public class CloudCourseFileResource {

	@ApiModelProperty("主键")
	private String id;
	@ApiModelProperty("云课堂资源外键id")
	private String cloudCourseResourceId;
	@ApiModelProperty("云课堂子课程外键id")
	private String cloudSubCourseId;
	@ApiModelProperty("文件名称")
	private String name;
	@ApiModelProperty("文件地址")
	private String url;
	@ApiModelProperty("文件类型 1.视频")
	private String type;
	@ApiModelProperty("文件默认的缩略图")
	private String thumbnail;
	@ApiModelProperty("其他自定义属性")
	private String param;
	@ApiModelProperty("创建时间")
	private String createTime;
	@ApiModelProperty("学校id")
	private String schoolId;
	@ApiModelProperty("文件状态 1:上传中 2:已上传")
	private Integer status;
	@ApiModelProperty("唯一标识")
	private String flag;
	//分页排序等
	@Transient
	@NotNull(message = "pager不能为空")
	@Valid
	private Pager pager;


}
