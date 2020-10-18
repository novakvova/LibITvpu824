package com.example.libit.viewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.libit.R;


public class CategoryCardViewHolder extends RecyclerView.ViewHolder {

    public NetworkImageView category_image;
    public TextView category_name;
    private View view;

    public CategoryCardViewHolder(@NonNull View itemView) {
        super(itemView);
        this.view = itemView;
        category_image = itemView.findViewById(R.id.category_image);
        category_name = itemView.findViewById(R.id.category_name);
    }

    public View getView() {
        return view;
    }
}
