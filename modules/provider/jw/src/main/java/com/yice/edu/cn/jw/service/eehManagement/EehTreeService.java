package com.yice.edu.cn.jw.service.eehManagement;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.eehManagement.EehAccount;
import com.yice.edu.cn.common.pojo.jw.eehManagement.EehSchool;
import com.yice.edu.cn.common.pojo.jw.eehManagement.EehTree;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.jw.dao.eehManagement.IEehAccountDao;
import com.yice.edu.cn.jw.dao.eehManagement.IEehSchoolDao;
import com.yice.edu.cn.jw.dao.eehManagement.IEehTreeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class EehTreeService {
    @Autowired
    private IEehTreeDao eehTreeDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private IEehAccountDao eehAccountDao;
    @Autowired
    private IEehSchoolDao eehSchoolDao;

    @Transactional(readOnly = true)
    public EehTree findEehTreeById(String id) {
        return eehTreeDao.findEehTreeById(id);
    }
    @Transactional
    public void saveEehTree(EehTree eehTree) {
        eehTree.setId(sequenceId.nextId());
        if(eehTree.getParentId()==null||"-1".equals(eehTree.getParentId())){
            eehTree.setParentId("-1");
            eehTree.setLevel(1);
            eehTree.setLeaf(2);
            eehTree.setStatus("1");
            eehTree.setRelation(eehTree.getId()+";");
            eehTreeDao.saveEehTree(eehTree);
        }else {
            EehTree eehTreeById = this.findEehTreeById(eehTree.getParentId());
            if(eehTreeById!=null){
                eehTree.setLevel(eehTreeById.getLevel().intValue()+1);
                eehTree.setLeaf(1);
                eehTree.setStatus("1");
                eehTree.setRelation(eehTreeById.getRelation()+eehTree.getId()+";");
                eehTreeDao.saveEehTree(eehTree);
            }
        }
       //添加电教馆管理员默认账号
        EehAccount eehAccount=new EehAccount();
        eehAccount.setId(sequenceId.nextId());
        eehAccount.setEehId(eehTree.getId());
        eehAccount.setEehName(eehTree.getTitle());
        eehAccount.setAccount("eeh"+ RandomUtil.randomNumbers(6));
        eehAccount.setAccountType("1");
        eehAccount.setName("管理员");
        eehAccount.setPlatform(eehTree.getType());
        eehAccount.setPassword(DigestUtil.sha1Hex(DigestUtil.md5Hex("66666666")));
        eehAccount.setStatus("1");
        eehAccountDao.saveEehAccount(eehAccount);
    }
    @Transactional(readOnly = true)
    public List<EehTree> findEehTreeListByCondition(EehTree eehTree) {
        return eehTreeDao.findEehTreeListByCondition(eehTree);
    }
    @Transactional(readOnly = true)
    public EehTree findOneEehTreeByCondition(EehTree eehTree) {
        return eehTreeDao.findOneEehTreeByCondition(eehTree);
    }
    @Transactional(readOnly = true)
    public long findEehTreeCountByCondition(EehTree eehTree) {
        return eehTreeDao.findEehTreeCountByCondition(eehTree);
    }
    @Transactional
    public void updateEehTree(EehTree eehTree) {
        eehTreeDao.updateEehTree(eehTree);
    }
    @Transactional
    public void deleteEehTree(String id) {
        EehAccount eehAccount=new EehAccount();
        EehSchool eehSchool=new EehSchool();
        eehAccount.setEehId(id);
        eehAccountDao.deleteEehAccountByCondition(eehAccount);
        eehSchool.setEehId(id);
        eehSchoolDao.deleteEehSchoolByCondition(eehSchool);
        eehTreeDao.deleteEehTree(id);
        this.deleteEehTreeByPid(id);
    }
    @Transactional
    public void deleteEehTreeByCondition(EehTree eehTree) {
        eehTreeDao.deleteEehTreeByCondition(eehTree);
    }
    @Transactional
    public void batchSaveEehTree(List<EehTree> eehTrees){
        eehTrees.forEach(eehTree -> eehTree.setId(sequenceId.nextId()));
        eehTreeDao.batchSaveEehTree(eehTrees);
    }

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public List<EehTree> findAllTreeMenu(String type) {
        EehTree eehTree=new EehTree();
        eehTree.setType(type);
        eehTree.setAccountStatus("1");
        List<EehTree> eehTreeList = eehTreeDao.findAllTreeMenu(eehTree);
        return ObjectKit.buildTree(eehTreeList,"-1");

    }

    private String getCharAndNumr(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            // 输出字母还是数字
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 字符串
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 取得大写字母还是小写字母
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    private void deleteEehTreeByPid(String id){
        EehAccount eehAccount=new EehAccount();
        EehSchool eehSchool=new EehSchool();
        List<EehTree> eehTreeList=eehTreeDao.findEehTreeByPid(id);
        if(eehTreeList!=null&&eehTreeList.size()>0){
            eehTreeList.forEach(s->{
                eehTreeDao.deleteEehTree(s.getId());
                eehAccount.setEehId(s.getId());
                eehAccountDao.deleteEehAccountByCondition(eehAccount);
                eehSchool.setEehId(s.getId());
                eehSchoolDao.deleteEehSchoolByCondition(eehSchool);
                this.deleteEehTreeByPid(s.getId());
            });
        }
    }

    @Transactional(readOnly = true)
    public EehTree lookEehTreeNewById(String id) {
        return eehTreeDao.lookEehTreeNewById(id);
    }

    @Transactional(readOnly = true)
    public List<String> findChildEehId(String id) {
        List<String> eehList=new ArrayList<>();
        eehList.add(id);
        this.findChildEehIds(id,eehList);
        return eehList;
    }

    public void findChildEehIds(String id,List<String> eehList){
        List<EehTree> eehTreeList=eehTreeDao.findEehTreeByPid(id);
        if(eehTreeList!=null&&eehTreeList.size()>0){
            eehTreeList.forEach(s->{
                eehList.add(s.getId());
                this.findChildEehIds(s.getId(),eehList);
            });
        }
    }

    @Transactional(readOnly = true)
    public List<EehTree> findEehSchoolListNoCondition(EehTree eehTree) {
        List<EehTree> eehSchoolList= eehTreeDao.findEehSchoolListNoCondition(eehTree);
        return ObjectKit.buildTree(eehSchoolList,eehTree.getId());
    }
}
