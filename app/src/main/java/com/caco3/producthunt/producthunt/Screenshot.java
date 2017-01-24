package com.caco3.producthunt.producthunt;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Screenshot implements Serializable {
  @SerializedName("300px")
  @Expose
  private String url300px;
  @SerializedName("850px")
  @Expose
  private String url850px;

  private static final long serialVersionUID = 7987465465113213456L;

  public String getUrl300px() {
    return url300px;
  }

  public String getUrl850px() {
    return url850px;
  }
}
