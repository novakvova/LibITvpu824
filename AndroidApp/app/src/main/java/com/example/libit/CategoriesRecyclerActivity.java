package com.example.libit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.libit.adapters.CategoriesAdapter;
import com.example.libit.adapters.CategoryCardRecyclerViewAdapter;
import com.example.libit.decorations.CategoryGridItemDecoration;
import com.example.libit.models.Category;
import com.example.libit.network.NetworkService;
import com.example.libit.network.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesRecyclerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Category> categories;
    private CategoryCardRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_recycler);
        recyclerView = findViewById(R.id.recycler_view);

        setRecyclerView();

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
                            categories.clear();
                            categories.addAll(0,response.body());
                            adapter.notifyDataSetChanged();
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

    private void setRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1,
                GridLayoutManager.VERTICAL, false));
        categories = new ArrayList<>();
        adapter = new CategoryCardRecyclerViewAdapter(categories);

        recyclerView.setAdapter(adapter);

        int largePadding = 16;
        int smallPadding = 4;
        recyclerView.addItemDecoration(new CategoryGridItemDecoration(largePadding, smallPadding));
    }
}