package com.example.dashbord;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Search extends AppCompatActivity implements com.example.dashbord.SearchAdapter.OnButtonClickListener, com.example.dashbord.SearchAdapter.OnButtonShareListenr {

    private EditText search;
    private ImageButton btnback,searchbtn1;
    private RecyclerView searchrecycleview;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;
    private ArrayList<String> itemsname = new ArrayList<String>();
    private ArrayList<String> itemsquantity = new ArrayList<String>();
    private ArrayList<String> itemstype = new ArrayList<String>();
    private ArrayList<String> childID = new ArrayList<String>();
    private com.example.dashbord.SearchAdapter SearchAdapter;
    private  String Search;
    private  String catname;


    private void setUI(){
        search = (EditText) findViewById(R.id.search);
        searchbtn1 = findViewById(R.id.searchbtn1);

        btnback = findViewById(R.id.backbtn);

        databaseReference = firebaseDatabase.getInstance().getReference().child("User").child(""+catname).child("items");
        valueEventListener = new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemsname.clear();itemsquantity.clear();itemstype.clear();
                if (snapshot.exists()){
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        String name = dataSnapshot.child("itmname").getValue().toString();
                        itemsname.add(name);
                        String quantity = dataSnapshot.child("itmquantity").getValue().toString();
                        itemsquantity.add(quantity);
                        String type = dataSnapshot.child("itmtype").getValue().toString();
                        itemstype.add(type);
                        String IDchild = dataSnapshot.child("childID").getValue().toString();
                        childID.add(IDchild);
                    }
                }
                initSearchRecyclerView();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
    }
    private void setActivity() {
        Intent intent = new Intent(this, com.example.dashbord.ItemsMain.class);
        intent.putExtra("catname",catname);
        startActivity(intent);
    }

    private void initSearchRecyclerView() {
        searchrecycleview =findViewById(R.id.searchrecycleview);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        searchrecycleview.setLayoutManager(layoutManager);
        SearchAdapter = new com.example.dashbord.SearchAdapter(itemsname,itemsquantity,itemstype,this,this);
        searchrecycleview.setAdapter(SearchAdapter);
    }
    @Override
    public void onButtonClick(int position) {

        Intent intent1 = new Intent(this, com.example.dashbord.Update.class);
        intent1.putExtra("catname",catname);

        intent1.putExtra("childID",childID.get(position));
        startActivity(intent1);
    }

    @Override
    public void onButtonShare(int position) {
        Intent myIntent= new Intent(Intent.ACTION_SEND);
        myIntent.setType("text/plain");
        String shareBody=itemsname.get(position);
        String shareSub="FOOD ITEM SHARING";
        myIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
        String sharebody1=itemsquantity.get(position);
        String sharebody2=itemstype.get(position);
        myIntent.putExtra(Intent.EXTRA_TEXT,"Item Name: "+shareBody+"\n"+ "Quantity: "+sharebody1 +"\n"+ "Item type: "+sharebody2);

        startActivity(Intent.createChooser(myIntent,"Share using"));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = getIntent();
        catname = intent.getStringExtra("catname");
        setUI();

        searchbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search = search.getText().toString();

                String query = Search.toLowerCase();
                Log.d("query",query);
                Query firebasesearch = databaseReference.orderByChild("itmname").startAt(query).endAt(query + "\uf8ff");

                firebasesearch.addListenerForSingleValueEvent(valueEventListener);
                initSearchRecyclerView();
                searchrecycleview.clearOnChildAttachStateChangeListeners();

            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActivity();
            }
        });





    }


}