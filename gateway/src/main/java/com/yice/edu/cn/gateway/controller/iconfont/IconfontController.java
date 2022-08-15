package com.yice.edu.cn.gateway.controller.iconfont;

import cn.hutool.http.HttpUtil;
import com.yice.edu.cn.common.pojo.ResponseJson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequestMapping("/iconfont")
@RestController
public class IconfontController {
    @Value("${iconfont.link}")
    private String link;
    @GetMapping("/getIconfontLink")
    public ResponseJson getIconfontLink(){
        return new ResponseJson(link);
    }

    /**
     * 获取css连接里的css样式表
     * @return
     */
    @GetMapping("/getIconfontCss")
    public ResponseJson getIconfontCss(){
        String res = HttpUtil.get("http:" + link);
        Pattern pattern = Pattern.compile("\\.icon-[a-zA-Z0-9\\-_]+:");
        Matcher matcher = pattern.matcher(res);
        List<String> icons=new ArrayList<String>();
        while(matcher.find()){
            icons.add(matcher.group().replaceAll("\\.|:",""));
        }
        return new ResponseJson(icons);
    }
}
