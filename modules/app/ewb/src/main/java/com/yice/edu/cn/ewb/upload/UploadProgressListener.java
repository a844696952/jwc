package com.yice.edu.cn.ewb.upload;

import org.apache.commons.fileupload.ProgressListener;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

/**
 * 上传进度条监听
 * @see <a href="https://blog.csdn.net/weixin_37264997/article/details/82622897">引用</a>
 */
@Component
public class UploadProgressListener implements ProgressListener {

    private HttpSession session;

    public void setSession(HttpSession session){
        this.session = session;
        System.out.println("upload_percent 0%--------------------------------------" + LocalDateTime.now());
        session.setAttribute("upload_percent", 0);
    }
    @Override
    public void update(long pBytesRead, long pContentLength, int pItems) {
        int percent = (int)(pBytesRead * 100.0 / pContentLength);
        System.out.println("upload_percent "+percent+"%--------------------------------------" + LocalDateTime.now());
        session.setAttribute("upload_percent", percent);
    }
}
