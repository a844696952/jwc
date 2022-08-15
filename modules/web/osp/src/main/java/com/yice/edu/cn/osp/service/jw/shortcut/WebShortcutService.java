package com.yice.edu.cn.osp.service.jw.shortcut;

import com.alicp.jetcache.anno.Cached;
import com.yice.edu.cn.common.pojo.jw.shortcut.WebShortcut;
import com.yice.edu.cn.osp.feignClient.jw.shortcut.WebShortcutFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebShortcutService {
    @Autowired
    private WebShortcutFeign webShortcutFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public WebShortcut findWebShortcutById(String id) {
        return webShortcutFeign.findWebShortcutById(id);
    }

    public WebShortcut saveWebShortcut(WebShortcut webShortcut) {
        return webShortcutFeign.saveWebShortcut(webShortcut);
    }
    @Cached(name="shortcut_list_",key = "'osp_index'",expire = 10000)
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
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
