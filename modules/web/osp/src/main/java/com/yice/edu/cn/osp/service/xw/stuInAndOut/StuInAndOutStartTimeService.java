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
public class StuInAndOutStartTimeService {
    @Autowired
    private StuInAndOutStartTimeFeign stuInAndOutStartTimeFeign;
    @Autowired
    private StuInAndOutClassTimeFeign stuInAndOutClassTimeFeign;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public StuInAndOutStartTime findStuInAndOutStartTimeById(String id) {
        return stuInAndOutStartTimeFeign.findStuInAndOutStartTimeById(id);
    }

    public ResponseJson saveStuInAndOutStartTime(StuInAndOutStartTime stuInAndOutStartTime) {
        StuInAndOutStartTime sst = new StuInAndOutStartTime();
        sst.setSchoolId(mySchoolId());
        StuInAndOutClassTime ssc = new StuInAndOutClassTime();
        ssc.setSchoolId(mySchoolId());
        List<StuInAndOutClassTime> sscList = stuInAndOutClassTimeFeign.findStuInAndOutClassTimeListByCondition(ssc);
        if (sscList != null && sscList.size() > 0) {
            for (StuInAndOutClassTime so : sscList) {
                List<StuInAndOutStartTime> sstList = stuInAndOutStartTimeFeign.findStuInAndOutStartTimeListByCondition(sst);
                if (sstList.size() == 1) {
                    List<DayAndEndTime> list0 = so.getList().get(0).getList().stream().filter(f -> Objects.nonNull(f) && StrUtil.isNotEmpty(f.getEndTime())).collect(Collectors.toList());
                    if (list0.size() == 0) {
                        if (DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) <= DateUtil.timeToSecond(so.getList().get(0).getStartTime())) {
                            return new ResponseJson(false, "所选时间不符合规则,请重新选择");
                        }
                    } else {
                        DayAndEndTime dy = list0.stream().max((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                        if (DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) <= DateUtil.timeToSecond(dy.getEndTime())) {
                            return new ResponseJson(false, "所选时间不符合规则,请重新选择");
                        }
                    }
                } else if (sstList.size() == 2) {
                    if (so.getList().size() == 1) {
                        List<DayAndEndTime> list0 = so.getList().get(0).getList().stream().filter(f -> Objects.nonNull(f) && StrUtil.isNotEmpty(f.getEndTime())).collect(Collectors.toList());
                        if (list0.size() == 0) {
                            if (DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) <= DateUtil.timeToSecond(so.getList().get(0).getStartTime())) {
                                return new ResponseJson(false, "所选时间不符合规则,请重新选择");
                            }
                        } else {
                            DayAndEndTime dy = list0.stream().max((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                            if (DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) <= DateUtil.timeToSecond(dy.getEndTime())) {
                                return new ResponseJson(false, "所选时间不符合规则,请重新选择");
                            }
                        }
                    } else if (so.getList().size() == 2) {
                        List<DayAndEndTime> list0 = so.getList().get(0).getList().stream().filter(f -> Objects.nonNull(f) && StrUtil.isNotEmpty(f.getEndTime())).collect(Collectors.toList());
                        List<DayAndEndTime> list1 = so.getList().get(1).getList().stream().filter(f -> Objects.nonNull(f) && StrUtil.isNotEmpty(f.getEndTime())).collect(Collectors.toList());
                        if (list0.size() == 0 && list1.size() > 0) {
                            DayAndEndTime dy = list1.stream().max((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                            if (DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) <= DateUtil.timeToSecond(dy.getEndTime())) {
                                return new ResponseJson(false, "所选时间不符合规则,请重新选择");
                            }
                        }
                        if (list0.size() > 0 && list1.size() == 0) {
                            if (DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) <= DateUtil.timeToSecond(so.getList().get(1).getStartTime())) {
                                return new ResponseJson(false, "所选时间不符合规则,请重新选择");
                            }
                        }
                        if (list0.size() > 0 && list1.size() > 0) {
                            DayAndEndTime dy = list1.stream().max((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                            if (DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) <= DateUtil.timeToSecond(dy.getEndTime())) {
                                return new ResponseJson(false, "所选时间不符合规则,请重新选择");
                            }
                        }
                        if (list0.size() == 0 && list1.size() == 0) {
                            if (DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) <= DateUtil.timeToSecond(so.getList().get(2).getStartTime())) {
                                return new ResponseJson(false, "所选时间不符合规则,请重新选择");
                            }
                        }
                    }
                }
            }
            stuInAndOutStartTimeFeign.saveStuInAndOutStartTime(stuInAndOutStartTime);
        } else {
            stuInAndOutStartTimeFeign.saveStuInAndOutStartTime(stuInAndOutStartTime);
        }
        return new ResponseJson();
    }

    public void batchSaveStuInAndOutStartTime(List<StuInAndOutStartTime> stuInAndOutStartTimes) {
        stuInAndOutStartTimeFeign.batchSaveStuInAndOutStartTime(stuInAndOutStartTimes);
    }

    public List<StuInAndOutStartTime> findStuInAndOutStartTimeListByCondition(StuInAndOutStartTime stuInAndOutStartTime) {
        return stuInAndOutStartTimeFeign.findStuInAndOutStartTimeListByCondition(stuInAndOutStartTime);
    }

    public StuInAndOutStartTime findOneStuInAndOutStartTimeByCondition(StuInAndOutStartTime stuInAndOutStartTime) {
        return stuInAndOutStartTimeFeign.findOneStuInAndOutStartTimeByCondition(stuInAndOutStartTime);
    }

    public long findStuInAndOutStartTimeCountByCondition(StuInAndOutStartTime stuInAndOutStartTime) {
        return stuInAndOutStartTimeFeign.findStuInAndOutStartTimeCountByCondition(stuInAndOutStartTime);
    }

    public ResponseJson updateStuInAndOutStartTime(StuInAndOutStartTime stuInAndOutStartTime) {
        StuInAndOutClassTime ssc = new StuInAndOutClassTime();
        ssc.setSchoolId(mySchoolId());
        List<StuInAndOutClassTime> sscList = stuInAndOutClassTimeFeign.findStuInAndOutClassTimeListByCondition(ssc);
        StuInAndOutStartTime sst = new StuInAndOutStartTime();
        sst.setSchoolId(mySchoolId());
        List<StuInAndOutStartTime> sstList = stuInAndOutStartTimeFeign.findStuInAndOutStartTimeListByCondition(sst);
        if (sscList.size() == 0) {
            stuInAndOutStartTimeFeign.updateStuInAndOutStartTime(stuInAndOutStartTime);
        } else {
            Integer range = isWhatRange(stuInAndOutStartTime);
            for (StuInAndOutClassTime so : sscList) {
                if (range == 0) {
                    if (sstList.size() == 1) {
                        List<DayAndEndTime> list0 = so.getList().get(0).getList().stream().filter(f -> Objects.nonNull(f) && StrUtil.isNotEmpty(f.getEndTime())).collect(Collectors.toList());
                        if (list0.size() > 0) {
                            DayAndEndTime dy = list0.stream().min((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                            if (DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) >= DateUtil.timeToSecond(dy.getEndTime())) {
                                return new ResponseJson(false, "所选时间不符合规则,请重新选择");
                            }
                        }
                        so.getList().get(range).setStartTime(stuInAndOutStartTime.getStartTime());
                    } else if (sstList.size() == 2) {
                        if (so.getList().size() == 1) {
                            List<DayAndEndTime> list0 = so.getList().get(0).getList().stream().filter(f -> Objects.nonNull(f) && StrUtil.isNotEmpty(f.getEndTime())).collect(Collectors.toList());
                            if (list0.size() > 0) {
                                DayAndEndTime dy = list0.stream().min((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                                if (DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) >= DateUtil.timeToSecond(dy.getEndTime())) {
                                    return new ResponseJson(false, "所选时间不符合规则,请重新选择");
                                }
                                so.getList().get(range).setStartTime(stuInAndOutStartTime.getStartTime());
                            }
                        } else if (so.getList().size() == 2) {
                            List<DayAndEndTime> list0 = so.getList().get(0).getList().stream().filter(f -> Objects.nonNull(f) && StrUtil.isNotEmpty(f.getEndTime())).collect(Collectors.toList());
                            List<DayAndEndTime> list1 = so.getList().get(1).getList().stream().filter(f -> Objects.nonNull(f) && StrUtil.isNotEmpty(f.getEndTime())).collect(Collectors.toList());
                            if ((list0.size() == 0 && list1.size() == 0) || (list0.size() == 0 && list1.size() > 0)) {
                                if (DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) >= DateUtil.timeToSecond(so.getList().get(1).getStartTime())) {
                                    return new ResponseJson(false, "所选时间不符合规则,请重新选择");
                                }
                            }
                            if ((list0.size() > 0 && list1.size() == 0) || (list0.size() > 0 && list1.size() > 0)) {
                                DayAndEndTime dy = list0.stream().min((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                                if (DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) >= DateUtil.timeToSecond(dy.getEndTime())) {
                                    return new ResponseJson(false, "所选时间不符合规则,请重新选择");
                                }
                            }
                            so.getList().get(range).setStartTime(stuInAndOutStartTime.getStartTime());
                        }
                    } else if (sstList.size() == 3) {
                        if (so.getList().size() == 1) {
                            List<DayAndEndTime> list0 = so.getList().get(0).getList().stream().filter(f -> Objects.nonNull(f) && StrUtil.isNotEmpty(f.getEndTime())).collect(Collectors.toList());
                            if (list0.size() > 0) {
                                DayAndEndTime dy = list0.stream().min((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                                if (DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) >= DateUtil.timeToSecond(dy.getEndTime())) {
                                    return new ResponseJson(false, "所选时间不符合规则,请重新选择");
                                }
                            }
                            so.getList().get(range).setStartTime(stuInAndOutStartTime.getStartTime());
                        } else if (so.getList().size() == 2) {
                            List<DayAndEndTime> list0 = so.getList().get(0).getList().stream().filter(f -> Objects.nonNull(f) && StrUtil.isNotEmpty(f.getEndTime())).collect(Collectors.toList());
                            List<DayAndEndTime> list1 = so.getList().get(1).getList().stream().filter(f -> Objects.nonNull(f) && StrUtil.isNotEmpty(f.getEndTime())).collect(Collectors.toList());
                            if ((list0.size() == 0 && list1.size() == 0) || (list0.size() == 0 && list1.size() > 0)) {
                                if (DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) >= DateUtil.timeToSecond(so.getList().get(1).getStartTime())) {
                                    return new ResponseJson(false, "所选时间不符合规则,请重新选择");
                                }
                            }
                            if ((list0.size() > 0 && list1.size() == 0) || (list0.size() > 0 && list1.size() > 0)) {
                                DayAndEndTime dy = list0.stream().min((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                                if (DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) >= DateUtil.timeToSecond(dy.getEndTime())) {
                                    return new ResponseJson(false, "所选时间不符合规则,请重新选择");
                                }
                            }
                            so.getList().get(range).setStartTime(stuInAndOutStartTime.getStartTime());
                        } else if (so.getList().size() == 3) {
                            List<DayAndEndTime> list0 = so.getList().get(0).getList().stream().filter(f -> Objects.nonNull(f) && StrUtil.isNotEmpty(f.getEndTime())).collect(Collectors.toList());
                            List<DayAndEndTime> list1 = so.getList().get(1).getList().stream().filter(f -> Objects.nonNull(f) && StrUtil.isNotEmpty(f.getEndTime())).collect(Collectors.toList());
                            List<DayAndEndTime> list2 = so.getList().get(2).getList().stream().filter(f -> Objects.nonNull(f) && StrUtil.isNotEmpty(f.getEndTime())).collect(Collectors.toList());
                            if ((list0.size() == 0 && list1.size() == 0 && list2.size() == 0) ||
                                    (list0.size() == 0 && list1.size() == 0 && list2.size() > 0) ||
                                    (list0.size() == 0 && list1.size() > 0 && list2.size() == 0) ||
                                    (list0.size() == 0 && list1.size() > 0 && list2.size() > 0)) {
                                if (DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) >= DateUtil.timeToSecond(so.getList().get(1).getStartTime())) {
                                    return new ResponseJson(false, "所选时间不符合规则,请重新选择");
                                }
                            }
                            if ((list0.size() > 0 && list1.size() == 0 && list2.size() == 0) ||
                                    (list0.size() > 0 && list1.size() == 0 && list2.size() > 0) ||
                                    (list0.size() > 0 && list1.size() > 0 && list2.size() == 0) ||
                                    (list0.size() > 0 && list1.size() > 0 && list2.size() > 0)) {
                                DayAndEndTime dy = list0.stream().min((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                                if (DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) >= DateUtil.timeToSecond(dy.getEndTime())) {
                                    return new ResponseJson(false, "所选时间不符合规则,请重新选择");
                                }
                            }
                            so.getList().get(range).setStartTime(stuInAndOutStartTime.getStartTime());
                        }
                    }
                } else if (range == 1) {
                    if (sstList.size() == 2) {
                        if (so.getList().size() == 1) {
                            List<DayAndEndTime> list0 = so.getList().get(0).getList().stream().filter(f -> Objects.nonNull(f) && StrUtil.isNotEmpty(f.getEndTime())).collect(Collectors.toList());
                            if (list0.size() > 0) {
                                DayAndEndTime dy = list0.stream().max((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                                if (DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) <= DateUtil.timeToSecond(dy.getEndTime())) {
                                    return new ResponseJson(false, "所选时间不符合规则,请重新选择");
                                }
                            }
                        } else if (so.getList().size() == 2) {
                            List<DayAndEndTime> list0 = so.getList().get(0).getList().stream().filter(f -> Objects.nonNull(f) && StrUtil.isNotEmpty(f.getEndTime())).collect(Collectors.toList());
                            List<DayAndEndTime> list1 = so.getList().get(1).getList().stream().filter(f -> Objects.nonNull(f) && StrUtil.isNotEmpty(f.getEndTime())).collect(Collectors.toList());
                            if (list0.size() == 0 && list1.size() > 0) {
                                DayAndEndTime dy = list1.stream().min((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                                if (DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) <= DateUtil.timeToSecond(so.getList().get(0).getStartTime()) ||
                                        DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) >= DateUtil.timeToSecond(dy.getEndTime())) {
                                    return new ResponseJson(false, "所选时间不符合规则,请重新选择");
                                }
                            }
                            if (list0.size() > 0 && list1.size() == 0) {
                                DayAndEndTime dy = list0.stream().max((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                                if (DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) <= DateUtil.timeToSecond(dy.getEndTime())) {
                                    return new ResponseJson(false, "所选时间不符合规则,请重新选择");
                                }
                            }
                            if (list0.size() > 0 && list1.size() > 0) {
                                DayAndEndTime dy = list0.stream().max((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                                DayAndEndTime dy1 = list1.stream().min((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                                if (DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) <= DateUtil.timeToSecond(dy.getEndTime()) ||
                                        DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) >= DateUtil.timeToSecond(dy1.getEndTime())) {
                                    return new ResponseJson(false, "所选时间不符合规则,请重新选择");
                                }
                            }
                            if (list0.size() == 0 && list1.size() == 0) {
                                if (DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) <= DateUtil.timeToSecond(so.getList().get(0).getStartTime())) {
                                    return new ResponseJson(false, "所选时间不符合规则,请重新选择");
                                }
                            }
                            so.getList().get(range).setStartTime(stuInAndOutStartTime.getStartTime());
                        }
                    } else if (sstList.size() == 3) {
                        if (so.getList().size() == 1) {
                            List<DayAndEndTime> list0 = so.getList().get(0).getList().stream().filter(f -> Objects.nonNull(f) && StrUtil.isNotEmpty(f.getEndTime())).collect(Collectors.toList());
                            if (list0.size() > 0) {
                                DayAndEndTime dy = list0.stream().max((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                                if (DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) <= DateUtil.timeToSecond(dy.getEndTime())) {
                                    return new ResponseJson(false, "所选时间不符合规则,请重新选择");
                                }
                            }
                        } else if (so.getList().size() == 2) {
                            List<DayAndEndTime> list0 = so.getList().get(0).getList().stream().filter(f -> Objects.nonNull(f) && StrUtil.isNotEmpty(f.getEndTime())).collect(Collectors.toList());
                            List<DayAndEndTime> list1 = so.getList().get(1).getList().stream().filter(f -> Objects.nonNull(f) && StrUtil.isNotEmpty(f.getEndTime())).collect(Collectors.toList());
                            if (list0.size() == 0 && list1.size() > 0) {
                                DayAndEndTime dy = list1.stream().min((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                                if (DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) <= DateUtil.timeToSecond(so.getList().get(0).getStartTime()) ||
                                        DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) >= DateUtil.timeToSecond(dy.getEndTime())) {
                                    return new ResponseJson(false, "所选时间不符合规则,请重新选择");
                                }
                            }
                            if (list0.size() > 0 && list1.size() == 0) {
                                DayAndEndTime dy = list0.stream().max((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                                if (DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) <= DateUtil.timeToSecond(dy.getEndTime())) {
                                    return new ResponseJson(false, "所选时间不符合规则,请重新选择");
                                }
                            }
                            if (list0.size() > 0 && list1.size() > 0) {
                                DayAndEndTime dy = list0.stream().max((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                                DayAndEndTime dy1 = list1.stream().min((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                                if (DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) <= DateUtil.timeToSecond(dy.getEndTime()) ||
                                        DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) >= DateUtil.timeToSecond(dy1.getEndTime())) {
                                    return new ResponseJson(false, "所选时间不符合规则,请重新选择");
                                }
                            }
                            if (list0.size() == 0 && list1.size() == 0) {
                                if (DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) <= DateUtil.timeToSecond(so.getList().get(0).getStartTime())) {
                                    return new ResponseJson(false, "所选时间不符合规则,请重新选择");
                                }
                            }
                            so.getList().get(range).setStartTime(stuInAndOutStartTime.getStartTime());
                        } else if (so.getList().size() == 3) {
                            List<DayAndEndTime> list0 = so.getList().get(0).getList().stream().filter(f -> Objects.nonNull(f) && StrUtil.isNotEmpty(f.getEndTime())).collect(Collectors.toList());
                            List<DayAndEndTime> list1 = so.getList().get(1).getList().stream().filter(f -> Objects.nonNull(f) && StrUtil.isNotEmpty(f.getEndTime())).collect(Collectors.toList());
                            List<DayAndEndTime> list2 = so.getList().get(2).getList().stream().filter(f -> Objects.nonNull(f) && StrUtil.isNotEmpty(f.getEndTime())).collect(Collectors.toList());
                            if ((list0.size() == 0 && list1.size() == 0 && list2.size() == 0) ||
                                    (list0.size() == 0 && list1.size() == 0 && list2.size() > 0)) {
                                if (DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) <= DateUtil.timeToSecond(so.getList().get(0).getStartTime()) ||
                                        DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) >= DateUtil.timeToSecond(so.getList().get(2).getStartTime())) {
                                    return new ResponseJson(false, "所选时间不符合规则,请重新选择");
                                }
                            }
                            if ((list0.size() == 0 && list1.size() > 0 && list2.size() == 0) ||
                                    (list0.size() == 0 && list1.size() > 0 && list2.size() > 0)) {
                                DayAndEndTime dy = list1.stream().min((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                                if (DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) <= DateUtil.timeToSecond(so.getList().get(0).getStartTime()) ||
                                        DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) >= DateUtil.timeToSecond(dy.getEndTime())) {
                                    return new ResponseJson(false, "所选时间不符合规则,请重新选择");
                                }
                            }
                            if ((list0.size() > 0 && list1.size() == 0 && list2.size() == 0) ||
                                    (list0.size() > 0 && list1.size() == 0 && list2.size() > 0)) {
                                DayAndEndTime dy = list0.stream().max((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                                if (DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) <= DateUtil.timeToSecond(dy.getEndTime()) ||
                                        DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) >= DateUtil.timeToSecond(so.getList().get(2).getStartTime())) {
                                    return new ResponseJson(false, "所选时间不符合规则,请重新选择");
                                }
                            }
                            if ((list0.size() > 0 && list1.size() > 0 && list2.size() == 0) ||
                                    (list0.size() > 0 && list1.size() > 0 && list2.size() > 0)) {
                                DayAndEndTime dy = list0.stream().max((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                                DayAndEndTime dy1 = list1.stream().min((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                                if (DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) <= DateUtil.timeToSecond(dy.getEndTime()) ||
                                        DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) >= DateUtil.timeToSecond(dy1.getEndTime())) {
                                    return new ResponseJson(false, "所选时间不符合规则,请重新选择");
                                }
                            }
                            so.getList().get(range).setStartTime(stuInAndOutStartTime.getStartTime());
                        }
                    }
                } else if (range == 2) {
                    if (so.getList().size() == 1) {
                        if (DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) <= DateUtil.timeToSecond(sstList.get(1).getStartTime())) {
                            return new ResponseJson(false, "所选时间不符合规则,请重新选择");
                        }
                    } else if (so.getList().size() == 2) {
                        List<DayAndEndTime> list1 = so.getList().get(1).getList().stream().filter(f -> Objects.nonNull(f) && StrUtil.isNotEmpty(f.getEndTime())).collect(Collectors.toList());
                        if (list1.size() > 0) {
                            DayAndEndTime dy = list1.stream().max((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                            if (DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) <= DateUtil.timeToSecond(dy.getEndTime())) {
                                return new ResponseJson(false, "所选时间不符合规则,请重新选择");
                            }
                        }
                    } else if (so.getList().size() == 3) {
                        List<DayAndEndTime> list0 = so.getList().get(0).getList().stream().filter(f -> Objects.nonNull(f) && StrUtil.isNotEmpty(f.getEndTime())).collect(Collectors.toList());
                        List<DayAndEndTime> list1 = so.getList().get(1).getList().stream().filter(f -> Objects.nonNull(f) && StrUtil.isNotEmpty(f.getEndTime())).collect(Collectors.toList());
                        List<DayAndEndTime> list2 = so.getList().get(2).getList().stream().filter(f -> Objects.nonNull(f) && StrUtil.isNotEmpty(f.getEndTime())).collect(Collectors.toList());
                        if ((list0.size() == 0 && list1.size() == 0 && list2.size() == 0) ||
                                (list0.size() > 0 && list1.size() == 0 && list2.size() == 0)) {
                            if (DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) <= DateUtil.timeToSecond(so.getList().get(1).getStartTime())) {
                                return new ResponseJson(false, "所选时间不符合规则,请重新选择");
                            }
                        }
                        if ((list0.size() == 0 && list1.size() == 0 && list2.size() > 0) ||
                                (list0.size() > 0 && list1.size() == 0 && list2.size() > 0)) {
                            DayAndEndTime dy = list2.stream().min((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                            if (DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) <= DateUtil.timeToSecond(so.getList().get(1).getStartTime()) ||
                                    DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) >= DateUtil.timeToSecond(dy.getEndTime())) {
                                return new ResponseJson(false, "所选时间不符合规则,请重新选择");
                            }
                        }
                        if ((list0.size() == 0 && list1.size() > 0 && list2.size() > 0) ||
                                (list0.size() > 0 && list1.size() > 0 && list2.size() > 0)) {
                            DayAndEndTime dy1 = list2.stream().min((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                            DayAndEndTime dy = list1.stream().max((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                            if (DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) <= DateUtil.timeToSecond(dy.getEndTime()) ||
                                    DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) >= DateUtil.timeToSecond(dy1.getEndTime())) {
                                return new ResponseJson(false, "所选时间不符合规则,请重新选择");
                            }
                        }
                        if ((list0.size() == 0 && list1.size() > 0 && list2.size() == 0) ||
                                (list0.size() > 0 && list1.size() > 0 && list2.size() == 0)) {
                            DayAndEndTime dy = list1.stream().max((x, y) -> Integer.compare(DateUtil.timeToSecond(x.getEndTime()), DateUtil.timeToSecond(y.getEndTime()))).get();
                            if (DateUtil.timeToSecond(stuInAndOutStartTime.getStartTime()) <= DateUtil.timeToSecond(dy.getEndTime())) {
                                return new ResponseJson(false, "所选时间不符合规则,请重新选择");
                            }
                        }
                        so.getList().get(range).setStartTime(stuInAndOutStartTime.getStartTime());
                    }
                }
                stuInAndOutStartTimeFeign.updateStuInAndOutStartTime(stuInAndOutStartTime);
                stuInAndOutClassTimeFeign.updateStuInAndOutClassTime(so);
            }
        }
        return new ResponseJson();

    }

    public void updateStuInAndOutStartTimeForAll(StuInAndOutStartTime stuInAndOutStartTime) {
        stuInAndOutStartTimeFeign.updateStuInAndOutStartTimeForAll(stuInAndOutStartTime);
    }

    public void deleteStuInAndOutStartTime(String id) {
        StuInAndOutStartTime stuInAndOutStartTime = stuInAndOutStartTimeFeign.findStuInAndOutStartTimeById(id);
        StuInAndOutClassTime stuInAndOutClassTime = new StuInAndOutClassTime();
        stuInAndOutClassTime.setSchoolId(mySchoolId());
        List<StuInAndOutClassTime> list = stuInAndOutClassTimeFeign.findStuInAndOutClassTimeListByCondition(stuInAndOutClassTime);
        for (StuInAndOutClassTime s : list) {
            if (s.getList() != null && s.getList().size() > 0) {
                int a = 0;
                for (int i = 0; i < s.getList().size(); i++) {
                    if (s.getList().get(i) != null && s.getList().get(i).getStartTime() != null) {
                        if (s.getList().get(i).getStartTime().equals(stuInAndOutStartTime.getStartTime())) {
                            s.getList().get(i).setStartTime(null);
                            s.getList().get(i).setList(null);
                            s.getList().remove(i);
                            stuInAndOutClassTimeFeign.updateStuInAndOutClassTime(s);
                        } else {
                            a++;
                        }
                    }
                }
                if (a == 0 && s.getList().size() == 0) {
                    stuInAndOutClassTimeFeign.deleteStuInAndOutClassTime(s.getId());
                }
            }
        }
        stuInAndOutStartTimeFeign.deleteStuInAndOutStartTime(id);
    }

    public void deleteStuInAndOutStartTimeByCondition(StuInAndOutStartTime stuInAndOutStartTime) {
        stuInAndOutStartTimeFeign.deleteStuInAndOutStartTimeByCondition(stuInAndOutStartTime);
    }

    public Integer isWhatRange(StuInAndOutStartTime stuInAndOutStartTime) {
        StuInAndOutStartTime stuInAndOutStartTime1 = new StuInAndOutStartTime();
        stuInAndOutStartTime1.setSchoolId(mySchoolId());
        StuInAndOutStartTime stuInAndOutStartTime2 = stuInAndOutStartTimeFeign.findStuInAndOutStartTimeById(stuInAndOutStartTime.getId());
        List<StuInAndOutStartTime> list = stuInAndOutStartTimeFeign.findStuInAndOutStartTimeListByCondition(stuInAndOutStartTime1);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getStartTime().equals(stuInAndOutStartTime2.getStartTime())) {
                return i;
            }
        }
        return null;
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
