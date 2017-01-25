package com.caco3.producthunt.producthunt;

import com.caco3.producthunt.producthunt.generator.AbstractPojoGenerator;

import java.util.Random;


public class ThumbnailGenerator extends AbstractPojoGenerator<Thumbnail> {
  private static final String[] dummyUrls = new String[] {
          "https://www.youtube.com/", "http://greenrobot.org/", "http://greenrobot.org/greendao/"
  };
  private static final String[] dummyMediaTypes = new String[] {
          "image"
  };

  private final Random random = new Random();

  @Override
  public Thumbnail generateOne() {
    Thumbnail thumbnail = new Thumbnail();
    thumbnail.setThumbnailId(random.nextLong());
    thumbnail.setImageUrl(dummyUrls[random.nextInt(dummyUrls.length)]);
    thumbnail.setMediaType(dummyMediaTypes[random.nextInt(dummyMediaTypes.length)]);

    return thumbnail;
  }
}
