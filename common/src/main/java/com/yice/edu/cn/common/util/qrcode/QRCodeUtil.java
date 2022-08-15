package com.yice.edu.cn.common.util.qrcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.util.oss.QiniuUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Hashtable;
import java.util.Random;
import java.util.UUID;

/**
 * 二维码工具类
 */
@Component
public class QRCodeUtil {
    private static final String CHARSET = "utf-8";
    private static final String FORMAT = "PNG";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 300;
    // LOGO宽度
    private static final int LOGO_WIDTH = 60;
    // LOGO高度
    private static final int LOGO_HEIGHT = 60;

    private static BufferedImage createImage(String content, String logoPath, String title,String title2, boolean needCompress) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE,
                hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height + 80, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height + 80; y++) {
                try {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                } catch (Exception e) {
                    image.setRGB(x, y, 0xFFFFFFFF);
                }
            }
        }
        // 插入图片
        QRCodeUtil.insertImage(image, logoPath, title,title2, needCompress);
        return image;
    }

    /**
     * 生成长宽都为300的二维码，没有logo和title
     * @param content 二维码内容
     * @return
     * @throws Exception
     */
    private static BufferedImage createImageNoLogoAndTitle(String content) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE,
                hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height , BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                try {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                } catch (Exception e) {
                    image.setRGB(x, y, 0xFFFFFFFF);
                }
            }
        }
        // 插入图片
        return image;
    }

    /**
     * 生成长宽都为300的二维码，没有logo和title
     * @param content 二维码内容
     * @return 七牛云文件地址
     * @throws Exception
     */
    public static String encodeNoLogoAndTitle(String content) throws Exception {
        BufferedImage image = QRCodeUtil.createImageNoLogoAndTitle(content);
        File file = File.createTempFile("temp", "png");
        ImageIO.write(image, FORMAT, file);
        String fileName = QiniuUtil.commonUploadFile(file, Constant.Upload.ZC_QRCODE);
        System.out.println(fileName);
        return fileName;
    }


    /**
     * 删除白边
     */
    private static BitMatrix deleteWhite(BitMatrix matrix) {
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;

        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1]))
                    resMatrix.set(i, j);
            }
        }
        return resMatrix;
    }

    /**
     * 插入LOGO
     *
     * @param source       二维码图片
     * @param logoPath     LOGO图片地址
     * @param needCompress 是否压缩
     * @throws Exception
     */
    private static void insertImage(BufferedImage source, String logoPath, String title, String title2, boolean needCompress) throws Exception {
        int width = 60;
        int height = 60;
        if (logoPath == null || "".equals(logoPath)) {
            // 插入LOGO
            Graphics2D graph = source.createGraphics();
            if (StringUtils.isNotBlank(title)) {
                int size = getFontSizeBychineseLength(title);
                graph.setColor(Color.BLACK);
                graph.setFont(new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, size)); //字体、字型、字号
                graph.drawString(title, 25, 320);
                graph.dispose();
            }
            
            Graphics2D graph2 = source.createGraphics();

            if (StringUtils.isNotBlank(title2)) {
                int size = getFontSizeBychineseLength(title2);
                graph2.setColor(Color.BLACK);
                graph2.setFont(new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, size)); //字体、字型、字号
                graph2.drawString(title2, 25, 350);
                graph2.dispose();
            }

        } else {
            File file = new File(logoPath);
            if (!file.exists()) {
                throw new Exception("logo file not found.");
            }
            Image src = ImageIO.read(new File(logoPath));
            if (needCompress) { // 压缩LOGO
                if (width > LOGO_WIDTH) {
                    width = LOGO_WIDTH;
                }
                if (height > LOGO_HEIGHT) {
                    height = LOGO_HEIGHT;
                }
                Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(image, 0, 0, null); // 绘制缩小后的图
                g.dispose();
                src = image;
            }
            // 插入LOGO
            Graphics2D graph = source.createGraphics();
            int x = (QRCODE_SIZE - width) / 2;
            int y = (QRCODE_SIZE - height) / 2;
            graph.drawImage(src, x, y, width, height, null);
            if (StringUtils.isNotBlank(title)) {
                int size = getFontSizeBychineseLength(title);
                graph.setColor(Color.BLACK);
                graph.setFont(new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, size)); //字体、字型、字号
                graph.drawString(title, 25, 320);
            }
            
            if (StringUtils.isNotBlank(title2)) {
                int size = getFontSizeBychineseLength(title2);
                graph.setColor(Color.BLACK);
                graph.setFont(new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, size)); //字体、字型、字号
                graph.drawString(title2, 25, 350);
            }
            graph.dispose();
        }

    }

    private static int getFontSizeBychineseLength(String chinese){
        int length = 0;
        for(int i = 0; i < chinese.length(); i++)
        {
            int ascii = Character.codePointAt(chinese, i);
            if(ascii >= 0 && ascii <=255){
                length++;
            }else{
                length += 2;
            }
        }
        if(length>20){
            return 16;
        }else{
            return 24;
        }
    }

    /**
     * 生成二维码(内嵌LOGO)
     * 二维码文件名随机，文件名可能会有重复
     *
     * @param content      内容
     * @param logoPath     LOGO地址
     *                     存放目录
     * @param needCompress 是否压缩LOGO
     * @throws Exception
     */
    public static String encode(String content, String logoPath, String title,String title2, boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, logoPath, title,title2, needCompress);
        File file = File.createTempFile("temp", "png");
        ImageIO.write(image, FORMAT, file);
        String fileName = QiniuUtil.commonUploadFile(file, Constant.Upload.ZC_QRCODE);
        System.out.println(fileName);
        return fileName;

    }

    public static String visitorEncode(String content, String logoPath, String title,String title2, boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, logoPath, title,title2, needCompress);
        File file = File.createTempFile("temp", "png");
        ImageIO.write(image, FORMAT, file);
        String fileName = QiniuUtil.commonUploadFile(file, Constant.Upload.VISITOR_QRCODE);
        System.out.println(fileName);
        return fileName;

    }

    public static byte[] visitorEncode(String content, String logoPath, String title,String title2) throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, logoPath, title,title2, true);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, FORMAT, baos);
        return baos.toByteArray();

    }

    public static byte[] encode(String content, String logoPath, String title,String title2) throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, logoPath, title,title2, true);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, FORMAT, baos);
        return baos.toByteArray();

    }


    /**
     * 解析二维码
     *
     * @param file 二维码图片
     * @return
     * @throws Exception
     */
    public static String decode(File file) throws Exception {
        BufferedImage image;
        image = ImageIO.read(file);
        if (image == null) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
        result = new MultiFormatReader().decode(bitmap, hints);
        String resultStr = result.getText();
        return resultStr;
    }

    /**
     * 解析二维码
     *
     * @param path 二维码图片地址
     * @return
     * @throws Exception
     */
    public static String decode(String path) throws Exception {
        return QRCodeUtil.decode(new File(path));
    }


    public static void main(String[] args) throws Exception {
        String text = "http://www.baidu.com";
        
        //不含Logo
        String encode = QRCodeUtil.encode(text, null, "title1", "title2", true);
        System.out.println(encode);
    }
}