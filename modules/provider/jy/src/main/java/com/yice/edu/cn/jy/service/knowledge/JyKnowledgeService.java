package com.yice.edu.cn.jy.service.knowledge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yice.edu.cn.common.constants.KnowledgeConstants;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jy.knowledge.JyKnowledge;
import com.yice.edu.cn.common.pojo.jy.knowledge.UploadKnowledgeVo;
import com.yice.edu.cn.jy.dao.knowledge.IJyKnowledgeDao;

@Service
public class JyKnowledgeService {
    @Autowired
    private IJyKnowledgeDao jyKnowledgeDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public JyKnowledge findJyKnowledgeById(String id) {
        return jyKnowledgeDao.findJyKnowledgeById(id);
    }
    @Transactional
    public void saveJyKnowledge(JyKnowledge jyKnowledge) {
        jyKnowledge.setId(sequenceId.nextId());
        jyKnowledge.setDel(Constant.DELSIGN.NORMAL);
        jyKnowledgeDao.saveJyKnowledge(jyKnowledge);
        
        if(Integer.parseInt(jyKnowledge.getLevel())<=Integer.parseInt(KnowledgeConstants.TYPE.SUBJECT)) {
        	return;
        }
        
        //修改上级的知识点为非叶子节点
        JyKnowledge jyKnowledgeParent  = jyKnowledgeDao.findJyKnowledgeById(jyKnowledge.getParentId());
        if(jyKnowledgeParent.getLeaf().equals(KnowledgeConstants.LEAF.IS_LEAF)) {
        	JyKnowledge updateKnowledgeParent = new JyKnowledge();
        	updateKnowledgeParent.setId(jyKnowledgeParent.getId());
        	updateKnowledgeParent.setLeaf(KnowledgeConstants.LEAF.IS_NOT_LEAF);
        	jyKnowledgeDao.updateJyKnowledge(updateKnowledgeParent);
        	
        }
    }
    @Transactional(readOnly = true)
    public List<JyKnowledge> findJyKnowledgeListByCondition(JyKnowledge jyKnowledge) {
        return jyKnowledgeDao.findJyKnowledgeListByCondition(jyKnowledge);
    }
    @Transactional(readOnly = true)
    public JyKnowledge findOneJyKnowledgeByCondition(JyKnowledge jyKnowledge) {
        return jyKnowledgeDao.findOneJyKnowledgeByCondition(jyKnowledge);
    }
    @Transactional(readOnly = true)
    public long findJyKnowledgeCountByCondition(JyKnowledge jyKnowledge) {
        return jyKnowledgeDao.findJyKnowledgeCountByCondition(jyKnowledge);
    }
    @Transactional
    public void updateJyKnowledge(JyKnowledge jyKnowledge) {
        jyKnowledgeDao.updateJyKnowledge(jyKnowledge);
    }
    @Transactional
    public void deleteJyKnowledge(String id) {
        jyKnowledgeDao.deleteJyKnowledge(id);
    }
    @Transactional
    public void deleteJyKnowledgeByCondition(JyKnowledge jyKnowledge) {
        jyKnowledgeDao.deleteJyKnowledgeByCondition(jyKnowledge);
    }
    
    /**
     * 删除知识点树节点和其子节点
     * @param jyKnowledge
     */
    @Transactional
    public void deleteByLogic(JyKnowledge jyKnowledge){
    	
    	//判断父节点是否是子节点，并修改父节点的叶子属性
    	JyKnowledge parentKnowledge = jyKnowledgeDao.findJyKnowledgeById(jyKnowledge.getId());
    	String parentId = parentKnowledge.getParentId();
    	JyKnowledge queryNodeKnowledge = new JyKnowledge();
    	queryNodeKnowledge.setParentId(parentId);
    	queryNodeKnowledge.setDel(Constant.DELSIGN.NORMAL);
    	long count = jyKnowledgeDao.findJyKnowledgeCountByCondition(queryNodeKnowledge);
    	if(count<=1) {
    		//修改父节点的叶子属性为叶子结点
    		JyKnowledge updateParentKnowledge = new JyKnowledge();
    		updateParentKnowledge.setId(parentId);
    		updateParentKnowledge.setLeaf(KnowledgeConstants.LEAF.IS_LEAF);
    		jyKnowledgeDao.updateJyKnowledge(updateParentKnowledge);
    	}
    	
    	jyKnowledge.setDel(Constant.DELSIGN.DEL);
    	jyKnowledgeDao.updateJyKnowledge(jyKnowledge);
    	
    	JyKnowledge nodeKnowledge = new JyKnowledge();
    	nodeKnowledge.setParentId(jyKnowledge.getId());
    	nodeKnowledge.setDel(Constant.DELSIGN.DEL);
    	jyKnowledgeDao.updateJyKnowledgeByParentId(nodeKnowledge);
    	
    	
    }

    public List<JwClasses> findJwClassesById(List<String> classId){
    	return jyKnowledgeDao.findJwClassesById(classId);
	}
    
    @Transactional
    public void batchSaveJyKnowledge(List<UploadKnowledgeVo> jyKnowledgesVo,Map<String,String> gradeMap) {
    	
    	jyKnowledgesVo.forEach(vo->{
    		
    		//先判断科目名称是否已经添加
    		JyKnowledge querySubjectKnowledge = new JyKnowledge();
    		querySubjectKnowledge.setLevel(KnowledgeConstants.TYPE.SUBJECT);
    		querySubjectKnowledge.setName(vo.getSubjectName());
    		querySubjectKnowledge.setParentId(gradeMap.get(vo.getGradeName()));
    		querySubjectKnowledge.setDel(Constant.DELSIGN.NORMAL);
    		JyKnowledge jySubject = jyKnowledgeDao.findOneJyKnowledgeByCondition(querySubjectKnowledge);
    		if(jySubject==null) {
    			jySubject = new JyKnowledge();
        		jySubject.setId(sequenceId.nextId());
        		jySubject.setName(vo.getSubjectName());
        		jySubject.setLeaf(StringUtils.isEmpty(vo.getKnowledgeF())?KnowledgeConstants.LEAF.IS_LEAF:KnowledgeConstants.LEAF.IS_NOT_LEAF);
        		jySubject.setType(KnowledgeConstants.TYPE.SUBJECT);
        		jySubject.setDel(Constant.DELSIGN.NORMAL);
        		jySubject.setLevel(KnowledgeConstants.TYPE.SUBJECT);
        		jySubject.setParentId(gradeMap.get(vo.getGradeName()));
        		jyKnowledgeDao.saveJyKnowledge(jySubject);
    			
    		}
    		
    		if(vo.getKnowledgeF()!=null && !"".equals(vo.getKnowledgeF())) {
        		//先判断科目名称是否已经添加
        		JyKnowledge queryKnowledgeF = new JyKnowledge();
        		queryKnowledgeF.setLevel(KnowledgeConstants.TYPE.KNOWLEDGE_THREE);
        		queryKnowledgeF.setName(vo.getKnowledgeF());
        		queryKnowledgeF.setParentId(jySubject.getId());
        		queryKnowledgeF.setDel(Constant.DELSIGN.NORMAL);
        		JyKnowledge jyKnowledgeF = jyKnowledgeDao.findOneJyKnowledgeByCondition(queryKnowledgeF);
        		if(jyKnowledgeF==null) {
        			jyKnowledgeF = new JyKnowledge();
	        		jyKnowledgeF.setId(sequenceId.nextId());
	        		jyKnowledgeF.setName(vo.getKnowledgeF());
	        		jyKnowledgeF.setLeaf(StringUtils.isEmpty(vo.getKnowledgeS())?KnowledgeConstants.LEAF.IS_LEAF:KnowledgeConstants.LEAF.IS_NOT_LEAF);
	        		jyKnowledgeF.setType(KnowledgeConstants.TYPE.KNOWLEDGE_THREE);
	        		jyKnowledgeF.setDel(Constant.DELSIGN.NORMAL);
	        		jyKnowledgeF.setLevel(KnowledgeConstants.TYPE.KNOWLEDGE_THREE);
	        		jyKnowledgeF.setParentId(jySubject.getId());
	        		jyKnowledgeDao.saveJyKnowledge(jyKnowledgeF);	
	        		
        		}
        		
        		if(vo.getKnowledgeS()!=null && !"".equals(vo.getKnowledgeS())) {
        			//判断知识点2名称是否已经添加
            		JyKnowledge queryKnowledgeS = new JyKnowledge();
            		queryKnowledgeS.setLevel(KnowledgeConstants.TYPE.KNOWLEDGE_FOUR);
            		queryKnowledgeS.setName(vo.getKnowledgeS());
            		queryKnowledgeS.setParentId(jyKnowledgeF.getId());
            		queryKnowledgeS.setDel(Constant.DELSIGN.NORMAL);
            		JyKnowledge jyKnowledgeS = jyKnowledgeDao.findOneJyKnowledgeByCondition(queryKnowledgeS);
            		if(jyKnowledgeS==null) {
            			jyKnowledgeS = 	new JyKnowledge();
		        		jyKnowledgeS.setId(sequenceId.nextId());
		        		jyKnowledgeS.setName(vo.getKnowledgeS());
		        		jyKnowledgeS.setLeaf(StringUtils.isEmpty(vo.getKnowledgeT())?KnowledgeConstants.LEAF.IS_LEAF:KnowledgeConstants.LEAF.IS_NOT_LEAF);
		        		jyKnowledgeS.setType(KnowledgeConstants.TYPE.KNOWLEDGE_FOUR);
		        		jyKnowledgeS.setDel(Constant.DELSIGN.NORMAL);
		        		jyKnowledgeS.setLevel(KnowledgeConstants.TYPE.KNOWLEDGE_FOUR);
		        		jyKnowledgeS.setParentId(jyKnowledgeF.getId());
		        		jyKnowledgeDao.saveJyKnowledge(jyKnowledgeS);
            		}
	        		if(vo.getKnowledgeT()!=null && !"".equals(vo.getKnowledgeT())) {
	        			//判断知识点3名称是否已经添加
	            		JyKnowledge queryKnowledgeT = new JyKnowledge();
	            		queryKnowledgeT.setLevel(KnowledgeConstants.TYPE.KNOWLEDGE_FIVE);
	            		queryKnowledgeT.setName(vo.getKnowledgeT());
	            		queryKnowledgeT.setParentId(jyKnowledgeS.getId());
	            		queryKnowledgeT.setDel(Constant.DELSIGN.NORMAL);
	            		JyKnowledge jyKnowledgeT = jyKnowledgeDao.findOneJyKnowledgeByCondition(queryKnowledgeT);
	            		if(jyKnowledgeT==null) {
	            		jyKnowledgeT = new JyKnowledge();
	            		jyKnowledgeT.setId(sequenceId.nextId());
	            		jyKnowledgeT.setName(vo.getKnowledgeT());
	            		jyKnowledgeT.setLeaf(KnowledgeConstants.LEAF.IS_LEAF);
	            		jyKnowledgeT.setType(KnowledgeConstants.TYPE.KNOWLEDGE_FIVE);
	            		jyKnowledgeT.setDel(Constant.DELSIGN.NORMAL);
	            		jyKnowledgeT.setLevel(KnowledgeConstants.TYPE.KNOWLEDGE_FIVE);
	            		jyKnowledgeT.setParentId(jyKnowledgeS.getId());
	            		jyKnowledgeDao.saveJyKnowledge(jyKnowledgeT);
	            		}
	        		}
        		}
    		}
    	});
    }
}
