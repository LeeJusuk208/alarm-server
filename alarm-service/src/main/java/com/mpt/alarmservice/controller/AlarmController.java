package com.mpt.alarmservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mpt.alarmservice.dto.AlarmDto;
import com.mpt.alarmservice.dto.UserResponse;
import com.mpt.alarmservice.service.AlarmService;
import com.mpt.alarmservice.service.SendEmailService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/alarm")
public class AlarmController {

    private final SendEmailService sendEmailService;
    private final AlarmService alarmService;

    public AlarmController(SendEmailService sendEmailService, AlarmService alarmService) {
        this.sendEmailService = sendEmailService;
        this.alarmService = alarmService;
    }


    @PostMapping("")
    public ResponseEntity<String> createAlarm(@RequestHeader HttpHeaders httpHeaders, @RequestParam(required = false) HashMap<String, String> param) throws JsonProcessingException {
        UserResponse userResponse = alarmService.getUserResponse(alarmService.responseEntity(httpHeaders));
        ResponseEntity<String> result = alarmService.alarmRegisterResult(userResponse, param);
        return ResponseEntity.ok(result.getBody());
    }

    @GetMapping("/{goods_id}")
    public ResponseEntity<AlarmDto> hasAlarm(@RequestHeader HttpHeaders httpHeaders, @PathVariable(required = false) String goods_id) throws JsonProcessingException {
        UserResponse userResponse = alarmService.getUserResponse(alarmService.responseEntity(httpHeaders));
        return alarmService.hasAlarm(userResponse, Integer.parseInt(goods_id));
    }

    @GetMapping("/sendmail")
    public void sendAlarmWithEmails() {
        try {
            sendEmailService.sendHtmlMessage(sendEmailService.findAlarmList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
