package com.mpt.alarmservice.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Goods {
    private int id;
    private String name;
    private int del_price;
    private int price;
    private double rating;
    private int rating_count;
    private String img;
    private String url;

    @Builder
    public Goods(int id, String name, int del_price, int price, double rating, int rating_count, String img, String url) {
        this.id = id;
        this.name = name;
        this.del_price = del_price;
        this.price = price;
        this.rating = rating;
        this.rating_count = rating_count;
        this.img = img;
        this.url = url;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", del_price=" + del_price +
                ", price=" + price +
                ", rating=" + rating +
                ", rating_count=" + rating_count +
                ", img='" + img + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
