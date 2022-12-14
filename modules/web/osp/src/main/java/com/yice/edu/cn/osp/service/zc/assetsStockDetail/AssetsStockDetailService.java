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

    //??????????????????
    public List<AssetsStockDetail> findAssetsStockDetailListByFuzzyCondition(AssetsStockDetail assetsStockDetail) {
        return assetsStockDetailFeign.findAssetsStockDetailListByFuzzyCondition(assetsStockDetail);
    }

    //??????????????????
    public long findAssetsStockDetailCountByFuzzyCondition(AssetsStockDetail assetsStockDetail) {
        return assetsStockDetailFeign.findAssetsStockDetailCountByFuzzyCondition(assetsStockDetail);
    }

    public Workbook exportAssetsStockDetail(List<AssetsStockDetail> data) {

        List<AssertStockDetailExcelExport> listExcels = data.stream().map(ss -> {
            AssertStockDetailExcelExport excel = new AssertStockDetailExcelExport();
            BeanUtils.copyProperties(ss, excel);
            excel.setAssetsProperty(ss.getAssetsProperty() == 1? "????????????" : "?????????");
            switch (ss.getStatus()){
                case 1: excel.setStatus("??????"); break;
                case 2: excel.setStatus("??????"); break;
                case 3: excel.setStatus("?????????"); break;
                case 4: excel.setStatus("??????"); break;
                case 5: excel.setStatus("??????"); break;
            }
            excel.setIsOverValidTime(ss.getIsOverValidTime().equals("1")? "???" : "???");
            excel.setIsOverMaintenanceTime(ss.getIsOverMaintenanceTime().equals("1")? "???" : "???");
            return excel;
        }).collect(Collectors.toList());
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "??????????????????"), AssertStockDetailExcelExport.class, listExcels);
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
                //?????????redis??????ids???????????????ids?????????????????????????????????????????????ids????????????????????????ids??????????????????????????????
                List<String> tempAssetsResIds = new ArrayList<>(assetsResIds);
                assetsResIds.removeAll(cachedQrcodeIds);
                if(assetsResIds.isEmpty()){
                    return new AssetsQrcodeMsg("?????????????????????????????????......",true);
                }

                AssetsQrcodeMsg assetsQrcodeMsg3 = assetsStockDetailFeign.createQrCodes(assetsResIds);
                if(!assetsQrcodeMsg.getWait()){
                    cachedQrcodeIds.removeAll(tempAssetsResIds);
                    qrcodeSetCache.put(schoolId,cachedQrcodeIds);
                }else{
                    //????????????list???set?????? ??????redis
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
     * ???????????????????????????
     * @param assetsStockDetail
     * @return
     */
    public String findAssetsUsePercentage(AssetsStockDetail assetsStockDetail) {
        return assetsStockDetailFeign.findAssetsUsePercentage(assetsStockDetail);
    }

    /**
     * assetsProperty 1:??????  2?????????
     * ????????????????????????????????????
     * @param assetsStockDetail
     * @return
     */
    public List<AssetsStockDetail> findRecentOneYearAssertsCount(AssetsStockDetail assetsStockDetail) {
        return assetsStockDetailFeign.findRecentOneYearAssertsCount(assetsStockDetail);
    }

    /**
     * assetsProperty 1:??????  2?????????
     * ???????????????????????????????????????
     * @param assetsStockDetail
     * @return
     */
    public List<AssetsStockDetail> findRecentOneMonthAssertsCount(AssetsStockDetail assetsStockDetail) {
        return assetsStockDetailFeign.findRecentOneMonthAssertsCount(assetsStockDetail);
    }


    /**
     * assetsProperty  1:???????????? 2????????????
     * ????????????????????????????????????
     * @param assetsStockDetail
     * @return
     */
    public List<AssetsFile> findFileTotalPrice(AssetsStockDetail assetsStockDetail) {
        return assetsStockDetailFeign.findFileTotalPrice(assetsStockDetail);
    }
}
