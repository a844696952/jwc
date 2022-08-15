package com.yice.edu.cn.osp.service.xw.stuInAndOut;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.stuInAndOut.DayAndEndTime;
import com.yice.edu.cn.common.pojo.xw.stuInAndOut.StuInAndOutClassTime;
import com.yice.edu.cn.common.pojo.xw.stuInAndOut.StuInAndOutStartTime;
import com.yice.edu.cn.osp.feignClient.xw.stuInAndOut.StuInAndOutClassTimeFeign;
import com.yice.edu.cn.osp.feignClient.xw.stuInAndOut.StuInAndOutStartTimeFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;


@Service
public class StuInAndOutClassTimeService {
    @Autowired
    private StuInAndOutClassTimeFeign stuInAndOutClassTimeFeign;
    @Autowired
    private StuInAndOutStartTimeFeign stuInAndOutStartTimeFeign;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public StuInAndOutClassTime findStuInAndOutClassTimeById(String id) {
        return stuInAndOutClassTimeFeign.findStuInAndOutClassTimeById(id);
    }

    public ResponseJson saveStuInAndOutClassTime(StuInAndOutClassTime stuInAndOutClassTime) {
        StuInAndOutStartTime stuInAndOutStartTime = new StuInAndOutStartTime();
        stuInAndOutStartTime.setSchoolId(mySchoolId());
        List<StuInAndOutStartTime> list = stuInAndOutStartTimeFeign.findStuInAndOutStartTimeListByCondition(stuInAndOutStartTime);
        if (list.size() == 1) {
            List<DayAndEndTime> list0 = stuInAndOutClassTime.getList().get(0).getList().stream().filter(f -> Objects.nonNull(f) && StrUtil.isNotEmpty(f.getEndTime())).collect(Collectors.toList());
            String startTime0 = stuInAndOutClassTime.getList().get(0).getStartTime();
            if (list0.size() > 0) {
                DayAndEndTime dy = list0.stream().min((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                if (DateUtil.timeToSecond(dy.getEndTime()) <= DateUtil.timeToSecond(startTime0)) {
                    return new ResponseJson(false, "所选时间不符合规则，请重新选择");
                }
            }
        }
        if (list.size() == 2) {
            if (stuInAndOutClassTime.getList().size() == 1) {
                List<DayAndEndTime> list0 = stuInAndOutClassTime.getList().get(0).getList().stream().filter(f -> Objects.nonNull(f) && StrUtil.isNotEmpty(f.getEndTime())).collect(Collectors.toList());
                String startTime0 = stuInAndOutClassTime.getList().get(0).getStartTime();
                if (list0.size() > 0) {
                    DayAndEndTime dy = list0.stream().min((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                    DayAndEndTime dy1 = list0.stream().max((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                    if (DateUtil.timeToSecond(dy.getEndTime()) <= DateUtil.timeToSecond(startTime0) ||
                            DateUtil.timeToSecond(dy1.getEndTime()) >= DateUtil.timeToSecond(list.get(1).getStartTime())) {
                        return new ResponseJson(false, "所选时间不符合规则，请重新选择");
                    }
                }
            } else if (stuInAndOutClassTime.getList().size() == 2) {
                List<DayAndEndTime> list0 = stuInAndOutClassTime.getList().get(0).getList().stream().filter(f -> Objects.nonNull(f) && StrUtil.isNotEmpty(f.getEndTime())).collect(Collectors.toList());
                List<DayAndEndTime> list1 = stuInAndOutClassTime.getList().get(1).getList().stream().filter(f -> Objects.nonNull(f) && StrUtil.isNotEmpty(f.getEndTime())).collect(Collectors.toList());
                String startTime0 = stuInAndOutClassTime.getList().get(0).getStartTime();
                String startTime1 = stuInAndOutClassTime.getList().get(1).getStartTime();
                if (list0.size() > 0 && list1.size() > 0) {
                    DayAndEndTime dy = list0.stream().min((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                    DayAndEndTime dy1 = list0.stream().max((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                    DayAndEndTime dy2 = list1.stream().min((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                    if (DateUtil.timeToSecond(dy.getEndTime()) <= DateUtil.timeToSecond(startTime0) ||
                            DateUtil.timeToSecond(dy1.getEndTime()) >= DateUtil.timeToSecond(startTime1) ||
                            DateUtil.timeToSecond(dy2.getEndTime()) <= DateUtil.timeToSecond(startTime1)
                            ) {
                        return new ResponseJson(false, "所选时间不符合规则，请重新选择");
                    }
                }
                if (list0.size() > 0 && list1.size() == 0) {
                    DayAndEndTime dy = list0.stream().min((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                    DayAndEndTime dy1 = list0.stream().max((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                    if (DateUtil.timeToSecond(dy.getEndTime()) <= DateUtil.timeToSecond(startTime0) ||
                            DateUtil.timeToSecond(dy1.getEndTime()) >= DateUtil.timeToSecond(startTime1)
                            ) {
                        return new ResponseJson(false, "所选时间不符合规则，请重新选择");
                    }
                }
                if (list0.size() == 0 && list1.size() > 0) {
                    DayAndEndTime dy2 = list1.stream().min((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                    if (DateUtil.timeToSecond(dy2.getEndTime()) <= DateUtil.timeToSecond(startTime1)) {
                        return new ResponseJson(false, "所选时间不符合规则，请重新选择");
                    }
                }
            }
        }
        if (list.size() == 3) {
            if (stuInAndOutClassTime.getList().size() == 1) {
                List<DayAndEndTime> list0 = stuInAndOutClassTime.getList().get(0).getList().stream().filter(f -> Objects.nonNull(f) && StrUtil.isNotEmpty(f.getEndTime())).collect(Collectors.toList());
                String startTime0 = stuInAndOutClassTime.getList().get(0).getStartTime();
                if (list0.size() > 0) {
                    DayAndEndTime dy = list0.stream().min((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                    DayAndEndTime dy1 = list0.stream().max((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                    if (DateUtil.timeToSecond(dy.getEndTime()) <= DateUtil.timeToSecond(startTime0) ||
                            DateUtil.timeToSecond(dy1.getEndTime()) >= DateUtil.timeToSecond(list.get(1).getStartTime())) {
                        return new ResponseJson(false, "所选时间不符合规则，请重新选择");
                    }
                }
            } else if (stuInAndOutClassTime.getList().size() == 2) {
                List<DayAndEndTime> list0 = stuInAndOutClassTime.getList().get(0).getList().stream().filter(f -> Objects.nonNull(f) && StrUtil.isNotEmpty(f.getEndTime())).collect(Collectors.toList());
                List<DayAndEndTime> list1 = stuInAndOutClassTime.getList().get(1).getList().stream().filter(f -> Objects.nonNull(f) && StrUtil.isNotEmpty(f.getEndTime())).collect(Collectors.toList());
                String startTime0 = stuInAndOutClassTime.getList().get(0).getStartTime();
                String startTime1 = stuInAndOutClassTime.getList().get(1).getStartTime();
                if (list0.size() > 0 && list1.size() > 0) {
                    DayAndEndTime dy = list0.stream().min((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                    DayAndEndTime dy1 = list0.stream().max((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                    DayAndEndTime dy2 = list1.stream().min((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                    DayAndEndTime dy3 = list1.stream().max((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                    if (DateUtil.timeToSecond(dy.getEndTime()) <= DateUtil.timeToSecond(startTime0) ||
                            DateUtil.timeToSecond(dy1.getEndTime()) >= DateUtil.timeToSecond(startTime1) ||
                            DateUtil.timeToSecond(dy2.getEndTime()) <= DateUtil.timeToSecond(startTime1) ||
                            DateUtil.timeToSecond(dy3.getEndTime()) >= DateUtil.timeToSecond(list.get(2).getStartTime())
                            ) {
                        return new ResponseJson(false, "所选时间不符合规则，请重新选择");
                    }
                }
                if (list0.size() > 0 && list1.size() == 0) {
                    DayAndEndTime dy = list0.stream().min((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                    DayAndEndTime dy1 = list0.stream().max((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                    if (DateUtil.timeToSecond(dy.getEndTime()) <= DateUtil.timeToSecond(startTime0) ||
                            DateUtil.timeToSecond(dy1.getEndTime()) >= DateUtil.timeToSecond(startTime1)
                            ) {
                        return new ResponseJson(false, "所选时间不符合规则，请重新选择");
                    }
                }
                if (list0.size() == 0 && list1.size() > 0) {
                    DayAndEndTime dy0 = list1.stream().min((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                    DayAndEndTime dy1 = list1.stream().max((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                    if (DateUtil.timeToSecond(dy0.getEndTime()) <= DateUtil.timeToSecond(startTime1) ||
                            DateUtil.timeToSecond(dy1.getEndTime()) >= DateUtil.timeToSecond(list.get(2).getStartTime())) {
                        return new ResponseJson(false, "所选时间不符合规则，请重新选择");
                    }
                }
            } else if (stuInAndOutClassTime.getList().size() == 3) {
                List<DayAndEndTime> list0 = stuInAndOutClassTime.getList().get(0).getList().stream().filter(f -> Objects.nonNull(f) && StrUtil.isNotEmpty(f.getEndTime())).collect(Collectors.toList());
                List<DayAndEndTime> list1 = stuInAndOutClassTime.getList().get(1).getList().stream().filter(f -> Objects.nonNull(f) && StrUtil.isNotEmpty(f.getEndTime())).collect(Collectors.toList());
                List<DayAndEndTime> list2 = stuInAndOutClassTime.getList().get(2).getList().stream().filter(f -> Objects.nonNull(f) && StrUtil.isNotEmpty(f.getEndTime())).collect(Collectors.toList());
                String startTime0 = stuInAndOutClassTime.getList().get(0).getStartTime();
                String startTime1 = stuInAndOutClassTime.getList().get(1).getStartTime();
                String startTime2 = stuInAndOutClassTime.getList().get(2).getStartTime();
                if ((list0.size() == 0 && list1.size() == 0 && list2.size() > 0)) {
                    DayAndEndTime dy = list2.stream().min((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                    if (DateUtil.timeToSecond(dy.getEndTime()) <= DateUtil.timeToSecond(startTime2)) {
                        return new ResponseJson(false, "所选时间不符合规则，请重新选择");
                    }
                }
                if ((list0.size() == 0 && list1.size() > 0 && list2.size() == 0)) {
                    DayAndEndTime dy = list1.stream().min((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                    DayAndEndTime dy1 = list1.stream().max((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                    if (DateUtil.timeToSecond(dy.getEndTime()) <= DateUtil.timeToSecond(startTime1) ||
                            DateUtil.timeToSecond(dy1.getEndTime()) >= DateUtil.timeToSecond(startTime2)) {
                        return new ResponseJson(false, "所选时间不符合规则，请重新选择");
                    }
                }
                if ((list0.size() == 0 && list1.size() > 0 && list2.size() > 0)) {
                    DayAndEndTime dy = list1.stream().min((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                    DayAndEndTime dy1 = list1.stream().max((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                    DayAndEndTime dy2 = list2.stream().min((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                    if (DateUtil.timeToSecond(dy.getEndTime()) <= DateUtil.timeToSecond(startTime1) ||
                            DateUtil.timeToSecond(dy1.getEndTime()) >= DateUtil.timeToSecond(startTime2) ||
                            DateUtil.timeToSecond(dy2.getEndTime()) <= DateUtil.timeToSecond(startTime2)) {
                        return new ResponseJson(false, "所选时间不符合规则，请重新选择");
                    }
                }
                if ((list0.size() > 0 && list1.size() == 0 && list2.size() == 0)) {
                    DayAndEndTime dy = list0.stream().min((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                    DayAndEndTime dy1 = list0.stream().max((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                    if (DateUtil.timeToSecond(dy.getEndTime()) <= DateUtil.timeToSecond(startTime0) ||
                            DateUtil.timeToSecond(dy1.getEndTime()) >= DateUtil.timeToSecond(startTime1)) {
                        return new ResponseJson(false, "所选时间不符合规则，请重新选择");
                    }
                }
                if ((list0.size() > 0 && list1.size() == 0 && list2.size() > 0)) {
                    DayAndEndTime dy = list0.stream().min((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                    DayAndEndTime dy1 = list0.stream().max((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                    DayAndEndTime dy2 = list2.stream().min((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                    if (DateUtil.timeToSecond(dy.getEndTime()) <= DateUtil.timeToSecond(startTime0) ||
                            DateUtil.timeToSecond(dy1.getEndTime()) >= DateUtil.timeToSecond(startTime1) ||
                            DateUtil.timeToSecond(dy2.getEndTime()) <= DateUtil.timeToSecond(startTime2)) {
                        return new ResponseJson(false, "所选时间不符合规则，请重新选择");
                    }
                }
                if ((list0.size() > 0 && list1.size() > 0 && list2.size() > 0)) {
                    DayAndEndTime dy = list0.stream().min((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                    DayAndEndTime dy1 = list0.stream().max((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                    DayAndEndTime dy2 = list1.stream().min((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                    DayAndEndTime dy3 = list1.stream().max((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                    DayAndEndTime dy4 = list2.stream().min((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                    if (DateUtil.timeToSecond(dy.getEndTime()) <= DateUtil.timeToSecond(startTime0) ||
                            DateUtil.timeToSecond(dy1.getEndTime()) >= DateUtil.timeToSecond(startTime1) ||
                            DateUtil.timeToSecond(dy2.getEndTime()) <= DateUtil.timeToSecond(startTime1) ||
                            DateUtil.timeToSecond(dy3.getEndTime()) >= DateUtil.timeToSecond(startTime2) ||
                            DateUtil.timeToSecond(dy4.getEndTime()) <= DateUtil.timeToSecond(startTime2)) {
                        return new ResponseJson(false, "所选时间不符合规则，请重新选择");
                    }
                }
                if ((list0.size() > 0 && list1.size() > 0 && list2.size() == 0)) {
                    DayAndEndTime dy = list0.stream().min((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                    DayAndEndTime dy1 = list0.stream().max((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                    DayAndEndTime dy2 = list1.stream().min((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                    DayAndEndTime dy3 = list1.stream().max((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                    if (DateUtil.timeToSecond(dy.getEndTime()) <= DateUtil.timeToSecond(startTime0) ||
                            DateUtil.timeToSecond(dy1.getEndTime()) >= DateUtil.timeToSecond(startTime1) ||
                            DateUtil.timeToSecond(dy2.getEndTime()) <= DateUtil.timeToSecond(startTime1) ||
                            DateUtil.timeToSecond(dy3.getEndTime()) >= DateUtil.timeToSecond(startTime2)) {
                        return new ResponseJson(false, "所选时间不符合规则，请重新选择");
                    }
                }
            }
        }
        stuInAndOutClassTimeFeign.saveStuInAndOutClassTime(stuInAndOutClassTime);
        return new ResponseJson();
    }

    public void batchSaveStuInAndOutClassTime(List<StuInAndOutClassTime> stuInAndOutClassTimes) {
        stuInAndOutClassTimeFeign.batchSaveStuInAndOutClassTime(stuInAndOutClassTimes);
    }

    public List<StuInAndOutClassTime> findStuInAndOutClassTimeListByCondition(StuInAndOutClassTime stuInAndOutClassTime) {
        return stuInAndOutClassTimeFeign.findStuInAndOutClassTimeListByCondition(stuInAndOutClassTime);
    }

    public StuInAndOutClassTime findOneStuInAndOutClassTimeByCondition(StuInAndOutClassTime stuInAndOutClassTime) {
        return stuInAndOutClassTimeFeign.findOneStuInAndOutClassTimeByCondition(stuInAndOutClassTime);
    }

    public long findStuInAndOutClassTimeCountByCondition(StuInAndOutClassTime stuInAndOutClassTime) {
        return stuInAndOutClassTimeFeign.findStuInAndOutClassTimeCountByCondition(stuInAndOutClassTime);
    }

    public void updateStuInAndOutClassTime(StuInAndOutClassTime stuInAndOutClassTime) {
        stuInAndOutClassTimeFeign.updateStuInAndOutClassTime(stuInAndOutClassTime);
    }

    public void updateStuInAndOutClassTimeForAll(StuInAndOutClassTime stuInAndOutClassTime) {
        stuInAndOutClassTimeFeign.updateStuInAndOutClassTimeForAll(stuInAndOutClassTime);
    }

    public void deleteStuInAndOutClassTime(String id) {
        stuInAndOutClassTimeFeign.deleteStuInAndOutClassTime(id);
    }

    public void deleteStuInAndOutClassTimeByCondition(StuInAndOutClassTime stuInAndOutClassTime) {
        stuInAndOutClassTimeFeign.deleteStuInAndOutClassTimeByCondition(stuInAndOutClassTime);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
