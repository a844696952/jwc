package com.yice.edu.cn.yed.service.jy;

import com.yice.edu.cn.common.pojo.jy.sensitiveWord.SensitiveWord;
import com.yice.edu.cn.yed.feignClient.jy.sensitiveWord.SensitiveWordFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensitiveWordService {
    @Autowired
    private SensitiveWordFeign sensitiveWordFeign;

    public SensitiveWord findSensitiveWordById(String id) {
        return sensitiveWordFeign.findSensitiveWordById(id);
    }

    public SensitiveWord saveSensitiveWord(SensitiveWord sensitiveWord) {
        return sensitiveWordFeign.saveSensitiveWord(sensitiveWord);
    }

    public List<SensitiveWord> findSensitiveWordListByCondition(SensitiveWord sensitiveWord) {
        return sensitiveWordFeign.findSensitiveWordListByCondition(sensitiveWord);
    }

    public SensitiveWord findOneSensitiveWordByCondition(SensitiveWord sensitiveWord) {
        return sensitiveWordFeign.findOneSensitiveWordByCondition(sensitiveWord);
    }

    public long findSensitiveWordCountByCondition(SensitiveWord sensitiveWord) {
        return sensitiveWordFeign.findSensitiveWordCountByCondition(sensitiveWord);
    }

    public void updateSensitiveWord(SensitiveWord sensitiveWord) {
        sensitiveWordFeign.updateSensitiveWord(sensitiveWord);
    }

    public void deleteSensitiveWord(String id) {
        sensitiveWordFeign.deleteSensitiveWord(id);
    }

    public void deleteSensitiveWordByCondition(SensitiveWord sensitiveWord) {
        sensitiveWordFeign.deleteSensitiveWordByCondition(sensitiveWord);
    }
}
