package com.young.test1.domain.vo;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 读书破万卷，下笔如有神 *
 * 代码反行之，算法记于心 *
 * 项目名: test
 * author: 0YOUNG
 * data:2022/7/27
 */

public class SelfPageInfo {
    private Integer total;
    private Integer pageNum;
    private Integer pageSize;
    private Integer pages;
    private Object data;
    private Integer size;

    public SelfPageInfo() {
    }


    public SelfPageInfo(int intValue, int pageNum, int pageSize, int pages, Object list) {
        this.total = intValue;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pages = pages;
        this.data = list;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
