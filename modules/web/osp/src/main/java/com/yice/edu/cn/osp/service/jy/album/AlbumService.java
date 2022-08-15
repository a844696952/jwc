package com.yice.edu.cn.osp.service.jy.album;

import com.yice.edu.cn.common.pojo.jy.album.Album;
import com.yice.edu.cn.osp.feignClient.jy.album.AlbumFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService {
    @Autowired
    private AlbumFeign albumFeign;
    public List<Album> findAlbumListByCondition(Album album) {
        return albumFeign.findAlbumListByCondition(album);
    }

    public void updateAlbum(Album album) {
        albumFeign.updateAlbum(album);
    }

    public long findAlbumCountByCondition(Album album) {
        return albumFeign.findAlbumCountByCondition(album);
    }
}
