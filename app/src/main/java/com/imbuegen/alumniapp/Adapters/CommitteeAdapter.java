package com.imbuegen.alumniapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.imbuegen.alumniapp.Models.CommitteeMember;
import com.imbuegen.alumniapp.R;
import com.imbuegen.alumniapp.Service.CommitteePhotoDownloader;

import java.util.ArrayList;

public class CommitteeAdapter extends RecyclerView.Adapter<CommitteeAdapter.ViewHolder>
{
    private ArrayList<CommitteeMember> members; //The committee members to be displayed

    class ViewHolder extends RecyclerView.ViewHolder
    {
        private CardView cardView; //The card view used for displaying the details

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            cardView = (CardView)itemView;
        }
    }

    public CommitteeAdapter(ArrayList<CommitteeMember> mems)
    {
        members = mems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        //Inflating the cardview
        View cardView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comittee_member_item, viewGroup,false);

        //Initializing the view holder
        ViewHolder viewHolder = new ViewHolder(cardView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i)
    {
        RelativeLayout cardViewLayout = viewHolder.cardView.findViewById(R.id.committee_card_layout); //Getting the relative layout located in the cardview

        //Displaying member name
        ((TextView)cardViewLayout.findViewById(R.id.committee_member_name)).setText(members.get(i).getName());

        //Displaying the member position
        ((TextView)cardViewLayout.findViewById(R.id.committee_member_position)).setText(members.get(i).getPosition());

        //Downloading and displaying the member photo
        ImageView imageView = cardViewLayout.findViewById(R.id.committee_member_photo_card).findViewById(R.id.committee_member_photo);
        CommitteePhotoDownloader photoDownloader = new CommitteePhotoDownloader(imageView);
        photoDownloader.execute(members.get(i).getPhotoUrl());
    }

    @Override
    public int getItemCount()
    {
        return members.size();
    }

}
