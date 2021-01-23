package com.djacm.alumniapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.djacm.alumniapp.Models.CommitteeMember;
import com.djacm.alumniapp.R;

import java.util.ArrayList;

public class CommitteeAdapter extends RecyclerView.Adapter<CommitteeAdapter.ViewHolder>
{
    private ArrayList<CommitteeMember> members; //The committee members to be
    private boolean isFaculty; //Whether the adapter is used to display faculty members in horizontal fashion

    class ViewHolder extends RecyclerView.ViewHolder
    {
        private CardView cardView; //The card view used for displaying the details

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            cardView = (CardView)itemView;
        }
    }

    public CommitteeAdapter(ArrayList<CommitteeMember> mems, boolean isFac)
    {
        members = mems;
        isFaculty = isFac;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        //Inflating the cardview
        View cardView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comittee_member_item, viewGroup,false);

        //Making changes to the card view in case of displaying faculty
        if(isFaculty)
        {
            cardView.setLayoutParams(new CardView.LayoutParams(CardView.LayoutParams.WRAP_CONTENT, CardView.LayoutParams.MATCH_PARENT));
            ((TextView)cardView.findViewById(R.id.committee_member_name)).setTextSize(16); //Decreasing text size
        }

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

        //Displaying the member photo
        ((ImageView)cardViewLayout.findViewById(R.id.committee_member_photo)).setImageBitmap(members.get(i).getPhoto());
    }

    @Override
    public int getItemCount()
    {
        return members.size();
    }

}
