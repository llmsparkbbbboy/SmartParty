package com.qiantang.smartparty.modle;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by zhaoyong bai on 2018/5/25.
 */
public class RxComment {

    private int isDz;
    private int dz;
    private String avatar;
    private String creationtime;
    private String userId;
    private String content;
    private String username;

    public int getIsDz() {
        return isDz;
    }

    public void setIsDz(int isDz) {
        this.isDz = isDz;
    }

    public int getDz() {
        return dz;
    }

    public void setDz(int dz) {
        this.dz = dz;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCreationtime() {
        return creationtime;
    }

    public void setCreationtime(String creationtime) {
        this.creationtime = creationtime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
