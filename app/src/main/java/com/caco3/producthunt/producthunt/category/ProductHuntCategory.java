package com.caco3.producthunt.producthunt.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ProductHuntCategory {
  @Id
  private Long id;
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

  @Generated(hash = 106304847)
  public ProductHuntCategory(Long id, long categoryId, String slug, String name,
          String color, String itemName) {
      this.id = id;
      this.categoryId = categoryId;
      this.slug = slug;
      this.name = name;
      this.color = color;
      this.itemName = itemName;
  }

  @Generated(hash = 257751680)
  public ProductHuntCategory() {
  }

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

  public Long getId() {
      return this.id;
  }

  public void setId(Long id) {
      this.id = id;
  }

  public void setCategoryId(long categoryId) {
      this.categoryId = categoryId;
  }

  public void setSlug(String slug) {
      this.slug = slug;
  }

  public void setName(String name) {
      this.name = name;
  }

  public void setColor(String color) {
      this.color = color;
  }

  public void setItemName(String itemName) {
      this.itemName = itemName;
  }
}
