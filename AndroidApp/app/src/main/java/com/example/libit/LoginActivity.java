package com.example.libit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.example.libit.models.LoginView;
import com.example.libit.network.ImageRequester;
import com.example.libit.network.NetworkService;
import com.example.libit.network.Tokens;
import com.example.libit.network.utils.CommonUtils;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private ImageRequester imageRequester;
    private NetworkImageView editImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imageRequester = ImageRequester.getInstance();
        editImage = findViewById(R.id.chooseImageLogin);
        imageRequester.setImageFromUrl(editImage, "https://karpaty.tk/images/testAvatarHen.jpg");
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


    public void onClickSignIn(View view) {
        final TextInputEditText password = findViewById(R.id.input_password);
        final TextInputEditText email = findViewById(R.id.input_email);
//        if (email.getText().toString() == "" || password.getText().toString() == "") {
////            passwordLayout.setError("Fill all fields!");
//        } else {
////            passwordLayout.setError("");
//        }
        CommonUtils.showLoading(this);
        LoginView m = new LoginView();
        m.setEmail(email.getText().toString());
        m.setPassword(password.getText().toString());
        NetworkService.getInstance()
                .getJSONApi()
                .login(m)
                .enqueue(new Callback<Tokens>() {
                    @Override
                    public void onResponse(@NonNull Call<Tokens> call, @NonNull Response<Tokens> response) {
                        CommonUtils.hideLoading();
                        if (response.errorBody() == null && response.isSuccessful()) {
                            //passwordLayout.setError("");
                            //loginButton.setError("");
                            Tokens token = response.body();
                            saveJWTToken(token.getToken());
                            // Toast toast = Toast.makeText(getApplicationContext(), "All done! your ref token :" + post.getRefreshToken(), Toast.LENGTH_LONG);
                            //toast.show();
                            //saveJWTToken(post.getToken(),post.getRefreshToken());
                            //CommonUtils.hideLoading();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
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