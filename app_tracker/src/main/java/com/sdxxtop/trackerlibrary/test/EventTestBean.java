package com.sdxxtop.trackerlibrary.test;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sdxxtop.trackerlibrary.listener.IChangeName;

public class EventTestBean {

    /**
     * 事件类型
     */
    public final static int EVENT_TYPE_VIEW = 0;      // 页面预览事件
    public final static int EVENT_TYPE_CLICKED = 1;   // 点击事件

    public final static int MILL_OF_SECOND = 1;

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
     * 事件类型
     */
    private int type;
    /**
     * 事件发生时间
     */
    @Expose()
    @SerializedName("start_time")
    private long eventTime;
    /**
     * 页面停留时长
     */
    private double duration;

    public void setDuration(double duration) {
        this.duration = duration;
    }

    /**
     * 结束时间
     */
    @Expose()
    @SerializedName("end_time")
    private long endTime;
    /**
     * 路径
     */
    @Expose()
    @SerializedName("access_type")
    private String path;

    /**
     * 预留字段，暂时用不上， 用空串传给后台就可以了
     */
    @Expose()
    @SerializedName("apply_time")
    private String applyTime = "";

    /**
     * 是否上传成功
     */
    public boolean isPush;

    /**
     * 出现的次数
     */
    public long count;

    /**
     * 总时长
     */
    public double allDuration;


    public EventTestBean() {
    }

    public int getType() {
        return type;
    }

    public long getEventTime() {
        return eventTime;
    }

    public double getDuration() {
        return duration;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Event{" +
                "type=" + type +
                ", createTime=" + eventTime +
                ", duration=" + duration +
                ", path='" + path + '\'' +
                '}';
    }

    /**
     * 为预览页面事件生成path
     *
     * @param context
     * @param fragment
     * @return
     */
    public static String generateViewPath(@NonNull Context context, Fragment fragment) {
        StringBuilder builder = new StringBuilder();

        String contextName = getContextName(context);
        builder.append(contextName);

        if (fragment != null) {
            builder.append("$").append(fragment.getClass().getSimpleName());
        }
        return builder.toString();
    }

    private static String getContextName(Context context) {
        String name = context.getClass().getSimpleName();
        if (context instanceof IChangeName) {
            if (((IChangeName) context).isHasChangeName()) {
                name = name + ((IChangeName) context).changeAppendType();
            }
        }
        return name;
    }


    /**
     * 为点击事件生成path
     *
     * @param view
     * @param fragment
     * @return
     */
    public static String generateClickedPath(@NonNull View view, Fragment fragment) {
        StringBuilder builder = new StringBuilder(generateViewPath(view.getContext(), fragment));
        builder.append("$").append(view.getClass().getName());
        if (view.getId() != View.NO_ID) {
            String resourceName = view.getResources().getResourceEntryName(view.getId());
            if (!TextUtils.isEmpty(resourceName)) {
                builder.append("$").append(resourceName);
            }
        }

        return builder.toString();
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
