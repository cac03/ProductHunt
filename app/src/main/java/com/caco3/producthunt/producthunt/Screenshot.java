package com.caco3.producthunt.producthunt;


import com.google.gson.annotations.SerializedName;

public class Screenshot {
  @SerializedName("300px")
  private String url300px;
  @SerializedName("850px")
  private String url850px;

  public String getUrl300px() {
    return url300px;
  }

  public String getUrl850px() {
    return url850px;
  }
}
