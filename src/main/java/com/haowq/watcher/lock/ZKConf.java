package com.haowq.watcher.lock;

/**
 * @Auther: haowq
 * @Date: 2021/7/19 19:27
 * @Description:
 */
public class ZKConf {
    private String address;
    private Integer sessionTime;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(Integer sessionTime) {
        this.sessionTime = sessionTime;
    }
}
