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
     * 上传图片
     * */
    @Transactional(rollbackFor = Exception.class)
    public ResponseJson upload(MultipartFile file, String latticeId) {
        String suffix = file.getOriginalFilename().substring(Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf(".") + 1);
        String[] imgeArray = {"jpg", "png","jpeg"};
        if(!ArrayUtils.contains(imgeArray, suffix)){
            return new ResponseJson(false,"不支持的文件格式");
        }
        //上传到七牛
        String filePath = QiniuUtil.commonUploadFile(file, Constant.Upload.WB_LATTICE_PAGER + suffix);
        //先查出来
        LatticePager latticePager = mongoTemplate.findById(latticeId, LatticePager.class);
        if(ObjectUtil.isNull(latticePager)){
            return new ResponseJson(false,"点阵试卷不存在");
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
     * 文本框输入的修改并且对应的页码的保存
     * */
    @Transactional(rollbackFor = Exception.class)
    public ResponseJson textUpdateDmPagerBackground(DmPagerBackground dmPagerBackground) {
        DmPagerBackground background = findDmPagerBackgroundById(dmPagerBackground.getId());
        LatticePager latticePager = mongoTemplate.findById(dmPagerBackground.getLatticeId(), LatticePager.class);
        if(ObjectUtil.isNull(latticePager)){
            return new ResponseJson(false,"当前操作试卷不存在");
        }
        if(ObjectUtil.isNull(background)){
            return new ResponseJson(false,"当前点阵试卷背景数据错误");
        }
        if(!ObjectUtil.isNull(dmPagerBackground.getName())){
            //如果有名字，就说明是update名称的
            background.setName(dmPagerBackground.getName());
            dmPagerBackgroundDao.updateDmPagerBackground(background);
        }else{
            DmPagerNumber dmPagerNumber = new DmPagerNumber();
            //特殊情况如果只是点击但是没有改
            if(ObjectUtil.equal(background.getPagerNumber(),dmPagerBackground.getPagerNumber())){
                return new ResponseJson();
            }else{
                //则这是修改页码的，页码首先需要先判断输入的页码是否被占用
                dmPagerNumber.setSchoolId(dmPagerBackground.getSchoolId());
                dmPagerNumber.setPager(new Pager().setPaging(false));
                dmPagerNumber.setIsRecycle(0);
                List<DmPagerNumber> condition = dmPagerNumberDao.findDmPagerNumberListByCondition(dmPagerNumber);
                if(CollectionUtil.isNotEmpty(condition)){
                    for (DmPagerNumber dmPager : condition){
                        //要用逗号分隔并且比对
                        String[] split = dmPager.getPagerNumber().split(",");
                        for (String s : split) {
                            if(ObjectUtil.equal(s,String.valueOf(dmPagerBackground.getPagerNumber()))){
                                return new ResponseJson(false,"当前输入的点阵码已存在，请重新输入");
                            }
                        }
                    }
                }
            }
            //下面是页码没被占用的情况下,首先删除页码表之前的对应的试卷背景记录,一般情况来说只可能为一条，则删掉
            dmPagerNumber.setLatticeId(dmPagerBackground.getLatticeId());
            dmPagerNumber.setIsRecycle(null);
            DmPagerNumber number = dmPagerNumberDao.findOneDmPagerNumberByCondition(dmPagerNumber);
            Integer isRecycle = 0;
            String createDate = DateUtil.now();
            if(!ObjectUtil.isNull(number)){
                createDate = number.getCreateTime();
                dmPagerNumberDao.deleteDmPagerNumberByCondition(number);
            }
            //修改点阵码
            background.setPagerNumber(dmPagerBackground.getPagerNumber());
            dmPagerBackgroundDao.updateDmPagerBackground(background);
            //再次组建插入
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
        return new ResponseJson("更新成功");
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseJson reUpload(MultipartFile file, String id) {
        DmPagerBackground background = findDmPagerBackgroundById(id);
        if(ObjectUtil.isNull(background)){
            return new ResponseJson(false,"当前点阵试卷背景数据错误");
        }
        String suffix = file.getOriginalFilename().substring(Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf(".") + 1);
        String[] imgeArray = {"jpg", "png","jpeg"};
        if(!ArrayUtils.contains(imgeArray, suffix)){
            return new ResponseJson(false,"不支持的文件格式");
        }
        //删除原来的七牛地址图片
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
        //看是否是最后一个，如果只有最后一个，则不需要移动
        boolean moveFlag = false;
        DmPagerBackground find = new DmPagerBackground();
        find.setLatticeId(background.getLatticeId());
        find.setSchoolId(background.getSchoolId());
        List<DmPagerBackground> dmPagerBackgroundList = dmPagerBackgroundDao.findDmPagerBackgroundListByCondition(find);
        if(CollectionUtil.isNotEmpty(dmPagerBackgroundList) && dmPagerBackgroundList.size() != 1){
            moveFlag = true;
        }
        //先处理点阵页码的相关内容
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
                    //说明是最后一个,则直接删除
                    dmPagerNumberDao.deleteDmPagerNumberByCondition(condition);
                }else{
                    condition.setPagerNumber(sb.substring(0,sb.length()-1));
                    condition.setModifyTime(DateUtil.now());
                    dmPagerNumberDao.updateDmPagerNumber(condition);
                }
            }else{
                if(!moveFlag){
                    //回收的最后一个直接删除
                    dmPagerNumberDao.deleteDmPagerNumberByCondition(condition);
                }
            }
        }
        dmPagerBackgroundDao.deleteDmPagerBackground(id);
        //删除该背景页面 会导致试卷来更新
        LatticePager latticePager = mongoTemplate.findById(background.getLatticeId(), LatticePager.class);
        if(ObjectUtil.isNotNull(latticePager)){
            //再去删除相关的LatticePagerInfo
            List<LatticePagerInfo> infos = findInfo(latticePager.getId(),background.getLatticeNumber());
            if(CollectionUtil.isNotEmpty(infos)){
                infos.forEach(mongoTemplate::remove);
            }
            Update update = new Update();
            if(moveFlag){
                Update updateInfo = new Update();
                //移动,每一个都要减一
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
                //最后一个直接删除试卷，不需要更新了
                mongoTemplate.remove(latticePager);
            }
        }
    }


    private List<LatticePagerInfo> findInfo(Object id, Object number){
      return  mongoTemplate.find(Query.query(Criteria.where("pagerId").is(id)
              .and("pageNumber").is(number)), LatticePagerInfo.class);
    }
}
