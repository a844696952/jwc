package com.yice.edu.cn.common.pojo.xw.kq;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@ApiModel("考勤打卡原始记录表")
public class KqOriginalData extends CurSchoolYear {

    @ApiModelProperty("原始记录表id")
    private String id;
    @ApiModelProperty("学生id")
    private String studentId;
    @ApiModelProperty("用户id")
    private String userId;
    @ApiModelProperty("学生姓名")
    private String name;
    @ApiModelProperty(value = "人员类型,-1陌生人,0黑名单,1教师,2走读学生,3职工,4住宿生,5访客,6,云班牌", dataType = "String")
    private String userType;
    @ApiModelProperty("班级id")
    private String classId;
    @ApiModelProperty("班号")
    private String classesNumber;
    @ApiModelProperty("年级id")
    private String gradeId;
    @ApiModelProperty("年级名称")
    private String gradeName;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("学生头像(匹配用)")
    private String prsnAvtrUrlAddr;
    @ApiModelProperty("中移识别记录编号")
    private String reqId;
    @ApiModelProperty("入离校状态（0入1出-1不区分）")
    private String derectionFlag;
    @ApiModelProperty("抓拍时间")
    private String capturedTime;
    @ApiModelProperty("抓拍照片")
    private String capturedImage;
    @ApiModelProperty("抓拍设备编号")
    private String deviceNo;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty(value = "座位号")
    private Integer seatNumber;
    @ApiModelProperty("设备类型，1摄像头2门禁机3普通用户手机")
    private String deviceType;
    @ApiModelProperty("设备厂家，1中移，2海康，3普通用户手机")
    private String deviceFactory;
    @ApiModelProperty("访客身份证号")
    private String visiIdCard;
    @ApiModelProperty("访问类型 0家长 1陌生人")
    private String visitorType;
    @ApiModelProperty("访客描述")
    private String visitorDesc;
    @ApiModelProperty("设备分组名")
    private String groupName;
    @ApiModelProperty("设备分组id")
    private String groupId;
    @ApiModelProperty("设备分组类型")
    private List<String> groupType;
    @ApiModelProperty(value = "设备名称", dataType = "String")
    private String deviceName;
    @ApiModelProperty(value = "passStatus出入校状态，1正常,2请假,3异常", dataType = "String")
    private String passStatus;
    @ApiModelProperty("用户工号")
    private String worknumber;
    @ApiModelProperty(value = "抓拍消息类型 1识别记录 -1陌生人3查验记录 0黑名单", dataType = "String")
    private String capturedMessageType;
    @ApiModelProperty("打卡状态(1 缺卡 2 迟到 3 早退 4无需打卡 5正常 非5异常)")
    private String punchStatus;
    @ApiModelProperty("时间范围查询，[startTime,endTime],未设置某一时间设置为0")
    private String[] timeZone;
    @ApiModelProperty("是否即时返回 0 即时返回 ")
    private String InTime;
    @ApiModelProperty("处理描述")
    private String description;
    @ApiModelProperty("处理人")
    private String resolverId;
    @ApiModelProperty("处理人姓名")
    private String resolverName;
    @ApiModelProperty("处理人结果 0未处理 1已处理")
    private String resolveStatus;
    @ApiModelProperty("分组为宿舍类型时保存宿舍楼id")
    private String  dormitoryId;
    @ApiModelProperty("分组为宿舍类型时保存宿舍楼Name")
    private String dormitoryName;
    @ApiModelProperty("设备位置")
    private String deviceLocation;
    @ApiModelProperty("人员描述")
    private String userDesc;
    @ApiModelProperty("人员性别")
    private String sex;
}
