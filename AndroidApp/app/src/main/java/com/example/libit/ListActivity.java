package com.example.libit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.libit.data.UserAdapter;
import com.example.libit.models.ProfileView;
import com.example.libit.network.NetworkService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {
//    private List<State> states = new ArrayList();
//    ListView countriesList;

    private List<ProfileView> users = new ArrayList();

    ListView usersList;
    private ProfileView userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

//        // начальная инициализация списка
//        setInitialData();
//        // получаем элемент ListView
//        countriesList = (ListView) findViewById(R.id.listView);
//        // создаем адаптер
//        StateAdapter stateAdapter = new StateAdapter(this, R.layout.list_item, states);
//        // устанавливаем адаптер
//        countriesList.setAdapter(stateAdapter);
//        // слушатель выбора в списке
//        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//
//                // получаем выбранный пункт
//                State selectedState = (State)parent.getItemAtPosition(position);
//                Toast.makeText(getApplicationContext(), "Был выбран пункт " + selectedState.getName(),
//                        Toast.LENGTH_SHORT).show();
//            }
//        };
//        countriesList.setOnItemClickListener(itemListener);


//        NetworkService.getInstance()
//                .getJSONApi()
//                .getuserslist()
//                .enqueue(new Callback<List<ProfileView>>() {
//                    @Override
//                    public void onResponse(@NonNull Call<List<ProfileView>> call, @NonNull Response<List<ProfileView>> response) {
//                        if (response.errorBody() == null && response.isSuccessful()) {
//                            assert response.body() != null;
//                            List<ProfileView> retUsers = response.body();
//                            users = response.body();
//
//
//                            usersList = (ListView) findViewById(R.id.listView);
//                            // создаем адаптер
//                            UserAdapter userAdapter = new UserAdapter(ListActivity.this, R.layout.list_item, users);
//                            // устанавливаем адаптер
//                            usersList.setAdapter(userAdapter);
//                            // слушатель выбора в списке
//                            AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//
//                                    // получаем выбранный пункт
//                                    ProfileView selectedUser = (ProfileView)parent.getItemAtPosition(position);
//                                    Toast.makeText(getApplicationContext(), "Был выбран пункт " + selectedUser.getName(),
//                                            Toast.LENGTH_SHORT).show();
//                                }
//                            };
//                            usersList.setOnItemClickListener(itemListener);
//
//                            for (ProfileView item: retUsers) {
//                                Toast.makeText(getApplicationContext(), "Был выбран пункт " + item.getName(),
//                                        Toast.LENGTH_SHORT).show();
//                            }
//
//
////                            imageRequester.setImageFromUrl(editImage, BASE_URL + "/images/" + userProfile.getPhoto());
////                            tvProfileName.setText(userProfile.getName());
////                            tvProfileSurname.setText(userProfile.getSurname());
////                            tvProfileBirthDate.setText(userProfile.getDateOfBirth());
////                            tvProfilePhone.setText(userProfile.getPhone());
//
//                        } else {
//                            userProfile = null;
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(@NonNull Call<List<ProfileView>> call, @NonNull Throwable t) {
//                        userProfile = null;
//                        t.printStackTrace();
//                    }
//                });












//        // начальная инициализация списка
//        //setInitialData();
//        // получаем элемент ListView
//        usersList = (ListView) findViewById(R.id.listView);
//        // создаем адаптер
//        UserAdapter userAdapter = new UserAdapter(this, R.layout.list_item, users);
//        // устанавливаем адаптер
//        usersList.setAdapter(userAdapter);
//        // слушатель выбора в списке
//        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//
//                // получаем выбранный пункт
//                ProfileView selectedUser = (ProfileView)parent.getItemAtPosition(position);
//                Toast.makeText(getApplicationContext(), "Был выбран пункт " + selectedUser.getName(),
//                        Toast.LENGTH_SHORT).show();
//            }
//        };
//        usersList.setOnItemClickListener(itemListener);




    }

//    private void setInitialData(){
//
//        states.add(new State ("Бразилия", "Бразилиа", R.drawable.currencies));
//        states.add(new State ("Аргентина", "Буэнос-Айрес", R.drawable.currencies));
//        states.add(new State ("Колумбия", "Богота", R.drawable.currencies));
//        states.add(new State ("Уругвай", "Монтевидео", R.drawable.currencies));
//        states.add(new State ("Чили", "Сантьяго", R.drawable.currencies));
//    }

//    private void setInitialData(){
//
////        states.add(new State ("Бразилия", "Бразилиа", R.drawable.currencies));
////        states.add(new State ("Аргентина", "Буэнос-Айрес", R.drawable.currencies));
////        states.add(new State ("Колумбия", "Богота", R.drawable.currencies));
////        states.add(new State ("Уругвай", "Монтевидео", R.drawable.currencies));
////        states.add(new State ("Чили", "Сантьяго", R.drawable.currencies));
//
//
//        NetworkService.getInstance()
//                .getJSONApi()
//                .getuserslist()
//                .enqueue(new Callback<List<ProfileView>>() {
//                    @Override
//                    public void onResponse(@NonNull Call<List<ProfileView>> call, @NonNull Response<List<ProfileView>> response) {
//                        if (response.errorBody() == null && response.isSuccessful()) {
//                            assert response.body() != null;
//                            List<ProfileView> retUsers = response.body();
//                            users = response.body();
//
//                            for (ProfileView item: retUsers) {
//                                Toast.makeText(getApplicationContext(), "Был выбран пункт " + item.getName(),
//                                        Toast.LENGTH_SHORT).show();
//                            }
//
//
////                            imageRequester.setImageFromUrl(editImage, BASE_URL + "/images/" + userProfile.getPhoto());
////                            tvProfileName.setText(userProfile.getName());
////                            tvProfileSurname.setText(userProfile.getSurname());
////                            tvProfileBirthDate.setText(userProfile.getDateOfBirth());
////                            tvProfilePhone.setText(userProfile.getPhone());
//
//                        } else {
//                            userProfile = null;
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(@NonNull Call<List<ProfileView>> call, @NonNull Throwable t) {
//                        userProfile = null;
//                        t.printStackTrace();
//                    }
//                });
//    }
}