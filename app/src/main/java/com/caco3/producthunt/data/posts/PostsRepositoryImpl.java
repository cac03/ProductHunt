package com.caco3.producthunt.data.posts;

import com.caco3.producthunt.producthunt.category.ProductHuntCategory;
import com.caco3.producthunt.producthunt.posts.ProductHuntPost;
import com.caco3.producthunt.producthunt.posts.ProductHuntPostDao;

import java.util.List;

import static com.caco3.producthunt.util.Preconditions.checkNotNull;

/**
 * Created by caco3 on 1/24/17.
 */

public class PostsRepositoryImpl implements PostsRepository {
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
}
