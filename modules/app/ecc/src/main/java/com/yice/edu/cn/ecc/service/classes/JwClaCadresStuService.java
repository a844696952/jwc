package com.yice.edu.cn.ecc.service.classes;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jw.classes.JwClaCadresStu;
import com.yice.edu.cn.ecc.feignClient.classes.JwClaCadresStuFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JwClaCadresStuService {
    @Autowired
    private JwClaCadresStuFeign jwClaCadresStuFeign;
    public List<JwClaCadresStu> findStuAndCadresByClassesIdAndName(JwClaCadresStu jwClaCadresStu) {
        return jwClaCadresStuFeign.findStuAndCadresByClassesIdAndName(jwClaCadresStu);
    }
    public List<JwClaCadresStu> queryJwStudentByClassesId(String id){
        return jwClaCadresStuFeign.queryJwStudentByClassesId(id);
    }
}
