package com.caco3.producthunt.producthunt.generator;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPojoGenerator<T> {

  public abstract T generateOne();

  public List<T> generateList(int n) {
    List<T> res = new ArrayList<>(n);
    for(int i = 0; i < n; i++) {
      res.add(generateOne());
    }

    return res;
  }
}
