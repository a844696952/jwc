package com.yice.edu.cn.common.mongo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.json.JSONUtil;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.pojo.ArrayMatch;
import com.yice.edu.cn.common.pojo.Pager;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UntypedExampleMatcher;
import org.springframework.data.mongodb.core.query.Update;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class MongoKit {
    private static final Logger logger = LoggerFactory.getLogger(MongoKit.class);
    public static Query query(Object object) {
        Criteria criteria = null;
        try {
            Class<?> clazz = object.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object fieldVal = field.get(object);
                if (fieldVal != null && !"".equals(fieldVal)) {
                    if (criteria == null) {
                        criteria = where(field.getName()).is(fieldVal);
                    } else {
                        criteria.and(field.getName()).is(fieldVal);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        return criteria != null ? new Query(criteria) : new Query();
    }

    public static Query id(String id) {
        return new Query(where("id").is(id));
    }

    public static Update update(Object object) {
        Update update = new Update();
        try {
            List<Field> allFields = getAllField(object);
            for (Field field : allFields) {
                field.setAccessible(true);
                Object fieldVal = field.get(object);
                if (fieldVal != null && !"".equals(fieldVal)) {
                    update.set(field.getName(), fieldVal);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
        return update;
    }

    /**
     *
     * @param object
     * @param updateAll 是否修改除了@Transient注解注释外的所有字段
     * @return
     */
    public static Update update(Object object, boolean updateAll) {
        Update update = new Update();
        try {
            List<Field> allFields = getAllField(object);
            for (Field field : allFields) {
                field.setAccessible(true);
                Object fieldVal = field.get(object);
                if(updateAll){
                    update.set(field.getName(), fieldVal);
                }else{
                    if (fieldVal != null && !"".equals(fieldVal)) {
                        update.set(field.getName(), fieldVal);
                    }
                }

            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
        return update;
    }

    public static Criteria buildCriteria(Object object, Pager pager) {
        Criteria criteria;
        Example example;

        if (pager != null && pager.getLike() != null) {
            Object fieldValue = MongoKit.getFieldValue(object, pager.getLike());
            example = Example.of(object, UntypedExampleMatcher.matchingAll());
            if (fieldValue != null && fieldValue instanceof String) {
                criteria = new Criteria().alike(example).andOperator(new Criteria(pager.getLike()).regex(fieldValue.toString()));
            } else {
                criteria = new Criteria().alike(example);
            }
        } else {
            criteria = new Criteria().alike(Example.of(object, UntypedExampleMatcher.matchingAll()));
        }
        return criteria;
    }

    public static void sortPageInclude(Pager pager, List<AggregationOperation> operations) {
        if (pager != null) {
            if (!pager.getSort().equals(Sort.unsorted())) {
                operations.add(Aggregation.sort(pager.getSort()));
            }
            if (pager.getIncludes() != null) {
                operations.add(Aggregation.project(pager.getIncludes()));
            }
            if (pager.getPaging()) {
                operations.add(Aggregation.skip(pager.getBeginRow()));
                operations.add(Aggregation.limit(pager.getPageSize()));
            }
        }
    }

    public static Object getFieldValue(Object pojo, String fieldName) {
        Class<?> clazz = pojo.getClass();
        try {
            Field declaredField = clazz.getDeclaredField(fieldName);
            declaredField.setAccessible(true);
            Object value = declaredField.get(pojo);
            if (value != null && value instanceof String) {
                declaredField.set(pojo, null);
            }
            return value;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalArgumentException(clazz.getName() + "的字段名称指定错误");
        }
    }


    //以下开始对原生mongodb driver封装
    public static Document buildMatchDocument(Object pojo) {
        Document document = new Document();
        try {
            Method getPager = pojo.getClass().getMethod("getPager");
            Pager pager = (Pager) getPager.invoke(pojo);
            appendDocument("",pojo,pager,document);
            if(logger.isDebugEnabled()){
                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
                logger.debug("\n"+stackTrace[2].getClassName()+"."+stackTrace[2].getMethodName()+" ===> match:"+ JSONUtil.toJsonStr(document));
            }
        } catch (IllegalAccessException  | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
        return document;
    }
    public static Document buildUpdateDocument(Object pojo,boolean updateAll){
        Document document = new Document();
        Document set = new Document("$set",document);
        List<Field> allFields = getAllField(pojo);
        for (Field field : allFields) {
            field.setAccessible(true);
            try {
                if(field.getName().equals("id"))continue;
                Object val = toJson(field.get(pojo));
                if(updateAll){
                    document.append(field.getName(), val);
                }else{
                    if(val!=null&&!val.equals("")){
                        document.append(field.getName(), val);
                    }
                }
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
        return set;
    }
    public static void appendSort(FindIterable<Document> query,Pager pager){
        if(pager!=null){
            if(pager.getSortField()!=null&&pager.getSortOrder()!=null){
                String[] fields = pager.getSortField().split(",");
                String[] orders = pager.getSortOrder().split(",");
                Document doc = new Document();
                for (int i = 0; i < fields.length; i++) {
                    doc.append(fields[i].equals("id")?"_id":fields[i],Pager.ASC.equals(orders[i])?1:-1);
                }
                query.sort(doc);
                if(logger.isDebugEnabled()){
                    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
                    logger.debug("\n"+stackTrace[2].getClassName()+"."+stackTrace[2].getMethodName()+" ===> sort:"+ JSONUtil.toJsonStr(doc));
                }
            }
        }
    }
    public static void appendPage(FindIterable<Document> query,Pager pager){
        if(pager!=null){
            if(pager.getPaging()){
                query.skip(pager.getBeginRow().intValue()).limit(pager.getPageSize());
                if(logger.isDebugEnabled()){
                    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
                    logger.debug("\n"+stackTrace[2].getClassName()+"."+stackTrace[2].getMethodName()+" ===> page: skip:"+pager.getBeginRow()+",limit:"+pager.getPageSize());
                }
            }
        }
    }
    public static void appendProjection(FindIterable<Document> query,Pager pager){
        if(pager!=null){
            Document doc = new Document();
            if(pager.getExcludes()!=null){
                pager.getExcludes().forEach(field-> doc.append(field,0));
                query.projection(doc);
            }else if(pager.getIncludes()!=null){
                for (String field : pager.getIncludes()) {
                    doc.append(field,1);
                }
                query.projection(doc);
            }

            if(logger.isDebugEnabled()&&!doc.isEmpty()){
                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
                logger.debug("\n"+stackTrace[2].getClassName()+"."+stackTrace[2].getMethodName()+" ===> projection:"+ JSONUtil.toJsonStr(doc));
            }
        }
    }
    private static List<Field> getAllField(Object pojo){
        Class<?> clazz = pojo.getClass();
        Field[] fields = clazz.getDeclaredFields();
        List<Field> allFields = CollectionUtil.newArrayList(fields);
        Class<?> superclass;
        while((superclass=clazz.getSuperclass())!=null&&!superclass.getName().equals("java.lang.Object")){
            Field[] superFields = superclass.getDeclaredFields();
            for (Field superField : superFields) {
                if(!alreadyIn(allFields,superField)){
                    allFields.add(superField);
                }
            }
            clazz=superclass;
        }
        allFields.removeIf(new Predicate<Field>() {
            @Override
            public boolean test(Field field) {
                return field.getName().equals("pager")||field.getName().equals("serialVersionUID")||hasTransientAnnotation(field.getDeclaredAnnotations());
            }
        });
        return allFields;
    }
    private static void appendDocument(String path,Object pojo,Pager pager, Document document) throws IllegalAccessException {
        List<Field> allFields = getAllField(pojo);
        for (Field field : allFields) {
            String name = field.getName();
            field.setAccessible(true);
            Object value = field.get(pojo);
            String fieldName=(path + "." +  (name.equals("id")?"_id":name)).replaceAll("^.","");
            if(value!=null){
                if(BeanUtil.isBean(value.getClass())){
                    appendDocument(path + "." + name,value,pager,document);
                }else{
                    if(pager!=null&&pager.getLike()!=null&& pager.getLike().contains(fieldName)){
                        document.append(fieldName, new Document("$regex",".*"+ ReUtil.escape(value.toString())+".*"));
                    }else{
                        document.append(fieldName, value);
                    }
                }
            }



            //范围查询条件
            if(pager!=null&&fieldName.equals(pager.getRangeField())&&pager.getRangeArray()!=null&&pager.getRangeArray().length==2){
                Object[] array = pager.getRangeArray();
                if(array[0]!=null&&array[1]!=null){
                    document.append(fieldName,new Document("$gte",array[0]).append("$lte",array[1]));
                }else if(array[0]==null&&array[1]!=null){
                    document.append(fieldName,new Document("$lte",array[1]));
                }else if(array[0]!=null&&array[1]==null){
                    document.append(fieldName,new Document("$gte",array[0]));
                }
            }

        }
        //数组字段查询
        if(pager != null){
            ArrayMatch[] arrayMatches = pager.getArrayMatches();
            if(arrayMatches !=null&& arrayMatches.length>0){
                for (ArrayMatch arrayMatch : arrayMatches) {
                    if(arrayMatch.isElemMatch()){
                        document.append(arrayMatch.getFieldName(),Document.parse(JSONUtil.toJsonStr(arrayMatch.getFieldValue()).replaceAll("\"id\"","\"_id\"")));
                    }else{
                        document.append(arrayMatch.getFieldName().replaceAll("\\.id$","._id"),arrayMatch.getFieldValue());
                    }
                }
            }
        }

    }

    /**
     * bean对象转document
     * @param pojo
     * @return
     * @throws IllegalAccessException
     */
    public static Document beanToDocument(Object pojo) throws IllegalAccessException {
        Document doc=new Document();
        List<Field> allFields = getAllField(pojo);
        for (Field field : allFields) {
            field.setAccessible(true);
            Object value = field.get(pojo);
            String name = field.getName();
            if(BeanUtil.isBean(value.getClass())){
                doc.append(name,beanToDocument(value));
            }else{
                doc.append(name.equals("id")?"_id": name,value);
            }
        }
        return doc;
    }
    private static boolean hasTransientAnnotation(Annotation[] annotations){
        for (Annotation annotation : annotations) {
            if(annotation.annotationType().equals(Transient.class)){
                return true;
            }
        }
        return false;
    }
    private static boolean alreadyIn(List<Field> allFields,Field field){
        for (Field allField : allFields) {
            if(allField.getName().equals(field.getName())){
                return true;
            }
        }
        return false;
    }
    private static Object toJson(Object o){
        if(isPrimitive(o)||o instanceof String||o==null){
            return o;
        }
        String str = JSONUtil.toJsonStr(o).replaceAll("\"id\":", "\"_id\":");
        if(o.getClass().isArray()||o instanceof Collection){
            return JSONUtil.parseArray(str);
        }else{
            return JSONUtil.parseObj(str);
        }
    }
    private static boolean isPrimitive(Object obj) {
        try {
            return ((Class<?>)obj.getClass().getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }
    //运行改main方法需要将common项目的pom.xml里spring-boot-starter-data-mongodb的scope改成compile
    public static void main(String[] args) {
        /*JwCourse course = new JwCourse();
        course.setName("语文");
        course.setAllUpload(true);
        Pager pager = new Pager();
        pager.setLike("answerSheet.answerSheetStore");
        course.setPager(pager);
        course.setAlias("alias");
        course.setId("12124134");
        AnswerSheet answerSheet = new AnswerSheet();
        answerSheet.setId("9870");
        answerSheet.setAnswerSheetStore("store");
        AnswerSheetData answerSheetData = new AnswerSheetData();
        answerSheetData.setTypeId(1);
        answerSheet.setAnswerSheetDatas(Arrays.asList(answerSheetData));
        course.setAnswerSheet(answerSheet);
        System.out.println(JSONUtil.toJsonStr(buildMatchDocument(course)));*/
//        System.out.println(BeanUtil.isBean(String.class));
        /*S s = new S();
        s.setPager(new Pager());
        s.setSubject("数学");
        s.setScore(100);
        s.setId("99");
        s.setName("teddy");
        JSONUtil.toJsonStr(buildMatchDocument(s));
        System.out.println(update(s,false));
        System.out.println(buildUpdateDocument(s,false));*/
        String str="{\"name\":\"小王哥\",\"id\":\"1753688335901876224\",\"people.id\":\"123\"}";
        System.out.println(str.replaceAll("\"id\"","\"_id\"").replaceAll("\\.id\"","._id\""));
    }
    public static class P{
        private String id;
        private String name;
        private Integer age;
        private Pager pager;

        public Pager getPager() {
            return pager;
        }

        public void setPager(Pager pager) {
            this.pager = pager;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
    public static class S extends P{
        private String subject;
        private Integer score;

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }
}