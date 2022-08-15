package com.yice.edu.cn.xw.dao.checkedDetail;

import java.util.List;
import java.util.Map;

import com.yice.edu.cn.common.pojo.xw.checkedDetail.CheckedDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ICheckedDetailDao {
    List<CheckedDetail> findCheckedDetailListByCondition(CheckedDetail checkedDetail);

    long findCheckedDetailCountByCondition(CheckedDetail checkedDetail);

    CheckedDetail findOneCheckedDetailByCondition(CheckedDetail checkedDetail);

    CheckedDetail findCheckedDetailById(@Param("id") String id);

    void saveCheckedDetail(CheckedDetail checkedDetail);

    void updateCheckedDetail(CheckedDetail checkedDetail);

    void deleteCheckedDetail(@Param("id") String id);

    void deleteCheckedDetailByCondition(CheckedDetail checkedDetail);

    void batchSaveCheckedDetail(List<CheckedDetail> checkedDetails);

    List<CheckedDetail> findCheckedDetailListForAppTapByCondition(CheckedDetail checkedDetail);//tap教师查询 小红点

    List<CheckedDetail> findCheckedDetailsForAppTapByConditionNewPrincipal(CheckedDetail checkedDetail); //tap校长查询当天的值班记录
    List<CheckedDetail> findDutyArrmentListByConditionForLike(CheckedDetail checkedDetail);
    List<CheckedDetail> findCheckedDetailsForAppTapNameOrTelByConditionNewPrincipal(CheckedDetail checkedDetail); //tap校长查询值班统计，教师名字和手机号查询

    void updateCheckedDetailForTapNewTeacher(CheckedDetail checkedDetail);


    void deleteCheckedDetailByConditionForLike(CheckedDetail checkedDetail_sc);

    List<CheckedDetail> findCheckedDetailListByCondition4like(CheckedDetail checkedDetail);

    void updateCheckedDetail4Like(CheckedDetail checkedDetail);

    void updateCheckedDetailBatch(CheckedDetail checkedDetail1Update);

    void deleteCheckedDetail4Like(List<String> delIds);

    void updateCheckedDetailBatch4Like(CheckedDetail checkedDetailUpdate);
    //删除值班明细
    void deleteCheckedDetail4(CheckedDetail checkedDetail);

    List<CheckedDetail> findListConditionByTime(CheckedDetail checkedDetailc);

    void deleteCheckedDetail4LikeF(CheckedDetail detailCheckDetail);
}
