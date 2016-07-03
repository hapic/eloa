package com.el.oa.common.page;

import java.io.Serializable;
import java.util.List;


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
