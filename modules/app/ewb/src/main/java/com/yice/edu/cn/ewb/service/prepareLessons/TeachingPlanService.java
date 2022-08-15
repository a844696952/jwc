package com.yice.edu.cn.ewb.service.prepareLessons;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.prepareLessons.TeachingPlan;
import com.yice.edu.cn.ewb.feignClient.prepareLessons.TeachingPlanFeign;

@Service
public class TeachingPlanService {

	@Autowired
	private TeachingPlanFeign teachingPlanFeign;

	public ResponseJson findTeachingPlanListByCondition(TeachingPlan teachingPlan) {

		return teachingPlanFeign.findTeachingPlanListByCondition(teachingPlan);
	}

	public ResponseEntity<byte[]> downloadTeachingPlan(String id) throws IOException {
		ResponseEntity<byte[]> responseEntity = teachingPlanFeign.downloadTeachingPlan(id);
		return responseEntity;
	}

	public TeachingPlan lookTeachingPlanById(String id) {
		return teachingPlanFeign.lookTeachingPlanById(id);
	}

	public ResponseJson findTeachingPlanList(TeachingPlan teachingPlan) {

		return teachingPlanFeign.findTeachingPlanList(teachingPlan);
	}


	public ResponseJson findMaterialInformation(TeachingPlan teachingPlan) {

		return teachingPlanFeign.findMaterialInformation(teachingPlan);
	}

}
