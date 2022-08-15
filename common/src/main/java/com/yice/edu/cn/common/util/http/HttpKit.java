package com.yice.edu.cn.common.util.http;

import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.pojo.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/*
 */
public class HttpKit {
    private final static Logger logger = LoggerFactory.getLogger(HttpKit.class);
    public static void downloadFile(String path, HttpServletResponse response) {
        InputStream inputStream = null;
        ServletOutputStream outputStream = null;
        try {
            URL url = new URL(Constant.RES_PRE + (path.startsWith("/")?path:("/"+path)));
            URLConnection urlConnection = url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setReadTimeout(3000);
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            response.setContentLength(urlConnection.getContentLength());
            response.setCharacterEncoding("utf-8");
            response.addHeader("Content-Type", "application/octet-stream");
            String disposition = urlConnection.getHeaderField("Content-Disposition");
            response.addHeader("Content-Disposition", StrUtil.isNotEmpty(disposition)?disposition.replaceFirst("inline;","attachment;"):"attachment; filename=\"文件.jpg\"; filename*=utf-8' '文件.jpg");
            outputStream = response.getOutputStream();
            int i = -1;
            byte[] b = new byte[1024];
            while ((i = inputStream.read(b)) != -1) {
                outputStream.write(b, 0, i);
                outputStream.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }


    public static byte[] downloadFileToServer(String path) {
        InputStream inputStream = null;
        ServletOutputStream outputStream = null;
        ByteArrayOutputStream bout = null;
        HttpURLConnection urlConnection = null;
        byte[] fileBytes = null;
        try {
            URL url = new URL(Constant.RES_PRE + (path.startsWith("/")?path:("/"+path)));
            URLConnection connection = url.openConnection();
            urlConnection = (HttpURLConnection)connection;
            urlConnection.setDoInput(true);
            urlConnection.setReadTimeout(10000);
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            bout = new ByteArrayOutputStream();
            int i = -1;
            byte[] b = new byte[1024];
            while ((i = inputStream.read(b)) != -1) {
                bout.write(b, 0, i);
                /*outputStream.write(b, 0, i);
                outputStream.flush();*/
            }
            fileBytes=  bout.toByteArray();
            urlConnection.disconnect();
        } catch (IOException e) {
            logger.error("HttpKit图片下载异常："+e);
        } finally {
           if (urlConnection!=null){
               urlConnection.disconnect();
           }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bout != null){
                try {
                    bout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileBytes;
    }

    /**
     *  获取客户端真实Ip，有可能客户端是多个代理后请求的，会出现多个Ip地址，由逗号分隔
     *  取第一个ip地址，为真实地址
     */
    public static String getHttpsServletRequestIp(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        int i = ip.indexOf(",");
        if(i!=-1){
            return ip.substring(0,i);
        }
        return ip;
    }

    //下载图片到服务器
    public static byte[] downloadFileToServerSchoolBadge(String path) {
        InputStream inputStream = null;
        ServletOutputStream outputStream = null;
        ByteArrayOutputStream bout = null;
        HttpURLConnection urlConnection = null;
        byte[] fileBytes = null;
        try {
            URL url = new URL(path);
            URLConnection connection = url.openConnection();
            urlConnection = (HttpURLConnection)connection;
            urlConnection.setDoInput(true);
            urlConnection.setReadTimeout(10000);
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            bout = new ByteArrayOutputStream();
            int i = -1;
            byte[] b = new byte[1024];
            while ((i = inputStream.read(b)) != -1) {
                bout.write(b, 0, i);
                /*outputStream.write(b, 0, i);
                outputStream.flush();*/
            }
            fileBytes=  bout.toByteArray();
            urlConnection.disconnect();
        } catch (IOException e) {
            logger.error("HttpKit图片下载异常："+e);
        } finally {
            if (urlConnection!=null){
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bout != null){
                try {
                    bout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileBytes;
    }
}
