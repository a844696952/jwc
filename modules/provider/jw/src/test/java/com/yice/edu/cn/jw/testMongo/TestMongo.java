package com.yice.edu.cn.jw.testMongo;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMongo {
    @Autowired
    private MongoTemplate mot;

    @Test
    public void testField(){
        Document first = mot.getCollection("pen").find(new Document("_id", new ObjectId("5b83589c8fe55b1680c857a2"))).first();
        System.out.println(first);
    }
}
