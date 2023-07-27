package com.mpt.alarmservice.domain;

import lombok.Builder;
import lombok.Data;

@Data
public class Content {
    private int id;
    private String name;
    private int del_price;
    private int price;
    private double rating;
    private int rating_count;
    private String img;
    private String url;
    private int targetPrice;

    @Builder
    public Content(Goods goods, int targetPrice) {
        this.id = goods.getId();
        this.name = goods.getName();
        this.del_price = goods.getDel_price();
        this.price = goods.getPrice();
        this.rating = goods.getRating();
        this.rating_count = goods.getRating_count();
        this.img = goods.getImg();
        this.url = goods.getUrl();
        this.targetPrice = targetPrice;
    }

}
