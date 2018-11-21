package com.frame.framelibrary.beans;

/**
 * Created by tang on 2018/10/16.
 */

public class EventBean<T> {
    private int code;
    private T obj;

    public EventBean(int code, T obj) {
        this.code = code;
        this.obj = obj;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "EventBean{" +
                "code=" + code +
                ", obj=" + obj +
                '}';
    }
}
