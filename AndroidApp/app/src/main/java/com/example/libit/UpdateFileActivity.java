package com.example.libit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.libit.models.ServerResponse;
import com.example.libit.network.NetworkService;
import com.example.libit.network.utils.FileUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateFileActivity extends AppCompatActivity {
    Button btnUpload, btnPickFile;
    Uri mediaPath;
    ImageView imgView;
    ProgressDialog progressDialog;
    TextView str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_file);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");

        btnUpload = findViewById(R.id.upload_file);
        btnPickFile = findViewById(R.id.pick_file);
        imgView = findViewById(R.id.preview_file);
        str = findViewById(R.id.filename);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    uploadFile();
                } catch (IOException e) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            e.getMessage(), Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        btnPickFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            Uri fileUri = data.getData();
            if (fileUri != null) {
                str.setText(fileUri.toString());
                mediaPath = fileUri;
            }
        }
    }

    private void uploadFile() throws IOException {
        progressDialog.show();
        File file = FileUtils.from(UpdateFileActivity.this, mediaPath);
        // creates RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("*/*"), file);
        // MultipartBody.Part is used to send also the actual filename
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        // adds another part within the multipart request
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        NetworkService.getInstance()
                .getJSONApi()
                .uploadFile(body, description)
                .enqueue(new Callback<ServerResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ServerResponse> call, @NonNull Response<ServerResponse> response) {
                        progressDialog.hide();
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Cool", Toast.LENGTH_LONG);
                        toast.show();
                    }

                    @Override
                    public void onFailure(@NonNull Call<ServerResponse> call, @NonNull Throwable t) {
                        progressDialog.hide();
                        String error = "Error occurred while getting request!";
                        Toast toast = Toast.makeText(getApplicationContext(),
                                error, Toast.LENGTH_LONG);
                        toast.show();
                        t.printStackTrace();
                    }
                });
    }
}