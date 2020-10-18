package com.example.libit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.NetworkImageView;
import com.example.libit.models.Category;
import com.example.libit.network.ImageRequester;
import com.example.libit.network.NetworkService;

public class ClickedCategoryActivity extends AppCompatActivity {
    private final String BASE_URL = NetworkService.getBaseUrl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked_category);

        ImageRequester imageRequester = ImageRequester.getInstance();
        TextView tvCategoryName = findViewById(R.id.categoryNameClicked);
        NetworkImageView imageCategory = findViewById(R.id.categoryImageClicked);

        Category category = (Category) getIntent().getSerializableExtra("category");
        if (category != null) {
            tvCategoryName.setText(category.getName());
            imageRequester.setImageFromUrl(imageCategory, BASE_URL + "/images/" + category.getImage());
        }

    }
}