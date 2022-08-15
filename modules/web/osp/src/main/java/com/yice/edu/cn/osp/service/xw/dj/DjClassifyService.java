package com.yice.edu.cn.osp.service.xw.dj;

import com.alibaba.fastjson.JSONObject;
import com.yice.edu.cn.common.pojo.xw.dj.DjClassify;
import com.yice.edu.cn.osp.feignClient.xw.dj.DjClassifyFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DjClassifyService {
    @Autowired
    private DjClassifyFeign djClassifyFeign;

    public DjClassify findDjClassifyById(String id) {
        return djClassifyFeign.findDjClassifyById(id);
    }

    public DjClassify saveDjClassify(DjClassify djClassify) {
        djClassify.setStatus(0);
        return djClassifyFeign.saveDjClassify(djClassify);
    }

    public List<DjClassify> findActivityDjClassify() {
        return djClassifyFeign.findActivityDjClassify();
    }

    public List<DjClassify> findDjClassifyListByCondition(DjClassify djClassify) {
        return djClassifyFeign.findDjClassifyListByCondition(djClassify);
    }

    public DjClassify findOneDjClassifyByCondition(DjClassify djClassify) {
        return djClassifyFeign.findOneDjClassifyByCondition(djClassify);
    }

    public long findDjClassifyCountByCondition(DjClassify djClassify) {
        return djClassifyFeign.findDjClassifyCountByCondition(djClassify);
    }

    public void updateDjClassify(DjClassify djClassify) {
        djClassifyFeign.updateDjClassify(djClassify);
    }

    public void deleteDjClassify(String id) {
        djClassifyFeign.deleteDjClassify(id);
    }
    public JSONObject deleteDjClassifyNotEnpty(String id) {
        boolean b = djClassifyFeign.deleteDjClassifyNotEnpty(id);
        JSONObject json=new JSONObject();
        if (b){
            json.put("success",b);
            json.put("msg","删除成功");
        }else {
            json.put("success",b);
            json.put("msg","该分类下有活动信息，无法删除");
        }
        return json;
    }

    public void deleteDjClassifyByCondition(DjClassify djClassify) {
        djClassifyFeign.deleteDjClassifyByCondition(djClassify);
    }

    public List<DjClassify> selectClassifyListByType(DjClassify djClassify) {
        return djClassifyFeign.selectClassifyListByType(djClassify);
    }

    public List<DjClassify> findClassifyListByType(DjClassify djClassify) {
        return djClassifyFeign.findClassifyListByType(djClassify);
    }


}
