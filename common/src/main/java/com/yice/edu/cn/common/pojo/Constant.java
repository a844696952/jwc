package com.yice.edu.cn.common.pojo;


import java.util.Arrays;
import java.util.List;

public interface Constant {
    String RES_PRE = "https://res.ycjdedu.com";
    String JWT_SECRET = "7786df7fc3a34e26a61c034d5ec8245d";
    String DATE_FORMATTER = "yyyy-MM-dd HH:mm:ss";
    String DATE_FORMATTER_DAY = "yyyy-MM-dd";
    String TEACHER = "teacher";
    String ADMIN = "admin";
    String DEFAULT_PWD = "66666666";//登录的默认密码
    String STUDENT_DEFAULT_PWD="66666666";//学生默认登录密码
    String CLOUD_MARKET_KEY="40a7ed99-71ce-4151-be2e-81aac109b255";

    //默认头像
    interface AVATAR {
        String BOY = "/headProfile/boy.png";
        String GIRL = "/headProfile/girl.png";
        String MAN = "/headProfile/man.png";
        String WOMEN = "/headProfile/women.png";
    }

    //第三方服务的常量
    interface THIRDPARTY_SERVICE {
        String WIRIS_SHOW_IMAGE = "http://218.6.69.201:10878/pluginwiris_engine/app/showimage";
    }

    /**
     *德育微信小程序的相关参数
     * */
    interface DYWECHAT{
        /**
         * 教师端的参数
         * */
        String DY_TEACHER_APPID = "wxe76a72066191383f";
        String DY_TEACHER_APPSERECT = "6f183bd1ccb5ef24f40b32ec59e0f775";
        String DY_TEACHER_MESSAGE_TEM_ID = "n_M34Mgm2oPBGHwj6l62afwGCdTu1z_HAPkd8G-36cc";
        String DY_TEACHER_REDIS_KEY = "dy_teacher_access_token";

        /**
         * 家长端的参数
         * */
        String DY_PARENTS_APPID = "wx03c5f1aeb95c0529";
        String DY_PARENTS_APPSERECT = "79872267ec431291742f90b7a3822b48";
        String DY_PARENTS_MESSAGE_TEM_ID = "KsMeXFVhjj3V2n2NfTHT2uNZhp3dOn-9iDIiqnP1CsM";
        String DY_PARENTS_REDIS_KEY = "dy_parents_access_token";
    }

    /**
     * 数据字典
     * 类型常量
     * 获取对应类型
     */
    interface DD_TYPE {
        /**
         * 国籍
         */
        String NATIONALITY = "1";
        /**
         * 性别
         */
        String SEX = "2";
        /**
         * 政治面貌
         */
        String POLITICAL_FACE = "3";
        /**
         * 年级
         */
        String GRADE = "4";
        /**
         * 最高学历
         */
        String HIGHEST_EDU = "5";
        /**
         * 教师状态
         */
        String TEACHER_STATUS = "6";
        /**
         * 学生状态
         */
        String STUDENT_STATUS = "7";
        /**
         * 证件类型
         */
        String DOCUMENT_TYPE = "8";
        /**
         * 教师职务
         */
        String TEACHER_POST = "9";
        /**
         * 学校类型
         */
        String SCHOOL_TYPE = "15";
        /**
         * 学校状态
         */
        String SCHOOL_STATUS = "13";
        /**
         * 科目
         */
        String SUBJECT = "18";
        /**
         * 婚姻状况
         */
        String MARITALSTATUS = "22";

        /**
         * 考试类型
         */
        String EXAMINATIONTYPE = "23";

        /**
         * 资产报修状态
         */
        String ASSETSTATUS = "24";

        /**
         * 档案情况
         */
        String ARCHIVALSITUATION = "25";

        /**
         * 党员类型
         */
        String PARTYTYPE = "26";
        /**
         * 值班日期
         */
        String ATTENTDANTTYPE = "38";

    }

    /**
     * 数据字典
     * 年级类型
     */
    interface DD_LEVEL {
        /**
         * 小学
         */
        String JUNIOR = "120";
        /**
         * 初中
         */
        String MIDDLE = "121";
        /**
         * 高中
         */
        String HIGH = "122";
    }


    String TOKEN = "token";//登录的请求令牌名称
    int HAVEN_LOGIN = 909;//未登录
    int NO_PERMISSION = 904;//没有权限
    int LOGIN_INVALID = 905;//登录失效
    int NEED_VALID=808;//前端需要做验证码校验

    interface Redis {
        String SCHOOL_YEAR = "schoolYear";

        String YED_ADMIN_CACHE = "adminLogin";
        String YED_ADMIN_ID_HEADER = "admin_id";
        String YED_ADMIN_PERMS = "adminPerms";//admin的权限存放cachename
        String YED_ADMIN_KEY_SUFFIX = "_PERMS";//admin的权限存放key年后缀
        int YED_ADMIN_TIMEOUT = 3600;//admin的登录缓存超时时间
        String EWB_QR_CODE_INFO = "qrcodeInfo";
        String DM_COURSE_INFO = "schoolCourse";//学校课程表
        String DM_HOLIDAY_INFO="holidayInfo";//日历
        int DM_COURSE_INFO_TIMEOUT = 3;//课表缓存时间

        int DM_HOLIDAY_DAY=30;//节假日

        String OSP_TEACHER_CACHE = "teacherLogin";
        String API_CACHE = "apiLogin";
        String OSP_TEACHER_ID_HEADER = "teacher_id";
        String OSP_LOGIN_VALID="ospLoginValid";
        String OSP_TEACHER_PERMS = "teacherPerms";//teacher的权限存放cachename
        String OSP_TEACHER_KEY_SUFFIX = "_PERMS";//teacher的权限存放key年后缀
        int OSP_TEACHER_TIMEOUT = 3600;//admin的登录缓存超时时间
        String OSP_CHAPTER_MENU = "ChapterMenu"; //章节菜单
        String OSP_CHAPTER_MENU_CANCAT = "ChapterMenuCANCAT"; //章节菜单拼接展示
        String OSP_STUDY_MENU = "StudyMenu"; //章节菜单

        String TAP_TEACHER_CACHE = "appTeacherLogin:";
        String TAP_TEACHER_ID_HEADER = "appTeacher_id";
        String TAP_TEACHER_IDENTIFYING_CODE = "tapIdentifyingCode:";//验证码
        String TAP_TEACHER_LOGIN_ERROR = "tapTeacherLoginError:";//登录错误key前缀
        String YED_ADMIN_IDENTIFYING_CODE = "yedAdminIdentifyingCode";//管理员验证通过
        int TAP_TEACHER_LOGIN_ERROR_TIME = 5;//登录错误超过5次时间间隔（分钟）
        int TAP_TEACHER_IDENTIFYING_CODE_TIME = 180;//验证码超时时间（秒）
        int YED_ADMIN_IDENTIFYING_CODE_TIME = 180;//验证码超时时间（秒）
        int TAP_TEACHER__TIMEOUT = 3;//登录3天不操作过期
        String TAP_TEACHER_PERMS = "appTeacherPerms";//teacher登录app的权限存放cachename
        String TAP_TEACHER_KEY_SUFFIX = "_PERMS";//teacher的权限存放key年后缀


        String H5_GETIMAGE_CODE = "h5GetImageCode";
        int H5_CODE_TIMEOUT = 5;

        String BMP_PARENT_CACHE = "appParentLogin";
        String BMP_PARENT_ID_HEADER = "appParent_id";
        String BMP_PARENT_PERMS = "appParentPerms"; //parent登录app的权限存放cachename
        String BMP_PARENT_KEY_SUFFIX = "_PERMS"; //parent的权限存放key年后缀
        String BMP_VERIFICATION_CODE = "bmpVerificationCode";//验证码
        String BMP_PARENT_KEY_LOGIN_ERROR = "bmpParentLoginError";//登录错误标识
        int BMP_PARENT_LOGIN_ERROR_TIME = 5;//登录错误超过五次时间间隔
        int BMP_PARENT_TIMEOUT = 3;//家长端登录3天不操作过期
        String BMP_TOKEN_CACHE = "bmpParentToken";//保存token到缓存
        String BMP_TOKEN_SUFFIX = "-token";//保存token到缓存的key的后缀

        String ECC_TEACHER_CACHE = "eccTeacherLogin";
        String ECC_TEACHER_ID_HEADER = "eccDmClass_id";
        String ECC_TEACHER_PERMS = "appTeacherPerms";//teacher登录app的权限存放cachename
        String ECC_TEACHER_KEY_SUFFIX = "_PERMS";//teacher的权限存放key年后缀


        String ECC_LOGIN = "eccLogin";
        /**
         * 这个是最考试所用的缓存键，已经作废
         */
        @Deprecated
        String ECC_CHECKIN_SETTING = "eccCheckInSetting"; //考勤设置缓存名
        String ECC_CHECKIN_SETTING2 = "eccCheckInSetting:"; //2019-07-01考勤设置缓存名
        String ECC_CHECKIN_SPECIAL = "eccCheckInSpecial"; //考勤特殊日期缓存名
        String ECC_CHECKIN_RECORD = "eccCheckInRecord";//每个学生的最后一次考勤记录缓存名
        String ECC_CHECKIN_SPECIALDAY = "eccCheckInSpecialDay";
        String ECC_CHECKIN_PROTECTEDSTUDENT = "eccCheckInProtectedStudent";
        String ECC_CHECKIN_CLASS_STUDENTS = "ECC_CHECKIN_CLASS_STUDENTS";
        String ZC_QRCODE_ASSETSRESIDS = "ZC_QRCODE_ASSETSRESIDS";
        int ECC_EQUIPMENT_TIMEOUT = 36000;//admin的登录缓存超时时间

        String RPM_TEACHER_CACHE = "rpmTeacherLogin";
        String RPM_TEACHER_ID_HEADER = "teacher_id";
        int RPM_TEACHER_TIMEOUT = 10;
        String CC_USER_ID = "cc_id";
        String TEACHER_ATTENDANCE_SETTING = "teacherCheckInSetting";//考勤设置缓存名
        String TEACHER_ATTENDANCE_SPECIAL = "teacherCheckInSpecial"; //考勤特殊日期缓存名
        String TEACHER_ATTENDANCE_RECORD = "teacherCheckInRecord";//教师的最后一次考勤记录缓存名

        //考勤机状态（离线或者在线）
        String ATTENDANCE_MACHINE_LIST = "ATTENDANCE_MACHINE_LIST";
        String ATTENDANCE_MACHINE_STATUS = "ATTENDANCE_MACHINE_STATUS";
        String ATTENDANCE_MACHINE_STATUS_AREA = "ATTENDANCE.MACHINE.STATUS.AREA";


        String SCHOOL_VALID = "schoolValid";

        String EWB_TEACHER_CACHE = "ewbTeacherLogin";
        String EWB_TEACHER_ID_HEADER = "ewbTeacher_id";
        String MES_TEACHER_CACHE = "mesTeacherLogin";
        String MES_TEACHER_ID_HEADER = "mesTeacher_id";
        String EWB_TEACHER_IDENTIFYING_CODE = "ewbIdentifyingCode";//验证码
        String EWB_TEACHER_LOGIN_ERROR = "ewbTeacherLoginError";//登录错误key前缀
        String MES_TEACHER_LOGIN_ERROR = "mesTeacherLoginError";//登录错误key前缀
        int EWB_TEACHER_LOGIN_ERROR_TIME = 5;//登录错误超过5次时间间隔（分钟）
        int EWB_TEACHER_IDENTIFYING_CODE_TIME = 180;//验证码超时时间（秒）
        int EWB_TEACHER__TIMEOUT = 3;//登录3天不操作过期
        String EWB_TEACHER_PERMS = "appTeacherPerms";//teacher登录app的权限存放cachename
        String EWB_TEACHER_KEY_SUFFIX = "_PERMS";//teacher的权限存放key年后缀

        String DMSS_TEACHER_PERMS = "dmssTeacherPerms";//电子扳班牌H5 teacher的权限存放cachename

        int DMSS_TEACHER_TIMEOUT = 3600;//admin的登录缓存超时时间


        String EWB_STUDENT_CACHE = "ewbStudentLogin";
        String EWB_STUDENT_ID_HEADER = "ewbStudent_id";
        String MES_STUDENT_CACHE = "mesStudentLogin";
        String MES_STUDENT_ID_HEADER = "mesStudent_id";
        String EWB_STUDENT_IDENTIFYING_CODE = "ewbStudentIdentifyingCode";//验证码
        String EWB_STUDENT_LOGIN_ERROR = "ewbStudentLoginError";//登录错误key前缀
        String MES_STUDENT_LOGIN_ERROR = "mesStudentLoginError";//登录错误key前缀
        int EWB_STUDENT_LOGIN_ERROR_TIME = 5;//登录错误超过5次时间间隔（分钟）
        int MES_STUDENT_LOGIN_ERROR_TIME = 5;//登录错误超过5次时间间隔（分钟）
        int EWB_STUDENT_IDENTIFYING_CODE_TIME = 180;//验证码超时时间（秒）
        int EWB_STUDENT__TIMEOUT = 3;//登录3天不操作过期

        String PAD_STUDENT_ACCOUNT = "padStudentAccount";//设备管理-电子白板-学生账号
        String PAD_STUDENT_ACCOUNT_KEY = "pad_student_account";//设备管理-电子白板-学生账号key

        String CC_PER_STATUS = "ccPerStatus";
        String CC_SUBSCRIBE = "ccSubScribe"; //云课堂订阅地址和用户关系
        String CC_COURSE_ROOM = "ccCourseRoom";//云课堂房间旁听人数缓存
        String CC_USER_STATUS = "ccUserStatus";//云课堂房间人员状态缓存
        String CC_PAD_UID = "ccPadUID";//云课堂房间白板UID缓存
        String CC_TEMP_FILE = "ccTempFile";//云课堂房间临时文件缓存
        String CC_RECORDING = "recordingHandle";//录播句柄缓存
        int CC_COURSE_TIME = 24;//单位时，用于计算旁听人数

        String HOLIDAY = "holiday";//节假日数据缓存
        int HOLIDAY_TIMEOUT = 180;//天,节假日数据缓存时间

        String STUINTIMERECORDS = "stuInTimeRecords";//學生考勤即時统计
        int STUINTIMERECORDS_TIMEOUT = 20;//缓存时间  小时

        String EMS_STUDENT_ID_HEADER="emsStudent_id";
        String EMS_STUDENT_CACHE="ems_student_cache";
        String EMS_TEACHER_ID_HEADER="emsTeacher_id";
        String EMS_TEACHER_CACHE="ems_teacher_cache";

        String PREPARE_LESSONS_MENU = "prepareLessonsCloseMenu";//备课中用户最后一次停留的菜单信息


        String PCD_ACCOUNT_ID_HEADER="pcdAccount_id";
        String PCD_ACCOUNT_CACHE = "pcdAccountLogin";
        int PCD_ACCOUNT_TIMEOUT = 3600;//pcd的登录缓存超时时间

        /*stuInAndOut*/
        String STU_IN_AND_OUT_CLASS_NEED_ATTEND_CACHE = "stuInAndOutClassNeedAttendCache";//班级需要上课的时间段
        int STU_IN_AND_OUT_CLASS_NEED_ATTEND_TIMEOUT = 1;
        /*stuInAndOut*/


        /*……ycVeriface…………*/
        String YC_VERIFACE_ACCOUNT = "ycVerifaceAccount";//亿策人脸识别账号列表
        int YC_VERIFACE_ACCOUNT_TIMEOUT = 30;
        String YC_VERIFACE_BACK_ONLINE_SCHOOL = "ycVerifaceBackOnlineSchool";//亿策人脸识别账号列表
        int YC_VERIFACE_BACK_ONLINE_SCHOOL_TIMEOUT = 1;
        String YC_VERIFACE_BACK_ONLINE_DATAS ="ycVerifaceBackOnlineDatas";
        int YC_VERIFACE_BACK_ONLINE_DATAS_TIMEOUT = 1;
        String YC_VERIFACE_GENERATE_ACCOUNT = "ycVerifaceGenerateAccount";//亿策人脸识别生成账号临时存放列表
        int YC_VERIFACE_GENERATE_ACCOUNT_TIMEOUT = 1;
        String YC_VERIFACE_DOOR_DEVICE_PERSON_CHANGE_LIST = "ycVerifaceDoorDevicePersonChangeList";//亿策人脸识别门禁人员变化特征值数据包
        int YC_VERIFACE_DOOR_DEVICE_PERSON_CHANGE_LIST_TIMEOUT = 1;
        String YC_VERIFACE_ONLINE_DOOR_DEVICE_LIST = "ycVerifaceOnlineDoorDeviceList";//亿策人脸识别门禁在线列表
        String YC_VERIFACE_ONLINE_DOOR_DEVICE_RESET_FAILED_NUM = "ycVerifaceOnlineDoorDeviceResetFailedNum";//亿策人脸识别生成账号临时存放列表
        int YC_VERIFACE_ONLINE_DOOR_DEVICE_RESET_FAILED_NUM_TIMEOUT = 1;

        /*……ycVeriface…………*/
    }


    /**
     * 状态管理
     * 保存各种状态信息
     */
    interface STATUS {
        /**
         * 教师在职
         */
        String TEACHER_ON_LINE = "40";
        /**
         * 教师离职
         */
        String TEACHER_QUIT_LINE = "41";
        /**
         * 学校管理员
         */
        String TEACHER_ADMIN = "99";
    }

    interface Upload {
        List<String> OFFICE_FILE_EXT = Arrays.asList(".xls", ".xlsx", ".ppt", ".pptx", ".doc", ".docx", ".pdf", ".txt", ".html", ".zip", ".rar", ".gif", ".jpg", ".jpeg", ".png", ".psd", ".swf", ".bmp", ".mp3", ".mp4", ".wav", ".avi");
        String UPLOAD_AVATAR = "upload/avatar";
        String UPLOAD_COVER = "upload/cover";
        String UPLOAD_JOURNAL_IMAGE = "upload/journal/image";
        String UPLOAD_EDITOR_IMAGE = "upload/editor/image";
        String UPLOAD_HANDOUT = "upload/handout";
        String UPLOAD_PRACTICE = "upload/practice";
        String UPLOAD_BMPHOMEWORK = "upload/bmpHomework";
        String UPLOAD_TAPHOMEWORK = "upload/tapHomework";
        String UPLOAD_EWBHOMEWORK = "upload/ewbHomework";
        String UPLOAD_PREPLESSON = "upload/prepLessonResource";
        String UPLOAD_OA_ATTACHMENT = "upload/oa/attachment";
        String UPLOAD_MATERIAL = "upload/material";
        String UPLOAD_NETWORK = "upload/network";
        String UPLOAD_APP_ICON = "app/icon";
        String UPLOAD_DOC = "upload/doc";
        String UPLOAD_WRITING = "upload/writing";
        String UPLOAD_REVIEW_IMG = "upload/review/img";
        String ZC_QRCODE = "upload/zc/qrcode";//资产管理二维码
        String ZC_BAOXIU = "upload/zc/baoxiu";//资产报修图片
        String ZC_Contract = "upload/zc/contract";//资产合同文件
        String ZC_REPAIRNEW = "upload/zc/repairNew";//报修附件
        String ZC_ASSETS_STOCK_DETAIL = "upload/zc/assetsStockDetail";//资产库存明细附件
        String UPLOAD_APP_VERSION = "upload/appVersion";
        String UPLOAD_PAPER_IMG = "upload/paper/img";
        String UPLOAD_COMMON_IMG = "upload/common/img";
        String UPLOAD_STUDENT_QUESTION = "upload/student/question";
        String UPLOAD_RESOURCE_CENTER = "upload/resourceCenter";//资源中心
        String VISITOR_QRCODE = "upload/visitor/qrcode";//访客二维码
        String VISITOR_IMAGE = "upload/visitorImg";//访客头像
        String UPLOAD_CLASS_IMG = "upload/classImg";
        String UPLOAD_CLASS_VIDEO = "upload/classVideo";
        String UPLOAD_CLOUDCOURSE_RESOURSE = "upload/cloudcourse";
        String OA_OFFICE_FILE = "oa/office/file";
        String UPLOAD_DJ_PARTYMEMBERPHOTO = "upload/djActivity/partyMemberPhoto";
        String UPLOAD_DJ_STUDY_RESOURCE = "upload/djActivity/studyResource";
        String UPLOAD_APK = "upload/apk";
        String UPLOAD_SMART_PEN = "upload/smartPen";
        String UPLOAD_CMS = "upload/cms";
        String TKPZ_ASSETS_STOCK_DETAIL = "upload/tkpz/tkpzDetail";//题库资源凭证
        String CL_CUSTOM_MATERIAL = "upload/clCustomMaterial";//自定义材料上传路径
        String ZY_CAPTURED_IMAGE = "upload/zYcapturedImage";//中移设备抓拍图片
        String YC_CAPTURED_IMAGE = "upload/yCcapturedImage";//中移设备抓拍图片
        String WB_LATTICE_PAGER = "upload/wb/latticePager";//点阵试卷PDF
        String WB_LATTICE_PAGER_PAGES = "upload/wb/latticePager/pages";//点阵试卷PDF 转图片
        String XW_HOUSE_APPLICAN_FILES = "upload/houseApplicanFiles"; //宿舍申请 文件
        String UPLOAD_DY_FILE = "upload/dy/file";//德育资源文件目录
        String UPLOAD_LOC_SCHOOL_CONFIG = "upload/loc/schoolConfig";
        String UPLOAD_PCD_DOC="upload/pcdDoc";//省市区公文上传
        String UPLOAD_SCHOOL_ACTIVE = "upload/schoolActive";
    }

    /**
     * 作业相关常量
     */
    interface HOMEWORK {
        int REMARK_HAS = 1;//已点评
        int REMARK_NOT = 2;//未点评
        int SUBMIT_HAS = 1;//已提交
        int SUBMIT_NOT = 2;//未提交
        int SUBMIT_OUT_TIME = 3;//逾期
        int REPLY_WAY_PHOTO = 1;//拍照
        int REPLY_WAY_COMFIRM = 2;//确认提交
        int PUBLISH_ON = 1;//立即发布
        int PUBLISH_NOT = 2;//暂不发布
        int HOMEWORK_TYPE_ONLINE = 1;//线上作业
        int HOMEWORK_TYPE_OFFLINE = 2;//线下作业
    }

    /**
     * 题目相关常量
     */
    interface TOPICS {
        int FROM_HOMEWORK = 1;//来源作业
        int CORRECT = 1;//正确
        int WRONG = 2;//错误
        int NONE = 3;//未作答 空提交
    }

    /**
     * 题目来源
     */
    interface TOPIC_SOURCE {
        String YCJD = "100000";//亿策题库
        String XBTK = "100001";//校本题库
        String WDTK = "100002";//亿策题库
        String TWENTYONESHIJI = "100021";//21世纪
    }

    /**
     * 题目归属
     */
    interface TOPIC_CREATEBY {
        String YCJD = "1";//亿策平台题库
        String SCHOOLBASE = "2";//校本题库
        String PERSONAL = "3";//校本题库
    }

    /**
     * 逻辑删除标志
     */
    interface DELSIGN {
        String NORMAL = "1";//正常
        String DEL = "2";//删除
    }

    /**
     * mcs消息消费服务中用到的渠道
     */
    interface MCS_CHANEL {
        String AUTH_IDENTIFY = "authIdentify";//身份验证通道
        String JPUSH_SEND_PUSH = "jpush_send_push";//极光推送的发送推送的通道
        String ZC_SENG_MSG = "zc_msg";//资产消息
        String SMS_SEND_MSG = "sms_msg";//短信消息
    }

    /**
     * 短信发送 短信签名
     */
    interface MCS_SIGN_NAME {
        String YCJD = "亿策金点";
        String YCFKXT = "亿策访客系统";
    }

    /**
     * 短信发送 模板标识
     */
    interface MCS_TEMPLATE {
        String SMS_VERIFICATION = "SMS_67290732";//短信-验证码
        String SMS_VERIFICATION_ZC = "SMS_153716413";//短信-资产
        String SMS_VISITOR_PASS = "SMS_163625143";//短信-亿策访客系统-通过
        String SMS_VISITOR_REFUSE = "SMS_163620286";//短信-亿策访客系统-拒绝
        String SMS_OA_URGE = "SMS_164508593";//短信-OA流程催办模板
    }


    interface OA {
        int WAIT_TO_APPROVE = 0;//流程待审批
        int SUCCESS_COMPLETE = 1;//流程审批成功结束
        int FAIL_COMPLETE = 2;//流程审批失败结束
        int CANCEL_COMPLETE = 3;//流程已撤销
        String BEGIN_TIME = "beginTime";//表单里的开始时间
        String END_TIME = "endTime";//表单里的结束时间
        String DURATION = "duration";//请假时长,可能是天可能是分钟,根据字段的placehoder来定
        String ROOM = "room";//表单里的空间字段,如实验室什么的
        String APPLY_TIME = "applyTime";
        String TIME_INTERVAL = "timeInterval";
        String CLASS_HOUR = "classHour";
        String DATA_TYPE_STRING = "String";
        String DATA_TYPE_INTEGER = "Integer";
        String DATA_TYPE_DOUBLE = "Double";
        String DATA_TYPE_ARRAY = "Array";
        String FORM_SELECT_SUFFIX = "Name";//表单空间里select空间冗余的字段名称后缀
        int SEND_SMS_MAX_COUNT = 10;//每天单个流程短信通知最大次数
        int MAX_APPROVER = 10; //流程审批人最大不能超过人数
        String DATA_TYPE_NUMBER = "Number";
    }

    /**
     * APP 类型
     */
    interface APP_TYPE {
        int TAP_TYPE = 1;//教师APP
        int BMP_TYPE = 2;//学生/家长APP
    }

    /**
     * 用于存放网盘文件的类型，用于区分文件夹所属的资源
     * 0：个人资源，1：校本资源，2：我收藏的，3：我分享的
     */
    interface RESOUCES_TYPE {
        //        0：个人资源，1：校本资源，2：我收藏的，3：我分享的
        int PERSION_TYPE = 0;//0：个人资源
        int SCHOOL_TYPE = 1;//1：校本资源
        int COLLECTION_TYPE = 2;//2：我收藏的
        int SHARE_TYPE = 3;//3：我分享的
    }


    /**
     * 性别常量
     */
    interface SEX_TYPE {
        String MAN = "4";//男
        String WOMAN = "5";//女
    }

    /**
     * IM注册状态常量
     */
    interface IM_TYPE {
        String REGISTER_STATUS_HAS = "1";//im已注册
        String REGISTER_STATUS_NOT = "0";//im未注册
    }

    /**
     * 国籍常量
     */
    interface NATIONALITY_TYPE {
        String CHINA = "1";//中国
        String CHINA_HONGKONG_MACAO_TAIWAN = "2";//中国(港澳台)
        String FOREIGN_COUNTRY = "3";//国外
    }

    /**
     * 政治面貌常量
     */
    interface POLITICS_FACE_TYPE {
        String INNOCEENCE_TYPE = "6";//群众
        String LEAGUE_MEMBERS_TYPE = "7";//团员，共青团员
        String COMMUNIST_TYPE = "8";//共产党员
    }

    /**
     * 学生状态常量
     */
    interface STUDENT_STATUS_TYPE {
        String STUDY_HAS = "50";//在读
        String REPAATER = "51";//留级
        String STUDY_SUSPENSION = "52";//休学
        String TRANSFER_TO_SCHOOL = "53";//转校
        String STUDY_OTHER = "54";//转校

    }

    /**
     * 学生当前状态常量
     */
    interface STUDENT_NOW_STATUS_TYPE {
        String IN_SCHOOL = "0"; //在校
        String LEAVE_IN = "1"; //请假(在校)
        String LEAVE_OUT = "2"; //请假(离校)
        String NOT_INT_SCHOOL = "3";//离校
    }

    /**
     * 学生住宿状态常量
     */
    interface STUDENT_BOARDER_TYPE {
        String BOARDER_HAS = "0"; //是
        String BOARDER_NOT = "1";  //否
    }

    /**
     * 新值班管理的签到情况常量
     */
    interface DUTY_SIGN_IN_CONDITION {
        String SIGN_IN_NOT = "0";//未签到
        String SIGN_IN_HAS = "1";//已签到
        String SIGN_IN_LATE = "2";//迟到
    }

    /**
     * 新值班管理签到时间判断
     */
    interface DUTY_SIGN_IN_TIME_TYPE {
        String SIGN_IN_TIME_IN = "1"; //签到
        String SIGN_IN_TIME_BEFORE = "2";//提前，无法签到
        String SIGN_IN_TIME_BEHIND = "3";//超出时间无法签到
    }

    /**
     * 新值班管理的签到类型常量
     */
    interface DUTY_SIGN_IN_TYPE {
        String CHECK_TYPE_CARD = "1";//打卡签到
        String CHECK_TYPE_PHOTO = "2";//拍照签到
    }

    /**
     * 学期学年查询的状态常量
     */
    interface SEMESTER_TIME_TYPE {
        String SEMESTER = "1"; //学期
        String SCHOOL_YEAR = "2"; //学年
    }

    /**
     * 中移对接头像录入状态常量
     */
    interface ZY_IMG_TYPE {
        String ZY_CHECK_STATUS_HAS = "0";  //成功录入
        String ZY_CHECK_STATUS_NOT = "1";  //未录入（失败录入）
    }

    /**
     * 屏保的状态
     * 1: "进行中",
     * 2: "未开始",
     * 3: "已停止",
     * 4: "草稿"
     */
    interface dmScreenSaver {
        /**
         * 进行中
         */
        Integer RUNNING = 1;
        /**
         * 未开始
         */
        Integer NOTSTART = 2;
        /**
         * 已停止
         */
        Integer STOP = 3;
        /**
         * 草稿
         */
        Integer DRAFT = 4;
    }

    /**
     * 教师职务
     */
    interface TEACHER_POST {
        /**
         * 校长
         */
        String PRINCIPAL = "70";
        /**
         * 教务主任
         */
        String DEAN = "71";
        /**
         * 总务主任
         */
        String GENERAL_MANAGER = "72";
        /**
         * 年段长
         */
        String GRADE_LEADER = "73";
        /**
         * 班主任
         */
        String CLASS_TEACHER = "74";
        /**
         * 任课老师
         */
        String ORDINARY_TEACHER = "75";
        /**
         * 心理辅导
         */
        String COUNSELING = "76";

    }

    interface PERSON_TYPE {
        Integer TEACHER = 1;//教师
        Integer STAFF = 2;//职工
    }

    interface JPUSH {
        String SOUND_1 = "sound.caf";
    }

    /**
     * 考试类型: 0线下考试,1网阅考试
     */
    interface ExamType {
        /**
         * 线下考试
         */
        int offLine_exam = 0;
        /**
         * 网阅考试
         */
        int onLine_exam = 1;

    }

    /**
     * 学生平板端密码与账号初始值
     */
    interface PadStudentAccount {
        int PAD_STUDENT_ACCOUNT_INTI_VALUE = 100000;//设备管理-电子白板-学生账号初始值
        int PAD_STUDENT_PASSWORD = 888888;//设备管理-电子白板-学生账号密码
    }


    /**
     * 云课堂
     */
    interface CCourse {
        String APP_PRE = "cc";//云课堂前缀
        String VIDEO_RESOURCES = "1";//录播视频资源
        int ACCOUNT_TYPE_INSIDE = 0;//校内账号
        int ACCOUNT_TYPE_OUTSIDE = 1;//校外账号
        int ACCOUNT_ONLINE = 1;//在线
        int ACCOUNT_OFFLINE = 2;//离线
        interface ROLE{
            int LECTURER = 1;//主讲人
            int INTERACTOR = 2;//互动人
            int LISTENER = 3;//听课人
            int RECORDER = 4;//录播人
            int PAD=5;//白板
        }
        interface DEVICE{
            int PC = 1;//PC
            int TE = 2;//终端
        }
        interface STATUS{
            int WIAT = 1;//待上课 2->上课中 3->已结束
            int ONGOING = 2;//终端
            int FINISH = 3;
        }
        interface SUBSCRIBE{
            String SINGLE_MSG = "singleMsg";//个人订阅
            String ROOM_MSG = "roomMsg";//房间订阅
            String HALL_MSG = "hallMsg";//大厅订阅
            String PAD_MSG = "padMsg";//白板订阅
        }
        interface AGORA{
        	String appId = "ac0a869ef30944b0b8cd976a10ec3b52";
        }
    }

    /**
     * 海康和中移动录入成功的status
     */
    interface TEACHER_IMAGE_IMPUT {
        int HK_STATUS = 1;
        int ZYD_STATUS = 2;
    }

    /**
     * 动态定时任务类型
     */
    interface DYNAMIC_CRON {
        String TASK_REFRESH = "0";//定时跟新动态任务列表
        String TASK_STUDENT_ARRIVE_SCHOOL = "1";//学生出入校定时教师推送
        String TASK_STUDENT_IN_OUT_SCHOOL = "2";//学生出入校定时家长推送
        String NEW_TASK_STUDENT_ARRIVE_SCHOOL = "6";//学生出入校定时教师推送
        String NEW_TASK_STUDENT_IN_OUT_SCHOOL = "7";//学生出入校定时家长推送
        String TASK_TEACHER_CLOCK_IN_MORNINGIN = "3";//教师上班打卡推送给考勤组管理员
        String TASK_TEACHER_CLOCK_IN_NOONIN = "4";//教师上班打卡推送给考勤组管理员
        String TASK_STUDENT_CLASS_END_TIME_CHECK_STATUS = "5";//学生出入校下课时间统计学生来校情况
    }
     /**
      * 亿策人脸识别人员注册表
      * */
     interface YC_VERIFACE_PERSON_ENTER {
         String PERSON_TYPE_BLACKPERSON = "0";//黑名单
         String PERSON_TYPE_TEACHER = "1";//教师
         String PERSON_TYPE_STUDENT = "2";//学生
         String PERSON_TYPE_STAFF = "3";//职工
         String PERSON_TYPE_BOARDER = "4";//寄宿生
         String PERSON_TYPE_VISITOR = "5";//访客
     }
    /**
     * 亿策人脸识别websoket通知设备配置变化代码
     * */
    interface YC_VERIFACE_TOLD_DEVICE_CHANGE_CODE {
        String PERSON_LIST_CLEAR = "-1";//-1清除所有
        String PERSON_LIST_RESET = "0";//0清除原有人员，更新至最新人员列表
        String PERSON_LIST_INSERT = "1";//1人员增加
        String PERSON_LIST_DELETE = "2";//2人员减少
        String PERSON_LIST_UPDATE = "3";//3人员更新
    }
    /**
     * 考勤原始数据
     */
    interface KQ_ORIGINAL_DATA {
        //人员类型userType
        String USER_TYPE_STRANGER = "-1";//陌生人
        String USER_TYPE_BLACKPERSON = "0";//黑名单
        String USER_TYPE_TEACHER = "1";//教师
        String USER_TYPE_DAY_STUDENT = "2";//走读生
        String USER_TYPE_STAFF = "3";//职工
        String USER_TYPE_BOARDER = "4";//寄宿生
        String USER_TYPE_VISITOR = "5";//访客
        String USER_TYPE_DM = "6";//云班牌学生
        //入离校状态derectionFlag
        String DERECTION_FLAG_IS_IN = "0";
        String DERECTION_FLAG_IS_OUT = "1";
        String DERECTION_FLAG_IS_NULL = "-1";
        //设备厂家deviceFactory
        String DEVICE_FACTORY_YC = "0";//亿策
        String DEVICE_FACTORY_ZY = "1";//中移动
        String DEVICE_FACTORY_HK = "2";//海康
        String DEVICE_FACTORY_USER_PHONE = "3";//普通用户手机
        String DEVICE_FACTORY_DM = "4";//云班牌
        //设备类型
        String DEVICE_TYPE_CAMERA = "1";
        String DEVICE_TYPE_DOOR = "2";
        //"passStatus出入校状态，1正常,2请假,3异常
        String PASS_STATUS_MORMAL = "1";//正常
        String PASS_STATUS_LEAVE = "2";//请假
        String PASS_STATUS_WRONG = "3";//异常
        //抓拍消息类型 1识别记录2陌生人
        String CAPTURE_MESSAGE_TYPE_MORMAL = "1";
        String CAPTURE_MESSAGE_TYPE_STRANGER = "2";

    }

    /**
     * 党建中学习资源的状态标识
     */
    interface STUDY_RESOURCE {
        //未发布、已发布、已关闭
        int STUDY_NO_ISSUE = 0;
        int STUDY_ISSUE = 1;
        int STUDY_CLOSE = 2;
    }

    /**
     * 党建中我的学习中的状态标识
     */
    interface MY_STUDY_TEACHER_RESOURCE {
        //未学习、已学习
        int TEACHER_NO_STUDY = 0;
        int TEACHER_STUDY = 1;
    }

    /**
     * 党建中是否是管理员标识
     **/
    interface IS_ADMIN {
        String IS_ADMIN = "1";
    }

    /**
     * 字典表中德育权限(ad_dd表中type_id=44)Permission
     **/
    interface DY_PERMISSION {
        Integer CLASS_MANAGEMENT= 444;//班级管理
        Integer DORMITORY_INSPECTION_ENTRY= 445;//宿舍检查(录入+历史+通知)
        Integer SCHOOL_CHECK_NOTICE= 446;//学校检查(通知)
        Integer SCHOOL_CHECK_ENTRY= 447;//学校检查（录入+历史+通知）
        Integer DORMITORY_INSPECTION_NOTICE= 448;//宿舍检查（通知）
        Integer DORMITORY_INSPECTION_HISTORY= 449;//宿舍检查（录入+历史)
        Integer SCHOOL_CHECK_HISTORY= 450;//学校检查（录入+历史)
    }

    /**
     * 党建中活动签到时间
     **/
    interface ACTIVITY_SIGNTIME {

        Integer SIGNTIME = 7200000;
    }

    /**
     * 党建中活动签到距离
     **/
    interface ACTIVITY_DISTANCE {

        double DISTANCE = 200.0;
    }

    /**
     * 党建中学习资源activityType，学习资源
     */
    interface DJ_STUDY_TYPE {
        String TYPE = "学习活动";
    }

    interface DATE_FORMAT {
        String FORMAT = "yyyy-MM-dd HH:mm:ss";
    }

    /**
     * 资产相关
     */
    interface ZC {
        //资产出库单号前缀
        String CK_PREFIX = "ZCCK";
    }


    /**
     * 资料是否完整
     */
    interface PARTY_MEMBER_INTACT {
        int COMPLETE = 1;//完整
        int NOCOMPLETE = 2;//不完整
    }

    /**
     * 学生小组类型
     */
    interface STUDENTGROUPTYPE {
        /**
         * 一般
         */
        String GROUP_NORMAL = "1";

        /**
         * 中等
         */
        String GROUP_MEDIUM = "2";

        /**
         * 良好
         */
        String GROUP_WELL = "3";

        /**
         * 优秀
         */
        String GROUP_EEXCEELLENT = "4";
    }

    /**
     * 云班牌倒计时管理展示状态
     */
    interface DM_COUNT_DOWN_MANAGE {
        int MANAGE_DISABLE = 0;//展示结束
        int MANAGE_VALID = 1;//正在展示
    }

    interface TOPIC_TYPE {
        /**
         * 个人题库
         */
        int PERSON_TOPIC = 1;
        /**
         * 校本题库
         */
        int SCHOOL_TOPIC = 2;

    }

    interface STOMP {
        String CLIENT_OSP = "osp";
        String CLIENT_CC = "cc";
        String CLIENT_CC_H5 = "cc_h5";
        String CLIENT_TAP = "tap";
        String CLIENT_BMP = "bmp";
        String CLIENT_ECC = "ecc";
        String CLIENT_YC_VERIFACE = "ycVeriface";

    }

    interface CLASS_SCHEDULE {
        /**
         * 学校默认节数
         */
        long CLASS_SCHEDULE_NUMBER = 7;
        /**
         * 学校默认午休标识位子
         */
        int CLASS_NOON_BREAK = 4;
    }

    /**
     * 文件服务
     */
    interface FILE_SERVICE {
        int TYPE_QINIU = 1;
    }

    /**
     * 默认往前查询天数
     */
    interface SCHOOL_DATE_CENTER {
        int DAY = 1;
        int WEEK = 7;
        int MONTH = 30;
        int HALFMONTH = 15;
        int YEAR = 12;
    }

    /**
     * 自定义材料权重上限
     */
    interface CL_CUSTOM_MATERIAL {
        Long WEIGHT = 10000000L;
    }

    /**
     *
     *
     */
    interface SCHOOL_RISE_RECORD {
        int NO_SET_TIME_RISE = 1;//1.未设置升学的开始时间和结束时间
        int NOT_BEGIN_RISE = 2;//2.未开始升学
        int HAS_RISE = 3;//3.已经升过学
        int ERROR_RISE = 4;//4.升学状态异常
        int BEGIN_RISE = 5;//5.正在升学
        int ERROR_MANY_YEARS_NO_RISE = 6;//6.多年未升学,请在升学日期开始升学
        int ERROR_RISE_LESS_NEXT_TERM = 7;//7.升班的时间不能小于下学期的开始时间
    }


    /**
     * 宿舍类型常量
     */
    interface DORM_TYPE {
        String DORM_MALE = "1";//男生宿舍
        String DORM_FEMALE = "2";//女生宿舍
    }

    /**
     * 宿舍类别常量
     */
    interface DORM_CATEGORY {
        String DORM_STUDENT = "1"; //学生宿舍
        String DORM_TEACHER = "2";  //老师宿舍
        String DORM_MASTER = "3";//宿管宿舍
        String DORM_OTHER = "4";
    }

    /**
     * 宿管员类型
     */
    interface DORM_STAFF_TYPE {
        String DORM_ADMIN = "0";//宿管员
        String DORM_TEACHER = "1";//宿管老师
    }


    /**
     * 删除标志
     */
    interface DELETE_STATUS {
        Integer EXIST = 1;  //未删除
        Integer NO_EXIST = 0;   //删除
    }

    /**
     * 教材所属类型
     */
    interface TEXTBOOK {
        Integer SCHOOL_TYPE = 1;    //学校
        Integer TEACHER_TYPE = 2;   //教师个人
    }

    /**
     * 章节所属的类型
     */
    interface SECTION {
        Integer SCHOOL_TYPE = 1;    //学校
        Integer TEACHER_TYPE = 2;   //教师个人
    }

    /**
     * 学校禁止使用
     * 2001-学校到期，2002-学校禁用，2003-升学异常，2004-正在升学
     */
    interface SCHOOL_CODE {
        int SCHOOL_DUE = 2001;
        int SCHOOL_DISABLED = 2002;
        int SCHOOL_ERROR = 2003;
        int SCHOOL_CAREERS = 2004;
        String SCHOOL_HIT = "无法使用该应用,请联系学校！";
    }

    /**
     * 选修课异常码
     *
     */
    interface ELECTIVE_ERR_CODE {
        int PEOPLE_NUM_FULL = 7001;//申请的选修课人数已满
        int COURSE_FULL = 7002;//申请的课程已经达到最大数量
        int TIME_REPEAT = 7003;//已申请该时间段的其他选修课,上课时间重复
        int COURSE_CLOSE = 7004;//课程已关闭
        int TIME_END = 7005;//报名时间已结束
        int SELECT_ERRO = 7006;//选课失败
        int NOT_SET = 7007;//未进行选修课设置
    }

    /**
     * 本地化部署学校生产配置
     */
    interface SCHOOL_CONFIG{
        String LOGGING_PATTERN_FILE="%d{ABSOLUTE} [%X{X-B3-TraceId}/%X{X-B3-SpanId}] %-5p [%t] %C{2} - %m%n";
        String MYSQL_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
        String LOGGING_LEVEL_CN_YCJD_LOCDAO="warn";
        String LOGGING_LEVEL_ORG_SPRINGFRAMEWORK_WEB ="warn";
        String MYSQL_PRE="jdbc:mysql://";
        String MSQL_SUFFIX="/loc?characterEncoding=UTF-8&useSSL=false&allowMultiQueries=true";
        String MONGODB_PRE="mongodb://";
        String MONGODB_SUFFIX="/loc";
    }


    /**
     * 区级平台
     */
    interface PCD_DATA{
        String PRIMARY="primary";
        String MIDDLE="middle";
        String HIGH="high";
        String YEAR="year";
        String TEACHERNUM="teacherNum";
        String STUDENTNUM="studentNum";
        String RESOURCEOFALLAREA="resourceOfAllArea";
        String RESOURCEOFSCHOOL="resourceOfSchool";
        String HOMEWORK="homework";
        String ONLINE="online";
        String UNDERLINE="underLine";
        String DURATION="duration";
        String RATE="rate";
        String SUBJECT = "subject";
        String XIAOXUE="小学";
        String CHUZHONG="初中";
        String GAOZHONG="高中";
    }
}
