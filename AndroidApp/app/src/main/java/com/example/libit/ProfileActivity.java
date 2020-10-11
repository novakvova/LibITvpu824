package com.example.libit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.libit.models.ProfileView;
import com.example.libit.models.RegisterView;
import com.example.libit.network.NetworkService;
import com.example.libit.network.Tokens;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    TextView txtToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle arguments = getIntent().getExtras();
        String userToken = arguments.get("token").toString();
        txtToken = findViewById(R.id.textView);
        txtToken.append(userToken);
    }

    public void OnClickInfo(View view) {
        final EditText email = findViewById(R.id.editTextTextEmailAddress);
        String userToken = txtToken.getText().toString();

        ProfileView profile = new ProfileView();
        profile.setEmail(email.getText().toString());
                NetworkService.getInstance()
                .getJSONApi()
                       .showInfo("Bearer "+ userToken)
               // .showInfo(userToken)
                .enqueue(new Callback<ProfileView>() {
                    @Override
                    public void onResponse(@NonNull Call<ProfileView> call, @NonNull Response<ProfileView> response) {
                        //   CommonUtils.hideLoading();
                        ProfileView user = response.body();
                        email.append("Email: " + user.getEmail() + "\n");

                    }

                    @Override
                    public void onFailure(@NonNull Call<ProfileView> call, @NonNull Throwable t) {

                        //CommonUtils.hideLoading();
                        //textView.append("Error occurred while getting request!");
                        t.printStackTrace();
                    }
                });

    }
}