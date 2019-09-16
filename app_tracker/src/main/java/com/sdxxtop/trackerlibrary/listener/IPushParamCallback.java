package com.sdxxtop.trackerlibrary.listener;


import androidx.core.util.Pair;

import java.util.List;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-08-09 09:50
 * Version: 1.0
 * Description:
 * 用于获取请求的参数
 * ci ui 等
 */
public interface IPushParamCallback<T> {
    List<Pair<String, T>> call();
}
