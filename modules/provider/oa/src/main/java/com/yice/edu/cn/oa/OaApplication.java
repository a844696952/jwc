package com.yice.edu.cn.oa;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.oa.process.ProcessLib;
import com.yice.edu.cn.common.pojo.oa.process.SchoolProcess;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@SpringBootApplication(scanBasePackages = {"com.yice.edu.cn.common","com.yice.edu.cn.oa"})
@Configuration
@EnableFeignClients
@EnableHystrix
public class OaApplication {

    public static void main(String[] args) {
        SpringApplication.run(OaApplication.class, args);
    }

    /**
     * 1.同步mongodb里的processLib表,如果不存在或者版本不匹配就覆盖,同时修改schoolProcess表
     */
    @Bean
    public CommandLineRunner init(final MongoTemplate mot) {
        return strings -> {
            //1)同步mongodb里的processLib表,如果不存在或者版本不匹配就覆盖,同时修改schoolProcess表
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("processData/processType.json");
            String jsonArray = IoUtil.read(inputStream, StandardCharsets.UTF_8);
            List<ProcessLib> processLibs = JSONUtil.toList(JSONUtil.parseArray(jsonArray), ProcessLib.class);
            List<ProcessLib> allProcessLib = mot.findAll(ProcessLib.class);
            inputStream.close();
           processLibs.forEach(processLib -> {
               boolean match = allProcessLib.stream().anyMatch(p -> p.getId().equals(processLib.getId()) && p.getRev()>=processLib.getRev());
               if(!match){
                   InputStream imageIconStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(processLib.getImageIcon().replaceAll("^\\/", ""));
                   InputStream appIconStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(processLib.getAppIcon().replaceAll("^\\/", ""));
                   QiniuUtil.commonUploadInputstreamForKey(imageIconStream,processLib.getImageIcon());
                   QiniuUtil.commonUploadInputstreamForKey(appIconStream,processLib.getAppIcon());
                   mot.upsert(query(where("id").is(processLib.getId())), MongoKit.update(processLib), ProcessLib.class);
                   mot.updateMulti(query(where("processLibId").is(processLib.getId())),
                           Update.update("type",processLib.getType())
                                   .set("imageIcon",processLib.getImageIcon())
                                   .set("appIcon",processLib.getAppIcon())
                                   .set("processForms",processLib.getProcessForms())
                                   .set("timeSpan",processLib.getTimeSpan())
                                   .set("clearLeave",processLib.getClearLeave())
                                    .set("occupancy",processLib.getOccupancy()),
                           SchoolProcess.class
                   );
               }
           });



        };
    }
}
