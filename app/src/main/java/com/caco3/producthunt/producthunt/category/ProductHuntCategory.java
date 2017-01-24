package com.caco3.producthunt.producthunt.category;

import com.google.gson.annotations.SerializedName;

public class ProductHuntCategory {
  @SerializedName("id")
  private long categoryId;
  @SerializedName("slug")
  private String slug;
  @SerializedName("name")
  private String name;
  @SerializedName("color")
  private String color;
  @SerializedName("item_name")
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
