package com.yice.edu.cn.common.pojo.jy.titleQuota;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("题目额度教师使用记录表")
public class HistoryTeacherAesExtend extends HistoryTeacherAes{

    private String[] timeArr;//时间数组
}
