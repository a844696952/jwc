package com.yice.edu.cn.common.pojo.xw.stuInAndOut;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.xw.kq.KqOriginalData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;

@Data
@ApiModel("学生出入校大数据对象")
public class StuInAndOutBigData {
    @ApiModelProperty(value = "全校学生总数")
    private Integer schoolAllStuNum;
    @ApiModelProperty(value = "全校请假学生总数")
    private Integer schoolAllLeaveStuNum;
    @ApiModelProperty(value = "全校离校学生总数")
    private Integer schoolAllNoInStuNum;
    @ApiModelProperty(value = "全校在校学生总数")
    private Integer schoolAllInStuNum;
//入校记录
    @ApiModelProperty(value = "当日全校所有入校记录")
    private List<KqOriginalData> allInSchoolCaptureRecord;
    @ApiModelProperty(value = "当日全校所有入校异常记录")
    private List<KqOriginalData> allInSchoolAbnormalCaptureRecord;
    @ApiModelProperty(value = "当日全校所有入校异常记录数量")
    private Long allInSchoolAbnormalCaptureRecordNum;
//出校记录
    @ApiModelProperty(value = "当日全校所有出校记录")
    private List<KqOriginalData> allOutSchoolCaptureRecord;
    @ApiModelProperty(value = "当日全校所有出校异常记录")
    private List<KqOriginalData> allOutSchoolAbnormalCaptureRecord;
    @ApiModelProperty(value = "当日全校所有出校异常记录数量")
    private Long allOutSchoolAbnormalCaptureRecordNum;
//班级详情
    @ApiModelProperty(value = "班级情况")
    private List<StuInAndOutBigDataClassDetail> classDetail;
}
