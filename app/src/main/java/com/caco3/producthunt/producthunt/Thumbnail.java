package com.caco3.producthunt.producthunt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class Thumbnail {
  @Id
  private Long id;
  @SerializedName("id")
  @Expose
  private long thumbnailId;
  @SerializedName("media_type")
  @Expose
  private String mediaType;
  @SerializedName("image_url")
  @Expose
  private String imageUrl;

  @Generated(hash = 1300960851)
  public Thumbnail(Long id, long thumbnailId, String mediaType, String imageUrl) {
      this.id = id;
      this.thumbnailId = thumbnailId;
      this.mediaType = mediaType;
      this.imageUrl = imageUrl;
  }

  @Generated(hash = 248300619)
  public Thumbnail() {
  }

  public long getThumbnailId() {
    return thumbnailId;
  }

  public String getMediaType() {
    return mediaType;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setThumbnailId(long thumbnailId) {
      this.thumbnailId = thumbnailId;
  }

  public void setMediaType(String mediaType) {
      this.mediaType = mediaType;
  }

  public void setImageUrl(String imageUrl) {
      this.imageUrl = imageUrl;
  }

  public Long getId() {
      return this.id;
  }

  public void setId(Long id) {
      this.id = id;
  }
}
