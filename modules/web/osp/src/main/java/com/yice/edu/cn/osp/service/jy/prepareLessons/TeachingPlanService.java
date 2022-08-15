package com.yice.edu.cn.osp.service.jy.prepareLessons;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.prepareLessons.TeachingPlan;
import com.yice.edu.cn.osp.feignClient.jy.prepareLessons.TeachingPlanFeign;



@Service
public class TeachingPlanService {
	
	@Autowired
	private TeachingPlanFeign teachingPlanFeign;

	public ResponseJson findTeachingPlanListByCondition(TeachingPlan teachingPlan) {

		return teachingPlanFeign.findTeachingPlanListByCondition(teachingPlan);
	}

//    public void downloadTeachingPlan(String id,String fileName, HttpServletResponse response) throws IOException {
//        ResponseEntity<byte[]> responseEntity=teachingPlanFeign.downloadTeachingPlan(id);
//        response.setCharacterEncoding("utf-8");
//        response.setContentType("application/octet-stream");
//        
//        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".docx\"");
//        response.getOutputStream().write(responseEntity.getBody());
//    }
	
	public ResponseEntity<byte[]> downloadTeachingPlan(String id) throws IOException {
      ResponseEntity<byte[]> responseEntity=teachingPlanFeign.downloadTeachingPlan(id);
      return responseEntity;
  }

	public TeachingPlan editTeachingPlanById(String id) {
		return teachingPlanFeign.editTeachingPlanById(id);
	}

	public TeachingPlan lookTeachingPlanById(String id) {
		return teachingPlanFeign.lookTeachingPlanById(id);
	}

	public ResponseJson saveTeachingPlan(TeachingPlan teachingPlan) {

		return teachingPlanFeign.saveTeachingPlan(teachingPlan);
	}

	public ResponseJson updateTeachingPlan(TeachingPlan teachingPlan) {

		return teachingPlanFeign.updateTeachingPlan(teachingPlan);
	}

	public ResponseJson deleteTeachingPlan(String id) {

		return teachingPlanFeign.deleteTeachingPlan(id);
	}


	public ResponseJson deleteLessonsFile(String id) {

		return teachingPlanFeign.deleteLessonsFile(id);
	}

    public ResponseJson findTeachingPlanList( TeachingPlan teachingPlan) {

        return teachingPlanFeign.findTeachingPlanList(teachingPlan);
    }

}
