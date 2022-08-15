package com.yice.edu.cn.xw.service.cms;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsSchoolSpace;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsSchoolSpaceConfig;
import com.yice.edu.cn.common.util.jmessage.utils.StringUtils;
import com.yice.edu.cn.xw.dao.cms.IXwCmsSchoolSpaceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class XwCmsSchoolSpaceService {
    @Autowired
    private IXwCmsSchoolSpaceDao xwCmsSchoolSpaceDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private XwCmsSchoolSpaceConfigService xwCmsSchoolSpaceConfigService;

/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public XwCmsSchoolSpace findXwCmsSchoolSpaceById(String id) {
        return xwCmsSchoolSpaceDao.findXwCmsSchoolSpaceById(id);
    }
    @Transactional
    public void saveXwCmsSchoolSpace(XwCmsSchoolSpace xwCmsSchoolSpace) {
        xwCmsSchoolSpace.setId(sequenceId.nextId());
        xwCmsSchoolSpace.setCreateTime(DateUtil.format(DateTime.now(),"yyyy-MM-dd HH:mm:ss"));
        xwCmsSchoolSpaceDao.saveXwCmsSchoolSpace(xwCmsSchoolSpace);
    }
    @Transactional(readOnly = true)
    public List<XwCmsSchoolSpace> findXwCmsSchoolSpaceListByCondition(XwCmsSchoolSpace xwCmsSchoolSpace) {
        return xwCmsSchoolSpaceDao.findXwCmsSchoolSpaceListByCondition(xwCmsSchoolSpace);
    }
    @Transactional(readOnly = true)
    public XwCmsSchoolSpace findOneXwCmsSchoolSpaceByCondition(XwCmsSchoolSpace xwCmsSchoolSpace) {
        return xwCmsSchoolSpaceDao.findOneXwCmsSchoolSpaceByCondition(xwCmsSchoolSpace);
    }

    public  XwCmsSchoolSpace findXwCmsSchoolSpaceByDomain(String secondDomain){
        return xwCmsSchoolSpaceDao.findXwCmsSchoolSpaceByDomain(secondDomain);
    }

    public List<XwCmsSchoolSpace> findProvinceList(){
        return xwCmsSchoolSpaceDao.findProvinceList();
    }

    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public long findXwCmsSchoolSpaceCountByCondition(XwCmsSchoolSpace xwCmsSchoolSpace) {
        return xwCmsSchoolSpaceDao.findXwCmsSchoolSpaceCountByCondition(xwCmsSchoolSpace);
    }
    @Transactional(rollbackFor = Exception.class)
    public void updateXwCmsSchoolSpace(XwCmsSchoolSpace xwCmsSchoolSpace) {
        if(Objects.nonNull(xwCmsSchoolSpace)) {
            if (StringUtils.isNotEmpty(xwCmsSchoolSpace.getSchoolId())) {
                //判断是否已经配置空间开关
                XwCmsSchoolSpaceConfig schoolSpaceConfigById = xwCmsSchoolSpaceConfigService.findSchoolSpaceConfigById(xwCmsSchoolSpace.getSchoolId());
                if(Objects.isNull(schoolSpaceConfigById)){
                    XwCmsSchoolSpaceConfig xwCmsSchoolSpaceConfig = new XwCmsSchoolSpaceConfig();
                    xwCmsSchoolSpaceConfig.setSchoolId(xwCmsSchoolSpace.getSchoolId());
                    xwCmsSchoolSpaceConfig.setStatus(1);
                    xwCmsSchoolSpaceConfigService.saveXwCmsSchoolSpaceConfig(xwCmsSchoolSpaceConfig);
                }
            }
        }
        xwCmsSchoolSpaceDao.updateXwCmsSchoolSpace(xwCmsSchoolSpace);
    }
    
    @Transactional(rollbackFor = Exception.class)
    public void updateXwCmsSchoolSpaceBySchoolId(XwCmsSchoolSpace xwCmsSchoolSpace){
        xwCmsSchoolSpaceDao.updateXwCmsSchoolSpaceBySchoolId(xwCmsSchoolSpace);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteXwCmsSchoolSpace(String id) {
        xwCmsSchoolSpaceDao.deleteXwCmsSchoolSpace(id);
    }
    @Transactional
    public void deleteXwCmsSchoolSpaceByCondition(XwCmsSchoolSpace xwCmsSchoolSpace) {
        xwCmsSchoolSpaceDao.deleteXwCmsSchoolSpaceByCondition(xwCmsSchoolSpace);
    }
    @Transactional(rollbackFor = Exception.class)
    public void batchSaveXwCmsSchoolSpace(List<XwCmsSchoolSpace> xwCmsSchoolSpaces){
        xwCmsSchoolSpaces.forEach(xwCmsSchoolSpace -> xwCmsSchoolSpace.setId(sequenceId.nextId()));
        xwCmsSchoolSpaceDao.batchSaveXwCmsSchoolSpace(xwCmsSchoolSpaces);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
