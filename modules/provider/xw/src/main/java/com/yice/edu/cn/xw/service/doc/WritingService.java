package com.yice.edu.cn.xw.service.doc;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.common.pojo.xw.document.*;
import com.yice.edu.cn.xw.dao.doc.WritingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class WritingService {
    @Autowired
    private WritingDao writingDao;
    @Autowired
    private SequenceId sequenceId;

    @Autowired
    private WritingLeaderService writingLeaderService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private WritingManagementService writingManagementService;

    //通过id查询单个
    @Transactional(readOnly = true)
    public Writing findWritingById(String id) {
        Writing writing  = writingDao.findWritingById(id);//通过id查询的sql语句已被修改（将携带发送对象返回）
        WritingManagement writingManagement = new WritingManagement();
        writingManagement.setWritingId(id);
        writingManagement.setDepartmentType(1);
        List<WritingManagement> writingManagements  = writingManagementService.findWritingManagementListByCondition(writingManagement);
        writing.setWritingManagementList(writingManagements);
        writing.setFileUrlList(writing.getFileUrl().split("\\|"));
        writing.setFileNameList(writing.getFileName().split("\\|"));
        List<WritingManagement> list = new ArrayList<>();

        if(writingManagements!=null){
            int readNum = 0;//已读
            int UnReadNum = 0;//未读
            for(int i = 0;i<writingManagements.size();i++){
                if(writingManagements.get(i).getType()==1){
                    UnReadNum++;
                }else{
                    readNum++;
                }
            }
            writing.setReadNum(readNum);
            writing.setUnReadNum(UnReadNum);
        };
        return writing;
        //用来当做审批完成时的推送对象名单
        /*if(writingManagements!=null&&writingManagements.size()>0){
            String[] sendObjId = writing.getSendObjectId().split(",");
            String[] sendObjName = writing.getSendObjectName().split(",");
            String[] sendObjImg   = writing.getSendObjectImg().split(",");
            String[] departmentParentId = writing.getDepartmentParentId().split(",");
            for (int i =0;i<sendObjId.length;i++){
                WritingManagement writingManagement = new WritingManagement();
                writingManagement.setWritingObjectId(sendObjId[i]);
                writingManagement.setWritingObjectName(sendObjName[i]);
                writingManagement.setImgUrl(sendObjImg[i]);
                writingManagement.setDepartmentParentId(departmentParentId[i]);
                writingManagement.setDepartmentType(1);
                writingManagement.setSchoolId(writing.getSchoolId());
                list.add(writingManagement);
            }
            writing.setSendObjectName(null);
            writing.setSendObjectImg(null);
            writing.setDepartmentParentId(null);
            writing.setWritingManagementList(list);
        }*/

    }

    //添加已被修改
    @Transactional
    public void saveWriting(DocDepartment docDepartment) {
        Writing writing = docDepartment.getWriting();
        //发文管理
        String id = sequenceId.nextId();
        writing.setId(id);
        writing.setWritingType(1);
        writing.setReject(0);
        writingDao.saveWriting(writing);

        //发文管理与领导批阅表
        WritingLeader writingLeader  = new WritingLeader();
        writingLeader.setWritingId(id);
        writingLeader.setLeaderName(writing.getLeaderName());
        writingLeader.setLeaderId(writing.getLeaderId());
        writingLeader.setType(1);
        writingLeader.setSchoolId(writing.getSchoolId());

        writingLeaderService.saveWritingLeader(writingLeader);

        //发文管理与发送对象查看表
        if(writing.getSendObject()!=null){
        List<SendObject> sendObject = writing.getSendObject();
        List<WritingManagement> writingManagements = new ArrayList<>();
        List<Department> departmentList = docDepartment.getDepartments();
        sendObject.forEach(f->{
            WritingManagement writingManagement = new WritingManagement();
            writingManagement.setId(sequenceId.nextId());
            writingManagement.setWritingId(id);
            writingManagement.setType(1);
            writingManagement.setWritingObjectId(f.getId());
            writingManagement.setWritingObjectName(f.getName());
            writingManagement.setDepartmentType(1);
            writingManagement.setDepartmentParentId(f.getParentId());
            writingManagement.setCreateTime(DateUtil.now());
            writingManagement.setSchoolId(f.getSchoolId());
            if(f.getImgUrl()!=null){
                writingManagement.setImgUrl(f.getImgUrl());
            }else {
                writingManagement.setImgUrl("/headProfile/man.png");
            }
            writingManagements.add(writingManagement);

            /*writingManagementService.saveWritingManagement(writingManagement);*/
        });

        removeDuplicate(writingManagements);

        /* 组织架构树
        writingManagements.addAll(removeDuplicate(addMyParentId(writingManagements,departmentList)));

        writingManagements.forEach(f->{
            f.setId(sequenceId.nextId());
            f.setWritingId(id);
        });*/
        writingManagementService.batchSaveWritingManagement(writingManagements);
            String[] leaderIdList = new String[]{writing.getLeaderId()};
            //推送给批阅领导
            final Push push1 = Push.newBuilder(JpushApp.TAP).getSimplePush(leaderIdList,"公文通知","您有一条新的公文通知，请及时审批！",Extras.XW_WRITING_TYPE);
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push1));




        }
    }
    //查询的接口
    @Transactional(readOnly = true)
    public List<Writing> findWritingListByCondition(Writing writing) {
        //查询sql已被修改
        List<Writing> list = writingDao.findWritingListByCondition(writing);
        list.forEach(f->{
          if(f.getNameList()!=null){
              String[] read = f.getNameList().split(",");
              int readNum = 0;//已读
              int UnReadNum = 0;//未读
              for(int i = 0;i<read.length;i++){
                  if(read[i].equals("1")){
                      UnReadNum++;
                  }else{
                      readNum++;
                  }
              }
              f.setReadNum(readNum);
              f.setUnReadNum(UnReadNum);
          }

        });
        return list;
    }
    @Transactional(readOnly = true)
    public Writing findOneWritingByCondition(Writing writing) {
        return writingDao.findOneWritingByCondition(writing);
    }
    @Transactional(readOnly = true)
    public long findWritingCountByCondition(Writing writing) {
        return writingDao.findWritingCountByCondition(writing);
    }
    @Transactional
    public void updateWriting(DocDepartment docDepartment) {
        Writing writing = docDepartment.getWriting();
        WritingLeader writingLeader = new WritingLeader();
        writingLeader.setWritingId(writing.getId());
        writingLeader.setType(1);
        //首先找到该条记录
        WritingLeader writingLeader1 =  writingLeaderService.findOneWritingLeaderByCondition(writingLeader);

        //添加此条记录的驳回意见
        writingLeader1.setRemarks(" ");
        writingLeaderService.updateWritingLeader(writingLeader1);

        //清空发送对象，重新插入
        WritingManagement writingManagement = new WritingManagement();
        writingManagement.setWritingId(writing.getId());
        writingManagementService.deleteWritingManagementByCondition(writingManagement);

        List<SendObject> sendObjectList = writing.getSendObject();
        List<WritingManagement> writingManagements = new ArrayList<>();
        sendObjectList.forEach(f->{
            WritingManagement writingManagement1 = new WritingManagement();
            writingManagement1.setImgUrl(f.getImgUrl());
            writingManagement1.setCreateTime(DateUtil.now());
            writingManagement1.setWritingObjectId(f.getId());
            writingManagement1.setWritingObjectName(f.getName());
            writingManagement1.setWritingId(writing.getId());
            writingManagement1.setDepartmentParentId(f.getParentId());
            writingManagement1.setType(1);
            writingManagement1.setDepartmentType(f.getType());
            writingManagement1.setSchoolId(f.getSchoolId());
            writingManagements.add(writingManagement1);
        });

        /*组织架构树
        List<Department> departmentList = docDepartment.getDepartments();

        writingManagements.addAll(removeDuplicate(addMyParentId(writingManagements,departmentList)));
        writingManagements.forEach(f->{
            f.setWritingId(writing.getId());
            f.setId(sequenceId.nextId());
        });*/
        removeDuplicate(writingManagements);
        writingManagementService.batchSaveWritingManagement(writingManagements);

        writingDao.updateWriting(writing);
    }
    @Transactional
    public void deleteWriting(String id) {
        writingDao.deleteWriting(id);
    }
    @Transactional
    public void deleteWritingByCondition(Writing writing) {
        writingDao.deleteWritingByCondition(writing);
    }
    @Transactional
    public void batchSaveWriting(List<Writing> writings){
        writings.forEach(writing -> writing.setId(sequenceId.nextId()));
        writingDao.batchSaveWriting(writings);
    }


    //修改驳回查看状态
    @Transactional
    public Writing getWritingRejectUpdate(String id){
        Writing writing = findWritingById(id);
        writing.setReject(2);
        writingDao.updateWriting(writing);

        return writing;
    }


    @Transactional(readOnly = true)
    public Boolean getdepartmentUpdate(DocDepartment docDepartment){
        List<Department> departmentList = docDepartment.getDepartments();
        String id = docDepartment.getId();
        WritingManagement writingManagement = new WritingManagement();
        writingManagement.setWritingId(id);
        writingManagement.setDepartmentType(0);

        List<WritingManagement> writingManagements = writingManagementService.findWritingManagementListByCondition(writingManagement);
        Boolean flag = true;
        int size = writingManagements.size();
        int count = departmentList.size();
        look: for (int i=0;i<size;i++){
            for(int j = 0;j<count;j++){
                if(writingManagements.get(i).getDepartmentParentId().equals(departmentList.get(j).getParentId())&&writingManagements.get(i).getWritingObjectId().equals(departmentList.get(j).getId())){
                    continue look;
                }
            }
            flag = false;
            break ;
         }

         return flag;

    }



    /**
     * 去除重复
     * @param list
     * @return
     */
    public   static   List  removeDuplicate(List<WritingManagement> list)  {
        for  ( int  i  =   0 ; i  <  list.size()  -   1 ; i ++ )  {
            for  ( int  j  =  list.size()  -   1 ; j  >  i; j -- )  {
                if  (list.get(j).getWritingObjectId().equals(list.get(i).getWritingObjectId()))  {
                    list.remove(j);
                }
            }
        }
        return list;
    }

    /**
     * 通过发送对象筛选出所有的上级节点
     * @param writingManagements
     * @param departmentList
     * @return
     */
    public List<WritingManagement> addMyParentId(List<WritingManagement> writingManagements,List<Department> departmentList){
        ArrayList<WritingManagement> writingManagements1=new ArrayList<>();
        for  ( int  i  =   0 ; i  <  writingManagements.size() ; i ++ )  {
            List<WritingManagement> resultList=findMyParentId(writingManagements.get(i).getDepartmentParentId(),departmentList,writingManagements1);
            if(resultList!=null)
                writingManagements1.addAll(resultList);
        }
        return writingManagements1;
    }

    public List<WritingManagement> findMyParentId(String parentId, List<Department> departmentList, ArrayList<WritingManagement> writingManagementArrayList){
        for (int i = 0; i <departmentList.size(); i++) {
            if(departmentList.get(i).getId().equals(parentId)){
                WritingManagement writingManagement=new WritingManagement();
                writingManagement.setWritingObjectId(departmentList.get(i).getId());
                writingManagement.setDepartmentParentId(departmentList.get(i).getParentId());
                writingManagement.setWritingObjectName(departmentList.get(i).getName());
                writingManagement.setDepartmentType(departmentList.get(i).getType());
                writingManagement.setSchoolId(departmentList.get(i).getSchoolId());
                writingManagementArrayList.add(writingManagement);
                if(!departmentList.get(i).getParentId().equals("-1")){
                    findMyParentId(departmentList.get(i).getParentId(),departmentList,writingManagementArrayList);
                }else {
                    return writingManagementArrayList;
                }
            }
        }
        return null;
    }

}
