package com.sdxxtop.zjlguardian.ui.report.data;


import com.bin.david.form.annotation.SmartColumn;
import com.sdxxtop.common.utils.UIUtils;

/**
 * Created by huang on 2017/11/1.
 */

public class ChildData {

    @SmartColumn(id = 5, name = "子类", autoCount = true, minHeight = 100)
    private String child;

    public ChildData(String child) {
        this.child = child;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return "ChildData{" +
                "child='" + child + '\'' +
                '}';
    }
}
