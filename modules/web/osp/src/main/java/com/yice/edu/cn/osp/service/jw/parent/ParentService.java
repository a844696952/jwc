package com.yice.edu.cn.osp.service.jw.parent;


import com.yice.edu.cn.common.pojo.jw.parent.Parent;
import com.yice.edu.cn.osp.feignClient.jw.parent.ParentFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ParentService {
    @Autowired
    private ParentFeign parentFeign;
    public Parent  findParentByStudentId(String id) {
        return parentFeign.findParentByStudentId(id);
    }
}
