package com.yice.edu.cn.common.pojo.ts.jpush;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Data
@ApiModel("推送详情")
@Document
public class PushDetail{

    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "推送接收人",dataType = "List")
    private List<Receiver> receiverList;
    @ApiModelProperty(value = "推送类型",dataType = "Integer")
    private Integer type;
    @ApiModelProperty(value = "推送内容",dataType = "Map")
    private Map<String,Object> content;
    @ApiModelProperty(value = "推送时间",dataType = "String")
    private String sendDate;
    @ApiModelProperty(value = "业务ID",dataType = "String")
    private String referenceId;

    @Transient
    @ApiModelProperty(value = "推送接收人ids",dataType = "List")
    private List<String> receiverIds;
    @ApiModelProperty(value = "推送接收人信息",dataType = "Receiver")




    @Transient
    private Receiver receiver;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    public static PushDetail.Builder newBuilder() {
        return new PushDetail.Builder();
    }
    public static class Builder {
        private PushDetail pushDetail;

        public Builder() {
            this.pushDetail = new PushDetail();
        }

        public PushDetail.Builder setId(String id) {
            this.pushDetail.id = id;
            return this;
        }

        public PushDetail.Builder setType(Integer type) {
            this.pushDetail.type = type;
            return this;
        }
        public PushDetail.Builder setReferenceId(String referenceId){
            this.pushDetail.referenceId=referenceId;
            return this;
        }
        public PushDetail.Builder setSendDate(String sendDate){
            this.pushDetail.sendDate=sendDate;
            return this;
        }

        public PushDetail.Builder setReceiverIds(List<String> receiverIds) {
            this.pushDetail.receiverIds = receiverIds;
            return this;
        }

        //还有其他字段这里添加set方法
        public PushDetail.Builder setContent(Map<String,Object> content) {
            this.pushDetail.content = content;
            return this;
        }

        public PushDetail build() {
            return this.pushDetail;
        }

    }
}
