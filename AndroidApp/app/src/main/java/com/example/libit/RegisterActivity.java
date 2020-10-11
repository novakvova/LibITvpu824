package com.example.libit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.NetworkImageView;
import com.example.libit.data.UserRepository;
import com.example.libit.models.RegisterView;
import com.example.libit.network.ImageRequester;
import com.example.libit.network.NetworkService;
import com.example.libit.network.SessionManager;
import com.example.libit.network.Tokens;
import com.example.libit.network.utils.CommonUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private ImageRequester imageRequester;
    private NetworkImageView editImage;
    private final String BASE_URL = NetworkService.getBaseUrl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        imageRequester = ImageRequester.getInstance();
        editImage = findViewById(R.id.chooseImageRegister);
        imageRequester.setImageFromUrl(editImage, BASE_URL + "/images/testAvatarHen.jpg");
    }

    public void onClickRegister(View view) {
        final TextInputEditText email = findViewById(R.id.input_emailRegister);
        final TextInputLayout emailLayout = findViewById(R.id.emailLayoutRegister);

        final TextInputEditText password = findViewById(R.id.input_passwordRegister);
        final TextInputLayout passwordLayout = findViewById(R.id.passwordLayoutRegister);

        final TextInputEditText passwordConfirm = findViewById(R.id.input_passwordConfirmRegister);
        final TextInputLayout passwordConfirmLayout = findViewById(R.id.passwordConfirmLayoutRegister);

        boolean isCorrect = true;

        if (Objects.requireNonNull(email.getText()).toString().equals("")) {
            emailLayout.setError("Required field!");
            isCorrect = false;
        } else {
            emailLayout.setError(null);
        }

        if (Objects.requireNonNull(password.getText()).toString().equals("")) {
            passwordLayout.setError("Required field!");
            isCorrect = false;
        } else {
            passwordLayout.setError(null);
        }

        if (Objects.requireNonNull(passwordConfirm.getText()).toString().equals("")) {
            passwordConfirmLayout.setError("Required field!");
            isCorrect = false;
        } else if (!password.getText().toString().equals(passwordConfirm.getText().toString())) {
            passwordConfirmLayout.setError("Confirm your password!");
            isCorrect = false;
        } else {
            passwordConfirmLayout.setError(null);
        }

        if (!isCorrect)
            return;

        CommonUtils.showLoading(this);

        final RegisterView model = new RegisterView();
        model.setEmail(email.getText().toString());
        model.setPassword(password.getText().toString());

        NetworkService.getInstance()
                .getJSONApi()
                .register(model)
                .enqueue(new Callback<Tokens>() {
                    @Override
                    public void onResponse(@NonNull Call<Tokens> call, @NonNull Response<Tokens> response) {
                        CommonUtils.hideLoading();
                        if (response.errorBody() == null && response.isSuccessful()) {
                            Tokens token = response.body();
                            assert token != null;

                            SessionManager sessionManager = SessionManager.getInstance(RegisterActivity.this);
                            sessionManager.saveJWTToken(token.getToken());
                            sessionManager.saveUserLogin(model.getEmail());

                            Intent intent = new Intent(RegisterActivity.this, ProfileActivity.class);
                            startActivity(intent);
                        } else {
                            String errorMessage;
                            try {
                                assert response.errorBody() != null;
                                errorMessage = response.errorBody().string();
                            } catch (IOException e) {
                                errorMessage = response.message();
                                e.printStackTrace();
                            }
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    errorMessage, Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Tokens> call, @NonNull Throwable t) {
                        CommonUtils.hideLoading();
                        String error = "Error occurred while getting request!";
                        Toast toast = Toast.makeText(getApplicationContext(),
                                error, Toast.LENGTH_LONG);
                        toast.show();
                        t.printStackTrace();
                    }
                });
    }
}