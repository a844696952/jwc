package com.yice.edu.cn.common.util.zip;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jy.resources.JyCollectionResource;
import com.yice.edu.cn.common.pojo.xw.visitorManage.Visitor;
import com.yice.edu.cn.common.pojo.xw.zc.AssetsResQrcode;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class ZipUtils {

    //根据文件链接把文件下载下来并且转成字节码
    public byte[] getImageFromURL(String urlPath) {
        byte[] data = null;
        InputStream is = null;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(Constant.RES_PRE + (urlPath.startsWith("/") ? urlPath : ("/" + urlPath)));
            URLConnection urlConnection = url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setReadTimeout(3000);
            urlConnection.connect();
            is = urlConnection.getInputStream();
            data = readInputStream(is);
        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException");
        } catch (IOException e) {
            System.out.println("IOException");
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                System.out.println("IOException");
            }
        }
        return data;
    }


    public byte[] readInputStream(InputStream is) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = -1;
        try {
            while ((length = is.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
            baos.flush();
        } catch (IOException e) {
            System.out.println("IOException");
        }
        byte[] data = baos.toByteArray();
        try {
            is.close();
            baos.close();
        } catch (IOException e) {
            System.out.println("IOException");
        }
        return data;
    }

    public void down(List<JyCollectionResource> fileList, HttpServletResponse response) throws IOException {
        try {
            String filename = new String("wxTempConfig.zip".getBytes("UTF-8"), "ISO8859-1");//控制文件名编码
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ZipOutputStream zos = new ZipOutputStream(bos);
            ZipUtils s = new ZipUtils();
            int idx = 1;
            for (JyCollectionResource j : fileList) {
                zos.putNextEntry(new ZipEntry(j.getName() + j.getUrl().substring(j.getUrl().lastIndexOf("."), j.getUrl().length())));
                byte[] bytes = s.getImageFromURL(j.getUrl());
                zos.write(bytes, 0, bytes.length);
                zos.closeEntry();
                idx++;
            }
            zos.close();
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName=" + filename);// 设置文件名
            OutputStream os = response.getOutputStream();
            os.write(bos.toByteArray());
            os.close();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void downQrCode(List<AssetsResQrcode> fileList, HttpServletResponse response) throws IOException {
        try {
            String filename = new String("wxTempConfig.zip".getBytes("UTF-8"), "ISO8859-1");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ZipOutputStream zos = new ZipOutputStream(bos);
            ZipUtils s = new ZipUtils();
            int idx = 1;
            for (AssetsResQrcode j : fileList) {
                zos.putNextEntry(new ZipEntry(j.getSerialNumber() + ".png"));
                byte[] bytes = j.getQrCode();
                zos.write(bytes, 0, bytes.length);
                zos.closeEntry();
                idx++;
            }
            zos.close();
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName=" + filename);// 设置文件名
            response.addHeader("Content-Length", String.valueOf(bos.toByteArray().length));
            OutputStream os = response.getOutputStream();
            os.write(bos.toByteArray());
            os.close();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void downVisitorCode(Visitor visitor, HttpServletResponse response) throws IOException {
        try {
            String filename = new String("wxTempConfig.zip".getBytes("UTF-8"), "ISO8859-1");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ZipOutputStream zos = new ZipOutputStream(bos);
            ZipUtils s = new ZipUtils();
            zos.putNextEntry(new ZipEntry(visitor.getId() + ".png"));
            byte[] bytes = visitor.getQrCode();
            zos.write(bytes, 0, bytes.length);
            zos.closeEntry();
            zos.close();
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName=" + filename);// 设置文件名
            response.addHeader("Content-Length", String.valueOf(bos.toByteArray().length));
            OutputStream os = response.getOutputStream();
            os.write(bos.toByteArray());
            os.close();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}