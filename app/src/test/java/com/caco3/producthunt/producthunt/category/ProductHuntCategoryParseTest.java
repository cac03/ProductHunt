package com.caco3.producthunt.producthunt.category;


import com.google.gson.Gson;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class ProductHuntCategoryParseTest {
  private static final Gson gson = new Gson();
  private static final String VALID_JSON_RESPONSE = "{ \"id\" : 1, \"slug\" : \"tech\", \"name\" : \"Tech\", \"color\" : \"#da552f\", \"item_name\" : \"product\"}";
  @Test
  public void validJsonProvided_categoryCorrectlyParsed() {
    ProductHuntCategory category = gson.fromJson(VALID_JSON_RESPONSE, ProductHuntCategory.class);
    assertThat(category.getCategoryId())
            .isEqualTo(1L);
    assertThat(category.getSlug())
            .isEqualTo("tech");
    assertThat(category.getName())
            .isEqualTo("Tech");
    assertThat(category.getColor())
            .isEqualTo("#da552f");
    assertThat(category.getItemName())
            .isEqualTo("product");
  }
}
