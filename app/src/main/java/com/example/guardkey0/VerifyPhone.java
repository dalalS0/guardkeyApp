package com.example.guardkey0;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyPhone extends AppCompatActivity {

    EditText verify_code1,verify_code2,verify_code3,verify_code4,verify_code5,verify_code6;
    Button btn_verify, btn_resend;
    boolean otpValid = true;

    FirebaseAuth firebaseAuth;
    PhoneAuthProvider.ForceResendingToken token;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    String verificationId;
    String phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verify_phone);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });




        Intent data = getIntent();
        phone = data.getStringExtra("phone");


        verify_code1 = findViewById(R.id.code1);
        verify_code2 = findViewById(R.id.code2);
        verify_code3 = findViewById(R.id.code3);
        verify_code4 = findViewById(R.id.code4);
        verify_code5 = findViewById(R.id.code5);
        verify_code6 = findViewById(R.id.code6);
        btn_resend = findViewById(R.id.resend_btn);
        btn_verify = findViewById(R.id.verify_Btn);

       btn_verify.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               validateFiled(verify_code1);
               validateFiled(verify_code2);
               validateFiled(verify_code3);
               validateFiled(verify_code4);
               validateFiled(verify_code5);
               validateFiled(verify_code6);

               if(otpValid) {

                   String OTP =  verify_code1.getText().toString()+
                           verify_code2.getText().toString()+
                           verify_code3.getText().toString()+
                           verify_code4.getText().toString()+
                           verify_code5.getText().toString()+
                           verify_code6.getText().toString();


                   PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, OTP);
                   verifyAtintocation(credential);


               }
           }
       });



        firebaseAuth = FirebaseAuth.getInstance();


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                verificationId = s;
                token = forceResendingToken;
                btn_resend.setVisibility(View.GONE);
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                btn_resend.setVisibility(View.VISIBLE);

            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                verifyAtintocation(phoneAuthCredential);
                btn_resend.setVisibility(View.GONE);

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

                Toast.makeText(VerifyPhone.this, "OTP Verification Failed", Toast.LENGTH_SHORT).show();
            }
        };

        sendOTP(phone);
//354650


        btn_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resendOTP(phone);
            }
        });

    }

    private void verifyAtintocation(PhoneAuthCredential credential) {
        firebaseAuth.getCurrentUser().linkWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Intent intent = new Intent(VerifyPhone.this,KeysActivity.class);
                startActivity(intent);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

    }

//354650
    public void sendOTP(String phoneNumber){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber,60, TimeUnit.SECONDS,this,mCallbacks);

    }

    public void resendOTP(String phoneNumber){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber,60, TimeUnit.SECONDS,this,mCallbacks,token);

    }

    public void validateFiled(EditText filed){
        if(filed.getText().toString().isEmpty()){
            filed.setError("Required");
            otpValid = false;
        }else{
            otpValid = true;
        }
    }
}