package com.yice.edu.cn.jw.service.teacher;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.teacher.JwCmsAddress;
import com.yice.edu.cn.common.pojo.jw.teacher.JwTeacherCms;
import com.yice.edu.cn.jw.dao.teacher.IJwCmsAddressDao;
import com.yice.edu.cn.jw.dao.teacher.IJwTeacherCmsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JwTeacherCmsService {
    @Autowired
    private IJwTeacherCmsDao jwTeacherCmsDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private IJwCmsAddressDao cmsAddressDao;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public JwTeacherCms findJwTeacherCmsById(String id) {
        return jwTeacherCmsDao.findJwTeacherCmsById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveJwTeacherCms(JwTeacherCms jwTeacherCms) {
        JwTeacherCms model = new JwTeacherCms();
        model.setTeacherId(jwTeacherCms.getTeacherId());
        List<JwTeacherCms> list = jwTeacherCmsDao.findJwTeacherCmsListByCondition(model);
        if (CollectionUtil.isNotEmpty(list)) {
            jwTeacherCms.setId(list.get(0).getId());
            jwTeacherCms.setUpdateTime(DateUtil.now());
            updateJwTeacherCms(jwTeacherCms);
        } else {
            jwTeacherCms.setId(sequenceId.nextId());
            jwTeacherCms.setCreateTime(DateUtil.now());
            jwTeacherCmsDao.saveJwTeacherCms(jwTeacherCms);
        }
        //返回地址给前端
        List<JwCmsAddress> address = cmsAddressDao.getJwCmsAddress();
        if (CollectionUtil.isNotEmpty(address)) {
            jwTeacherCms.setLoginUrl(address.get(0).getLoginUrl());
            jwTeacherCms.setEncryptionPassword(Base64.encode(jwTeacherCms.getPassword()));
        }
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<JwTeacherCms> findJwTeacherCmsListByCondition(JwTeacherCms jwTeacherCms) {
        List<JwTeacherCms> list = jwTeacherCmsDao.findJwTeacherCmsListByCondition(jwTeacherCms);
        List<JwCmsAddress> address = cmsAddressDao.getJwCmsAddress();
        if (CollectionUtil.isNotEmpty(list) && CollectionUtil.isNotEmpty(address)) {
            list.forEach(item -> {
                item.setEncryptionPassword(Base64.encode(item.getPassword()));
                item.setLoginUrl(address.get(0).getLoginUrl());
            });
        }
        return list;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public JwTeacherCms findOneJwTeacherCmsByCondition(JwTeacherCms jwTeacherCms) {
        JwTeacherCms detail = jwTeacherCmsDao.findOneJwTeacherCmsByCondition(jwTeacherCms);
        if (detail != null && StrUtil.isNotBlank(detail.getPassword())) {
            detail.setEncryptionPassword(Base64.encode(detail.getPassword()));
        }
        List<JwCmsAddress> address = cmsAddressDao.getJwCmsAddress();
        if (detail != null && CollectionUtil.isNotEmpty(address)) {
            detail.setLoginUrl(address.get(0).getLoginUrl());
        }
        return detail;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public long findJwTeacherCmsCountByCondition(JwTeacherCms jwTeacherCms) {
        return jwTeacherCmsDao.findJwTeacherCmsCountByCondition(jwTeacherCms);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateJwTeacherCms(JwTeacherCms jwTeacherCms) {
        jwTeacherCmsDao.updateJwTeacherCms(jwTeacherCms);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteJwTeacherCms(String id) {
        jwTeacherCmsDao.deleteJwTeacherCms(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteJwTeacherCmsByCondition(JwTeacherCms jwTeacherCms) {
        jwTeacherCmsDao.deleteJwTeacherCmsByCondition(jwTeacherCms);
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchSaveJwTeacherCms(List<JwTeacherCms> jwTeacherCmss) {
        jwTeacherCmss.forEach(jwTeacherCms -> jwTeacherCms.setId(sequenceId.nextId()));
        jwTeacherCmsDao.batchSaveJwTeacherCms(jwTeacherCmss);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
