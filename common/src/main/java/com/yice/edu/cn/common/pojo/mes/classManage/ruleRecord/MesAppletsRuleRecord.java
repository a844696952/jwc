package com.yice.edu.cn.common.pojo.mes.classManage.ruleRecord;

import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@ApiModel("德育小程序点评记录表")
public class MesAppletsRuleRecord extends CurSchoolYear {

    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("学生id")
    private String studentId;
    @ApiModelProperty("制度id")
    private String ruleId;
    @ApiModelProperty("制度名称")
    private String ruleName;
    @ApiModelProperty("分值")
    private Integer score;
    @ApiModelProperty("标签类型 0--待改进 1--表扬")
    private Integer tagType;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("班级id")
    private String classesId;
    @ApiModelProperty("评语")
    @Length(groups = GroupOne.class,max = 50)
    private String comment;
    @ApiModelProperty("评价人")
    private String commentator;
    @ApiModelProperty("评价人id")
    private String commentatorId;
    @ApiModelProperty("评论者职务 1--班主任 2--任课老师3--班干部")
    private Integer commentatorPost;
    @ApiModelProperty("学年id")
    private String schoolYearId;
    @ApiModelProperty("学年")
    private String fromTo;
    @ApiModelProperty("0表示上学期,1下学期")
    private Integer term;

    //业务字段
    private List<String> studentIds;//学生id集合
    private String operatorPostName;//操作人职务名称
    private String icon;//制度图标地址
    private String description;//制度描述
    private Integer searchType;// 1 --> 本周， 2 --> 上周， 3 --> 本月, 自定义时间段则为null
    private String gradeId;
    private String gradeName;//年级名称
    private String classNumber;//班号
    private String currentTeacherId;//当前登陆老师id
    private Boolean isDelete;//是否删除和追加评语统一标识
    private QueryPage queryPage;//页面查询条件对象
    @ApiModelProperty("开始时间")
    private String searchBeginTime;
    @ApiModelProperty("结束时间")
    private String searchEndTime;
    private long addScore;
    private long reduceScore;
    private long stuRecordCount;//学生被评数量
    private long recordCount;
    private String imgUrl;
    private String studentName;
    private long totalScore;
    private int sortNum;
    private long rank;//排名
    private Integer stuType;//学生类别 1--最佳学生 2-急需改进 3--进步最快 4--退步最大




    @Override
    public boolean equals(Object targetObj){
        if(targetObj instanceof  MesAppletsRuleRecord){
            MesAppletsRuleRecord mesAppletsRuleRecord=(MesAppletsRuleRecord) targetObj;
            if(mesAppletsRuleRecord.getTotalScore() >-1 && mesAppletsRuleRecord.getRank()<1){
                return mesAppletsRuleRecord.getTotalScore() ==this.getTotalScore();
            }else{
                return mesAppletsRuleRecord.getRank() ==this.getRank();
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (int)this.getTotalScore()+(int)this.getRank();
        return result;
    }





}
