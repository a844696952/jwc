package com.yice.edu.cn.common.pojo.jw.classes.rise;

import java.util.List;

import lombok.Data;

@Data
public class ElectronicClazzCardBatchAddVo {
	
     private List<ElectronicClazzCard> electronicClazzCardList;
     
     private String schoolId;
     
}
