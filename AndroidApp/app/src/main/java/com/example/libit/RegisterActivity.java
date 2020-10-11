package com.example.libit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.libit.models.LoginView;
import com.example.libit.models.RegisterView;
import com.example.libit.network.NetworkService;
import com.example.libit.network.Tokens;
import com.example.libit.network.utils.CommonUtils;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void saveJWTToken(String token) {
        SharedPreferences prefs;
        SharedPreferences.Editor edit;
        prefs = this.getSharedPreferences("jwtStore", Context.MODE_PRIVATE);
        edit = prefs.edit();
        try {
            edit.putString("token", token);
            Log.i("Login", token);
            edit.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void OnClickRegister(View view) {
        final EditText password = findViewById(R.id.editTextTextPassword);
        final EditText email = findViewById(R.id.editTextTextEmailAddress);

      //  CommonUtils.showLoading(this);
        RegisterView m = new RegisterView();
        m.setEmail(email.getText().toString());
        m.setPassword(password.getText().toString());
        NetworkService.getInstance()
                .getJSONApi()
                .register(m)
                .enqueue(new Callback<Tokens>() {
                    @Override
                    public void onResponse(@NonNull Call<Tokens> call, @NonNull Response<Tokens> response) {
                     //   CommonUtils.hideLoading();
                        Tokens token2 = response.body();
                       if (response.errorBody() == null && response.isSuccessful()) {
                            //passwordLayout.setError("");
                            //loginButton.setError("");
                            Tokens token = response.body();
                            saveJWTToken(token.getToken());
                          //   Toast toast = Toast.makeText(getApplicationContext(), "All done! your ref token :" + token.getToken(), Toast.LENGTH_LONG);
                         //   toast.show();
                           // saveJWTToken(post.getToken(),post.getRefreshToken());
                          //  CommonUtils.hideLoading();
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            //CommonUtils.hideLoading();
                            String error = "error";
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Login invalid!!! ",  Toast.LENGTH_LONG);
                            toast.show();

                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<Tokens> call, @NonNull Throwable t) {

                        //CommonUtils.hideLoading();
                        //textView.append("Error occurred while getting request!");
                        t.printStackTrace();
                    }
                });


    }
}