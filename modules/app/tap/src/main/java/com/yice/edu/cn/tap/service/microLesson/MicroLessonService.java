package com.yice.edu.cn.tap.service.microLesson;

import cn.hutool.core.io.IoUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

@Service
@Slf4j
public class MicroLessonService {

    /**
     * 文件下载
     * @param content 文件内容
     * @param response 输出流
     */
    public void downFile(String content,String fileName, HttpServletResponse response) throws UnsupportedEncodingException {
        if(StringUtils.isNotEmpty(content)){
            byte[] bytes = content.getBytes("utf-8");
            try(OutputStream outputStream=response.getOutputStream()){
                response.reset();
                response.setContentType("application/octet-stream");
                response.setCharacterEncoding("UTF-8");
                response.setHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes(),"UTF-8"));
                IoUtil.write(outputStream,true,bytes);
            }catch (Exception ex){
                log.error(ex.getStackTrace()[0].toString());
            }
        }
    }

}
