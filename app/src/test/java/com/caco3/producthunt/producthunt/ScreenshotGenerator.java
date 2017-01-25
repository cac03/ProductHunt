package com.caco3.producthunt.producthunt;

import com.caco3.producthunt.producthunt.generator.AbstractPojoGenerator;

import java.util.Random;

/**
 * Created by caco3 on 1/25/17.
 */

public class ScreenshotGenerator extends AbstractPojoGenerator<Screenshot> {
  private static final String[] randomUrls = new String[]{
          "https://www.youtube.com/", "http://greenrobot.org/", "http://greenrobot.org/greendao/"
  };
  private final Random random = new Random();

  @Override
  public Screenshot generateOne() {
    String randomUrl1 = randomUrls[random.nextInt(randomUrls.length)];
    String randomUrl2 = randomUrls[random.nextInt(randomUrls.length)];
    return new Screenshot(randomUrl1, randomUrl2);
  }
}
