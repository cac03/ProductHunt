package com.caco3.producthunt.producthunt;

import com.google.gson.annotations.SerializedName;

public class Thumbnail {
  @SerializedName("id")
  private long thumbnailId;
  @SerializedName("media_type")
  private String mediaType;
  @SerializedName("image_url")
  private String imageUrl;

  public long getThumbnailId() {
    return thumbnailId;
  }

  public String getMediaType() {
    return mediaType;
  }

  public String getImageUrl() {
    return imageUrl;
  }
}
