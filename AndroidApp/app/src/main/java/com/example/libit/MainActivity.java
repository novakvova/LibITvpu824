package com.example.libit;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.NetworkImageView;
import com.example.libit.entities.Currency;
import com.example.libit.entities.Post;
import com.example.libit.network.ImageRequester;
import com.example.libit.network.NetworkService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        final TextView textView = findViewById(R.id.textView);

//        NetworkService.getInstance()
//                .getJSONApi()
//                .getPostWithID(1)
//                .enqueue(new Callback<Post>() {
//                    @Override
//                    public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
//                        Post post = response.body();
//
//                        textView.append(post.getId() + "\n");
//                        textView.append(post.getUserId() + "\n");
//                        textView.append(post.getTitle() + "\n");
//                        textView.append(post.getBody() + "\n");
//                    }
//
//                    @Override
//                    public void onFailure(@NonNull Call<Post> call, @NonNull Throwable t) {
//
//                        textView.append("Error occurred while getting request!");
//                        t.printStackTrace();
//                    }
//                });

                NetworkService.getInstance()
                .getJSONApi()
                .getCurrency()
                .enqueue(new Callback<List<Currency>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Currency>> call, @NonNull Response<List<Currency>> response) {
                        List<Currency> currencies = response.body();
                        for (Currency currency : currencies){
                            textView.append(currency.getCcy() + "\n");
                            textView.append(currency.getBase_ccy() + "\n");
                            textView.append(currency.getBuy() + "\n");
                            textView.append(currency.getSale() + "\n");

                            textView.append("\n\n");
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Currency>> call, @NonNull Throwable t) {

                        textView.append("Error occurred while getting request!");
                        t.printStackTrace();
                    }
                });
    }
}