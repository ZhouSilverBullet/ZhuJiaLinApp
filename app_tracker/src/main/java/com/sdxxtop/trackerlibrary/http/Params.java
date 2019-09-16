package com.sdxxtop.trackerlibrary.http;

import com.sdxxtop.trackerlibrary.util.StringUtil;

import java.util.HashMap;

/**
 * Created by Administrator on 2018/5/7.
 */

public class Params {
    protected HashMap<String, String> map;

    public Params() {
        map = new HashMap<>();
        defaultValue();
    }

    private void defaultValue() {
    }


    public void removeKey(String key) {
        if (map.containsKey(key)) {
            map.remove(key);
        }
    }

    public void put(String key, String value) {
        map.put(key, StringUtil.stringNotNull(value));
    }

    public void put(String key, long value) {
        map.put(key, value + "");
    }

    public void put(String key, int value) {
        map.put(key, value + "");
    }

    public String getData() {
        return NetHelper.getBase64Data(map);
    }


}
