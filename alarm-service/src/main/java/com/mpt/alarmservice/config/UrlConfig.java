package com.mpt.alarmservice.config;

public enum UrlConfig {
    AUTH_SERVER("auth-server","8080");

    private String ip;
    private String port;

    UrlConfig(String ip,String port) {
        this.ip = ip;
        this.port = port;
    }

    public String getUrl() {
        return "http://" + ip + ":" + port;
    }
}
