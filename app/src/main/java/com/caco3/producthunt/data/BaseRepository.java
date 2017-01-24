package com.caco3.producthunt.data;


import java.util.List;

public interface BaseRepository<T> {
  void save(T entity);
  void saveAll(Iterable<T> entities);
  List<T> listAll();
  void update(T entity);
  void updateAll(Iterable<T> entities);
  void remove(T entity);
  void removeAll();
}
