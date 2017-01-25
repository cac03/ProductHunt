package com.caco3.producthunt.producthunt.posts;


import com.caco3.producthunt.producthunt.Screenshot;
import com.caco3.producthunt.producthunt.Thumbnail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.converter.PropertyConverter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ProductHuntPost implements Serializable {
  @Id
  private Long id;
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
  @Convert(converter = ScreenshotConverter.class, columnType = byte[].class)
  /**@see ScreenshotConverter */
  private Screenshot screenshot;
  @SerializedName("thumbnail")
  @Expose
  @Convert(converter = ThumbnailConverter.class, columnType = byte[].class)
  private Thumbnail thumbnail;
  /**@see ThumbnailConverter*/
  @SerializedName("votes_count")
  @Expose
  private int votesCount;
  @SerializedName("tagline")
  @Expose
  private String tagline;

  private static final long serialVersionUID = 89798765453131L;

  @Generated(hash = 203018294)
  public ProductHuntPost(Long id, long categoryId, int postId, String name, String discussionUrl,
          String redirectUrl, Screenshot screenshot, Thumbnail thumbnail, int votesCount,
          String tagline) {
      this.id = id;
      this.categoryId = categoryId;
      this.postId = postId;
      this.name = name;
      this.discussionUrl = discussionUrl;
      this.redirectUrl = redirectUrl;
      this.screenshot = screenshot;
      this.thumbnail = thumbnail;
      this.votesCount = votesCount;
      this.tagline = tagline;
  }

@Generated(hash = 52185221)
  public ProductHuntPost() {
  }

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

  public int getVotesCount() {
    return votesCount;
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

  public void setPostId(int postId) {
      this.postId = postId;
  }

  public void setName(String name) {
      this.name = name;
  }

  public void setDiscussionUrl(String discussionUrl) {
      this.discussionUrl = discussionUrl;
  }

  public void setRedirectUrl(String redirectUrl) {
      this.redirectUrl = redirectUrl;
  }

  public void setScreenshot(Screenshot screenshot) {
      this.screenshot = screenshot;
  }

  public void setVotesCount(int votesCount) {
      this.votesCount = votesCount;
  }

  public String getTagline() {
    return this.tagline;
}

public void setTagline(String tagline) {
    this.tagline = tagline;
}

public Thumbnail getThumbnail() {
    return this.thumbnail;
}

public void setThumbnail(Thumbnail thumbnail) {
    this.thumbnail = thumbnail;
}

  static class ScreenshotConverter implements PropertyConverter<Screenshot, byte[]> {
    @Override
    public Screenshot convertToEntityProperty(byte[] databaseValue) {
      try (ByteArrayInputStream byteArrayInputStream
                   = new ByteArrayInputStream(databaseValue);
        ObjectInputStream ois = new ObjectInputStream(byteArrayInputStream)) {
        return (Screenshot)ois.readObject();
      } catch (ClassNotFoundException | IOException rethrow) {
        throw new RuntimeException(rethrow);
      }
    }

    @Override
    public byte[] convertToDatabaseValue(Screenshot entityProperty) {
      try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
           ObjectOutputStream oos = new ObjectOutputStream(byteArrayOutputStream)) {
        oos.writeObject(entityProperty);
        return byteArrayOutputStream.toByteArray();
      } catch (IOException rethrow) {
        throw new RuntimeException(rethrow);
      }
    }
  }

  static class ThumbnailConverter implements PropertyConverter<Thumbnail, byte[]> {
    @Override
    public Thumbnail convertToEntityProperty(byte[] databaseValue) {
      try (ByteArrayInputStream byteArrayInputStream
                   = new ByteArrayInputStream(databaseValue);
           ObjectInputStream ois = new ObjectInputStream(byteArrayInputStream)) {
        return (Thumbnail)ois.readObject();
      } catch (ClassNotFoundException | IOException rethrow) {
        throw new RuntimeException(rethrow);
      }
    }

    @Override
    public byte[] convertToDatabaseValue(Thumbnail entityProperty) {
      try (ByteArrayOutputStream byteArrayOutputStream
                   = new ByteArrayOutputStream();
           ObjectOutputStream oos = new ObjectOutputStream(byteArrayOutputStream)) {
        oos.writeObject(entityProperty);
        oos.flush();
        return byteArrayOutputStream.toByteArray();
      } catch (IOException rethrow) {
        throw new RuntimeException(rethrow);
      }
    }
  }
}
