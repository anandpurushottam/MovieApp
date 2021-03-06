package com.worldwide.movie.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Dao public class Video {
  @PrimaryKey @SerializedName("id") @Expose private String id;
  @SerializedName("key") @Expose private String key;
  @SerializedName("name") @Expose private String name;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}