package com.el.oa.mapper.common;

import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/4/21.
 */
public interface BaseMapper<T extends Serializable, E> {

    int countByExample(E E);

    int deleteByExample(E E);

    int deleteByPrimaryKey(Object id);

    int insert(T record);

    int insertSelective(T record);

    List<T> selectByExample(E E);

    T getByExample(E E);

    T selectByPrimaryKey(@Param("id") Object id);

    int updateByExampleSelective(@Param("record") T record, @Param("example") E E);

    int updateByExample(@Param("record") T record, @Param("example") E E);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);

    List<T> getPageList(E example);

}
