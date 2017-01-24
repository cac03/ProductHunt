package com.caco3.producthunt.producthunt.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductHuntCategory {
  @SerializedName("id")
  @Expose
  private long categoryId;
  @SerializedName("slug")
  @Expose
  private String slug;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("color")
  @Expose
  private String color;
  @SerializedName("item_name")
  @Expose
  private String itemName;

  public long getCategoryId() {
    return categoryId;
  }

  public String getSlug() {
    return slug;
  }

  public String getName() {
    return name;
  }

  public String getColor() {
    return color;
  }

  public String getItemName() {
    return itemName;
  }
}
