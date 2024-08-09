package com.example.guardkey0;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.guardkey0.models.userModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {
    TextView txSignUp;
    TextView txtForgetPassword;
    ProgressBar progressBar, progressBar2;
    TextView msgResetEmail, msgResetWrong, msgFillBlank;

    Button Login,login_btn2;
    public final static String TAG = "TAG";

    FirebaseAuth auth;
    TextView msgError;
    EditText email,password;
    userModel userModel;

    TextView msgEmailisEmbty,msgPasswordisempty,msgPasswordlength;

EditText et_phone,et_CountryCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        userModel = new userModel();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//        if (firebaseUser != null && firebaseUser.isEmailVerified()) {
//            Intent intent = new Intent(login.this,KeysActivity.class);
//            startActivity(intent);
//            finish();
//
//        }



        msgError = findViewById(R.id.msgError);
        progressBar = findViewById(R.id.proBar);
        Login = findViewById(R.id.loginBtn);
        msgEmailisEmbty = findViewById(R.id.Emailempty);
        msgPasswordisempty =findViewById(R.id.PassEmpty);
        msgPasswordlength = findViewById(R.id.PassLength);
        email = findViewById(R.id.email);
        password = findViewById(R.id.etPassword);
        login_btn2 = findViewById(R.id.loginBtn2);

        auth = FirebaseAuth.getInstance();
        txtForgetPassword = findViewById(R.id.forgetPassword);


        login_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, OTP.class));

            }
        });

        txSignUp = findViewById(R.id.txtSignUp);
        txSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, SginUp.class));

            }
        });


        txtForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(login.this);
                LayoutInflater inflater = login.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.rest_pass, null);
                dialogBuilder.setView(dialogView);

                EditText email2 = (EditText) dialogView.findViewById(R.id.email);
                Button button = (Button) dialogView.findViewById(R.id.reset);
                button.setText("Send");


                //icon loading
                progressBar2 = (ProgressBar) dialogView.findViewById(R.id.proBar2);
                progressBar2.setVisibility(View.GONE);

                //massage wrong fill the Email blank
                msgFillBlank = (TextView) dialogView.findViewById(R.id.msgFill);
                msgFillBlank.setVisibility(View.GONE);

                //massage reset email
                msgResetEmail = (TextView) dialogView.findViewById(R.id.msgReset);
                msgResetEmail.setVisibility(View.GONE);

                //massage Wrong send the reset email
                msgResetWrong = (TextView) dialogView.findViewById(R.id.msgResetWrong);
                msgResetWrong.setVisibility(View.GONE);

                dialogBuilder.setMessage("")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();


                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        msgResetEmail.setVisibility(View.GONE);
                        msgResetWrong.setVisibility(View.GONE);
                        msgFillBlank.setVisibility(View.GONE);

                        progressBar2.setVisibility(View.VISIBLE);

                        email2.getText().toString();
                        if (TextUtils.isEmpty(email2.getText().toString())) {
                            msgFillBlank.setVisibility(View.VISIBLE);//massage Fill the email
                            email2.setText("");
                            progressBar2.setVisibility(View.GONE);

                        } else {
                            firebaseAuth.sendPasswordResetEmail(email2.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        msgResetEmail.setVisibility(View.VISIBLE);//massage Please check your email for reset your password
                                        email2.setText("");
                                        progressBar2.setVisibility(View.GONE);


                                    } else {
                                        msgResetWrong.setVisibility(View.VISIBLE);//massage Failed to Send, The email is wrong
                                        email2.setText("");
                                        progressBar2.setVisibility(View.GONE);


                                    }
                                }
                            });
                        }


                    }
                });


            }
        });







        Login.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                msgError.setVisibility(View.GONE);
                //sp if the user click the button again the wrong note massages will gone
                msgEmailisEmbty.setVisibility(View.GONE);
                msgPasswordisempty.setVisibility(View.GONE);
                msgPasswordlength.setVisibility(View.GONE);

                loginUser();


            }
        }));

    }


    private void loginUser() {
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();





        if(TextUtils.isEmpty(userEmail)){
            msgEmailisEmbty.setVisibility(View.VISIBLE);//Email is Empty!
            email.setText("");
            password.setText("");
            progressBar.setVisibility(View.GONE);
            return;
        }

        if(TextUtils.isEmpty(userPassword)){
            msgPasswordisempty.setVisibility(View.VISIBLE); //"Password is Empty!
            email.setText("");
            password.setText("");
            progressBar.setVisibility(View.GONE);

            return;
        }

        //Login user
        auth.signInWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(login.this,KeysActivity.class);
                            startActivity(intent);


                            finish();

                            email.setText("");
                            password.setText("");
                            progressBar.setVisibility(View.GONE);


                        }else{
                            msgError.setVisibility(View.VISIBLE);//Error may be the email is wrong or the password
                            password.setText("");
                            progressBar.setVisibility(View.GONE);


                        }

                    }
                });

    }
}
