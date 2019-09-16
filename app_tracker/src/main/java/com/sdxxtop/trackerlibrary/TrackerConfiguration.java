package com.sdxxtop.trackerlibrary;

public class TrackerConfiguration {

    /**
     * 基础的URL
     *
     */
    private String uploadBaseUrl;

    /**
     * 上传统计数据的URL
     *
     *  pointlog/burypoint
     */
    private String uploadUrl;

    /**
     * 分钟
     */
    private int uploadTime = 1;

    private String appKey;

    /**
     * app是否是debug的模式下面
     */
    private boolean isDebug;

    /**
     * 是否是家长版
     * 家长版的接口没有CompanyId
     */
    private boolean isParent;

    /**
     * 是否是测试环境
     * 测试环境，上传成功后，不进行删除
     *
     */
    private boolean isTest;

    public boolean isTest() {
        return isTest;
    }

    public TrackerConfiguration setTest(boolean test) {
        isTest = test;
        return this;
    }

    public TrackerConfiguration() {
    }

    public TrackerConfiguration setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
        return this;
    }

    public String getUploadUrl() {
        return uploadUrl;
    }

    public TrackerConfiguration setUploadBaseUrl(String uploadBaseUrl) {
        this.uploadBaseUrl = uploadBaseUrl;
        return this;
    }

    public String getUploadBaseUrl() {
        return uploadBaseUrl;
    }

    public int getUploadTime() {
        return uploadTime;
    }

    public TrackerConfiguration setUploadTime(int uploadTime) {
        this.uploadTime = uploadTime;
        return this;
    }

    public TrackerConfiguration setAppKey(String appKey) {
        this.appKey = appKey;
        return this;
    }

    public String getAppKey() {
        return appKey;
    }

    public TrackerConfiguration setDebug(boolean debug) {
        isDebug = debug;
        return this;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public boolean isParent() {
        return isParent;
    }

    public TrackerConfiguration setParent(boolean parent) {
        isParent = parent;
        return this;
    }
}
