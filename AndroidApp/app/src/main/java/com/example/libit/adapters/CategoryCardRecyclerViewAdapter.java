package com.example.libit.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libit.ClickedCategoryActivity;
import com.example.libit.click_listeners.OnDeleteListener;
import com.example.libit.models.Category;
import com.example.libit.network.ImageRequester;
import com.example.libit.network.NetworkService;
import com.example.libit.viewHolders.CategoryCardViewHolder;
import com.example.libit.R;

import java.util.List;

public class CategoryCardRecyclerViewAdapter extends RecyclerView.Adapter<CategoryCardViewHolder> {

    private List<Category> categoryList;
    private ImageRequester imageRequester;
    private OnDeleteListener deleteListener;
    private Context context;
    private final String BASE_URL = NetworkService.getBaseUrl();

    public CategoryCardRecyclerViewAdapter(List<Category> categoryListList, OnDeleteListener deleteListener, Context context) {
        this.categoryList = categoryListList;
        imageRequester = ImageRequester.getInstance();
        this.deleteListener = deleteListener;
        this.context=context;
    }

    @NonNull
    @Override
    public CategoryCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_card, parent, false);
        return new CategoryCardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(final @NonNull CategoryCardViewHolder holder, final int position) {
        if (categoryList != null && position < categoryList.size()) {
            final Category category = categoryList.get(position);
            holder.category_name.setText(category.getName());

            holder.getView().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    deleteListener.deleteItem(categoryList.get(position));
                    return true;
                }
            });

            holder.category_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.itemView.getContext(), ClickedCategoryActivity.class).
                            putExtra("category", category);
                    holder.itemView.getContext().startActivity(intent);
                }
            });
            String url = BASE_URL + "/images/" + category.getImage();
            imageRequester.setImageFromUrl(holder.category_image, url);
        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
