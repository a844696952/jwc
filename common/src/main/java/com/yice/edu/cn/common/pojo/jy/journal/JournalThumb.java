package com.yice.edu.cn.common.pojo.jy.journal;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class JournalThumb {
    private String id;
    @Indexed
    private String journalSqId;
    @Indexed
    private String userId;//有可能是教师id或者是学生id
}
