package com.caco3.producthunt.producthunt.posts;


import com.caco3.producthunt.producthunt.Screenshot;
import com.caco3.producthunt.producthunt.Thumbnail;
import com.google.gson.annotations.SerializedName;


public class ProductHuntPost {
  @SerializedName("category_id")
  private long categoryId;
  @SerializedName("id")
  private int postId;
  @SerializedName("name")
  private String name;
  @SerializedName("discussion_url")
  private String discussionUrl;
  @SerializedName("redirect_url")
  private String redirectUrl;
  @SerializedName("screenshot_url")
  private Screenshot screenshot;
  @SerializedName("thumbnail")
  private Thumbnail thumbnail;
  @SerializedName("votes_count")
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
