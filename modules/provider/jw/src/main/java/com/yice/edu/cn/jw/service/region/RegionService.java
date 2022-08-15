package com.yice.edu.cn.jw.service.region;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.general.region.Region;
import com.yice.edu.cn.jw.dao.region.IRegionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegionService {
    @Autowired
    private IRegionDao regionDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public Region findRegionById(String id) {
        return regionDao.findRegionById(id);
    }
    @Transactional
    public void saveRegion(Region region) {
        region.setId(sequenceId.nextId());
        regionDao.saveRegion(region);
    }
    @Transactional(readOnly = true)
    public List<Region> findRegionListByCondition(Region region) {
        return regionDao.findRegionListByCondition(region);
    }
    @Transactional(readOnly = true)
    public long findRegionCountByCondition(Region region) {
        return regionDao.findRegionCountByCondition(region);
    }
    @Transactional
    public void updateRegion(Region region) {
        regionDao.updateRegion(region);
    }
    @Transactional
    public void deleteRegion(String id) {
        regionDao.deleteRegion(id);
    }
    @Transactional(readOnly = true)
    public List<Region> findRegionForCascade(String ids) {
        String[] split = ids.split(",");
        Region region = new Region();
        Pager pager = new Pager();
        pager.setPaging(false).setIncludes("id","name","levelType");
        region.setPager(pager);
        region.setLevelType("1");
        List<Region> provinces = regionDao.findRegionListByCondition(region);
        region.setLevelType("2");
        region.setParentId(split[0]);
        List<Region> cities = regionDao.findRegionListByCondition(region);
        for (Region province : provinces) {
            if(province.getId().equals(split[0])){
                province.setChildren(cities);
            }else{
                province.setChildren(new ArrayList<>());
            }
        }
        region.setLevelType("3");
        region.setParentId(split[1]);
        List<Region> districts = regionDao.findRegionListByCondition(region);
        for (Region city : cities) {
            if(city.getId().equals(split[1])){
                city.setChildren(districts);
            }else{
                city.setChildren(new ArrayList<>());
            }
        }
        return provinces;
    }
}
