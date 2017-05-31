package com.olife.theserver;

/**
 * Created by chenfuhai on 2017/4/11 0011.
 */

public class BasicNameValue {

    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public BasicNameValue(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
