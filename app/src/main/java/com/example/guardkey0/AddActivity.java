package com.example.guardkey0;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddActivity extends AppCompatActivity {
    EditText user_input, pass_input, app_input;
    Button add_button,btnCancel;
    ImageView goBack;
    MyDatabaseHelper myDB;
    TextView insertedMsg, errorMsg, emptyFiled;

    TextView strongPass,weakPass,msgPasswordlength ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        msgTextView();

        myDB = new MyDatabaseHelper(AddActivity.this);

        goBack = (ImageView) findViewById(R.id.btnView);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        user_input = findViewById(R.id.title_input);
        pass_input = findViewById(R.id.author_input);
        app_input = findViewById(R.id.pages_input);


        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String newEntry = user_input.getText().toString();
                String newEntry2 = pass_input.getText().toString();
                String newEntry3 = app_input.getText().toString();


                if (user_input.length() != 0 && app_input.length() != 0 &&  pass_input.length() != 0) {
                    if (pass_input.length() > 4) {
                        if (checkString(newEntry2)) {
                            AddData(newEntry, newEntry2, newEntry3);
                            user_input.setText("");
                            pass_input.setText("");
                            app_input.setText("");
                            weakPass.setVisibility(View.GONE);
                            strongPass.setVisibility(View.VISIBLE);//Password length must be greater then 6 letter

                        } else {
                            AddData(newEntry, newEntry2, newEntry3);
                            user_input.setText("");
                            pass_input.setText("");
                            app_input.setText("");
                            weakPass.setVisibility(View.VISIBLE);
                            emptyFiled.setVisibility(View.GONE);
                            strongPass.setVisibility(View.GONE);

                        }
                    } else {
                        AddData(newEntry, newEntry2, newEntry3);
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




        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this, KeysActivity.class);
                startActivity(intent);
            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
                Intent intent = new Intent(AddActivity.this, KeysActivity.class);
                startActivity(intent);
            }
        });

    }

    private void msgTextView() {
        msgPasswordlength = findViewById(R.id.PassLength2);
        insertedMsg = findViewById(R.id.insertedMsg);
        emptyFiled = findViewById(R.id.emptyF);
        errorMsg = findViewById(R.id.error);
        weakPass = findViewById(R.id.weakPass);
        strongPass = findViewById(R.id.StrongPass);

    }


    public void AddData(String username,String password,String App_name) {
        boolean insertData = myDB.addData(username,password,App_name);


        if (insertData) {
            insertedMsg.setVisibility(View.VISIBLE);
        } else {
            errorMsg.setVisibility(View.VISIBLE);

        }
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