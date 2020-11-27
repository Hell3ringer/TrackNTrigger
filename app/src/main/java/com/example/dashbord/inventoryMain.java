package com.example.dashbord;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class inventoryMain extends AppCompatActivity implements com.example.dashbord.dashAdapter.OnButtonClickListener {

    private ImageView image;
    private RecyclerView recyclerView;
    private ImageButton btnadd,btncatdel;



    private com.example.dashbord.category cat1,cat2,cat3;
    private String catname;
    private long def;
    private int position;
    private boolean flag ;

    private ArrayList<String> images = new ArrayList<>();
    private ArrayList<String> categories = new ArrayList<>();


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private dashAdapter dashAdapter;


    private void setUI(){
        btnadd = findViewById(R.id.floatingadd);
        image = findViewById(R.id.image);
        //btncatdel = findViewById(R.id.btndelcat);

//        btncatdel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActivity();
            }
        });




    }
    private void setActivity() {
        Intent intent = new Intent(this, com.example.dashbord.addCategory.class);

        intent.putExtra("catname",catname);
        startActivity(intent);
    }
    public void initdashRecyclerView(){

        recyclerView=findViewById(R.id.dashrecyclerview);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        dashAdapter = new dashAdapter(this,images,categories,this);
        recyclerView.setAdapter(dashAdapter);
    }

    private void defaultvalues(){
//        categories.add("Groceries");
//        categories.add("Books");
//        categories.add("Misc");
//        images.add("https://i.imgur.com/XhTjOMu.png");
//        images.add("https://i.imgur.com/U9tdGo6_d.webp?maxwidth=760&fidelity=grand");
//        images.add("https://i.imgur.com/53QR2iB_d.webp?maxwidth=760&fidelity=grand");
        cat1 = new com.example.dashbord.category();
        cat1.setCategory("Groceries");
        cat1.setImage("https://i.imgur.com/XhTjOMu.png");
        databaseReference.child("Groceries").setValue(cat1);
        cat1 = new com.example.dashbord.category();
        cat1.setCategory("Books");
        cat1.setImage("https://i.imgur.com/U9tdGo6_d.webp?maxwidth=760&fidelity=grand");
        databaseReference.child("Books").setValue(cat1);
        cat1 = new com.example.dashbord.category();
        cat1.setCategory("Furniture");
        cat1.setImage("https://i.imgur.com/X0qnvfp.png");
        databaseReference.child("Furniture").setValue(cat1);
        cat1 = new com.example.dashbord.category();
        cat1.setCategory("Applainces");
        cat1.setImage("https://i.imgur.com/UpUzKwA.png");
        databaseReference.child("Applainces").setValue(cat1);
        cat1 = new com.example.dashbord.category();

    }
    private void firebaseinfo(){

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                def=snapshot.getChildrenCount();

                Log.d("posss",String.valueOf(def));

                if (snapshot.exists()) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                            catname = "" + dataSnapshot.child("category").getValue().toString();
                            categories.add("" + catname);
                            switch (catname){
                                case "Groceries":
                                    images.add("https://i.imgur.com/XhTjOMu.png");
                                    break;
                                case "Books":
                                    images.add("https://i.imgur.com/U9tdGo6_d.webp?maxwidth=760&fidelity=grand");
                                    break;
                                case "Furniture":
                                    images.add("https://i.imgur.com/X0qnvfp.png");
                                    break;
                                case "Applainces":
                                    images.add("https://i.imgur.com/UpUzKwA.png");
                                    break;
                                default:
                                    images.add("https://i.imgur.com/EDT2Rtp.png");





                        }
                    }
                }
                initdashRecyclerView();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventorymain);
        databaseReference=firebaseDatabase.getInstance().getReference().child("User");

        setUI();
        Log.d("bool flag before",String.valueOf(flag));
        flag = true;
        if (flag){
            defaultvalues();
            flag = false;
            Log.d("bool flag in",String.valueOf(flag));
        }
        Log.d("bool flag after ",String.valueOf(flag));


        firebaseinfo();

    }

    @Override
    public void onButtonClick(int position) {
        Intent intent1 = new Intent(this, com.example.dashbord.ItemsMain.class);
        flag = true;
        intent1.putExtra("catname",categories.get(position));
        startActivity(intent1);
    }

//    @Override
//    public void onButtonClickDelete(int position) {
//        databaseReference.child(categories.get(position)).removeValue();
//    }
}