package com.yice.edu.cn.dy.service.classManage.mesAppletsPostPerm;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherPost;
import com.yice.edu.cn.common.pojo.mes.classManage.mesAppletsPostPerm.MesAppletsPostPerm;
import com.yice.edu.cn.dy.dao.classManage.mesAppletsPostPerm.IMesAppletsPostPermDao;
import com.yice.edu.cn.dy.feign.DdFeign;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MesAppletsPostPermService {
    @Autowired
    private IMesAppletsPostPermDao mesAppletsPostPermDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private DdFeign ddFeign;

/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
@Transactional(readOnly = true, rollbackFor = Exception.class)
    public MesAppletsPostPerm findMesAppletsPostPermById(String id) {
        return mesAppletsPostPermDao.findMesAppletsPostPermById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveMesAppletsPostPerm(MesAppletsPostPerm mesAppletsPostPerm) {
        mesAppletsPostPerm.setId(sequenceId.nextId());
        mesAppletsPostPerm.setCreateTime(DateUtil.now());
        mesAppletsPostPermDao.saveMesAppletsPostPerm(mesAppletsPostPerm);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<MesAppletsPostPerm> findMesAppletsPostPermListByCondition(MesAppletsPostPerm mesAppletsPostPerm) {
        List<MesAppletsPostPerm> list = mesAppletsPostPermDao.findMesAppletsPostPermListByCondition(mesAppletsPostPerm);
        if (CollectionUtil.isNotEmpty(list)) {
            //获取职务
            Dd dd = new Dd();
            dd.setTypeId("9");
            List<Dd> ddList = ddFeign.findDdListByCondition(dd);
            if (CollectionUtil.isNotEmpty(ddList)) {
                Map<String, String> map1 = listToMap(ddList);
                list.stream().forEach(i -> i.setPostName(map1.get(i.getPostId())));
            }

            //获取页面权限
            dd.setTypeId("44");
            List<Dd> dList = ddFeign.findDdListByCondition(dd);
            if (CollectionUtil.isNotEmpty(dList)) {
                Map<String, String> map2 = listToMap(dList);
                list.stream().forEach(i -> i.setMesPerm(map2.get(i.getMesPermId())));
                Map<String, List<MesAppletsPostPerm>> permMap = list.stream().filter(data -> data.getPostId() != null)
                        .collect(Collectors.groupingBy(MesAppletsPostPerm::getPostId));
                Map<String, String> map = new HashMap<>(16);
                permMap.forEach((key, value) -> map.put(key, StringUtils.join(value.stream().map(MesAppletsPostPerm::getMesPerm).toArray(), ",")));
                List<MesAppletsPostPerm> data = removeDuplicateByPostId(list);
                data.stream().forEach(item -> {
                    item.setMesPerms(map.get(item.getPostId()));
                    item.setMesPermIds(permMap.get(item.getPostId()).stream().map(MesAppletsPostPerm::getMesPermId).collect(Collectors.toList()));
                });
                return data;
            }
        }
        return list;
    }

    private List<MesAppletsPostPerm> removeDuplicateByPostId(List<MesAppletsPostPerm> list) {
        Set<MesAppletsPostPerm> personSet = new TreeSet<>(Comparator.comparing(MesAppletsPostPerm::getPostId));
        personSet.addAll(list);
        return new ArrayList<>(personSet);
    }

    private Map<String, String> listToMap(List<Dd> dList) {
        return dList.stream().collect(Collectors.toMap(Dd::getId, Dd::getName));
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public MesAppletsPostPerm findOneMesAppletsPostPermByCondition(MesAppletsPostPerm mesAppletsPostPerm) {
        return mesAppletsPostPermDao.findOneMesAppletsPostPermByCondition(mesAppletsPostPerm);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public long findMesAppletsPostPermCountByCondition(MesAppletsPostPerm mesAppletsPostPerm) {
        return mesAppletsPostPermDao.findMesAppletsPostPermCountByCondition(mesAppletsPostPerm);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateMesAppletsPostPerm(MesAppletsPostPerm mesAppletsPostPerm) {
        mesAppletsPostPermDao.updateMesAppletsPostPerm(mesAppletsPostPerm);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateMesPerm(MesAppletsPostPerm mesAppletsPostPerm) {
        if (Objects.nonNull(mesAppletsPostPerm.getPostId())) {
            MesAppletsPostPerm model = new MesAppletsPostPerm();
            model.setPostId(mesAppletsPostPerm.getPostId());
            deleteMesAppletsPostPermByCondition(model);
            saveMesPermission(mesAppletsPostPerm);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteMesAppletsPostPerm(String id) {
        mesAppletsPostPermDao.deleteMesAppletsPostPerm(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteMesAppletsPostPermByCondition(MesAppletsPostPerm mesAppletsPostPerm) {
        mesAppletsPostPermDao.deleteMesAppletsPostPermByCondition(mesAppletsPostPerm);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveMesPermission(MesAppletsPostPerm mesAppletsPostPerm) {
        if (CollectionUtil.isNotEmpty(mesAppletsPostPerm.getMesPermIds())) {
            MesAppletsPostPerm search = new MesAppletsPostPerm();
            search.setPostId(mesAppletsPostPerm.getPostId());
            List<MesAppletsPostPerm> result = mesAppletsPostPermDao.findMesAppletsPostPermListByCondition(search);
            if (CollectionUtil.isNotEmpty(result)) {
                deleteMesAppletsPostPermByCondition(search);
            }
            List<MesAppletsPostPerm> list = new ArrayList<>();
            mesAppletsPostPerm.getMesPermIds().forEach(item -> {
                MesAppletsPostPerm model = new MesAppletsPostPerm();
                model.setMesPermId(item);
                model.setPostId(mesAppletsPostPerm.getPostId());
                model.setSchoolId(mesAppletsPostPerm.getSchoolId());
                list.add(model);
            });
            batchSaveMesAppletsPostPerm(list);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchSaveMesAppletsPostPerm(List<MesAppletsPostPerm> mesAppletsPostPerms){
        String time = DateUtil.now();
        mesAppletsPostPerms.forEach(mesAppletsPostPerm -> {
            mesAppletsPostPerm.setId(sequenceId.nextId());
            mesAppletsPostPerm.setCreateTime(time);
        });
        mesAppletsPostPermDao.batchSaveMesAppletsPostPerm(mesAppletsPostPerms);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Set<Integer> findMesAppletsPostPermByPostId(List<TeacherPost> teacherPosts){
        return mesAppletsPostPermDao.findMesAppletsPostPermByPostId(teacherPosts);
    }
}
