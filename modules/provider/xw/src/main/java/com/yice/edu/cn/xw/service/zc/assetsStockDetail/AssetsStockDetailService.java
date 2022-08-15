package com.yice.edu.cn.xw.service.zc.assetsStockDetail;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Pager;
//import com.yice.edu.cn.common.pojo.jw.semester.Semester;
import com.yice.edu.cn.common.pojo.xw.zc.assetsFile.AssetsFile;
import com.yice.edu.cn.common.pojo.xw.zc.assetsStockDetail.AssetsStockDetail;
import com.yice.edu.cn.xw.dao.zc.assetsStockDetail.IAssetsStockDetailDao;
//import com.yice.edu.cn.xw.feignClient.jy.semester.SemesterFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AssetsStockDetailService {
    @Autowired
    private IAssetsStockDetailDao assetsStockDetailDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public AssetsStockDetail findAssetsStockDetailById(String id) {
        return assetsStockDetailDao.findAssetsStockDetailById(id);
    }
    @Transactional
    public void saveAssetsStockDetail(AssetsStockDetail assetsStockDetail) {
        assetsStockDetail.setId(sequenceId.nextId());
        assetsStockDetailDao.saveAssetsStockDetail(assetsStockDetail);
    }
    @Transactional(readOnly = true)
    public List<AssetsStockDetail> findAssetsStockDetailListByCondition(AssetsStockDetail assetsStockDetail) {
        return assetsStockDetailDao.findAssetsStockDetailListByCondition(assetsStockDetail);
    }
    @Transactional(readOnly = true)
    public AssetsStockDetail findOneAssetsStockDetailByCondition(AssetsStockDetail assetsStockDetail) {
        return assetsStockDetailDao.findOneAssetsStockDetailByCondition(assetsStockDetail);
    }
    @Transactional(readOnly = true)
    public long findAssetsStockDetailCountByCondition(AssetsStockDetail assetsStockDetail) {
        return assetsStockDetailDao.findAssetsStockDetailCountByCondition(assetsStockDetail);
    }
    @Transactional
    public void updateAssetsStockDetail(AssetsStockDetail assetsStockDetail) {
        assetsStockDetailDao.updateAssetsStockDetail(assetsStockDetail);
    }
    @Transactional
    public void updateAssetsStockDetailForNotNull(AssetsStockDetail assetsStockDetail) {
        assetsStockDetailDao.updateAssetsStockDetailForNotNull(assetsStockDetail);
    }
    @Transactional
    public void deleteAssetsStockDetail(String id) {
        assetsStockDetailDao.deleteAssetsStockDetail(id);
    }
    @Transactional
    public void deleteAssetsStockDetailByCondition(AssetsStockDetail assetsStockDetail) {
        assetsStockDetailDao.deleteAssetsStockDetailByCondition(assetsStockDetail);
    }
    @Transactional
    public void batchSaveAssetsStockDetail(List<AssetsStockDetail> assetsStockDetails){
        assetsStockDetails.forEach(assetsStockDetail -> assetsStockDetail.setId(sequenceId.nextId()));
        assetsStockDetailDao.batchSaveAssetsStockDetail(assetsStockDetails);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @Transactional(readOnly = true)
    public AssetsStockDetail findAssetsStockDetailJoinTableById(String id) {
        return assetsStockDetailDao.findAssetsStockDetailJoinTableById(id);
    }

//    @Autowired
//    private SemesterFeign semesterFeign;

    // 首页、资产库存明细查询
    @Transactional(readOnly = true)
    public List<AssetsStockDetail> findAssetsStockDetailListByFuzzyCondition(AssetsStockDetail assetsStockDetail) {
        return assetsStockDetailDao.findAssetsStockDetailListByFuzzyCondition(assetsStockDetail);
    }

    @Transactional(readOnly = true)
    public long findAssetsStockDetailCountByFuzzyCondition(AssetsStockDetail assetsStockDetail) {
        return assetsStockDetailDao.findAssetsStockDetailCountByFuzzyCondition(assetsStockDetail);
    }


    public String getLastCodeByInventoryCode(String inventoryCode) {
        return assetsStockDetailDao.getLastCodeByInventoryCode(inventoryCode);
    }

    // 资产使用记录
    @Transactional(readOnly = true)
    public List<AssetsStockDetail> findAssetsUseRecordById(AssetsStockDetail assetsStockDetail) {
        List<AssetsStockDetail> assetsStockDetaiList = assetsStockDetailDao.findAssetsUseRecordById(assetsStockDetail);
        if(assetsStockDetaiList != null && assetsStockDetaiList.size() > 1){
            for(int i = 1;i<assetsStockDetaiList.size();i++ ){
                assetsStockDetaiList.get(i).setStatus(1);
            }
        }
        return assetsStockDetaiList;
    }

    // 资产使用记录 条数
    @Transactional(readOnly = true)
    public long findAssetsUseRecordCountById(AssetsStockDetail assetsStockDetail) {
        return assetsStockDetailDao.findAssetsUseRecordCountById(assetsStockDetail);
    }


//    public List<Semester> findSemesterListByCondition(AssetsStockDetail assetsStockDetail) {
//        Semester semester = new Semester();
//        Pager pager = new Pager();
//        pager.setPaging(false);
//        semester.setPager(pager);
//        return semesterFeign.findSemesterListByCondition(semester);
//    }


    @Transactional
    public void updateAssetsStockDetailForNotNullByCondition(AssetsStockDetail assetsStockDetail) {
        assetsStockDetailDao.updateAssetsStockDetailForNotNullByCondition(assetsStockDetail);
    }

    public String findAssetsUsePercentage(AssetsStockDetail assetsStockDetail) {
        return assetsStockDetailDao.findAssetsUsePercentage(assetsStockDetail);
    }

    /**
     * 查询最近一年的资产数量
     * @param assetsStockDetail
     * @return
     */
    public List<AssetsStockDetail> findRecentOneYearAssertsCount(AssetsStockDetail assetsStockDetail) {

//        String   DateUtil.format(DateUtil.offset(new Date(), DateField.YEAR,-1),"yyyy-MM-dd");
        List<AssetsStockDetail>  assetsStockDetailList = assetsStockDetailDao.findRecentOneYearAssertsCount(assetsStockDetail);
        String[] last12Months = new String[12];
        Calendar cal = Calendar.getInstance();
        //如果当前日期大于二月份的天数28天或者29天会导致计算月份错误，会多出一个三月份，故设置一个靠前日期解决此问题
        cal.set(Calendar.DAY_OF_MONTH, 1);
        for (int i = 0; i < 12; i++) {

            String month = String.format("%0" + 2 + "d",cal.get(Calendar.MONTH) + 1);
            last12Months[11 - i] = cal.get(Calendar.YEAR) + "-" + month;
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1); //逐次往前推1个月
        }
        for (int i = 0; i < last12Months.length; i++) {
            boolean flag = true;
            for (int j = 0; j < assetsStockDetailList.size(); j++) {
                if(last12Months[i].equals(assetsStockDetailList.get(j).getXTime())){
                    flag = false;
                    break;
                }
            }
            if(flag){
                AssetsStockDetail assetsStockDetailItem = new AssetsStockDetail();
                assetsStockDetailItem.setXTime(last12Months[i]);
                assetsStockDetailItem.setYCount(0);
                assetsStockDetailList.add(assetsStockDetailItem);
            }
        }

        return sortAssetsStockDetailList(assetsStockDetailList);
    }

    /**
     * 查询最近一个月的资产数量
     * @param assetsStockDetail
     * @return
     */
    public List<AssetsStockDetail> findRecentOneMonthAssertsCount(AssetsStockDetail assetsStockDetail) {

        List<AssetsStockDetail>  assetsStockDetailList = assetsStockDetailDao.findRecentOneMonthAssertsCount(assetsStockDetail);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        String maxDateStr = sdf.format(d);
        String minDateStr = "";
        Calendar calc = Calendar.getInstance();
        List<String> list = new ArrayList<>();
        try {
            for (int i = 0; i < 30; i++) {
                calc.setTime(sdf.parse(maxDateStr));
                calc.add(calc.DATE, -i);
                Date minDate = calc.getTime();
                minDateStr = sdf.format(minDate);
                list.add(minDateStr);
            }
        } catch (ParseException e1) {
            e1.printStackTrace();

        }
        System.out.println(list);
        for (int i = 0; i < list.size(); i++) {
            boolean flag = true;// 默认是都要过滤的。
            for (int j = 0; j < assetsStockDetailList.size(); j++) {
                if(list.get(i).equals(assetsStockDetailList.get(j).getXTime())){
                    flag = false;
                    break;
                }
            }
            if(flag){
                AssetsStockDetail assetsStockDetailItem = new AssetsStockDetail();
                assetsStockDetailItem.setXTime(list.get(i));
                assetsStockDetailItem.setYCount(0);
                assetsStockDetailList.add(assetsStockDetailItem);
            }
        }

        return sortAssetsStockDetailList(assetsStockDetailList);
    }

    public List<AssetsFile> findFileTotalPrice(AssetsStockDetail assetsStockDetail){
        return assetsStockDetailDao.findFileTotalPrice(assetsStockDetail);
    }


    /**
     * 对 list进行排序
     * @param assetsStockDetailList
     * @return
     */
    public List<AssetsStockDetail> sortAssetsStockDetailList(List<AssetsStockDetail> assetsStockDetailList){

        Collections.sort(assetsStockDetailList, (o1, o2) -> {
            Date d1 = null;
            Date d2 = null;
            SimpleDateFormat sDateFormat = null;
            if(assetsStockDetailList.get(0).getXTime().length() ==  10){
                sDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            }else{
                sDateFormat=new SimpleDateFormat("yyyy-MM");
            }
            try {
                d1 = sDateFormat.parse(o1.getXTime());
                d2 = sDateFormat.parse(o2.getXTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (d1.before(d2)) {
                return 1;
            }
            return -1;
        });

        return assetsStockDetailList;

    }

}
