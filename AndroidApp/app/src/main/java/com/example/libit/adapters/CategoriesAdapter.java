package com.example.libit.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.libit.ClickedCategoryActivity;
import com.example.libit.R;
import com.example.libit.models.Category;
import com.example.libit.network.ImageRequester;
import com.example.libit.network.NetworkService;

import java.util.List;

public class CategoriesAdapter extends BaseAdapter {
    private List<Category> categories;
    private LayoutInflater layoutInflater;
    private ImageRequester imageRequester;
    private final String BASE_URL = NetworkService.getBaseUrl();
    private Context context;

    public CategoriesAdapter(List<Category> categories, Context context) {
        this.categories = categories;
        imageRequester = ImageRequester.getInstance();
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (categories == null)
            return 0;
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        if (categories == null)
            return null;
        if (categories.size() > position)
            return categories.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (categories.size() < position)
            return 0;

        return categories.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_category, parent, false);
        }
        TextView tvCategoryName = convertView.findViewById(R.id.categoryName);
        NetworkImageView imageCategory = convertView.findViewById(R.id.categoryImage);
        Button btn = convertView.findViewById(R.id.categoryButton);

        tvCategoryName.setText(categories.get(position).getName());
        imageRequester.setImageFromUrl(imageCategory, BASE_URL + "/images/" + categories.get(position).getImage());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Category category = categories.get(position);
                Intent intent = new Intent(context, ClickedCategoryActivity.class).
                        putExtra("category", category);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}
