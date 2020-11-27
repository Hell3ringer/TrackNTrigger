package com.example.dashbord;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class additem extends AppCompatActivity {
    private TextView itmname,itmquantity,itmtype;
    private String childID;
    private Button btnok;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference,databaseReference1;
    private newitem itm;
    private String catname;

    private Integer srno = new Integer(0);


    private void setUI() {

        itmname = (TextView) findViewById(R.id.nametext);
        itmquantity = (TextView) findViewById(R.id.quantitytext);

        btnok = (Button) findViewById(R.id.btnok);
        firebase();

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itm = new newitem();
                itm.setItmname(itmname.getText().toString());
                itm.setItmquantity(itmquantity.getText().toString());
                itm.setItmtype(catname);



                databaseReference1 = databaseReference.push();
                childID=databaseReference1.getRef().getKey();
                itm.setChildID(childID);
                databaseReference1.setValue(itm);

                Log.d("ID",childID);

                setActivity();
            }
        });
    }
    private void firebase(){
        databaseReference = database.getInstance().getReference().child("User").child(catname).child("items");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    srno = (int)snapshot.getChildrenCount();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setActivity(){
        Intent intent = new Intent(this, com.example.dashbord.ItemsMain.class);
        intent.putExtra("catname",catname);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);
        Intent intent = getIntent();
        catname = intent.getStringExtra("catname");

        setUI();

    }
}