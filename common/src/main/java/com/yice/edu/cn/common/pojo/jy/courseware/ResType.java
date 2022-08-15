package com.yice.edu.cn.common.pojo.jy.courseware;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.yice.edu.cn.common.pojo.jy.courseware.FileType.*;

public enum ResType {






    /**
     * 课件
     */
    COURSEWARE(Collections.singletonList(PPT),"课件",1),

    /**
     * 微课
     */
    SMALL_COURSE(Collections.singletonList(VIDEO),"微课",2),

    /**
     * 素材
     */
    MATERIAL(Arrays.asList(IMAGE,AUDIO,VIDEO),"素材",3),


    /**
     * 试卷
     */
    TEST_PAPER(Arrays.asList(WORD,PDF),"试卷",4),

    /**
     * 课堂检测
     */
    COURSE_TEST(Arrays.asList(WORD,PDF),"检测",5);

    private Collection<FileType> types;
    private String label;
    private int num;

    public int getNum() {
        return num;
    }

    public String getLabel() {
        return label;
    }

    ResType(Collection<FileType> types, String label, int num) {
        this.types = types;
        this.label = label;
        this.num = num;
    }

    public boolean matches(final String ext){
        for (FileType type : this.types) {
            if(Pattern.matches(type.getExts(), ext.toLowerCase())){
                return true;
            }
        }
        return false;
    }

    public boolean matches(final FileType fileType){
        for (FileType type : this.types) {
            if(type.equals(fileType)){
                return true;
            }
        }
        return false;
    }

    @Data
    @AllArgsConstructor
    public static class Label{
        private int num;
        private String key;
        private String label;
        private Set<FileType> fileTypes = new LinkedHashSet<>();

    }


    public static List<Label> getLabels(){
        return Arrays.stream(ResType.values())
                .sorted(Comparator.comparingInt(ResType::getNum))
                .map(e-> new Label(e.num,e.name(),e.label,new LinkedHashSet<FileType>(e.types)))
                .collect(Collectors.toList());
    }



//    public static void main(String[] args) {
//        getLabels().forEach(label1 -> {
//            System.out.println(label1.label);
//            System.out.println(label1.fileTypes);
//            System.out.println();
//        });
//        getLabelMap().forEach((k,v)->{
//            System.out.println(k);
//            System.out.println(v);
//            System.out.println();
//        });
//    }


//    public static ResType matcheOne(final String ext){
//        return Arrays.stream(ResType.values()).filter(t->t.matches(ext)).findAny().orElse(null);
//    }


//    public static void main(String[] args) {
//        String regex = "^(jpg|png|jpeg|gif)$";
//        System.out.println(Pattern.matches(regex, ""));
//
//        System.out.println(ResType.matcheOne("jpg"));
//    }

//    public static void main(String[] args) {
//        final String s = "http://www/baiducom/asd.jpg";
//        final int beginIndex = s.lastIndexOf(".")+1;
//        System.out.println(beginIndex);
//        final String substring = s.substring(beginIndex);
//        System.out.println(substring);
//        System.out.println(IMAGE.matches(substring));
//
//    }
}
