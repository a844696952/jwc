package com.yice.edu.cn.jy.service.prepareLessonsNew;

import com.yice.edu.cn.common.constants.KnowledgeConstants;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jy.courseware.CourseRes;
import com.yice.edu.cn.common.pojo.jy.courseware.CourseWare;
import com.yice.edu.cn.common.pojo.jy.prepareLessonsNew.JySubjectMaterialExtend;
import com.yice.edu.cn.jy.dao.courseware.ICourseResDao;
import com.yice.edu.cn.jy.dao.prepareLessonsNew.IJySubjectMaterialExtendDao;
import com.yice.edu.cn.jy.service.courseware.CourseWareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

@Service
public class JySubjectMaterialExtendService {
    @Autowired
    public IJySubjectMaterialExtendDao jySubjectMaterialExtendDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private ICourseResDao iCourseResDao;
    @Autowired
    private CourseWareService courseWareService;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public JySubjectMaterialExtend findJySubjectMaterialExtendById(String id) {
        return jySubjectMaterialExtendDao.findJySubjectMaterialExtendById(id);
    }

    @Transactional
    public void saveJySubjectMaterialExtend(JySubjectMaterialExtend jySubjectMaterialExtend) {
        //修改父节点为非叶子节点
        if (!"-1".equals(jySubjectMaterialExtend.getParentId())) {
            JySubjectMaterialExtend parentModel = new JySubjectMaterialExtend();
            parentModel.setId(jySubjectMaterialExtend.getParentId());
            parentModel.setLeaf(Integer.parseInt(KnowledgeConstants.LEAF.IS_NOT_LEAF));
            jySubjectMaterialExtendDao.updateJySubjectMaterialExtend(parentModel);
        }

        JySubjectMaterialExtend obj = new JySubjectMaterialExtend();
        obj.setParentId(jySubjectMaterialExtend.getParentId());
        obj.setSchoolId(jySubjectMaterialExtend.getSchoolId());
        obj.setType(jySubjectMaterialExtend.getType());
        List<JySubjectMaterialExtend> list = jySubjectMaterialExtendDao.findJySubjectMaterialExtendListByCondition(obj);
        if (!list.isEmpty() && list.size() != 0) {
            //取sort最大的值
            Integer maxResult = list.stream().map(skt -> skt.getSort()).max(Comparator.comparing(Function.identity())).get();
            jySubjectMaterialExtend.setSort(maxResult + 1);
        }
        jySubjectMaterialExtend.setId(sequenceId.nextId());
        jySubjectMaterialExtend.setPath((jySubjectMaterialExtend.getPath() == null ? "" : jySubjectMaterialExtend.getPath()) + jySubjectMaterialExtend.getId() + ";");
        jySubjectMaterialExtendDao.saveJySubjectMaterialExtend(jySubjectMaterialExtend);
    }

    @Transactional(readOnly = true)
    public List<JySubjectMaterialExtend> findJySubjectMaterialExtendListByCondition(JySubjectMaterialExtend jySubjectMaterialExtend) {
        return jySubjectMaterialExtendDao.findJySubjectMaterialExtendListByCondition(jySubjectMaterialExtend);
    }

    @Transactional(readOnly = true)
    public JySubjectMaterialExtend findOneJySubjectMaterialExtendByCondition(JySubjectMaterialExtend jySubjectMaterialExtend) {
        return jySubjectMaterialExtendDao.findOneJySubjectMaterialExtendByCondition(jySubjectMaterialExtend);
    }

    @Transactional(readOnly = true)
    public long findJySubjectMaterialExtendCountByCondition(JySubjectMaterialExtend jySubjectMaterialExtend) {
        return jySubjectMaterialExtendDao.findJySubjectMaterialExtendCountByCondition(jySubjectMaterialExtend);
    }

    @Transactional
    public void updateJySubjectMaterialExtend(JySubjectMaterialExtend jySubjectMaterialExtend) {
        jySubjectMaterialExtendDao.updateJySubjectMaterialExtend(jySubjectMaterialExtend);
    }

    @Transactional
    public void updateJySubjectMaterialExtendForAll(JySubjectMaterialExtend jySubjectMaterialExtend) {
        jySubjectMaterialExtendDao.updateJySubjectMaterialExtendForAll(jySubjectMaterialExtend);
    }

    @Transactional(rollbackFor=Exception.class)
    public void deleteJySubjectMaterialExtend(String id) {
        JySubjectMaterialExtend js = new JySubjectMaterialExtend();
        js.setParentId(id);
        List<JySubjectMaterialExtend> jsList = jySubjectMaterialExtendDao.findJySubjectMaterialExtendListByCondition(js);
        if (jsList != null && jsList.size() > 0) {
            //有叶子节点
            jsList.stream().forEach(skt -> {
                //先删除叶子节点
                jySubjectMaterialExtendDao.deleteJySubjectMaterialExtend(skt.getId());
            });
            //删除父节点
            jySubjectMaterialExtendDao.deleteJySubjectMaterialExtend(id);
            //更新资源到未分类，统一更新
            moveResToNoType(id,jsList.get(0).getSchoolId());
            moveCourseWareToNoType(id,jsList.get(0).getSchoolId());
        } else {
            //本身就是叶子节点
            CourseRes courseRes = new CourseRes();
            CourseWare courseWare=new CourseWare();
            JySubjectMaterialExtend jsme = jySubjectMaterialExtendDao.findJySubjectMaterialExtendById(id);
            //解除资源与目录的绑定
            if (jsme.getParentId().equals("-1") || jsme.getParentId() == null) {
                courseRes.setLv1(id);
                courseWare.setLv1(id);
            } else {
                courseRes.setLv1(jsme.getParentId());
                courseRes.setLv2(id);
                courseWare.setLv1(jsme.getParentId());
                courseWare.setLv2(id);
            }
            courseWare.setSchoolId(jsme.getSchoolId());
            courseRes.setSchoolId(jsme.getSchoolId());
            updateChildNode(id);
            jySubjectMaterialExtendDao.deleteJySubjectMaterialExtend(id);
            //更新资源到未分类
            iCourseResDao.mv0(courseRes);
            courseWareService.updateCourseWareToNoType(courseWare);
        }
    }

    private void moveResToNoType(String id,String schoolId) {
        CourseRes courseRes = new CourseRes();
        courseRes.setLv1(id);
        courseRes.setSchoolId(schoolId);
        iCourseResDao.mv0(courseRes);
    }

    private void moveCourseWareToNoType(String id,String schoolId) {
        CourseWare courseWare=new CourseWare();
        courseWare.setLv1(id);
        courseWare.setSchoolId(schoolId);
        courseWareService.updateCourseWareToNoType(courseWare);
    }

    //修改父节点的类型为叶子节点
    private void updateChildNode(String id) {
        JySubjectMaterialExtend js1 = jySubjectMaterialExtendDao.findJySubjectMaterialExtendById(id);

        JySubjectMaterialExtend js2 = new JySubjectMaterialExtend();
        js2.setParentId(js1.getParentId());
        long count = jySubjectMaterialExtendDao.findJySubjectMaterialExtendCountByCondition(js2);
        //当前节点的父节点下，如果只有一个或者小于一个的叶子节点，删除之后，父节点就是叶子节点
        if (count <= 1) {
            //修改父节点为叶子节点
            JySubjectMaterialExtend js3 = new JySubjectMaterialExtend();
            js3.setId(js1.getParentId());
            js3.setLeaf(Integer.parseInt(KnowledgeConstants.LEAF.IS_LEAF));
            jySubjectMaterialExtendDao.updateJySubjectMaterialExtend(js3);
        }
    }

    @Transactional
    public void deleteJySubjectMaterialExtendByCondition(JySubjectMaterialExtend jySubjectMaterialExtend) {
        jySubjectMaterialExtendDao.deleteJySubjectMaterialExtendByCondition(jySubjectMaterialExtend);
    }

    @Transactional
    public void batchSaveJySubjectMaterialExtend(List<JySubjectMaterialExtend> jySubjectMaterialExtends) {
        jySubjectMaterialExtends.forEach(jySubjectMaterialExtend -> jySubjectMaterialExtend.setId(sequenceId.nextId()));
        jySubjectMaterialExtendDao.batchSaveJySubjectMaterialExtend(jySubjectMaterialExtends);
    }

    @Transactional
    public void updateJySubjectMaterialExtendSort(List<JySubjectMaterialExtend> jsmeList) {
        jsmeList.stream()
                .forEach(skt -> jySubjectMaterialExtendDao.updateJySubjectMaterialExtend(skt));
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
