package com.example.dashbord;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class dashAdapter extends RecyclerView.Adapter<com.example.dashbord.dashAdapter.dashViewHolder>{

    private Context context;
    //private newitem item;


    private OnButtonClickListener onButtonClickListener;
    //private OnButtonDelete onButtonDelete;
    
    private ArrayList<String> images = new ArrayList<>();
    private ArrayList<String> categories = new ArrayList<>();



    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public dashAdapter(Context context,ArrayList<String> images,ArrayList<String> categories,OnButtonClickListener onButtonClickListener) {

        this.images = images;
        this.context = context;
        this.categories = categories;
        this.onButtonClickListener=onButtonClickListener;
        //this.onButtonDelete=onButtonDelete;

    }

    @NonNull
    @Override
    public dashViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashrecyclerlayout,parent,false);
        dashViewHolder holder = new dashViewHolder(view,onButtonClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.dashbord.dashAdapter.dashViewHolder holder, int position) {
        Glide.with(context).asBitmap().apply(RequestOptions.circleCropTransform()).load(images.get(position)).into(holder.image);
        holder.category.setText(categories.get(position));
    }





    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class dashViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;
        TextView category;
        Button btninfo;
        ImageButton btncatdel;
        LinearLayout parentLayout;
        View.OnClickListener onClickListener;
        OnButtonClickListener onButtonClickListener;
        //OnButtonDelete onButtonDelete;
        public dashViewHolder(@NonNull View itemView,OnButtonClickListener onButtonClickListener) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            category=itemView.findViewById(R.id.category);
            parentLayout=itemView.findViewById(R.id.dashrecycler);
            //btncatdel=itemView.findViewById(R.id.btndelcat);



            this.onButtonClickListener=onButtonClickListener;
            //this.onButtonDelete = onButtonDelete;
            parentLayout.setOnClickListener(this);
            //btncatdel.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            onButtonClickListener.onButtonClick(getAdapterPosition());
           // onButtonDelete.onButtonClickDelete(getAdapterPosition());
        }
    }

    public interface OnButtonClickListener{
        void onButtonClick(int position);
    }
//    public interface OnButtonDelete{
//        void onButtonClickDelete(int position);
//    }

}
