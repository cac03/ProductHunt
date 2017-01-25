package com.caco3.producthunt.posts;

import android.content.Context;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.caco3.producthunt.R;
import com.caco3.producthunt.producthunt.posts.ProductHuntPost;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.caco3.producthunt.util.Preconditions.checkNotNull;


class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder> {

  interface UiEventsListener {
    void onPostClicked(ProductHuntPost post);
  }

  private Comparator<ProductHuntPost> comparator;
  private Context context;
  private UiEventsListener listener;
  private SortedList<ProductHuntPost> items = new SortedList<>(ProductHuntPost.class,
          new SortedList.Callback<ProductHuntPost>() {
    @Override
    public int compare(ProductHuntPost o1, ProductHuntPost o2) {
      return comparator.compare(o1, o2);
    }

    @Override
    public void onChanged(int position, int count) {
      notifyItemRangeChanged(position, count);
    }

    @Override
    public boolean areContentsTheSame(ProductHuntPost oldItem, ProductHuntPost newItem) {
      return oldItem.equals(newItem);
    }

    @Override
    public boolean areItemsTheSame(ProductHuntPost item1, ProductHuntPost item2) {
      return item1.getPostId() == item2.getPostId();
    }

    @Override
    public void onInserted(int position, int count) {
      notifyItemRangeInserted(position, count);
    }

    @Override
    public void onRemoved(int position, int count) {
      notifyItemRangeRemoved(position, count);
    }

    @Override
    public void onMoved(int fromPosition, int toPosition) {
      notifyItemMoved(fromPosition, toPosition);
    }
  });

  PostsAdapter(UiEventsListener listener, Comparator<ProductHuntPost> comparator) {
    this.listener = checkNotNull(listener, "listener == null");
    this.comparator = checkNotNull(comparator, "comparator == null");
  }

  void setItems(List<ProductHuntPost> newPosts) {
    items.beginBatchedUpdates();
    removeAllThatAreNotIn(newPosts);
    items.addAll(newPosts);
    items.endBatchedUpdates();
  }

  private void removeAllThatAreNotIn(List<ProductHuntPost> anotherList) {
    Collections.sort(anotherList, comparator);
    for (int i = items.size() - 1; i >= 0; i--) {
      ProductHuntPost post = items.get(i);
      if (Collections.binarySearch(anotherList, post, comparator) < 0) {
        items.remove(post);
      }
    }
  }

  @Override
  public void onAttachedToRecyclerView(RecyclerView recyclerView) {
    super.onAttachedToRecyclerView(recyclerView);
    this.context = recyclerView.getContext();
  }

  @Override
  public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
    this.context = null;
    super.onDetachedFromRecyclerView(recyclerView);
  }

  @Override
  public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
    return new PostViewHolder(view);
  }

  @Override
  public void onBindViewHolder(PostViewHolder holder, int position) {
    holder.bind(items.get(position));
  }

  @Override
  public int getItemCount() {
    return items.size();
  }

  class PostViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.post_item_thumbnail)
    ImageView thumbnail;
    @BindView(R.id.post_item_name)
    TextView name;
    @BindView(R.id.post_item_votes_count)
    TextView votesCount;
    @BindView(R.id.post_item_tagline)
    TextView tagline;

    PostViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    void bind(final ProductHuntPost post) {
      name.setText(post.getName());
      votesCount.setText(String.format(Locale.getDefault(), "%d", post.getVotesCount()));
      tagline.setText(post.getTagline());
      Picasso.with(context)
              .load(post.getThumbnail().getImageUrl())
              .centerCrop()
              .fit()
              .into(thumbnail);
      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          listener.onPostClicked(post);
        }
      });
    }
  }
}
