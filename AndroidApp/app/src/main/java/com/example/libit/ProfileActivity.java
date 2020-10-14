package com.example.libit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.NetworkImageView;
import com.example.libit.models.UserView;
import com.example.libit.network.ImageRequester;
import com.example.libit.network.NetworkService;
import com.example.libit.network.SessionManager;
import com.example.libit.network.utils.CommonUtils;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.Calendar;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private ImageRequester imageRequester;
    private NetworkImageView editImage;
    private final String BASE_URL = NetworkService.getBaseUrl();
    private UserView userProfile;
    private final Calendar calendar = Calendar.getInstance();
    private TextInputEditText textEditName;
    private TextInputEditText textEditSurname;
    private TextInputEditText textEditBirthDate;
    private TextInputEditText textEditPhone;
    private TextInputEditText textEditEmail;

    private void showDatePicker(View view) {
        //hide keyboard
        InputMethodManager imm = (InputMethodManager) ProfileActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        // date picker dialog
        DatePickerDialog datePicker = new DatePickerDialog(ProfileActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int yearSelect, int monthSelect, int daySelect) {
                        calendar.set(yearSelect, monthSelect, daySelect);
                        textEditBirthDate.setText(daySelect + "/" + (monthSelect + 1) + "/" + yearSelect);
                    }
                }, year, month, day);
        datePicker.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        imageRequester = ImageRequester.getInstance();

        editImage = findViewById(R.id.chooseImageProfile);
        textEditName = findViewById(R.id.textProfileName);
        textEditSurname = findViewById(R.id.textProfileSurname);

        textEditBirthDate = findViewById(R.id.textProfileDateOfBirth);
        textEditBirthDate.setInputType(InputType.TYPE_NULL);

        textEditPhone = findViewById(R.id.textProfilePhone);
        textEditEmail = findViewById(R.id.textProfileEmail);
        textEditBirthDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    showDatePicker(view);
                }
            }
        });

        textEditBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(view);
            }
        });

        NetworkService.getInstance()
                .getJSONApi()
                .profile()
                .enqueue(new Callback<UserView>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(@NonNull Call<UserView> call, @NonNull Response<UserView> response) {
                        if (response.errorBody() == null && response.isSuccessful()) {
                            assert response.body() != null;
                            userProfile = response.body();

                            imageRequester.setImageFromUrl(editImage, BASE_URL + "/images/" + userProfile.getPhoto());
                            textEditName.setText(userProfile.getName());
                            textEditSurname.setText(userProfile.getSurname());

                            calendar.setTime(userProfile.getDateOfBirth());
                            int day = calendar.get(Calendar.DAY_OF_MONTH);
                            int month = calendar.get(Calendar.MONTH);
                            int year = calendar.get(Calendar.YEAR);
                            textEditBirthDate.setText(day + "/" + (month + 1) + "/" + year);

                            textEditPhone.setText(userProfile.getPhone());
                            textEditEmail.setText((userProfile.getEmail()));
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

    public void onClickUpdate(View view) {
        final UserView userUpdate = new UserView();
        userUpdate.setName(Objects.requireNonNull(textEditName.getText()).toString());
        userUpdate.setSurname(Objects.requireNonNull(textEditSurname.getText()).toString());

        userUpdate.setDateOfBirth(calendar.getTime());
        userUpdate.setPhone(Objects.requireNonNull(textEditPhone.getText()).toString());
        userUpdate.setEmail(Objects.requireNonNull(textEditEmail.getText()).toString());

        userUpdate.setPhoto(userProfile.getPhoto());
        userUpdate.setRegistrationDate(userProfile.getRegistrationDate());

        if (userUpdate.equals(userProfile)) {
            String warnings = "Nothing to update!";
            Toast toast = Toast.makeText(getApplicationContext(),
                    warnings, Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        CommonUtils.showLoading(this);

        NetworkService.getInstance()
                .getJSONApi()
                .update(userUpdate)
                .enqueue(new Callback<UserView>() {
                    @Override
                    public void onResponse(@NonNull Call<UserView> call, @NonNull Response<UserView> response) {
                        CommonUtils.hideLoading();
                        if (response.errorBody() == null && response.isSuccessful()) {
                            userProfile = response.body();

                            SessionManager sessionManager = SessionManager.getInstance(ProfileActivity.this);
                            sessionManager.saveUserLogin(userProfile.getEmail());

                            String succeed = "Update have been done";
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    succeed, Toast.LENGTH_LONG);
                            toast.show();
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
}