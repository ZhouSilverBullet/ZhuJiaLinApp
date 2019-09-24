package com.sdxxtop.zjlguardian.widget.people_picker.data;

import java.util.List;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-24 09:22
 * Version: 1.0
 * Description:
 */
public class DepartmentData implements IPickerData {
    private String label;
    private String value;
    private boolean status;
    private List<PeopleData> peopleDataList;

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

    public List<PeopleData> getPeopleDataList() {
        return peopleDataList;
    }

    public void setPeopleDataList(List<PeopleData> peopleDataList) {
        this.peopleDataList = peopleDataList;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String value() {
        return label;
    }
}
