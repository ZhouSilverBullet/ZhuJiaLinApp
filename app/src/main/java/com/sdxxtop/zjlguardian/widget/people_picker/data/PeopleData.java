package com.sdxxtop.zjlguardian.widget.people_picker.data;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-24 09:22
 * Version: 1.0
 * Description:
 */
public class PeopleData implements IPickerData {
    private int peopleId;
    private String label;
    private String value;
    private boolean status;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getPeopleId() {
        return peopleId;
    }

    public void setPeopleId(int peopleId) {
        this.peopleId = peopleId;
    }

    @Override
    public int getId() {
        return peopleId;
    }

    @Override
    public String value() {
        return label;
    }
}
