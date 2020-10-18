package com.example.libit.viewHolders;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.libit.CategoriesActivity;
import com.example.libit.ClickedCategoryActivity;
import com.example.libit.R;
import com.example.libit.models.Category;
import com.google.android.material.button.MaterialButton;


public class CategoryCardViewHolder extends RecyclerView.ViewHolder {

    public NetworkImageView category_image;
    public TextView category_name;
    private View view;
    public MaterialButton category_button;

    public CategoryCardViewHolder(@NonNull View itemView) {
        super(itemView);
        this.view = itemView;
        category_image = itemView.findViewById(R.id.category_image);
        category_name = itemView.findViewById(R.id.category_name);
        category_button = itemView.findViewById(R.id.category_button);

//        category_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Category category = new Category(1, "Hello", "belka.jpg");
//                Intent intent = new Intent(view.getContext(), ClickedCategoryActivity.class).
//                        putExtra("category", category);
//                view.getContext().startActivity(intent);
//            }
//        });
    }

    public View getView() {
        return view;
    }
}
