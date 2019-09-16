package com.sdxxtop.trackerlibrary.db.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-08-17 09:59
 * Version: 1.0
 * Description:
 */
@Entity
public class TrackerPathEntry {


    /**
     * 这个根据一个path+时间，做一个唯一的标识
     */
    @Id
    private String key;

    /**
     * 是否已经完成，调取了onDestroy的时候
     * 记录一个完成，不然只在重新进入application的时候，会全部发送给后台
     * 不在确认是否是完成，可能当时 app 的进程已经被杀死
     */
    private boolean isComplete;

    /**
     * 是否已经上传
     */
    private boolean isPush;

    /**
     * 公司id
     */
    @Expose()
    @SerializedName("company_id")
    private String companyId;

    /**
     * 用户id
     */
    @Expose()
    @SerializedName("userid")
    private String userId;


    /**
     * 事件发生时间
     */
    @Expose()
    @SerializedName("start_time")
    private Long startTime;

    /**
     * 结束时间
     */
    @Expose()
    @SerializedName("end_time")
    private Long endTime;

    /**
     * 时长
     */
    @Expose()
    @SerializedName("apply_time")
    private Long duration;

    /**
     * 路径
     */
    @Expose()
    @SerializedName("access_type")
    private String path;

    @Generated(hash = 53475793)
    public TrackerPathEntry(String key, boolean isComplete, boolean isPush,
            String companyId, String userId, Long startTime, Long endTime,
            Long duration, String path) {
        this.key = key;
        this.isComplete = isComplete;
        this.isPush = isPush;
        this.companyId = companyId;
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.path = path;
    }

    @Generated(hash = 792954241)
    public TrackerPathEntry() {
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean getIsComplete() {
        return this.isComplete;
    }

    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public boolean getIsPush() {
        return this.isPush;
    }

    public void setIsPush(boolean isPush) {
        this.isPush = isPush;
    }

    public String getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Long getDuration() {
        return this.duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "TrackerPathEntry{" +
                "key='" + key + '\'' +
                ", isComplete=" + isComplete +
                ", companyId='" + companyId + '\'' +
                ", userId='" + userId + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", duration=" + duration +
                ", path='" + path + '\'' +
                '}';
    }
}
