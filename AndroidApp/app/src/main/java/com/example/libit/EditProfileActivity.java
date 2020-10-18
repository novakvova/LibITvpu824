package com.example.libit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.example.libit.models.ProfileEditView;
import com.example.libit.models.RegisterView;
import com.example.libit.models.UserView;
import com.example.libit.network.ImageRequester;
import com.example.libit.network.NetworkService;
import com.example.libit.network.SessionManager;
import com.example.libit.network.Tokens;
import com.example.libit.network.utils.CommonUtils;
import com.example.libit.network.utils.FileUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private Button btnSave;
    private UserView userProfile;
    TextView currentDateTime;
    Calendar dateAndTime = Calendar.getInstance();

    public static final int PICKFILE_RESULT_CODE = 1;
    private ImageRequester imageRequester;
    private NetworkImageView editImage;
    private String chooseImageBase64;
    private final String BASE_URL = NetworkService.getBaseUrl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        currentDateTime = (TextView) findViewById(R.id.currentDateTime);
        setInitialDateTime();

        btnSave = findViewById(R.id.btnSave);
        imageRequester = ImageRequester.getInstance();
        editImage = findViewById(R.id.chooseImageRegister);
        imageRequester.setImageFromUrl(editImage, BASE_URL + "/images/avatar.jpg");
    }

    public void setDate(View v) {
        new DatePickerDialog(EditProfileActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

// установка начальных даты и времени

    private void setInitialDateTime() {

        currentDateTime.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME));
    }

    // установка обработчика выбора даты

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }

    };

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

        final ProfileEditView model = new ProfileEditView();
        model.setName(name.getText().toString());
        model.setPhone(phone.getText().toString());
        model.setSurname(surname.getText().toString());
        model.setPhoto(chooseImageBase64);
       // model.setDateOfBirth(dateAndTime.getTime());


        NetworkService.getInstance()
                .getJSONApi()
                .edit(model)
                .enqueue(new Callback<UserView>() {
                    @Override
                    public void onResponse(@NonNull Call<UserView> call, @NonNull Response<UserView> response) {
                        CommonUtils.hideLoading();
                        if (response.errorBody() == null && response.isSuccessful()) {
                            assert response.body() != null;
                            userProfile = response.body();

                            //imageRequester.setImageFromUrl(editImage, BASE_URL + "/images/" + userProfile.getPhoto());
                            name.setText(userProfile.getName());
                            surname.setText(userProfile.getSurname());
                            // tvProfileBirthDate.setText(userProfile.getDateOfBirth());
                            phone.setText(userProfile.getPhone());
                            Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                            startActivity(intent);

                        } else {
                            userProfile = null;
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<UserView> call, @NonNull Throwable t) {
                        CommonUtils.hideLoading();
                        String error = "Error occurred while getting request!";
                        Toast toast = Toast.makeText(getApplicationContext(),
                                error, Toast.LENGTH_LONG);
                        toast.show();
                        t.printStackTrace();
                    }
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICKFILE_RESULT_CODE: {
                if (resultCode == -1) {
                    Uri fileUri = data.getData();
                    try {
                        File imgFile = FileUtils.from(getApplicationContext(), fileUri);
                        byte[] buffer = new byte[(int) imgFile.length() + 100];
                        int length = new FileInputStream(imgFile).read(buffer);
                        chooseImageBase64 = Base64.encodeToString(buffer, 0, length, Base64.NO_WRAP);
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        editImage.setImageBitmap(myBitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }

        }
    }

    public void onClickSelectImage(View view) {
        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("image/*");
        chooseFile = Intent.createChooser(chooseFile, "Оберіть фото");
        startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);
    }
}