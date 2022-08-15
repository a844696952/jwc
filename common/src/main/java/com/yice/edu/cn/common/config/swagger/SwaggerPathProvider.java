package com.yice.edu.cn.common.config.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import springfox.documentation.PathProvider;

@Component
public class SwaggerPathProvider implements PathProvider {
    @Value("${spring.application.name}")
    private String serviceName;
    @Override
    public String getApplicationBasePath() {
        return "/"+serviceName;
    }

    @Override
    public String getOperationPath(String s) {
        return s;
    }

    @Override
    public String getResourceListingPath(String s, String s1) {
        return s;
    }
}
