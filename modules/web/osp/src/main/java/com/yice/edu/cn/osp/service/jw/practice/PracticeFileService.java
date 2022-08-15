package com.yice.edu.cn.osp.service.jw.practice;

import com.yice.edu.cn.common.pojo.jw.practice.PracticeFile;
import com.yice.edu.cn.osp.feignClient.jw.practice.PracticeFileFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class PracticeFileService {
    @Autowired
    private PracticeFileFeign practiceFileFeign;

    public PracticeFile findPracticeFileById(String id) {
        return practiceFileFeign.findPracticeFileById(id);
    }

    public PracticeFile savePracticeFile(PracticeFile practiceFile) {
        return practiceFileFeign.savePracticeFile(practiceFile);
    }

    public List<PracticeFile> findPracticeFileListByCondition(PracticeFile practiceFile) {
        return practiceFileFeign.findPracticeFileListByCondition(practiceFile);
    }

    public PracticeFile findOnePracticeFileByCondition(PracticeFile practiceFile) {
        return practiceFileFeign.findOnePracticeFileByCondition(practiceFile);
    }

    public long findPracticeFileCountByCondition(PracticeFile practiceFile) {
        return practiceFileFeign.findPracticeFileCountByCondition(practiceFile);
    }

    public void updatePracticeFile(PracticeFile practiceFile) {
        practiceFileFeign.updatePracticeFile(practiceFile);
    }

    public void deletePracticeFile(String id) {
        practiceFileFeign.deletePracticeFile(id);
    }

    public void deletePracticeFileByCondition(PracticeFile practiceFile) {
        practiceFileFeign.deletePracticeFileByCondition(practiceFile);
    }
    public List<PracticeFile> findPracticeFileListById(String id){
       return practiceFileFeign.findPracticeFileListById(id);
    }
}
