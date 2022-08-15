package com.yice.edu.cn.tap.service.oa;

import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.oa.common.SelComponent;
import com.yice.edu.cn.tap.feignClient.oa.DatasourceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatasourceService {
    @Autowired
    private DatasourceFeign datasourceFeign;
    public List<Department> getMyDepartmentNames(String id) {
        return datasourceFeign.getMyDepartmentNames(id);
    }
    public List<SelComponent> getclassHour(){
    	return datasourceFeign.getclassHour();
    }
    public List<SelComponent> getTimeInterval(){
    	return datasourceFeign.getTimeInterval();
    }
}
