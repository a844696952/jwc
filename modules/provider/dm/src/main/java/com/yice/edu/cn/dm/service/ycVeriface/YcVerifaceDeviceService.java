package com.yice.edu.cn.dm.service.ycVeriface;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDeviceGroup;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.device.YcVerifaceDevice;
import com.yice.edu.cn.dm.service.zyDevice.KqDeviceGroupService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class YcVerifaceDeviceService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    KqDeviceGroupService kqDeviceGroupService;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public YcVerifaceDevice findYcVerifaceDeviceById(String id) {
        return mot.findById(id,YcVerifaceDevice.class);
    }
    public void saveYcVerifaceDevice(YcVerifaceDevice ycVerifaceDevice) {
        ycVerifaceDevice.setId(sequenceId.nextId());
        mot.insert(ycVerifaceDevice);
    }
    public List<YcVerifaceDevice> findYcVerifaceDeviceListByCondition(YcVerifaceDevice ycVerifaceDevice) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(YcVerifaceDevice.class)).find(MongoKit.buildMatchDocument(ycVerifaceDevice));
        Pager pager = ycVerifaceDevice.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<YcVerifaceDevice> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(YcVerifaceDevice.class, document)));
        return list;
    }
    public long findYcVerifaceDeviceCountByCondition(YcVerifaceDevice ycVerifaceDevice) {
        return mot.getCollection(mot.getCollectionName(YcVerifaceDevice.class)).countDocuments(MongoKit.buildMatchDocument(ycVerifaceDevice));
    }
    public YcVerifaceDevice findOneYcVerifaceDeviceByCondition(YcVerifaceDevice ycVerifaceDevice) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(YcVerifaceDevice.class)).find(MongoKit.buildMatchDocument(ycVerifaceDevice));
       MongoKit.appendProjection(query,ycVerifaceDevice.getPager());
       return mot.getConverter().read(YcVerifaceDevice.class,query.first());
    }

    public void updateYcVerifaceDevice(YcVerifaceDevice ycVerifaceDevice) {
        mot.updateFirst(query(where("id").is(ycVerifaceDevice.getId())), MongoKit.update(ycVerifaceDevice),YcVerifaceDevice.class);
    }
    public void deleteYcVerifaceDevice(String id) {
        mot.remove(query(where("id").is(id)),YcVerifaceDevice.class);
    }
    public void deleteYcVerifaceDeviceByCondition(YcVerifaceDevice ycVerifaceDevice) {
        mot.getCollection(mot.getCollectionName(YcVerifaceDevice.class)).deleteMany(MongoKit.buildMatchDocument(ycVerifaceDevice));
    }
    public void batchSaveYcVerifaceDevice(List<YcVerifaceDevice> ycVerifaceDevices){
        ycVerifaceDevices.forEach(ycVerifaceDevice -> ycVerifaceDevice.setId(sequenceId.nextId()));
        mot.insertAll(ycVerifaceDevices);
    }

    public List<YcVerifaceDevice> findSchoolInAndOutDevice(String schoolId) {
        List<YcVerifaceDevice> inAndOutDevices = new ArrayList<>();
        KqDeviceGroup kqDeviceGroup = new KqDeviceGroup();
        kqDeviceGroup.setSchoolId(schoolId);
        List<KqDeviceGroup> kqDeviceGroupList = kqDeviceGroupService.findKqDeviceGroupListByCondition(kqDeviceGroup);
        List<KqDeviceGroup> collect = kqDeviceGroupList.stream().filter(group -> {
            List<String> groupType = group.getGroupType();
            if (CollectionUtil.isNotEmpty(groupType) && groupType.contains("0")) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());

        if (CollectionUtil.isNotEmpty(collect)){
            for (KqDeviceGroup group:collect){
                String groupId = group.getId();
                //找出相关分组下的所有亿策设备
                YcVerifaceDevice y = new YcVerifaceDevice();
                y.setSchoolId(schoolId);
                y.setDeviceGroupId(groupId);
                List<YcVerifaceDevice> ycVerifaceDeviceListByCondition = findYcVerifaceDeviceListByCondition(y);
                inAndOutDevices.addAll(ycVerifaceDeviceListByCondition);
            }
        }

        return inAndOutDevices;
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
