package com.mpt.alarmservice.domain;

import lombok.Builder;
import lombok.Data;

@Data
public class Alarm {
    private String email;
    private int goodsId;
    private int targetPrice;

    @Builder
    public Alarm(String email, int goodsId, int targetPrice) {
        this.email = email;
        this.goodsId = goodsId;
        this.targetPrice = targetPrice;
    }
}
