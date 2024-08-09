package com.example.guardkey0;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;



    private ArrayList id, username, password, nameApp;


    public CustomAdapter(Activity activity,Context context, ArrayList id, ArrayList username, ArrayList password, ArrayList nameApp) {
        this.context = context;
        this.activity = activity;
        this.id = id;
        this.username = username;
        this.password = password;
        this.nameApp = nameApp;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.id_txt.setText(String.valueOf(id.get(position)));
        holder.appName_txt.setText(String.valueOf(nameApp.get(position)));
        holder.char_app.setText(String.valueOf(nameApp.get(position).toString().charAt(0)));
        holder.char_app2.setText(String.valueOf(nameApp.get(position).toString().charAt(0)));
        //Recyclerview onClickListener
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(id.get(position)));
                intent.putExtra("username", String.valueOf(username.get(position)));
                intent.putExtra("password", String.valueOf(password.get(position)));
                intent.putExtra("nameApp", String.valueOf(nameApp.get(position)));

                activity.startActivityForResult(intent, 1);
            }
        });


    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id_txt, appName_txt,char_app,char_app2;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id_txt = itemView.findViewById(R.id.id_txt);
            appName_txt = itemView.findViewById(R.id.username_txt);
            char_app = itemView.findViewById(R.id.char_app);
            char_app2 = itemView.findViewById(R.id.char_app2);
            mainLayout = itemView.findViewById(R.id.mainLayout);

        }

    }

}
