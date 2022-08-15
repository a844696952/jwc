package com.yice.edu.cn.common.pojo.kqsdk;

import com.yice.edu.cn.common.pojo.Constant;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

public class TeacherInfo {
    static String DEFAULT_PASSWORD = "123456";
    private String cardno = "42";
    public TeacherInfo(String name, String cardno, String pwd, String imgpath) {
        this.setUsername(name);
        this.setCardno(cardno);
        this.setPassword(pwd);
        this.setImgpath(imgpath);
    }

    public TeacherInfo(String name, String cardno,String imgpath) {
        this(name,cardno,DEFAULT_PASSWORD,imgpath);
    }

    public TeacherInfo() {

    }

    public String getCardno() {
        return cardno;
    }
    public TeacherInfo setCardno(String cardno) {
        this.cardno = cardno;
        return this;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public TeacherInfo setUsername(String username) {
        this.username = username;
        return this;
    }
    public String getImgpath() {
        return imgpath;
    }
    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }
    private String password = "123456";
    private String username = "";
    private String imgpath = "";
    private String errMessage;

    public String getErrMessage() {
        return errMessage;
    }

    public TeacherInfo setErrMessage(String errMessage) {
        this.errMessage = errMessage;
        return this;
    }

    public static void bytesFromString(String s,byte[] buffer) {
        if(s == null)
            return;
        byte[] buff = null;
        try {
            buff = s.getBytes("gbk");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            buff = s.getBytes();
        }
        if(buff != null){
            System.arraycopy(buff,0,buffer,0,buff.length);
        }else{
            System.out.println(s+" transto gbk failed");
        }

    }

    private byte[] imgBuffer = null;

    public boolean isDebug() {
        return debug;
    }

    public TeacherInfo setDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    private boolean debug = false;

    public byte[] imgBuffer(){
        if(imgBuffer != null && imgBuffer.length > 0){
            return imgBuffer;
        }
        byte[] buff = new byte[0];
        try {
            if(this.imgpath!=null && this.imgpath.startsWith("/")){
                this.imgpath = this.imgpath.substring(1);
            }
            String imgURL = Constant.RES_PRE+"/"+this.getImgpath();
            if(this.imgpath!=null && this.imgpath.startsWith("http")){
                imgURL = this.imgpath;
            }

            if(!this.debug){
                BufferedImage bi = ImageIO.read(new URL(imgURL));
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bi, "jpg", bos);
                buff = bos.toByteArray();
            }else{
                FileInputStream fis = null;
                File f = null;
                try{
                    fis = new FileInputStream(f = new File(this.imgpath));
                    buff = new byte[(int)f.length()];
                    fis.read(buff);
                }catch (Exception ex){
                    ex.printStackTrace();
                }finally {
                    if(fis!=null){
                        try{
                            fis.close();
                        }catch (Throwable tw){

                        }
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgBuffer = buff;
        return imgBuffer;
    }
    /*public byte[] imgBuffer() {
        if(imgBuffer != null && imgBuffer.length > 0){
            return imgBuffer;
        }
        File f = new File(this.imgpath);
        byte[] buff = new byte[0];
        if(!f.exists()){
            return buff;
        }

        int fl = (int) f.length();
        if(fl > 200 * 1024){
            buff = new byte[1];
            return buff;
        }

        buff = new byte[fl];
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
            fis.read(buff);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            if(fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        imgBuffer = buff;
        return buff;
    }*/
}
