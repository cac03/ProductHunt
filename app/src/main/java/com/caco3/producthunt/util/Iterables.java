package com.caco3.producthunt.util;


import java.util.ArrayList;

public class Iterables {
  private Iterables(){
  }

  public static <T> ArrayList<T> toArrayList(Iterable<T> iterable) {
    if (iterable instanceof ArrayList) {
      return (ArrayList<T>)iterable;
    } else {
      ArrayList<T> arrayList = new ArrayList<>();
      for(T item : iterable) {
        arrayList.add(item);
      }

      return arrayList;
    }
  }
}
