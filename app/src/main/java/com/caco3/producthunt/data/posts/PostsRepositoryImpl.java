package com.caco3.producthunt.data.posts;

import com.caco3.producthunt.producthunt.category.ProductHuntCategory;
import com.caco3.producthunt.producthunt.posts.ProductHuntPost;
import com.caco3.producthunt.producthunt.posts.ProductHuntPostDao;
import com.caco3.producthunt.util.Iterables;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.caco3.producthunt.util.Preconditions.checkNotNull;


public class PostsRepositoryImpl implements PostsRepository {
  private static final Comparator<ProductHuntPost> postsByPostIdComparator
          = new Comparator<ProductHuntPost>() {
    @Override
    public int compare(ProductHuntPost o1, ProductHuntPost o2) {
      return Integer.compare(o1.getPostId(), o2.getPostId());
    }
  };

  private final ProductHuntPostDao dao;

  public PostsRepositoryImpl(ProductHuntPostDao dao) {
    this.dao = checkNotNull(dao, "dao == null");
  }

  @Override
  public void save(ProductHuntPost entity) {
    dao.save(entity);
  }

  @Override
  public void saveAll(Iterable<ProductHuntPost> entities) {
    dao.saveInTx(entities);
  }

  @Override
  public List<ProductHuntPost> listAll() {
    return dao.loadAll();
  }

  @Override
  public void update(ProductHuntPost entity) {
    dao.update(entity);
  }

  @Override
  public void updateAll(Iterable<ProductHuntPost> entities) {
    dao.updateInTx(entities);
  }

  @Override
  public void remove(ProductHuntPost entity) {
    dao.delete(entity);
  }

  @Override
  public void removeAll() {
    dao.deleteAll();
  }

  @Override
  public List<ProductHuntPost> listAllByCategory(ProductHuntCategory category) {
    return dao.queryBuilder()
            .where(ProductHuntPostDao.Properties.CategoryId.eq(category.getCategoryId()))
            .build().list();
  }

  @Override
  public void removeAllByCategory(ProductHuntCategory category) {
    dao.queryBuilder().where(ProductHuntPostDao.Properties.CategoryId.eq(category.getCategoryId()))
            .buildDelete().executeDeleteWithoutDetachingEntities();
  }

  @Override
  public void removeAll(Iterable<ProductHuntPost> entities) {
    dao.deleteInTx(entities);
  }

  @Override
  public void replaceAllWith(Iterable<ProductHuntPost> entities) {
    List<ProductHuntPost> newPosts = Iterables.toArrayList(entities);
    Collections.sort(newPosts, postsByPostIdComparator);
    List<ProductHuntPost> oldPosts = dao.queryBuilder()
            .orderAsc(ProductHuntPostDao.Properties.PostId).build().list();
    removeAll(extractToRemove(oldPosts, newPosts));
    updateAll(extractToUpdate(oldPosts, newPosts));
    saveAll(extractToSave(oldPosts, newPosts));
  }

  @Override
  public void replaceAllInCategoryWith(ProductHuntCategory category, Iterable<ProductHuntPost> posts) {
    List<ProductHuntPost> newPosts = Iterables.toArrayList(posts);
    Collections.sort(newPosts, postsByPostIdComparator);
    List<ProductHuntPost> oldPosts = listAllByCategory(category);
    Collections.sort(oldPosts, postsByPostIdComparator);

    removeAll(extractToRemove(oldPosts, newPosts));
    updateAll(extractToUpdate(oldPosts, newPosts));
    saveAll(extractToSave(oldPosts, newPosts));
  }

  private List<ProductHuntPost> extractToRemove(List<ProductHuntPost> oldEntities,
                                                List<ProductHuntPost> newEntities) {
    List<ProductHuntPost> toRemove = new ArrayList<>();
    for(ProductHuntPost oldEntity : oldEntities) {
      int index = Collections.binarySearch(newEntities, oldEntity, postsByPostIdComparator);
      boolean found = index >= 0;
      if (!found) {
        toRemove.add(oldEntity);
      }
    }

    return toRemove;
  }

  private List<ProductHuntPost> extractToUpdate(List<ProductHuntPost> oldEntities,
                                                List<ProductHuntPost> newEntities) {
    List<ProductHuntPost> toUpdate = new ArrayList<>();
    for(ProductHuntPost oldEntity : oldEntities) {
      int index = Collections.binarySearch(newEntities, oldEntity, postsByPostIdComparator);
      boolean found = index >= 0;
      if (found) {
        toUpdate.add(fillNewEntity(oldEntity, newEntities.get(index)));
      }
    }

    return toUpdate;
  }

  private ProductHuntPost fillNewEntity(ProductHuntPost oldEntity,
                                        ProductHuntPost newEntity) {
    newEntity.setId(oldEntity.getId());

    return newEntity;
  }

  private List<ProductHuntPost> extractToSave(List<ProductHuntPost> oldEntities,
                                              List<ProductHuntPost> newEntities) {
    List<ProductHuntPost> toSave = new ArrayList<>();
    for(ProductHuntPost newPost : newEntities) {
      int index = Collections.binarySearch(oldEntities, newPost, postsByPostIdComparator);
      boolean found = index >= 0;
      if (!found) {
        toSave.add(newPost);
      }
    }

    return toSave;
  }
}
