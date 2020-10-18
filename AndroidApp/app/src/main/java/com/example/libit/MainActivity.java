package com.example.libit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.toolbox.NetworkImageView;
import com.example.libit.network.ImageRequester;
import com.example.libit.network.NetworkService;

public class MainActivity extends AppCompatActivity {
    private ImageRequester imageRequester;
    private NetworkImageView editImage;
    private final String BASE_URL = NetworkService.getBaseUrl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






        imageRequester = ImageRequester.getInstance();
        editImage = findViewById(R.id.chooseImage);
        imageRequester.setImageFromUrl(editImage, BASE_URL + "/images/testAvatarHen.jpg");
    }

    public void btnRegistration(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void btnLogin(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void onClickList(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);

    }
}