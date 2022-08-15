package com.yice.edu.cn.osp.feignClient.dm.school;

import com.yice.edu.cn.common.pojo.dm.school.DmSchoolBigNews;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "dm",contextId = "dmSchoolBigNewsFeign",path = "/dmSchoolBigNews")
public interface DmSchoolBigNewsFeign {
    @GetMapping("/findDmSchoolBigNewsById/{id}")
    DmSchoolBigNews findDmSchoolBigNewsById(@PathVariable("id") String id);

    @PostMapping("/saveDmSchoolBigNews")
    DmSchoolBigNews saveDmSchoolBigNews(DmSchoolBigNews dmSchoolBigNews);

    @PostMapping("/findDmSchoolBigNewsListByCondition")
    List<DmSchoolBigNews> findDmSchoolBigNewsListByCondition(DmSchoolBigNews dmSchoolBigNews);

    @PostMapping("/findOneDmSchoolBigNewsByCondition")
    DmSchoolBigNews findOneDmSchoolBigNewsByCondition(DmSchoolBigNews dmSchoolBigNews);

    @PostMapping("/findDmSchoolBigNewsCountByCondition")
    long findDmSchoolBigNewsCountByCondition(DmSchoolBigNews dmSchoolBigNews);

    @PostMapping("/updateDmSchoolBigNews")
    void updateDmSchoolBigNews(DmSchoolBigNews dmSchoolBigNews);

    @GetMapping("/deleteDmSchoolBigNews/{id}")
    void deleteDmSchoolBigNews(@PathVariable("id") String id);

    @PostMapping("/deleteDmSchoolBigNewsByCondition")
    void deleteDmSchoolBigNewsByCondition(DmSchoolBigNews dmSchoolBigNews);

    @PostMapping("/findDmSchoolBigNewsListByactiveNameLike")
    List<DmSchoolBigNews> findDmSchoolBigNewsListByactiveNameLike(DmSchoolBigNews dmSchoolBigNews);

    @PostMapping("/findDmSchoolBigNewsListByactiveNameLikeCount")
    Long findDmSchoolBigNewsListByactiveNameLikeCount(DmSchoolBigNews dmSchoolBigNews);
}
