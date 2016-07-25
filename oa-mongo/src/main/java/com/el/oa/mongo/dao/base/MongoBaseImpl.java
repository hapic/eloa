package com.el.oa.mongo.dao.base;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 　　　　　　　　┏┓　　　┏┓+ +
 * 　　　　　　　┏┛┻━━━┛┻┓ + +
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃ ++ + + +
 * 　　　　　　 ████━████ ┃+
 * 　　　　　　　┃　　　　　　　┃ +
 * 　　　　　　　┃　　　┻　　　┃
 * 　　　　　　　┃　　　　　　　┃ + +
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃ + + + +
 * 　　　　　　　　　┃　　　┃　　　　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃ + 　　　　神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃　　+
 * 　　　　　　　　　┃　 　　┗━━━┓ + +
 * 　　　　　　　　　┃ 　　　　　　　┣┓
 * 　　　　　　　　　┃ 　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛+ + + +
 *
 * @User : Hapic
 * @Date : 2016/7/6 21:46
 */

public abstract class MongoBaseImpl<T> implements MongoBase<T> {

     Class<T> clazz;

    public MongoBaseImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Resource(name = "mongoTemplate")
    protected MongoOperations mongo;

    public void insert(T object, String collectionName) {
        this.mongo.insert(object, collectionName);
    }

    public T findOne(Criteria criteria, String collectionName) {
        return (T) this.mongo.findOne(new Query(criteria), clazz, collectionName);
    }

    public List<T> findAll(String collectionName) {
        List result = this.mongo.findAll(clazz, collectionName);
        return result;
    }

    public void update(Criteria criteria, Map<String, Object> params, String collectionName) {
        Update upd = new Update();

        for(String k : params.keySet()){
            upd.set(k,params.get(k));
        }

        this.mongo.upsert(new Query(criteria), upd, clazz, collectionName);
    }



    public void createCollection(String collectionName) {
        this.mongo.createCollection(collectionName);
    }

    public void remove(Criteria criteria, String collectionName) {
        this.mongo.remove(new Query(criteria), clazz, collectionName);
    }

    public T findById(String id, String collectionName) {
        return (T) this.mongo.findOne(new Query(Criteria.where("id").is(id)), clazz, collectionName);
    }


}
