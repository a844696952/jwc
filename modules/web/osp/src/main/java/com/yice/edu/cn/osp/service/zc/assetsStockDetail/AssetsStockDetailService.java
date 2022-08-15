package com.yice.edu.cn.osp.service.zc.assetsStockDetail;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.xw.zc.AssetsQrcodeMsg;
import com.yice.edu.cn.common.pojo.xw.zc.AssetsResQrcode;
import com.yice.edu.cn.common.pojo.xw.zc.assetsFile.AssetsFile;
import com.yice.edu.cn.common.pojo.xw.zc.assetsStockDetail.AssertStockDetailExcelExport;
import com.yice.edu.cn.common.pojo.xw.zc.assetsStockDetail.AssetsStockDetail;
import com.yice.edu.cn.osp.feignClient.zc.assetsStockDetail.AssetsStockDetailFeign;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AssetsStockDetailService {
    @Autowired
    private AssetsStockDetailFeign assetsStockDetailFeign;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public AssetsStockDetail findAssetsStockDetailById(String id) {
        return assetsStockDetailFeign.findAssetsStockDetailById(id);
    }

    public AssetsStockDetail saveAssetsStockDetail(AssetsStockDetail assetsStockDetail) {
        return assetsStockDetailFeign.saveAssetsStockDetail(assetsStockDetail);
    }

    public List<AssetsStockDetail> findAssetsStockDetailListByCondition(AssetsStockDetail assetsStockDetail) {
        return assetsStockDetailFeign.findAssetsStockDetailListByCondition(assetsStockDetail);
    }

    public AssetsStockDetail findOneAssetsStockDetailByCondition(AssetsStockDetail assetsStockDetail) {
        return assetsStockDetailFeign.findOneAssetsStockDetailByCondition(assetsStockDetail);
    }

    public long findAssetsStockDetailCountByCondition(AssetsStockDetail assetsStockDetail) {
        return assetsStockDetailFeign.findAssetsStockDetailCountByCondition(assetsStockDetail);
    }

    public void updateAssetsStockDetail(AssetsStockDetail assetsStockDetail) {
        assetsStockDetailFeign.updateAssetsStockDetail(assetsStockDetail);
    }

    public void updateAssetsStockDetailForNotNull(AssetsStockDetail assetsStockDetail) {
        assetsStockDetailFeign.updateAssetsStockDetailForNotNull(assetsStockDetail);
    }

    public void deleteAssetsStockDetail(String id) {
        assetsStockDetailFeign.deleteAssetsStockDetail(id);
    }

    public void deleteAssetsStockDetailByCondition(AssetsStockDetail assetsStockDetail) {
        assetsStockDetailFeign.deleteAssetsStockDetailByCondition(assetsStockDetail);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    public AssetsStockDetail findAssetsStockDetailJoinTableById(String id) {
        return assetsStockDetailFeign.findAssetsStockDetailJoinTableById(id);
    }

    //首页模糊查询
    public List<AssetsStockDetail> findAssetsStockDetailListByFuzzyCondition(AssetsStockDetail assetsStockDetail) {
        return assetsStockDetailFeign.findAssetsStockDetailListByFuzzyCondition(assetsStockDetail);
    }

    //首页模糊查询
    public long findAssetsStockDetailCountByFuzzyCondition(AssetsStockDetail assetsStockDetail) {
        return assetsStockDetailFeign.findAssetsStockDetailCountByFuzzyCondition(assetsStockDetail);
    }

    public Workbook exportAssetsStockDetail(List<AssetsStockDetail> data) {

        List<AssertStockDetailExcelExport> listExcels = data.stream().map(ss -> {
            AssertStockDetailExcelExport excel = new AssertStockDetailExcelExport();
            BeanUtils.copyProperties(ss, excel);
            excel.setAssetsProperty(ss.getAssetsProperty() == 1? "固定资产" : "易耗品");
            switch (ss.getStatus()){
                case 1: excel.setStatus("空闲"); break;
                case 2: excel.setStatus("占用"); break;
                case 3: excel.setStatus("维修中"); break;
                case 4: excel.setStatus("故障"); break;
                case 5: excel.setStatus("报废"); break;
            }
            excel.setIsOverValidTime(ss.getIsOverValidTime().equals("1")? "是" : "否");
            excel.setIsOverMaintenanceTime(ss.getIsOverMaintenanceTime().equals("1")? "是" : "否");
            return excel;
        }).collect(Collectors.toList());
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "资产库存明细"), AssertStockDetailExcelExport.class, listExcels);
        return workbook;
    }


    public List<AssetsStockDetail> findAssetsUseRecordById(AssetsStockDetail assetsStockDetail) {
        return assetsStockDetailFeign.findAssetsUseRecordById(assetsStockDetail);
    }


    public long findAssetsUseRecordCountById(AssetsStockDetail assetsStockDetail) {
        return assetsStockDetailFeign.findAssetsUseRecordCountById(assetsStockDetail);
    }


    @CreateCache(name=Constant.Redis.ZC_QRCODE_ASSETSRESIDS,expire = 300)
    private Cache<String, Set<String>> qrcodeSetCache;

    public AssetsQrcodeMsg createQrCodes(List<String> assetsResIds,String schoolId) {
        AssetsQrcodeMsg assetsQrcodeMsg = assetsStockDetailFeign.getAssetsQrcodeMsg(assetsResIds);
        Set<String> cachedQrcodeIds = qrcodeSetCache.get(schoolId);
        if(!assetsQrcodeMsg.getWait()){
            if(cachedQrcodeIds!=null&&!cachedQrcodeIds.isEmpty()){
                cachedQrcodeIds.removeAll(assetsResIds);
                qrcodeSetCache.put(schoolId,cachedQrcodeIds);
            }
            return assetsQrcodeMsg;
        }else{

            if(cachedQrcodeIds==null||cachedQrcodeIds.isEmpty()){
                Set<String> assetsResIdsSet = new HashSet<>(assetsResIds);
                qrcodeSetCache.put(schoolId, assetsResIdsSet);
                AssetsQrcodeMsg assetsQrcodeMsg2 = assetsStockDetailFeign.createQrCodes(assetsResIds);
                if(!assetsQrcodeMsg.getWait()){
                    qrcodeSetCache.put(schoolId,null);
                }
                return assetsQrcodeMsg2;
            }else{
                //先判断redis中的ids和传过来的ids有没有重复，有重复就从传过来的ids里面找出不重复的ids，然后交给服务去生成
                List<String> tempAssetsResIds = new ArrayList<>(assetsResIds);
                assetsResIds.removeAll(cachedQrcodeIds);
                if(assetsResIds.isEmpty()){
                    return new AssetsQrcodeMsg("正在生成二维码，请稍候......",true);
                }

                AssetsQrcodeMsg assetsQrcodeMsg3 = assetsStockDetailFeign.createQrCodes(assetsResIds);
                if(!assetsQrcodeMsg.getWait()){
                    cachedQrcodeIds.removeAll(tempAssetsResIds);
                    qrcodeSetCache.put(schoolId,cachedQrcodeIds);
                }else{
                    //在将两个list用set去重 存到redis
                    cachedQrcodeIds.addAll(assetsResIds);
                    qrcodeSetCache.put(schoolId,cachedQrcodeIds);
                }
                return assetsQrcodeMsg3;
            }
        }
    }

    public List<AssetsResQrcode> findAssetsResQrcodes(List<String> ids) {
        return assetsStockDetailFeign.findAssetsResQrcodes(ids);
    }

//    public List<Semester> findSemesterListByCondition(Semester semester) {
//        return assetsStockDetailFeign.findSemesterListByCondition(semester);
//    }

    public void updateAssetsStockDetailForNotNullByCondition(AssetsStockDetail assetsStockDetail) {
        assetsStockDetailFeign.updateAssetsStockDetailForNotNullByCondition(assetsStockDetail);
    }

    /**
     * 查询资产使用百分比
     * @param assetsStockDetail
     * @return
     */
    public String findAssetsUsePercentage(AssetsStockDetail assetsStockDetail) {
        return assetsStockDetailFeign.findAssetsUsePercentage(assetsStockDetail);
    }

    /**
     * assetsProperty 1:固定  2：易耗
     * 查询最近一年资产使用记录
     * @param assetsStockDetail
     * @return
     */
    public List<AssetsStockDetail> findRecentOneYearAssertsCount(AssetsStockDetail assetsStockDetail) {
        return assetsStockDetailFeign.findRecentOneYearAssertsCount(assetsStockDetail);
    }

    /**
     * assetsProperty 1:固定  2：易耗
     * 查询最近一个月资产使用记录
     * @param assetsStockDetail
     * @return
     */
    public List<AssetsStockDetail> findRecentOneMonthAssertsCount(AssetsStockDetail assetsStockDetail) {
        return assetsStockDetailFeign.findRecentOneMonthAssertsCount(assetsStockDetail);
    }


    /**
     * assetsProperty  1:固定资产 2：易耗品
     * 查询学校的各种资产的总价
     * @param assetsStockDetail
     * @return
     */
    public List<AssetsFile> findFileTotalPrice(AssetsStockDetail assetsStockDetail) {
        return assetsStockDetailFeign.findFileTotalPrice(assetsStockDetail);
    }
}
