package com.example.guardkey0;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UpdateActivity extends AppCompatActivity {
    EditText user_input, pass_input;
    TextView app_input,char_app;
    Button update_button, delete_button;
    ImageView goBack;
    String id, username, pass, app_n;
    TextView insertedMsg, errorMsg, emptyFiled;

    TextView strongPass,weakPass,msgPasswordlength;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        user_input = findViewById(R.id.title_input2);
        pass_input = findViewById(R.id.author_input2);
        app_input = findViewById(R.id.pages_input2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);
        msgTextView();

        //First we call this
        getAndSetIntentData();
        char_app = findViewById(R.id.char_app3);
        char_app.setText(String.valueOf(app_n.charAt(0)));

//731728

        //Set actionbar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(username);
        }

        goBack = (ImageView) findViewById(R.id.go_back);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateActivity.this, KeysActivity.class);
                startActivity(intent);
            }
        });

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //And only then we call this
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                username = user_input.getText().toString().trim();
                pass = pass_input.getText().toString().trim();



                if (user_input.length() != 0 && app_input.length() != 0 &&  pass_input.length() != 0) {
                    if (pass_input.length() > 4) {
                        if (checkString(pass)) {
                            myDB.updateData(id, username, pass);
                            user_input.setText("");
                            pass_input.setText("");
                            app_input.setText("");
                            weakPass.setVisibility(View.GONE);
                            strongPass.setVisibility(View.VISIBLE);//Password length must be greater then 6 letter

                        } else {
                            myDB.updateData(id, username, pass);
                            user_input.setText("");
                            pass_input.setText("");
                            app_input.setText("");
                            weakPass.setVisibility(View.VISIBLE);
                            emptyFiled.setVisibility(View.GONE);
                            strongPass.setVisibility(View.GONE);

                        }
                    } else {
                        myDB.updateData(id, username, pass);
                        user_input.setText("");
                        pass_input.setText("");
                        app_input.setText("");
                        weakPass.setVisibility(View.VISIBLE);
                        strongPass.setVisibility(View.GONE);
                        emptyFiled.setVisibility(View.GONE);
                        msgPasswordlength.setVisibility(View.VISIBLE);//Password length must be greater then 6 letter
                    }
                } else {
                    emptyFiled.setVisibility(View.VISIBLE);
                    errorMsg.setVisibility(View.GONE);
                    insertedMsg.setVisibility(View.GONE);
                    weakPass.setVisibility(View.GONE);
                    strongPass.setVisibility(View.GONE);
                    msgPasswordlength.setVisibility(View.GONE);
                }

            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
                //goToMainActivity();


            }
        });

    }

    private void goToMainActivity() {

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }, 3000); // 5000 milliseconds (5 seconds) delay

    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("username") &&
                getIntent().hasExtra("password") && getIntent().hasExtra("nameApp")){
            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            username = getIntent().getStringExtra("username");
            pass = getIntent().getStringExtra("password");
            app_n = getIntent().getStringExtra("nameApp");

            //Setting Intent Data
            user_input.setText(username);
            pass_input.setText(pass);
            app_input.setText(app_n);
            Log.d("stev", username +" "+ pass +" "+ app_n);
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + username + " ?");
        builder.setMessage("Are you sure you want to delete " + username + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);
                finish();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();

    }
    private void msgTextView() {
        msgPasswordlength = findViewById(R.id.PassLength2);
        insertedMsg = findViewById(R.id.insertedMsg);
        emptyFiled = findViewById(R.id.emptyF);
        errorMsg = findViewById(R.id.error);
        weakPass = findViewById(R.id.weakPass);
        strongPass = findViewById(R.id.StrongPass);

    }

    private static boolean checkString(String str) {
        char ch;
        boolean capitalFlag = false;
        boolean lowerCaseFlag = false;
        boolean numberFlag = false;
        for(int i=0;i < str.length();i++) {
            ch = str.charAt(i);
            if( Character.isDigit(ch)) {
                numberFlag = true;
            }
            else if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            } else if (Character.isLowerCase(ch)) {
                lowerCaseFlag = true;
            }
            if(numberFlag && capitalFlag && lowerCaseFlag)
                return true;
        }
        return false;
    }
}