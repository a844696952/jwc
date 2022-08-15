package com.yice.edu.cn.common.pojo.dm.classCard;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 云班牌
 */
@Data
public class DmClassCard implements Serializable {

    private String id;//主键id
    private String schoolId;//学校id
    private String equipmentId;//设备id
    @Transient
    private String gradeId;//年级
    @NotNull(message="请选择班级")
    @Valid
    private String classId;//班级id

    @Transient
    @Excel(name = "年级")
    private String gradeName;//年级名称

    @Transient
    @Excel(name = "班级")
    private String classNumber;//班级号

    @Transient
    private String className;//班级id
    @Excel(name = "设备名")
    private String equipmentName;//设备名称
    private String newVersion;//最新版本
    private Integer installStatus;//安装状态 0：成功   1：失败
    private Integer downStatus;//下载状态 0：成功 1：失败
    private String apkUrl;//apk路径
    private String status;//通知状态  0：成功   1：失败
    private String version;//版本号
    private String position;//操作人
    private String remark;//备注
    private String createTime;//创建时间
    private String updateTime;//修改时间
    private Integer lockStatus;//锁屏状态 0：未锁屏，1：锁屏中
    @Transient
    private String[] rowData;//复选框数组 用户名
    private String[] cardIds;//复选框数组 用户名
    @Transient
    private String keyWord;//模糊查询关键字
    private String teacherId;//删除标识 0：未删除 1：删除
    private String teacherName; //管理员名称
    private String motto;//校训
    private String schoolBadge; //校徽
    @Excel(name = "用户名")
    @NotNull(message="用户名不能为空")
    @Valid
    private String userName;//用户名
    @Excel(name = "密码")
    @NotNull(message="密码不能为空")
    @Valid
    private String password;//密码
    //分页排序等
    @Transient
    private Pager pager;
    @Transient
    private String times;
    private String studentId;//学生Id
    private String classAlias;//班牌别名
    private String schoolYearId; //学年ID

    @Transient
    private String name;
    private String parentId;//构建树的父节点id
    @Transient
    private DmTimedTask dmTimedTaskForm;
    private List<DmClassCard>  children;
    @Transient
    private List<Object> devices;//复选框数组




}
