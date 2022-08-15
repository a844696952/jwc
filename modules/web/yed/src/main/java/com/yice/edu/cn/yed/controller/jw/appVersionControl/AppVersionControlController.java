package com.yice.edu.cn.yed.controller.jw.appVersionControl;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.appVersionControl.AppVersionControl;
import com.yice.edu.cn.common.pojo.jw.appVersionControl.FileVersion;
import com.yice.edu.cn.common.pojo.jy.handout.FileObj;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.yed.service.jw.appVersionControl.AppVersionControlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import net.dongliu.apk.parser.bean.UseFeature;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@RestController
@RequestMapping("/appVersionControl")
@Api(value = "/appVersionControl",description = "模块")
public class AppVersionControlController {
    @Autowired
    private AppVersionControlService appVersionControlService;

    @PostMapping("/saveAppVersionControl")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response=AppVersionControl.class)
    public ResponseJson saveAppVersionControl(
            @ApiParam(value = "对象", required = true)
            @RequestBody AppVersionControl appVersionControl){
        String s=appVersionControlService.saveAppVersionControl(appVersionControl);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findAppVersionControlById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=AppVersionControl.class)
    public ResponseJson findAppVersionControlById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        AppVersionControl appVersionControl=appVersionControlService.findAppVersionControlById(id);
        return new ResponseJson(appVersionControl);
    }

    @PostMapping("/update/updateAppVersionControl")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateAppVersionControl(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody AppVersionControl appVersionControl){
        appVersionControlService.updateAppVersionControl(appVersionControl);
        return new ResponseJson();
    }

    @GetMapping("/look/lookAppVersionControlById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=AppVersionControl.class)
    public ResponseJson lookAppVersionControlById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        AppVersionControl appVersionControl=appVersionControlService.findAppVersionControlById(id);
        return new ResponseJson(appVersionControl);
    }

    @PostMapping("/findAppVersionControlsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=AppVersionControl.class)
    public ResponseJson findAppVersionControlsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AppVersionControl appVersionControl){
        if("id".equals(appVersionControl.getPager().getSortField())||appVersionControl.getPager().getSortField()==null){
            appVersionControl.getPager().setSortOrder(Pager.DESC);
            appVersionControl.getPager().setSortField("createTime");
        }
        if(appVersionControl.getSearchTimeZone()!=null){
            appVersionControl.setStartTime(appVersionControl.getSearchTimeZone()[0]);
            appVersionControl.setEndTime(appVersionControl.getSearchTimeZone()[1]);
        }

        List<AppVersionControl> data=appVersionControlService.findAppVersionControlListByCondition(appVersionControl);
        long count=appVersionControlService.findAppVersionControlCountByCondition(appVersionControl);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneAppVersionControlByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=AppVersionControl.class)
    public ResponseJson findOneAppVersionControlByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody AppVersionControl appVersionControl){
        AppVersionControl one=appVersionControlService.findOneAppVersionControlByCondition(appVersionControl);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteAppVersionControl/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteAppVersionControl(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        appVersionControlService.deleteAppVersionControl(id);
        return new ResponseJson();
    }


    @PostMapping("/findAppVersionControlListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=AppVersionControl.class)
    public ResponseJson findAppVersionControlListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AppVersionControl appVersionControl){
        List<AppVersionControl> data=appVersionControlService.findAppVersionControlListByCondition(appVersionControl);
        return new ResponseJson(data);
    }

    @PostMapping("/ignore/apkFile")
    public FileVersion uploadHandout(MultipartFile file){
        FileVersion fileVersion = new FileVersion();
        File f = null;
        if(file.equals("")||file.getSize()<=0){
            file = null;
        }else{
            try {
                InputStream ins = file.getInputStream();
                f=new File(file.getOriginalFilename());
                inputStreamToFile(ins, f);
                ApkFile apkFile = new ApkFile(f);
                ApkMeta apkMeta = apkFile.getApkMeta();

                fileVersion.setVersionNumber((double)apkMeta.getVersionCode());
                fileVersion.setVersionName(apkMeta.getVersionName());
                /*System.out.println(apkMeta.getVersionCode());
                System.out.println(apkMeta.getVersionName());
                for (UseFeature feature : apkMeta.getUsesFeatures()) {
                    System.out.println(feature.getName());
                }*/

                File del = new File(f.toURI());
                del.delete();

            }catch (Exception e){

            }
        }
        try{
            fileVersion.setSuccess(true);
            fileVersion.setPath(QiniuUtil.apkFile(file, Constant.Upload.UPLOAD_APP_VERSION));
        }catch (Exception e){
            fileVersion.setSuccess(false);
            fileVersion.setPath("文件格式错误");
        }
        return fileVersion;
    }


    /**
     *
     * @param ins
     * @param file
     *
     * 将MultipartFile 转化成 File类型
     */
    public static void inputStreamToFile(InputStream ins,File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
