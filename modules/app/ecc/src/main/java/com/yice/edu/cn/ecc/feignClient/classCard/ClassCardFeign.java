package com.yice.edu.cn.ecc.feignClient.classCard;

import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "classCardFeign",path = "/dmClassCard")
public interface ClassCardFeign {
    /**
     * 云班牌登陆 (查询用户名是否存在,密码是否正确)
     * @param dmClassCard
     * @return
     */
    @PostMapping("/findDmClassCardUser")
    List<DmClassCard> findDmClassCardUser(DmClassCard dmClassCard);

    /**
     * 云班牌登陆后（获取在线离线状态 和 版本号）
     * @param dmClassCard
     * @return
     */
    @PostMapping("/dmClassCardStatus")
    void dmClassCardStatus(DmClassCard dmClassCard);


    @PostMapping("/findDmClassCardCountByCondition")
    long findDmClassCardCountByCondition(DmClassCard dmClassCard);

    @GetMapping("/findDmClassCardByStudentId/{studentId}")
    DmClassCard findDmClassCardByStudentId(@PathVariable("studentId") String studentId);

    @PostMapping("/findOneDmClassCardByCondition")
    DmClassCard findOneDmClassCardByCondition(DmClassCard dmClassCard);
    @PostMapping("/updateDmClassCard")
    void updateDmClassCard(DmClassCard dmClassCard);

    @GetMapping("/findDmClassMsgCardById/{id}")
    DmClassCard findDmClassMsgCardById(@PathVariable("id") String id);

    @GetMapping("/findSchoolByUserName/{userName}")
    DmClassCard findSchoolByUserName(@PathVariable(value = "userName")String userName);

    @DeleteMapping("/batchEccStudentFace/{schoolId}")
    void batchEccStudentFace(@PathVariable("schoolId") String schoolId);

    @DeleteMapping("/deleteParentMsgBySchoolId/{schoolId}")
    void deleteParentMsgBySchoolId(@PathVariable("schoolId")String schoolId);


    @DeleteMapping("/batchDeleteEccKqRecord/{schoolId}")
    void batchDeleteEccKqRecord(@PathVariable("schoolId") String schoolId);

}