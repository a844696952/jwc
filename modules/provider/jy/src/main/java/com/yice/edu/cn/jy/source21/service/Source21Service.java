package com.yice.edu.cn.jy.source21.service;

import cn.hutool.core.date.DateUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.google.gson.Gson;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.TopicType;
import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.jy.questionItem.QuestionItem;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItem;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItemKnowledge;
import com.yice.edu.cn.common.pojo.jy.topics.Topics;
import com.yice.edu.cn.jy.dao.questionItem.IQuestionItemDao;
import com.yice.edu.cn.jy.service.knowledgePoint.KnowledgePointService;
import com.yice.edu.cn.jy.service.questionItem.QuestionItemService;
import com.yice.edu.cn.jy.service.subjectSource.MaterialItemKnowledgeService;
import com.yice.edu.cn.jy.service.subjectSource.MaterialItemService;
import com.yice.edu.cn.jy.service.topics.TopicsService;
import com.yice.edu.cn.jy.source21.constant.APIConstant;
import com.yice.edu.cn.common.pojo.jy.source21.model.common.*;
import com.yice.edu.cn.common.pojo.jy.source21.model.question.Question;
import com.yice.edu.cn.common.pojo.jy.source21.model.question.QuestionType;
import com.yice.edu.cn.common.pojo.jy.source21.model.question.SearchParam;
import com.yice.edu.cn.common.pojo.jy.source21.model.result.APIResult;
import com.yice.edu.cn.jy.source21.util.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class Source21Service {
    @Autowired
    private MaterialItemService materialItemService;
    @Autowired
    private MaterialItemKnowledgeService materialItemKnowledgeService;
    @Autowired
    private TopicsService topicsService;
    @Autowired
    private KnowledgePointService knowledgePointService;
    @Autowired
    private IQuestionItemDao questionItemDao;
    @Autowired
    private APIQuestionService apiQuestionService;
    @CreateCache(expire = 600)
    private Cache<String,String> lock;
    private ExecutorService executor = Executors.newCachedThreadPool();


    private static Map<Integer,String> stageMap;
    private static Map<String,String> subjectToMap;
    private static Map<String,String> subjectNameMap;
    private static Map<String,Integer> mine2stage;
    private static Map<Integer,Integer> stage2mine;
    private static Map<Integer,TopicType> type2mine;
    static
    {
        stageMap = new HashMap<>();//21世纪学段信息
        stageMap.put(APIConstant.STAGE_XIAOXUE,"小学");
        stageMap.put(APIConstant.STAGE_CHUZHONG,"初中");
        stageMap.put(APIConstant.STAGE_GAOZHONG,"高中");
        subjectToMap = getSubjectToMap();
        subjectNameMap = getSubjectNameMap();
        mine2stage = new HashMap<>();//我们平台对21世纪学段的 映射转换
        mine2stage.put("120",APIConstant.STAGE_XIAOXUE);//小学
        mine2stage.put("121",APIConstant.STAGE_CHUZHONG);//初中
        mine2stage.put("122",APIConstant.STAGE_GAOZHONG);//高中
        stage2mine = new HashMap<>();//我们平台对21世纪学段的 映射转换
        stage2mine.put(APIConstant.STAGE_XIAOXUE,120);//小学
        stage2mine.put(APIConstant.STAGE_CHUZHONG,121);//初中
        stage2mine.put(APIConstant.STAGE_GAOZHONG,122);//高中
        type2mine = new HashMap<>();
        //1 = '单选题', 2 = '多选题', 3 = '判断题', 4 = '填空题', 5 = '解答题', 6 = '完形填空题', 7 = '复合题'
        type2mine.put(1, TopicType.SINGLE);
        type2mine.put(2, TopicType.MULTIPLE);
        type2mine.put(3, TopicType.JUDGE);
        type2mine.put(4, TopicType.FILL);
        type2mine.put(5, TopicType.QAA);
        type2mine.put(6, TopicType.CLOZE);
        type2mine.put(7, TopicType.READING);
    }

    /**
     * 获取全部学段的科目
     */
    public Map<Integer,List<Subject>> getSubject() {
        //学段, 1:小学，2:初中，3:高中
        Integer[] stages = {1,2,3};
        //Integer[] stages = {1,2};
        Map<Integer,List<Subject>> subjects = new HashMap<>();
        for(Integer stage:stages){
            subjects.put(stage,apiQuestionService.getSubjects(stage));
        }
        return subjects;
    }

    /**
     * 我们平台通过学段获取21世纪的科目列表
     * @param stage 我们平台的stage 120 121 122
     */
    public List<Subject> getSubject4stage(String stage) {
        return apiQuestionService.getSubjects(mine2stage.get(stage));
    }

    /**
     * 获取所有书籍
     * @return
     */
    public Map<String,List<Book>> getBooks() {
        //获取所有科目
        Map<Integer,List<Subject>> subjects = getSubject();
        //获取所有版本
        List<Version> versions = new ArrayList<>();
        Map<String,String> subjectToName = new HashMap<>();
        subjects.forEach((k,v)->
            v.stream().forEach(subject->{
                subjectToName.put(""+k+subject.getSubjectId(),stageMap.get(k)+subject.getSubjectName());
                versions.addAll(apiQuestionService.getVersions4Question(k,subject.getSubjectId()));
            })
        );
        Log.info("获取所有版本:"+new Gson().toJson(versions));
        //获取所有的册别
        Map<String,List<Book>> books = new HashMap<>();
        versions.stream().forEach(version ->
            books.put(version.getVersionName()+"-"+subjectToName.get(""+version.getStage()+version.getSubjectId()),apiQuestionService.getBooks(version.getVersionId()))
        );
        return books;
    }
    /**
     * 获取指定版本的书籍
     * @return
     */
    public List<Book> getBooksByVersion(String versionId) {
        return apiQuestionService.getBooks(Integer.parseInt(versionId));
    }

    /**
     * 获取章节
     */
    public List<Chapter> getChaptersByBook(String bookId){
        return apiQuestionService.getChapters(Integer.parseInt(bookId));
    }
    /**
     * 获取章节
     */
    public Integer getCategorys(String bookId){
        String key = "21_down_category_"+bookId;
        if(StringUtils.isEmpty(lock.get(key))){
            lock.put(key,"a");//添加锁
            //本地有就不在抓取
            MaterialItem materialItem = new MaterialItem();
            materialItem.setMaterialId(bookId);
            long c = materialItemService.findMaterialItemCountByCondition(materialItem);
            if(c>0){
                lock.remove(key);//释放锁
                return 1;
            }
            executor.submit(()->{
                //通过册别获取章节
                List<Chapter> chapters = apiQuestionService.getChapters(Integer.parseInt(bookId));
                if(chapters==null||chapters.size()<=0){
                    //空章节不进行插入
                    lock.remove(key);//释放锁
                    return ;
                }
                //把顶级节点的父类id 重置成-1
                chapters.forEach(chapter -> chapter.setUpid(-1));
                List<MaterialItem> to = new ArrayList<>();
                this.Recursive4Category(to,chapters,1,String.valueOf(bookId));
                //保存本地
                materialItemService.batchSaveMaterialItem(to);
                //下载知识点关系
                List<MaterialItemKnowledge> materialItemKnowledges = new ArrayList<>();
                to.stream().filter(mi->mi.getLeaf()==1).forEach(mi->{
                    //叶子节点 进行获取对应知识点
                    List<CategoryKnowledge> categoryKnowledges = apiQuestionService.getKnowledgePoints4Category(Integer.parseInt(mi.getId()));
                    materialItemKnowledges.addAll(categoryKnowledges.stream().map(ck->{
                        MaterialItemKnowledge materialItemKnowledge = new MaterialItemKnowledge();
                        materialItemKnowledge.setKnowledgePointId(ck.getKnowledgeId());
                        materialItemKnowledge.setMaterialItemId(mi.getId());
                        materialItemKnowledge.setMaterialItemPath(mi.getPath());
                        return materialItemKnowledge;
                    }).collect(Collectors.toList()));
                });
                materialItemKnowledgeService.batchSaveMaterialItemKnowledge(materialItemKnowledges);
                lock.remove(key);//释放锁
            });
            return 2;
        }else{
            return 1;
        }
    }

    /**
     * 递归处理章节信息 构建转换成我们的记录
     */
    private void Recursive4Category(List<MaterialItem> to,List<Chapter> from,int level,String bookId){
        from.forEach(chapter -> {
           MaterialItem materialItem = new MaterialItem();
           materialItem.setMaterialId(bookId);
           materialItem.setId(chapter.getId().toString());
           materialItem.setName(chapter.getName());
           materialItem.setParentId(chapter.getUpid().toString());
           materialItem.setLevel(level);
           materialItem.setSort(from.indexOf(chapter)+1);
           if(chapter.getChilds()!=null&&chapter.getChilds().size()>0){
               materialItem.setLeaf(2);
               Recursive4Category(to,chapter.getChilds(),level+1,bookId);
           }else
               materialItem.setLeaf(1);
           materialItem.setPath(chapter.getParentpath()+","+chapter.getId().toString());
           to.add(materialItem);
        });
    }

    /**
     * 获取所有的知识点
     * @return
     */
    public void getKnowledge(){
        //获取所有科目
        List<KnowledgePoint> to = new ArrayList<>();
        Map<Integer,List<Subject>> subjects = getSubject();
        subjects.forEach((k,v)->
            v.stream().forEach(subject->{
                Recursive4Knowledge(to,apiQuestionService.getKnowledgePoints4Question(k,subject.getSubjectId()),k);
            })
        );
        //添加到本地库
        knowledgePointService.batchSaveKnowledgePoint4New(to);
    }
    /**
     * 获取所有的知识点
     * @return
     */
    public void synchronizeType(){
        String t = DateUtil.now();
        //获取所有科目
        executor.submit(()->{
            List<QuestionItem> to = new ArrayList<>();
            Map<Integer,List<Subject>> subjects = getSubject();
            subjects.forEach((k,v)->
                    v.stream().forEach(subject->{
                        List<QuestionType> list = apiQuestionService.getQuestionsTypes(k,subject.getSubjectId());
                        if(list!=null){
                            to.addAll(
                                    list.stream().map(qt->{
                                        QuestionItem questionItem = new QuestionItem();
                                        questionItem.setId(String.valueOf(qt.getType()));
                                        questionItem.setName(qt.getTypeName());
                                        questionItem.setSubjectId(subjectToMap.get(k+"_"+subject.getSubjectId()));
                                        questionItem.setSubjectName(subjectNameMap.get(questionItem.getSubjectId()));
                                        questionItem.setSchoolSectionId(String.valueOf(stage2mine.get(k)));
                                        questionItem.setSchoolSection(stageMap.get(k));
                                        questionItem.setCreateTime(t);
                                        return questionItem;
                                    }).collect(Collectors.toList())
                            );
                        }
                    })
            );
            System.out.println(new Gson().toJson(to));
            //添加到本地库
            questionItemDao.batchSaveQuestionItem(to);
        });
    }

    /**
     * 获取所有的知识点
     * @param stage 我们平台的stage 120 121 122
     * @return
     */
    public void downKnowledgeByStage(String stage){
        String key = "21_down_knowledge_"+stage;
        if(StringUtils.isEmpty(lock.get(key))){
            lock.put(key,"a");
            executor.submit(()->{
                //获取所有科目
                List<KnowledgePoint> to = new ArrayList<>();
                List<Subject> subjects = getSubject4stage(stage);
                subjects.stream().forEach(subject->
                        Recursive4Knowledge(to,apiQuestionService.getKnowledgePoints4Question(mine2stage.get(stage),subject.getSubjectId()),mine2stage.get(stage))
                );
                //添加到本地库
                knowledgePointService.batchSaveKnowledgePoint4New(to);
                lock.remove(key);
            });
        }
    }
    /**
     * 递归查询知识点 只保留叶子节点
     * @param to
     * @param from
     * @param stage
     */
    private void Recursive4Knowledge(List<KnowledgePoint> to,List<Knowledge> from,int stage){
        from.stream().forEach(knowledge -> {
            KnowledgePoint knowledgePoint = new KnowledgePoint();
            knowledgePoint.setId(knowledge.getId().toString());
            knowledgePoint.setName(knowledge.getName());
            knowledgePoint.setTopicCount(0);
            knowledgePoint.setTypeId(String.valueOf(stage2mine.get(stage)));
            knowledgePoint.setTypeName(stageMap.get(stage));
            knowledgePoint.setSubjectId(subjectToMap.get(stage+"_"+knowledge.getSubjectId()));
            knowledgePoint.setSubjectName(subjectNameMap.get(knowledgePoint.getSubjectId()));
            knowledgePoint.setCreateTime(DateUtil.now());
            knowledgePoint.setParentId(String.valueOf(knowledge.getUpid()));
            to.add(knowledgePoint);
            if(knowledge.getChilds()!=null&&knowledge.getChilds().size()>0){
                Recursive4Knowledge(to,knowledge.getChilds(),stage);
            }
        });
    }
    /**
     * 21世纪对我们平台做科目映射匹配
     * @return
     */
    private static Map<String,String> getSubjectToMap(){
        Map<String,String> sMap = new HashMap<>();
        //小学
        sMap.put("1_2","130");//语文
        sMap.put("1_3","131");//数学
        sMap.put("1_4","132");//外语
        sMap.put("1_9","133");//品德与生活
        sMap.put("1_5","134");//科学
        sMap.put("1_1010","135");//体育
        sMap.put("1_1006","136");//音乐
        sMap.put("1_1008","137");//美术
        sMap.put("1_14","139");//信息技术

        sMap.put("1_143","199");//跨课
        sMap.put("1_144","116");//劳技
        sMap.put("1_145","118");//专题教育
        //初中
        sMap.put("2_2","150");//语文
        sMap.put("2_3","151");//数学
        sMap.put("2_4","152");//外语
        sMap.put("2_8","153");//历史
        sMap.put("2_10","154");//地理
        sMap.put("2_11","155");//生物
        sMap.put("2_6","156");//物理
        sMap.put("2_7","157");//化学
        sMap.put("2_9","158");//思想品德
        sMap.put("2_1010","159");//体育
        sMap.put("2_1006","160");//音乐
        sMap.put("2_1008","161");//美术
        sMap.put("2_14","162");//信息技术

        sMap.put("2_5","166");//科学
        sMap.put("2_20","167");//历史与社会
        sMap.put("2_21","168");//社会思品
        sMap.put("2_12","169");//综合
        sMap.put("2_99","170");//跨课
        sMap.put("2_16","171");//劳技
        sMap.put("2_18","172");//专题教育
        //高中
        sMap.put("3_2","180");//语文
        sMap.put("3_3","181");//数学
        sMap.put("3_4","182");//外语
        sMap.put("3_8","183");//历史
        sMap.put("3_10","184");//地理
        sMap.put("3_11","185");//生物
        sMap.put("3_6","186");//物理
        sMap.put("3_7","187");//化学
        sMap.put("3_9","188");//政治
        sMap.put("3_1010","189");//体育
        sMap.put("3_1008","190");//美术
        sMap.put("3_14","191");//信息技术
        sMap.put("3_5","18201");//科学
        sMap.put("3_20","18202");//历史与社会
        sMap.put("3_21","18203");//社会思品
        sMap.put("3_12","18204");//综合
        sMap.put("3_99","18205");//跨科
        sMap.put("3_16","18206");//劳技
        sMap.put("3_18","18207");//专题教育
        sMap.put("3_1006","18208");//音乐
        sMap.put("3_19","18209");//基础能力

        return sMap;
    }

    /**
     * 我们平台所有科目的内容
     * @return
     */
    private static Map<String,String> getSubjectNameMap(){
        Map<String,String> sMap = new HashMap<>();
        //小学
        sMap.put("130","语文");//语文
        sMap.put("131","数学");//数学
        sMap.put("132","外语");//外语
        sMap.put("133","品德与生活");//品德与生活
        sMap.put("134","科学");//科学
        sMap.put("135","体育");//体育
        sMap.put("136","音乐");//音乐
        sMap.put("137","美术");//美术
//        sMap.put("138","健康");//健康
        sMap.put("139","信息技术");//信息技术
//        sMap.put("140","校本课程");//校本课程
//        sMap.put("141","班会");//班会
//        sMap.put("142","自习");//自习
        sMap.put("143","跨课");//跨课
        sMap.put("144","劳技");//劳技
        sMap.put("145","专题教育");//专题教育
        //初中
        sMap.put("150","语文");//语文
        sMap.put("151","数学");//数学
        sMap.put("152","外语");//外语
        sMap.put("153","历史");//历史
        sMap.put("154","地理");//地理
        sMap.put("155","生物");//生物
        sMap.put("156","物理");//物理
        sMap.put("157","化学");//化学
        sMap.put("158","思想品德");//思想品德
        sMap.put("159","体育");//体育
        sMap.put("160","音乐");//音乐
        sMap.put("161","美术");//美术
        sMap.put("162","信息技术");//信息技术
//        sMap.put("163","校本课程");//校本课程
//        sMap.put("164","班会");//班会
//        sMap.put("165","自习");//自习
        sMap.put("166","科学");//科学
        sMap.put("167","历史与社会");//历史与社会
        sMap.put("168","社会思品");//社会思品
        sMap.put("169","综合");//综合
        sMap.put("170","跨课");//跨课
        sMap.put("171","劳技");//劳技
        sMap.put("172","专题教育");//专题教育
        //高中
        sMap.put("180","语文");//语文
        sMap.put("181","数学");//数学
        sMap.put("182","外语");//外语
        sMap.put("183","历史");//历史
        sMap.put("184","地理");//地理
        sMap.put("185","生物");//生物
        sMap.put("186","物理");//物理
        sMap.put("187","化学");//化学
        sMap.put("188","政治");//政治
        sMap.put("189","体育");//体育
        sMap.put("190","美术");//美术
        sMap.put("191","信息技术");//信息技术
        sMap.put("18201","科学");//科学
        sMap.put("18202","历史与社会");//历史与社会
        sMap.put("18203","社会思品");//社会思品
        sMap.put("18204","综合");//综合
        sMap.put("18205","跨科");//跨科
        sMap.put("18206","劳技");//劳技
        sMap.put("18207","专题教育");//专题教育
        sMap.put("18208","音乐");//音乐
        sMap.put("18209","基本能力");//基本能力
//        sMap.put("192","通用技术");//通用技术
//        sMap.put("193","校本课程");//校本课程
//        sMap.put("194","班会");//班会
//        sMap.put("195","自习");//自习
        return sMap;
    }

    /**
     * 查询章节对应的知识点
     * @return
     */
    public String getCategoryKowledge(){
        //获取所有的章节 的叶子节点的id
        List<Chapter> chapterList = new ArrayList<>();
        //获取每个章节对应的知识点
        List<CategoryKnowledge> knowledgeList = apiQuestionService.getKnowledgePoints4Category(104343);
        return new Gson().toJson(knowledgeList);
    }

    /**
     * 通过科目获取版本信息
     * @param stage
     * @param subjectId
     * @return
     */
    public List<Version> getVersionsBySubject(String stage, String subjectId) {
        return apiQuestionService.getVersions4Question(mine2stage.get(stage),Integer.parseInt(subjectId));
    }

    /**
     * 查询题目列表
     * @param searchParam
     * @return
     */
    public APIResult<Question> getQuestions(SearchParam searchParam){
        searchParam.setPager(Optional.ofNullable(searchParam.getPager()).orElse(new Pager()));
        return apiQuestionService.getQuestions(new APIRequestParams() {
            {
                this.chapterId = searchParam.getChapterId();//77684
                this.knowledgeId = searchParam.getKnowledgeId();//77684
                this.stage = mine2stage.get(searchParam.getStage().toString());
                this.subjectId = searchParam.getSubjectId();
                this.perPage = searchParam.getPager().getPageSize();
                this.page = searchParam.getPager().getPageNumber();
                this.baseType = searchParam.getBaseType();
                this.difficult = searchParam.getDifficult();
                this.examType = searchParam.getExamType();
                this.type = searchParam.getType();
                this.keyword = searchParam.getKeyword();
            }
        });
    }

    public List<QuestionType> getTypesBySubject(String stage, String subjectId) {
        return apiQuestionService.getQuestionTypes(mine2stage.get(stage),Integer.parseInt(subjectId));
    }

    /**
     * 获取题目详情
     * @param questionId
     */
    public Topics getQuestionDetail(String questionId) {
        Question question = apiQuestionService.getQuestionDetail(questionId);
        if(Optional.ofNullable(question).isPresent()){
            Topics topics = questtionConversion(question);
            //同步到本地库
            topicsService.deleteTopics(topics.getId());
            topicsService.saveTopics(topics);
            return topics;
        }
        return null;
    }

    /**
     * 详情题目替换成我们平台的题目
     */
    private Topics questtionConversion(Question question){
        Topics topics = new Topics();

        //构建题目选项数量和问题数量
        Integer optionNum = 0,questionNum=1;
        //21世纪题型：1='单选题',2='多选题',3='判断题',4='填空题',5='解答题',6='完形填空题',7='复合题'
        if(question.getBaseType() == 1 || question.getBaseType() == 2){
            if(Optional.ofNullable(question.getOptions()).isPresent()&&Optional.ofNullable(question.getOptions().get(0)).isPresent()){
                optionNum = question.getOptions().get(0).size();
            }else optionNum = 4;//默认4个
        }else if(question.getBaseType() == 3){
            optionNum = 2;//判断题2个
        }else if(question.getBaseType() == 4 ){//填空题按答案数量判断
            optionNum = Optional.ofNullable(question.getAnswerJson()).orElse(new ArrayList<>()).size();
            questionNum = optionNum;
        }else if(question.getBaseType() == 6){//完型看选项数量
            optionNum = question.getOptions().get(0).size();
            questionNum = Optional.ofNullable(question.getAnswerJson()).orElse(new ArrayList<>()).size();
        }
        //构建题目内容
        StringBuffer content = new StringBuffer();
        content.append(question.getStem().replaceAll("\\{#blank#\\}\\d+\\{#/blank#\\}","( )"));
        //给选项加<p>标签 <div class="options-li">
//        String regex = "<[p][\\s\\S]*?>";
//        Pattern pattern = Pattern.compile(regex);
        Optional.ofNullable(question.getOptions()).ifPresent(qs->
            qs.forEach(m->{
                if(question.getBaseType()==6){//完型选项
                    content.append("<p>").append(question.getOptions().indexOf(m)+1).append("、 ");
                }
                m.forEach((k,v)->{
                    if(question.getBaseType()==1||question.getBaseType()==2){
                        content.append("<p>").append(k).append(": ").append("<span>").append(v).append("</span>").append("</p>");
                    }else if(question.getBaseType()==6){
                        content.append("<span>").append(k).append(": ").append("<span>").append(v).append("</span>").append("</span>").append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                    }
                });
                if(question.getBaseType()==6){
                    content.append("</p>");
                }
            })
        );
        //转换知识点
        List<KnowledgePoint> knowledgePointList = new ArrayList<>();
        Optional.ofNullable(question.getKnowledge()).ifPresent(knowledgeList ->
            knowledgeList.forEach(knowledge -> {
                KnowledgePoint knowledgePoint = new KnowledgePoint();
                knowledgePoint.setId(knowledge.getId().toString());
                knowledgePoint.setName(knowledge.getName());
                knowledgePointList.add(knowledgePoint);
            })
        );
        //构建答案-----start------>
        if(question.getAnswerJson()==null||question.getAnswerJson().isEmpty()){
            topics.setAnswer(new String[]{});
        }else{
            //21世纪题型：1 = '单选题', 2 = '多选题', 3 = '判断题', 4 = '填空题', 5 = '解答题', 6 = '完形填空题', 7 = '复合题'
            //对AnswerJson进行处理获取
            if(question.getBaseType()==3){
                //判断题 答案转换成对错
                topics.setAnswer(new String[]{question.getAnswerJson().stream().filter(o->StringUtils.isNotEmpty(String.valueOf(o).trim())).map(o -> String.valueOf(o)).collect(Collectors.joining()).equals("0")?"错":"对"});
            }else if(question.getBaseType()==1){
                if(question.getAnswerJson().size()>0){
                    topics.setAnswer(new String[]{String.valueOf(question.getAnswerJson().stream().filter(o->StringUtils.isNotEmpty(String.valueOf(o).trim())).map(o -> String.valueOf(o)).collect(Collectors.joining()))});
                }else
                    topics.setAnswer(new String[]{question.getAnswer()});
            } else if(question.getBaseType()==2)
                topics.setAnswer(new String[]{question.getAnswerJson().stream().filter(o->StringUtils.isNotEmpty(String.valueOf(o).trim())).map(o -> String.valueOf(o)).collect(Collectors.joining())});
            else if(question.getBaseType()==6){
                topics.setAnswer(question.getAnswerJson().stream().map(al->
                    ((ArrayList) al).stream().filter(o->StringUtils.isNotEmpty(String.valueOf(o).trim())).map(o -> String.valueOf(o)).collect(Collectors.joining())
                ).toArray(String[]::new));
            }
            else
                topics.setAnswer(question.getAnswerJson().stream().map(o -> String.valueOf(o).trim()).toArray(String[]::new));
        }
        //<------------------构建答案- end ---------->
        //复合题处理
        if(question.getBaseType()==7&&question.getSubsets().size()>0){
            topics.setChild(question.getSubsets().stream().map(q->questtionConversion(q)).collect(Collectors.toList()));
        }
        //语文英语 问答题 要特殊处理 根据
        final String subject = subjectToMap.get(question.getStage()+"_"+question.getSubjectId());
        if(question.getType()==32&&("130".equals(subject)||"150".equals(subject)||"180".equals(subject))){
            topics.setTypeId(TopicType.CHINESE_WRITING.getId());
            topics.setTypeName(TopicType.CHINESE_WRITING.getName());
        } else if(question.getType()==33&&("132".equals(subject)||"152".equals(subject)||"182".equals(subject))){
            topics.setTypeId(TopicType.ENGLISH_WRITING.getId());
            topics.setTypeName(TopicType.ENGLISH_WRITING.getName());
        } else {
            topics.setTypeId(type2mine.get(question.getBaseType()).getId());
            topics.setTypeName(type2mine.get(question.getBaseType()).getName());
        }
        topics.setId(question.getQuestionId().toString());
        topics.setContent(content.toString());
        topics.setAnnualPeriodId(stage2mine.get(question.getStage()).toString());
        topics.setAnnualPeriodName(stageMap.get(question.getStage()));
        topics.setOptionNum(optionNum.toString());
        topics.setQuestionNum(questionNum.toString());
        topics.setDifficult(String.valueOf(question.getDifficult()));
        topics.setDifficultName(question.getDifficultName());
        topics.setTopicClass(new String[]{String.valueOf(question.getExamType())});
        topics.setTopicClassName(question.getExamTypeName());
        topics.setKnowledges(knowledgePointList);
        topics.setCategories(question.getCategories());//章节id
        topics.setQuestionItemId(String.valueOf(question.getType()));
        topics.setQuestionItemName(question.getTypeName());
        topics.setAnalysis(question.getExplanation());
        topics.setSubjectId(subject);
        topics.setSubjectName(subjectNameMap.get(topics.getSubjectId()));
        topics.setCreateUser("1");
        return topics;
    }
}
