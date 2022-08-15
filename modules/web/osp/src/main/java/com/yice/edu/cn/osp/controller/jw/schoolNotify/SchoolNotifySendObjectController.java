package com.yice.edu.cn.osp.controller.jw.schoolNotify;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.jw.department.DepartmentForSchoolNotify;
import com.yice.edu.cn.common.pojo.jw.schoolNotify.SchoolNotify;
import com.yice.edu.cn.common.pojo.jw.schoolNotify.SchoolNotifyClassInfo;
import com.yice.edu.cn.common.pojo.jw.schoolNotify.SchoolNotifySendObject;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import com.yice.edu.cn.osp.service.jw.classes.JwClassesService;
import com.yice.edu.cn.osp.service.jw.department.DepartmentService;
import com.yice.edu.cn.osp.service.jw.schoolNotify.SchoolNotifySendObjectService;
import com.yice.edu.cn.osp.service.jw.schoolNotify.SchoolNotifyService;
import com.yice.edu.cn.osp.service.jw.student.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.*;

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



    @PostMapping("/save/saveSchoolNotify")
    @ApiOperation(value = "保存通知对象", notes = "返回保存好的对象", response=SchoolNotify.class)
    public ResponseJson saveSchoolNotify(
            @ApiParam(value = "对象", required = true)
            @RequestBody SchoolNotify schoolNotify){
        schoolNotify.setSchoolId(mySchoolId());
        schoolNotify.setSenderId(myId());
        schoolNotify.setSenderName(currentTeacher().getName());
        SchoolNotify s=schoolNotifyService.saveSchoolNotify(schoolNotify);
        return new ResponseJson(s);
    }

    @PostMapping("/save/saveSchoolNotifySendObject")
    @ApiOperation(value = "保存通知发送对象", notes = "返回保存好的对象", response = SchoolNotifySendObject.class)
    public ResponseJson saveSchoolNotifySendObject(
            @ApiParam(value = "对象", required = true)
            @RequestBody SchoolNotifySendObject schoolNotifySendObject) {
        schoolNotifySendObject.setSchoolId(mySchoolId());
        SchoolNotifySendObject s = schoolNotifySendObjectService.saveSchoolNotifySendObject(schoolNotifySendObject);
        return new ResponseJson(s);
    }


    @PostMapping("update/updateSchoolNotifySendObject")
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
        SchoolNotifySendObject schoolNotifySendObject=new SchoolNotifySendObject();
       schoolNotifySendObject.setId(id);
       schoolNotifySendObject.setDel("2");
        schoolNotifySendObjectService.updateSchoolNotifySendObject1(schoolNotifySendObject);
        return new ResponseJson();
    }


    @PostMapping("look/findSchoolNotifySendObjectListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response = SchoolNotifySendObject.class)
    public ResponseJson findSchoolNotifySendObjectListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody SchoolNotifySendObject schoolNotifySendObject) {
        schoolNotifySendObject.setSchoolId(mySchoolId());
        List<SchoolNotifySendObject> data = schoolNotifySendObjectService.findSchoolNotifySendObjectListByCondition(schoolNotifySendObject);
        return new ResponseJson(data);
    }

    @PostMapping("look/getMySchoolNotifySendObjectList")
    @ApiOperation(value = "获取当前用户的通知列表,传入收发状态（state）：1、发送列表  2、接受列表  读取状态（readState）：0、全部 1、未读  2、已读", notes = "返回响应对象,不包含总条数", response = SchoolNotifySendObject.class)
    public ResponseJson getMySchoolNotifySendObjectList(@RequestBody SchoolNotifySendObject schoolNotifySendObject) {
        //查询全部要置空读取状态
        if(schoolNotifySendObject.getReadState().equals("0"))
            schoolNotifySendObject.setReadState(null);

        //状态为1：发送列表查询通知表，2：查询的是发送对象表
        if (schoolNotifySendObject.getState().equals("1")) {
            SchoolNotify schoolNotify = schoolNotifySendObject.getSchoolNotify();
            if(schoolNotify==null)
                schoolNotify=new SchoolNotify();
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

    @GetMapping("look/getSchoolNotifyToWebIndex")
    public ResponseJson getSchoolNotifyToWebIndex(){


        return new ResponseJson(schoolNotifySendObjectService.getSchoolNotifyToWebIndex());
    }

    @PostMapping("look/getSchoolNotifyReadDetailById")
    @ApiOperation(value = "获取通知的读取人数详情，传入通知id 、sendType、readState", notes = "返回响应对象,不包含总条数", response = SchoolNotifySendObject.class)
    public ResponseJson getMySchoolNotifySendObjectList( @RequestBody SchoolNotify schoolNotify) {
        String readState=schoolNotify.getReadState();
        if(schoolNotify.getSendType().equals("1")){
            SchoolNotifySendObject schoolNotifySendObject = new SchoolNotifySendObject();
            schoolNotifySendObject.setReadState(schoolNotify.getReadState());
            schoolNotify.setReadState(null);
            schoolNotifySendObject.setSchoolNotify(schoolNotify);
            schoolNotifySendObject.setSchoolId(mySchoolId());
            schoolNotifySendObject.setPager(schoolNotify.getPager());
            List<Department> departmentList = schoolNotifySendObjectService.getSchoolNotifyReadDetail(schoolNotifySendObject);
             long count = schoolNotifySendObjectService.getSchoolNotifyReadDetailCount(schoolNotifySendObject);
            return new ResponseJson(departmentList,count);

        }else{
            SchoolNotifySendObject schoolNotifySendObject = new SchoolNotifySendObject();
            schoolNotify.setReadState(null);
            schoolNotifySendObject.setSchoolNotify(schoolNotify);
            schoolNotifySendObject.setSchoolId(mySchoolId());
            schoolNotifySendObject.setType("0");
            List<SchoolNotifySendObject> schoolNotifySendObjectList= schoolNotifySendObjectService.findSchoolNotifySendObjectListByCondition(schoolNotifySendObject);
            schoolNotifySendObjectList.forEach(schoolNotifySendObject1 -> {
                if(!schoolNotifySendObject1.getDepartmentParentId().equals("-1"))
                    schoolNotifySendObject1.setType("1");
            });
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
     * 已读未读列表树，如果节点下面没有元素，就移除该节点
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


    @GetMapping("/ignore/findDepartmentForTree/{type}")
    @ApiOperation(value = "查找组织架构树,1、为学校结构树（教师） 2、班级学生结构树（家长）")
    public ResponseJson findDepartmentForSchoolNotify(@PathVariable int type) {
        List<Department> departments = null;
        if (type == 1) {
            departments = departmentService.findDepartmentHaveOrNotTeacherForTree(LoginInterceptor.mySchoolId(), false, 0);
        } else if (type == 2) {
            departments = findClassesTree();
        }
        return new ResponseJson(departments);
    }


    public List<Department> findClassesTree() {

        JwClasses jwClasses=new JwClasses();
        jwClasses.setSchoolId(mySchoolId());
        List<JwClasses> classesList=classesService.findJwClassesListByCondition(jwClasses);

        List<SchoolNotifyClassInfo> data=new ArrayList<>();
        //由于原JwClasses对象number为String类型，分组会造成顺序不对，所以创建新对象进行分组
        classesList.forEach(c -> {
            SchoolNotifyClassInfo schoolNotifyClassInfo=new SchoolNotifyClassInfo();
            schoolNotifyClassInfo.setClassGradeId(Integer.valueOf(c.getGradeId()));
            schoolNotifyClassInfo.setGradeId(c.getGradeId());
            schoolNotifyClassInfo.setGradeName(c.getGradeName());
            schoolNotifyClassInfo.setClassNumber(Integer.valueOf(c.getNumber()));
            schoolNotifyClassInfo.setNumber(c.getNumber());
            schoolNotifyClassInfo.setId(c.getId());
            data.add(schoolNotifyClassInfo);
        });



        Map<Integer, Map<Integer, List<SchoolNotifyClassInfo>>> classMap = data.stream().collect(Collectors.groupingBy(SchoolNotifyClassInfo::getClassGradeId, Collectors.groupingBy(SchoolNotifyClassInfo::getClassNumber)));
        List<Department> treeList = new ArrayList<>();

        classMap.forEach((k, v) -> {
            Department department = new Department();
            Map<Integer, List<SchoolNotifyClassInfo>> classMap1 = v;
            List<SchoolNotifyClassInfo> studentList = classMap1.values().stream().findFirst().get();
            department.setType(0);
            department.setParentId("-1");
            department.setName(studentList.get(0).getGradeName());
            department.setId(studentList.get(0).getGradeId());

            List<Department> children = new ArrayList<>();
            classMap1.forEach((k1, v1) -> {
                List<SchoolNotifyClassInfo> Class = v1;
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


    @PostMapping("look/findSchoolNotifySendObjectListForSchoolNotifyByCondition")
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

    @GetMapping("look/findSchoolNotifyById/{id}")
    @ApiOperation(value = "去查看通知对象,通过id查找", notes = "返回响应对象", response=SchoolNotify.class)
    public ResponseJson findSchoolNotifyById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        SchoolNotify schoolNotify=schoolNotifyService.findSchoolNotifyById(id);
        return new ResponseJson(schoolNotify);
    }

    @PostMapping("look/findSchoolNotifyByIdForReadState")
    @ApiOperation(value = "去查看通知对象,通过id查找", notes = "返回响应对象", response=SchoolNotify.class)
    public ResponseJson findSchoolNotifyByIdForReadState(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @RequestBody SchoolNotifySendObject notify){
        SchoolNotify schoolNotify=schoolNotifyService.findSchoolNotifyById(notify.getSchoolNotify().getId());
        if(notify.getReadState().equals("1")){
            schoolNotifySendObjectService.updateSchoolNotifySendObject(notify);
        }

        return new ResponseJson(schoolNotify);
    }

    /**
     * 获取map中第一个数据值
     *
     * @param map 数据源
     * @return
     */
    private static Object getFirstOrNull(Map<Integer, List<SchoolNotifyClassInfo>> map) {

        Object obj = null;
        for (Map.Entry<Integer, List<SchoolNotifyClassInfo>> entry : map.entrySet()) {
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
