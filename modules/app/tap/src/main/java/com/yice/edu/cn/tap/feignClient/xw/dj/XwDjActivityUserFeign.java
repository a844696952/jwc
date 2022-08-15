package com.yice.edu.cn.tap.feignClient.xw.dj;

import com.alibaba.fastjson.JSONObject;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberActivity.XwDjActivityAndUserVo;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberActivity.XwDjActivityUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",path = "/xwDjActivityUser")
public interface XwDjActivityUserFeign {

    @PostMapping("/signIn")
    JSONObject signIn(XwDjActivityAndUserVo xwDjActivityAndUserVo);
    @GetMapping("/findXwDjActivityUserSignInQRCode/{id}")
    String findXwDjActivityUserSignInQRCode(@PathVariable("id") String id);
    @PostMapping("/findXwDjActivityUserCount")
    long findXwDjActivityUserCount(XwDjActivityUser xwDjActivityUser);
    @GetMapping("/findXwDjActivityUserById/{id}")
    XwDjActivityUser findXwDjActivityUserById(@PathVariable("id") String id);
    @PostMapping("/saveXwDjActivityUser")
    XwDjActivityUser saveXwDjActivityUser(XwDjActivityUser xwDjActivityUser);
    @PostMapping("/saveBatchXwDjActivityUser")
    void saveBatchXwDjActivityUser(List<XwDjActivityUser> list);
    @PostMapping("/findXwDjActivityUserListByCondition")
    List<XwDjActivityAndUserVo> findXwDjActivityUserListByCondition(XwDjActivityUser xwDjActivityUser);
    @PostMapping("/findXwDjActivityUserListCountByCondition")
    long findXwDjActivityUserListCountByCondition(XwDjActivityUser xwDjActivityUser);
    @PostMapping("/findXwDjActivityUserSignUpListByCondition")
    XwDjActivityAndUserVo findXwDjActivityUserSignUpListByCondition(XwDjActivityUser xwDjActivityUser);
    @PostMapping("/findXwDjActivityUserSignUpListCountByCondition")
    long findXwDjActivityUserSignUpListCountByCondition(XwDjActivityUser xwDjActivityUser);
    @PostMapping("/findOneXwDjActivityUserByCondition")
    XwDjActivityUser findOneXwDjActivityUserByCondition(XwDjActivityUser xwDjActivityUser);
    @PostMapping("/findXwDjActivityUserListCondition")
    List<XwDjActivityUser> findXwDjActivityUserListCondition(XwDjActivityUser xwDjActivityUser);
    @PostMapping("/findXwDjActivityUserCountByCondition")
    long findXwDjActivityUserCountByCondition(XwDjActivityUser xwDjActivityUser);
    @PostMapping("/findXwDjActivityUserListCountCondition")
    long findXwDjActivityUserListCountCondition(XwDjActivityUser xwDjActivityUser);
    @PostMapping("/updateXwDjActivityUser")
    void updateXwDjActivityUser(XwDjActivityUser xwDjActivityUser);
    @PostMapping("/askForLeave")
    void askForLeave(XwDjActivityUser xwDjActivityUser);
    @PostMapping("/signUp")
    void signUp(XwDjActivityUser xwDjActivityUser);
    @GetMapping("/deleteXwDjActivityUser/{id}")
    void deleteXwDjActivityUser(@PathVariable("id") String id);
    @PostMapping("/deleteXwDjActivityUserByCondition")
    void deleteXwDjActivityUserByCondition(XwDjActivityUser xwDjActivityUser);
}
