package com.example.dashbord;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class register extends AppCompatActivity {

    EditText mUserName,mEmail,mPassword,mProfession,mPhone,otpEnter;
    Button mRegisterBtn,mNext;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    String phoneNumber = "+911234567890";
    String otpCode = "123457";
    String verificationId;
    CountryCodePicker countryCodePicker;
    PhoneAuthCredential credential;
    Boolean verificationOnProgress = false;
    ProgressBar progressBar;
    PhoneAuthProvider.ForceResendingToken token;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUserName   = findViewById(R.id.username);
        mEmail      = findViewById(R.id.emailaddress);
        mPassword   = findViewById(R.id.password);
        mProfession = findViewById(R.id.profession);
        mRegisterBtn= findViewById(R.id.register);
        mLoginBtn   = findViewById(R.id.loginhere);
        mPhone      = findViewById(R.id.phone);
        fStore      = FirebaseFirestore.getInstance();
        fAuth       = FirebaseAuth.getInstance();
        otpEnter = findViewById(R.id.entercode);
        countryCodePicker = findViewById(R.id.country);
        mNext = findViewById(R.id.Next);
        progressBar = findViewById(R.id.progressBar);


        
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mPhone.getText().toString().isEmpty() && mPhone.getText().toString().length() == 10) {
                    if(!verificationOnProgress){
                        mNext.setEnabled(false);
                        progressBar.setVisibility(View.VISIBLE);
                        String phoneNum = "+"+countryCodePicker.getSelectedCountryCode()+mPhone.getText().toString();
                        Log.d("phone", "Phone No.: " + phoneNum);
                        requestPhoneAuth(phoneNum);
                    }else {
                        mNext.setEnabled(true);
                        otpEnter.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.VISIBLE);
                        otpCode = otpEnter.getText().toString();
                        if(otpCode.isEmpty()){
                            otpEnter.setError("Required");
                            return;
                        }

                        credential = PhoneAuthProvider.getCredential(verificationId,otpCode);
                        verifyAuth(credential);
                    }

                }else {
                    mPhone.setError("Valid Phone Required");
                }
            }
            
        });
        

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String email = mEmail.getText().toString().trim();
               String password = mPassword.getText().toString().trim();
               String username = mUserName.getText().toString();
               String profession = mProfession.getText().toString();
               String phoneNumber = mPhone.getText().toString();

               if(TextUtils.isEmpty(email)){
                   mEmail.setError("Enter Email");
                   return;
               }
               if(TextUtils.isEmpty(password)){
                   mPassword.setError("Enter Password");
                   return;
               }
               if(password.length() < 6){
                   mPassword.setError("password must be atleast 6 characters");
                   return;
               }

               fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()) {
                           fAuth.getCurrentUser().sendEmailVerification();
                           Toast.makeText(com.example.dashbord.register.this, "verification link has been sent to your mail,please verify", Toast.LENGTH_SHORT).show();
                           userID = fAuth.getCurrentUser().getUid();
                           DocumentReference documentReference = fStore.collection("Users").document(userID);
                           Map<String,Object> user= new HashMap<>();
                           user.put("userName",username);
                           user.put("Profession",profession);
                           user.put("PhoneNumber",phoneNumber);
                           user.put("email",email);
                           documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                               @Override
                               public void onSuccess(Void aVoid) {
                                   Toast.makeText(com.example.dashbord.register.this, "User data is saved successfully", Toast.LENGTH_SHORT).show();
                               }
                           });
                           startActivity(new Intent(getApplicationContext(), com.example.dashbord.logout.class));
                           finish();
                       } else{
                           Toast.makeText(com.example.dashbord.register.this, "Error occured" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                       }

                   }
               });
            }
        });
    }



    private void requestPhoneAuth(String phoneNumber) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber,120L, TimeUnit.SECONDS,this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                    @Override
                    public void onCodeAutoRetrievalTimeOut(String s) {
                        super.onCodeAutoRetrievalTimeOut(s);
                        Toast.makeText(com.example.dashbord.register.this, "OTP Timeout, Please Re-generate the OTP Again.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        verificationId = s;
                        token = forceResendingToken;
                        verificationOnProgress = true;
                        progressBar.setVisibility(View.INVISIBLE);
                        mNext.setText("Verify");
                        mNext.setEnabled(true);
                        otpEnter.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                        // called if otp is automatically detected by the app
                        verifyAuth(phoneAuthCredential);

                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Toast.makeText(com.example.dashbord.register.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }

    private void verifyAuth(PhoneAuthCredential phoneAuthCredential) {
        fAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(com.example.dashbord.register.this, "phone number verified", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    mNext.setVisibility(View.INVISIBLE);
                    otpEnter.setVisibility(View.INVISIBLE);
                    mRegisterBtn.setVisibility(View.VISIBLE);
                }else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(com.example.dashbord.register.this, "Error occured", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void gotologin(View view) {
        startActivity(new Intent(getApplicationContext(), com.example.dashbord.login.class));
        finish();
    }
}