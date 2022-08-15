package com.yice.edu.cn.osp.controller.jy.resources;

import com.qiniu.http.Response;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.resources.JyCollectionResource;
import com.yice.edu.cn.common.pojo.jy.resources.JyResouces;
import com.yice.edu.cn.common.pojo.jy.resources.JyResoucesType;
import com.yice.edu.cn.common.pojo.jy.resources.JySchoolResouces;
import com.yice.edu.cn.common.util.FileTypeUtil;
import com.yice.edu.cn.common.util.http.HttpKit;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.common.util.zip.ZipUtils;
import com.yice.edu.cn.osp.service.jy.resources.JyResoucesService;
import com.yice.edu.cn.osp.service.jy.resources.JyResoucesTypeService;
import com.yice.edu.cn.osp.service.jy.resources.JySchoolResoucesService;
import com.yice.edu.cn.osp.service.jy.subjectSource.MaterialService;
import com.yice.edu.cn.osp.service.jy.subjectSource.SubjectMaterialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/jyResouces")
@Api(value = "/jyResouces", description = "创建时间：2018-10-29。说明：用于存放我的资源模块")
public class JyResoucesController {
    @Autowired
    private JyResoucesService jyResoucesService;
    @Autowired
    private JyResoucesTypeService jyResoucesTypeService;
    @Autowired
    private JySchoolResoucesService jySchoolResoucesService;
    @Autowired
    private ZipUtils zipUtils;
    @Autowired
    private SubjectMaterialService subjectMaterialService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private FileTypeUtil fileTypeUtil;
    @PostMapping("/saveJyResouces")
    @ApiOperation(value = "保存创建时间：2018-10-29。说明：用于存放我的资源对象", notes = "返回响应对象")
    public ResponseJson saveJyResouces(
            @ApiParam(value = "创建时间：2018-10-29。说明：用于存放我的资源对象", required = true)
            @RequestBody JyResouces jyResouces) {
        //截取文件名后缀
        String suffix = jyResouces.getName().substring(jyResouces.getName().indexOf(".") + 1);
        //不包含文件后缀名
        String fileName = jyResouces.getName().substring(0,jyResouces.getName().lastIndexOf("."));
        long cnt = isFileSameName(fileName,jyResouces.getFileId());
        if(cnt==0){
            jyResouces.setName(fileName);
        }else{
            jyResouces.setName(fileName+"("+cnt+")");
        }
        jyResouces.setSchoolId(mySchoolId());
        jyResouces.setTeacherId(myId());
        jyResouces.setType(fileTypeUtil.setResouceType(suffix));
        if (null == jyResouces.getFileId()) {
            //前期文件夹编号暂时为0，后期做网盘再迭代
            jyResouces.setFileId("0");
        }
        jyResoucesService.saveJyResouces(jyResouces);
        return new ResponseJson(true);
    }


    @PostMapping("/saveListJyResouces")
    @ApiOperation(value = "保存创建时间：2018-10-29。说明：用于存放多个我的资源对象", notes = "返回响应对象")
    public ResponseJson saveListJyResouces(
            @ApiParam(value = "创建时间：2018-10-29。说明：用于存放多个我的资源对象", required = true)
            @RequestBody List<JyResouces> jyResoucesList) {
        //为了处理文件批量上传出现重名情况，采用单个循环上传，因为控件上传的时候，还没有入库，不好判断
        for (JyResouces jyResouces : jyResoucesList) {
            //不包含文件后缀名
            String fileName = jyResouces.getName().substring(0,jyResouces.getName().lastIndexOf("."));
            long cnt = isFileSameName(fileName,"0");
            if(cnt==0){
                jyResouces.setName(fileName);
            }else{
                jyResouces.setName(fileName+"("+cnt+")");
            }
            jyResouces.setSchoolId(mySchoolId());
            jyResouces.setTeacherId(myId());
            if (null == jyResouces.getFileId()) {
                jyResouces.setFileId("0");
            }
            jyResoucesService.saveJyResouces(jyResouces);
        }
        return new ResponseJson(true);
    }



    private String checkFileSize(double len) {
        //单位为byte
        if (len >= 0 && len < 1024) {
            return len + "b";
        } else if (len >= 1024) {
            len = len / 1024;
            if (len >= 1024) {
                return String.format("%.2f", len / 1024) + "M";
            } else {
                return String.format("%.2f", len) + "kb";
            }
        }
        return null;
    }


    @PostMapping("deleteQiniuFile")
    @ApiOperation(value = "创建时间：2018-10-29。说明：用于删除七牛的文件", notes = "返回响应对象")
    public Response deleteQiniuFile(@ApiParam(value = "去删除七牛的文件,需要用到qinliuUrl", required = true) String qinliuUrl) {
        return QiniuUtil.deleteFile(qinliuUrl);
    }

    @PostMapping("/uploadQiniuFile")
    @ApiOperation(value = "创建时间：2018-10-29。说明：上传文件到七牛", notes = "返回资源名称和资源的url")
    public ResponseJson uploadQiniuFile(@ApiParam(value = "上传到七牛的文件file", required = true) MultipartFile file) {
        //文件名后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        int suffixInt = fileTypeUtil.setResouceType(suffix);
        if(suffixInt==0){
            return new ResponseJson(false,"不支持的文件格式");
        }
        //不包含文件后缀名
        String fileName = file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."));
        Map<String, String> map = new HashMap<>();
        long cnt = isFileSameName(fileName,"0");
        map.put("name", file.getOriginalFilename());
        //获取保存文件路径
        String url = QiniuUtil.commonUploadFile(file, Constant.Upload.UPLOAD_NETWORK + suffix);
        map.put("url", url);
        map.put("fileSize", checkFileSize(file.getSize()));
        map.put("fileId", "0");
        map.put("type",String.valueOf(suffixInt));
        return new ResponseJson(map);
    }



    @GetMapping("/update/findJyResoucesById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找创建时间：2018-10-29。说明：用于存放我的资源", notes = "返回响应对象")
    public ResponseJson findJyResoucesById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        JyResouces jyResouces = jyResoucesService.findJyResoucesById(id);
        return new ResponseJson(jyResouces);
    }

    @PostMapping("/update/updateJyResouces")
    @ApiOperation(value = "创建时间：2018-10-29。说明：用于存放我的资源对象", notes = "返回响应对象")
    public ResponseJson updateJyResouces(
            @ApiParam(value = "创建时间：2018-10-29。说明：用于存放我的资源对象,对象属性不为空则修改", required = true)
            @RequestBody JyResouces jyResouces) {
        jyResouces.setTeacherId(myId());
        jyResouces.setSchoolId(mySchoolId());
        int cnt = jyResoucesService.changeResouces(jyResouces);
        if(cnt==0){
            jyResoucesService.updateJyResouces(jyResouces);
            return new ResponseJson();
        }else{
            return new ResponseJson(false, "与其他文件名称相同，请修改");
        }
    }
    @PostMapping("/removeShare")
    @ApiOperation(value = "创建时间：2018-10-29。说明：用于存放我的资源对象", notes = "返回响应对象")
    public ResponseJson removeShare(
            @ApiParam(value = "创建时间：2018-10-29。说明：用于存放我的资源对象,对象属性不为空则修改", required = true)
            @RequestBody JyResouces jyResouces) {
            jyResoucesService.removeShare(jyResouces);
            return new ResponseJson();
    }
    @GetMapping("/look/lookJyResoucesById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找创建时间：2018-10-29。说明：用于存放我的资源", notes = "返回响应对象")
    public ResponseJson lookJyResoucesById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        JyResouces jyResouces = jyResoucesService.findJyResoucesById(id);
        return new ResponseJson(jyResouces);
    }

    @PostMapping("/findJyResoucessByCondition")
    @ApiOperation(value = "根据条件查找创建时间：2018-10-29。说明：用于存放我的资源", notes = "返回响应对象")
    public ResponseJson findJyResoucessByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody JyResouces jyResouces) {
        jyResouces.setTeacherId(myId());
        List<JyResouces> data = jyResoucesService.findJyResoucesListByCondition(jyResouces);
        data.stream().filter(x -> !Objects.equals(x.getName(), "111")).collect(toList());
        long count = jyResoucesService.findJyResoucesCountByCondition(jyResouces);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneJyResoucesByCondition")
    @ApiOperation(value = "根据条件查找单个创建时间：2018-10-29。说明：用于存放我的资源,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneJyResoucesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody JyResouces jyResouces) {
        JyResouces one = jyResoucesService.findOneJyResoucesByCondition(jyResouces);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteJyResouces/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteJyResouces(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        jyResoucesService.deleteJyResouces(id);
        return new ResponseJson();
    }


    @PostMapping("/findJyResoucesListByCondition")
    @ApiOperation(value = "根据条件查找创建时间：2018-10-29。说明：用于存放我的资源列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findJyResoucesListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody JyResouces jyResouces) {
        jyResouces.setSchoolId(mySchoolId());
        List<JyResouces> data = jyResoucesService.findJyResoucesListByCondition(jyResouces);
        return new ResponseJson(data);
    }

    @PostMapping("/findJyResoucess")
    @ApiOperation(value = "返回当前讲师的资源列表和文件夹")
    public ResponseJson findJyResoucess(
            @ApiParam(value = "参数为整个JyResouces对象")
            @Validated
            @RequestBody JyResouces jyResouces) {
        jyResouces.setTeacherId(myId());
        List<JyResouces> data = jyResoucesService.findJyResoucesList(jyResouces);
        long count = jyResoucesService.findJyResoucesCount(jyResouces);
        return new ResponseJson(data, count);
    }

    /**
     * 批量移动文件到相应的文件夹
     *
     * @param jyResouces
     */
    @PostMapping("/updateManyResouces")
    @ApiOperation(value = "返回文件夹和资源个数")
    public ResponseJson updateManyResouces(
            @ApiParam(value = "参数为整个JyResouces对象")
            @Validated
            @RequestBody JyResouces jyResouces) {
        if(jyResouces.getRowDatas().length>0){
            List<JyResoucesType> jyResoucesTypes = jyResoucesTypeService.removeRepartResoucesType(jyResouces);
            jyResouces.setCnt(0);
            jyResoucesTypes.stream().forEach((JyResoucesType e) ->{
                //如果移动的文件夹包含在了目标文件夹
                if(jyResouces.getFileId().equals(e.getId())){
                    jyResouces.setCnt(jyResouces.getCnt()+1);
                }
            });
            if(jyResouces.getCnt()>0){
                return new ResponseJson(false,"移动文件中包含了目标文件夹或其目录，操作失败");
            }else{
                jyResoucesService.updateManyResouces(jyResouces);
                return new ResponseJson();
            }
        }else{
            jyResoucesService.updateManyResouces(jyResouces);
            return new ResponseJson();
        }
    }

    /**
     * 批量删除文件
     *
     * @param jyResouces
     */
    @PostMapping("/deleteManyResouces")
    @ApiOperation(value = "返回文件夹和资源个数")
    public ResponseJson deleteManyResouces(
            @ApiParam(value = "参数为整个JyResouces对象")
            @Validated
            @RequestBody JyResouces jyResouces) {
        jyResoucesService.deleteManyResouces(jyResouces);
        return new ResponseJson();
    }
    /**
     * 批量删除文件
     *
     * @param jyResouces
     */
    @PostMapping("/deleteManyResoucesByFileId")
    @ApiOperation(value = "删除文件和文件夹通过文件夹编号删除文件")
    public ResponseJson deleteManyResoucesByFileId(
            @ApiParam(value = "参数为整个JyResouces对象")
            @Validated
            @RequestBody JyResouces jyResouces) {
        jyResoucesService.deleteManyResoucesByFileId(jyResouces);
        return new ResponseJson();
    }
    /**
     * 批量分享
     *
     * @param jyResouces
     */
    @PostMapping("/insertManyResouces")
    @ApiOperation(value = "批量分享")
    public ResponseJson insertManyResouces(
            @ApiParam(value = "参数为整个JyResouces对象")
            @RequestBody JyResouces jyResouces) {
        jyResoucesService.insertManyResouces(jyResouces);
        return new ResponseJson();
    }

    @PostMapping(value = "/downLoadZipFile")
    @ApiOperation(value = "下载压缩文件")
    public void downLoadZipFile(HttpServletResponse response, @RequestBody JyCollectionResource jyCollectionResource) {
        List<JyCollectionResource> fileList = jyResoucesService.getFileList(jyCollectionResource);//查询数据库中记录
        try {
            zipUtils.down(fileList, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //单个下载文件
    @PostMapping("/downloadFile")
    public void downloadFile(@RequestBody JyResouces jyResouces, HttpServletResponse response) {
        HttpKit.downloadFile(jyResouces.getUrl(), response);
    }

    //分享文件
    @PostMapping("/saveJySchoolResouces")
    @ApiOperation(value = "分享文件", notes = "返回响应对象")
    public ResponseJson saveJySchoolResouces(
            @ApiParam(value = "校本资源对象", required = true)
            @RequestBody JySchoolResouces jySchoolResouces) {
        JyResouces jyResouces = jyResoucesService.findJyResoucesById(jySchoolResouces.getId());
        if (jyResouces.getShareStatus() == 1) {
            return new ResponseJson(false, "该文件已经分享");
        }
        jySchoolResouces.setSchoolId(mySchoolId());
        jySchoolResouces.setTeacherId(myId());
        jySchoolResouces.setFileId("0");
        jySchoolResouces.setResoucesId(jySchoolResouces.getId());
        jyResoucesService.saveJySchoolResouces(jySchoolResouces);
        return new ResponseJson();
    }

    //分享文件
    @PostMapping("/noShare")
    @ApiOperation(value = "取消分享文件", notes = "返回响应对象")
    public ResponseJson noShare(
            @ApiParam(value = "校本资源对象", required = true)
            @RequestBody JyResouces jyResouces) {
        jyResoucesService.noShare(jyResouces);
        return new ResponseJson();
    }
    //删除文件或文件夹
    @PostMapping("/deleteFile")
    public ResponseJson deleteFile(
            @ApiParam(value = "参数为整个JyResouces对象")
            @Validated
            @RequestBody JyResouces jyResouces) {
        jyResoucesService.deleteFile(jyResouces);
        return new ResponseJson();
    }

    //新建文件夹
    @PostMapping("/saveJyResoucesType")
    @ApiOperation(value = "用于新建我的上传里的文件夹", notes = "返回响应对象")
    public ResponseJson saveJyResoucesType(
            @ApiParam(value = "整个jyResoucesType对象", required = true)
            @RequestBody JyResoucesType jyResoucesType) {
        if(null==jyResoucesType.getName().trim() || "".equals(jyResoucesType.getName().trim())){
            return new ResponseJson(false, "请输入文件名");
        }
        jyResoucesType.setTeacherId(myId());
        jyResoucesType.setSchoolId(mySchoolId());
        jyResoucesType.setType(Constant.RESOUCES_TYPE.PERSION_TYPE);
        long cnt = jyResoucesTypeService.findJyResoucesTypeCountByCondition(jyResoucesType);
        if(cnt==0){
            //说明没有重复的文件夹
            JyResoucesType s = jyResoucesTypeService.saveJyResoucesType(jyResoucesType);
            return new ResponseJson(s);
        }else{
            return new ResponseJson(false, "该文件夹已存在");
        }
    }

    //重命名文件夹
    @PostMapping("/renameJyResoucesType")
    @ApiOperation(value = "重命名我的上传文件夹", notes = "返回响应对象")
    public ResponseJson renameJyResoucesType(
            @ApiParam(value = "整个jyResoucesType对象", required = true)
            @RequestBody JyResoucesType jyResoucesType) {
        if(null==jyResoucesType.getName() || "".equals(jyResoucesType.getName())){
            return new ResponseJson(false, "请输入文件名");
        }
        jyResoucesType.setTeacherId(myId());
        jyResoucesType.setSchoolId(mySchoolId());
        jyResoucesType.setType(Constant.RESOUCES_TYPE.PERSION_TYPE);
        int cnt = jyResoucesTypeService.repeatType(jyResoucesType);
        if(cnt==0){
            //说明没有重复的文件夹
            jyResoucesTypeService.updateJyResoucesType(jyResoucesType);
            return new ResponseJson();
        }else{
            return new ResponseJson(false, "与其他文件名称相同，请修改");
        }
    }

    //获取文件名
    @GetMapping("/getJyResoucesType/{id}")
    @ApiOperation(value = "重命名我的上传文件夹", notes = "返回响应对象")
    public ResponseJson getJyResoucesType(
            @ApiParam(value = "整个jyResoucesType对象", required = true)
            @PathVariable String id) {
        JyResoucesType jyResoucesType = jyResoucesTypeService.findJyResoucesTypeById(id);
        return new ResponseJson(jyResoucesType);
    }
    //删除文件夹
    @GetMapping("/deleteJyResoucesType/{id}")
    @ApiOperation(value = "删除文件夹", notes = "返回响应对象")
    public ResponseJson deleteJyResoucesType(
            @ApiParam(value = "整个jyResoucesType对象", required = true)
            @PathVariable("id") String id) {
        System.out.println(id);
        jyResoucesService.deleteJyResoucesType(id);
        return new ResponseJson();
    }

    private long isFileSameName(String fileName,String fileId){
        JyResouces jyResouces = new JyResouces();
        if(fileName.indexOf("(")>0 || fileName.indexOf(")")>0){
            jyResouces.setName(fileName.substring(0,fileName.indexOf("(")));
        }else{
            jyResouces.setName(fileName);
        }
        jyResouces.setTeacherId(myId());
        long count = jyResoucesService.repeatResouces(jyResouces);
        return count;
    }
    /**
     * 去重复
     * @param jyResouces
     * @return
     */
    @PostMapping("/repeatResouces")
    @ApiOperation(value = "去重复")
    public int repeatResouces(
            @ApiParam(value = "参数为整个jyResouces对象")
            @RequestBody JyResouces jyResouces){
        return jyResoucesService.repeatResouces(jyResouces);
    }

}
