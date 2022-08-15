package com.yice.edu.cn.xw.service.doc;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.xw.document.Writing;
import com.yice.edu.cn.common.pojo.xw.document.WritingManagement;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.xw.dao.doc.WritingDao;
import com.yice.edu.cn.xw.dao.doc.WritingManagementDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class WritingManagementService {
    @Autowired
    private WritingManagementDao writingManagementDao;
    @Autowired
    private SequenceId sequenceId;

    @Autowired
    private WritingDao writingDao;
    @Autowired
    private WritingService writingService;

    @Transactional(readOnly = true)
    public WritingManagement findWritingManagementById(String id) {
        return writingManagementDao.findWritingManagementById(id);
    }
    @Transactional
    public void saveWritingManagement(WritingManagement writingManagement) {
        writingManagement.setId(sequenceId.nextId());
        writingManagementDao.saveWritingManagement(writingManagement);
    }

    @Transactional(readOnly = true)
    public List<WritingManagement> findWritingManagementListByCondition(WritingManagement writingManagement) {

        return writingManagementDao.findWritingManagementListByCondition(writingManagement);
    }
    @Transactional(readOnly = true)
    public WritingManagement findOneWritingManagementByCondition(WritingManagement writingManagement) {
        return writingManagementDao.findOneWritingManagementByCondition(writingManagement);
    }
    @Transactional(readOnly = true)
    public long findWritingManagementCountByCondition(WritingManagement writingManagement) {
        return writingManagementDao.findWritingManagementCountByCondition(writingManagement);
    }
    @Transactional
    public void updateWritingManagement(WritingManagement writingManagement) {
        writingManagementDao.updateWritingManagement(writingManagement);
    }
    @Transactional
    public void deleteWritingManagement(String id) {
        writingManagementDao.deleteWritingManagement(id);
    }
    @Transactional
    public void deleteWritingManagementByCondition(WritingManagement writingManagement) {
        writingManagementDao.deleteWritingManagementByCondition(writingManagement);
    }
    @Transactional
    public void batchSaveWritingManagement(List<WritingManagement> writingManagements){
        writingManagements.forEach(writingManagement -> writingManagement.setId(sequenceId.nextId()));
        writingManagementDao.batchSaveWritingManagement(writingManagements);
    }


    //我接收的查询方法(已弃用)
    @Transactional
    public List<Writing> findWritingAndManagementListByCondtion(WritingManagement writingManagement){
        List<WritingManagement> list = writingManagementDao.findWritingManagementListByCondition(writingManagement);
        List<Writing> writings = new ArrayList<>();
        list.forEach(f->{
            Writing writing = new Writing();
            writing.setId(f.getWritingId());
            writing.setWritingType(2);
            Writing writing1 = writingDao.findOneWritingByCondition(writing);
//            System.out.println(writing1);
            if(writing1!=null){
                writings.add(writing1);
            }
        });
        return writings;
    }

    //点击查看时，返回数据并将状态修改为已查看
    @Transactional
    public Writing lookAndupdateWritingAndWritingManagement(Writing writings){
        Writing writing = writingService.findWritingById(writings.getId());

        //找到此人记录的对应记录
       WritingManagement writingManagement =  new WritingManagement();
       writingManagement.setWritingObjectId(writings.getSendObjectId());
       writingManagement.setWritingId(writings.getId());
       WritingManagement writingManagement1 = findOneWritingManagementByCondition(writingManagement);

       //修改状态
       writingManagement1.setType(2);
       updateWritingManagement(writingManagement1);

       writing.setFileUrlList(writing.getFileUrl().split("\\|"));
       writing.setFileNameList(writing.getFileName().split("\\|"));
       return writing;
    }

    @Transactional(readOnly = true)
    //我接收的查询方法2.0
    public List<Writing> findWritingAndWritingManagement(Writing writing){
        List<Writing> writings = writingManagementDao.findWritingAndWritingManagement(writing);
        return writings;
    }

    @Transactional(readOnly = true)
    //我接收的查询方法的总数量
    public long findWritingAndWritingManagementLong(Writing writing){
        return writingManagementDao.findWritingAndWritingManagementLong(writing);
    }


    public List<Department> getDocManagementReadOrUnRead(WritingManagement writingManagement){
        writingManagement.setDepartmentType(1);
        List<WritingManagement> writingManagement1 = findWritingManagementListByCondition(writingManagement);

        writingManagement.setDepartmentType(0);
        writingManagement.setType(null);
        List<WritingManagement> writingManagement2 = findWritingManagementListByCondition(writingManagement);

        return buildTree(writingManagement1,writingManagement2);
    }

    public  List<Department> buildTree(List<WritingManagement> data1 , List<WritingManagement> data2){
        List<WritingManagement> data=new ArrayList<>();
        data.addAll(data1);
        data.addAll(data2);
        List<Department> departmentList=new ArrayList<>();
        data.forEach(f -> {
            Department department=new Department();
            department.setId(f.getWritingObjectId());
            department.setName(f.getWritingObjectName());
            department.setType(f.getDepartmentType());
            department.setParentId(f.getDepartmentParentId());
            department.setImgUrl(f.getImgUrl());
            departmentList.add(department);
        });
        return ObjectKit.buildTree(departmentList,"-1");

    }

}
