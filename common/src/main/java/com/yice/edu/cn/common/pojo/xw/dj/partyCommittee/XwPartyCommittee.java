package com.yice.edu.cn.common.pojo.xw.dj.partyCommittee;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.Pattern;
import java.util.List;


@Data
@ApiModel("党支部委员会管理，树状结构")
public class XwPartyCommittee{

    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("学校编号")
    private String schoolId;
    @ApiModelProperty("党支部委员会名称")
    @Pattern(regexp = "^[a-zA-Z_——\u4E00-\u9FA5]+[a-zA-Z_——\u4E00-\u9FA5.0-9]*$" ,groups = GroupOne.class,message = "名称不能包含特殊字符，应以字母、中文、下划线开头")
    private String name;
    @ApiModelProperty("父id")
    private String parentId;
    @ApiModelProperty("修改时间")
    private String updateTime;
    @ApiModelProperty("头像")
    private String imgUrl;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("组织结构类型 1--组织 2--人员")
    private String type;
    @ApiModelProperty("讲师编号")
    private String teacherId;
    //分页排序等
    @Transient
//    @NotNull(message = "pager不能为空")
    private Pager pager;
    //额外字段
    @Transient
    private List<XwPartyCommittee> children;
    @ApiModelProperty("父名称")
    @Transient
    private String parentName;
}
