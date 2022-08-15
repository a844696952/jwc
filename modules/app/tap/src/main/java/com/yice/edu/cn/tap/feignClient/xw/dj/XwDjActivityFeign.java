package com.yice.edu.cn.tap.feignClient.xw.dj;

import com.yice.edu.cn.common.pojo.xw.dj.partyMemberActivity.XwDjActivity;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberActivity.XwDjActivityAndUserVo;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberActivity.XwDjActivityUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "xwDjActivityFeign",path = "/xwDjActivity")
public interface XwDjActivityFeign {
    @GetMapping("/findXwDjActivityById/{id}")
    XwDjActivity findXwDjActivityById(@PathVariable("id") String id);
    @PostMapping("/findTeacherXwDjActivityById")
    XwDjActivityAndUserVo findTeacherXwDjActivityById(XwDjActivityAndUserVo xwDjActivityAndUserVo);
    @PostMapping("/saveXwDjActivity")
    XwDjActivity saveXwDjActivity(XwDjActivity xwDjActivity);
    @PostMapping("/findXwDjActivityTeacherListByCondition")
    List<XwDjActivity> findXwDjActivityTeacherListByCondition(XwDjActivity xwDjActivity);
    @PostMapping("/findXwDjActivityIsSendByCondition")
    XwDjActivityAndUserVo findXwDjActivityIsSendByCondition(XwDjActivityUser xwDjActivityUser);
    @PostMapping("/findXwDjActivityIsSendCountByCondition")
    long findXwDjActivityIsSendCountByCondition(XwDjActivityUser xwDjActivityUser);
    @PostMapping("/findOneXwDjActivityByCondition")
    XwDjActivity findOneXwDjActivityByCondition(XwDjActivity xwDjActivity);
    @PostMapping("/findXwDjActivityTeacherCountByCondition")
    long findXwDjActivityTeacherCountByCondition(XwDjActivity xwDjActivity);
    @PostMapping("/updateXwDjActivity")
    void updateXwDjActivity(XwDjActivity xwDjActivity);
    @GetMapping("/deleteXwDjActivity/{id}")
    void deleteXwDjActivity(@PathVariable("id") String id);
    @PostMapping("/deleteXwDjActivityByCondition")
    void deleteXwDjActivityByCondition(XwDjActivity xwDjActivity);
    @PostMapping("/closeXwDjActivity")
    void closeXwDjActivity(XwDjActivity xwDjActivity);
}
