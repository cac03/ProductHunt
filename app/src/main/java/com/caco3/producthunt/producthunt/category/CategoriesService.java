package com.caco3.producthunt.producthunt.category;

import java.io.IOException;
import java.util.List;


public interface CategoriesService {
  List<ProductHuntCategory> getAll() throws IOException;
}
