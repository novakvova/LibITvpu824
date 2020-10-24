package com.example.libit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.libit.models.Book;
import com.example.libit.network.JSONPlaceHolderApi;
import com.example.libit.network.NetworkService;
import com.example.libit.network.utils.CommonUtils;
import com.example.libit.network.utils.FileUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadActivity extends AppCompatActivity {
    private TextView tvBase;
    private static final int PICKFILE_RESULT_CODE = 1;
    private final String BASE_URL = NetworkService.getBaseUrl();
    private Book addedBook;
    private String chooseBookBase64;
   // private String chooseImageBase64;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        tvBase = findViewById(R.id.textView);
    }

    public void onClickUpload(View view) {



        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
       // intent.setType("application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, PICKFILE_RESULT_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICKFILE_RESULT_CODE: {
                if (resultCode == -1) {
                    Uri fileUri = data.getData();
                    try {
//                       // File dir = Environment.getExternalStorageDirectory();
                        File imgFile = FileUtils.from(getApplicationContext(), fileUri);
//                       // File yourFile = new File(dir, "path/to/the/file/inside/the/sdcard.ext");
//
//                        InputStream in = getContentResolver().openInputStream(fileUri);
//                        byte[] buffer = new byte[8192];
//                        int bytesRead;
//                        ByteArrayOutputStream output = new ByteArrayOutputStream();
//                        Base64OutputStream output64 = new Base64OutputStream(output, Base64.DEFAULT);
//                        try {
//                            while ((bytesRead = in.read(buffer)) != -1) {
//                                output64.write(buffer, 0, bytesRead);
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        output64.close();
//
//                        String attachedFile = output.toString();
//                        tvBase.setText(attachedFile);


                        String src = fileUri.getPath();
                        File source = new File(src);
                        String filename = fileUri.getLastPathSegment();
                        File destination = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CustomFolder/" + filename);
                       // File destination2 =  new File(NetworkService.getBaseUrl() + "/images/"+ filename);
                       // copy(source, destination);





                        String encodeFileToBase64Binary;// = encodeFileToBase64Binary(imgFile);
                        int size = (int) imgFile.length();
                        byte[] bytes = new byte[size];
                        try {
                            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(imgFile));
                            buf.read(bytes, 0, bytes.length);
                            buf.close();
                        } catch (FileNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        String encoded = Base64.encodeToString(bytes,Base64.NO_WRAP);
                        chooseBookBase64 = encoded;
                        tvBase.setText(encoded);




//                        InputStream in = getContentResolver().openInputStream(fileUri);
//                        byte[] bytes=getBytes(in);
//                        File imgFile = FileUtils.from(getApplicationContext(), fileUri);
//                        byte[] buffer = new byte[(int) imgFile.length() + 10000];
//                        int length = new FileInputStream(imgFile).read(buffer);
//                        chooseImageBase64 = Base64.encodeToString(buffer, 0, length, Base64.NO_WRAP);
//                        String document=Base64.encodeToString(bytes,Base64.DEFAULT);
//                        tvBase.setText(document);
////                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
////                        editImage.setImageBitmap(myBitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }




//                    String src = uri.getPath();
////                    String filePath = uri.getPath();
////                    Toast.makeText(getApplicationContext(), filePath,
////                            Toast.LENGTH_LONG).show();
//                    File source = new File(src);
//                    String filename = uri.getLastPathSegment();
//                    File destination = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CustomFolder/" + filename);
//                    try {
//                        copy(source, destination);
//                    }
//                    catch (IOException e) {
//                        e.printStackTrace();
//                    }


//
                }
                break;
            }
        }

    }




    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    public void onClickSave(View view) {

        final TextInputEditText name = findViewById(R.id.input_name);
        final TextInputLayout nameLayout = findViewById(R.id.nameLayout);
        final TextInputEditText author = findViewById(R.id.input_author);
        final TextInputLayout authorLayout = findViewById(R.id.authorLayout);
        final TextInputEditText category = findViewById(R.id.input_category);
        final TextInputLayout categoryLayout = findViewById(R.id.categoryLayout);

        final Book model = new Book();
        model.setName(name.getText().toString());
        model.setAuthor(author.getText().toString());
        model.setCategoryId(Integer.parseInt(category.getText().toString()));
        model.setImage(chooseBookBase64);


        CommonUtils.showLoading(this);
        NetworkService.getInstance()
                .getJSONApi()
                .addBook(model)
                .enqueue(new Callback<Book>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(@NonNull Call<Book> call, @NonNull Response<Book> response) {
                        CommonUtils.hideLoading();
                        if (response.errorBody() == null && response.isSuccessful()) {
                            assert response.body() != null;
                            addedBook = response.body();

                            String succeed = "Add succeed";
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    succeed, Toast.LENGTH_LONG);
                            toast.show();
                        }
                     else {
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
                    public void onFailure(@NonNull Call<Book> call, @NonNull Throwable t) {
                        CommonUtils.hideLoading();
                        addedBook = null;
                        t.printStackTrace();
                    }
                });
    }



}