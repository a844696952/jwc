package com.yice.edu.cn.dm.service.wb.LatticePager;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.wb.latticePager.DmPagerBackground;
import com.yice.edu.cn.common.pojo.wb.latticePager.DmPagerNumber;
import com.yice.edu.cn.common.pojo.wb.latticePager.LatticePager;
import com.yice.edu.cn.common.pojo.wb.latticePager.LatticePagerInfo;
import com.yice.edu.cn.common.util.jmessage.utils.StringUtils;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.dm.dao.wb.LatticePager.IDmPagerBackgroundDao;
import com.yice.edu.cn.dm.dao.wb.LatticePager.IDmPagerNumberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;


@Service
public class LatticePagerService {



    @Autowired
    private MongoTemplate mot;

    @Autowired
    private SequenceId sequenceId;

    @Autowired
    private IDmPagerBackgroundDao dmPagerBackgroundDao;

    @Autowired
    private IDmPagerNumberDao dmPagerNumberDao;

    /**
     *
     * 根据条件查询点阵试卷
     * */
    @Transactional(rollbackFor = Exception.class)
    public List<LatticePager> findLatticePagerListByCondition(LatticePager latticePager) {
        Query query = getQuery(latticePager);
        if(Objects.nonNull(latticePager.getPager()) && latticePager.getPager().getPaging()){
            query.with(latticePager.getPager());
        }
        return mot.find(query,LatticePager.class);
    }

    @Transactional(rollbackFor = Exception.class)
    public long findLatticePagerCountByCondition(LatticePager latticePager) {
        Query query = getQuery(latticePager);
        return mot.count(query, LatticePager.class);
    }


    private Query getQuery(LatticePager latticePager){
        List<Criteria> list=new ArrayList<>();
        if(StringUtils.isNotEmpty(latticePager.getName())){
            list.add(where("name").regex(".*?"+latticePager.getName()+".*"));
        }
        if(StringUtils.isNotEmpty(latticePager.getSchoolId())){
            list.add(where("schoolId").is(latticePager.getSchoolId()));
        }
        if(StringUtils.isNotEmpty(latticePager.getTeacherId())){
            list.add(where("teacherId").is(latticePager.getTeacherId()));
        }
        if(ObjectUtil.isNotNull(latticePager.getSearchTimeZone()) &&
                latticePager.getSearchTimeZone().length == 2){
            list.add(where("modifyTime").gte(latticePager.getSearchTimeZone()[0]).lte(latticePager.getSearchTimeZone()[1]));
        }
        Query query=new Query();
        if(CollUtil.isNotEmpty(list)){
            query=new Query(new Criteria().andOperator(list.toArray(new Criteria[list.size()])));
        }
        //排序
        query.with(new Sort(Sort.Direction.DESC,"modifyTime"));
        return query;
    }


    /**
     * 保存或者更新，根据id来判断
     * */
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateLatticePager(LatticePager latticePager) {
       //判断是否有id
        if(!ObjectUtil.isNull(latticePager.getId())){
            //删除之前的试卷
            mot.remove(query(where("_id").is(latticePager.getId())),LatticePager.class);
            //删除试卷相关的信息info
            List<LatticePagerInfo> infos = mot.find(query(where("pagerId").is(latticePager.getId())), LatticePagerInfo.class);
            if(CollectionUtil.isNotEmpty(infos)){
                infos.forEach(mot::remove);
            }
            //删除相关的试卷背景表
            DmPagerBackground dmPagerBackground = new DmPagerBackground();
            dmPagerBackground.setLatticeId(latticePager.getId());
            dmPagerBackgroundDao.deleteDmPagerBackgroundByCondition(dmPagerBackground);
            //删除试卷背景表相关的点阵页码表
            DmPagerNumber dmPagerNumber = new DmPagerNumber();
            dmPagerNumber.setLatticeId(latticePager.getId());
            dmPagerNumberDao.deleteDmPagerNumberByCondition(dmPagerNumber);
        }
        saveLatticePager(latticePager);
    }


    private void saveLatticePager(LatticePager latticePager) {
        String id = sequenceId.nextId();
        latticePager.setId(id);
        latticePager.setCreateTime(DateUtil.now());
        latticePager.setModifyTime(DateUtil.now());
        //首先save试卷详情表
        if(CollectionUtil.isNotEmpty(latticePager.getLatticePagerInfos())){
            latticePager.getLatticePagerInfos().forEach(l ->{
                l.setId(sequenceId.nextId());
                l.setPagerId(id);
                l.setCreateTime(DateUtil.now());
                l.setModifyTime(DateUtil.now());
                l.setSchoolId(latticePager.getSchoolId());
                if(l.getType() == 3){
                    StringBuilder sb = new StringBuilder();
                    l.getAnswers().forEach(sb::append);
                    l.setAnswer(sb.toString());
                }
                mot.insert(l);
            });
        }
        latticePager.setLatticePagerInfos(null);
        mot.insert(latticePager);
        //再次保存试卷背景表，根据图片来
        if(CollectionUtil.isNotEmpty(latticePager.getPagers())){
            for(int i = 0; i< latticePager.getPagers().size();i++){
                DmPagerBackground dmPagerBackground = new DmPagerBackground();
                dmPagerBackground.setId(sequenceId.nextId());
                dmPagerBackground.setLatticeId(id);
                String suffix = latticePager.getPagers().get(i).substring(0,latticePager.getPagers().get(i).
                        lastIndexOf('.')).split("_")[1];
                dmPagerBackground.setName(latticePager.getName().concat("_").concat(suffix));
                dmPagerBackground.setSchoolId(latticePager.getSchoolId());
                dmPagerBackground.setImagePath(latticePager.getPagers().get(i));
                dmPagerBackground.setLatticeNumber(i+1);
                dmPagerBackgroundDao.saveDmPagerBackground(dmPagerBackground);
            }
        }
    }




    @Transactional(rollbackFor = Exception.class)
    public void deleteLatticePager(String id) {
        //首先删除相关的试卷详情表
        List<LatticePagerInfo> pagerInfos = mot.find(query(where("pagerId").
                is(Optional.ofNullable(id).orElse(""))), LatticePagerInfo.class);
        if(CollectionUtil.isNotEmpty(pagerInfos)){
            pagerInfos.forEach(p -> mot.remove(p));
        }
        //接着删除相关的点阵试卷背景表
        DmPagerBackground dmPagerBackground = new DmPagerBackground();
        dmPagerBackground.setLatticeId(id);
        dmPagerBackgroundDao.deleteDmPagerBackgroundByCondition(dmPagerBackground);
        //再次删除点阵试卷页码表
        DmPagerNumber dmPagerNumber = new DmPagerNumber();
        dmPagerNumber.setLatticeId(id);
        dmPagerNumberDao.deleteDmPagerNumberByCondition(dmPagerNumber);
        //最后删除七牛传了得照片
        LatticePager latticePager = mot.findById(Optional.ofNullable(id).orElse(""), LatticePager.class);
        QiniuUtil.deleteFile(Optional.ofNullable(latticePager).isPresent()? latticePager.getPagerPath().substring(1):"");
        if(CollectionUtil.isNotEmpty(Objects.requireNonNull(latticePager).getPagers())){
            latticePager.getPagers().forEach(s -> QiniuUtil.deleteFile(s.substring(1)));
        }
        //删除试卷
        mot.remove(latticePager);
    }

    /**
     * 查询id
     * */
    @Transactional(rollbackFor = Exception.class)
    public LatticePager findLatticePagerById(String id) {
            LatticePager latticePager = mot.findById(id, LatticePager.class);
            if(ObjectUtil.isNotNull(latticePager)){
                //设置pagersInfo
                latticePager.setLatticePagerInfos(mot.find(query(where("pagerId").
                        is(latticePager.getId())),LatticePagerInfo.class));
                return latticePager;
            }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseJson findLatticePagerReference(DmPagerBackground dmPagerBackground) {
        List<DmPagerBackground> listByCondition = dmPagerBackgroundDao.findDmPagerBackgroundListByCondition(dmPagerBackground);
        if(CollectionUtil.isEmpty(listByCondition)){
            return  new ResponseJson(false,"该页码没有绑定");
        }
        LatticePager latticePager = mot.findById(listByCondition.get(0).getLatticeId(), LatticePager.class);
        if(ObjectUtil.isNull(latticePager)){
            return  new ResponseJson(false,"没有选中的试卷");
        }
        latticePager.setLatticePagerInfos(mot.find(query(where("pagerId").is(listByCondition.get(0).getLatticeId())
                .and("pageNumber").is(listByCondition.get(0).getLatticeNumber())), LatticePagerInfo.class));
        latticePager.setDmPagerBackgrounds(listByCondition);
        return new ResponseJson(latticePager);
    }
}
