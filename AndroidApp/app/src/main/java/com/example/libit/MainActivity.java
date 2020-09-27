package com.example.libit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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
        imageRequester.setImageFromUrl(editImage,"https://www.monsterenergy.com/media/uploads_image/2019/09/09/1600/800/6ea650cb9a77f4ac03b1c87fd8b21429.jpg?mod=v1_0485ae83b33a11d39f357c0a1ad1802f");
    }
}