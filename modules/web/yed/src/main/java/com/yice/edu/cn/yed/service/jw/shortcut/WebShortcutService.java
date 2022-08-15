package com.yice.edu.cn.yed.service.jw.shortcut;

import com.yice.edu.cn.common.pojo.jw.shortcut.WebShortcut;
import com.yice.edu.cn.yed.feignClient.jw.shortcut.WebShortcutFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebShortcutService {
    @Autowired
    private WebShortcutFeign webShortcutFeign;

    public WebShortcut findWebShortcutById(String id) {
        return webShortcutFeign.findWebShortcutById(id);
    }

    public WebShortcut saveWebShortcut(WebShortcut webShortcut) {
        //判断是否重复插入
        WebShortcut w = new WebShortcut();
        w.setPermId(webShortcut.getPermId());
        if(webShortcutFeign.findWebShortcutCountByCondition(w)>0)
            return webShortcut;
        return webShortcutFeign.saveWebShortcut(webShortcut);
    }

    public List<WebShortcut> findWebShortcutListByCondition(WebShortcut webShortcut) {
        return webShortcutFeign.findWebShortcutListByCondition(webShortcut);
    }

    public WebShortcut findOneWebShortcutByCondition(WebShortcut webShortcut) {
        return webShortcutFeign.findOneWebShortcutByCondition(webShortcut);
    }

    public long findWebShortcutCountByCondition(WebShortcut webShortcut) {
        return webShortcutFeign.findWebShortcutCountByCondition(webShortcut);
    }

    public void updateWebShortcut(WebShortcut webShortcut) {
        webShortcutFeign.updateWebShortcut(webShortcut);
    }

    public void updateWebShortcutForNotNull(WebShortcut webShortcut) {
        webShortcutFeign.updateWebShortcutForNotNull(webShortcut);
    }

    public void deleteWebShortcut(String id) {
        webShortcutFeign.deleteWebShortcut(id);
    }

    public void deleteWebShortcutByCondition(WebShortcut webShortcut) {
        webShortcutFeign.deleteWebShortcutByCondition(webShortcut);
    }
}
