package com.yice.edu.cn.tap.controller.dm.ycVeriface;

import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.common.pojo.xw.kq.KqOriginalData;
import com.yice.edu.cn.tap.service.dm.ycVeriface.YcVerifaceAlarmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

/**
 * @author:xushu
 * @date:2019/9/3
 */
@RestController
@RequestMapping("/kqOriginalData")
@Api(value = "/kqOriginalData", description = "陌生人预警模块")
public class YcVerifaceAlarmController {
    @Autowired
    private YcVerifaceAlarmService ycVerifaceAlarmService;

    @PostMapping("/findYcVerifaceAlarmsByCondition")
    @ApiOperation(value = "根据条件查找陌生人预警记录", notes = "返回响应对象", response = KqOriginalData.class)
    public ResponseJson findYcVerifaceAlarmsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqOriginalData kqOriginalData) {
        kqOriginalData.setUserType("-1,0");
        kqOriginalData.setSchoolId(mySchoolId());
        kqOriginalData.getPager().setSortOrder(Pager.DESC);
        kqOriginalData.getPager().setSortField("createTime");
        List<KqOriginalData> data = ycVerifaceAlarmService.findKqOriginalDataListByCondition(kqOriginalData);
        long count = ycVerifaceAlarmService.findKqOriginalDataCountByCondition(kqOriginalData);
        return new ResponseJson(data, count);
    }

    @PostMapping("/update/updateYcVerifaceAlarm")
    @ApiOperation(value = "修改预警记录", notes = "返回响应对象")
    public ResponseJson updateYcVerifaceAlarm(
            @ApiParam(value = "修改预警记录", required = true)
            @RequestBody KqOriginalData kqOriginalData) {
        kqOriginalData.setResolverId(myId());
        kqOriginalData.setSchoolId(mySchoolId());
        kqOriginalData.setResolveStatus("1");
        ycVerifaceAlarmService.updateKqOriginalData(kqOriginalData);
        return new ResponseJson();
    }
}
