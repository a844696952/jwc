package com.yice.edu.cn.common.pojo.jy.knowledge;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 知识点树
 * @author Administrator
 *
 */
@Data
public class KnowkedgeTreeNodeViewVo {
	@ApiModelProperty(value = "id",dataType = "String")
    private String id;
	@ApiModelProperty(value = "节点名称",dataType = "String")
    private String name;
	@ApiModelProperty(value = "子节点集合",dataType = "List")
    private List<KnowkedgeTreeNodeViewVo> knowledgeArr;
    @ApiModelProperty(value = "是否是叶子结点(1.叶子结点 2.非叶子结点)",dataType = "String")
    private Boolean leaf;
    @ApiModelProperty(value = "当前树级别",dataType = "String")
    private String level;
}
