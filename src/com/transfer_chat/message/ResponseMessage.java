package com.transfer_chat.message;

import java.io.Serializable;

public class ResponseMessage implements Serializable {
    public static final int NORMAL = 1, TRANSFER_REQUEST = 2;
    private int type;
    private String message;

    public ResponseMessage(int type, String message) {
        this.type = type;
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}