package com.yice.edu.cn.osp.controller.jy.prepareLessonsNew;

import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.constants.KnowledgeConstants;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.prepareLessonsNew.JySubjectMaterialExtend;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItem;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.osp.service.jy.prepareLessonsNew.JySubjectMaterialExtendService;
import com.yice.edu.cn.osp.service.jy.subjectSource.MaterialItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/jySubjectMaterialExtend")
@Api(value = "/jySubjectMaterialExtend", description = "章节模块")
public class JySubjectMaterialExtendController {
    @Autowired
    private JySubjectMaterialExtendService jySubjectMaterialExtendService;
    @Autowired
    private MaterialItemService materialItemService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/saveJySubjectMaterialExtend")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response = JySubjectMaterialExtend.class)
    public ResponseJson saveJySubjectMaterialExtend(
            @ApiParam(value = "对象", required = true)
            @RequestBody JySubjectMaterialExtend jySubjectMaterialExtend) {
        //最多允许两级节点，也可以前端控制
        if (jySubjectMaterialExtend.getLevel().intValue() == 3) {
            return new ResponseJson(false, "最多允许创建两级节点！");
        }
        //放学校和教师id
        if (jySubjectMaterialExtend.getType().equals(Constant.SECTION.SCHOOL_TYPE)) {
            jySubjectMaterialExtend.setSchoolId(mySchoolId());
            jySubjectMaterialExtend.setType(Constant.SECTION.SCHOOL_TYPE);
        } else {
            jySubjectMaterialExtend.setSchoolId(mySchoolId());
            jySubjectMaterialExtend.setCreateUserId(myId());
            jySubjectMaterialExtend.setType(Constant.SECTION.TEACHER_TYPE);
        }

        //判断是否为根节点
        if (jySubjectMaterialExtend.getParentId() == null) {
            jySubjectMaterialExtend.setParentId("-1");
        }
        //过滤重复章节名称
        JySubjectMaterialExtend ks = new JySubjectMaterialExtend();
        ks.setParentId(jySubjectMaterialExtend.getParentId());
        ks.setName(jySubjectMaterialExtend.getName());
        ks.setDdId(jySubjectMaterialExtend.getDdId());
        ks.setAnnualPeriodId(jySubjectMaterialExtend.getAnnualPeriodId());
        ks.setLevel(jySubjectMaterialExtend.getLevel());
        ks.setMaterialId(jySubjectMaterialExtend.getMaterialId());
        ks.setSchoolId(jySubjectMaterialExtend.getSchoolId());
        long count = jySubjectMaterialExtendService.findJySubjectMaterialExtendCountByCondition(ks);
        if (jySubjectMaterialExtend.getLevel().intValue() == 1) {
            //同一个教材下面不能有相同的章节
            if (count > 0) {
                return new ResponseJson(false, "该章节名称已经重复!");
            }
        } else {
            //同一个章节下面不能有相同的子章节
            if (count > 0) {
                return new ResponseJson(false, "该子章节名称已经重复!");
            }
        }
        jySubjectMaterialExtend.setLeaf(Integer.parseInt(KnowledgeConstants.LEAF.IS_LEAF));
        JySubjectMaterialExtend s = jySubjectMaterialExtendService.saveJySubjectMaterialExtend(jySubjectMaterialExtend);
        return new ResponseJson(s);
    }

    @GetMapping("/findJySubjectMaterialExtendById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response = JySubjectMaterialExtend.class)
    public ResponseJson findJySubjectMaterialExtendById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        JySubjectMaterialExtend jySubjectMaterialExtend = jySubjectMaterialExtendService.findJySubjectMaterialExtendById(id);
        return new ResponseJson(jySubjectMaterialExtend);
    }

    @PostMapping("/updateJySubjectMaterialExtend")
    @ApiOperation(value = "修改对象非空字段", notes = "返回响应对象")
    public ResponseJson updateJySubjectMaterialExtend(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody JySubjectMaterialExtend jySubjectMaterialExtend) {
        jySubjectMaterialExtendService.updateJySubjectMaterialExtend(jySubjectMaterialExtend);
        return new ResponseJson();
    }

    @PostMapping("/updateJySubjectMaterialExtendSort")
    @ApiOperation(value = "排序", notes = "返回响应对象")
    public ResponseJson updateJySubjectMaterialExtendSort(
            @ApiParam(value = "修改对象", required = true)
            @RequestBody List<JySubjectMaterialExtend> jsmeList) {
        jySubjectMaterialExtendService.updateJySubjectMaterialExtendSort(jsmeList);
        return new ResponseJson();

    }

    @PostMapping("/updateJySubjectMaterialExtendForAll")
    @ApiOperation(value = "修改对象所有字段", notes = "返回响应对象")
    public ResponseJson updateJySubjectMaterialExtendForAll(
            @ApiParam(value = "被修改的对象", required = true)
            @RequestBody JySubjectMaterialExtend jySubjectMaterialExtend) {
        jySubjectMaterialExtendService.updateJySubjectMaterialExtendForAll(jySubjectMaterialExtend);
        return new ResponseJson();
    }


    @PostMapping("/findJySubjectMaterialExtendsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response = JySubjectMaterialExtend.class)
    public ResponseJson findJySubjectMaterialExtendsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody JySubjectMaterialExtend jySubjectMaterialExtend) {
        jySubjectMaterialExtend.setSchoolId(mySchoolId());
        List<JySubjectMaterialExtend> data = jySubjectMaterialExtendService.findJySubjectMaterialExtendListByCondition(jySubjectMaterialExtend);
        long count = jySubjectMaterialExtendService.findJySubjectMaterialExtendCountByCondition(jySubjectMaterialExtend);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneJySubjectMaterialExtendByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response = JySubjectMaterialExtend.class)
    public ResponseJson findOneJySubjectMaterialExtendByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody JySubjectMaterialExtend jySubjectMaterialExtend) {
        JySubjectMaterialExtend one = jySubjectMaterialExtendService.findOneJySubjectMaterialExtendByCondition(jySubjectMaterialExtend);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteJySubjectMaterialExtend/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteJySubjectMaterialExtend(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        JySubjectMaterialExtend js = jySubjectMaterialExtendService.findJySubjectMaterialExtendById(id);
        String teacherId = myId();
        String schoolId = mySchoolId();
        if (StringUtils.isEmpty(js.getCreateUserId()) && js.getSchoolId().equals(schoolId)) {
            //学校管理员
            jySubjectMaterialExtendService.deleteJySubjectMaterialExtend(id);
            return new ResponseJson();
        } else if (js.getCreateUserId().equals(teacherId)) {
            //教师
            jySubjectMaterialExtendService.deleteJySubjectMaterialExtend(id);
            return new ResponseJson();
        } else {
            return new ResponseJson("无操作权限！");
        }
    }


    @PostMapping("/findJySubjectMaterialExtendListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response = JySubjectMaterialExtend.class)
    public ResponseJson findJySubjectMaterialExtendListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody JySubjectMaterialExtend jySubjectMaterialExtend) {
        jySubjectMaterialExtend.setSchoolId(mySchoolId());
        List<JySubjectMaterialExtend> data = jySubjectMaterialExtendService.findJySubjectMaterialExtendListByCondition(jySubjectMaterialExtend);
        return new ResponseJson(data);
    }

    @PostMapping("/findJySubjectMaterialExtendTreeByTextBookId")
    @ApiOperation(value = "根据教材Id以及其他条件查找章节树", notes = "返回响应对象,不包含总条数")
    public ResponseJson findJySubjectMaterialExtendTreeByTextBookId(
            @ApiParam(value = "对象")
            @RequestBody JySubjectMaterialExtend jySubjectMaterialExtend) {

        if (StringUtils.isEmpty(jySubjectMaterialExtend.getMaterialId()) || jySubjectMaterialExtend.getType() == null) {
            return new ResponseJson();
        }

        //获取教材自带章节
        List<MaterialItem> materialItemData = findJMaterialItemListByMaterialId(jySubjectMaterialExtend);
        List<MaterialItem> treeData = ObjectKit.buildTree(materialItemData, "-1");

        if (jySubjectMaterialExtend.getType().equals(Constant.SECTION.SCHOOL_TYPE)) {
            //学校自定义教材章节
            List<JySubjectMaterialExtend> schoolData = findJySubjectMaterialExtendListBySchool(jySubjectMaterialExtend);
            List<JySubjectMaterialExtend> schoolTreeData = ObjectKit.buildTree(schoolData, "-1");
            return new ResponseJson(treeData, schoolTreeData);
        } else {
            //教师自定义教材章节，包含学校
            List<JySubjectMaterialExtend> schoolData = findJySubjectMaterialExtendListBySchool(jySubjectMaterialExtend);
            List<JySubjectMaterialExtend> teacherData = findJySubjectMaterialExtendListByTeacher(jySubjectMaterialExtend);
            teacherData.addAll(schoolData);
            List<JySubjectMaterialExtend> teacherTreeData = ObjectKit.buildTree(teacherData, "-1");
            return new ResponseJson(treeData, teacherTreeData);
        }
    }


    /**
     * 查询教材下的章节信息
     *
     * @param js
     * @return
     */
    private List<MaterialItem> findJMaterialItemListByMaterialId(JySubjectMaterialExtend js) {
        MaterialItem materialItem = new MaterialItem();
        materialItem.setMaterialId(js.getMaterialId());
        Pager pager1 = new Pager();
        pager1.setSortField("sort");
        pager1.setSortOrder("desc");
        pager1.setPaging(false);
        materialItem.setPager(pager1);
        List<MaterialItem> data = materialItemService.findMaterialItemListByCondition(materialItem);
        return data;
    }

    /**
     * 查询教材下学校自定义的章节信息
     *
     * @param js
     * @return
     */
    private List<JySubjectMaterialExtend> findJySubjectMaterialExtendListBySchool(JySubjectMaterialExtend js) {
        //学校
        JySubjectMaterialExtend jsme1 = new JySubjectMaterialExtend();
        jsme1.setSchoolId(mySchoolId());
        jsme1.setType(Constant.SECTION.SCHOOL_TYPE);
        jsme1.setMaterialId(js.getMaterialId());
        Pager pager2 = new Pager();
        pager2.setSortField("sort");
        pager2.setSortOrder("desc");
        pager2.setPaging(false);
        jsme1.setPager(pager2);
        List<JySubjectMaterialExtend> data = jySubjectMaterialExtendService.findJySubjectMaterialExtendListByCondition(jsme1);
        return data;
    }

    /**
     * 查询教材下教师自定义的章节信息
     *
     * @param js
     * @return
     */
    private List<JySubjectMaterialExtend> findJySubjectMaterialExtendListByTeacher(JySubjectMaterialExtend js) {
        JySubjectMaterialExtend jsme2 = new JySubjectMaterialExtend();
        jsme2.setSchoolId(mySchoolId());
        jsme2.setCreateUserId(myId());
        jsme2.setType(Constant.SECTION.TEACHER_TYPE);
        jsme2.setMaterialId(js.getMaterialId());
        Pager pager3 = new Pager();
        pager3.setSortField("sort");
        pager3.setSortOrder("desc");
        pager3.setPaging(false);
        jsme2.setPager(pager3);
        List<JySubjectMaterialExtend> data = jySubjectMaterialExtendService.findJySubjectMaterialExtendListByCondition(jsme2);
        return data;
    }

    @PostMapping("/redis/savePrepareLessonsMenu")
    @ApiOperation(value = "保存最后一次停留的菜单信息", notes = "返回菜单信息", response = JySubjectMaterialExtend.class)
    public ResponseJson savePrepareLessonsMenu(
            @ApiParam(value = "保存对象")
            @Validated
            @RequestBody JySubjectMaterialExtend jySubjectMaterialExtend) {
        String key = Constant.Redis.PREPARE_LESSONS_MENU + "_" + jySubjectMaterialExtend.getCreateUserId();
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(jySubjectMaterialExtend));
        return new ResponseJson();
    }

    @GetMapping("/redis/findPrepareLessonsMenu")
    @ApiOperation(value = "获取保存最后一次停留的菜单信息", notes = "返回状态信息", response = JySubjectMaterialExtend.class)
    public ResponseJson findPrepareLessonsMenu() {
        //当前登录的用户
        String key = Constant.Redis.PREPARE_LESSONS_MENU + "_" + myId();
        String result = stringRedisTemplate.opsForValue().get(key);
        JySubjectMaterialExtend jsme = JSONUtil.toBean(result, JySubjectMaterialExtend.class);
        return new ResponseJson(jsme);
    }

}
