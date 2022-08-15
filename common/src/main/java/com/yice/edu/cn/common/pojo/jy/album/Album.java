package com.yice.edu.cn.common.pojo.jy.album;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@Document
public class Album {
    private String id;
    private String title;//名称
    @Indexed
    private String userId;//有可能是教师id或者是学生id
    private String path;//图片路径
    @Indexed
    private String parentId;//父id,如果顶级节点则为-1
    private String type;//目录还是图片,0是目录,1是图片
    //分页排序等
    @Transient
    @NotNull
    private Pager pager;
}
