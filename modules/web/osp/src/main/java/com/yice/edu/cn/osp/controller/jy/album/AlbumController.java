package com.yice.edu.cn.osp.controller.jy.album;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.album.Album;
import com.yice.edu.cn.osp.service.jy.album.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;

@RestController
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;
    @PostMapping("/ignore/findAlbumsByParentId/{parentId}")
    public ResponseJson findAlbumsByParentId(@PathVariable String parentId,@RequestBody @Validated Pager pager){
        Album album = new Album();
        album.setParentId(parentId);
        album.setUserId(myId());
        album.setPager(pager);
        List<Album> albums = albumService.findAlbumListByCondition(album);
        long count=albumService.findAlbumCountByCondition(album);
        return new ResponseJson(albums,count);
    }

    @PostMapping("/ignore/updateAlbum")
    public ResponseJson updateAlbum(@RequestBody Album album){
        albumService.updateAlbum(album);
        return new ResponseJson();
    }
}
