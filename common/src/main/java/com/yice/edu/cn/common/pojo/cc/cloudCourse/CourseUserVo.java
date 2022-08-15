package com.yice.edu.cn.common.pojo.cc.cloudCourse;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of ={"userType","userId"})
public class  CourseUserVo {
    private Long agoraUID;
    private String name ;
    private String imgUrl;
    private Integer userType;
    private String userId;
    //1主讲人，2互动人，3听课人 4录播人 5白板 //默认为听课人
    private Integer role;
    //默认false
    private boolean videoEnabled;
    //当前登录人是否为主讲//需要作为参数
    private boolean isowner;
    //听课列表用 听课列表第一位是否为自己//默认值false;
    private boolean isself;
    //设备类型(1pc 2终端)
    private Integer deviceType;

    public CourseUserVo(int userType, String userId) {
        this.userType = userType;
        this.userId = userId;
    }

    public CourseUserVo() {
    }
}
