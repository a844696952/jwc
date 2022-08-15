package com.yice.edu.cn.dm.service.dmScreenSaver;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.dm.dmScreenSaver.AreaByDmClassVo;
import com.yice.edu.cn.common.pojo.dm.dmScreenSaver.DmScreenSaver;
import com.yice.edu.cn.dm.dao.dmScreenSaver.IDmScreenSaverDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DmScreenSaverService {
    @Autowired
    private IDmScreenSaverDao dmScreenSaverDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public DmScreenSaver findDmScreenSaverById(String id) {
        return dmScreenSaverDao.findDmScreenSaverById(id);
    }
    @Transactional
    public void saveDmScreenSaver(DmScreenSaver dmScreenSaver) {
        dmScreenSaver.setId(sequenceId.nextId());
        dmScreenSaver.setPwd("1234");
        dmScreenSaverDao.saveDmScreenSaver(dmScreenSaver);
    }
    @Transactional(readOnly = true)
    public List<DmScreenSaver> findDmScreenSaverListByCondition(DmScreenSaver dmScreenSaver) {
        return dmScreenSaverDao.findDmScreenSaverListByCondition(dmScreenSaver);
    }
    @Transactional(readOnly = true)
    public DmScreenSaver findOneDmScreenSaverByCondition(DmScreenSaver dmScreenSaver) {
        return dmScreenSaverDao.findOneDmScreenSaverByCondition(dmScreenSaver);
    }
    @Transactional(readOnly = true)
    public long findDmScreenSaverCountByCondition(DmScreenSaver dmScreenSaver) {
        return dmScreenSaverDao.findDmScreenSaverCountByCondition(dmScreenSaver);
    }
    @Transactional
    public void updateDmScreenSaver(DmScreenSaver dmScreenSaver) {
        dmScreenSaverDao.updateDmScreenSaver(dmScreenSaver);
    }
    @Transactional
    public void deleteDmScreenSaver(String id) {
        dmScreenSaverDao.deleteDmScreenSaver(id);
    }
    @Transactional
    public void deleteDmScreenSaverByCondition(DmScreenSaver dmScreenSaver) {
        dmScreenSaverDao.deleteDmScreenSaverByCondition(dmScreenSaver);
    }
    @Transactional
    public void batchSaveDmScreenSaver(List<DmScreenSaver> dmScreenSavers){
        dmScreenSavers.forEach(dmScreenSaver -> dmScreenSaver.setId(sequenceId.nextId()));
        dmScreenSaverDao.batchSaveDmScreenSaver(dmScreenSavers);
    }
    @Transactional
    public void batchUpdateDmScreenSaver(DmScreenSaver dmScreenSaver){
        dmScreenSaverDao.batchUpdateDmScreenSaver(dmScreenSaver);
    }

    @Transactional
    public void batchDeleteDmScreenSaver(DmScreenSaver dmScreenSaver){
        dmScreenSaverDao.batchDeleteDmScreenSaver(dmScreenSaver);
    }

    //获取班级名称和班牌的用户名
    @Transactional
    public List<AreaByDmClassVo> getAddAreaByDmClass(DmScreenSaver dmScreenSaver){
        List<AreaByDmClassVo> areaByDmClassVos = new ArrayList<>();
        //获取所有的楼栋
//        List<JwAcademicBuilding> getBuildingList = dmScreenSaverDao.getBuildingList(dmScreenSaver);
//        //循环楼栋
//        for(int j=0;j<getBuildingList.size();j++){
//            AreaByDmClassVo areaByDmClassVo = new AreaByDmClassVo();
//            areaByDmClassVo.setLabel(getBuildingList.get(j).getName());
//            List<AreaByDmClassVo> classVos = new ArrayList<>();
//            //循环获取楼层
//            for(int i=0;i<Integer.parseInt(getBuildingList.get(j).getFloors());i++){
//                AreaByDmClassVo areaByDmClassVo1 = new AreaByDmClassVo();
//                dmScreenSaver.setFloor((i+1));
//                dmScreenSaver.setAcademicBuildingId(getBuildingList.get(j).getId());
//                areaByDmClassVo1.setLabel((i+1)+"楼");
//                dmScreenSaver.setRowData(dmScreenSaverDao.getSpaceList(dmScreenSaver));
//                if(dmScreenSaver.getRowData().length>0){
//                    areaByDmClassVo1.setChildren(dmScreenSaverDao.getAreaByDmClass(dmScreenSaver));
//                    classVos.add(areaByDmClassVo1);
//                }
//            }
//            areaByDmClassVo.setChildren(classVos);
//            areaByDmClassVos.add(areaByDmClassVo);
//        }

        return areaByDmClassVos;
    }
    //获取班级名称和班牌的用户名
    @Transactional
    public List<AreaByDmClassVo> getUpdateAreaByDmClass(DmScreenSaver dmScreenSaver){
        String items[] = dmScreenSaverDao.findDmScreenSaverById(dmScreenSaver.getId()).getSendArea().split(",");
        List<String> stringList = Arrays.asList(items);
        List<AreaByDmClassVo> areaByDmClassVos = new ArrayList<>();
//        //获取所有的楼栋
//        List<JwAcademicBuilding> getBuildingList = dmScreenSaverDao.getBuildingList(dmScreenSaver);
//        //循环楼栋
//        for(int j=0;j<getBuildingList.size();j++){
//            AreaByDmClassVo areaByDmClassVo = new AreaByDmClassVo();
//            areaByDmClassVo.setLabel(getBuildingList.get(j).getName());
//            List<AreaByDmClassVo> classVos = new ArrayList<>();
//            //循环获取楼层
//            for(int i=0;i<Integer.parseInt(getBuildingList.get(j).getFloors());i++){
//                AreaByDmClassVo areaByDmClassVo1 = new AreaByDmClassVo();
//                dmScreenSaver.setFloor((i+1));
//                dmScreenSaver.setAcademicBuildingId(getBuildingList.get(j).getId());
//                areaByDmClassVo1.setLabel((i+1)+"楼");
//                dmScreenSaver.setRowData(dmScreenSaverDao.getSpaceList(dmScreenSaver));
//                if(dmScreenSaver.getRowData().length>0){
//                    List<AreaByDmClassVo> areaByDmClassVos1 = dmScreenSaverDao.getAreaByDmClass(dmScreenSaver);
//                    areaByDmClassVos1.stream().forEach(e->{
//                        if(stringList.contains(e.getId())){
//                            e.setSelected(true);
//                        }
//                    });
//                    areaByDmClassVo1.setChildren(areaByDmClassVos1);
//                    classVos.add(areaByDmClassVo1);
//                }
//            }
//            areaByDmClassVo.setChildren(classVos);
//            areaByDmClassVos.add(areaByDmClassVo);
//        }

        return areaByDmClassVos;
    }
    //根据班级编号获取到云班牌用户名
    @Transactional
    public String getUserNameByClassId(DmScreenSaver dmScreenSaver){
        return dmScreenSaverDao.getUserNameByClassId(dmScreenSaver);
    }
    @Transactional
    public void batchUpdateDmScreenSaverStatus(DmScreenSaver dmScreenSaver){
        dmScreenSaverDao.batchUpdateDmScreenSaverStatus(dmScreenSaver);
    }

    @Transactional
    public DmScreenSaver getRunNingDmScreenSaver(DmScreenSaver dmScreenSaver){
        return dmScreenSaverDao.getRunNingDmScreenSaver(dmScreenSaver);
    }
}
