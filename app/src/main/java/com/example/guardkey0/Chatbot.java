package com.example.guardkey0;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.guardkey0.models.userModel;

public class Chatbot extends AppCompatActivity {

    TextView respond_txt1,respond_txt2,respond_txt3,username;
    LinearLayout respond_user1,respond_user2,respond_user3;
    LinearLayout respond_bot1,respond_bot2,respond_bot3;
    ImageView go_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chatbot);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userModel userModel = new userModel();

        username = findViewById(R.id.name_user);

        respond_txt1 = findViewById(R.id.respond1);
        respond_txt2 = findViewById(R.id.respond2);
        respond_txt3 = findViewById(R.id.respond3);

        respond_user1 = findViewById(R.id.user_respond1);
        respond_user2 = findViewById(R.id.user_respond2);
        respond_user3 = findViewById(R.id.user_respond3);

        respond_bot1 = findViewById(R.id.bot_respond1);
        respond_bot2 = findViewById(R.id.bot_respond2);
        respond_bot3 = findViewById(R.id.bot_respond3);


        go_back = (ImageView) findViewById(R.id.go_back);

        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Chatbot.this, KeysActivity.class);
                startActivity(intent);
            }
        });
        respond_txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                respond_txt1.setTextColor(Color.GRAY);
                respond_user1.setVisibility(View.VISIBLE);


                //respond_txt1.setClickable(false);
                respond_botAfter1();
            }
        });





        respond_txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                respond_txt2.setTextColor(Color.GRAY);
                respond_user2.setVisibility(View.VISIBLE);




                //respond_txt1.setClickable(false);
                respond_botAfter2();
            }
        });


        respond_txt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                respond_txt3.setTextColor(Color.GRAY);
                respond_user3.setVisibility(View.VISIBLE);




                //respond_txt1.setClickable(false);
                respond_botAfter3();
            }
        });
    }

    private void respond_botAfter3() {
        Handler handler= new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                respond_bot3.setVisibility(View.VISIBLE);

                scrollDown();
            }
        }, 1000); // Delay in milliseconds (e.g., 1000 ms = 1 second)

    }

    private void respond_botAfter2() {
        Handler handler= new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                respond_bot2.setVisibility(View.VISIBLE);

                scrollDown();
            }
        }, 1000); // Delay in milliseconds (e.g., 1000 ms = 1 second)

    }

    private void scrollDown() {
        final ScrollView scroll = ((ScrollView) findViewById(R.id.main));
        scroll.post(new Runnable() {
            @Override
            public void run() {
                scroll.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    private void respond_botAfter1() {

        Handler handler= new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                respond_bot1.setVisibility(View.VISIBLE);
                scrollDown();
            }
        }, 1000); // Delay in milliseconds (e.g., 1000 ms = 1 second)



    }
}