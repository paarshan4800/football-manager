package com.transfer_chat.message;

import java.io.Serializable;

public class RequestMessage implements Serializable {
    public static final int ONLINE = 1, LOGOUT = 2, REQUEST_PING = 3, TRANSFER = 3, LOAN = 4, VIEW_TRANSFERS = 5;
    private String message;
    private int type;
    private int playersManagerID;

    public RequestMessage(int type, String message) {
        this.message = message;
        this.type = type;
    }

    public RequestMessage(int type, String message, int playersManagerID) {
        this.message = message;
        this.type = type;
        this.playersManagerID = playersManagerID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPlayersManagerID() {
        return playersManagerID;
    }

    public void setPlayersManagerID(int playersManagerID) {
        this.playersManagerID = playersManagerID;
    }
}