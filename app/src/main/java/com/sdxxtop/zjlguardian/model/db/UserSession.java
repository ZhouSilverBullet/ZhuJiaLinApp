package com.sdxxtop.zjlguardian.model.db;

import android.text.TextUtils;

import com.sdxxtop.network.helper.HttpConstantValue;
import com.sdxxtop.network.utils.SpUtil;

/**
 * 用户信息的统一操作类
 */
public class UserSession implements IUserData {

    private final UserInfo userInfo;

    public static UserSession getInstance() {
        return SingleHolder.sUserSave;
    }

    public static class SingleHolder {
        private static UserSession sUserSave = new UserSession();
    }

    private UserSession() {
        userInfo = new UserInfo();
    }

    @Override
    public void saveInfo(String autoToken, int expireTime, int partId,
                         int userid, String mobile, int type) {
        SpUtil.putString(HttpConstantValue.AUTO_TOKEN, autoToken);
        SpUtil.putInt(HttpConstantValue.EXPIRE_TIME, expireTime);
        SpUtil.putInt(HttpConstantValue.PART_ID, partId);
        SpUtil.putInt(HttpConstantValue.USER_ID, userid);
        SpUtil.putString(HttpConstantValue.MOBILE, mobile);

        userInfo.autoToken = autoToken;
        userInfo.expireTime = expireTime;
        userInfo.partId = partId;
        userInfo.userId = userid;
        userInfo.mobile = mobile;
    }

    @Override
    public void saveMineInfo(String userName, String partName) {
        SpUtil.putString(HttpConstantValue.USER_NAME, userName);
        SpUtil.putString(HttpConstantValue.PART_NAME, partName);

        userInfo.userName = userName;
        userInfo.partName = partName;
    }

    @Override
    public void savePassword(String password) {
        SpUtil.putString(HttpConstantValue.PASSWORD, password);
        userInfo.password = password;
    }

    @Override
    public void removeAutoLoginInfo() {
        userInfo.autoToken = "";
        userInfo.expireTime = 0;

        SpUtil.putString(HttpConstantValue.AUTO_TOKEN, "");
        SpUtil.putInt(HttpConstantValue.EXPIRE_TIME, 0);
    }

    public void logout() {
        removeAutoLoginInfo();
    }

    public String getAutoToken() {
        String autoToken = userInfo.autoToken;
        if (TextUtils.isEmpty(autoToken)) {
            return SpUtil.getString(HttpConstantValue.AUTO_TOKEN);
        }
        return autoToken;
    }

    public int getExpireTime() {
        int expireTime = userInfo.expireTime;
        if (expireTime == 0) {
            return SpUtil.getInt(HttpConstantValue.EXPIRE_TIME, 0);
        }
        return expireTime;
    }

    public int getPartId() {
        int partId = userInfo.partId;
        if (partId == 0) {
            return SpUtil.getInt(HttpConstantValue.PART_ID, 0);
        }
        return partId;
    }

    public int getUserId() {
        int userId = userInfo.userId;
        if (userId == 0) {
            return SpUtil.getInt(HttpConstantValue.USER_ID, 0);
        }
        return userId;
    }

    public String getMobile() {
        String mobile = userInfo.mobile;
        if (TextUtils.isEmpty(mobile)) {
            return SpUtil.getString(HttpConstantValue.MOBILE);
        }
        return mobile;
    }

    public String getPartName() {
        String partName = userInfo.partName;
        if (TextUtils.isEmpty(partName)) {
            return SpUtil.getString(HttpConstantValue.PART_NAME);
        }
        return partName;
    }

    public String getUserName() {
        String userName = userInfo.userName;
        if (TextUtils.isEmpty(userName)) {
            return SpUtil.getString(HttpConstantValue.USER_NAME);
        }
        return userName;
    }

    public String getPassword() {
        String password = userInfo.password;
        if (TextUtils.isEmpty(password)) {
            return SpUtil.getString(HttpConstantValue.PASSWORD);
        }
        return password;
    }

    private class UserInfo {
        String autoToken;
        int expireTime;
        int partId;
        int userId;
        String mobile;

        String userName;
        String partName;

        String password;
    }

}
