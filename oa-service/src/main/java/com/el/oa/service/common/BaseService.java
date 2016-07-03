package com.el.oa.service.common;


import com.el.oa.common.page.PageList;
import com.el.oa.common.page.PaginatedList;
import com.el.oa.common.utils.ReflectUtils;
import com.el.oa.domain.common.BaseModel;
import com.el.oa.iservice.common.IBaseService;
import com.el.oa.mapper.common.BaseMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/4/21.
 */
@Transactional
public class BaseService<T extends Serializable, E extends BaseModel> implements IBaseService<T, E>, ApplicationContextAware, InitializingBean {
    protected final Log log = LogFactory.getLog(this.getClass());
    private ApplicationContext applicationContext;
    protected BaseMapper<T, E> mapper;

    @Transactional(readOnly = true)
    public int countByExample(E E) {
        return mapper.countByExample(E);
    }

    @Override
    public int deleteByExample(E E) {
        return mapper.deleteByExample(E);
    }

    @Override
    public int deleteByPrimaryKey(Object id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(T record) {
        return mapper.insert(record);
    }

    @Override
    public int insertSelective(T record) {
        return mapper.insertSelective(record);
    }

    @Transactional(readOnly = true)
    public List<T> selectByExample(E E) {
        return mapper.selectByExample(E);
    }

    @Transactional(readOnly = true)
    public T selectByPrimaryKey(Object id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByExampleSelective(T record,E E) {
        return mapper.updateByExampleSelective(record, E);
    }

    @Override
    public int updateByExample(T record,E E) {
        return mapper.updateByExample(record, E);
    }

    @Override
    public int updateByPrimaryKeySelective(T record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(T record) {
        return mapper.updateByPrimaryKey(record);
    }

    @Override
    @Transactional(readOnly = true)
    public PageList<T> getPageList(E example) {
        PageList<T> list = new PaginatedList(example.getPageIndex(), example.getPageSize());
        list.setTotalItem(countByExample(example));
        list.addAll(mapper.getPageList(example));
        return list;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String className = ReflectUtils.getSuperClassGenricType(this.getClass()).getSimpleName();
        className = className.substring(0, 1).toLowerCase() + className.substring(1);
        if (applicationContext.containsBean(className + "Mapper")) {
            mapper = (BaseMapper) applicationContext.getBean(className + "Mapper");
        } else {
            log.error("no bean " + className + ",notice: saf client not need it.");
        }
    }

    public BaseMapper<T, E> getMapper() {
        return mapper;
    }

    public void setMapper(BaseMapper<T, E> mapper) {
        this.mapper = mapper;
    }
}
