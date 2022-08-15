package com.yice.edu.cn.osp.service.xw.dj.djLedger;




import com.yice.edu.cn.common.pojo.xw.dj.djLedgerTemplate.DjSchoolLedger;
import com.yice.edu.cn.osp.feignClient.xw.dj.djLedger.DjSchoolLedgerFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

@Service
public class DjSchoolLedgerService {
    @Autowired
    private DjSchoolLedgerFeign djSchoolLedgerFeign;
    public String appRootPath;

    public String handledBase64Block;

    public String xmlimaHref;
    public DjSchoolLedger findDjSchoolLedgerById(String id) {
        return djSchoolLedgerFeign.findDjSchoolLedgerById(id);
    }

    public DjSchoolLedger saveDjSchoolLedger(DjSchoolLedger djSchoolLedger) {
        return djSchoolLedgerFeign.saveDjSchoolLedger(djSchoolLedger);
    }

    public List<DjSchoolLedger> findDjSchoolLedgerListByCondition(DjSchoolLedger djSchoolLedger) {
        return djSchoolLedgerFeign.findDjSchoolLedgerListByCondition(djSchoolLedger);
    }

    public DjSchoolLedger findOneDjSchoolLedgerByCondition(DjSchoolLedger djSchoolLedger) {
        return djSchoolLedgerFeign.findOneDjSchoolLedgerByCondition(djSchoolLedger);
    }

    public long findDjSchoolLedgerCountByCondition(DjSchoolLedger djSchoolLedger) {
        return djSchoolLedgerFeign.findDjSchoolLedgerCountByCondition(djSchoolLedger);
    }

    public void updateDjSchoolLedger(DjSchoolLedger djSchoolLedger) {
        djSchoolLedgerFeign.updateDjSchoolLedger(djSchoolLedger);
    }

    public void deleteDjSchoolLedger(String id) {
        djSchoolLedgerFeign.deleteDjSchoolLedger(id);
    }

    public void deleteDjSchoolLedgerByCondition(DjSchoolLedger djSchoolLedger) {
        djSchoolLedgerFeign.deleteDjSchoolLedgerByCondition(djSchoolLedger);
    }




    public void doExport (String id, HttpServletResponse response , HttpServletRequest request){
        response.reset();
        response.setHeader("content-type","application/msword");
        response.setHeader("Content-Disposition", "attachment;filename=" + "fff.doc");
        OutputStream os = null;
        try{
            os = response.getOutputStream();
            DjSchoolLedger djSchoolLedgerById = djSchoolLedgerFeign.findDjSchoolLedgerById(id);
            String ledgerContent = djSchoolLedgerById.getLedgerContent();
            byte[] bytes = ledgerContent.getBytes(Charset.forName("UTF-8"));
            // 将字节流传入到响应流里,响应到浏览器    os.write(bytes);    os.close();
            os.write(bytes);
            os.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
