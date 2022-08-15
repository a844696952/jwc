package com.yice.edu.cn.osp.service.jy.titleQuota;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.jy.titleQuota.HistoryTeacherAes;
import com.yice.edu.cn.osp.feignClient.jy.titleQuota.HistoryTeacherAesFeign;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class HistoryTeacherAesService {
    @Autowired
    private HistoryTeacherAesFeign historyTeacherAesFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public HistoryTeacherAes findHistoryTeacherAesById(String id) {
        return historyTeacherAesFeign.findHistoryTeacherAesById(id);
    }

    public HistoryTeacherAes saveHistoryTeacherAes(HistoryTeacherAes historyTeacherAes) {
        return historyTeacherAesFeign.saveHistoryTeacherAes(historyTeacherAes);
    }

    public void batchSaveHistoryTeacherAes(List<HistoryTeacherAes> historyTeacherAess){
        historyTeacherAesFeign.batchSaveHistoryTeacherAes(historyTeacherAess);
    }

    public List<HistoryTeacherAes> findHistoryTeacherAesListByCondition(HistoryTeacherAes historyTeacherAes) {
        return historyTeacherAesFeign.findHistoryTeacherAesListByCondition(historyTeacherAes);
    }

    public HistoryTeacherAes findOneHistoryTeacherAesByCondition(HistoryTeacherAes historyTeacherAes) {
        return historyTeacherAesFeign.findOneHistoryTeacherAesByCondition(historyTeacherAes);
    }

    public long findHistoryTeacherAesCountByCondition(HistoryTeacherAes historyTeacherAes) {
        return historyTeacherAesFeign.findHistoryTeacherAesCountByCondition(historyTeacherAes);
    }

    public void updateHistoryTeacherAes(HistoryTeacherAes historyTeacherAes) {
        historyTeacherAesFeign.updateHistoryTeacherAes(historyTeacherAes);
    }

    public void updateHistoryTeacherAesForAll(HistoryTeacherAes historyTeacherAes) {
        historyTeacherAesFeign.updateHistoryTeacherAesForAll(historyTeacherAes);
    }

    public void deleteHistoryTeacherAes(String id) {
        historyTeacherAesFeign.deleteHistoryTeacherAes(id);
    }

    public void deleteHistoryTeacherAesByCondition(HistoryTeacherAes historyTeacherAes) {
        historyTeacherAesFeign.deleteHistoryTeacherAesByCondition(historyTeacherAes);
    }


    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public HistoryTeacherAes findIsExist(HistoryTeacherAes historyTeacherAes) {
        return   historyTeacherAesFeign.findIsExist(historyTeacherAes);
    }

    public HistoryTeacherAes findIsDownload(HistoryTeacherAes historyTeacherAes) {
        return  historyTeacherAesFeign.findIsDownload(historyTeacherAes);
    }

    public HistoryTeacherAes findByTeacherIdVist(HistoryTeacherAes historyTeacherAes) {
        return historyTeacherAesFeign.findByTeacherIdVist(historyTeacherAes);
    }

    public List<HistoryTeacherAes> findHistoryTeacherAesCountByCondition4Like(HistoryTeacherAes historyTeacherAes) {
        return historyTeacherAesFeign.findHistoryTeacherAesCountByCondition4Like(historyTeacherAes);
    }

    public List<Map<String,Object>> findHistoryTeacherAesListByCondition4Like(HistoryTeacherAes historyTeacherAes) {
        return  historyTeacherAesFeign.findHistoryTeacherAesListByCondition4Like(historyTeacherAes);
    }
}
