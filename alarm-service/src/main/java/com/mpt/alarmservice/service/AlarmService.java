package com.mpt.alarmservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpt.alarmservice.config.UrlConfig;
import com.mpt.alarmservice.dao.AlarmDao;
import com.mpt.alarmservice.dto.AlarmDto;
import com.mpt.alarmservice.dto.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Slf4j
@Service
public class AlarmService {
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private final AlarmDao alarmDao;

    @Value("${mpt.access-token}")
    private String ACCESS_URL;

    public AlarmService(ObjectMapper objectMapper, RestTemplate restTemplate, AlarmDao alarmDao) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
        this.alarmDao = alarmDao;
    }

    public ResponseEntity<String> alarmRegisterResult(UserResponse userResponse, HashMap<String, String> alarmInfo) {
        if (userResponse.getStatus().equals("Verify Success :Token Refreshed")) {
            return alarmDao.postAlarm(userResponse, alarmInfo);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Alarm Register Failed.");
        }
    }

    public ResponseEntity<AlarmDto> hasAlarm(UserResponse userResponse, int goodsId) {
        return ResponseEntity.status(HttpStatus.OK).body(alarmDao.existAlarm(userResponse, goodsId));
    }

    public UserResponse getUserResponse(ResponseEntity<String> response) throws JsonProcessingException {
        return objectMapper.readValue(response.getBody(), UserResponse.class);
    }

    public ResponseEntity<String> responseEntity(HttpHeaders httpHeaders) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", httpHeaders.getFirst("Authorization"));
        HttpEntity<MultiValueMap<String, String>> requestHeader = new HttpEntity(headers);
        String url = UrlConfig.AUTH_SERVER.getUrl() + ACCESS_URL;
        return restTemplate.exchange(url, HttpMethod.GET, requestHeader, String.class);
    }
}
