package com.el.oa.common.page;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yfzhangsheng on 2015/2/4.
 */
public interface PageList<T extends Serializable> extends WrapperList<T>,Serializable {

    boolean isMiddlePage();

    boolean isLastPage();

    boolean isNextPageAvailable();

    boolean isPreviousPageAvailable();

    int getPageSize();

    void setPageSize(int pageSize);

    int getIndex();

    void setIndex(int index);

    int getTotalItem();

    void setTotalItem(int totalItem);

    int getTotalPage();

    int getStartRow();

    int getEndRow();

    int getNextPage();

    int getPreviousPage();

    boolean isFirstPage();

    boolean isEmpty();
}
