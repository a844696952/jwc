package com.yice.edu.cn.xw.service.zc.assetsInNew;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.xw.zc.assetsInNew.AssetsInNew;
import com.yice.edu.cn.common.pojo.xw.zc.assetsStockDetail.AssetsStockDetail;
import com.yice.edu.cn.common.service.CurSchoolYearService;
import com.yice.edu.cn.xw.dao.zc.assetsInNew.IAssetsInNewDao;
import com.yice.edu.cn.xw.feignClient.jw.schoolYear.SchoolYearFeign;
import com.yice.edu.cn.xw.service.zc.assetsStockDetail.AssetsStockDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class AssetsInNewService {
    @Autowired
    private IAssetsInNewDao assetsInNewDao;
    @Autowired
    private AssetsStockDetailService assetsStockDetailService;
//    @Autowired
//    private SchoolYearFeign schoolYearFeign;
    @Autowired
    private CurSchoolYearService curSchoolYearService;

    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public AssetsInNew findAssetsInNewById(String id) {
        return assetsInNewDao.findAssetsInNewById(id);
    }
    @Transactional
    public void saveAssetsInNew(AssetsInNew assetsInNew) {
        assetsInNew.setId(sequenceId.nextId());

//        1、点击“保存”，提示“入库成功，入库单号：xxxxxxxxxxx”
//        2、入库单号（校内唯一）：系统自动生成；单号规则：ZCRK（资产入库首字母）+入库日期（年月日）+当日入库流水号（3位）
//        3、一个入库单对应一个入库单号
        String putInNo = "ZCRK";

        SimpleDateFormat sdf =   new SimpleDateFormat("yyyyMMdd");
        String today = sdf.format(new Date());
        putInNo = putInNo.concat(today);
        //判断今天是否有入库单，没有的话，从001开始
        String todayNo = assetsInNewDao.findAssetsNoByToday(putInNo);
        if(todayNo == null){
            putInNo = putInNo.concat("001");
        }else{
            Integer i = Integer.parseInt(todayNo.substring(todayNo.length()-3,todayNo.length()));
            String lastNo = String.format("%0" + 3 + "d",++i );
            putInNo = putInNo +  lastNo;
        }
        assetsInNew.setPutInNo(putInNo);

        List<AssetsInNew> assetsInNewList = new ArrayList<>();
        assetsInNewList = assetsInNew.getAssetsInNewList();
        assetsInNewList.forEach( item -> {
            item.setAssetsFileId(item.getId());//资产档案ID
            item.setId(sequenceId.nextId());
//            item.setAssetsName(assetsInNew.getAssetsName());//资产名称
//            item.setInventoryCode(assetsInNew.);//存货编码
            //item.setAssetsCategoryId(assetsInNew.getId());
            item.setPutInNo(assetsInNew.getPutInNo());
            item.setCreateId(assetsInNew.getCreateId());
            item.setCreateUsername(assetsInNew.getCreateUsername());
            item.setPutInTime(assetsInNew.getPutInTime());
            item.setRemark(assetsInNew.getRemark());//备注
            assetsInNewDao.saveAssetsInNew(item);

            AssetsStockDetail assetsStockDetail = new AssetsStockDetail();
//            assetsStockDetail.setAssetsNo(item.getInventoryCode());
//
            String inventoryCode = item.getInventoryCode();//存货编码
            String assetsNo = "";//资产编号
//             资产编号
//             1、入库后系统自动生成资产编号
//             2、编号规则：12位：存货编码（8位）+4位流水号（校内唯一)
//            判断存货编码是否有入库单，没有的话，从001开始
            Integer increaseNum = 0 ;
            String concatNum = "";//待拼接的后 4 位流水号
            String lastCode = assetsStockDetailService.getLastCodeByInventoryCode(inventoryCode);//最新的资产编码
            if(lastCode == null){//如果存货编码没有
                concatNum = "0000";//初始化为 0001
                increaseNum = Integer.parseInt(concatNum);
            }else{//如果有最新的资产编码，
                lastCode = lastCode.substring(lastCode.length()-4,lastCode.length());//取后面4位
                increaseNum = Integer.parseInt(lastCode);//解析成数字，如果 0004
            }



//            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            //如果是年
            String useTimelimitDate = "";
            String maintenanceTimelimitUnit = "";
            //取到采购日期
            Date date = null;
            try {
                 date = sdf1.parse(item.getAssetsBuyTime());
            }catch(Exception e){
                 e.printStackTrace();
            }
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(date);
            //加上期限时间
            if(item.getAssetsUseTimelimitUnit() != null){
                if("0".equals(item.getAssetsUseTimelimitUnit())){
                    calendar1.add(Calendar.YEAR,Integer.parseInt(item.getAssetsUseTimelimitNumber()));
                } else if("1".equals(item.getAssetsUseTimelimitUnit())) {
                    calendar1.add(Calendar.MONTH, Integer.parseInt(item.getAssetsUseTimelimitNumber()));
                }
                //设置到属性里面
                useTimelimitDate = sdf1.format(calendar1.getTime());
            }


            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(date);

            if(item.getAssetsMaintenanceTimelimitUnit() != null){
                if("0".equals(item.getAssetsMaintenanceTimelimitUnit())){
                    calendar2.add(Calendar.YEAR,Integer.parseInt(item.getAssetsMaintenanceTimelimitNumber()));
                } else if("1".equals(item.getAssetsMaintenanceTimelimitUnit())) {
                    calendar2.add(Calendar.MONTH, Integer.parseInt(item.getAssetsMaintenanceTimelimitNumber()));
                }
                //设置到属性里面
                maintenanceTimelimitUnit = sdf1.format(calendar2.getTime());
            }



            List<AssetsStockDetail> stockDetailList = new ArrayList<>();
            for(int i=0;i<item.getPutInQuantity();i++){
                AssetsStockDetail stockDetail = new AssetsStockDetail();
                stockDetail.setId(sequenceId.nextId());
                concatNum = String.format("%0" + 4 + "d",++increaseNum );
                stockDetail.setAssetsNo( inventoryCode + concatNum );
                // 插入档案表的信息
                stockDetail.setAssetsName(item.getAssetsName());
                stockDetail.setAssetsProperty(Integer.parseInt(item.getAssetsProperty()));
                stockDetail.setAssetsUnit(item.getAssetsUnit());
                stockDetail.setAssetsModel(item.getAssetsModel());
                stockDetail.setManufacturer(item.getManufacturer());
                stockDetail.setSupplier(item.getSupplier());
                if(item.getAssetsPrice() != null){
                    stockDetail.setAssetsPrice(Double.parseDouble(item.getAssetsPrice()));
                }
                stockDetail.setAssetsBarcode(item.getAssetsBarcode());
                stockDetail.setInventoryCode(item.getInventoryCode());
                stockDetail.setAssetsCategoryId(item.getAssetsCategoryId());
                stockDetail.setAssetsBuyTime(item.getAssetsBuyTime());
                // 插入入库单的信息
                stockDetail.setPutInNo(item.getPutInNo());
                stockDetail.setPutInWarehouse(item.getPutInWarehouse());
                stockDetail.setPutInWarehouseName(item.getPutInWarehouseName());
                stockDetail.setAssetsProperty(Integer.parseInt(item.getAssetsProperty()));
                stockDetail.setAssetsFileId(item.getAssetsFileId());
                stockDetail.setStatus(1); //设置状态为 1 空闲
                stockDetail.setBeforeRepairStatus(1); // 设置维修之前的状态1
                stockDetail.setDel(1);
                stockDetail.setSchoolId(assetsInNew.getSchoolId());
                stockDetail.setPutInTime(item.getPutInTime());
                stockDetail.setAssetsCategoryAncestorId(item.getAssetsCategoryAncestorId());
                stockDetail.setUseTimeLimitDate(useTimelimitDate);
                stockDetail.setMaintenanceTimeLimitUnit(maintenanceTimelimitUnit);

                curSchoolYearService.setSchoolYearTerm(stockDetail,assetsInNew.getSchoolId());
//                CurSchoolYear curSchoolYear = schoolYearFeign.findCurSchoolYear(assetsInNew.getSchoolId());
//                stockDetail.setSchoolYearId(curSchoolYear.getSchoolYearId());
//                stockDetail.setTerm(curSchoolYear.getTerm());

                stockDetailList.add(stockDetail);
            }
            assetsStockDetailService.batchSaveAssetsStockDetail(stockDetailList);
        });


    }
    @Transactional(readOnly = true)
    public List<AssetsInNew> findAssetsInNewListByCondition(AssetsInNew assetsInNew) {
        return assetsInNewDao.findAssetsInNewListByCondition(assetsInNew);
    }
    @Transactional(readOnly = true)
    public AssetsInNew findOneAssetsInNewByCondition(AssetsInNew assetsInNew) {
        return assetsInNewDao.findOneAssetsInNewByCondition(assetsInNew);
    }
    @Transactional(readOnly = true)
    public long findAssetsInNewCountByCondition(AssetsInNew assetsInNew) {
        return assetsInNewDao.findAssetsInNewCountByCondition(assetsInNew);
    }
    @Transactional
    public void updateAssetsInNew(AssetsInNew assetsInNew) {
        assetsInNewDao.updateAssetsInNew(assetsInNew);
    }
    @Transactional
    public void deleteAssetsInNew(String id) {
        assetsInNewDao.deleteAssetsInNew(id);
    }
    @Transactional
    public void deleteAssetsInNewByCondition(AssetsInNew assetsInNew) {
        assetsInNewDao.deleteAssetsInNewByCondition(assetsInNew);
    }
    @Transactional
    public void batchSaveAssetsInNew(List<AssetsInNew> assetsInNews){
        assetsInNews.forEach(assetsInNew -> assetsInNew.setId(sequenceId.nextId()));
        assetsInNewDao.batchSaveAssetsInNew(assetsInNews);
    }

    public List<AssetsInNew> findAssetsNoListByCondition(AssetsInNew assetsInNew) {
        String[] putInTimeRange =  assetsInNew.getPutInTimeRange();

        if(putInTimeRange != null && putInTimeRange.length >0){
            assetsInNew.setSearchStartTime(putInTimeRange[0]);
            assetsInNew.setSearchEndTime(putInTimeRange[1]);
        }
        return assetsInNewDao.findAssetsNoListByCondition(assetsInNew);
    }

    public AssetsInNew findAssetsNoInfoByNo(AssetsInNew assetsInNew) {
        AssetsInNew assetsInNewRet = new AssetsInNew();

        List<AssetsInNew> assetsInNewList = assetsInNewDao.findAssetsNoListByCondition(assetsInNew);
        assetsInNewRet.setCreateTime(assetsInNewList.get(0).getCreateTime());
        assetsInNewRet.setRemark(assetsInNewList.get(0).getRemark());

        return assetsInNewRet;
    }

    public List<AssetsInNew> findAssetsInNewDetailByNo(AssetsInNew assetsInNew) {
        return assetsInNewDao.findAssetsInNewDetailByNo(assetsInNew);
    }

    public long findAssetsInNewDetailCountByNo(AssetsInNew assetsInNew) {
        return assetsInNewDao.findAssetsInNewDetailCountByNo(assetsInNew);
    }

    public long findAssetsNoCountByCondition(AssetsInNew assetsInNew) {
        String[] putInTimeRange =  assetsInNew.getPutInTimeRange();

        if(putInTimeRange != null && putInTimeRange.length >0){
            assetsInNew.setSearchStartTime(putInTimeRange[0]);
            assetsInNew.setSearchEndTime(putInTimeRange[1]);
        }
        return assetsInNewDao.findAssetsNoCountByCondition(assetsInNew);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
