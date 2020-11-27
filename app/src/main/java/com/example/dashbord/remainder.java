 package com.example.dashbord;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

 @RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class remainder extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    public FloatingActionButton floatingActionButton,fab;
    public static int EventID=0;
    int Delay=3600000;
    private TextView mTextView,mdatetext,mtimetext;
    private AlarmManager alarmManager;
    private EditText editTextRemainder,TitleE,CommentE;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    FirebaseDatabase database;
    DatabaseReference myRef;
    SwitchCompat switchCompat;
    Calendar c ;
    private String DString,time,title,cmnt,Usermailid,Userphoneno;
    private String Repeat="Off";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Usermailid ="vamshiduvva23@gmail.com";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remainder);
        mTextView = findViewById(R.id.textView);
        mdatetext=findViewById(R.id.date_text);
        mtimetext=findViewById(R.id.time_text);
        myRef=database.getInstance().getReference();
        floatingActionButton=findViewById(R.id.floatingActionButton);
        fab=findViewById(R.id.fab_back1);
        switchCompat=findViewById(R.id.switch1);
        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchCompat.isChecked()){
                    Repeat="On";
                }
                else{
                    Repeat="Off";
                }
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(com.example.dashbord.remainder.this, com.example.dashbord.ListR.class);
                startActivity(i);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = TitleE.getText().toString();
                cmnt = CommentE.getText().toString();
                if (!title.isEmpty()  && !time.isEmpty() && !DString.isEmpty() ) {
                    Reminder_data data = new Reminder_data(title, cmnt, time, DString, Repeat);
                    Intent inte = new Intent(com.example.dashbord.remainder.this, com.example.dashbord.ListR.class);
                    startActivity(inte);
                    String id = myRef.push().getKey();
                    myRef.child("users").child(id).setValue(data);
                    if(Repeat.equalsIgnoreCase("Off")) {
                        startAlarm(title, cmnt);
                    }
                    else if(Repeat.equalsIgnoreCase("On")){
                        startRepeatingA(title,cmnt);
                    }
                    SendSms(title,cmnt);
                    sendEmail(title,cmnt);
                    Toast.makeText(com.example.dashbord.remainder.this, "Reminder set!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(com.example.dashbord.remainder.this,"Enter all the details",Toast.LENGTH_SHORT).show();
                }
            }
        });

        TitleE=findViewById(R.id.Title);
        CommentE=findViewById(R.id.Comment);
        editTextRemainder = findViewById(R.id.Title);
        c= Calendar.getInstance();
        Button buttonTimePicker = findViewById(R.id.button_Remainder);
        buttonTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment tP = new TimeSet();
                tP.show(getSupportFragmentManager(), "time picker");
            }
        });
        Button bdp=findViewById(R.id.button_date);
        bdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker=new com.example.dashbord.DateSet();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });
//        Button buttonCancelAlarm = findViewById(R.id.button_cancel);
//        buttonCancelAlarm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cancelAlarm();
//            }
//        });

    }
     private void sendEmail(String ti,String cm) {
         AlarmManager alarmMan = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
         Intent intent = new Intent(getApplicationContext(), com.example.dashbord.MailReceiver.class);
         intent.putExtra("Tiv",ti);
         intent.putExtra("Cmntv",cm);
         intent.putExtra("datev",DString);
         intent.putExtra("timev",time);
         intent.putExtra("Usermail",Usermailid);
         PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, PendingIntent.FLAG_ONE_SHOT);
         alarmMan.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis()-Delay,pendingIntent);

     }

     private void startRepeatingA(String t, String ch) {

         AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
         Intent intent = new Intent(getApplicationContext(), com.example.dashbord.Receiver.class);
         intent.putExtra("title",t);
         intent.putExtra("Dets",ch);
         PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, PendingIntent.FLAG_ONE_SHOT);
         if (c.before(Calendar.getInstance())) {
             c.add(Calendar.DATE, 1);
         }
         alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis()-Delay,AlarmManager.INTERVAL_DAY,pendingIntent);
     }

     private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, com.example.dashbord.Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.cancel(pendingIntent);
        mTextView.setText("Reminder canceled");
        mdatetext.setText("");
        mtimetext.setText("");
         Toast.makeText(com.example.dashbord.remainder.this,"Cancelled",Toast.LENGTH_LONG).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        time=hourOfDay+":"+minute;
        updateTimeText(c);
        mtimetext.setText(time);
    }
    private void SendSms(String Ti,String Cm){
        AlarmManager am=(AlarmManager)getSystemService(ALARM_SERVICE);
        Intent in=new Intent(getApplicationContext(), com.example.dashbord.SmsReceiver.class);
        in.putExtra("Title",Ti);
        in.putExtra("Message",Cm);
        in.putExtra("Phone",Userphoneno);
        PendingIntent pending=PendingIntent.getBroadcast(this,EventID,in,PendingIntent.FLAG_UPDATE_CURRENT);
        EventID++;
        am.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis()-Delay,pending);
    }
   // @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void startAlarm(String T,String C) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), com.example.dashbord.Receiver.class);
        intent.putExtra("title", T);
        intent.putExtra("Dets", C);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, PendingIntent.FLAG_ONE_SHOT);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() - Delay, pendingIntent);
    }
//
//
//        if (Repeat.equalsIgnoreCase("On")) {
//            long t=(long)c.getTimeInMillis()-(long)70000;
//            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,t , AlarmManager.INTERVAL_DAY, pendingIntent);
//        } else {
//            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
//        }


    private void updateTimeText(Calendar c) {
        String timeText = "Reminder set ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        timeText+=" "+DString;
        //   mTextView.setText(timeText);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        DString=DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime());
        mdatetext.setText(DString);

    }

}