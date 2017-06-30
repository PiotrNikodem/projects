package com.example.sda.retrofitapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sda on 30.05.17.
 */

public class ExternalKey implements Serializable {

    @SerializedName("TableName")
    private String tableName;
    @SerializedName("RecId")
    private int recId;
    @SerializedName("ExternalSystem")
    private String externalSystem;
    @SerializedName("ExternalId")
    private String externalId;
    @SerializedName("ExternalTableName")
    private String externalTableName;
    @Expose(serialize = false)
    private int id;
}
