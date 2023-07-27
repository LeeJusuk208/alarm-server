package com.mpt.alarmservice.service;

import com.mpt.alarmservice.dao.GoodsDao;
import com.mpt.alarmservice.domain.Alarm;
import com.mpt.alarmservice.domain.Content;
import com.mpt.alarmservice.domain.Goods;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Slf4j
@Service
@AllArgsConstructor
public class SendEmailService {
    @Autowired
    private final GoodsDao goodsDao;
    @Autowired
    private final JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;


    public void sendSimpleMessage(List<Alarm> alarmList){

        HashMap<String, ArrayList<Content>> mailList = getMailList(alarmList);
        for (String email : mailList.keySet()) {

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(email);
            simpleMailMessage.setSubject("test");
            String mailText = "";
            ArrayList<Content> contentsList = mailList.get(email);
            for (Content content : contentsList) {
                mailText += "goods_id: "+ content.getId() + " ";
                mailText += "goods_price: "+ content.getPrice() + " ";
                mailText += "\n";
            }

            simpleMailMessage.setText(mailText);
            //메일 발송
            mailSender.send(simpleMailMessage);
        }

    }

    public void sendHtmlMessage(List<Alarm> alarmList){

        HashMap<String, ArrayList<Content>> mailList = getMailList(alarmList);
        for (String email : mailList.keySet()) {
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setTo(email);
                helper.setSubject("[Musinsa Price Tracker] 가격 알람");
                Context context = setContext(mailList, email);
                String content = templateEngine.process("emailTemplate", context);
                helper.setText(content, true);
                mailSender.send(message);
            } catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    private static Context setContext(HashMap<String, ArrayList<Content>> mailList, String email) {
        Context context = new Context();

        ArrayList<Content> contentsList = mailList.get(email);

        ArrayList<String> imageUrls = new ArrayList<>();
        ArrayList<String> goodsUrls = new ArrayList<>();
        ArrayList<String> goodsNames = new ArrayList<>();
        ArrayList<Integer> goodsTargetPrices = new ArrayList<>();
        ArrayList<Integer> goodsCurrentPrices = new ArrayList<>();

        for (Content content : contentsList) {
            imageUrls.add("https:" + content.getImg());
            goodsUrls.add("https:" + content.getUrl());
            goodsNames.add(content.getName());
            goodsTargetPrices.add(content.getTargetPrice());
            goodsCurrentPrices.add(content.getPrice());
        }

        context.setVariable("imageUrls", imageUrls);
        context.setVariable("goodsUrls", goodsUrls);
        context.setVariable("goodsNames", goodsNames);
        context.setVariable("goodsTargetPrices", goodsTargetPrices);
        context.setVariable("goodsCurrentPrices", goodsCurrentPrices);

        return context;
    }

    private HashMap<String, ArrayList<Content>> getMailList(List<Alarm> alarmList) {

        HashMap<String, ArrayList<Content>> mailList = new HashMap<>();
        HashMap<Integer, Goods> goodsMap = new HashMap<>();

        for (Alarm alarm : alarmList) {
            String email = alarm.getEmail();
            int goodsId = alarm.getGoodsId();
            Goods goods = getGoods(goodsMap, goodsId);
            if(alarm.getTargetPrice() >= goods.getPrice()){
                ArrayList<Content> contentList;
                Content content = new Content(goods, alarm.getTargetPrice());
                if(mailList.containsKey(email)){
                    contentList = mailList.get(email);
                }
                else{
                    contentList = new ArrayList<>();
                }
                contentList.add(content);
                mailList.put(email, contentList);
            }
        }
        return mailList;
    }

    private Goods getGoods(HashMap<Integer, Goods> goodsMap, int goodsId) {
        Goods goods;

        if(goodsMap.containsKey(goodsId)){
            goods = goodsMap.get(goodsId);
        }else{
            goods = goodsDao.getGoodsById(goodsId);
            goodsMap.put(goodsId, goods);
        }

        return goods;
    }
}
