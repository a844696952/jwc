package com.yice.edu.cn.common.pojo.dm.msg;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


/**
 * @author dengfengfeng
 */
@Data
public class ParentReceiveMsg extends ReceiveMsg {

    @ApiModelProperty("家长id")
    private String parentId;

    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    protected Pager pager;

    public ParentReceiveMsg() {
    }

    public ParentReceiveMsg(DmMsg msg){
        super(msg);
        this.parentId = msg.getParentId();
    }

}
