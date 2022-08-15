package com.yice.edu.cn.jy.service.mySpace.album;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.jy.album.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UntypedExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class AlbumService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;

    public Album findAlbumById(String id) {
        return mot.findOne(query(where("id").is(id)), Album.class);
    }
    public void saveAlbum(Album album) {
        mot.insert(album);
    }
    public List<Album> findAlbumListByCondition(Album album) {
        Example<Album> example = Example.of(album, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        if(album.getPager()!=null&&album.getPager().getPaging()){
            query.with(album.getPager());
        }else if(album.getPager()!=null){
            query.with(album.getPager().getSort());
        }
        return mot.find(query, Album.class);
    }
    public Album findOneAlbumByCondition(Album album) {
        Example<Album> example = Example.of(album, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        return mot.findOne(query, Album.class);
    }
    public long findAlbumCountByCondition(Album album) {
        Example<Album> example = Example.of(album, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        return mot.count(query, Album.class);
    }
    public void updateAlbum(Album album) {
        mot.updateFirst(query(where("id").is(album.getId())), MongoKit.update(album),Album.class);
    }
    public void deleteAlbum(String id) {
        mot.remove(query(where("id").is(id)),Album.class);
    }
    public void deleteAlbumByCondition(Album album) {
        Example<Album> example = Example.of(album, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        mot.remove(query, Album.class);
    }
    public void batchSaveAlbum(List<Album> albums){
        mot.insertAll(albums);
    }
}
