package com.yice.edu.cn.jy.controller.mySpace.album;

import com.yice.edu.cn.common.pojo.jy.album.Album;
import com.yice.edu.cn.jy.service.mySpace.album.AlbumService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/album")
@Api(value = "/album",description = "相册模块")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    @GetMapping("/findAlbumById/{id}")
    @ApiOperation(value = "通过id查找相册", notes = "返回相册对象")
    public Album findAlbumById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return albumService.findAlbumById(id);
    }

    @PostMapping("/saveAlbum")
    @ApiOperation(value = "保存相册", notes = "返回相册对象")
    public Album saveAlbum(
            @ApiParam(value = "相册对象", required = true)
            @RequestBody Album album){
        albumService.saveAlbum(album);
        return album;
    }

    @PostMapping("/findAlbumListByCondition")
    @ApiOperation(value = "根据条件查找相册列表", notes = "返回相册列表")
    public List<Album> findAlbumListByCondition(
            @ApiParam(value = "相册对象")
            @RequestBody Album album){
        return albumService.findAlbumListByCondition(album);
    }
    @PostMapping("/findAlbumCountByCondition")
    @ApiOperation(value = "根据条件查找相册列表个数", notes = "返回相册总个数")
    public long findAlbumCountByCondition(
            @ApiParam(value = "相册对象")
            @RequestBody Album album){
        return albumService.findAlbumCountByCondition(album);
    }

    @PostMapping("/updateAlbum")
    @ApiOperation(value = "修改相册", notes = "相册对象必传")
    public void updateAlbum(
            @ApiParam(value = "相册对象,对象属性不为空则修改", required = true)
            @RequestBody Album album){
        albumService.updateAlbum(album);
    }

    @GetMapping("/deleteAlbum/{id}")
    @ApiOperation(value = "通过id删除相册")
    public void deleteAlbum(
            @ApiParam(value = "相册对象", required = true)
            @PathVariable String id){
        albumService.deleteAlbum(id);
    }
    @PostMapping("/deleteAlbumByCondition")
    @ApiOperation(value = "根据条件删除相册")
    public void deleteAlbumByCondition(
            @ApiParam(value = "相册对象")
            @RequestBody Album album){
        albumService.deleteAlbumByCondition(album);
    }
    @PostMapping("/findOneAlbumByCondition")
    @ApiOperation(value = "根据条件查找单个相册,结果必须为单条数据", notes = "返回单个相册,没有时为空")
    public Album findOneAlbumByCondition(
            @ApiParam(value = "相册对象")
            @RequestBody Album album){
        return albumService.findOneAlbumByCondition(album);
    }


}
