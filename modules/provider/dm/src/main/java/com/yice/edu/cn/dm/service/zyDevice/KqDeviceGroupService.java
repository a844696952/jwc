package com.yice.edu.cn.dm.service.zyDevice;

import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDeviceGroup;
import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDevicePerson;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class KqDeviceGroupService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private KqDevicePersonService kqDevicePersonService;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public KqDeviceGroup findKqDeviceGroupById(String id) {
        return mot.findById(id,KqDeviceGroup.class);
    }
    public void saveKqDeviceGroup(KqDeviceGroup kqDeviceGroup) {
        kqDeviceGroup.setCreateTime(DateUtil.now());
        kqDeviceGroup.setId(sequenceId.nextId());
        mot.insert(kqDeviceGroup);
    }
    public List<KqDeviceGroup> findKqDeviceGroupListByCondition(KqDeviceGroup kqDeviceGroup) {
        if (kqDeviceGroup.getGroupType()!=null&&kqDeviceGroup.getGroupType().size()==0){
            kqDeviceGroup.setGroupType(null);
        }
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(KqDeviceGroup.class)).find(MongoKit.buildMatchDocument(kqDeviceGroup));
        Pager pager = kqDeviceGroup.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<KqDeviceGroup> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(KqDeviceGroup.class, document)));
        return list;
    }
    public long findKqDeviceGroupCountByCondition(KqDeviceGroup kqDeviceGroup) {
        if (kqDeviceGroup.getGroupType()!=null&&kqDeviceGroup.getGroupType().size()==0){
            kqDeviceGroup.setGroupType(null);
        }
        return mot.getCollection(mot.getCollectionName(KqDeviceGroup.class)).countDocuments(MongoKit.buildMatchDocument(kqDeviceGroup));
    }
    public KqDeviceGroup findOneKqDeviceGroupByCondition(KqDeviceGroup kqDeviceGroup) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(KqDeviceGroup.class)).find(MongoKit.buildMatchDocument(kqDeviceGroup));
       MongoKit.appendProjection(query,kqDeviceGroup.getPager());
       return mot.getConverter().read(KqDeviceGroup.class,query.first());
    }

    public void updateKqDeviceGroup(KqDeviceGroup kqDeviceGroup) {
        kqDeviceGroup.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(kqDeviceGroup.getId())), MongoKit.update(kqDeviceGroup),KqDeviceGroup.class);
    }
    public void deleteKqDeviceGroup(String id) {
        mot.remove(query(where("id").is(id)),KqDeviceGroup.class);
    }
    public void deleteKqDeviceGroupByCondition(KqDeviceGroup kqDeviceGroup) {
        mot.getCollection(mot.getCollectionName(KqDeviceGroup.class)).deleteMany(MongoKit.buildMatchDocument(kqDeviceGroup));
    }
    public void batchSaveKqDeviceGroup(List<KqDeviceGroup> kqDeviceGroups){
        kqDeviceGroups.forEach(kqDeviceGroup -> kqDeviceGroup.setId(sequenceId.nextId()));
        mot.insertAll(kqDeviceGroups);
    }


    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public void updateKqDeviceGroupAndDeviceType(KqDeviceGroup kqDeviceGroup) {
        updateKqDeviceGroup(kqDeviceGroup);
        //修改分组下所有设备的设备出入校类型
        List<String> groupType = kqDeviceGroup.getGroupType();
        String groupId = kqDeviceGroup.getId();
        String schoolId = kqDeviceGroup.getSchoolId();
        KqDevicePerson kqDevicePerson = new KqDevicePerson();
        kqDevicePerson.setGroupId(groupId);
        kqDevicePerson.setSchoolId(schoolId);
        List<KqDevicePerson> deviceList = kqDevicePersonService.findKqDevicePersonListByCondition(kqDevicePerson);
        for (KqDevicePerson device:deviceList){
            device.setGroupType(groupType);
            kqDevicePersonService.updateKqDevicePerson(device);
        }
    }


}
