package com.example.taskapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.task.R;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Scope;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView txtView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);


        recyclerView = findViewById(R.id.contactRecyclerView);
        txtView = findViewById(R.id.textBox);




        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/").addConverterFactory(GsonConverterFactory.create()).build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);


        Call<List<Post>> call = jsonPlaceHolderApi.getPost();


        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    txtView.setText("Error : " + response.code());
                    return;
                }
                ArrayList<Post> postList = new ArrayList<>();
                List<Post> posts = response.body();
                for (Post post : posts) {
                  postList.add(post);
                }
                ContactsRecViewAdapter contactsRecViewAdapter = new ContactsRecViewAdapter(postList);
                recyclerView.setAdapter(contactsRecViewAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }


            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });

    }
}