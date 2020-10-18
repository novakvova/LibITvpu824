package com.example.libit.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libit.models.Category;
import com.example.libit.network.ImageRequester;
import com.example.libit.network.NetworkService;
import com.example.libit.viewHolders.CategoryCardViewHolder;
import com.example.libit.R;

import java.util.List;

public class CategoryCardRecyclerViewAdapter extends RecyclerView.Adapter<CategoryCardViewHolder> {

    private List<Category> categoryList;
    private ImageRequester imageRequester;
    private final String BASE_URL = NetworkService.getBaseUrl();

    public CategoryCardRecyclerViewAdapter(List<Category> categoryListList) {
        this.categoryList = categoryListList;
        imageRequester = ImageRequester.getInstance();
    }

    @NonNull
    @Override
    public CategoryCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_card, parent, false);
        return new CategoryCardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryCardViewHolder holder, int position) {
        if (categoryList != null && position < categoryList.size()) {
            Category category = categoryList.get(position);
            holder.category_name.setText(category.getName());
            String url = BASE_URL + "/images/" + category.getImage();
            imageRequester.setImageFromUrl(holder.category_image, url);
        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
