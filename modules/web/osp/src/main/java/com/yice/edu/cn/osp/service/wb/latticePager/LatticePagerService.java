package com.yice.edu.cn.osp.service.wb.latticePager;


import cn.hutool.core.util.ObjectUtil;
import com.lowagie.text.pdf.PdfReader;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.wb.latticePager.DmPagerBackground;
import com.yice.edu.cn.common.pojo.wb.latticePager.LatticePager;
import com.yice.edu.cn.common.pojo.wb.latticePager.LatticePagerInfo;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.osp.feignClient.wb.latticePager.LatticePagerFeign;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class LatticePagerService {

    @Autowired
    private LatticePagerFeign latticePagerFeign;


    public List<LatticePager> findLatticePagerListByCondition(LatticePager latticePager) {
        return latticePagerFeign.findLatticePagerListByCondition(latticePager);
    }

    public long findLatticePagerCountByCondition(LatticePager latticePager) {
        return latticePagerFeign.findLatticePagerCountByCondition(latticePager);
    }


    /**
     *
     * 解析pdf转图片出去，并且保存到七牛
     * */
    public ResponseJson analysisPdf(MultipartFile multipartFile) throws Exception{
        //文件名后缀
        String suffix = multipartFile.getOriginalFilename().substring(Objects.requireNonNull(multipartFile.
                getOriginalFilename()).lastIndexOf(".") + 1);
        int dot = multipartFile.getOriginalFilename().lastIndexOf('.');
        String fileName =  multipartFile.getOriginalFilename().substring(0,dot);
        String filePath;
        List<String> images = new ArrayList<>();
        if(ObjectUtil.equal(suffix,"pdf")){
            //领导要求存图片到七牛
            filePath = QiniuUtil.commonUploadFile(multipartFile, Constant.Upload.WB_LATTICE_PAGER + suffix);
            PDDocument pdDocument  = PDDocument.load(multipartFile.getInputStream());
            PDFRenderer renderer = new PDFRenderer(pdDocument);
            PdfReader reader = new PdfReader(multipartFile.getInputStream());
            int pages = reader.getNumberOfPages();
            for (int i = 0; i < pages; i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, 300);
                InputStream inputStream = bufferedImageToInputStream(image);
                //解析出来png得也要存七牛
                String forKey = QiniuUtil.commonUploadInputstreamForKey(inputStream,Constant.Upload.WB_LATTICE_PAGER_PAGES
                        .concat(QiniuUtil.getDatePath()).concat("/").concat(fileName)
                        .concat("_").concat(String.valueOf(i+1)).concat(".png"));
                images.add(forKey);
            }
        }else{
            return new ResponseJson(false,"不支持的文件格式");
        }
        return new ResponseJson(filePath,images);
    }


    private InputStream bufferedImageToInputStream(BufferedImage image){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", os);
            return new ByteArrayInputStream(os.toByteArray());
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }

    public LatticePager saveOrUpdateLatticePager(LatticePager latticePager) {
        return latticePagerFeign.saveOrUpdateLatticePager(latticePager);
    }

    public void deleteLatticePager(String id) {
        latticePagerFeign.deleteLatticePager(id);
    }

    public LatticePager lookLatticePagerById(String id) {
        return latticePagerFeign.findLatticePagerById(id);
    }
}
