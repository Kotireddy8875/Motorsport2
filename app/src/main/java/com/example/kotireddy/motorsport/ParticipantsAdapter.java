package com.example.kotireddy.motorsport;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ParticipantsAdapter extends RecyclerView.Adapter<ParticipantsAdapter.ViewHolder>{
    ArrayList<ParticipantsDescriptionPojo> teamData;
    Context mContext;

    public ParticipantsAdapter(Participants teamsDisplayActivity, ArrayList<ParticipantsDescriptionPojo> teamData) {
        this.teamData = teamData;
        this.mContext = teamsDisplayActivity;
    }

    @Override
    public ParticipantsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int id = R.layout.participants_list;
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View movieView = inflater.inflate(id, parent, false);
        ViewHolder viewHolder = new ViewHolder(movieView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ParticipantsAdapter.ViewHolder holder, int position) {
        Picasso.with(mContext).load(teamData.get(position).getLogo()).into(holder.logoView);
        holder.teamName.setText(teamData.get(position).getTeamName());
    }

    @Override
    public int getItemCount() {
        return teamData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView logoView;
        TextView teamName;

        public ViewHolder(final View itemView) {
            super(itemView);
            teamName = (TextView) itemView.findViewById(R.id.participant_name);
            logoView = (ImageView) itemView.findViewById(R.id.participant_logo);
            logoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, teamData.get(getAdapterPosition()).getTeamId(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, Description.class);
                    intent.putExtra(itemView.getResources().getString(R.string.Data), teamData);
                    intent.putExtra(itemView.getResources().getString(R.string.Position), getAdapterPosition());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
