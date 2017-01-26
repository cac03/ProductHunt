package com.caco3.producthunt.data.categories;


import com.caco3.producthunt.producthunt.category.ProductHuntCategory;
import com.caco3.producthunt.producthunt.category.ProductHuntCategoryDao;

import java.util.List;

import static com.caco3.producthunt.util.Preconditions.checkNotNull;

public class CategoriesRepositoryImpl implements CategoriesRepository {
  private final ProductHuntCategoryDao dao;

  public CategoriesRepositoryImpl(ProductHuntCategoryDao dao) {
    this.dao = checkNotNull(dao, "dao == null");
  }

  @Override
  public void save(ProductHuntCategory entity) {
    dao.save(entity);
  }

  @Override
  public void saveAll(Iterable<ProductHuntCategory> entities) {
    dao.saveInTx(entities);
  }

  @Override
  public List<ProductHuntCategory> listAll() {
    return dao.loadAll();
  }

  @Override
  public void update(ProductHuntCategory entity) {
    dao.update(entity);
  }

  @Override
  public void updateAll(Iterable<ProductHuntCategory> entities) {
    dao.updateInTx(entities);
  }

  @Override
  public void remove(ProductHuntCategory entity) {
    dao.delete(entity);
  }

  @Override
  public void removeAll() {
    dao.deleteAll();
  }

  @Override
  public void removeAll(Iterable<ProductHuntCategory> entities) {
    dao.deleteInTx(entities);
  }
}
