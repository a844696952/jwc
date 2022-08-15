package com.yice.edu.cn.osp.service.jw.perm;

import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import com.yice.edu.cn.osp.feignClient.jw.perm.PermFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermService {
    @Autowired
    private PermFeign permFeign;

    public Perm findPermById(String id) {
        return permFeign.findPermById(id);
    }

    public Perm savePerm(Perm perm) {
        return permFeign.savePerm(perm);
    }

    public List<Perm> findPermListByCondition(Perm perm) {
        return permFeign.findPermListByCondition(perm);
    }

    public Perm findOnePermByCondition(Perm perm) {
        return permFeign.findOnePermByCondition(perm);
    }

    public long findPermCountByCondition(Perm perm) {
        return permFeign.findPermCountByCondition(perm);
    }

    public void updatePerm(Perm perm) {
        permFeign.updatePerm(perm);
    }

    public void deletePerm(String id) {
        permFeign.deletePerm(id);
    }

    public void deletePermByCondition(Perm perm) {
        permFeign.deletePermByCondition(perm);
    }
}
