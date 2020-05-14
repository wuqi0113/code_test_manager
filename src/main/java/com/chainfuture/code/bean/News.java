package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class News extends Page implements Serializable{

    private static final long serialVersionUID = -4017818719298727195L;
    private Integer id; //id
    private String  title;//标题
    private String  summary;
    private String content;//内容
    private int tokenTotal=0;//奖励token总数
    private int tokenSurplus=0;//剩余token数
    private String createTime;//创建时间
    private String parentPromot;//推广者
    private String promot;//被推广者
    private String publisher;//咨询发布者
    private Integer status;
    private int readAmount=0;
    private String  address;
    private String  assetName;
    private String  amount;
    private String  needPush;
    private String  summaryImg;
    private Integer  newsId;

    //不存数据库
    private Integer news_add;//增加的奖励数量

    public News() {
        super();
    }

    public News(Integer id, String title, String summary, String content, int tokenTotal, int tokenSurplus, String createTime, String parentPromot, String promot, String publisher, Integer status, int readAmount, String address, String assetName, String amount, String needPush, String summaryImg, Integer newsId) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.tokenTotal = tokenTotal;
        this.tokenSurplus = tokenSurplus;
        this.createTime = createTime;
        this.parentPromot = parentPromot;
        this.promot = promot;
        this.publisher = publisher;
        this.status = status;
        this.readAmount = readAmount;
        this.address = address;
        this.assetName = assetName;
        this.amount = amount;
        this.needPush = needPush;
        this.summaryImg = summaryImg;
        this.newsId = newsId;
    }
}
