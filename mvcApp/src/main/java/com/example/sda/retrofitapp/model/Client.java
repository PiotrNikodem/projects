package com.example.sda.retrofitapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sda on 27.05.17.
 */

public class Client implements Serializable {

    @SerializedName("Id")
    private int id;
    @SerializedName("Name")
    private String name;
    @SerializedName("Country")
    private String country;
    @SerializedName("City")
    private String city;
    @SerializedName("PhoneNo1")
    private String phone;
    @SerializedName("ExternalKeys")
    @Expose(serialize = false)
    private List<ExternalKey> externalKeys;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<ExternalKey> getExternalKeys() {
        return externalKeys;
    }

    public void setExternalKeys(List<ExternalKey> externalKeys) {
        this.externalKeys = externalKeys;
    }
}
