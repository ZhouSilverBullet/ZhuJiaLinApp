package com.sdxxtop.trackerlibrary.listener;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-08-09 11:11
 * Version: 1.0
 * Description:
 * 需要改变activity的名字，或者fragment的名字
 * 目的：当共用一个activity/fragment的时候，所需要的实现的这个类型来进行额外的拼接
 */
public interface IChangeName {
    String DEFAULT_TYPE = "changeName_empty";

    /**
     * 确定拼接的类型
     *
     * @return
     */
    String changeAppendType();

    /**
     * 判断是否默认实现的，默认实现，不进行拼接
     *
     * @return
     */
    default boolean isHasChangeName() {
        return !(IChangeName.DEFAULT_TYPE.equals(changeAppendType()));
    }

}
