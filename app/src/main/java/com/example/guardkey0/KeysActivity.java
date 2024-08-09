package com.example.guardkey0;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guardkey0.models.userModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class KeysActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView add_button;
    ImageView empty_imageview;
    TextView no_data;
    ImageView chat_img;
    MyDatabaseHelper myDB;
    ImageView btnPopup;

    ArrayList<String> id, username, password, appname;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_keys);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });




        btnPopup = findViewById(R.id.iPopup);
        registerForContextMenu(btnPopup);


        chat_img = findViewById(R.id.chat_img);
        recyclerView = findViewById(R.id.recyclerView);
        add_button =  findViewById(R.id.add_button);
        empty_imageview = findViewById(R.id.empty_imageview);
        no_data = findViewById(R.id.no_data);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KeysActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
        chat_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KeysActivity.this, Chatbot.class);
                startActivity(intent);
                finish();

            }
        });

        myDB = new MyDatabaseHelper(KeysActivity.this);
        id = new ArrayList<>();
        username = new ArrayList<>();
        password = new ArrayList<>();
        appname = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(KeysActivity.this,this, id, username, password,
                appname);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(KeysActivity.this));

    }


    //for menu items


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.menu_item,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.log_out) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(KeysActivity.this, login.class);
        //not letting the user to be able to return back if he sign out
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);        }
        return super.onContextItemSelected(item);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            empty_imageview.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                id.add(cursor.getString(0));
                username.add(cursor.getString(1));
                password.add(cursor.getString(2));
                appname.add(cursor.getString(3));
            }
            empty_imageview.setVisibility(View.GONE);
        }
    }


}
