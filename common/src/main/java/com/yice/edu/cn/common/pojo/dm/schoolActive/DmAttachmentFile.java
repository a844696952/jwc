package com.yice.edu.cn.common.pojo.dm.schoolActive;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Data
@ApiModel("电子班牌附件表")
public class DmAttachmentFile{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("文件名称")
    private String fileName;
    @ApiModelProperty("文件路径")
    private String filePath;
    @ApiModelProperty("文件类型")
    private Integer fileType;
    @ApiModelProperty("引用Id")
    private String referenceId;
    @ApiModelProperty("文件描述")
    private String fileDesc;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("学校Id")
    private String schoolId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DmAttachmentFile that = (DmAttachmentFile) o;

        return filePath != null ? filePath.equals(that.filePath) : that.filePath == null;
    }

    @Override
    public int hashCode() {
        return filePath != null ? filePath.hashCode() : 0;
    }
}
