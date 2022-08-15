package com.yice.edu.cn.tap.feignClient.album;

import com.yice.edu.cn.common.pojo.jy.album.Album;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "jy",path = "/album")
public interface AlbumFeign {
    @PostMapping("/findAlbumListByCondition")
    List<Album> findAlbumListByCondition(Album album);
    @PostMapping("/updateAlbum")
    void updateAlbum(Album album);
}
