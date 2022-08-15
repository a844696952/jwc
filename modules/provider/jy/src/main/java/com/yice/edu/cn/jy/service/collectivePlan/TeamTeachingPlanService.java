package com.yice.edu.cn.jy.service.collectivePlan;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.collectivePlan.JyPrepareLessonsDiscuss;
import com.yice.edu.cn.common.pojo.jy.collectivePlan.JyPrepareLessonsDiscussReply;
import com.yice.edu.cn.common.pojo.jy.collectivePlan.TeacherCollection;
import com.yice.edu.cn.common.pojo.jy.collectivePlan.TeamTeachingPlan;
import com.yice.edu.cn.common.pojo.jy.prepareLessons.ItemPackage;
import com.yice.edu.cn.common.pojo.jy.prepareLessons.LessonsFile;
import com.yice.edu.cn.jy.dao.collectivePlan.IJyPrepareLessonsDiscussDao;
import com.yice.edu.cn.jy.dao.collectivePlan.IJyPrepareLessonsDiscussReplyDao;
import com.yice.edu.cn.jy.dao.collectivePlan.ITeacherCollectionDao;
import com.yice.edu.cn.jy.dao.collectivePlan.ITeamTeachingPlanDao;
import com.yice.edu.cn.jy.dao.prepareLessons.ILessonsFileDao;
import com.yice.edu.cn.jy.dao.prepareLessons.ItemPackageDao;
import com.yice.edu.cn.jy.dao.resources.IJyResoucesDao;
import com.yice.edu.cn.jy.wordGenerator.AnalysisMEditorHTML;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TeamTeachingPlanService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private ITeamTeachingPlanDao teamTeachingPlanDao;
    @Autowired
    private ILessonsFileDao lessonsFileDao;
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private IJyResoucesDao jyResoucesDao;
    @Autowired
    private SequenceId sequenceId;

    @Autowired
    private ITeacherCollectionDao iTeacherCollectionDao;
    @Autowired
    private ItemPackageDao itemPackageDao;
    @Autowired
    private IJyPrepareLessonsDiscussDao jyPrepareLessonsDiscussDao;
    @Autowired
    private IJyPrepareLessonsDiscussReplyDao iJyPrepareLessonsDiscussReplyDao;





    @Transactional(readOnly = true)
    public TeamTeachingPlan findOneTeamTeachingPlanByCondition(TeamTeachingPlan teamTeachingPlan) {
        return teamTeachingPlanDao.findOneTeamTeachingPlanByCondition(teamTeachingPlan);
    }
    @Transactional(readOnly = true)
    public long findTeamTeachingPlanCountByCondition(TeamTeachingPlan teamTeachingPlan) {
        return teamTeachingPlanDao.findTeamTeachingPlanCountByCondition(teamTeachingPlan);
    }

    @Transactional
    public void deleteTeamTeachingPlanByCondition(TeamTeachingPlan teamTeachingPlan) {
        teamTeachingPlanDao.deleteTeamTeachingPlanByCondition(teamTeachingPlan);
    }
    @Transactional
    public void batchSaveTeamTeachingPlan(List<TeamTeachingPlan> teamTeachingPlans){
        teamTeachingPlans.forEach(teamTeachingPlan -> teamTeachingPlan.setId(sequenceId.nextId()));
        teamTeachingPlanDao.batchSaveTeamTeachingPlan(teamTeachingPlans);
    }


    /**
     * 复用个人备课中的Service
     */
    public ResponseJson findTeamTeachingPlanListByCondition(TeamTeachingPlan teachingPlan) {

        List<TeamTeachingPlan> plans = teamTeachingPlanDao.findTeamTeachingPlanListByCondition(teachingPlan);
        teachingPlan.setPager(null);
        List<TeamTeachingPlan> plansSize = teamTeachingPlanDao.findTeamTeachingPlanListByCondition(teachingPlan);
        return new ResponseJson(plans, plansSize.size());
    }


    public TeamTeachingPlan editTeamTeachingPlanById(String id) {
        TeamTeachingPlan teachingPlan = teamTeachingPlanDao.findTeamTeachingPlanById(id);
        if(teachingPlan!=null) {
            ItemPackage itemPackage=new ItemPackage();
            itemPackage.setTeachingPlanId(teachingPlan.getTeacherPlanId());
            List<ItemPackage> itemPackages = itemPackageDao.findItemPackageByCondition(itemPackage);
            if(itemPackages!=null) teachingPlan.setItemPackages(itemPackages);
        }
        return teachingPlan;
    }

/*    public TeamTeachingPlan lookTeamTeachingPlanById(String id) {
        TeamTeachingPlan teachingPlan = teamTeachingPlanDao.findTeamTeachingPlanById(id);
        //通过id获取作业
        if(teachingPlan!=null) {
            String itemPackageId = teachingPlan.getItemPackageIds();
            if(itemPackageId!=null) {
                List<String> itemPackageIds = Arrays.asList(itemPackageId);
                Query query = new Query();
                query.addCriteria(Criteria.where("id").in(itemPackageIds));
                List<Homework> homework = mot.find(query, Homework.class);
                teachingPlan.setHomeworks(homework);
            }
        }

        return teachingPlan;
    }*/

    public TeamTeachingPlan lookTeamTeachingPlanById(String id) {
        TeamTeachingPlan teachingPlan = findTeamTeachingPlanById(id);
        //通过id获取题包
        if(teachingPlan!=null) {
            if(StringUtils.isNotBlank(teachingPlan.getItemPackageIds())) {
                List<ItemPackage> itemPackages = itemPackageDao.findItemPackageByIds(new ArrayList<>(Arrays.asList(teachingPlan.getItemPackageIds().split(","))));
                if(itemPackages!=null) teachingPlan.setItemPackages(itemPackages);
            }
        }


        return teachingPlan;
    }



    @Transactional
    public String saveTeamTeachingPlan(TeamTeachingPlan teachingPlan) {
        teachingPlan.setId(sequenceId.nextId());
        //遍历教案中的文件新增的中间文件表
        if(teachingPlan.getLessonsFiles().size()>0){
            List<LessonsFile>  lessonsFiles = teachingPlan.getLessonsFiles();
            lessonsFiles.forEach(e->{
                e.setTeachingPlanId(teachingPlan.getId());
            } );
            //将新生成的教案里的文件存放在教案表
            lessonsFileDao.batchSaveLessonsFile(lessonsFiles);
        }
        teamTeachingPlanDao.saveTeamTeachingPlan(teachingPlan);
        return teachingPlan.getId();
    }

    @Transactional
    public ResponseJson updateTeamTeachingPlan(TeamTeachingPlan teachingPlan) {
        if(Objects.equals(" ",teachingPlan.getItemPackageIds())){
            teachingPlan.setItemPackageIds("");
        }
        teachingPlan.setUpdateTime(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        List<LessonsFile> lessonsFiles = teachingPlan.getLessonsFiles();
        int successRow = teamTeachingPlanDao.updateTeamTeachingPlan(teachingPlan);

        if(lessonsFiles.size()>0) {
            LessonsFile le=new LessonsFile();
            le.setTeachingPlanId(teachingPlan.getId());
            lessonsFiles.forEach(leFile->{
                leFile.setTeachingPlanId(teachingPlan.getId());
            });
            //删除关联文件
            lessonsFileDao.deleteLessonsFileByCondition(le);
            lessonsFileDao.batchSaveLessonsFile(lessonsFiles);
        }
        //将修改次数+1
        TeacherCollection teacherCollection = new TeacherCollection ();
        teacherCollection.setTeacherPlanId(teachingPlan.getId());
        teacherCollection.setCollectionPlanId(teachingPlan.getCollectivePlanId());
        iTeacherCollectionDao.updateModifyCount(teacherCollection);
        return successRow==1?new ResponseJson(true, "更新成功"):new ResponseJson(false, "更新失败");

    }


    @Transactional
    public ResponseJson deleteTeamTeachingPlan(String id) {
        //删除资源
        LessonsFile lessonsFile = new LessonsFile();
        lessonsFile.setTeachingPlanId(id);
        lessonsFileDao.deleteLessonsFileByCondition(lessonsFile);
        //解除教案与讨论组关系
        TeacherCollection teacherCollection = new TeacherCollection();
        teacherCollection.setTeacherPlanId(id);
        iTeacherCollectionDao.deleteTeacherCollectionByCondition(teacherCollection);
        //删除教案下的评论
        JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss = new JyPrepareLessonsDiscuss();
        jyPrepareLessonsDiscuss.setTeachingPlanId(id);
        jyPrepareLessonsDiscussDao.deleteJyPrepareLessonsDiscussByCondition(jyPrepareLessonsDiscuss);
        //删除教案下的回复
        JyPrepareLessonsDiscussReply jyPrepareLessonsDiscussReply = new JyPrepareLessonsDiscussReply();
        jyPrepareLessonsDiscussReply.setTeachingPlanId(id);
        iJyPrepareLessonsDiscussReplyDao.deleteJyPrepareLessonsDiscussReplyByCondition(jyPrepareLessonsDiscussReply);
        //删除教案
        int successRow = teamTeachingPlanDao.deleteTeamTeachingPlan(id);
        return successRow==1?new ResponseJson(true, "删除成功"):new ResponseJson(true, "删除失败");
    }

    @Transactional
    public ResponseJson deleteLessonsFile(String id) {

        int successRow = lessonsFileDao.deleteLessonsFile(id);
        jyResoucesDao.deleteJyResouces(id);
        return successRow==1?new ResponseJson(true, "删除成功"):new ResponseJson(true, "删除失败");
    }

    //查询 集体备课讨论组下的 已完成 和 正在讨论的教案
    @Transactional
    public ResponseJson findTeachingPlanList(TeamTeachingPlan teachingPlan) {
        List<TeamTeachingPlan> plans = teamTeachingPlanDao.findTeachingPlanList(teachingPlan);
        teachingPlan.setPager(null);
        List<TeamTeachingPlan> plansSize = teamTeachingPlanDao.findTeachingPlanList(teachingPlan);
        return new ResponseJson(plans,null,plansSize.size());
    }

    // 查询教师的个人教案列表（未被选择提交的教案）
    @Transactional
    public ResponseJson findTeachingPlanListNotChosen(TeamTeachingPlan teachingPlan) {
        List<TeamTeachingPlan> plans = teamTeachingPlanDao.findTeachingPlanListNotChosen(teachingPlan);
        return new ResponseJson(plans);
    }

    //查询当前教师 是否为主备人
    @Transactional
    public List<TeamTeachingPlan> findTeachingPlanIsPrincipal(TeamTeachingPlan teachingPlan) {
        List<TeamTeachingPlan> plans = teamTeachingPlanDao.findTeachingPlanIsPrincipal(teachingPlan);
        return plans;
    }

    @Transactional
    public TeamTeachingPlan findTeamTeachingPlanById(String id) {
        //获取一条集体教案
        TeamTeachingPlan teachingPlan = teamTeachingPlanDao.findTeamTeachingPlanById(id);
        return teachingPlan;
    }

    @Transactional
    public TeamTeachingPlan findTeachingPlanById(String id) {
        //获取一条个人教案
        TeamTeachingPlan teachingPlan = teamTeachingPlanDao.findTeachingPlanById(id);
        return teachingPlan;
    }

    @Transactional
    public ResponseEntity<byte[]> downTeamloadTeachingPlan(String id) {
        TeamTeachingPlan entity = editTeachingPlanById(id);
        ResponseEntity<byte[]> responseEntity = null;
        if(entity!=null) {
            String teachTarget=entity.getTeachTarget();// 教学目标
            String tearchPoint=entity.getTearchPoint();// 教学要点
            String tearchMethod=entity.getTearchMethod();// 教学方法
            String teachDifficulty=entity.getTeachDifficulty();// 教学难点
            String tearchProcess=entity.getTearchProcess();// 教学过程
            StringBuilder sb =new StringBuilder(String.format("<title>%s</title>", entity.getCourseName()));
            sb.append(StringUtils.isNotBlank(teachTarget)?String.format("%s"+teachTarget, "<h1>教学目标</h1>"):"");
            sb.append(StringUtils.isNotBlank(tearchPoint)?String.format("%s"+tearchPoint, "<h1>教学要点</h1>"):"");
            sb.append(StringUtils.isNotBlank(tearchMethod)?String.format("%s"+tearchMethod, "<h1>教学方法</h1>"):"");
            sb.append(StringUtils.isNotBlank(teachDifficulty)?String.format("%s"+teachDifficulty, "<h1>教学难点</h1>"):"");
            sb.append(StringUtils.isNotBlank(tearchProcess)?String.format("%s"+tearchProcess, "<h1>教学过程</h1>"):"");
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=\"" + entity.getCourseName() + ".docx\"");
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            try {
                AnalysisMEditorHTML.writeContextToStream(sb.toString(), bout);
                byte[] b = bout.toByteArray();
                responseEntity=new ResponseEntity<byte[]>(b, headers, HttpStatus.OK);

            } finally {
                if(bout!=null) {
                    try {
                        bout.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        log.error("关闭输出流IO异常 {}",e.getMessage());
                    }
                }
            }
            //teachingPlanDao.updateDownloadCount(id);
        }

        return responseEntity;
    }

    public TeamTeachingPlan editTeachingPlanById(String id) {
        TeamTeachingPlan teachingPlan = teamTeachingPlanDao.findTeamTeachingPlanById(id);
        if(teachingPlan!=null&&teachingPlan.getLessonsFiles()!=null) {
            List<String> collect = teachingPlan.getLessonsFiles().stream().map(f->f.getId()).collect(Collectors.toList());
            teachingPlan.setLessonsFileIds(String.join(",", collect));
            teachingPlan.setLessonsFiles(null);
        }
        return teachingPlan;
    }


}
