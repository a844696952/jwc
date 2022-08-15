package com.yice.edu.cn.gateway.ignore;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties(prefix = "api.login")
@Component
@Data
public class ApiLoginIgnore {
    private List<String> ignores;
}
