package com.bob.android.morph.bean;


import com.google.gson.annotations.SerializedName;

/**
 * created by cly on 2019-09-16
 */
public class Condition {

    private String operation;
    private String value;
    @SerializedName("return")
    private String result;
    private LeftOrRight left;
    private LeftOrRight right;


    public String getOperation() {
        return operation;
    }

    public String getValue() {
        return value;
    }

    public String getResult() {
        return result;
    }

    public LeftOrRight getLeft() {
        return left;
    }

    public LeftOrRight getRight() {
        return right;
    }

    public static class LeftOrRight {
        private String operation;
        private String value;
        private LeftOrRight left;
        private LeftOrRight right;

        public String getOperation() {
            return operation;
        }

        public String getValue() {
            return value;
        }

        public LeftOrRight getLeft() {
            return left;
        }

        public LeftOrRight getRight() {
            return right;
        }
    }
}
