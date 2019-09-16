package com.sdxxtop.trackerlibrary.test;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.util.Comparator;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-08-15 14:51
 * Version: 1.0
 * Description:
 */
public class PinyinComparator implements Comparator<EventTestBean> {
    @Override
    public int compare(EventTestBean o1, EventTestBean o2) {
        char c1 = o1.getPath().charAt(0);
        char c2 = o2.getPath().charAt(0);
        return concatPinyinStringArray(
                PinyinHelper.toHanyuPinyinStringArray(c1)).compareTo(
                concatPinyinStringArray(PinyinHelper
                        .toHanyuPinyinStringArray(c2)));
    }

    private String concatPinyinStringArray(String[] pinyinArray) {
        StringBuffer pinyinSbf = new StringBuffer();
        if ((pinyinArray != null) && (pinyinArray.length > 0)) {
            for (int i = 0; i < pinyinArray.length; i++) {
                pinyinSbf.append(pinyinArray[i]);
            }
        }
        return pinyinSbf.toString();
    }
}