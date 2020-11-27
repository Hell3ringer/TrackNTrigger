package com.example.dashbord;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class itemdetails extends RecyclerView.ViewHolder {



    View view;
    public  Button btninfo1;




    public itemdetails(@NonNull View itemView) {
        super(itemView);


        view = itemView;

    }


    public  void setView(Context context,String itmname,String itmquantity,String itmtype){

        //TextView srnotv =view.findViewById(R.id.Srno);
        TextView nametv =view.findViewById(R.id.Name);
        EditText quantitytv =view.findViewById(R.id.Quantity);
        TextView typetv =view.findViewById(R.id.Type);


        //srnotv.setText(itmsrno);
        nametv.setText(itmname);
        quantitytv.setText(itmquantity);
        typetv.setText(itmtype);










    }



}
