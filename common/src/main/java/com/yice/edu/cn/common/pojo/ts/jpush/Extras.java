package com.yice.edu.cn.common.pojo.ts.jpush;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 推送的额外字段,可继承使用
 * 须得和app定义好extras中各个字段的用途
 * 类型支持，Number String Boolean
 * type,0-学校广播,给学校发送推送用的
 * 1-系统广播,通知app诸如服务器维护等信息
 * 2-系统广播,作业通知
 * 3-系统广播,班牌开机
 * 4-系统广播,班牌关机
 * 5-系统广播,班牌重启
 * 6-系统广播,班牌定时任务
 * 7-OA办公,审批任务
 * 8-OA办公,审批结果
 * 9-OA办公,收到抄送
 * 10-XW发文，审批通过
 * 11-TAP工资，教师工资通知
 * 12-BMP学生评价，老师对学生评价之后通知
 * 13-BMP教师评教，发起教师评教通知
 * 14-TAP学生评价教师之后，对教师发起通知
 * 15-TAP发起对学生评价的通知
 * 16-TAP发起校园通知
 * 17-BMP发起校园通知
 * 18-系统广播,通知班牌展示家长消息
 * 19-XW收文，审批通过
 * 20-发送屏保通知
 * 21-帮助.net推送ip给安卓
 * 22访客推送
 * 23-学生出入校通知
 * 25-学生到校通知
 * 26-系统广播,班牌批量升级更新
 * 27-职工考勤设备打卡通知
 * 28 云班牌锁屏
 * 29 云班牌解锁
 * 30 党建活动通知
 * 31-校务党建公文审批推送
 * 32TAP校务中党建管理发送学习资源给教师，同时推送通知教师接收到新的学习资源
 * 33-职工考勤管理员接收上班未到通知
 */
@Data
public class Extras {
    public static final Integer SCHOOL_BROADCAST = 0;
    public static final Integer SYSTEM_BROADCAST = 1;
    public static final Integer SYSTEM_BROADCAST_HOMEWORK = 2;
    public static final Integer SYSTEM_EQUIPMENT_STARTBOOT = 3;
    public static final Integer SYSTEM_EQUIPMENT_SHUTDOWN = 4;
    public static final Integer SYSTEM_EQUIPMENT_REBOOT = 5;
    public static final Integer SYSTEM_EQUIPMENT_TASK = 6;
    public static final Integer OA_APPROVE_TASK = 7;
    public static final Integer OA_APPROVE_RESULT = 8;
    public static final Integer OA_RECEIVE_COPY = 9;
    public static final Integer XW_WRITING_TYPE = 10;
    public static final Integer TAP_TEACHER_WAGE = 11;
    public static final Integer BMP_STUDENT_EVALUATE = 12;
    public static final Integer BMP_TEACHER_SURVEY = 13;
    public static final Integer TAP_SURVEY_TO_TEACHER = 14;
    public static final Integer TAP_EVALUATE_TO_TEACHER = 15;
    public static final Integer TAP_SCHOOL_NOTIFY = 16;
    public static final Integer BMP_SCHOOL_NOTIFY = 17;
    public static final Integer SYSTEM_BROADCAST_PARENTMSG = 18;
    public static final Integer XW_DOC_TYPE = 19;
    public static final Integer DM_SCREEN_SAVER = 20;
    public static final Integer EWB_PUSH_IP = 21;
    public static final Integer VISITOR_NOTICE = 22;
    public static final Integer XW_STUDENT_IN_OUT_SCHOOL = 23;
    public static final Integer XW_STUDENT_ARRIVE_SCHOOL = 25;
    public static final Integer SYSTEM_VERSION_TASK = 26;
    public static final Integer XW_WORKER_CLOCK_IN_OUT = 27;
    public static final Integer DM_SCREEN_LOCK = 28;
    public static final Integer DM_SCREEN_UNLOCK = 29;
    public static final Integer XW_DJ_ACTIVITY=30; //党建活动通知
    public static final Integer XW_DJ_DOC_TYPE=31;//党建公文通知
    public static final Integer XW_DJ_STUDY_TASK = 32;//党建学习
    public static final Integer XW_WORKER_MANAGE_CLOCK_IN_PUSH = 33;//职工考勤管理员接收上班未到通知
    public static final Integer  DM_KQ_DK_TAP=40;//教师端考勤打卡推送
    public static final Integer DM_KQ_DK_BMP=41;//家长端考勤打卡推送
    public static final Integer ELECTIVE_COURSE_CLOSE=70;//选修课关闭
    public static final Integer XW_DORM_PERSON_BMP = 50;//住宿安排家长端推送
    public static final Integer XW_DORM_PERSON_TAP = 51;//住宿安排教师端推送
    public static final Integer TAP_CLOUDSUBCOURSE=42;//教师端云课堂推送
    public static final Integer DM_TAP_ACTIVITY=80;//教师端活动推送
    public static final Integer DM_BMP_ACTIVITY=81;//家长端活动推送
    public static final int DM_ECC_ACTIVITY=82;//班牌端活动刷新
    public static final int DM_ECC_MERIT_STUDENT=83;//班牌端三好学生
    public static final int DM_ECC_FAMOUS_TEACHER=84;//班牌端名师风采
    public static final int DM_ECC_MEETING=85;//班牌管理班会
    public static final int DY_ECC_InspectRecord=86;//德育扣分刷新
    public static final Integer DY_RATE_BMP = 87;//德育评比通知



    //云班牌消息
    public static final Integer DM_MSG = 34;
    public static final Integer BMP_HONOUR_ROLL = 35;
    /**
     * 云班牌模式消息
     */
    public static final Integer DM_MODE_MSG=36;

    /**
     *  光荣榜更新
     *
     *  因注解原因，只能用int不能使用Integer
     */
    public static final int DM_PHOTO_MSG=37;
    /**
     *  流动红旗更新
     */
    public static final int DM_REDBANNER_MSG=38;
    /**
     *  相册更新
     */
    public static final int DM_CLASSPHOTO_MSG=39;
    /**
     *  倒计时更新
     */
    public static final int DM_COUNTDOWN_MSG=40;
    /**
     *  失物招领更新
     */
    public static final int DM_LOSTANDFOUND_MSG=41;
    /**
     *  校园公告更新
     *
     *
     *  因注解原因，只能用int不能使用Integer
     */
    public static final int DM_CAMPUSANNOUNCEMENT_MSG=42;
    /**
     *试卷分享
     */
    public static final Integer PAPER_SHARE=43;
    /**
     *学生请假
     */
    public static final Integer STU_LEAVE=44;
    /**
     *通知人员
     */
    public static final Integer NOTIFY_PERSON=45;
    /**
     *教师宿舍申请
     */
    public static final Integer DORM_APPLICANT_TAP=47;
    /**
     *家长宿舍申请
     */
    public static final Integer DORM_APPLICANT_BMP=48;
    /**
     * 德育检查通知
     */
    public static final Integer MES_INSPECT_RECORD=46;
    /**
     *YC陌生人推送人员
     */
    public static final Integer NOTIFY_STRANGERS=49;
    /**
     *YC黑名单推送人员
     */
    public static final Integer NOTIFY_BLACKLIST=50;

    @NotNull
    private Integer type;
    private String id;
    private String referenceId;
    private String json;
    private String sendDate;

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private Extras extras;

        public Builder() {
            this.extras = new Extras();
        }

        public Builder setType(Integer type) {
            this.extras.type = type;
            return this;
        }

        public Builder setId(String id) {
            this.extras.id = id;
            return this;
        }

        public Builder setReferenceId(String referenceId){
            this.extras.referenceId=referenceId;
            return this;
        }

        public Builder setSendDate(String sendDate){
            this.extras.sendDate=sendDate;
            return this;
        }


        //还有其他字段这里添加set方法
        public Builder setJSON(String json) {
            this.extras.json = json;
            return this;
        }

        public Extras build() {
            return this.extras;
        }

    }

}
