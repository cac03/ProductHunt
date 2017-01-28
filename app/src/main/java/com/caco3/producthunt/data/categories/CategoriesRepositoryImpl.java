package com.caco3.producthunt.data.categories;


import com.caco3.producthunt.producthunt.category.ProductHuntCategory;
import com.caco3.producthunt.producthunt.category.ProductHuntCategoryDao;
import com.caco3.producthunt.util.Iterables;

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
    List<ProductHuntCategory> newEntities = Iterables.toArrayList(entities);
    Collections.sort(newEntities, comparatoryByCategoryId);

    removeAll(extractToRemove(old, newEntities));
    updateAll(extractToUpdate(old, newEntities));
    saveAll(extractToSave(old, newEntities));
  }

  private List<ProductHuntCategory> extractToRemove(List<ProductHuntCategory> oldEntities,
                                                    List<ProductHuntCategory> newEntities) {
    List<ProductHuntCategory> toRemove = new ArrayList<>();
    for(ProductHuntCategory oldEntity : oldEntities) {
      int index = Collections.binarySearch(newEntities, oldEntity, comparatoryByCategoryId);
      boolean found = index >= 0;
      if (!found) {
        toRemove.add(oldEntity);
      }
    }

    return toRemove;
  }

  private List<ProductHuntCategory> extractToUpdate(List<ProductHuntCategory> oldEntities,
                                                    List<ProductHuntCategory> newEntities) {
    List<ProductHuntCategory> toUpdate = new ArrayList<>();
    for(ProductHuntCategory oldEntity : oldEntities) {
      int index = Collections.binarySearch(newEntities, oldEntity, comparatoryByCategoryId);
      boolean found = index >= 0;
      if (found) {
        toUpdate.add(updateOldEntityWithNewData(oldEntity, newEntities.get(index)));
      }
    }

    return toUpdate;
  }

  private ProductHuntCategory updateOldEntityWithNewData(ProductHuntCategory oldEntity,
                                                         ProductHuntCategory newEntity) {
    oldEntity.setSlug(newEntity.getSlug());
    oldEntity.setColor(newEntity.getColor());
    oldEntity.setItemName(newEntity.getItemName());
    oldEntity.setName(newEntity.getName());
    oldEntity.setCategoryId(newEntity.getCategoryId());

    return oldEntity;
  }

  private List<ProductHuntCategory> extractToSave(List<ProductHuntCategory> oldEntities,
                                                  List<ProductHuntCategory> newEntities) {
    List<ProductHuntCategory> toSave = new ArrayList<>();
    for(ProductHuntCategory newCategory : newEntities) {
      int index = Collections.binarySearch(oldEntities, newCategory, comparatoryByCategoryId);
      boolean found = index >= 0;
      if (!found) {
        toSave.add(newCategory);
      }
    }

    return toSave;
  }
}
