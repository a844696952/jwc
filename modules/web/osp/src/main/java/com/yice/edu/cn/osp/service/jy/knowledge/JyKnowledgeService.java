package com.yice.edu.cn.osp.service.jy.knowledge;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jy.knowledge.JyKnowledge;
import com.yice.edu.cn.common.pojo.jy.knowledge.UploadKnowledgeVo;
import com.yice.edu.cn.osp.feignClient.dd.DdFeign;
import com.yice.edu.cn.osp.feignClient.jy.knowledge.JyKnowledgeFeign;

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

    public List<JyKnowledge> findJyKnowledgeListByCondition(JyKnowledge jyKnowledge) {
        return jyKnowledgeFeign.findJyKnowledgeListByCondition(jyKnowledge);
    }

    public JyKnowledge findOneJyKnowledgeByCondition(JyKnowledge jyKnowledge) {
        return jyKnowledgeFeign.findOneJyKnowledgeByCondition(jyKnowledge);
    }

    public long findJyKnowledgeCountByCondition(JyKnowledge jyKnowledge) {
        return jyKnowledgeFeign.findJyKnowledgeCountByCondition(jyKnowledge);
    }
     
    public Map<String,Object> uploadJyKnowledge(MultipartFile file) throws Exception{
        Map<String,Object> result = new HashMap<>();
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(0);
       InputStream is = file.getInputStream();
       
       List<UploadKnowledgeVo> list = ExcelImportUtil.importExcel(is,
            		UploadKnowledgeVo.class, params);
       
       Map<String,Object> map = new HashMap<String,Object>();
       map.put("jyKnowledgesVo", list);
       Dd dd = new Dd();
       dd.setTypeId(Constant.DD_TYPE.GRADE);
       List<Dd> ddList = ddFeign.findDdListByCondition(dd);
       map.put("gradeObjectMap", ddList);
       jyKnowledgeFeign.uploadJyKnowledge(map);

        return result;
    }
}
