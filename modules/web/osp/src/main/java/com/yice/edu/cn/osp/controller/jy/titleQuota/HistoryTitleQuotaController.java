package com.yice.edu.cn.osp.controller.jy.titleQuota;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.titleQuota.HistoryTitleQuota;
import com.yice.edu.cn.osp.service.jy.titleQuota.HistoryTitleQuotaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/historyTitleQuota")
@Api(value = "/historyTitleQuota",description = "题目额度资源操作记录表模块")
public class HistoryTitleQuotaController {
    @Autowired
    private HistoryTitleQuotaService historyTitleQuotaService;

    @PostMapping("/findHistoryTitleQuotasByCondition")
    @ApiOperation(value = "根据条件查找题目额度资源操作记录表", notes = "返回响应对象", response=HistoryTitleQuota.class)
    public ResponseJson findHistoryTitleQuotasByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody HistoryTitleQuota historyTitleQuota){
        historyTitleQuota.setSchoolId(mySchoolId());
        List<HistoryTitleQuota> data=historyTitleQuotaService.findHistoryTitleQuotaListByCondition4Like(historyTitleQuota);
        historyTitleQuota.getPager().setPaging(false);
        List<HistoryTitleQuota> data1=historyTitleQuotaService.findHistoryTitleQuotaListByCondition4Like(historyTitleQuota);
        long count= 0;
        if(data1!=null){
            count = data1.size();
        }
        return new ResponseJson(data,count);
    }

}
