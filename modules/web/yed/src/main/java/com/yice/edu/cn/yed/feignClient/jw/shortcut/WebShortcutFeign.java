package com.yice.edu.cn.yed.feignClient.jw.shortcut;

import com.yice.edu.cn.common.pojo.jw.shortcut.WebShortcut;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "webShortcutFeign",path = "/webShortcut")
public interface WebShortcutFeign {
    @GetMapping("/findWebShortcutById/{id}")
    WebShortcut findWebShortcutById(@PathVariable("id") String id);
    @PostMapping("/saveWebShortcut")
    WebShortcut saveWebShortcut(WebShortcut webShortcut);
    @PostMapping("/findWebShortcutListByCondition")
    List<WebShortcut> findWebShortcutListByCondition(WebShortcut webShortcut);
    @PostMapping("/findOneWebShortcutByCondition")
    WebShortcut findOneWebShortcutByCondition(WebShortcut webShortcut);
    @PostMapping("/findWebShortcutCountByCondition")
    long findWebShortcutCountByCondition(WebShortcut webShortcut);
    @PostMapping("/updateWebShortcut")
    void updateWebShortcut(WebShortcut webShortcut);
    @PostMapping("/updateWebShortcutForNotNull")
    void updateWebShortcutForNotNull(WebShortcut webShortcut);
    @GetMapping("/deleteWebShortcut/{id}")
    void deleteWebShortcut(@PathVariable("id") String id);
    @PostMapping("/deleteWebShortcutByCondition")
    void deleteWebShortcutByCondition(WebShortcut webShortcut);
}
