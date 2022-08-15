package com.yice.edu.cn.yed.controller.baseData.school;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import com.yice.edu.cn.common.pojo.jw.auth.SchoolPerm;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.jw.school.SchoolExt;
import com.yice.edu.cn.common.pojo.jw.schoolYear.SchoolYear;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.yed.service.baseData.school.SchoolService;
import com.yice.edu.cn.yed.service.baseData.schoolPerm.SchoolPermService;
import com.yice.edu.cn.yed.service.jw.perm.PermService;
import com.yice.edu.cn.yed.service.jw.schoolYear.SchoolYearService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/school")
@Api(value = "/school",description = "学校模块")
public class SchoolController {
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private SchoolPermService schoolPermService;
    @Autowired
    private PermService permService;
    @Value("#{'${spring.profiles.active}'!='dev'}")
    private boolean isNotDev;
    @CreateCache(name = Constant.Redis.SCHOOL_VALID)
    private Cache<String,String[]> schoolValidCache;
    @Autowired
    private SchoolYearService schoolYearService;


    @GetMapping("/findSchoolById/{id}")
    @ApiOperation(value = "通过id查找学校", notes = "返回响应对象")
    public ResponseJson findSchoolById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        School school=schoolService.findSchoolById(id);
        return new ResponseJson(school);
    }

    @PostMapping("/saveSchoolAndSchoolYear")
    @ApiOperation(value = "保存学校对象", notes = "返回响应对象")
    public ResponseJson saveSchoolAndSchoolYear(
            @ApiParam(value = "学校对象", required = true)
            @RequestBody SchoolExt schoolExt){
        School school = new School();
        school.setName(schoolExt.getName());
        long count = schoolService.findSchoolCountByCondition(school);
        if(count>0){
            return new ResponseJson(false,"学校名称已存在");
        }
        schoolService.saveSchoolAndSchoolYear(schoolExt);
        return new ResponseJson();
    }
    @PostMapping("/updateSchool")
    @ApiOperation(value = "修改学校对象", notes = "返回响应对象")
    public ResponseJson updateSchool(
            @ApiParam(value = "被修改的学校对象,对象属性不为空则修改", required = true)
            @RequestBody School school){
        long count = schoolService.findSchoolNoRepetitionSchoolName(school);
        if(count>0){
            return new ResponseJson(false,"学校名称已存在");
        }
        schoolService.updateSchool(school);
        //yed后台修改学校信息成功后，覆盖缓存
        schoolValidCache.PUT(school.getId(),new String[]{school.getStatus(),school.getOutTime()});
        return new ResponseJson();
    }

    @PostMapping("/findSchoolsByCondition")
    @ApiOperation(value = "根据条件查找学校", notes = "返回响应对象")
    public ResponseJson findSchoolsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody School school){
        List<School> data=schoolService.findSchoolListByCondition(school);
        long count=schoolService.findSchoolCountByCondition(school);
        return new ResponseJson(data,count);
    }
    @GetMapping("/deleteSchool/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteSchool(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        schoolService.deleteSchool(id);
        return new ResponseJson();
    }

    @GetMapping("/deleteSchoolByCondition")
    @ApiOperation(value = "根据条件删除学校", notes = "返回响应对象")
    public ResponseJson deleteSchoolByCondition(
            @ApiParam(value = "被删除的学校对象,对象属性不为空则作为删除条件", required = true)
            @RequestBody School school){
        schoolService.deleteSchoolByCondition(school);
        return new ResponseJson();
    }
    @GetMapping("/findAdminBySchool/{id}")
    @ApiOperation(value = "根据id查找学校管理员", notes = "返回响应对象")
    public ResponseJson findAdminBySchool(
            @ApiParam(value = "id", required = true)
            @PathVariable String id){
        return new ResponseJson(schoolService.findAdminBySchool(id));
    }
    @GetMapping("/findAdminBySchool/resetAdminPassword/{id}")
    @ApiOperation(value = "重置学校管理员密码", notes = "返回响应对象")
    public ResponseJson resetAdminPassword(
            @ApiParam(value = "id", required = true)
            @PathVariable String id){
        schoolService.resetAdminPassword(id);
        return new ResponseJson();
    }

    @GetMapping("/setPerms/findPermTreeForSchool/{schoolId}")
    public ResponseJson findPermTreeForSchool(@PathVariable String schoolId){
        SchoolPerm schoolPerm = new SchoolPerm();
        schoolPerm.setPager(new Pager().setPaging(false).setIncludes("id","title","parentId").setSortField("sortNum").setSortOrder(Pager.ASC));
        List<SchoolPerm> schoolPerms = schoolPermService.findSchoolPermListByCondition(schoolPerm);
        Perm perm = new Perm();
        perm.setSchoolId(schoolId);
        perm.setPager(new Pager().setPaging(false).setIncludes("id","parentId"));
        List<Perm> perms = permService.findPermListByCondition(perm);
        //过滤出叶子节点（前段的树如果中间节点勾选，所有其子节点也都勾选）
        List<Perm> leaves = new ArrayList<>();
        perms.forEach(p1->{
            boolean match = perms.stream().anyMatch(p2 -> p1.getId().equals(p2.getParentId()));
            if(!match){
                leaves.add(p1);
            }
        });
        return new ResponseJson(ObjectKit.buildTree(schoolPerms,"-1"), leaves.stream().flatMap(p -> Stream.of(p.getId())).collect(Collectors.toList()));
    }

    @PostMapping("/setPerms/updatePerms/{schoolId}")
    public ResponseJson updatePerms(@PathVariable String schoolId,@RequestBody List<String> checkedIds){
        schoolPermService.updatePerms(schoolId, checkedIds);
        return new ResponseJson();
    }
    
    @PostMapping("/queryClockInRange")
    public ResponseJson queryClockInRange(){
    	return new ResponseJson(schoolService.queryClockInRange());
    }

    public static void main(String[] args) {
        final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate now = LocalDate.now();
        LocalDate t = LocalDate.parse("2019-07-25", format);

        System.out.println(t.isEqual(now));
    }
}
