package com.yice.edu.cn.tap.controller.appIndex;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.appIndex.AppIndex;
import com.yice.edu.cn.tap.service.appIndex.AppIndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/appIndex")
@Api(value = "/appIndex",description = "教师端新版首页")
public class AppIndexController {
    @Autowired
    private AppIndexService appIndexService;


    @ApiOperation(value = "获取教师端首页数据", notes = "{\n" +
            "  \"result\": {\n" +
            "    \"success\": true\n" +
            "  },\n" +
            "  \"data\": [\n" +
            "    {\n" +
            "      \"id\": \"1\",\n" +
            "      \"identify\": \"visitor\",\n" +
            "      \"title\": \"访客审批\",\n" +
            "      \"parentId\": \"-1\",\n" +
            "      \"row\": 1,\n" +
            "      \"required\": false,\n" +
            "      \"display\": true\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"14\",\n" +
            "      \"identify\": \"carousel\",\n" +
            "      \"title\": \"轮播\",\n" +
            "      \"parentId\": \"-1\",\n" +
            "      \"row\": 2,\n" +
            "      \"required\": true,\n" +
            "      \"display\": true\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"2\",\n" +
            "      \"identify\": \"schoolPush\",\n" +
            "      \"title\": \"公告\",\n" +
            "      \"parentId\": \"-1\",\n" +
            "      \"row\": 3,\n" +
            "      \"required\": false,\n" +
            "      \"display\": true,\n" +
            "      \"data\": \"发给教师和家长\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"3\",\n" +
            "      \"identify\": \"docManagement\",//用以判断哪个模块的唯一标志\n" +
            "      \"title\": \"公文\",//标题\n" +
            "      \"parentId\": \"-1\",//父节点id\n" +
            "      \"row\": 4,//第几行\n" +
            "      \"col\": 1,//第几行里的第几列\n" +
            "      \"required\": false,//忽略该字段\n" +
            "      \"display\": true,//忽略该字段\n" +
            "      \"data\": 3//业务数据,这里表示有几个条未读公文\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"4\",\n" +
            "      \"identify\": \"schoolNotifySendObject\",\n" +
            "      \"title\": \"通知\",\n" +
            "      \"parentId\": \"-1\",\n" +
            "      \"row\": 4,\n" +
            "      \"col\": 2,\n" +
            "      \"required\": false,\n" +
            "      \"display\": true,\n" +
            "      \"data\": 0\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"8\",\n" +
            "      \"identify\": \"officeManager\",\n" +
            "      \"title\": \"OA\",\n" +
            "      \"parentId\": \"-1\",\n" +
            "      \"row\": 5,\n" +
            "      \"required\": false,\n" +
            "      \"children\": [\n" +
            "        {\n" +
            "          \"id\": \"9\",\n" +
            "          \"identify\": \"myProcessApply\",\n" +
            "          \"title\": \"我的申请\",\n" +
            "          \"parentId\": \"8\",\n" +
            "          \"col\": 1,\n" +
            "          \"required\": false,\n" +
            "          \"display\": true,\n" +
            "          \"data\": 22\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": \"10\",\n" +
            "          \"identify\": \"myApprove\",\n" +
            "          \"title\": \"我的审批\",\n" +
            "          \"parentId\": \"8\",\n" +
            "          \"col\": 2,\n" +
            "          \"required\": false,\n" +
            "          \"display\": true,\n" +
            "          \"data\": 8\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": \"11\",\n" +
            "          \"identify\": \"processCopy\",\n" +
            "          \"title\": \"抄送我的\",\n" +
            "          \"parentId\": \"8\",\n" +
            "          \"col\": 3,\n" +
            "          \"required\": false,\n" +
            "          \"display\": true,\n" +
            "          \"data\": 2\n" +
            "        }\n" +
            "      ],\n" +
            "      \"display\": true\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"13\",\n" +
            "      \"identify\": \"classSchedule\",\n" +
            "      \"title\": \"本周课表\",\n" +
            "      \"parentId\": \"-1\",\n" +
            "      \"row\": 6,\n" +
            "      \"required\": false,\n" +
            "      \"display\": true,\n" +
            "      \"data\": [\n" +
            "        [],\n" +
            "        [\n" +
            "          {\n" +
            "            \"id\": \"2361548425875251200\",\n" +
            "            \"numberName\": \"二\",\n" +
            "            \"spaceName\": \"教学主楼 2层 ZHL-100\",\n" +
            "            \"courseName\": \"校本课程\",\n" +
            "            \"gradeNameClassName\": \"高一(2)班\"\n" +
            "          }\n" +
            "        ],\n" +
            "        [\n" +
            "          {\n" +
            "            \"id\": \"2361548425875251201\",\n" +
            "            \"numberName\": \"一\",\n" +
            "            \"spaceName\": \"教学主楼 2层 ZHL-100\",\n" +
            "            \"courseName\": \"校本课程22\",\n" +
            "            \"gradeNameClassName\": \"高一(2)班\"\n" +
            "          }\n" +
            "        ],\n" +
            "        [],\n" +
            "        [],\n" +
            "        [],\n" +
            "        []\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"12\",\n" +
            "      \"identify\": \"personalDuty\",\n" +
            "      \"title\": \"我的值班\",\n" +
            "      \"parentId\": \"-1\",\n" +
            "      \"row\": 7,\n" +
            "      \"required\": false,\n" +
            "      \"display\": true,\n" +
            "      \"data\": \"2019-04-22\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"7\",\n" +
            "      \"identify\": \"homework\",\n" +
            "      \"title\": \"作业\",\n" +
            "      \"parentId\": \"-1\",\n" +
            "      \"row\": 1000,\n" +
            "      \"required\": false,\n" +
            "      \"display\": false,\n" +
            "      \"data\": [\n" +
            "        {\n" +
            "          \"id\": \"1120257692573773824\",\n" +
            "          \"subjectName\": \"语文\",\n" +
            "          \"endTime\": \"2019-04-25 00:00:00\",\n" +
            "          \"homeworkContent\": \"背古诗\",\n" +
            "          \"publishTime\": \"2019-04-22 17:27:00\",\n" +
            "          \"shouldNum\": 7,//总人数\n" +
            "          \"punctualNum\": 0,//正常提交人数\n" +
            "          \"overdueNum\": 0//逾期人数\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}")
    @GetMapping("/getAppIndexes")
    public ResponseJson getAppIndexes(){

        //判断 当前学校账号是否期或者处于升学状态
        ResponseJson responseJson = appIndexService.findSchoolExpireOrSchoolYear(mySchoolId());
        if (!responseJson.getResult().isSuccess()){
            return responseJson;
        }

        List<AppIndex> appIndexList=appIndexService.getAppIndexesForTeacher(mySchoolId(),myId());
        return new ResponseJson(appIndexList);
    }

    @GetMapping("/getCountLeaveSchool")
    public ResponseJson getCountLeaveSchool(){
        Long count =appIndexService.getCountLeaveSchool(mySchoolId(),myId());
        return new ResponseJson(count);
    }

}
