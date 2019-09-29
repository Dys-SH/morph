package com.bob.android.morph.bean;

/**
 * created by cly on 2019-09-24
 */
public class MorphEvent {
    private String id;
    private String data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "id = " + id + " -- data = " + data;
    }
}
