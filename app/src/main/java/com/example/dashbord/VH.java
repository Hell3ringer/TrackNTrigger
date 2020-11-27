package com.example.dashbord;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VH extends RecyclerView.ViewHolder {
    TextView title_r,cmnt_r,date_r,time_r,repeat_r;
    View view;
    public VH(@NonNull View itemView) {
        super(itemView);
        view=itemView;
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClicklis.onItemlongClick(v,getAdapterPosition());
                return false;
            }
        });
    }
    public void setView(Context context, String title, String cmnt, String time, String date,String repeat){
        title_r=view.findViewById(R.id.Title_itemtv);
        cmnt_r=view.findViewById(R.id.Descri_itemtv);
        time_r=view.findViewById(R.id.Time_itemtv);
        date_r=view.findViewById(R.id.date_itemtv);
        repeat_r=view.findViewById(R.id.repeat_info);
        title_r.setText(title);
        cmnt_r.setText(cmnt);
        time_r.setText(time);
        date_r.setText(date);
        repeat_r.setText(repeat);
    }
    private com.example.dashbord.VH.ClickListener mClicklis;
    public interface ClickListener{
        void onItemlongClick(View view,int p);
    }
    public void setonClickListener(com.example.dashbord.VH.ClickListener clickListener){
        mClicklis=clickListener;
    }
}
