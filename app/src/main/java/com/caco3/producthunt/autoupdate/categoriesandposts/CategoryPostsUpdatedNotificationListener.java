package com.caco3.producthunt.autoupdate.categoriesandposts;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v4.app.NotificationCompat;

import com.caco3.producthunt.R;
import com.caco3.producthunt.posts.PostsActivity;
import com.caco3.producthunt.producthunt.category.ProductHuntCategory;
import com.caco3.producthunt.producthunt.posts.ProductHuntPost;

import java.util.List;

import static com.caco3.producthunt.util.Preconditions.checkNotNull;


public class CategoryPostsUpdatedNotificationListener implements CategoryPostsUpdatedListener {
  @DrawableRes
  private static final int SMALL_ICON_ID = R.mipmap.ic_launcher;

  private final NotificationManager notificationManager;
  private final Context context;

  public CategoryPostsUpdatedNotificationListener(Context context) {
    this.context = checkNotNull(context, "context == null");
    this.notificationManager = (NotificationManager)context
            .getSystemService(Context.NOTIFICATION_SERVICE);
  }


  @Override
  public void onPostsUpdated(ProductHuntCategory category, List<ProductHuntPost> newPosts) {
    if (newPosts.isEmpty() || !category.getNotificationsEnabled()){
      return;
    }
    fireNotification(category, new NotificationCompat.Builder(context)
            .setContentTitle(makeTitleString(category))
            .setContentText(makeContentString(newPosts))
            .setSmallIcon(SMALL_ICON_ID)
            .setContentIntent(PendingIntent
                    .getActivity(context, 0, PostsActivity.forCategory(context, category), 0)));
  }

  private void fireNotification(ProductHuntCategory category, NotificationCompat.Builder builder) {
    notificationManager.notify((int)category.getCategoryId(), builder.build());
  }

  private String makeTitleString(ProductHuntCategory category) {
    return category.getName();
  }

  private String makeContentString(List<ProductHuntPost> posts) {
    if (posts.size() == 1) {
      return context.getString(R.string.new_post, posts.get(0).getName());
    } else {
      return context.getString(R.string.x_new_posts, posts.size());
    }
  }
}
