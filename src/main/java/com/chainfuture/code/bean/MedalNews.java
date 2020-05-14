package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class MedalNews extends Page implements Serializable {
    private static final long serialVersionUID = 7781054643207639963L;

    private  Integer  id;
    private  Integer  medalId;
    private  String   profit;
    private  Integer  newsId;

    public MedalNews() {super();}

    public MedalNews(Integer id, Integer medalId, String profit, Integer newsId) {
        super();
        this.id = id;
        this.medalId = medalId;
        this.profit = profit;
        this.newsId = newsId;
    }
}
