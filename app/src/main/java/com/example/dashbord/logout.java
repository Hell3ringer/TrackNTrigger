 package com.example.dashbord;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

 public class logout extends AppCompatActivity {

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_logout);

         if(FirebaseAuth.getInstance().getCurrentUser() == null){
             startActivity(new Intent(getApplicationContext(),login.class));
             finish();
         }

     }

     public void handlelogout(View view) {
         FirebaseAuth.getInstance().signOut();
         GoogleSignIn.getClient(this,new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build())
                 .signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
             @Override
             public void onSuccess(Void aVoid) {
                 startActivity(new Intent(getApplicationContext(),login.class));
                 finish();
             }
         }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 Toast.makeText(com.example.dashbord.logout.this, "Error occured", Toast.LENGTH_SHORT).show();
             }
         });

     }
 }