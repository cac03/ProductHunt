package com.caco3.producthunt.producthunt.category;

import com.caco3.producthunt.producthunt.generator.AbstractPojoGenerator;

import java.util.Random;


public class ProductHuntCategoryGenerator extends AbstractPojoGenerator<ProductHuntCategory> {
  private static final String[] dummyStrings = new String[] {
          "asfadsg", "sagdsgsafh", "qweqtwetrqwt", "asfadfaf", "asfzvxcv"
  };
  private final Random random = new Random();

  @Override
  public ProductHuntCategory generateOne() {
    ProductHuntCategory productHuntCategory = new ProductHuntCategory();
    productHuntCategory.setCategoryId(random.nextInt());
    productHuntCategory.setSlug(dummyStrings[random.nextInt(dummyStrings.length)]);
    productHuntCategory.setItemName(dummyStrings[random.nextInt(dummyStrings.length)]);
    productHuntCategory.setName(dummyStrings[random.nextInt(dummyStrings.length)]);
    productHuntCategory.setColor("#FFFFFF");

    return productHuntCategory;
  }
}
