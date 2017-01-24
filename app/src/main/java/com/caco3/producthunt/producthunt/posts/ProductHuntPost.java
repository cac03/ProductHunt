package com.caco3.producthunt.producthunt.posts;


import com.caco3.producthunt.producthunt.Screenshot;
import com.caco3.producthunt.producthunt.Thumbnail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ProductHuntPost {
  @SerializedName("category_id")
  @Expose
  private long categoryId;
  @SerializedName("id")
  @Expose
  private int postId;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("discussion_url")
  @Expose
  private String discussionUrl;
  @SerializedName("redirect_url")
  @Expose
  private String redirectUrl;
  @SerializedName("screenshot_url")
  @Expose
  private Screenshot screenshot;
  @SerializedName("thumbnail")
  @Expose
  private Thumbnail thumbnail;
  @SerializedName("votes_count")
  @Expose
  private int votesCount;

  public long getCategoryId() {
    return categoryId;
  }

  public int getPostId() {
    return postId;
  }

  public String getName() {
    return name;
  }

  public String getDiscussionUrl() {
    return discussionUrl;
  }

  public String getRedirectUrl() {
    return redirectUrl;
  }

  public Screenshot getScreenshot() {
    return screenshot;
  }

  public Thumbnail getThumbnail() {
    return thumbnail;
  }

  public int getVotesCount() {
    return votesCount;
  }
}
