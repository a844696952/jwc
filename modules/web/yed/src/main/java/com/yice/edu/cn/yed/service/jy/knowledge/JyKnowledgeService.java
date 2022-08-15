package com.yice.edu.cn.yed.service.jy.knowledge;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jy.knowledge.JyKnowledge;
import com.yice.edu.cn.common.pojo.jy.knowledge.UploadKnowledgeAndGradeMapVo;
import com.yice.edu.cn.common.pojo.jy.knowledge.UploadKnowledgeVo;
import com.yice.edu.cn.yed.feignClient.general.dd.DdFeign;
import com.yice.edu.cn.yed.feignClient.jy.knowledge.JyKnowledgeFeign;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;

@Service
public class JyKnowledgeService {
    @Autowired
    private JyKnowledgeFeign jyKnowledgeFeign;
    @Autowired
    private DdFeign ddFeign;

    public JyKnowledge findJyKnowledgeById(String id) {
        return jyKnowledgeFeign.findJyKnowledgeById(id);
    }

    public JyKnowledge saveJyKnowledge(JyKnowledge jyKnowledge) {
        return jyKnowledgeFeign.saveJyKnowledge(jyKnowledge);
    }

    public List<JyKnowledge> findJyKnowledgeListByCondition(JyKnowledge jyKnowledge) {
        return jyKnowledgeFeign.findJyKnowledgeListByCondition(jyKnowledge);
    }

    public JyKnowledge findOneJyKnowledgeByCondition(JyKnowledge jyKnowledge) {
        return jyKnowledgeFeign.findOneJyKnowledgeByCondition(jyKnowledge);
    }

    public long findJyKnowledgeCountByCondition(JyKnowledge jyKnowledge) {
        return jyKnowledgeFeign.findJyKnowledgeCountByCondition(jyKnowledge);
    }

    public void updateJyKnowledge(JyKnowledge jyKnowledge) {
        jyKnowledgeFeign.updateJyKnowledge(jyKnowledge);
    }

    public void deleteJyKnowledge(String id) {
        jyKnowledgeFeign.deleteJyKnowledge(id);
    }

    public void deleteJyKnowledgeByCondition(JyKnowledge jyKnowledge) {
        jyKnowledgeFeign.deleteJyKnowledgeByCondition(jyKnowledge);
    }
    
    public void deleteLogicById(JyKnowledge jyKnowledge) {
        jyKnowledgeFeign.deleteLogicById(jyKnowledge);
    }
    
   public Map<String,Object> uploadJyKnowledge(MultipartFile file) throws Exception{
       Map<String,Object> result = new HashMap<>();
       ImportParams params = new ImportParams();
       params.setTitleRows(0);
       params.setHeadRows(1);
      InputStream is = file.getInputStream();
      
      List<UploadKnowledgeVo> list = ExcelImportUtil.importExcel(is,
           		UploadKnowledgeVo.class, params);
      
      //校验
      for(UploadKnowledgeVo vo:list) {
    	  if(StringUtils.isEmpty(vo.getSubjectName()) || StringUtils.isEmpty(vo.getKnowledgeF()) || 
    			  StringUtils.isEmpty(vo.getKnowledgeS()) || StringUtils.isEmpty(vo.getKnowledgeT())
    			  || StringUtils.isEmpty(vo.getGradeName())) {
    		  result.put("success", false);
    		  result.put("msg", "行字段值不能为空!");
    		  return result;
    	  }
      }
      
      UploadKnowledgeAndGradeMapVo vo  = new UploadKnowledgeAndGradeMapVo();
      vo.setJyKnowledgesVoList(list);
      Dd dd = new Dd();
      dd.setTypeId(Constant.DD_TYPE.GRADE);
      List<Dd> ddList = ddFeign.findDdListByCondition(dd);
      
      Map<String,String> ddListMap = new HashMap<String,String>();
      ddList.forEach(obj->{
    	  ddListMap.put(obj.getName(), obj.getId());
      });
      vo.setGradeMap(ddListMap);
      jyKnowledgeFeign.uploadJyKnowledge(vo);
      result.put("success", true);
       return result;
   }
}
