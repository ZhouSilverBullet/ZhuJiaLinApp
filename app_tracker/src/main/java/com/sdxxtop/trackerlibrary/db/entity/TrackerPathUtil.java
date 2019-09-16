package com.sdxxtop.trackerlibrary.db.entity;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.sdxxtop.trackerlibrary.listener.IChangeName;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-08-17 11:46
 * Version: 1.0
 * Description:
 */
public class TrackerPathUtil {
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
}
