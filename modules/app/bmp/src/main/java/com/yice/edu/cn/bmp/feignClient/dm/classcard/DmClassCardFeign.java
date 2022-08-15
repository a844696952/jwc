package com.yice.edu.cn.bmp.feignClient.dm.classcard;

import com.yice.edu.cn.common.pojo.dm.classCard.ClassCardLock;
import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "dmClassCardFeign",path = "/dmClassCard")
public interface DmClassCardFeign {
    @GetMapping("/findDmClassCardById/{id}")
    DmClassCard findDmClassCardById(@PathVariable("id") String id);

    @PostMapping("/saveDmClassCard")
    DmClassCard saveDmClassCard(DmClassCard dmClassCard);

    @PostMapping("/findDmClassCardListByCondition")
    List<DmClassCard> findDmClassCardListByCondition(DmClassCard dmClassCard);

    @PostMapping("/findOneDmClassCardByCondition")
    DmClassCard findOneDmClassCardByCondition(DmClassCard dmClassCard);

    @PostMapping("/findDmClassCardCountByCondition")
    long findDmClassCardCountByCondition(DmClassCard dmClassCard);

    @PostMapping("/updateDmClassCard")
    void updateDmClassCard(DmClassCard dmClassCard);

    @GetMapping("/deleteDmClassCard/{id}")
    void deleteDmClassCard(@PathVariable("id") String id);

    @GetMapping("/relieveDmClassCard/{id}")
    void relieveDmClassCard(@PathVariable("id") String id);

    @PostMapping("/deleteDmClassCardByCondition")
    void deleteDmClassCardByCondition(DmClassCard dmClassCard);

    @PostMapping("/findDmClassCardListByclassId")
    List<DmClassCard> findDmClassCardListByclassId(DmClassCard dmClassCard);

    @PostMapping("/deleteDmClassCardAll")
    void deleteDmClassCardAll(DmClassCard dmClassCard);

    @PostMapping("/relieveDmClassCardAll")
    void relieveDmClassCardAll(DmClassCard dmClassCard);

    @PostMapping("/findDmClassCardToXls")
    List<DmClassCard> findDmClassCardToXls(DmClassCard dmClassCard);

    @PostMapping("/findDmClassCardUser")
    List<DmClassCard> findDmClassCardUser(DmClassCard dmClassCard);

    @PostMapping("/startboot")
    Boolean startboot(DmClassCard dmClassCard);

    @PostMapping("/shutdown")
    Boolean shutdown(DmClassCard dmClassCard);

    @PostMapping("/reboot")
    Boolean reboot(DmClassCard dmClassCard);

    @PostMapping("/updateEquipmentName")
    void updateEquipmentName(DmClassCard dmClassCard);

    @PostMapping("/dmClassCardStatus")
    void dmClassCardStatus(DmClassCard dmClassCard);

    @GetMapping("/lockDmScreen/{id}")
    void lockDmScreen(@PathVariable("id") String id);

    @GetMapping("/unLockDmScreen/{id}")
    void unLockDmScreen(@PathVariable("id") String id);

    @PostMapping("/batchChangeLockStatusByIds")
    void batchChangeLockStatusByIds(ClassCardLock classCardLock);

    @GetMapping("/getDmClassCardByTeacherId/{id}/{lockStatus}")
    List<DmClassCard> getDmClassCardByTeacherId(@PathVariable("id") String id, @PathVariable(value = "lockStatus") String lockStatus);

    @PostMapping("/updateDmClassManage")
    void updateDmClassManage(DmClassCard dmClassCard);

    @PostMapping("/getDmClassCardTree")
    List<DmClassCard> getDmClassCardTree(DmClassCard dmClassCard);

    @PostMapping("/findDmClassCardIdByTeacherId")
    List<DmClassCard> findDmClassCardIdByTeacherId(DmClassCard dmClassCard);

    @PostMapping("/cleraDmClassManage")
    void cleraDmClassManage(DmClassCard dmClassCard);

    @GetMapping("/findDmClassCardByStudentId/{studentId}")
    DmClassCard findDmClassCardByStudentId(@PathVariable("studentId") String studentId);

}