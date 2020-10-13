package com.example.libit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.NetworkImageView;
import com.example.libit.data.UserRepository;
import com.example.libit.models.UserView;
import com.example.libit.network.ImageRequester;
import com.example.libit.network.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    public static final int PICKFILE_RESULT_CODE = 1;

    private ImageRequester imageRequester;
    private NetworkImageView editImage;
    private final String BASE_URL = NetworkService.getBaseUrl();
    private UserView userProfile;
    TextView tvProfileName;
    TextView tvProfileSurname;
    TextView tvProfileBirthDate;
    TextView tvProfilePhone;

    private Button btnSelectImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        imageRequester = ImageRequester.getInstance();

        editImage = findViewById(R.id.chooseImageProfile);
        tvProfileName = findViewById(R.id.textProfileName);
        tvProfileSurname = findViewById(R.id.textProfileSurname);
        tvProfileBirthDate = findViewById(R.id.textProfileDateOfBirth);
        tvProfilePhone = findViewById(R.id.textProfilePhone);

        btnSelectImage = findViewById(R.id.btnSelectImage);

        NetworkService.getInstance()
                .getJSONApi()
                .profile()
                .enqueue(new Callback<UserView>() {
                    @Override
                    public void onResponse(@NonNull Call<UserView> call, @NonNull Response<UserView> response) {
                        if (response.errorBody() == null && response.isSuccessful()) {
                            assert response.body() != null;
                            userProfile = response.body();

                            imageRequester.setImageFromUrl(editImage, BASE_URL + "/images/" + userProfile.getPhoto());
                            tvProfileName.setText(userProfile.getName());
                            tvProfileSurname.setText(userProfile.getSurname());
                            tvProfileBirthDate.setText(userProfile.getDateOfBirth());
                            tvProfilePhone.setText(userProfile.getPhone());

                        } else {
                            userProfile = null;
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<UserView> call, @NonNull Throwable t) {
                        userProfile = null;
                        t.printStackTrace();
                    }
                });
    }

    public void btnSelectImageCkick(View view) {
        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("image/*");
        chooseFile = Intent.createChooser(chooseFile, "Оберіть фото");
        startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);

    }
}