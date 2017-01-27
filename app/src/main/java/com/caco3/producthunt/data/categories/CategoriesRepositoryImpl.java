package com.caco3.producthunt.data.categories;


import com.caco3.producthunt.producthunt.category.ProductHuntCategory;
import com.caco3.producthunt.producthunt.category.ProductHuntCategoryDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.caco3.producthunt.util.Preconditions.checkNotNull;

public class CategoriesRepositoryImpl implements CategoriesRepository {
  private static final Comparator<ProductHuntCategory> comparatoryByCategoryId
          = new Comparator<ProductHuntCategory>() {
            @Override
            public int compare(ProductHuntCategory o1, ProductHuntCategory o2) {
              return Long.compare(o1.getCategoryId(), o2.getCategoryId());
            }
          };
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

  @Override
  public void replaceAllWith(Iterable<ProductHuntCategory> entities) {
    List<ProductHuntCategory> old
            = dao.queryBuilder().orderAsc(ProductHuntCategoryDao.Properties.CategoryId)
            .build().list();
    List<ProductHuntCategory> newEntities;
    if (entities instanceof List) {
      newEntities = (List<ProductHuntCategory>)entities;
    } else {
      newEntities = new ArrayList<>();
      for(ProductHuntCategory category : entities) {
        newEntities.add(category);
      }
    }
    Collections.sort(newEntities, comparatoryByCategoryId);
    List<ProductHuntCategory> toRemove = new ArrayList<>();
    List<ProductHuntCategory> toUpdate = new ArrayList<>();
    for(ProductHuntCategory oldEntity : old) {
      int index = Collections.binarySearch(newEntities, oldEntity, comparatoryByCategoryId);
      boolean found = index >= 0;
      if (found) {
        toUpdate.add(update(oldEntity, newEntities.get(index)));
      } else {
        toRemove.add(oldEntity);
      }
    }

    removeAll(toRemove);
    updateAll(toUpdate);
  }

  private ProductHuntCategory update(ProductHuntCategory oldEntity, ProductHuntCategory newEntity) {
    newEntity.setNotificationsEnabled(oldEntity.getNotificationsEnabled());
    newEntity.setId(oldEntity.getId());
    return newEntity;
  }
}
