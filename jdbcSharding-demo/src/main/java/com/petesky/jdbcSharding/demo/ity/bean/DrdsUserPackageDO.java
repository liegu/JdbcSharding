package com.petesky.jdbcSharding.demo.ity.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Desc:
 * Created by liegu.pete on 2016/6/28 上午10:47.
 */
public class DrdsUserPackageDO  implements Serializable {

    Long id;
    Long userId;
    String packageName;
    Long disd;
    Integer appid;
    Date apptime;
    Short isRedown;
    Date cTime;
    Date uTime;
    String ver;
    String storeFront;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Long getDisd() {
        return disd;
    }

    public void setDisd(Long disd) {
        this.disd = disd;
    }

    public Integer getAppid() {
        return appid;
    }

    public void setAppid(Integer appid) {
        this.appid = appid;
    }

    public Date getApptime() {
        return apptime;
    }

    public void setApptime(Date apptime) {
        this.apptime = apptime;
    }

    public Short getIsRedown() {
        return isRedown;
    }

    public void setIsRedown(Short isRedown) {
        this.isRedown = isRedown;
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public Date getuTime() {
        return uTime;
    }

    public void setuTime(Date uTime) {
        this.uTime = uTime;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getStoreFront() {
        return storeFront;
    }

    public void setStoreFront(String storeFront) {
        this.storeFront = storeFront;
    }

}
