package com.example.libit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.toolbox.NetworkImageView;
import com.example.libit.models.LoginView;
import com.example.libit.network.ImageRequester;
import com.example.libit.network.NetworkService;
import com.example.libit.network.Tokens;
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
        imageRequester.setImageFromUrl(editImage, "http://10.0.2.2:53558/images/testAvatarHen.jpg");
    }

    public void onClickSignIn(View view) {
        final TextInputEditText password = findViewById(R.id.input_password);
        final TextInputEditText email = findViewById(R.id.input_email);
//        if (email.getText().toString() == "" || password.getText().toString() == "") {
////            passwordLayout.setError("Fill all fields!");
//        } else {
////            passwordLayout.setError("");
//        }
        LoginView m = new LoginView();
        m.setEmail(email.getText().toString());
        m.setPassword(password.getText().toString());
        NetworkService.getInstance()
                .getJSONApi()
                .login(m)
                .enqueue(new Callback<Tokens>() {
                    @Override
                    public void onResponse(@NonNull Call<Tokens> call, @NonNull Response<Tokens> response) {
                        if (response.errorBody() == null && response.isSuccessful()) {
                            //passwordLayout.setError("");
                            //loginButton.setError("");
                            Tokens post = response.body();
                            // Toast toast = Toast.makeText(getApplicationContext(), "All done! your ref token :" + post.getRefreshToken(), Toast.LENGTH_LONG);
                            //toast.show();
                            //saveJWTToken(post.getToken(),post.getRefreshToken());
                            //CommonUtils.hideLoading();
                            //Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                            //startActivity(intent);
                        } else {
                            String error = "error";
                            //emailLayout.setError("");
                            //password.setError("Login or password was wrong");
                            //CommonUtils.hideLoading();
                            //passwordLayout.setError("Login or password was wrong");
                            //loginButton.setError("Login or password was wrong");
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