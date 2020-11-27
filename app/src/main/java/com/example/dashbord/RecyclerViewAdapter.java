package com.example.dashbord;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecyclerViewAdapter extends RecyclerView.Adapter<com.example.dashbord.RecyclerViewAdapter.ViewHolder>{

    //private Context context;
    //private newitem item;

    private OnButtonClickListener onButtonClickListener;
    private OnButtonShareListenr onButtonShareListenr;
    private ArrayList<String> itemsname = new ArrayList<String>();
    private ArrayList<String> itemsquantity = new ArrayList<String>();
    private ArrayList<String> itemstype = new ArrayList<String>();
    private Map<Integer,String> map = new HashMap<Integer,String>();

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public RecyclerViewAdapter(ArrayList<String> itemsname,ArrayList<String> itemsquantity,ArrayList<String> itemstype,OnButtonClickListener onButtonClickListener,OnButtonShareListenr onButtonShareListenr) {

        this.itemsname = itemsname;
        this.itemsquantity = itemsquantity;
        this.itemstype = itemstype;
        this.onButtonClickListener=onButtonClickListener;
        this.onButtonShareListenr=onButtonShareListenr;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemdetails,parent,false);
        ViewHolder holder = new ViewHolder(view,onButtonClickListener,onButtonShareListenr);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itmname.setText(itemsname.get(position));
        holder.itmquantity.setText(itemsquantity.get(position));
        holder.itmtype.setText(itemstype.get(position));
    }


    @Override
    public int getItemCount() {
        return itemsname.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itmname,itmquantity,itmtype;
        Button btninfo;
        ImageButton btnshare;
        RelativeLayout parentLayout;
        OnButtonClickListener onButtonClickListener;
        OnButtonShareListenr onButtonShareListenr;


        public ViewHolder(@NonNull View itemView,OnButtonClickListener onButtonClickListener,OnButtonShareListenr onButtonShareListenr) {
            super(itemView);
            itmname=itemView.findViewById(R.id.Name);
            itmquantity=itemView.findViewById(R.id.Quantity);
            itmtype=itemView.findViewById(R.id.Type);
            btninfo=itemView.findViewById(R.id.btninfo);
            parentLayout=itemView.findViewById(R.id.parent_layout);
            btnshare=itemView.findViewById(R.id.btnshare);

            this.onButtonClickListener=onButtonClickListener;
            this.onButtonShareListenr=onButtonShareListenr;

            btninfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonClickListener.onButtonClick(getAdapterPosition());
                }
            });
            btnshare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonShareListenr.onButtonShare(getAdapterPosition());
                }
            });

        }



    }

    public interface OnButtonClickListener{
        void onButtonClick(int position);

    }
    public interface OnButtonShareListenr{
        void onButtonShare(int position);

    }

}
