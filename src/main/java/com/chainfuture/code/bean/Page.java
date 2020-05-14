package com.chainfuture.code.bean;

public class Page {
    private Integer page = 1; // 默认起始页
    private Integer pageIndex = 0;// 查询起始位置
    private String sort; // 排序的字段
    private String order;  // 排序的方式
    private Integer limit=50;
    private String  where;

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPageIndex() {
        this.pageIndex = (this.page - 1) * this.limit;
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
