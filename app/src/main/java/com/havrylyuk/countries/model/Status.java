package com.havrylyuk.countries.model;

/**
 * Created by Igor Havrylyuk on 09.03.2017.
 */
public class Status {

    private String message;
    private int value;

    public Status() {
    }

    public String getMessage() {
        return message;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return message + ' ' + ", ErrorCode:" + value;
    }
}
