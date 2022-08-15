package com.yice.edu.cn.ts.task;

import cn.hutool.json.JSONUtil;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.mes.schoolManage.audit.MesInstitutionAudit;
import com.yice.edu.cn.common.pojo.mes.schoolManage.audit.MesOperateRecord;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.common.util.date.DateUtils;
import com.yice.edu.cn.ts.feignClient.MesInstitutionAuditFeign;
import com.yice.edu.cn.ts.feignClient.MesOperateRecordFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author mayn
 * @createTime 2019-08-05 10:19:22
 * @Description 德育申诉审核，每天01:00更新未审核记录
 */

@Component
@EnableAsync
public class DyInstitutionAuditTask {

    @Autowired
    private MesOperateRecordFeign mesOperateRecordFeign;

    @Autowired
    private MesInstitutionAuditFeign mesInstitutionAuditFeign;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private final static Logger logger = LoggerFactory.getLogger(DmMobileRedBannerTask.class);


    /**
     * 查询出未审核申诉
     * 每天01:00,更改未审核申诉状态
     */
    @Scheduled(cron = "0 0 0 * * ?")
    @Async
    public void updateAuditStatus() {
        try {
            List<MesInstitutionAudit> mesInstitutionAudits = mesInstitutionAuditFeign.selectNoAudit();
            ArrayList<MesOperateRecord> operateRecords = new ArrayList<>(mesInstitutionAudits.size());
            mesInstitutionAudits.forEach(m->{
                MesOperateRecord operateRecord = new MesOperateRecord();
                operateRecord.setOperatorName("系统管理员");
                operateRecord.setAuditId(m.getId());
                operateRecord.setOperateContent("审核过期未处理，申诉失败");
                operateRecord.setOperateDate(DateUtils.Nowss());
                operateRecord.setCreateTime(DateUtils.Nowss());
                operateRecord.setOperateStatus(2);
                operateRecord.setOperateType(1);
                operateRecord.setSchoolId(m.getSchoolId());
                operateRecords.add(operateRecord);
            });
            List<Map> mapList = mesInstitutionAuditFeign.selectTeacherIdsByRecordId(mesInstitutionAudits);
            mapList.forEach(map -> {
                List teacherIdList = (List)map.get("teacherIds");
                String[] teacherIds = (String[]) teacherIdList.toArray(new String[teacherIdList.size()]);
                final Push push = Push.newBuilder(JpushApp.TAP).getSimplePusByRefrenceId(teacherIds,"德育-审核通知","您有一条审核过期未处理，申诉失败，请及时查看！", Extras.MES_INSPECT_RECORD,(String) map.get("recordId"));
                stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
            });
            mesOperateRecordFeign.batchSaveMesOperateRecord(operateRecords);
        } catch (HystrixRuntimeException hystrixRuntimeException) {
            // 调用为服务可能失败，在此记录一下日志
            logger.error("微服务调用失败", hystrixRuntimeException);
        } catch (Exception ex) {
            logger.error("服务器错误", ex);
        }
    }


}
