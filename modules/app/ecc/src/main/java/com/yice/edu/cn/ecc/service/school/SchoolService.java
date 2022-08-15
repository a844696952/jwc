package com.yice.edu.cn.ecc.service.school;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.ecc.feignClient.school.SchoolFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SchoolService {
    @Autowired
    private SchoolFeign schoolFeign;

    public School findSchoolById(String id) {
        School school = schoolFeign.findSchoolById(id);
        List<String> imgList = getImgStr(school.getDescr());
        if(imgList.size() <=0){
            imgList.add("https://res.ycjdedu.com"+school.getSchoolBadge());
        }
        school.setDescrImgs(imgList);
        return school;
    }

    public School saveSchool(School school) {
        return schoolFeign.saveSchool(school);
    }

    public List<School> findSchoolListByCondition(School school) {
        return schoolFeign.findSchoolListByCondition(school);
    }
    public long findSchoolCountByCondition(School school) {
        return schoolFeign.findSchoolCountByCondition(school);
    }

    public void updateSchool(School school) {
        schoolFeign.updateSchool(school);
    }

    public void deleteSchool(String id) {
        schoolFeign.deleteSchool(id);
    }

    public void deleteSchoolByCondition(School school) {
        schoolFeign.deleteSchoolByCondition(school);
    }

    public ResponseJson findSchoolExpireOrSchoolYear(String schoolId){
     return    schoolFeign.findSchoolExpireOrSchoolYear(schoolId);
    }

    /**
     * 将富文本中的图片地址取出来
     * @param htmlStr
     * @return
     */
    public static List<String> getImgStr(String htmlStr) {
        List<String> list = new ArrayList<>();
        if(htmlStr == null || htmlStr ==""){
            return list;
        }
        String img = "";
        Pattern p_image;
        Matcher m_image;
        // String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            // 得到<img />数据
            img = m_image.group();
            // 匹配<img>中的src数据
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                String group = m.group(1);
                if(!group.endsWith(".svg")){
                    list.add(group);
                }
            }
        }
        return list;
    }

}
