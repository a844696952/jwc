package com.yice.edu.cn.osp.service.xw.workerKq;

import com.yice.edu.cn.common.pojo.xw.workerKq.CommenSettings;
import com.yice.edu.cn.osp.feignClient.xw.workerKq.CommenSettingsFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommenSettingsService {
    @Autowired
    private CommenSettingsFeign commenSettingsFeign;

    public CommenSettings findCommenSettingsById(String id) {
        return commenSettingsFeign.findCommenSettingsById(id);
    }

    public CommenSettings saveCommenSettings(CommenSettings commenSettings) {
        return commenSettingsFeign.saveCommenSettings(commenSettings);
    }

    public List<CommenSettings> findCommenSettingsListByCondition(CommenSettings commenSettings) {
        return commenSettingsFeign.findCommenSettingsListByCondition(commenSettings);
    }

    public CommenSettings findOneCommenSettingsByCondition(CommenSettings commenSettings) {
        return commenSettingsFeign.findOneCommenSettingsByCondition(commenSettings);
    }

    public long findCommenSettingsCountByCondition(CommenSettings commenSettings) {
        return commenSettingsFeign.findCommenSettingsCountByCondition(commenSettings);
    }

    public void updateCommenSettings(CommenSettings commenSettings) {
        commenSettingsFeign.updateCommenSettings(commenSettings);
    }

    public void deleteCommenSettings(String id) {
        commenSettingsFeign.deleteCommenSettings(id);
    }

    public void deleteCommenSettingsByCondition(CommenSettings commenSettings) {
        commenSettingsFeign.deleteCommenSettingsByCondition(commenSettings);
    }
}
