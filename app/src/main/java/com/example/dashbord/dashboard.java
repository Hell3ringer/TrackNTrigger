package com.example.dashbord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class dashboard extends AppCompatActivity {
    private RelativeLayout inv,doc,profile,share,rem,todo;
    private Intent intent;



    private void setUI(){
        inv=findViewById(R.id.inv);
        doc=findViewById(R.id.doc);
        profile=findViewById(R.id.profile);
        share=findViewById(R.id.share);
        rem=findViewById(R.id.rem);
        todo=findViewById(R.id.todo);

        inv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(intent = new Intent(dashboard.this,inventoryMain.class));

            }
        });
        rem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(intent = new Intent(dashboard.this,Profile.class));

            }
        });
        todo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(intent = new Intent(dashboard.this,to_do.class));

            }
        });
        doc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(intent = new Intent(dashboard.this,Profile.class));

            }
        });
        share.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(intent = new Intent(dashboard.this,Profile.class));

            }
        });
        profile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(intent = new Intent(dashboard.this,Profile.class));

            }
        });

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setUI();


    }
}