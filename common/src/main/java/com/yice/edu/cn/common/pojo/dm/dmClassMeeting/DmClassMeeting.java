package com.yice.edu.cn.common.pojo.dm.dmClassMeeting;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.dm.schoolActive.DmAttachmentFile;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@ApiModel("电子班牌班会表")
public class DmClassMeeting{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("班会名称")
    @NotBlank(groups = GroupOne.class,message = "名字不能为空")
    @Length(groups = GroupOne.class,min = 1,max = 30)
    private String classMeetingName;
    @ApiModelProperty("班会时间")
    @NotBlank(groups = GroupOne.class,message = "时间不能为空")
    private String classMeetingTime;
    @ApiModelProperty("班会内容")
    @NotBlank(groups = GroupOne.class,message = "内容不能为空")
    @Length(groups = GroupOne.class,min = 1,max = 200)
    private String classMeetingContent;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("班级id")
    private String classId;
    //业务字段
    //图片路径
    private String filePath;
    private String beginTime;
    private String endTime;
    //文件集合
    @Size(groups = GroupOne.class,max = 6)
    private List<DmAttachmentFile> fileList;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
