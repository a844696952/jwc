package com.yice.edu.cn.common.pojo.oa.processCopy;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.oa.processBusinessData.ProcessBusinessData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
*
*流程抄送-vo对象
*
*/
@Data
public class ProcessCopyVo {
    private String id;
    private String teacherId;
    private String type;
    private String initiator;
    private String createTime;
    private Boolean looked;
    private String processBusinessDataId;
    private List<ProcessBusinessData> processBusinessData;
}
