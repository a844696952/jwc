package com.yice.edu.cn.osp.controller.jy.resources;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.resources.*;
import com.yice.edu.cn.common.util.http.HttpKit;
import com.yice.edu.cn.osp.service.jy.resources.JyCollectionResourceService;
import com.yice.edu.cn.osp.service.jy.resources.JyResoucesService;
import com.yice.edu.cn.osp.service.jy.resources.JyResoucesTypeService;
import com.yice.edu.cn.osp.service.jy.resources.JySchoolResoucesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/jySchoolResouces")
@Api(value = "/jySchoolResouces", description = "校本教案模块接口")
public class JySchoolResoucesController {
    @Autowired
    private JySchoolResoucesService jySchoolResoucesService;
    @Autowired
    private JyResoucesService jyResoucesService;
    @Autowired
    private JyCollectionResourceService jyCollectionResourceService;
    @Autowired
    private JyResoucesTypeService jyResoucesTypeService;
    @Autowired
    private com.yice.edu.cn.common.util.zip.ZipUtils zipUtils;
    @PostMapping("/saveJySchoolResouces")
    @ApiOperation(value = "新增校本教案，在校本教案中，不存在新增，校本教案由讲师分享加入的", notes = "返回响应对象")
    public ResponseJson saveJySchoolResouces(
            @ApiParam(value = "校本资源对象", required = true)
            @RequestBody JySchoolResouces jySchoolResouces) {
        JyResouces jyResouces = jyResoucesService.findJyResoucesById(jySchoolResouces.getId());
        if(jyResouces.getShareStatus()==1){
            return new ResponseJson(false,"该文件已经分享");
        }
        int cnt = jySchoolResoucesService.repeatSchoolResouces(jySchoolResouces);
        if(cnt==0){
            jySchoolResouces.setSchoolId(mySchoolId());
            jySchoolResouces.setTeacherId(myId());
            jySchoolResouces.setFileId("0");
            jySchoolResoucesService.saveJySchoolResouces(jySchoolResouces);
            return new ResponseJson();
        }else{
            return new ResponseJson(false,"与其他文件名称相同，请修改");
        }

    }

    @GetMapping("/update/findJySchoolResoucesById/{id}")
    @ApiOperation(value = "通过校本教案的编号去查询，返回的是校本教案对象", notes = "返回响应对象")
    public ResponseJson findJySchoolResoucesById(
            @ApiParam(value = "查询的参数是id", required = true)
            @PathVariable String id) {
        JySchoolResouces jySchoolResouces = jySchoolResoucesService.findJySchoolResoucesById(id);
        return new ResponseJson(jySchoolResouces);
    }

    @PostMapping("/update/updateJySchoolResouces")
    @ApiOperation(value = "修改校本教案，只用于重命名，其他的参数不用传递", notes = "返回响应对象")
    public ResponseJson updateJySchoolResouces(
            @ApiParam(value = "参数为校本教案整个对象，只需要传递id,和name两个参数", required = true)
            @RequestBody JySchoolResouces jySchoolResouces) {
            int cnt = jySchoolResoucesService.repeatSchoolResouces(jySchoolResouces);
            if(cnt==0){
                jySchoolResoucesService.updateJySchoolResouces(jySchoolResouces);
                return new ResponseJson();
            }else{
                return new ResponseJson(false,"与其他文件名称相同，请修改");
        }
    }

    @GetMapping("/look/lookJySchoolResoucesById/{id}")
    @ApiOperation(value = "查看某个教案详情，参数为编号", notes = "返回响应对象")
    public ResponseJson lookJySchoolResoucesById(
            @ApiParam(value = "参数为id", required = true)
            @PathVariable String id) {
        JySchoolResouces jySchoolResouces = jySchoolResoucesService.findJySchoolResoucesById(id);
        return new ResponseJson(jySchoolResouces);
    }

    @PostMapping("/findJySchoolResoucessByCondition")
    @ApiOperation(value = "分页显示校本教案，固定参数学校编号，后台固定", notes = "返回响应对象")
    public ResponseJson findJySchoolResoucessByCondition(
            @ApiParam(value = "参数是整个对象")
            @Validated
            @RequestBody JySchoolResouces jySchoolResouces) {
        //校本教案，只需要进行学校的限定
        jySchoolResouces.setSchoolId(mySchoolId());
        List<JySchoolResouces> data = jySchoolResoucesService.findJySchoolResoucesListByCondition(jySchoolResouces);
        long count = jySchoolResoucesService.findJySchoolResoucesCountByCondition(jySchoolResouces);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneJySchoolResoucesByCondition")
    @ApiOperation(value = "根据条件查找单条数据", notes = "没有时返回空")
    public ResponseJson findOneJySchoolResoucesByCondition(
            @ApiParam(value = "属性为对象")
            @RequestBody JySchoolResouces jySchoolResouces) {
        JySchoolResouces one = jySchoolResoucesService.findOneJySchoolResoucesByCondition(jySchoolResouces);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteJySchoolResouces/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteJySchoolResouces(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        jySchoolResoucesService.deleteJySchoolResouces(id);
        return new ResponseJson();
    }


    @PostMapping("/findJySchoolResoucesListByCondition")
    @ApiOperation(value = "根据条件查找校本资源列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findJySchoolResoucesListByCondition(
            @ApiParam(value = "参数为整个对象")
            @Validated
            @RequestBody JySchoolResouces jySchoolResouces) {
        jySchoolResouces.setSchoolId(mySchoolId());
        List<JySchoolResouces> data = jySchoolResoucesService.findJySchoolResoucesListByCondition(jySchoolResouces);
        return new ResponseJson(data);
    }

    @PostMapping("/findJySchoolResoucess")
    @ApiOperation(value = "返回当前讲师的资源列表和文件夹")
    public ResponseJson findJySchoolResoucess(
            @ApiParam(value = "参数为整个JySchoolResouces对象")
            @Validated
            @RequestBody JySchoolResouces jySchoolResouces) {
        jySchoolResouces.setTeacherId(myId());
        jySchoolResouces.setSchoolId(mySchoolId());
        List<JySchoolResouces> data = jySchoolResoucesService.findJySchoolResoucesList(jySchoolResouces);
        long count = jySchoolResoucesService.findJySchoolResoucesCount(jySchoolResouces);
        return new ResponseJson(data, count);
    }

    /**
     * 批量移动文件到相应的文件夹
     * @param
     */
    @PostMapping("/updateManySchoolResouces")
    @ApiOperation(value = "返回文件夹和资源个数")
    public ResponseJson updateManySchoolResouces(
            @ApiParam(value = "参数为整个JySchoolResouces对象")
            @RequestBody JyResouces jyResouces){
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
                jySchoolResoucesService.updateManySchoolResouces(jyResouces);
                return new ResponseJson();
            }
        }else{
            jySchoolResoucesService.updateManySchoolResouces(jyResouces);
            return new ResponseJson();
        }


    }

    /**
     * 批量收藏
     *
     */
    @PostMapping("/insertManySchoolResouces")
    @ApiOperation(value = "返回文件夹和资源个数")
    public List<JyCollectionResource> insertManySchoolResouces(
            @ApiParam(value = "参数为整个JySchoolResouces对象")
            @RequestBody JySchoolResouces jySchoolResouces){
        jySchoolResouces.setTeacherId(myId());
        return jySchoolResoucesService.insertManySchoolResouces(jySchoolResouces);
    }
    @PostMapping(value = "/downLoadZipFile")
    @ApiOperation(value = "下载压缩文件")
    public void downLoadZipFile(HttpServletResponse response, @RequestBody JyCollectionResource jyCollectionResource){
        List<JyCollectionResource> fileList = jyResoucesService.getFileList(jyCollectionResource);//查询数据库中记录
        try {
            zipUtils.down(fileList,response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //新建文件夹
    @PostMapping("/saveJyResoucesType")
    @ApiOperation(value = "用于新建我的上传里的文件夹", notes = "返回响应对象")
    public ResponseJson saveJyResoucesType(
            @ApiParam(value = "整个jyResoucesType对象", required = true)
            @RequestBody JyResoucesType jyResoucesType) {
        if(null==jyResoucesType.getName() || "".equals(jyResoucesType.getName())){
            return new ResponseJson(false, "请输入文件名");
        }
        jyResoucesType.setTeacherId(myId());
        jyResoucesType.setSchoolId(mySchoolId());
        jyResoucesType.setType(Constant.RESOUCES_TYPE.SCHOOL_TYPE);
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
        jyResoucesType.setType(Constant.RESOUCES_TYPE.SCHOOL_TYPE);
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
            @PathVariable String id) {
        jySchoolResoucesService.deleteJyResoucesType(id);
        return new ResponseJson();
    }


    //单个下载文件
    @PostMapping("/downloadFile")
    public void downloadFile(@RequestParam("url") String url, HttpServletResponse response) {
        HttpKit.downloadFile(url, response);
    }
    /**
     * 去重复
     * @param jySchoolResouces
     * @return
     */
    @PostMapping("/repeatCollectionResouces")
    @ApiOperation(value = "去重复")
    public int repeatCollectionResouces(
            @ApiParam(value = "参数为整个JyCollectionResource对象")
            @RequestBody JySchoolResouces jySchoolResouces){
        return jySchoolResoucesService.repeatSchoolResouces(jySchoolResouces);
    }

    /**
     * 通过文件夹编号删除校本资源
     * @param jyResouces
     * @return
     */
    @PostMapping("/deleteByResoucesType")
    @ApiOperation(value = "通过文件夹编号删除校本资源")
    public void deleteByResoucesType(
            @ApiParam(value = "参数为整个jyResouces对象")
            @RequestBody JyResouces jyResouces){
        jySchoolResoucesService.deleteByResoucesType(jyResouces);
    }

    /**
     * 通过文件编号删除校本资源
     * @param jyResouces
     * @return
     */
    @PostMapping("/deleteByResoucesId")
    @ApiOperation(value = "通过文件夹编号删除校本资源")
    public void deleteByResoucesId(
            @ApiParam(value = "参数为整个jyResouces对象")
            @RequestBody JyResouces jyResouces){
        jySchoolResoucesService.deleteByResoucesId(jyResouces);
    }

    @PostMapping("/censusSchoolResouces")
    @ApiOperation(value = "统计校本资源的每个类型的总数")
    public ResponseJson censusSchoolResouces(@RequestBody JySchoolResouces jySchoolResouces){
        jySchoolResouces.setSchoolId(mySchoolId());
        return new ResponseJson(jySchoolResoucesService.censusSchoolResouces(jySchoolResouces));
    }
    @PostMapping("/censusSumSchoolResouces")
    @ApiOperation(value = "统计校本资源的时间段的总数")
    public ResponseJson censusSumSchoolResouces(@RequestBody JySchoolResouces jySchoolResouces){
        jySchoolResouces.setSchoolId(mySchoolId());
        return new ResponseJson(jySchoolResoucesService.censusSumSchoolResouces(jySchoolResouces));
    }
    @PostMapping("/censusSumResoucesByDay")
    @ApiOperation(value = "统计校本资源的时间段的每一天的资源总数")
    public ResponseJson censusSumResoucesByDay(@RequestBody JySchoolResouces jySchoolResouces){
        jySchoolResouces.setSchoolId(mySchoolId());
        return new ResponseJson(jySchoolResoucesService.censusSumResoucesByDay(jySchoolResouces));
    }

}
