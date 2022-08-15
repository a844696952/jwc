package com.yice.edu.cn.dm.service.studentAspect;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.dm.studentAspect.DmStudentAspect;
import com.yice.edu.cn.dm.dao.studentAspect.IDmStudentAspectDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DmStudentAspectService {
    @Autowired
    private IDmStudentAspectDao dmStudentAspectDao;

/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public DmStudentAspect findDmStudentAspectById(String id) {
        return dmStudentAspectDao.findDmStudentAspectById(id);
    }

    @Transactional(readOnly = true)
    public List<DmStudentAspect> findDmStudentAspectListByCondition(DmStudentAspect dmStudentAspect) {
        return dmStudentAspectDao.findDmStudentAspectListByCondition(dmStudentAspect);
    }
    @Transactional(readOnly = true)
    public DmStudentAspect findOneDmStudentAspectByCondition(DmStudentAspect dmStudentAspect) {
        return dmStudentAspectDao.findOneDmStudentAspectByCondition(dmStudentAspect);
    }
    @Transactional(readOnly = true)
    public long findDmStudentAspectCountByCondition(DmStudentAspect dmStudentAspect) {
        return dmStudentAspectDao.findDmStudentAspectCountByCondition(dmStudentAspect);
    }
    @Transactional
    public void updateDmStudentAspect(DmStudentAspect dmStudentAspect) {
        dmStudentAspectDao.updateDmStudentAspect(dmStudentAspect);
    }
    @Transactional
    public void updateDmStudentAspectForAll(DmStudentAspect dmStudentAspect) {
        dmStudentAspectDao.updateDmStudentAspectForAll(dmStudentAspect);
    }
    @Transactional
    public void deleteDmStudentAspect(String id) {
        dmStudentAspectDao.deleteDmStudentAspect(id);
    }
    @Transactional
    public void deleteDmStudentAspectByCondition(DmStudentAspect dmStudentAspect) {
        dmStudentAspectDao.deleteDmStudentAspectByCondition(dmStudentAspect);
    }
    @Transactional
    public void batchSaveDmStudentAspect(List<DmStudentAspect> dmStudentAspects){
        dmStudentAspectDao.batchSaveDmStudentAspect(dmStudentAspects);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/


    @Transactional(rollbackFor = Exception.class)
    public void saveDmStudentAspect(DmStudentAspect dmStudentAspect) {
        //先查询studentId,有没有
        DmStudentAspect find = new DmStudentAspect();
        find.setSchoolId(dmStudentAspect.getSchoolId());
        find.setStudentId(dmStudentAspect.getStudentId());
        //一般来说只可能为一条，但是为了容错处理还是用list接收
        List<DmStudentAspect> list = dmStudentAspectDao.findDmStudentAspectListByCondition(find);
        if(CollectionUtil.isNotEmpty(list)){
            list.forEach(x ->{
                BeanUtils.copyProperties(dmStudentAspect,x, "id","studentId","schoolId");
                dmStudentAspectDao.updateDmStudentAspect(x);
            });
        }else{
            dmStudentAspectDao.saveDmStudentAspect(dmStudentAspect);
        }


    }
}
