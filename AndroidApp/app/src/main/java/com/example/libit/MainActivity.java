package com.example.libit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.toolbox.NetworkImageView;
import com.example.libit.network.ImageRequester;

public class MainActivity extends AppCompatActivity {
    private ImageRequester imageRequester;
    private NetworkImageView editImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageRequester = ImageRequester.getInstance();
        editImage = findViewById(R.id.chooseImage);
        imageRequester.setImageFromUrl(editImage,"https://karpaty.tk/images/testAvatarHen.jpg");
    }

    public void btnLogin(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}