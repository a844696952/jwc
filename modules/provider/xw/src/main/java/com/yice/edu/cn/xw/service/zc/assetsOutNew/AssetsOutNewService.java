package com.yice.edu.cn.xw.service.zc.assetsOutNew;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.department.DepartmentTeacher;
import com.yice.edu.cn.common.pojo.xw.zc.assetsOutNew.AssetsOutNew;
import com.yice.edu.cn.common.pojo.xw.zc.assetsStockDetail.AssetsStockDetail;
import com.yice.edu.cn.xw.dao.zc.assetsOutNew.IAssetsOutNewDao;
import com.yice.edu.cn.xw.dao.zc.assetsStockDetail.IAssetsStockDetailDao;
import com.yice.edu.cn.xw.feignClient.jw.department.DepartmentTeacherFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssetsOutNewService {
    @Autowired
    private IAssetsOutNewDao assetsOutNewDao;
    @Autowired
    private IAssetsStockDetailDao assetsStockDetailDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private DepartmentTeacherFeign departmentTeacherFeign;

    @Transactional(readOnly = true)
    public AssetsOutNew findAssetsOutNewById(String id) {
        return assetsOutNewDao.findAssetsOutNewById(id);
    }
    @Transactional
    public void saveAssetsOutNew(AssetsOutNew assetsOutNew) {
        assetsOutNew.setId(sequenceId.nextId());
        assetsOutNewDao.saveAssetsOutNew(assetsOutNew);
    }
    @Transactional(readOnly = true)
    public List<AssetsOutNew> findAssetsOutNewListByCondition(AssetsOutNew assetsOutNew) {
        return assetsOutNewDao.findAssetsOutNewListByCondition(assetsOutNew);
    }
    @Transactional(readOnly = true)
    public AssetsOutNew findOneAssetsOutNewByCondition(AssetsOutNew assetsOutNew) {
        return assetsOutNewDao.findOneAssetsOutNewByCondition(assetsOutNew);
    }
    @Transactional(readOnly = true)
    public long findAssetsOutNewCountByCondition(AssetsOutNew assetsOutNew) {
        return assetsOutNewDao.findAssetsOutNewCountByCondition(assetsOutNew);
    }
    @Transactional
    public void updateAssetsOutNew(AssetsOutNew assetsOutNew) {
        assetsOutNewDao.updateAssetsOutNew(assetsOutNew);
    }
    @Transactional
    public void deleteAssetsOutNew(String id) {
        assetsOutNewDao.deleteAssetsOutNew(id);
    }
    @Transactional
    public void deleteAssetsOutNewByCondition(AssetsOutNew assetsOutNew) {
        assetsOutNewDao.deleteAssetsOutNewByCondition(assetsOutNew);
    }
    @Transactional
    public void batchSaveAssetsOutNew(List<AssetsOutNew> assetsOutNews){
        assetsOutNews.forEach(assetsOutNew -> assetsOutNew.setId(sequenceId.nextId()));
        String schoolId = assetsOutNews.get(0).getSchoolId();
        String getOutTime = assetsOutNews.get(0).getGetOutTime();
        String assetsOutNumber = createAssetsOutNumber(schoolId,getOutTime);
        List<AssetsOutNew> newAssetsOutNews = assetsOutNews.stream().map(ss -> {
            List<DepartmentTeacher> department = departmentTeacherFeign.findDepartmentByTeacherId(ss.getApplyUserId());
            StringBuffer personDeptId = new StringBuffer();
            StringBuffer personDeptName = new StringBuffer();
            if(department.size()>0&&department!=null){
                for(DepartmentTeacher d:department){
                    personDeptId.append(d.getDepartmentId());
                    personDeptId.append(",");
                    personDeptName.append(d.getDepartmentName());
                    personDeptName.append(",");
                }
                String subPersonDeptId = personDeptId.substring(0, personDeptId.lastIndexOf(","));
                String subPersonDeptName = personDeptName.substring(0, personDeptName.lastIndexOf(","));
                ss.setApplyUserDepartmentId(subPersonDeptId);
                ss.setApplyUserDepartmentName(subPersonDeptName);
            }
            ss.setGetOutNumber(assetsOutNumber);
            ss.setGetOutCount(assetsOutNews.size());
            return ss;
        }).collect(Collectors.toList());
        assetsOutNewDao.batchSaveAssetsOutNew(newAssetsOutNews);
        //同步数据到资产明细
        newAssetsOutNews.forEach(s -> {
            AssetsStockDetail asd = new AssetsStockDetail();
            asd.setId(s.getAssetsId());
            asd.setStatus(2);//状态改为占用
            asd.setBeforeRepairStatus(2);
            asd.setReturnTime("");
            asd.setBorrowOperaterId(s.getCreateUserId());
            asd.setBorrowOperaterName(s.getCreateUsername());
            asd.setBorrowTime(s.getGetOutTime());
            asd.setBuildingId(s.getBuildingId());
            asd.setTypeId(s.getTypeId());
            asd.setUsePlaceId(s.getWarehouseId());
            asd.setUsePlace(s.getWarehouseName());
            asd.setDutyPerson(s.getApplyUserId());
            asd.setDutyPersonName(s.getApplyUserName());
            asd.setDutyPersonDeptId(s.getApplyUserDepartmentId());
            asd.setDutyPersonDept(s.getApplyUserDepartmentName());
            assetsStockDetailDao.updateAssetsStockDetailForNotNull(asd);
        });
    }
    /**
     * 根据createTime用今天的日期模糊查询取最新的,如果有,前缀+入库日期+流水号+1作为下一个出库单号
     */
    @Transactional
    public String createAssetsOutNumber(String schoolId,String getOutTime) {
        DateTime dateTime = DateUtil.parse(getOutTime);
        String yyyyMMdd = DateUtil.format(dateTime, "yyyyMMdd");
        Pager pager = new Pager();
        pager.setLike("createTime");
        pager.setSortField("createTime");
        pager.setSortOrder(Pager.DESC);
        AssetsOutNew assetsOutNew = new AssetsOutNew();
        assetsOutNew.setSchoolId(schoolId);
        assetsOutNew.setCreateTime(LocalDate.now().toString());
        assetsOutNew.setPager(pager);
        List<AssetsOutNew> list = assetsOutNewDao.findAssetsOutNewListByCondition(assetsOutNew);
        if (list != null && list.size() > 0) {
            String oldNum = list.get(0).getGetOutNumber();
            String subStr = oldNum.substring(Constant.ZC.CK_PREFIX.length(), oldNum.length());
            BigInteger newNum = new BigInteger(subStr);
            newNum = newNum.add(new BigInteger("1"));
            String number = newNum.toString();
            String finalNum = number.substring(number.length() - 3, number.length());
            return Constant.ZC.CK_PREFIX + yyyyMMdd+finalNum;
        }
        return Constant.ZC.CK_PREFIX + yyyyMMdd + "001";
    }
    @Transactional
    public List<AssetsOutNew> findAssetsOutNewListByCondition4Gather(AssetsOutNew assetsOutNew){
        return assetsOutNewDao.findAssetsOutNewListByCondition4Gather(assetsOutNew);
    }
    @Transactional
    public long findAssetsOutNewCountByCondition4Gather(AssetsOutNew assetsOutNew){
        return assetsOutNewDao.findAssetsOutNewCountByCondition4Gather(assetsOutNew);
    }
}
