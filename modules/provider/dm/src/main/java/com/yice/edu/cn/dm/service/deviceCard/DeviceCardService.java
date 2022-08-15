package com.yice.edu.cn.dm.service.deviceCard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.dm.feignClient.jw.student.StudentFeign;


/**
 * 
* @ClassName: DeviceCardService  
* @Description: 设备卡绑定service 
* @author xuchang  
* @date 2018年9月3日
 */
@Service
public class DeviceCardService {
	
	
	@Autowired
	private StudentFeign stuFeign;
	
	/**
	 * 
	* @Title: updateCardNumber  
	* @Description: 更新IC卡号  
	* @param @param Student stu  
	* @return ResponseJson    返回类型  
	* @throws
	 */
    public ResponseJson updateCardNumber(Student stu){
    	Student con=new Student();
    	con.setCardNumber(stu.getCardNumber());
    	long count=stuFeign.findStudentCountByCondition(con);
    	if(count>0) {
    		return new ResponseJson(false, "该卡已存在");
    	}
    	con.setId(stu.getId());
    	stuFeign.updateStudent(con);
    	return new ResponseJson(true, "更新成功");
    }
    
    public void unbundle(Student stu) {
    	stuFeign.updateStudent(stu);
    }
    
    public Student findStudentbyId(String id){
    	return stuFeign.findStudentById(id);
    }


    public ResponseJson findStudentListByCondition(Student student){
    	Pager p=student.getPager();
    	p.setIncludes("id","name","gradeName","gradeId","cardNumber","classesId","studentCode");
    	student.setPager(p);
    	long count = stuFeign.findStudentCountByCondition(student);
    	List<Student> list = stuFeign.findStudentListByCondition(student);
        return new ResponseJson(list, count);
    }
	
	
}
