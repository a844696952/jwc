package com.yice.edu.cn.common.pojo.jw.timetable.GA;


import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;

import lombok.Data;

@Data
public class ClassesBo{

    private int id;
    
    //11-22
    private String gradeId;
    
    private String gradeName;
    
    //n<=20
    private int classNumb;
    
    //人数
    private int number;
    
    private String jwClassId;
    
    
    public ClassesBo(JwClasses jwClass,int id,int number) {
    	this.id = id;
		this.gradeId = jwClass.getGradeId();
		this.gradeName = jwClass.getGradeName();
		this.classNumb = Integer.parseInt(jwClass.getNumber());
		this.number = number;
		this.jwClassId = jwClass.getId();
    }

	public ClassesBo(int id, String gradeId, String gradeName, int classNumb, int number, String jwClassId) {
		this.id = id;
		this.gradeId = gradeId;
		this.gradeName = gradeName;
		this.classNumb = classNumb;
		this.number = number;
		this.jwClassId = jwClassId;
	}


	public ClassesBo() {
		// TODO Auto-generated constructor stub
	}
    
    
}
