package com.yice.edu.cn.osp.service.dm.zyDevice;

import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDeviceGroupPerson;
import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDevicePerson;
import com.yice.edu.cn.common.pojo.xw.kq.ZyDeviceBean;
import com.yice.edu.cn.osp.feignClient.dm.zyDevice.KqDevicePersonFeign;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class KqDevicePersonService {
    @Autowired
    private KqDevicePersonFeign kqDevicePersonFeign;
    @Autowired
    private KqDeviceGroupPersonService kqDeviceGroupPersonService;

    public KqDevicePerson findKqDevicePersonById(String id) {
        return kqDevicePersonFeign.findKqDevicePersonById(id);
    }

    public KqDevicePerson saveKqDevicePerson(KqDevicePerson kqDevicePerson) {
        KqDevicePerson kqDevicePerson1 = kqDevicePersonFeign.saveKqDevicePerson(kqDevicePerson);
        if (kqDevicePerson1.getDeviceType().equals("2")) {
            //绑定该分组所有人和设备
            kqDeviceGroupPersonService.deviceChangeGroup(kqDevicePerson1.getGroupId());
        }
        return kqDevicePerson1;
    }

    public List<KqDevicePerson> findKqDevicePersonListByCondition(KqDevicePerson kqDevicePerson) {
        return kqDevicePersonFeign.findKqDevicePersonListByCondition(kqDevicePerson);
    }

    public KqDevicePerson findOneKqDevicePersonByCondition(KqDevicePerson kqDevicePerson) {
        return kqDevicePersonFeign.findOneKqDevicePersonByCondition(kqDevicePerson);
    }

    public long findKqDevicePersonCountByCondition(KqDevicePerson kqDevicePerson) {
        return kqDevicePersonFeign.findKqDevicePersonCountByCondition(kqDevicePerson);
    }

    public void updateKqDevicePerson(KqDevicePerson kqDevicePerson) {
        KqDevicePerson kqDevicePersonById = findKqDevicePersonById(kqDevicePerson.getId());
        String oldGroupId = kqDevicePersonById.getGroupId();
        String newGroupId = kqDevicePerson.getGroupId();
        kqDevicePersonFeign.updateKqDevicePerson(kqDevicePerson);
        if (kqDevicePersonById.getDeviceType().equals("2")){
            //绑定新旧分组所有人和设备
            kqDeviceGroupPersonService.deviceChangeGroup(oldGroupId);
            //System.out.println("旧分组绑定");
            kqDeviceGroupPersonService.deviceChangeGroup(newGroupId);
            //System.out.println("新分组绑定");
        }

    }

    public void deleteKqDevicePerson(String id) {
        KqDevicePerson kqDevicePersonById = findKqDevicePersonById(id);
        kqDevicePersonFeign.deleteKqDevicePerson(id);
        if (kqDevicePersonById.getDeviceType().equals("2")){
            //绑定新旧分组所有人和设备
            kqDeviceGroupPersonService.deviceChangeGroup(kqDevicePersonById.getGroupId());
        }
    }

    public void deleteKqDevicePersonByCondition(KqDevicePerson kqDevicePerson) {
        kqDevicePersonFeign.deleteKqDevicePersonByCondition(kqDevicePerson);
    }

    public List<ZyDeviceBean> fuseZyDeviceAndLocal(List<ZyDeviceBean> zyDeviceBeans, List<KqDevicePerson> kqDevicePersons) {

        List<ZyDeviceBean> list = new ArrayList<>();
            for (KqDevicePerson kd:kqDevicePersons){
              ZyDeviceBean z = new ZyDeviceBean();
              BeanUtils.copyProperties(kd,z);
              zyDeviceBeans.add(z);
            }
            Map<String, List<ZyDeviceBean>> result = zyDeviceBeans.stream().collect(Collectors.groupingBy(kq -> kq.getDeviceNo()));

            for (String deviceNo:result.keySet()){
                List<ZyDeviceBean> v = result.get(deviceNo);
                List<ZyDeviceBean> list1 =  v.stream().filter(zd -> StrUtil.isNotEmpty(zd.getGroupId())).collect(Collectors.toList());//已入库
                List<ZyDeviceBean> list2 =  v.stream().filter(zd -> StrUtil.isEmpty(zd.getGroupId())).collect(Collectors.toList());//未入库
                if (list1!=null&&!list1.isEmpty()&&list2!=null&&!list2.isEmpty()){//两边都有
                    list2.get(0).setGroupName(list1.get(0).getGroupName());
                    list2.get(0).setGroupId(list1.get(0).getGroupId());
                    list2.get(0).setId(list1.get(0).getId());
                    list2.get(0).setRemoved("0");
                    list.add(list2.get(0));
                }else if (list1.isEmpty()&&!list2.isEmpty()){//库里没有，中移动有
                    list2.get(0).setRemoved("0");
                    list2.get(0).setGroupId("--");
                    list.add(list2.get(0));
                }else if(!list1.isEmpty()&&list2.isEmpty()){//库里有，中移动没有
                    list1.get(0).setRemoved("1");
                    list1.get(0).setDeviceStatus("--");
                    list1.get(0).setDerectionFlag("--");
                    list1.get(0).setDeviceLocation("--");
                    String id = list1.get(0).getId();
                    //删除安防平台已经移除的设备
                    deleteKqDevicePerson(id);
                }
           }

            return list;
    }

    public List<ZyDeviceBean> deviceFilter(List<ZyDeviceBean> data, KqDevicePerson zyDevice) {
        data=   data.stream().filter(zd -> zd.getDeviceType()!=null&&zd.getDeviceType().equals(zyDevice.getDeviceType()) ).collect(Collectors.toList());

        if (StrUtil.isNotEmpty(zyDevice.getGroupId())&&StrUtil.isNotEmpty(zyDevice.getDerectionFlag())&&StrUtil.isNotEmpty(zyDevice.getDeviceStatus())){
            data=   data.stream().filter(zd -> zd.getGroupId().equals(zyDevice.getGroupId())
                    && zd.getDerectionFlag().equals(zyDevice.getDerectionFlag())
                    && zd.getDeviceStatus().equals(zyDevice.getDeviceStatus())
            ).collect(Collectors.toList());
        }
        if (StrUtil.isEmpty(zyDevice.getGroupId())&&StrUtil.isNotEmpty(zyDevice.getDerectionFlag())&&StrUtil.isNotEmpty(zyDevice.getDeviceStatus())){
            data=   data.stream().filter(zd ->  zd.getDerectionFlag().equals(zyDevice.getDerectionFlag())
                    && zd.getDeviceStatus().equals(zyDevice.getDeviceStatus())
            ).collect(Collectors.toList());
        }
        if (StrUtil.isNotEmpty(zyDevice.getGroupId())&&StrUtil.isEmpty(zyDevice.getDerectionFlag())&&StrUtil.isNotEmpty(zyDevice.getDeviceStatus())){
            data=   data.stream().filter(zd -> zd.getGroupId().equals(zyDevice.getGroupId())
                    && zd.getDeviceStatus().equals(zyDevice.getDeviceStatus())
            ).collect(Collectors.toList());
        }
        if (StrUtil.isNotEmpty(zyDevice.getGroupId())&&StrUtil.isNotEmpty(zyDevice.getDerectionFlag())&&StrUtil.isEmpty(zyDevice.getDeviceStatus())){
            data=  data.stream().filter(zd -> zd.getGroupId().equals(zyDevice.getGroupId())
                    && zd.getDerectionFlag().equals(zyDevice.getDerectionFlag())
            ).collect(Collectors.toList());
        }
        if (StrUtil.isNotEmpty(zyDevice.getGroupId())&&StrUtil.isEmpty(zyDevice.getDerectionFlag())&&StrUtil.isEmpty(zyDevice.getDeviceStatus())){
            data=  data.stream().filter(zd -> zd.getGroupId().equals(zyDevice.getGroupId())).collect(Collectors.toList());
        }
        if (StrUtil.isEmpty(zyDevice.getGroupId())&&StrUtil.isNotEmpty(zyDevice.getDerectionFlag())&&StrUtil.isEmpty(zyDevice.getDeviceStatus())){
            data=  data.stream().filter(zd -> zd.getDerectionFlag().equals(zyDevice.getDerectionFlag())).collect(Collectors.toList());
        }
        if (StrUtil.isEmpty(zyDevice.getGroupId())&&StrUtil.isEmpty(zyDevice.getDerectionFlag())&&StrUtil.isNotEmpty(zyDevice.getDeviceStatus())){
            data=  data.stream().filter(zd ->  zd.getDeviceStatus().equals(zyDevice.getDeviceStatus()) ).collect(Collectors.toList());
        }
        return data;
    }



}
