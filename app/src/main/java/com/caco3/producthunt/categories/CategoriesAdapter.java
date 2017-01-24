package com.caco3.producthunt.categories;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.caco3.producthunt.R;
import com.caco3.producthunt.producthunt.category.ProductHuntCategory;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.caco3.producthunt.util.Preconditions.checkNotNull;


class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder> {
  interface UiEventsListener {
    void onItemClicked(ProductHuntCategory category);
  }

  private UiEventsListener listener;
  private Comparator<ProductHuntCategory> comparator;
  private SortedList<ProductHuntCategory> items = new SortedList<>(ProductHuntCategory.class, new SortedList.Callback<ProductHuntCategory>() {
    @Override
    public int compare(ProductHuntCategory o1, ProductHuntCategory o2) {
      return comparator.compare(o1, o2);
    }

    @Override
    public void onChanged(int position, int count) {
      notifyItemRangeChanged(position, count);
    }

    @Override
    public boolean areContentsTheSame(ProductHuntCategory oldItem, ProductHuntCategory newItem) {
      return oldItem.equals(newItem);
    }

    @Override
    public boolean areItemsTheSame(ProductHuntCategory item1, ProductHuntCategory item2) {
      return item1.getCategoryId() == item2.getCategoryId();
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

  CategoriesAdapter(UiEventsListener uiEventsListener,
                           Comparator<ProductHuntCategory> categoryComparator) {
    this.listener = checkNotNull(uiEventsListener, "uiEventsListener == null");
    this.comparator = checkNotNull(categoryComparator, "categoryComparator == null");
  }

  void setItems(List<ProductHuntCategory> newCategories) {
    items.beginBatchedUpdates();
    removeAllThatAreNotIn(newCategories);
    items.addAll(newCategories);
    items.endBatchedUpdates();
  }

  private void removeAllThatAreNotIn(List<ProductHuntCategory> anotherList) {
    Collections.sort(anotherList, comparator);
    for (int i = items.size() - 1; i >= 0; i--) {
      ProductHuntCategory category = items.get(i);
      if (Collections.binarySearch(anotherList, category, comparator) < 0) {
        items.remove(category);
      }
    }
  }

  @Override
  public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.category_item, parent, false);
    return new CategoryViewHolder(view);
  }

  @Override
  public void onBindViewHolder(CategoryViewHolder holder, int position) {
    holder.bind(items.get(position));
  }

  @Override
  public int getItemCount() {
    return items.size();
  }

  class CategoryViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.category_item_name)
    TextView name;

    CategoryViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    void bind(final ProductHuntCategory category) {
      name.setText(category.getName());
      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          listener.onItemClicked(category);
        }
      });

    }
  }
}
