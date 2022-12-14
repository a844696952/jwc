package com.yice.edu.cn.xw.service.zc.assetsStockDetail;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.xw.zc.AssetsResQrcode;
import com.yice.edu.cn.common.pojo.xw.zc.assetsStockDetail.AssetsStockDetail;
import com.yice.edu.cn.common.util.crypto.SimpleCryptoKit;
import com.yice.edu.cn.common.util.qrcode.QRCodeUtil;
import com.yice.edu.cn.xw.dao.zc.assetsStockDetail.IAssetsResQrcodeDaoNew;
import com.yice.edu.cn.xw.dao.zc.assetsStockDetail.IAssetsStockDetailDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssetsResQrcodeServiceNew {
    @Autowired
    private IAssetsResQrcodeDaoNew assetsResQrcodeDao;
    @Autowired
    private IAssetsStockDetailDao assetsStockDetailDao;
    @Autowired
    private SequenceId sequenceId;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public AssetsResQrcode findAssetsResQrcodeById(String id) {
        return assetsResQrcodeDao.findAssetsResQrcodeById(id);
    }
    @Transactional
    public void saveAssetsResQrcode(AssetsResQrcode assetsResQrcode) {
        assetsResQrcodeDao.saveAssetsResQrcode(assetsResQrcode);
    }
    @Transactional(readOnly = true)
    public List<AssetsResQrcode> findAssetsResQrcodeListByCondition(AssetsResQrcode assetsResQrcode) {
        return assetsResQrcodeDao.findAssetsResQrcodeListByCondition(assetsResQrcode);
    }
    @Transactional(readOnly = true)
    public AssetsResQrcode findOneAssetsResQrcodeByCondition(AssetsResQrcode assetsResQrcode) {
        return assetsResQrcodeDao.findOneAssetsResQrcodeByCondition(assetsResQrcode);
    }
    @Transactional(readOnly = true)
    public long findAssetsResQrcodeCountByCondition(AssetsResQrcode assetsResQrcode) {
        return assetsResQrcodeDao.findAssetsResQrcodeCountByCondition(assetsResQrcode);
    }
    @Transactional
    public void updateAssetsResQrcode(AssetsResQrcode assetsResQrcode) {
        assetsResQrcodeDao.updateAssetsResQrcode(assetsResQrcode);
    }
    @Transactional
    public void updateAssetsResQrcodeForNotNull(AssetsResQrcode assetsResQrcode) {
        assetsResQrcodeDao.updateAssetsResQrcodeForNotNull(assetsResQrcode);
    }
    @Transactional
    public void deleteAssetsResQrcode(String id) {
        assetsResQrcodeDao.deleteAssetsResQrcode(id);
    }
    @Transactional
    public void deleteAssetsResQrcodeByCondition(AssetsResQrcode assetsResQrcode) {
        assetsResQrcodeDao.deleteAssetsResQrcodeByCondition(assetsResQrcode);
    }
    @Transactional
    public void batchSaveAssetsResQrcode(List<AssetsResQrcode> assetsResQrcodes){
        assetsResQrcodeDao.batchSaveAssetsResQrcode(assetsResQrcodes);
    }

    @Transactional
    public int findAssetsResQrcodeCountByAssetsResIds(List<String> assetsResIds) {
        return assetsResQrcodeDao.findAssetsResQrcodeCountByAssetsResIds(assetsResIds);
    }

    /**
     * ???????????????????????????????????????
     * TODO ?????????????????? Future
     * @param assetsResIds
     */
    @Transactional(rollbackFor = Exception.class)
    @Async
    public void createQrCodes(List<String> assetsResIds) {
        //??????????????????????????????
        List<String> hasssetsResIds = assetsResQrcodeDao.findHasQrcodeByAssetsResIds(assetsResIds);
        if(hasssetsResIds!=null){
            assetsResIds.removeAll(hasssetsResIds);
        }

//        assetsResQrcodeDao.deleteQrcodeByAssetsResIds(assetsResIds);

        //??????????????????????????? ID????????? ???????????????
        final List<AssetsStockDetail> assetsStockDetailList = assetsStockDetailDao.findAssetsResListByAssetsResIds(assetsResIds);
        List<AssetsResQrcode> assetsResQrcodes = assetsStockDetailList.stream()
                .parallel()
                .map(stockDetail ->{
                    final AssetsResQrcode assetsResQrcode = new AssetsResQrcode();
                    assetsResQrcode.setAssetsResId(stockDetail.getId());
                    assetsResQrcode.setAssetsName(stockDetail.getAssetsName());
                    assetsResQrcode.setSchoolId(stockDetail.getSchoolId());
                    assetsResQrcode.setSerialNumber(stockDetail.getAssetsNo());

                    final String assetsName = assetsResQrcode.getAssetsName();
                    final String assetsNo = assetsResQrcode.getSerialNumber();//????????????
                    String enAssetsNo = SimpleCryptoKit.encrypt(assetsNo);
                    byte[] encode = new byte[0];
                    String url = "https://www.ycjdedu.com/xw/zc/" + enAssetsNo;
                    try {

                        encode = QRCodeUtil.encode(url, null, assetsName, assetsNo);
                    } catch (Exception e) {

                        e.printStackTrace();
                    }

                    assetsResQrcode.setQrCode(encode);
                    return assetsResQrcode;
                }).collect(Collectors.toList());

        assetsResQrcodeDao.batchSaveAssetsResQrcode(assetsResQrcodes);

    }

    @Transactional(readOnly = true)
    public List<AssetsResQrcode> findAssetsResQrcodes(List<String> assetsResIds) {
        return assetsResQrcodeDao.findAssetsResQrcodes(assetsResIds);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
