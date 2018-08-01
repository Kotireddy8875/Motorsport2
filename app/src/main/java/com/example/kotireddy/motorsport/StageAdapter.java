package com.example.kotireddy.motorsport;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StageAdapter extends RecyclerView.Adapter<StageAdapter.ViewHolder> {
    Context c;
    ArrayList<Stagepojo> arrayList;
    public StageAdapter(MainActivity mainActivity, ArrayList<Stagepojo> arrayListLeague) {
        this.c=mainActivity;
        this.arrayList=arrayListLeague;
    }

    @NonNull
    @Override
    public StageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View v= LayoutInflater.from(c).inflate(R.layout.liststages,parent,false);
      return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StageAdapter.ViewHolder holder, int position) {
        holder.textView.setText(arrayList.get(position).getStrLeague());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CardView cardView;
        public ViewHolder(final View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.stagename);
            cardView=itemView.findViewById(R.id.myCardView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(c,Participants.class);
                    intent.putExtra(itemView.getResources().getString(R.string.myString),arrayList.get(getAdapterPosition()).getStrLeague());
                    c.startActivity(intent);
                }
            });
        }
    }
}
