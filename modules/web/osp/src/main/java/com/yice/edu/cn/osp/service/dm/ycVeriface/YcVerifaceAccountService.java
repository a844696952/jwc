package com.yice.edu.cn.osp.service.dm.ycVeriface;

import cn.hutool.core.collection.CollectionUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.account.YcVerifaceAccount;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.device.YcVerifaceDevice;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcVerifaceHeartbeatBean;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.osp.feignClient.dm.ycVeriface.YcVerifaceAccountFeign;
import com.yice.edu.cn.osp.feignClient.jw.student.StudentFeign;
import com.yice.edu.cn.osp.service.dm.school.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class YcVerifaceAccountService {
    @Autowired
    private YcVerifaceAccountFeign ycVerifaceAccountFeign;
    @Autowired
    private StudentFeign studentFeign;

    @CreateCache(name = Constant.Redis.YC_VERIFACE_ACCOUNT, expire = Constant.Redis.YC_VERIFACE_ACCOUNT_TIMEOUT,timeUnit = TimeUnit.SECONDS)
    private Cache<String, YcVerifaceAccount> ycVerifaceAccountList;
    @CreateCache(name = Constant.Redis.YC_VERIFACE_GENERATE_ACCOUNT, expire = Constant.Redis.YC_VERIFACE_GENERATE_ACCOUNT_TIMEOUT,timeUnit = TimeUnit.HOURS)
    private Cache<String, YcVerifaceAccount> ycVerifaceGenerateAccount;


    public YcVerifaceAccount findYcVerifaceAccountById(String id) {
        return ycVerifaceAccountFeign.findYcVerifaceAccountById(id);
    }

    public YcVerifaceAccount saveYcVerifaceAccount(YcVerifaceAccount ycVerifaceAccount) {

        YcVerifaceAccount y = new YcVerifaceAccount();
        y.setAccount(ycVerifaceAccount.getAccount());
        long yl = ycVerifaceAccountFeign.findYcVerifaceAccountCountByCondition(y);
        if (yl>0){
            ycVerifaceGenerateAccount.remove(ycVerifaceAccount.getAccount());
            YcVerifaceAccount account = generateAccount(ycVerifaceAccount);
            YcVerifaceAccount account1 = ycVerifaceAccountFeign.saveYcVerifaceAccount(account);
            ycVerifaceGenerateAccount.remove(account1.getAccount());
             return account1;
        }
        YcVerifaceAccount account = ycVerifaceAccountFeign.saveYcVerifaceAccount(ycVerifaceAccount);
        ycVerifaceGenerateAccount.remove(account.getAccount());
        return account;
    }

    public List<YcVerifaceAccount> findYcVerifaceAccountListByCondition(YcVerifaceAccount ycVerifaceAccount) {
        return ycVerifaceAccountFeign.findYcVerifaceAccountListByCondition(ycVerifaceAccount);
    }

    public YcVerifaceAccount findOneYcVerifaceAccountByCondition(YcVerifaceAccount ycVerifaceAccount) {
        return ycVerifaceAccountFeign.findOneYcVerifaceAccountByCondition(ycVerifaceAccount);
    }

    public long findYcVerifaceAccountCountByCondition(YcVerifaceAccount ycVerifaceAccount) {
        return ycVerifaceAccountFeign.findYcVerifaceAccountCountByCondition(ycVerifaceAccount);
    }

    public void updateYcVerifaceAccount(YcVerifaceAccount ycVerifaceAccount) {
        ycVerifaceAccountFeign.updateYcVerifaceAccount(ycVerifaceAccount);
    }

    public void deleteYcVerifaceAccount(String id) {
        ycVerifaceAccountFeign.deleteYcVerifaceAccount(id);
    }

    public void deleteYcVerifaceAccountByCondition(YcVerifaceAccount ycVerifaceAccount) {
        ycVerifaceAccountFeign.deleteYcVerifaceAccountByCondition(ycVerifaceAccount);
    }

    public YcVerifaceAccount generateAccount(YcVerifaceAccount ycVerifaceAccount) {
        //生成规则 账号规则：ycmj+随机生成6位数字，不能重复
        /*String schoolId = ycVerifaceAccount.getSchoolId();
            long l = Long.parseLong( schoolId);
            String  s= "ycmj";
            long kk=10000;
            for (int i= 0 ;i<6 ;i++){
                long oo = l%kk;
                oo=oo*Math.round(Math.random()*100000);
                String[] bytes = String.valueOf(oo).split("");
                int ww = 0;
                for (String w: bytes){
                    int i1 = Integer.parseInt(w);
                    ww = ww + i1;
                }
                oo = ww%10;
                l=l/kk;
                kk=1000;
                s=s+oo;
            }*/
        long round = Math.round(Math.random() * 1000000);
        ycVerifaceAccount.setAccount("ycmj"+String.valueOf(round));
        YcVerifaceAccount y = new YcVerifaceAccount();
        y.setAccount("ycmj"+String.valueOf(round));
        long yl = ycVerifaceAccountFeign.findYcVerifaceAccountCountByCondition(y);
        YcVerifaceAccount account = ycVerifaceGenerateAccount.get(y.getAccount());
        if (yl>0 ||account!=null){
            generateAccount(ycVerifaceAccount);
        }
        //ycVerifaceAccountFeign.saveYcVerifaceAccount(ycVerifaceAccount)
        ycVerifaceGenerateAccount.put(ycVerifaceAccount.getAccount(),ycVerifaceAccount);
        return ycVerifaceAccount;
    }

    public void createAccountCache(YcVerifaceAccount account) {
        String id = account.getId();
        ycVerifaceAccountList.put(id,account);
    }

    public void heartbeat(YcVerifaceAccount oneYcVerifaceAccount) {
        ycVerifaceAccountList.put(oneYcVerifaceAccount.getId(),oneYcVerifaceAccount);
    }

    public void setDoorDeviceCache(YcVerifaceDevice oneDoorDevice) {
        YcVerifaceAccount account = ycVerifaceAccountList.get(oneDoorDevice.getAccountId());
        List<YcVerifaceDevice> children = account.getChildren()==null?new ArrayList<>():account.getChildren();
        children.add(oneDoorDevice);
        children = children.stream().distinct().collect(Collectors.toList());
        account.setChildren(children);
        ycVerifaceAccountList.put(oneDoorDevice.getAccountId(),account);
    }

    public Map<String, YcVerifaceAccount> findHeartbeatAccount(List<YcVerifaceAccount> accountList) {
        Set<String> idSet = accountList.stream().map(YcVerifaceAccount::getId).collect(Collectors.toSet());
        Map<String, YcVerifaceAccount> all = ycVerifaceAccountList.getAll(idSet);
       return all;
    }

}
