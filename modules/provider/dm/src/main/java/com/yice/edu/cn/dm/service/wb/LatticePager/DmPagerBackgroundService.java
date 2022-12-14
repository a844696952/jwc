package com.yice.edu.cn.dm.service.wb.LatticePager;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.google.common.collect.ImmutableList;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.wb.latticePager.DmPagerBackground;
import com.yice.edu.cn.common.pojo.wb.latticePager.DmPagerNumber;
import com.yice.edu.cn.common.pojo.wb.latticePager.LatticePager;
import com.yice.edu.cn.common.pojo.wb.latticePager.LatticePagerInfo;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.dm.dao.wb.LatticePager.IDmPagerBackgroundDao;
import com.yice.edu.cn.dm.dao.wb.LatticePager.IDmPagerNumberDao;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DmPagerBackgroundService {
    @Autowired
    private IDmPagerBackgroundDao dmPagerBackgroundDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private IDmPagerNumberDao dmPagerNumberDao;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public DmPagerBackground findDmPagerBackgroundById(String id) {
        return dmPagerBackgroundDao.findDmPagerBackgroundById(id);
    }
    @Transactional
    public void saveDmPagerBackground(DmPagerBackground dmPagerBackground) {
        dmPagerBackground.setId(sequenceId.nextId());
        dmPagerBackgroundDao.saveDmPagerBackground(dmPagerBackground);
    }
    @Transactional(readOnly = true)
    public List<DmPagerBackground> findDmPagerBackgroundListByCondition(DmPagerBackground dmPagerBackground) {
        return dmPagerBackgroundDao.findDmPagerBackgroundListByCondition(dmPagerBackground);
    }
    @Transactional(readOnly = true)
    public DmPagerBackground findOneDmPagerBackgroundByCondition(DmPagerBackground dmPagerBackground) {
        return dmPagerBackgroundDao.findOneDmPagerBackgroundByCondition(dmPagerBackground);
    }
    @Transactional(readOnly = true)
    public long findDmPagerBackgroundCountByCondition(DmPagerBackground dmPagerBackground) {
        return dmPagerBackgroundDao.findDmPagerBackgroundCountByCondition(dmPagerBackground);
    }
    @Transactional
    public void updateDmPagerBackground(DmPagerBackground dmPagerBackground) {
        dmPagerBackgroundDao.updateDmPagerBackground(dmPagerBackground);
    }
    @Transactional
    public void updateDmPagerBackgroundForAll(DmPagerBackground dmPagerBackground) {
        dmPagerBackgroundDao.updateDmPagerBackgroundForAll(dmPagerBackground);
    }

    @Transactional
    public void deleteDmPagerBackgroundByCondition(DmPagerBackground dmPagerBackground) {
        dmPagerBackgroundDao.deleteDmPagerBackgroundByCondition(dmPagerBackground);
    }
    @Transactional
    public void batchSaveDmPagerBackground(List<DmPagerBackground> dmPagerBackgrounds){
        dmPagerBackgrounds.forEach(dmPagerBackground -> dmPagerBackground.setId(sequenceId.nextId()));
        dmPagerBackgroundDao.batchSaveDmPagerBackground(dmPagerBackgrounds);
    }

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    /**
     * ????????????
     * */
    @Transactional(rollbackFor = Exception.class)
    public ResponseJson upload(MultipartFile file, String latticeId) {
        String suffix = file.getOriginalFilename().substring(Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf(".") + 1);
        String[] imgeArray = {"jpg", "png","jpeg"};
        if(!ArrayUtils.contains(imgeArray, suffix)){
            return new ResponseJson(false,"????????????????????????");
        }
        //???????????????
        String filePath = QiniuUtil.commonUploadFile(file, Constant.Upload.WB_LATTICE_PAGER + suffix);
        //????????????
        LatticePager latticePager = mongoTemplate.findById(latticeId, LatticePager.class);
        if(ObjectUtil.isNull(latticePager)){
            return new ResponseJson(false,"?????????????????????");
        }
        DmPagerBackground dmPagerBackground = new DmPagerBackground();
        dmPagerBackground.setName(file.getOriginalFilename());
        dmPagerBackground.setCreateTime(DateUtil.now());
        dmPagerBackground.setSchoolId(latticePager.getSchoolId());
        dmPagerBackground.setLatticeId(latticeId);
        dmPagerBackground.setImagePath(filePath);
        saveDmPagerBackground(dmPagerBackground);
        return new ResponseJson(filePath);
    }


    /**
     *
     * ??????????????????????????????????????????????????????
     * */
    @Transactional(rollbackFor = Exception.class)
    public ResponseJson textUpdateDmPagerBackground(DmPagerBackground dmPagerBackground) {
        DmPagerBackground background = findDmPagerBackgroundById(dmPagerBackground.getId());
        LatticePager latticePager = mongoTemplate.findById(dmPagerBackground.getLatticeId(), LatticePager.class);
        if(ObjectUtil.isNull(latticePager)){
            return new ResponseJson(false,"???????????????????????????");
        }
        if(ObjectUtil.isNull(background)){
            return new ResponseJson(false,"????????????????????????????????????");
        }
        if(!ObjectUtil.isNull(dmPagerBackground.getName())){
            //??????????????????????????????update?????????
            background.setName(dmPagerBackground.getName());
            dmPagerBackgroundDao.updateDmPagerBackground(background);
        }else{
            DmPagerNumber dmPagerNumber = new DmPagerNumber();
            //?????????????????????????????????????????????
            if(ObjectUtil.equal(background.getPagerNumber(),dmPagerBackground.getPagerNumber())){
                return new ResponseJson();
            }else{
                //????????????????????????????????????????????????????????????????????????????????????
                dmPagerNumber.setSchoolId(dmPagerBackground.getSchoolId());
                dmPagerNumber.setPager(new Pager().setPaging(false));
                dmPagerNumber.setIsRecycle(0);
                List<DmPagerNumber> condition = dmPagerNumberDao.findDmPagerNumberListByCondition(dmPagerNumber);
                if(CollectionUtil.isNotEmpty(condition)){
                    for (DmPagerNumber dmPager : condition){
                        //??????????????????????????????
                        String[] split = dmPager.getPagerNumber().split(",");
                        for (String s : split) {
                            if(ObjectUtil.equal(s,String.valueOf(dmPagerBackground.getPagerNumber()))){
                                return new ResponseJson(false,"???????????????????????????????????????????????????");
                            }
                        }
                    }
                }
            }
            //???????????????????????????????????????,?????????????????????????????????????????????????????????,????????????????????????????????????????????????
            dmPagerNumber.setLatticeId(dmPagerBackground.getLatticeId());
            dmPagerNumber.setIsRecycle(null);
            DmPagerNumber number = dmPagerNumberDao.findOneDmPagerNumberByCondition(dmPagerNumber);
            Integer isRecycle = 0;
            String createDate = DateUtil.now();
            if(!ObjectUtil.isNull(number)){
                createDate = number.getCreateTime();
                dmPagerNumberDao.deleteDmPagerNumberByCondition(number);
            }
            //???????????????
            background.setPagerNumber(dmPagerBackground.getPagerNumber());
            dmPagerBackgroundDao.updateDmPagerBackground(background);
            //??????????????????
            dmPagerBackground.setId(null);
            dmPagerBackground.setPagerNumber(null);
            List<DmPagerBackground> dmPagerBackgroundListByCondition = dmPagerBackgroundDao.
                    findDmPagerBackgroundListByCondition(dmPagerBackground);
            StringBuilder sb = new StringBuilder();
            if(CollectionUtil.isNotEmpty(dmPagerBackgroundListByCondition)){
                dmPagerBackgroundListByCondition.forEach(d -> {
                    if(ObjectUtil.isNotNull(d.getPagerNumber())){
                        sb.append(d.getPagerNumber()).append(",");
                    }
                });
            }
            dmPagerNumber.setId(sequenceId.nextId());
            dmPagerNumber.setName(latticePager.getName());
            dmPagerNumber.setPagerNumber(sb.substring(0,sb.length()-1));
            dmPagerNumber.setCreateTime(createDate);
            dmPagerNumber.setModifyTime(DateUtil.now());
            dmPagerNumber.setIsRecycle(isRecycle);
            dmPagerNumber.setTeacherId(latticePager.getTeacherId());
            dmPagerNumber.setSchoolYearId(latticePager.getSchoolYearId());
            dmPagerNumber.setFromTo(latticePager.getFromTo());
            dmPagerNumber.setTerm(latticePager.getTerm());
            dmPagerNumberDao.saveDmPagerNumber(dmPagerNumber);
        }
        return new ResponseJson("????????????");
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseJson reUpload(MultipartFile file, String id) {
        DmPagerBackground background = findDmPagerBackgroundById(id);
        if(ObjectUtil.isNull(background)){
            return new ResponseJson(false,"????????????????????????????????????");
        }
        String suffix = file.getOriginalFilename().substring(Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf(".") + 1);
        String[] imgeArray = {"jpg", "png","jpeg"};
        if(!ArrayUtils.contains(imgeArray, suffix)){
            return new ResponseJson(false,"????????????????????????");
        }
        //?????????????????????????????????
        QiniuUtil.deleteFile(background.getImagePath());
        background.setImagePath(QiniuUtil.commonUploadFile(file, Constant.Upload.WB_LATTICE_PAGER + suffix));
        dmPagerBackgroundDao.updateDmPagerBackground(background);
        return new ResponseJson(background);
    }


    @Transactional(rollbackFor = Exception.class)
    public void deleteDmPagerBackground(String id) {
        DmPagerBackground background = findDmPagerBackgroundById(id);
        if(ObjectUtil.isNull(background)){
            return;
        }
        //????????????????????????????????????????????????????????????????????????
        boolean moveFlag = false;
        DmPagerBackground find = new DmPagerBackground();
        find.setLatticeId(background.getLatticeId());
        find.setSchoolId(background.getSchoolId());
        List<DmPagerBackground> dmPagerBackgroundList = dmPagerBackgroundDao.findDmPagerBackgroundListByCondition(find);
        if(CollectionUtil.isNotEmpty(dmPagerBackgroundList) && dmPagerBackgroundList.size() != 1){
            moveFlag = true;
        }
        //????????????????????????????????????
        DmPagerNumber dmPagerNumber = new DmPagerNumber();
        dmPagerNumber.setSchoolId(background.getSchoolId());
        dmPagerNumber.setLatticeId(background.getLatticeId());
        DmPagerNumber condition = dmPagerNumberDao.findOneDmPagerNumberByCondition(dmPagerNumber);
        if(ObjectUtil.isNotNull(condition)){
            if(condition.getIsRecycle() == 0){
                StringBuilder sb = new StringBuilder();
                String[] array = condition.getPagerNumber().split(",");
                for (String s : array) {
                    if(!ObjectUtil.equal(s,String.valueOf(background.getPagerNumber()))){
                        sb.append(s).append(",");
                    }
                }
                if("".contentEquals(sb)){
                    //?????????????????????,???????????????
                    dmPagerNumberDao.deleteDmPagerNumberByCondition(condition);
                }else{
                    condition.setPagerNumber(sb.substring(0,sb.length()-1));
                    condition.setModifyTime(DateUtil.now());
                    dmPagerNumberDao.updateDmPagerNumber(condition);
                }
            }else{
                if(!moveFlag){
                    //?????????????????????????????????
                    dmPagerNumberDao.deleteDmPagerNumberByCondition(condition);
                }
            }
        }
        dmPagerBackgroundDao.deleteDmPagerBackground(id);
        //????????????????????? ????????????????????????
        LatticePager latticePager = mongoTemplate.findById(background.getLatticeId(), LatticePager.class);
        if(ObjectUtil.isNotNull(latticePager)){
            //?????????????????????LatticePagerInfo
            List<LatticePagerInfo> infos = findInfo(latticePager.getId(),background.getLatticeNumber());
            if(CollectionUtil.isNotEmpty(infos)){
                infos.forEach(mongoTemplate::remove);
            }
            Update update = new Update();
            if(moveFlag){
                Update updateInfo = new Update();
                //??????,?????????????????????
                dmPagerBackgroundList.stream().filter(d -> d.getLatticeNumber()>
                        background.getLatticeNumber()).forEach(m ->{
                      findInfo(m.getLatticeId(),m.getLatticeNumber()).forEach(v ->{
                          updateInfo.set("pageNumber",v.getPageNumber()-1);
                          mongoTemplate.upsert(Query.query(Criteria.where("_id").is(v.getId())),updateInfo,LatticePagerInfo.class);
                      });
                      m.setLatticeNumber(m.getLatticeNumber()-1);
                      dmPagerBackgroundDao.updateDmPagerBackground(m);
                });
                List<String> path = latticePager.getPagers().stream().filter(l->
                        !ObjectUtil.equal(background.getImagePath(),l)).collect(Collectors.toList());
                update.set("pagers",path);
                mongoTemplate.upsert(Query.query(Criteria.where("_id").is(latticePager.getId())),update,LatticePager.class);
            }else{
                //???????????????????????????????????????????????????
                mongoTemplate.remove(latticePager);
            }
        }
    }


    private List<LatticePagerInfo> findInfo(Object id, Object number){
      return  mongoTemplate.find(Query.query(Criteria.where("pagerId").is(id)
              .and("pageNumber").is(number)), LatticePagerInfo.class);
    }
}
