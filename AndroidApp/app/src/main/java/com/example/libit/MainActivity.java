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
        //String baseUrl="http://10.0.2.2:53558/";
        String baseUrl="https://karpaty.tk/";
        imageRequester.setImageFromUrl(editImage,baseUrl+"images/belka.jpg");
    }
}