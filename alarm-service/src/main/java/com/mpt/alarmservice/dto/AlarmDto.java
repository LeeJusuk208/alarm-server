package com.mpt.alarmservice.dto;

import lombok.Data;

@Data
public class AlarmDto {
    private String email;
    private String goodsId;
    private int targetPrice;

}
