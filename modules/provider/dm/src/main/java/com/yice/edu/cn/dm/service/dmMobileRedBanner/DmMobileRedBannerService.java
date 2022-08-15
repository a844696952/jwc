package com.yice.edu.cn.dm.service.dmMobileRedBanner;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.dm.dmMobileRedBanner.DmMobileRedBanner;
import com.yice.edu.cn.common.pojo.dm.dmMobileRedBanner.DmMobileRedBannerList;
import com.yice.edu.cn.dm.dao.dmMobileRedBanner.IDmMobileRedBannerDao;
import com.yice.edu.cn.dm.dao.dmMobileRedBanner.IDmMobileRedBannerListDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DmMobileRedBannerService {
    @Autowired
    private IDmMobileRedBannerDao dmMobileRedBannerDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private IDmMobileRedBannerListDao dmMobileRedBannerListDao;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public DmMobileRedBanner findDmMobileRedBannerById(String id) {
        DmMobileRedBanner dmMobileRedBanner = dmMobileRedBannerDao.findDmMobileRedBannerById(id);
        dmMobileRedBanner.setClassList(dmMobileRedBanner.getClassIds().split(","));
        return dmMobileRedBanner;
    }

    /**
     * 1.先保存流动红旗   2.再保存流动红旗获得班级
     * @param dmMobileRedBanner
     */
    @Transactional
    public void saveDmMobileRedBanner(DmMobileRedBanner dmMobileRedBanner) {
        //保存流动红旗
        String bannerId = sequenceId.nextId();
        dmMobileRedBanner.setId(bannerId);
        String[] classIds = dmMobileRedBanner.getClassList();
        String classNames = getClassNames(classIds);
        dmMobileRedBanner.setClassNames(classNames);
        dmMobileRedBanner.setClassIds(transArrayToString(classIds));
        dmMobileRedBannerDao.saveDmMobileRedBanner(dmMobileRedBanner);
        //保存流动红旗班级
        List<DmMobileRedBannerList> list = bulidRedBannerListByClassIds(classIds,bannerId,dmMobileRedBanner.getSchoolId());
        dmMobileRedBannerListDao.batchSaveDmMobileRedBannerList(list);
    }
    @Transactional(readOnly = true)
    public List<DmMobileRedBanner> findDmMobileRedBannerListByCondition(DmMobileRedBanner dmMobileRedBanner) {
        return dmMobileRedBannerDao.findDmMobileRedBannerListByCondition(dmMobileRedBanner);
    }
    @Transactional(readOnly = true)
    public DmMobileRedBanner findOneDmMobileRedBannerByCondition(DmMobileRedBanner dmMobileRedBanner) {
        DmMobileRedBanner record = dmMobileRedBannerDao.findOneDmMobileRedBannerByCondition(dmMobileRedBanner);
        record.setClassList(dmMobileRedBanner.getClassIds().split(","));
        return record;
    }
    @Transactional(readOnly = true)
    public long findDmMobileRedBannerCountByCondition(DmMobileRedBanner dmMobileRedBanner) {
        return dmMobileRedBannerDao.findDmMobileRedBannerCountByCondition(dmMobileRedBanner);
    }

    /**
     * 更新流动红旗时，先更新流动红旗；然后更新流动红旗班级列表
     * @param dmMobileRedBanner
     */
    @Transactional
    public void updateDmMobileRedBanner(DmMobileRedBanner dmMobileRedBanner) {
        //更新流动红旗
        String[] classIds = dmMobileRedBanner.getClassList();
        String classNames = getClassNames(classIds);
        dmMobileRedBanner.setClassNames(classNames);
        dmMobileRedBanner.setClassIds(transArrayToString(classIds));
        dmMobileRedBannerDao.updateDmMobileRedBanner(dmMobileRedBanner);
        //更新流动红旗班级,删除原有班级，插入新的班级
        DmMobileRedBannerList dmMobileRedBannerListRecord = new DmMobileRedBannerList();
        dmMobileRedBannerListRecord.setRedBannerId(dmMobileRedBanner.getId());
        dmMobileRedBannerListDao.deleteDmMobileRedBannerListByCondition(dmMobileRedBannerListRecord);
        //插入新的班级列表
        List<DmMobileRedBannerList> list = bulidRedBannerListByClassIds(classIds,dmMobileRedBanner.getId(),dmMobileRedBanner.getSchoolId());
        dmMobileRedBannerListDao.batchSaveDmMobileRedBannerList(list);
    }
    @Transactional
    public void updateDmMobileRedBannerForNotNull(DmMobileRedBanner dmMobileRedBanner) {
        dmMobileRedBannerDao.updateDmMobileRedBannerForNotNull(dmMobileRedBanner);
    }

    /**
     * 删除流动红旗时，先删除流动红旗，再删除流动红旗班级列表
     * @param id
     */
    @Transactional
    public void deleteDmMobileRedBanner(String id) {
        dmMobileRedBannerDao.deleteDmMobileRedBanner(id);
        //删除流动红旗班级列表
        DmMobileRedBannerList dmMobileRedBannerListRecord = new DmMobileRedBannerList();
        dmMobileRedBannerListRecord.setRedBannerId(id);
        dmMobileRedBannerListDao.deleteDmMobileRedBannerListByCondition(dmMobileRedBannerListRecord);
    }
    @Transactional
    public void deleteDmMobileRedBannerByCondition(DmMobileRedBanner dmMobileRedBanner) {
        dmMobileRedBannerDao.deleteDmMobileRedBannerByCondition(dmMobileRedBanner);
    }
    @Transactional
    public void batchSaveDmMobileRedBanner(List<DmMobileRedBanner> dmMobileRedBanners){
        dmMobileRedBanners.forEach(dmMobileRedBanner -> dmMobileRedBanner.setId(sequenceId.nextId()));
        dmMobileRedBannerDao.batchSaveDmMobileRedBanner(dmMobileRedBanners);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @Transactional
    public void updateStatus() {
        dmMobileRedBannerDao.updateStatus();
    }

    @Transactional
    public String getClassNames(String[] ids) {
        return dmMobileRedBannerDao.getClassNames(ids);
    }

    /**
     * 根据班级id列表和bannerId,schoolId 生成流动红旗班级列表
     * @param classIds
     * @param bannerId
     * @param schoolId
     * @return
     */
    private List<DmMobileRedBannerList> bulidRedBannerListByClassIds(String[] classIds,String bannerId,String schoolId){
        List<DmMobileRedBannerList> list = new ArrayList<>();
        for (String classId : classIds) {
            DmMobileRedBannerList dmMobileRedBannerList = new DmMobileRedBannerList();
            dmMobileRedBannerList.setId(sequenceId.nextId());
            dmMobileRedBannerList.setClassId(classId);
            dmMobileRedBannerList.setRedBannerId(bannerId);
            dmMobileRedBannerList.setSchoolId(schoolId);
            list.add(dmMobileRedBannerList);
        }
        return list;
    }

    /**
     * 将数组转换成用逗号分隔的字符串
     * @param a
     * @return
     */
    public static String transArrayToString(Object[] a) {
        if (a == null)
            return "null";

        int iMax = a.length - 1;
        if (iMax == -1)
            return "";

        StringBuilder b = new StringBuilder();
        for (int i = 0; ; i++) {
            b.append(String.valueOf(a[i]));
            if (i == iMax)
                return b.toString();
            b.append(",");
        }
    }

    /**
     *  List转换为字符串并加入分隔符
     * @param list,separator
     * @return
     */
    public String listToString(List list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    public DmMobileRedBanner findDmMobileRedBannerByClassId(String classId){
        return dmMobileRedBannerDao.findDmMobileRedBannerByClassId(classId);
    }

    public DmMobileRedBanner findTodayAwardRedBanner(){
        return dmMobileRedBannerDao.findTodayAwardRedBanner();
    }

    public void updateStatusShowById(String id){
        dmMobileRedBannerDao.updateStatusShowById(id);
    }

    public DmMobileRedBanner findToBeIssuedDmMobileRedBannerByAwardTime(String awardTime){
        return dmMobileRedBannerDao.findToBeIssuedDmMobileRedBannerByAwardTime(awardTime);
    }

    public List<DmMobileRedBanner> findDmMobileRedBannersByClassId(String classId){
        return dmMobileRedBannerDao.findDmMobileRedBannersByClassId(classId);
    }

    public void changeDmMobileRedBannerByClassIds(List<String> classIds){
        if(classIds == null || classIds.size() <= 0){
            return;
        }
        classIds.forEach(classId->{
            List<DmMobileRedBanner> dmMobileRedBannerList = findDmMobileRedBannersByClassId(classId);
            removeDmMobileRedBannerClassByClassId(dmMobileRedBannerList,classId);
        });
    }

    @Transactional
    public void removeDmMobileRedBannerClassByClassId(List<DmMobileRedBanner> dmMobileRedBannerList,String classId){
        if(dmMobileRedBannerList == null || dmMobileRedBannerList.size() <=0){
            return;
        }
        dmMobileRedBannerList.forEach(item->{
            String classIds = item.getClassIds();
            if(classIds.equals(classId)){
                dmMobileRedBannerDao.deleteDmMobileRedBanner(item.getId());
            }else {
                String[] split = classIds.split(",");
                List<String> classIdList = Arrays.stream(split).collect(Collectors.toList());
                classIdList.remove(classId);
                String newClassIds = listToString(classIdList,',');
                item.setClassIds(newClassIds);

                String[] strings = new String[classIdList.size()];
                classIdList.toArray(strings);
                String newClassNames = getClassNames(strings);
                item.setClassNames(newClassNames);
                dmMobileRedBannerDao.updateDmMobileRedBanner(item);
            }
        });
    }
}
