package com.example.guardkey0;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.guardkey0.models.userModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Tag;

import java.util.List;

public class SginUp extends AppCompatActivity {
    EditText et_phone;
    Button btnSingOut;
    EditText et_name, et_pass, et_email, et_CountryCode;
    ProgressBar progressBar;
    FirebaseAuth auth;
    FirebaseDatabase database;
    public final static String TAG = "TAG";

    TextView pass_weak,txLog, msgCode, msgConfirm, msgConfirm1, msgEmailisEmbty, msgNameisEmbty, msgPasswordisempty, msgPhoneisempty, msgPasswordlength, msgWrongEmailexsited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sgin_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


//database using firebase for login and sign up
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        //

        pass_weak = (TextView)findViewById(R.id.weakPass);
        btnSingOut = (Button) findViewById(R.id.SingUp_Btn);
        et_name = (EditText) findViewById(R.id.etName);
        et_email = (EditText) findViewById(R.id.etEmail);
        et_pass = (EditText) findViewById(R.id.etPassword);
        btnSingOut = (Button) findViewById(R.id.SingUp_Btn);

        et_CountryCode = findViewById(R.id.country_code);
        msgCode = findViewById(R.id.codeEmpty);
        msgConfirm = findViewById(R.id.msgConfirm);
        msgConfirm1 = findViewById(R.id.msgConfirm1);
        msgEmailisEmbty = findViewById(R.id.emailEmpty);
        msgNameisEmbty = findViewById(R.id.nameEmpty);
        msgPasswordisempty = findViewById(R.id.passEmpty);
        msgPasswordlength = findViewById(R.id.PassLength);
        msgWrongEmailexsited = findViewById(R.id.msgWrong);
        msgPhoneisempty = findViewById(R.id.msgWrong);


        progressBar = findViewById(R.id.proBarS);


        txLog = findViewById(R.id.txt_LogIn);
        txLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SginUp.this, login.class));

            }
        });


        btnSingOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                createUser();


            }
        });


        et_phone = (EditText) findViewById(R.id.etPhone_Number);


    }

    private void createUser() {
        String userEmail = et_email.getText().toString();
        String contryCode = et_CountryCode.getText().toString();
        String userPassword = et_pass.getText().toString();
        String username = et_name.getText().toString();
        String userPhone = et_phone.getText().toString();


        if (TextUtils.isEmpty(username)) {
            msgNameisEmbty.setVisibility(View.VISIBLE);//"name is Empty!
            progressBar.setVisibility(View.GONE);
            msgEmailisEmbty.setVisibility(View.GONE);
            msgPasswordisempty.setVisibility(View.GONE);
            msgPasswordlength.setVisibility(View.GONE);
            msgCode.setVisibility(View.GONE);

            return;
        }
        if (TextUtils.isEmpty(userEmail)) {
            msgEmailisEmbty.setVisibility(View.VISIBLE);//"Email is Empty!
            progressBar.setVisibility(View.GONE);
            msgNameisEmbty.setVisibility(View.GONE);
            msgPasswordisempty.setVisibility(View.GONE);
            msgPasswordlength.setVisibility(View.GONE);
            msgCode.setVisibility(View.GONE);

            return;
        }
        if (TextUtils.isEmpty(contryCode)) {
            msgCode.setVisibility(View.VISIBLE);//"Country code is Empty!
            msgEmailisEmbty.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            msgNameisEmbty.setVisibility(View.GONE);
            msgPasswordisempty.setVisibility(View.GONE);
            msgPasswordlength.setVisibility(View.GONE);
            return;
        }

        if (TextUtils.isEmpty(userPassword)) {
            msgPasswordisempty.setVisibility(View.VISIBLE);//Password is Empty!
            progressBar.setVisibility(View.GONE);
            msgNameisEmbty.setVisibility(View.GONE);
            msgEmailisEmbty.setVisibility(View.GONE);
            msgPasswordlength.setVisibility(View.GONE);
            msgCode.setVisibility(View.GONE);

            return;

        }
        if (TextUtils.isEmpty(userPhone)) {
            progressBar.setVisibility(View.GONE);
            msgNameisEmbty.setVisibility(View.GONE);
            msgEmailisEmbty.setVisibility(View.GONE);
            msgPasswordisempty.setVisibility(View.GONE);
            msgCode.setVisibility(View.GONE);
            return;
        }
        if (!checkString(userPassword)) {
            pass_weak.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            msgNameisEmbty.setVisibility(View.GONE);
            msgEmailisEmbty.setVisibility(View.GONE);
            msgPasswordisempty.setVisibility(View.GONE);
            msgCode.setVisibility(View.GONE);
            return;
        }



        if (userPassword.length() < 6) {
            msgPasswordlength.setVisibility(View.VISIBLE);//Password length must be greater then 6 letter
            progressBar.setVisibility(View.GONE);
            msgNameisEmbty.setVisibility(View.GONE);
            msgEmailisEmbty.setVisibility(View.GONE);
            msgPasswordisempty.setVisibility(View.GONE);
            msgCode.setVisibility(View.GONE);
            return;
        }

//create user


                auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        // Toast.makeText(SginUp.this, "Sucess", Toast.LENGTH_SHORT).show();
                        msgConfirm1.setVisibility(View.VISIBLE);//Registration Successfully. Please check your email for verification
                        progressBar.setVisibility(View.GONE);
                        et_email.setText("");
                        et_name.setText("");
                        et_phone.setText("");
                        et_pass.setText("");

                        msgNameisEmbty.setVisibility(View.GONE);
                        msgEmailisEmbty.setVisibility(View.GONE);
                        msgPasswordisempty.setVisibility(View.GONE);
                        msgCode.setVisibility(View.GONE);

                        Intent phone = new Intent(SginUp.this, VerifyPhone.class);
                        phone.putExtra("phone", "+" + et_CountryCode.getText().toString() + userPhone);
                        startActivity(phone);
                        Log.d(TAG, "onSuccess: " + "+" + et_CountryCode.getText().toString() + userPhone);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SginUp.this, "failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);


                    }
                });



                // userModel userModel = new userModel(userEmail, userPassword,username);

//                        if (task.isSuccessful()) {
//
//                            //check if the email existed or not
//                            auth.getInstance().fetchSignInMethodsForEmail(userEmail)
//                                    .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
//                                            if (task.isSuccessful()) {
//                                                List<String> methods = task.getResult().getSignInMethods();
//                                                if (methods.isEmpty()) {
//                                                    msgConfirm1.setVisibility(View.VISIBLE);//Registration Successfully.
//                                                    progressBar.setVisibility(View.GONE);
//
//                                                } else {
//
//                                                    //email msg Verification
//                                                    auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                        @Override
//                                                        public void onComplete(@NonNull Task<Void> task) {
//
//                                                            if (task.isSuccessful()) {
//
//                                                                msgConfirm.setVisibility(View.VISIBLE);//Registration Successfully. Please check your email for verification
//                                                                progressBar.setVisibility(View.GONE);
//                                                                et_email.setText("");
//                                                                et_name.setText("");
//                                                                et_pass.setText("");
//                                                                Intent intent = new Intent(SginUp.this, login.class);
//                                                                startActivity(intent);
//
//
//                                                            } else {
//                                                                Toast.makeText(SginUp.this, "" + task.getException(), Toast.LENGTH_LONG).show();
//                                                                progressBar.setVisibility(View.GONE);
//                                                            }
//                                                        }
//                                                    });
//                                                }
//                                            }
//                                        }
//                                    });
//
//
//                        } else {
//                            //This email is already existed
//                            msgWrongEmailexsited.setVisibility(View.VISIBLE);
//                            et_email.setText("");
//                            et_pass.setText("");
//                            et_name.setText("");
//                            progressBar.setVisibility(View.GONE);
//
//
//                        }
//                    }
//                });



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

