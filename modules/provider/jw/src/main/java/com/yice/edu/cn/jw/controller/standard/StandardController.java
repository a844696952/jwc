package com.yice.edu.cn.jw.controller.standard;

import com.yice.edu.cn.common.pojo.general.standard.Standard;
import com.yice.edu.cn.jw.service.standard.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/standard")
public class StandardController {
    @Autowired
    private StandardService standardService;

    @GetMapping("/findStandardById/{id}")
    public Standard findStandardById(@PathVariable String id){
        return standardService.findStandardById(id);
    }

    @PostMapping("/saveStandard")
    public Standard saveStandard(@RequestBody Standard standard){
        standardService.saveStandard(standard);
        return standard;
    }

    @PostMapping("/findStandardListByCondition")
    public List<Standard> findStandardListByCondition(@RequestBody Standard standard){
        return standardService.findStandardListByCondition(standard);
    }
    @PostMapping("/findStandardCountByCondition")
    public long findStandardCountByCondition(@RequestBody Standard standard){
        return standardService.findStandardCountByCondition(standard);
    }

    @PostMapping("/updateStandard")
    public void updateStandard(@RequestBody Standard standard){
        standardService.updateStandard(standard);
    }

    @GetMapping("/deleteStandard/{id}")
    public void deleteStandard(@PathVariable String id){
        standardService.deleteStandard(id);
    }
}
