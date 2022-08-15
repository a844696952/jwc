package com.yice.edu.cn.common.pojo.dm.kq;


import com.yice.edu.cn.common.pojo.jw.student.Student;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 受保护的学生信息
 * @author dengfengfeng
 *
 */
@ApiModel(description="受保护的学生信息")
@Data
public class ProtectedStudent implements Serializable {

	private static final long serialVersionUID = 6719451404807472331L;

	@ApiModelProperty(value="学生id",dataType="String")
	private String studentId;
	
	@ApiModelProperty(value="学生姓名",dataType="String")
	private String name;
	
	@ApiModelProperty(value="学生性别",dataType="String")
	private String sex;
	
	@ApiModelProperty(value="是否已打卡",dataType="Boolean")
	private Boolean hasCheckIn;
	
	@ApiModelProperty(value="ic卡号",dataType="String")
	private String iccard;
	
	@ApiModelProperty(value="学校id",dataType="String")
	private String schoolId;
	
	@ApiModelProperty(value="年级id",dataType="String")
	private String gradeId;
	
	@ApiModelProperty(value="班级id",dataType="String")
	private String classId;

	@ApiModelProperty(value="头像",dataType="String")
	private String head;
	
	@ApiModelProperty(value="学籍号",dataType="String")
	private String studentCode;

	@ApiModelProperty(value="人脸id",dataType="String")
	private String faceId;

	@ApiModelProperty(value="打卡状态(1 缺卡 2 迟到 3 早退 4无需打卡 5正常 非5异常)",dataType="String")
	@Deprecated
	private String kqStatus;

	@ApiModelProperty(value="打卡时间",dataType="String")
	@Deprecated
	private String time;

	@ApiModelProperty(value = "开启状态 0-默认状态 1-未到 2--迟到 3--已到 4--请假 5--法定节假日 6--休息日")
	private Integer kqState;

	@ApiModelProperty(value = "是否是异常数据 0--是  1 不是")
	private Integer aberrant;

	@ApiModelProperty(value = "考勤结束计算时间")
	private String kqEndDate;

	@ApiModelProperty(value = "考勤开始计算时间")
	private String kqBeginDate;

    @ApiModelProperty(value = "请假开始时间")
	@Deprecated
	private String stuLeaveBeginTime;

    @ApiModelProperty(value = "请假结束时间")
	@Deprecated
	private String stuLeaveEndTime;

	@ApiModelProperty(value = "记录日期")
    private String recordDate;

	@ApiModelProperty(value = "业务类型 1--未签到 2--已签到 ")
	private Integer bussType;

	@ApiModelProperty(value = "学生考勤信息集合")
	private List<EccKqRecordInfo> kqRecordInfos;

	@ApiModelProperty(value = "学生请假信息集合")
	private List<EccKqStuLeaveInfo> stuLeaveInfos;

	@ApiModelProperty(value = "年级（供显示用）格式例：高一（1）班")
	private String gradeString;

	@ApiModelProperty(value = "年级")
	private String gradeName;

	@ApiModelProperty(value = "班级号")
	private String classesNumber;

	public ProtectedStudent(){

	}
	public ProtectedStudent(Student student){
		this.head = student.getImgUrl();
		this.iccard = student.getCardNumber();
		this.name = student.getName();
		this.schoolId = student.getSchoolId();
		this.sex = student.getSex();
		this.studentId = student.getId();
		this.gradeId = student.getGradeId();
		this.classId = student.getClassesId();
		this.gradeName = student.getGradeName();
		this.classesNumber = student.getClassesNumber();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProtectedStudent other = (ProtectedStudent) obj;
		if (iccard == null) {
			if (other.iccard != null)
				return false;
		} else if (!iccard.equals(other.iccard))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (schoolId == null) {
			if (other.schoolId != null)
				return false;
		} else if (!schoolId.equals(other.schoolId))
			return false;
		if (sex == null) {
			if (other.sex != null)
				return false;
		} else if (!sex.equals(other.sex))
			return false;
		if (studentId == null) {
			if (other.studentId != null)
				return false;
		} else if (!studentId.equals(other.studentId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((iccard == null) ? 0 : iccard.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((schoolId == null) ? 0 : schoolId.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		result = prime * result + ((studentId == null) ? 0 : studentId.hashCode());
		return result;
	}
	
	
	
}
