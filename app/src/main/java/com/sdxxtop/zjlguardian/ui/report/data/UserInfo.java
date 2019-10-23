package com.sdxxtop.zjlguardian.ui.report.data;

import androidx.annotation.Keep;

/**
 * Created by huang on 2017/11/1.
 */
@Keep
public class UserInfo {
    private String name;
    private int age;
    private long time;
    private ChildData childData;
    private boolean isCheck;
    private String url;
    private Boolean isTest;


    public UserInfo(String name, int age, long time, boolean isCheck, ChildData childData) {
        this.name = name;
        this.age = age;
        this.time = time;
        this.childData = childData;
        this.isCheck = isCheck;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public ChildData getChildData() {
        return childData;
    }

    public void setChildData(ChildData childData) {
        this.childData = childData;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", time=" + time +
                ", childData=" + childData +
                ", isCheck=" + isCheck +
                ", url='" + url + '\'' +
                ", isTest=" + isTest +
                '}';
    }
}
