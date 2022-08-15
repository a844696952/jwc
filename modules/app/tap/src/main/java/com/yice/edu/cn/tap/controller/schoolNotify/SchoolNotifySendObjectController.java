package com.yice.edu.cn.tap.controller.schoolNotify;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.jw.department.DepartmentForSchoolNotify;
import com.yice.edu.cn.common.pojo.jw.schoolNotify.SchoolNotify;
import com.yice.edu.cn.common.pojo.jw.schoolNotify.SchoolNotifySendObject;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.tap.interceptor.LoginInterceptor;
import com.yice.edu.cn.tap.service.classes.JwClassesService;
import com.yice.edu.cn.tap.service.department.DepartmentService;
import com.yice.edu.cn.tap.service.schoolNotify.SchoolNotifySendObjectService;
import com.yice.edu.cn.tap.service.schoolNotify.SchoolNotifyService;
import com.yice.edu.cn.tap.service.student.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import java.util.stream.Collectors;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/schoolNotifySendObject")
@Api(value = "/schoolNotifySendObject", description = "模块")
public class SchoolNotifySendObjectController {
    @Autowired
    private SchoolNotifySendObjectService schoolNotifySendObjectService;
    @Autowired
    private SchoolNotifyService schoolNotifyService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private JwClassesService classesService;


    @PostMapping("/saveSchoolNotifySendObject")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response = SchoolNotifySendObject.class)
    public ResponseJson saveSchoolNotifySendObject(
            @ApiParam(value = "对象", required = true)
            @RequestBody SchoolNotifySendObject schoolNotifySendObject) {
        schoolNotifySendObject.setSchoolId(mySchoolId());
        SchoolNotifySendObject s = schoolNotifySendObjectService.saveSchoolNotifySendObject(schoolNotifySendObject);
        return new ResponseJson(s);
    }


    @PostMapping("/updateSchoolNotifySendObject")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateSchoolNotifySendObject(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody SchoolNotifySendObject schoolNotifySendObject) {
        schoolNotifySendObjectService.updateSchoolNotifySendObject(schoolNotifySendObject);
        return new ResponseJson();
    }

    @GetMapping("/findSchoolNotifySendObjectById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response = SchoolNotifySendObject.class)
    public ResponseJson findSchoolNotifySendObjectById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        SchoolNotifySendObject schoolNotifySendObject = schoolNotifySendObjectService.findSchoolNotifySendObjectById(id);
        return new ResponseJson(schoolNotifySendObject);
    }

    @GetMapping("/deleteSchoolNotifySendObject/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteSchoolNotifySendObject(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        schoolNotifySendObjectService.deleteSchoolNotifySendObject(id);
        return new ResponseJson();
    }


    @PostMapping("/findSchoolNotifySendObjectListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response = SchoolNotifySendObject.class)
    public ResponseJson findSchoolNotifySendObjectListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody SchoolNotifySendObject schoolNotifySendObject) {
        schoolNotifySendObject.setSchoolId(mySchoolId());
        List<SchoolNotifySendObject> data = schoolNotifySendObjectService.findSchoolNotifySendObjectListByCondition(schoolNotifySendObject);
        return new ResponseJson(data);
    }

    @PostMapping("/getMySchoolNotifySendObjectList")
    @ApiOperation(value = "获取当前用户的通知列表,传入收发状态（state）：1、发送列表  2、接受列表  读取状态（readState）：0、全部 1、未读  2、已读", notes = "返回响应对象,不包含总条数", response = SchoolNotifySendObject.class)
    public ResponseJson getMySchoolNotifySendObjectList(@RequestBody SchoolNotifySendObject schoolNotifySendObject) {
        //查询全部要置空读取状态
        if(schoolNotifySendObject.getReadState().equals("0"))
            schoolNotifySendObject.setReadState(null);

        //状态为1：发送列表查询通知表，2：查询的是发送对象表
        if (schoolNotifySendObject.getState().equals("1")) {
            SchoolNotify schoolNotify = new SchoolNotify();
            schoolNotify.setSenderId(myId());
            schoolNotify.setSchoolId(mySchoolId());
            schoolNotify.setPager(schoolNotifySendObject.getPager());
            List<SchoolNotify> schoolNotifyList = schoolNotifyService.findSchoolNotifyListByCondition(schoolNotify);
            long count = schoolNotifyService.findSchoolNotifyCountByCondition(schoolNotify);
            List<SchoolNotifySendObject> data = new ArrayList<>();
            schoolNotifyList.forEach(schoolNotify1 -> {
                SchoolNotifySendObject schoolNotifySendObject1 = new SchoolNotifySendObject();
                schoolNotifySendObject1.setReadState("1");
                schoolNotifySendObject1.setSchoolNotify(schoolNotify1);
                data.add(schoolNotifySendObject1);
            });
            return new ResponseJson(data, count);
        } else {
            schoolNotifySendObject.setState(null);
            schoolNotifySendObject.setSchoolId(mySchoolId());
            schoolNotifySendObject.setObjectId(myId());
            schoolNotifySendObject.setDel("1");
            List<SchoolNotifySendObject> data = schoolNotifySendObjectService.findSchoolNotifySendObjectListByCondition(schoolNotifySendObject);
            long count = schoolNotifySendObjectService.findSchoolNotifySendObjectCountByCondition(schoolNotifySendObject);
            return new ResponseJson(data, count);
        }


    }


    @PostMapping("getSchoolNotifyReadDetailById")
    @ApiOperation(value = "获取通知的读取人数详情，传入通知id 、sendType、readState", notes = "返回响应对象,不包含总条数", response = SchoolNotifySendObject.class)
    public ResponseJson getSchoolNotifyReadDetailById( @RequestBody SchoolNotify schoolNotify) {
        String readState=schoolNotify.getReadState();
        if(schoolNotify.getSendType().equals("1")){
            SchoolNotifySendObject schoolNotifySendObject = new SchoolNotifySendObject();
            schoolNotifySendObject.setReadState(schoolNotify.getReadState());
            schoolNotify.setReadState(null);
            schoolNotifySendObject.setSchoolNotify(schoolNotify);
            schoolNotifySendObject.setSchoolId(mySchoolId());
            schoolNotifySendObject.setPager(schoolNotify.getPager());
            List<Department> departmentList = schoolNotifySendObjectService.getSchoolNotifyReadDetail(schoolNotifySendObject);
            long count  = schoolNotifySendObjectService.getSchoolNotifyReadDetailCount(schoolNotifySendObject);

            return new ResponseJson(departmentList,count);

        }else{
            SchoolNotifySendObject schoolNotifySendObject = new SchoolNotifySendObject();
            schoolNotify.setReadState(null);
            schoolNotifySendObject.setSchoolNotify(schoolNotify);
            schoolNotifySendObject.setSchoolId(mySchoolId());
            schoolNotifySendObject.setType("0");
            List<SchoolNotifySendObject> schoolNotifySendObjectList= schoolNotifySendObjectService.findSchoolNotifySendObjectListByCondition(schoolNotifySendObject);
            List<DepartmentForSchoolNotify> departmentList=buildTree(schoolNotifySendObjectList);
            removeEmpty(readState, departmentList);

            departmentList.forEach(department -> {
                department.getChildren().sort(Comparator.comparing(DepartmentForSchoolNotify::getClassNumber));
                department.getChildren().forEach(departmentForSchoolNotify ->{
                    departmentForSchoolNotify.setClassNumber(null);
                } );
            });

            return new ResponseJson(departmentList);
        }
    }

    /**
     * 某个班级已读、未读数量如果为零，则将对应的节点移除
     * @param readState
     * @param departmentList
     */
    private void removeEmpty(String readState, List<DepartmentForSchoolNotify> departmentList) {

        for (int i = 0; i <departmentList.size() ; i++) {
            List<DepartmentForSchoolNotify> classList=departmentList.get(i).getChildren();
            Iterator<DepartmentForSchoolNotify> it = classList.iterator();
            while(it.hasNext()){
                DepartmentForSchoolNotify x = it.next();
                if(readState.equals("1")){
                    if(x.getClassNum()-x.getClassReadNum()==0){
                        it.remove();
                    }
                }else {
                    if(x.getClassReadNum()==0){
                        it.remove();
                    }
                }

            }

        }

        Iterator<DepartmentForSchoolNotify> it1 = departmentList.iterator();
        while(it1.hasNext()){
            DepartmentForSchoolNotify x1 = it1.next();
            if(x1.getChildren().size()==0){
                it1.remove();
            }
        }

    }


    @GetMapping("/ignore/findDepartmentForTree/{type}/{personType}")
    @ApiOperation(value = "查找组织架构树,1、为学校结构树 2、班级学生结构树")
    public ResponseJson findDepartmentForSchoolNotify(@PathVariable("type") int type,@PathVariable("personType") int personType) {
        List<Department> departments = null;
        if (type == 1) {
            departments = departmentService.findDepartmentHaveOrNotTeacherForTree(LoginInterceptor.mySchoolId(), false,personType);
        } else if (type == 2) {
            departments = findClassesTree();
        }
        return new ResponseJson(departments);
    }


    public List<Department> findClassesTree() {

        JwClasses jwClasses=new JwClasses();
        jwClasses.setSchoolId(mySchoolId());
        List<JwClasses> data=classesService.findJwClassesListByCondition(jwClasses);
        Map<String, Map<String, List<JwClasses>>> classMap = data.stream().collect(Collectors.groupingBy(JwClasses::getGradeId, Collectors.groupingBy(JwClasses::getNumber)));
        List<Department> treeList = new ArrayList<>();

        classMap.forEach((k, v) -> {
            Department department = new Department();
            Map<String, List<JwClasses>> classMap1 = v;
            List<JwClasses> studentList = classMap1.values().stream().findFirst().get();
            department.setType(0);
            department.setParentId("-1");
            department.setName(studentList.get(0).getGradeName());
            department.setId(studentList.get(0).getGradeId());

            List<Department> children = new ArrayList<>();
            classMap1.forEach((k1, v1) -> {
                List<JwClasses> Class = v1;
                Department department1 = new Department();
                department1.setId(Class.get(0).getId());
                department1.setParentId(studentList.get(0).getGradeId());
                department1.setName(Class.get(0).getNumber() + "班");
                department1.setType(1);
                children.add(department1);

            });
            department.setChildren(children);

            treeList.add(department);

        });

        return treeList;
    }



    @PostMapping("/findSchoolNotifySendObjectListForSchoolNotifyByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response = SchoolNotifySendObject.class)
    public ResponseJson findSchoolNotifySendObjectListForSchoolNotifyByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody SchoolNotifySendObject schoolNotifySendObject) {
        schoolNotifySendObject.setSchoolId(mySchoolId());
        List<SchoolNotifySendObject> data = schoolNotifySendObjectService.findSchoolNotifySendObjectListByCondition(schoolNotifySendObject);

        List<Department> treeList=new ArrayList<>();

        data.forEach(schoolNotifySendObject1 -> {
            Department department=new Department();
            department.setName(schoolNotifySendObject1.getDepartmentName());
            department.setType(1);
            department.setImgUrl(schoolNotifySendObject1.getImgUrl());
            treeList.add(department);
        });

        return new ResponseJson(treeList);
    }


    @GetMapping("/getNotReadNum")
    @ApiOperation(value = "获取当前用户未读数量，用于判断是否显示红点", notes = "返回响应对象,不包含总条数", response = SchoolNotifySendObject.class)
    public ResponseJson findSchoolNotifySendObjectListForSchoolNotifyByCondition() {
        boolean isRead=false;
        SchoolNotifySendObject schoolNotifySendObject=new SchoolNotifySendObject();
        schoolNotifySendObject.setSchoolId(mySchoolId());
        schoolNotifySendObject.setObjectId(myId());
        schoolNotifySendObject.setReadState("1");
        schoolNotifySendObject.setDel("1");
        long count = schoolNotifySendObjectService.findSchoolNotifySendObjectCountByCondition(schoolNotifySendObject);
        if(count>0){
            isRead=true;
        }
        return new ResponseJson(isRead);
    }
    /**
     * 获取map中第一个数据值
     *
     * @param map 数据源
     * @return
     */
    private static Object getFirstOrNull(Map<String, List<JwClasses>> map) {
        Object obj = null;
        for (Map.Entry<String, List<JwClasses>> entry : map.entrySet()) {
            obj = entry.getValue();
            if (obj != null) {
                break;
            }
        }
        return obj;

    }

    public  List<DepartmentForSchoolNotify> buildTree(List<SchoolNotifySendObject> data ){
        List<DepartmentForSchoolNotify> departmentList=new ArrayList<>();
        data.forEach(schoolNotifySendObject1 -> {
            DepartmentForSchoolNotify department=new DepartmentForSchoolNotify();
            department.setId(schoolNotifySendObject1.getObjectId());
            department.setName(schoolNotifySendObject1.getDepartmentName());
            department.setType(Integer.parseInt(schoolNotifySendObject1.getType()));
            department.setParentId(schoolNotifySendObject1.getDepartmentParentId());
            department.setClassNumber(schoolNotifySendObject1.getClassNumber());
            department.setClassNum(schoolNotifySendObject1.getClassNum());
            department.setClassReadNum(schoolNotifySendObject1.getClassReadNum());
            departmentList.add(department);
        });
        return ObjectKit.buildTree(departmentList,"-1");

    }
}
