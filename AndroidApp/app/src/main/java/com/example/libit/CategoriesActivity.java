package com.example.libit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.libit.adapters.CategoriesAdapter;
import com.example.libit.models.Category;
import com.example.libit.network.NetworkService;
import com.example.libit.network.utils.CommonUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesActivity extends AppCompatActivity {
    private GridView gridView;
    private List<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        gridView = findViewById(R.id.gridViewCategories);

        CommonUtils.showLoading(this);
        NetworkService.getInstance()
                .getJSONApi()
                .getCategories()
                .enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Category>> call, @NonNull Response<List<Category>> response) {
                        CommonUtils.hideLoading();
                        if (response.errorBody() == null && response.isSuccessful()) {
                            assert response.body() != null;
                            categories = response.body();
                            CategoriesAdapter adapter = new CategoriesAdapter(categories, CategoriesActivity.this);

                            gridView.setAdapter(adapter);
                            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Category category = categories.get(position);
                                    Intent intent = new Intent(CategoriesActivity.this, ClickedCategoryActivity.class).
                                            putExtra("category", category);
                                    startActivity(intent);
                                }
                            });

                        } else {
                            categories = null;
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Category>> call, @NonNull Throwable t) {
                        CommonUtils.hideLoading();
                        categories = null;
                        t.printStackTrace();
                    }
                });
    }
}