package com.yice.edu.cn.common.pojo.mes.schoolManage.inspectRecord;

import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.mes.schoolManage.audit.MesOperateRecord;
import com.yice.edu.cn.common.pojo.mes.schoolManage.mesAttachFile.MesAttachFile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.util.List;

@Data
@ApiModel("检查记录表")
public class MesInspectRecord extends CurSchoolYear {

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("对象ID-（学生ID和班级ID）")
    private String objectId;
    @ApiModelProperty("对象名称")
    private String objectName;
    @ApiModelProperty("班级名称")
    private String className;
    @ApiModelProperty("对象类型 0--学生 1--班级")
    private Integer objectType;
    @ApiModelProperty("对象对应头像")
    private String objectImgUrl;
    @ApiModelProperty("一级制度名称")
    private String institutionParentName;
    @ApiModelProperty("二级制度id")
    private String institutionId;
    @ApiModelProperty("发生日期")
    private String happenDate;
    @ApiModelProperty("记录日期")
    private String createTime;
    @ApiModelProperty("记录人id")
    private String recordUserId;
    @ApiModelProperty("记录人名称")
    private String recordUserName;
    @ApiModelProperty("记录人类型 0--学生 1-老师")
    private Integer recordUserType;
    @ApiModelProperty("事件描述")
    private String institutionDesc;
    @ApiModelProperty("事件分值")
    private Integer institutionScore;
    @ApiModelProperty("班级id")
    private String classId;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("二级制度名称")
    private String institutionName;

    //业务字段
    private List<MesInspectRecordCondition> mesInspectRecordConditions;
    private Integer clicks;//二级制度点击次数
    private Integer sum;//二级制度总分
    private Integer count;//二级制度所在范围总数
    private String name;
    private Integer value;
    private String gradeName;//年级
    private String number;//班号
    private String info;//用户手写信息
    //二级制度主键
    @Transient
    private List<String> institutionIds;

    private Integer searchType;//0:按周查1:按月查2:按学期查
    private String beginTime;
    private String endTime;
    private String timeStatusId;
    private Integer status;
    private Integer scoreType;

    /**该条记录的状态,列表页面服务*/
    private Integer miaStatus;

    private MesOperateRecord mesOperateRecord;
    private List<MesAttachFile> mesAttachFiles;

    /**
     * 申述回复的相关内容list
     * */
    private List<MesOperateRecord> mesOperateRecords;


    /**
     * 申述标识  0 数据异常   1 不能申述(按钮置灰)   2 可申述  3可再次申述  4 不能申述（按钮不见）
     * */
    private Integer state;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MesInspectRecord that = (MesInspectRecord) o;

        return institutionParentName.equals(that.institutionParentName);
    }

    @Override
    public int hashCode() {
        return institutionParentName.hashCode();
    }
}
