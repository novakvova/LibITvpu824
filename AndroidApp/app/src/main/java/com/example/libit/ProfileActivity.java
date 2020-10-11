package com.example.libit;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.NetworkImageView;
import com.example.libit.data.UserRepository;
import com.example.libit.models.UserView;
import com.example.libit.network.ImageRequester;
import com.example.libit.network.NetworkService;

public class ProfileActivity extends AppCompatActivity {
    private ImageRequester imageRequester;
    private NetworkImageView editImage;
    private final String BASE_URL = NetworkService.getBaseUrl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        imageRequester = ImageRequester.getInstance();

        editImage = findViewById(R.id.chooseImageProfile);
        TextView tvProfileName = findViewById(R.id.textProfileName);
        TextView tvProfileSurname = findViewById(R.id.textProfileSurname);
        TextView tvProfileBirthDate = findViewById(R.id.textProfileDateOfBirth);
        TextView tvProfilePhone = findViewById(R.id.textProfilePhone);

        UserRepository userRepo = UserRepository.getInstance();
        UserView model = userRepo.getUserProfile();

        imageRequester.setImageFromUrl(editImage, BASE_URL + "/images/" + model.getPhoto());
        tvProfileName.setText(model.getName());
        tvProfileSurname.setText(model.getSurname());
        tvProfileBirthDate.setText(model.getDateOfBirth());
        tvProfilePhone.setText(model.getPhone());
    }
}