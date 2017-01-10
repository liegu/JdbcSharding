package com.petesky.jdbcSharding.demo.ity.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Desc:
 * Created by liegu.pete on 2016/8/4 下午8:22.
 */
public class UserActiveDaysDO implements Serializable {

    Long id;

    Long userId;

    Date activeDate;

    Date createTime;

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

    public Date getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(Date activeDate) {
        this.activeDate = activeDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
