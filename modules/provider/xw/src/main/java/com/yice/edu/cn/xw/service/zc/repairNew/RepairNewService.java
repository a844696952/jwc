package com.yice.edu.cn.xw.service.zc.repairNew;

import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.department.DepartmentTeacher;
import com.yice.edu.cn.common.pojo.ts.aliMsn.Sms;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildAdmin;
import com.yice.edu.cn.common.pojo.xw.zc.assetsStockDetail.AssetsStockDetail;
import com.yice.edu.cn.common.pojo.xw.zc.assetsWarehouse.AssetsWarehouse;
import com.yice.edu.cn.common.pojo.xw.zc.repairNew.RepairFile;
import com.yice.edu.cn.common.pojo.xw.zc.repairNew.RepairNew;
import com.yice.edu.cn.xw.dao.zc.assetsStockDetail.IAssetsStockDetailDao;
import com.yice.edu.cn.xw.dao.zc.assetsWarehouse.IAssetsWarehouseDao;
import com.yice.edu.cn.xw.dao.zc.repairNew.IRepairFileDao;
import com.yice.edu.cn.xw.dao.zc.repairNew.IRepairNewDao;
import com.yice.edu.cn.xw.feignClient.jw.department.DepartmentTeacherFeign;
import com.yice.edu.cn.xw.service.dormManage.dorm.DormBuildAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.math.BigInteger;
import java.util.stream.Collectors;

@Service
public class RepairNewService {
    @Autowired
    private IRepairNewDao repairNewDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private IRepairFileDao repairFileDao;
    @Autowired
    private IAssetsStockDetailDao assetsStockDetailDao;
    @Autowired
    private DepartmentTeacherFeign departmentTeacherFeign;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private IAssetsWarehouseDao assetsWarehouseDao;

    @Transactional(readOnly = true)
    public RepairNew findRepairNewById(String id) {
        RepairNew repairNewById = repairNewDao.findRepairNewById(id);
        //判断当前资产状态是否是空闲状态
        AssetsWarehouse warehouseById = assetsWarehouseDao.findAssetsWarehouseById(repairNewById.getWarehouseId());
        if (warehouseById != null ){
            //空闲状态时  场地为 仓库名
            repairNewById.setWarehouseName(warehouseById.getName());
        }
        return repairNewById;
    }
    @Transactional
    public void saveRepairNew(RepairNew repairNew) {
        repairNew.setId(sequenceId.nextId());
        //订单状态(1、待维修，2、已维修，3、挂起)
        repairNew.setStatus("1");
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s8 = formatter.format(currentTime);
        //创建时间
        repairNew.setCreateTime(s8);
        //报修时间
        repairNew.setRepairTime(s8);
        //获取教师所在部门
        List<DepartmentTeacher> data = departmentTeacherFeign.findDepartmentByTeacherId(repairNew.getSubmitterId());
        StringBuffer s = new StringBuffer();
        StringBuffer s1 = new StringBuffer();
        if( data != null && data.size() > 0 ){
            for(DepartmentTeacher d:data){
                s.append(d.getDepartmentId());
                s.append(",");
                s1.append(d.getDepartmentName());
                s1.append(",");
            }
            repairNew.setSubmitterDepartementId(String.valueOf(s));
            repairNew.setSubmitterDepartement(String.valueOf(s1));
        }
        //手动报修  保修单号为14位随机数
        if ((repairNew.getRepairType()).equals("2")){
            String i = String.valueOf(Math.random());
            i = i.substring(2,16);
            repairNew.setRepairNo(i);
        }else{
            String schoolId = repairNew.getSchoolId();
            String repairNo = createAssetNoNumber(repairNew ,schoolId);
            repairNew.setRepairNo(repairNo);
        }
        repairNewDao.saveRepairNew(repairNew);
        AssetsStockDetail assetsStockDetail = new AssetsStockDetail();
        //保存后,资产在 库存明细表 中的状态就为 维修中
        assetsStockDetail.setStatus(3);
        assetsStockDetail.setId(repairNew.getAssetsStockDetailId());
        assetsStockDetailDao.updateAssetsStockDetailForNotNull(assetsStockDetail);
    }

    /**
     * 生成报修单,如果今天已有出库单生成,取最新的出库单号截取后面的数字加1作为下一个出库单号
     */
    @Transactional
    public String createAssetNoNumber(RepairNew repairNew ,String schoolId) {
        String AssetNo = repairNew.getAssetNo();
        String today = LocalDate.now().toString();

        Pager pager = new Pager();
        RepairNew repairNew1 = new RepairNew();
        pager.setLike("repairTime");
        pager.setSortField("createTime");
        pager.setSortOrder(Pager.DESC);
        repairNew1.setRepairTime(today);
        repairNew1.setSchoolId(schoolId);
        repairNew1.setPager(pager);
        List<RepairNew> list = repairNewDao.findRepairNewListByConditionNew(repairNew1);
        if (list != null && list.size() > 0) {
            String oldNum = list.get(0).getRepairNo();
            //流水号
            Integer i = Integer.valueOf(oldNum.substring(AssetNo.length()));
            String lastNo = String.format("%0" + 2 + "d",++i );
            return AssetNo+lastNo;

        }
        return AssetNo + "01";
    }

    @Transactional(readOnly = true)
    public List<RepairNew> findRepairNewListByCondition(RepairNew repairNew) {
        String[] searchTimeZone = repairNew.getSearchTimeZone();
        if (searchTimeZone!=null&&searchTimeZone.length>0){
            repairNew.setSearchStearTime(searchTimeZone[0]);
            repairNew.setSearchEndTime(searchTimeZone[1]);
        }
        List<RepairNew> listByCondition = repairNewDao.findRepairNewListByCondition(repairNew);
        //判断当前资产状态是否是空闲状态
        for(RepairNew s : listByCondition){
            AssetsWarehouse warehouseById = assetsWarehouseDao.findAssetsWarehouseById(s.getWarehouseId());
            if (warehouseById != null ){
                s.setWarehouseName(warehouseById.getName());
            }
        }
        return listByCondition;
    }
    @Transactional(readOnly = true)
    public RepairNew findOneRepairNewByCondition(RepairNew repairNew) {
        return repairNewDao.findOneRepairNewByCondition(repairNew);
    }
    @Transactional(readOnly = true)
    public long findRepairNewCountByCondition(RepairNew repairNew) {
        String[] searchTimeZone = repairNew.getSearchTimeZone();
        if (searchTimeZone!=null&&searchTimeZone.length>0){
            repairNew.setSearchStearTime(searchTimeZone[0]);
            repairNew.setSearchEndTime(searchTimeZone[1]);
        }
        return repairNewDao.findRepairNewCountByCondition(repairNew);
    }

    //处理
    @Transactional
    public void updateRepairNew(RepairNew repairNew) {
        repairNew.setRepairNo(repairNew.getRepairNo());
        repairNew.setProcessType(repairNew.getProcessType());
        if (repairNew.getUpkeepCosts() != null){
            repairNew.setUpkeepCosts(repairNew.getUpkeepCosts());
        }
        //处理备注 非空判断
        if(repairNew.getProcessRemark() != null){
            repairNew.setProcessRemark(repairNew.getProcessRemark());
        }
        repairNew.setStatus("3");
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //得到字符串时间
        String s8 = formatter.format(currentTime);
        repairNew.setUpdateTime(s8);
        repairNewDao.updateRepairNew(repairNew);

        //只有扫码报修的 才进入统计
        if (repairNew.getRepairType().equals("1")){
            AssetsStockDetail assetsStockDetail = new AssetsStockDetail();
            assetsStockDetail.setDisposeStaffId(repairNew.getDisposeStaffId());
            assetsStockDetail.setDisposeStaffName(repairNew.getDisposeStaffName());
            assetsStockDetail.setProcessType(Integer.parseInt(repairNew.getProcessType()));
            assetsStockDetail.setUpdateTime(repairNew.getUpdateTime());
            //新增字段
            assetsStockDetail.setAssetsFileId(repairNew.getAssetsFileId());
            if (repairNew.getUpkeepCosts() != null){
                assetsStockDetail.setUpkeepCosts(repairNew.getUpkeepCosts());
            }
            if(repairNew.getProcessRemark() != null){
                assetsStockDetail.setProcessRemark(repairNew.getProcessRemark());
            }
            assetsStockDetail.setId(repairNew.getAssetsStockDetailId());
            //处理类型 (1 维修成功  (默认)  2无法维修 3报废)
            if(repairNew.getProcessType().equals("1")){
                AssetsStockDetail assetsStockDetail1 = assetsStockDetailDao.findAssetsStockDetailById(repairNew.getAssetsStockDetailId());
                if (assetsStockDetail1 != null){
                    assetsStockDetail.setStatus(assetsStockDetail1.getBeforeRepairStatus());
                }
            }else if (repairNew.getProcessType().equals("2")){
                assetsStockDetail.setStatus(4);
            }
            assetsStockDetailDao.updateAssetsStockDetailForNotNull(assetsStockDetail);
        }

        /*
        * 不管是  手动还是扫码  文件还是要存的
        * */
        List<RepairFile> repairFiles = repairNew.getRepairNewFiles();
        if (repairFiles.size() > 0 ){
            repairFiles.forEach( repairFile -> {
                repairFile.setId(sequenceId.nextId());
                repairFile.setSchoolId(repairNew.getSchoolId());
                repairFile.setRepairId(repairNew.getId());
                repairFile.setAssetsStockDetailId(repairNew.getAssetsStockDetailId());
            });
            repairFileDao.batchSaveRepairFile(repairFiles);
        }
    }
    @Transactional
    public void deleteRepairNew(String id) {
        repairNewDao.deleteRepairNew(id);
    }
    @Transactional
    public void deleteRepairNewByCondition(RepairNew repairNew) {
        repairNewDao.deleteRepairNewByCondition(repairNew);
    }
    @Transactional
    public void batchSaveRepairNew(List<RepairNew> repairNews){
        repairNews.forEach(repairNew -> repairNew.setId(sequenceId.nextId()));
        repairNewDao.batchSaveRepairNew(repairNews);
    }


    @Transactional
    public void updateRepairNewPerson(RepairNew repairNew) {
        repairNew.setStatus("2");
        repairNew.setRepairStaffId(repairNew.getRepairStaffId());
        repairNew.setRepairStaffName(repairNew.getRepairStaffName());
        repairNew.setRepairStaffTel(repairNew.getRepairStaffTel());
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //得到字符串时间
        String s8 = formatter.format(currentTime);
        repairNew.setStaffTime(s8);
        repairNewDao.updateRepairNewPerson(repairNew);
        //保存后发短信 提示
        //先获取该条数据的所有短信所需信息
        RepairNew repairNews = repairNewDao.findRepairNewByIdNew(repairNew.getId());
        Map<String,String> msg = new HashMap<>();
        msg.put("tel",repairNews.getRepairStaffTel());
        msg.put("assetSpace",repairNews.getWarehouseName());
        msg.put("assetName",repairNews.getAssetName());
        msg.put("submitter",repairNews.getSubmitterName()+repairNews.getSubmitterTel());
        final Sms sms = new Sms(repairNews.getRepairStaffTel(), Constant.MCS_SIGN_NAME.YCJD, Constant.MCS_TEMPLATE.SMS_VERIFICATION_ZC, msg);
        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.SMS_SEND_MSG, JSONUtil.toJsonStr(sms));
        String i = "1";
        if (repairNew.getRepairType().equals(i)){
            AssetsStockDetail assetsStockDetail = new AssetsStockDetail();
            assetsStockDetail.setId(repairNew.getAssetsStockDetailId());
            assetsStockDetail.setRepairStaffId(repairNew.getRepairStaffId());
            assetsStockDetail.setRepairStaffName(repairNew.getRepairStaffName());
            assetsStockDetail.setRepairStaffTel(repairNew.getRepairStaffTel());
            assetsStockDetail.setStatus(3);
            assetsStockDetailDao.updateAssetsStockDetailForNotNull(assetsStockDetail);
        }
    }



    //报废
    @Transactional
    public void scrapRepairNew(RepairNew repairNew) {
        repairNewDao.scrapRepairNew(repairNew);

        //只有扫码报修的 才进入统计
        if(repairNew.getRepairType().equals("1")){
            AssetsStockDetail assetsStockDetail = new AssetsStockDetail();
            assetsStockDetail.setScrapRemark(repairNew.getScrapRemark());
            assetsStockDetail.setOperatorId(repairNew.getOperatorId());
            assetsStockDetail.setOperatorName(repairNew.getOperatorName());
            assetsStockDetail.setScrapTime(repairNew.getScrapTime());
            assetsStockDetail.setId(repairNew.getAssetsStockDetailId());
            assetsStockDetail.setStatus(5);
            assetsStockDetailDao.updateAssetsStockDetailForNotNull(assetsStockDetail);
        }

        List<RepairFile> repairFiles = repairNew.getRepairNewFiles();
        if (repairFiles.size() > 0){
            repairFiles.forEach( repairFile -> {
                repairFile.setId(sequenceId.nextId());
                repairFile.setSchoolId(repairNew.getSchoolId());
                repairFile.setRepairId(repairNew.getId());
                repairFile.setAssetsStockDetailId(repairNew.getAssetsStockDetailId());
            });
            repairFileDao.batchSaveRepairFile(repairFiles);
        }
    }

    @Transactional(readOnly = true)
    public RepairNew lookRepairNewByAssetNo(String assetNo) {
        return repairNewDao.lookRepairNewByAssetNo(assetNo);
    }



    @Transactional(readOnly = true)
    public RepairNew findRepairNewByIdNew(String id) {
        return repairNewDao.findRepairNewByIdNew(id);
    }


    @Transactional(readOnly = true)
    public double findRepairNewUpkeepCostsBySchool(RepairNew repairNew) {
        return repairNewDao.findRepairNewUpkeepCostsBySchool(repairNew);
    }

    @Transactional(readOnly = true)
    public long findRepairNewsCountByWeek(RepairNew repairNew) {
        String today = LocalDate.now().toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(today);
        }catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar=Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        //获得当前的年
        int weekYear = calendar.get(Calendar.YEAR);
        //获得当前日期属于今年的第几周
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        //获得指定年的第几周的开始日期
        calendar.setWeekDate(weekYear, weekOfYear, 2);
        long starttime = calendar.getTime().getTime();
        //获得指定年的第几周的结束日期
        calendar.setWeekDate(weekYear, weekOfYear, 1);
        long endtime = calendar.getTime().getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //将时间戳格式化为指定格式
        String dateStart = simpleDateFormat.format(starttime);
        String dateEnd = simpleDateFormat.format(endtime);
        repairNew.setSearchStearTime(dateStart);
        repairNew.setSearchEndTime(dateEnd);
        return repairNewDao.findRepairNewsCountByWeek(repairNew);
    }


    @Transactional(readOnly = true)
    public long findRepairNewsCountByWeekLose1(RepairNew repairNew) {
        String today = LocalDate.now().toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(today);
        }catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar=Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        //获得当前的年
        int weekYear = calendar.get(Calendar.YEAR);
        //获得当前日期属于今年的第几周
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        //获得指定年的第几周的开始日期
        calendar.setWeekDate(weekYear, weekOfYear-1, 2);
        long starttime = calendar.getTime().getTime();
        //获得指定年的第几周的结束日期
        calendar.setWeekDate(weekYear, weekOfYear-1, 1);
        long endtime = calendar.getTime().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //将时间戳格式化为指定格式
        String dateStart = simpleDateFormat.format(starttime);
        String dateEnd = simpleDateFormat.format(endtime);
        repairNew.setSearchStearTime(dateStart);
        repairNew.setSearchEndTime(dateEnd);
        return repairNewDao.findRepairNewsCountByWeek(repairNew);
    }


    @Transactional(readOnly = true)
    public List<RepairNew> selectBuildingNameList(RepairNew repairNew) {
        return repairNewDao.selectBuildingNameList(repairNew);
    }

    @Transactional(readOnly = true)
    public List<RepairNew> selectRoomName(RepairNew repairNew) {
        return repairNewDao.selectRoomName(repairNew);
    }
    public List<RepairNew> findRepairNewsBySchoolIds(RepairNew repairNew) {
        return repairNewDao.findRepairNewsBySchoolIds(repairNew);
    }
    @Transactional(readOnly = true)
    public long findRepairNewCountBySchoolIds(RepairNew repairNew) {
        return repairNewDao.findRepairNewCountBySchoolIds(repairNew);
    }
}


