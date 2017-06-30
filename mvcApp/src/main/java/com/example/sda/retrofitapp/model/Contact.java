package com.example.sda.retrofitapp.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by sda on 24.06.17.
 */

public class Contact extends RealmObject {

    @PrimaryKey
    @SerializedName("Id")
    private Integer id;
    @SerializedName("LastName")
    private String name;
    @SerializedName("PhoneNo")
    private String phone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
