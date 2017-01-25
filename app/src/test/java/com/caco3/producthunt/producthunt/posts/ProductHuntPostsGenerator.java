package com.caco3.producthunt.producthunt.posts;

import com.caco3.producthunt.producthunt.generator.AbstractPojoGenerator;
import com.caco3.producthunt.producthunt.ScreenshotGenerator;
import com.caco3.producthunt.producthunt.ThumbnailGenerator;

import java.util.Random;

public class ProductHuntPostsGenerator extends AbstractPojoGenerator<ProductHuntPost> {
  private static final String[] randomUrls = new String[]{
          "https://google.com", "https://www.google.ru/search?q=gr", "https://twitter.com"
  };
  private final Random random = new Random();
  private final byte[] randomBuffer = new byte[1024];
  private final ScreenshotGenerator screenshotGenerator = new ScreenshotGenerator();
  private final ThumbnailGenerator thumbnailGenerator = new ThumbnailGenerator();

  @Override
  public ProductHuntPost generateOne() {
    ProductHuntPost post = new ProductHuntPost();
    post.setCategoryId(random.nextInt());
    post.setDiscussionUrl(randomUrls[random.nextInt(randomUrls.length)]);
    random.nextBytes(randomBuffer);
    post.setName(new String(randomBuffer));
    post.setRedirectUrl(randomUrls[random.nextInt(randomUrls.length)]);
    random.nextBytes(randomBuffer);
    post.setTagline(new String(randomBuffer));
    post.setPostId(random.nextInt());
    post.setScreenshot(screenshotGenerator.generateOne());
    post.setThumbnail(thumbnailGenerator.generateOne());
    return post;
  }
}
