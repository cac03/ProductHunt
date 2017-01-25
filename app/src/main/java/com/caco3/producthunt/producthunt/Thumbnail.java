package com.caco3.producthunt.producthunt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Thumbnail implements Serializable {
  @SerializedName("id")
  @Expose
  private long thumbnailId;
  @SerializedName("media_type")
  @Expose
  private String mediaType;
  @SerializedName("image_url")
  @Expose
  private String imageUrl;

  private static final long serialVersionUID = 897987465131346L;

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
}
