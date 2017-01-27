package com.caco3.producthunt.producthunt.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

@Entity
public class ProductHuntCategory implements Serializable {
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
  private boolean notificationsEnabled;

  private static final long serialVersionUID = 8794565489798797L;

  @Generated(hash = 1388592102)
  public ProductHuntCategory(Long id, long categoryId, String slug, String name,
          String color, String itemName, boolean notificationsEnabled) {
      this.id = id;
      this.categoryId = categoryId;
      this.slug = slug;
      this.name = name;
      this.color = color;
      this.itemName = itemName;
      this.notificationsEnabled = notificationsEnabled;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ProductHuntCategory that = (ProductHuntCategory) o;

    if (categoryId != that.categoryId) return false;
    if (slug != null ? !slug.equals(that.slug) : that.slug != null) return false;
    if (name != null ? !name.equals(that.name) : that.name != null) return false;
    if (color != null ? !color.equals(that.color) : that.color != null) return false;
    return itemName != null ? itemName.equals(that.itemName) : that.itemName == null;

  }

  @Override
  public int hashCode() {
    int result = (int) (categoryId ^ (categoryId >>> 32));
    result = 31 * result + (slug != null ? slug.hashCode() : 0);
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (color != null ? color.hashCode() : 0);
    result = 31 * result + (itemName != null ? itemName.hashCode() : 0);
    return result;
  }

  public boolean getNotificationsEnabled() {
      return this.notificationsEnabled;
  }

  public void setNotificationsEnabled(boolean notificationsEnabled) {
      this.notificationsEnabled = notificationsEnabled;
  }
}
