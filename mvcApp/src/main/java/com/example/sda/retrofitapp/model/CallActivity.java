package com.example.sda.retrofitapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sda on 23.05.17.
 */

public class CallActivity {

    @SerializedName("PhoneNo")
    private String phoneNumber;
    @SerializedName("Incoming")
    private boolean incoming;
    @SerializedName("Note")
    private String note;
    @SerializedName("ActivityType")
    private int type;
    @SerializedName("Status")
    private int status;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isIncoming() {
        return incoming;
    }

    public void setIncoming(boolean incoming) {
        this.incoming = incoming;
    }
}
