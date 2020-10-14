package com.example.libit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.libit.network.utils.CommonUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity {

    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        btnSave = findViewById(R.id.btnSave);
    }

    public void onClickSave(View view) {
        final TextInputEditText name = findViewById(R.id.input_name);
        final TextInputLayout nameLayout = findViewById(R.id.nameLayout);
        final TextInputEditText surname = findViewById(R.id.input_surname);
        final TextInputLayout surnameLayout = findViewById(R.id.surnameLayout);
        final TextInputEditText phone = findViewById(R.id.input_phone);
        final TextInputLayout phoneLayout = findViewById(R.id.phoneLayout);
        boolean isCorrect = true;

        if (Objects.requireNonNull(name.getText()).toString().equals("")) {
            nameLayout.setError("Required field!");
            isCorrect = false;
        } else {
            nameLayout.setError(null);
        }

        if (Objects.requireNonNull(surname.getText()).toString().equals("")) {
            surnameLayout.setError("Required field!");
            isCorrect = false;
        } else {
            surnameLayout.setError(null);
        }

        if (Objects.requireNonNull(phone.getText()).toString().equals("")) {
            phoneLayout.setError("Required field!");
            isCorrect = false;
        } else {
            phoneLayout.setError(null);
        }

        if (!isCorrect)
            return;

        CommonUtils.showLoading(this);

    }
}