package com.mpt.alarmservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpt.alarmservice.config.UrlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/api/alarm")
public class AlarmController {

    private final RestTemplate restTemplate;

    public AlarmController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("")
    public ResponseEntity<String> createAlarm(@RequestHeader HttpHeaders httpHeaders, @RequestParam HashMap<String, String> param) {

        if (param.isEmpty()) {
            param.put("alarmtype", "yesterday");
            param.put("setprice", "1");
            param.put("curprice", "yesterday");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization",httpHeaders.getFirst("Authorization"));
        HttpEntity<MultiValueMap<String, String>> requestHeader = new HttpEntity(headers);
        String url = UrlConfig.AUTH_SERVER.getUrl() + "/api/access_token_info";

        ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.GET, requestHeader, String.class);
        return ResponseEntity.ok(result.getBody());
    }
}
