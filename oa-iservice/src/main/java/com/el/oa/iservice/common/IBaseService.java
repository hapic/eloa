package com.el.oa.iservice.common;


import com.el.oa.common.page.PageList;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/4/21.
 */
public interface IBaseService<T extends Serializable,E> {

    int countByExample(E E);

    int deleteByExample(E E);

    int deleteByPrimaryKey(Object id);

    int insert(T record);

    int insertSelective(T record);

    List<T> selectByExample(E E);

    T selectByPrimaryKey(Object id);

    int updateByExampleSelective(T record, E E);

    int updateByExample(T record, E E);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);

    PageList<T> getPageList(E example);
}
