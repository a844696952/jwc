package com.yice.edu.cn.jw.controller.oaDatasource;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.oa.common.SelComponent;
import com.yice.edu.cn.jw.service.department.DepartmentService;
import com.yice.edu.cn.jw.service.teacher.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/oaDatasource")
public class OaDatasourceController {
    @Autowired
    private DepartmentService departmentService;
    @GetMapping("/getMyDepartmentNames/{id}")
    public List<Department> getMyDepartmentNames(@PathVariable("id") String id){
        return departmentService.getMyDepartmentNames(id);
    }

    /**
              *  获取上课的时段
     * @return
     */
    @GetMapping("/getTimeInterval")
    public List<SelComponent> getTimeInterval(){
    	List<SelComponent> selComponentList = new ArrayList<SelComponent>();
    	SelComponent moriningTime = new SelComponent();
    	moriningTime.setId("1");
    	moriningTime.setName("上午");
    	selComponentList.add(moriningTime);
    	
    	SelComponent afterTime = new SelComponent();
    	afterTime.setId("2");
    	afterTime.setName("下午");
    	selComponentList.add(afterTime);
    	
        return selComponentList;
    }
    
    /**
              *  获取课程节数
     * @return
     */
    @GetMapping("/getclassHour")
    public List<SelComponent> getclassHour(){
    	List<SelComponent> selComponentList = new ArrayList<SelComponent>();
    	for(int i = 1 ;i<5;i++) {
    	  	SelComponent moriningTime = new SelComponent();
        	moriningTime.setId(String.valueOf(i));
        	moriningTime.setName(String.valueOf(i));
        	selComponentList.add(moriningTime);
    	}
    	
        return selComponentList;
    }

}
