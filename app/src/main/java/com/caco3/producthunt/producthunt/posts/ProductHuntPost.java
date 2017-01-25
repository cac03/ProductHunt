package com.caco3.producthunt.producthunt.posts;


import com.caco3.producthunt.producthunt.Screenshot;
import com.caco3.producthunt.producthunt.Thumbnail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.converter.PropertyConverter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.caco3.producthunt.producthunt.category.DaoSession;
import com.caco3.producthunt.producthunt.ThumbnailDao;

@Entity
public class ProductHuntPost {
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
  @ToOne
  private Thumbnail thumbnail;
  @SerializedName("votes_count")
  @Expose
  private int votesCount;
  @SerializedName("tagline")
  @Expose
  private String tagline;
  /** Used to resolve relations */
  @Generated(hash = 2040040024)
  private transient DaoSession daoSession;
  /** Used for active entity operations. */
  @Generated(hash = 1095922276)
  private transient ProductHuntPostDao myDao;

  @Generated(hash = 2013786902)
public ProductHuntPost(Long id, long categoryId, int postId, String name, String discussionUrl,
        String redirectUrl, Screenshot screenshot, int votesCount, String tagline) {
    this.id = id;
    this.categoryId = categoryId;
    this.postId = postId;
    this.name = name;
    this.discussionUrl = discussionUrl;
    this.redirectUrl = redirectUrl;
    this.screenshot = screenshot;
    this.votesCount = votesCount;
    this.tagline = tagline;
}

@Generated(hash = 52185221)
  public ProductHuntPost() {
  }

  @Generated(hash = 250064951)
  private transient boolean thumbnail__refreshed;

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

  /** To-one relationship, resolved on first access. */
  @Generated(hash = 796225371)
  public Thumbnail getThumbnail() {
      if (thumbnail != null || !thumbnail__refreshed) {
          if (daoSession == null) {
              throw new DaoException("Entity is detached from DAO context");
          }
          ThumbnailDao targetDao = daoSession.getThumbnailDao();
          targetDao.refresh(thumbnail);
          thumbnail__refreshed = true;
      }
      return thumbnail;
  }

  /** To-one relationship, returned entity is not refreshed and may carry only the PK property. */
  @Generated(hash = 653052824)
  public Thumbnail peakThumbnail() {
      return thumbnail;
  }

  /** called by internal mechanisms, do not call yourself. */
  @Generated(hash = 1789739000)
  public void setThumbnail(Thumbnail thumbnail) {
      synchronized (this) {
          this.thumbnail = thumbnail;
          thumbnail__refreshed = true;
      }
  }

  /**
   * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
   * Entity must attached to an entity context.
   */
  @Generated(hash = 128553479)
  public void delete() {
      if (myDao == null) {
          throw new DaoException("Entity is detached from DAO context");
      }
      myDao.delete(this);
  }

  /**
   * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
   * Entity must attached to an entity context.
   */
  @Generated(hash = 1942392019)
  public void refresh() {
      if (myDao == null) {
          throw new DaoException("Entity is detached from DAO context");
      }
      myDao.refresh(this);
  }

  /**
   * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
   * Entity must attached to an entity context.
   */
  @Generated(hash = 713229351)
  public void update() {
      if (myDao == null) {
          throw new DaoException("Entity is detached from DAO context");
      }
      myDao.update(this);
  }

  /** called by internal mechanisms, do not call yourself. */
  @Generated(hash = 1324608822)
  public void __setDaoSession(DaoSession daoSession) {
      this.daoSession = daoSession;
      myDao = daoSession != null ? daoSession.getProductHuntPostDao() : null;
  }

public String getTagline() {
    return this.tagline;
}

public void setTagline(String tagline) {
    this.tagline = tagline;
}

  /**
   * Unfortunately GreenDao supports only single FK key per entity
   */
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
}
