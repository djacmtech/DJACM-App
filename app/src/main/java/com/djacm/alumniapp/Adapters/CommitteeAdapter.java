package com.djacm.alumniapp.Adapters;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.djacm.alumniapp.Models.CommitteeMember;
import com.djacm.alumniapp.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.HashMap;

public class CommitteeAdapter extends RecyclerView.Adapter<CommitteeAdapter.ViewHolder>
{
    private ArrayList<CommitteeMember> members; //The committee members to be
    private LinearLayoutManager layoutManager; //The layout manager associated with the recycler view
    public int currYear; //The year whose members are currently stored in the adapter
    private boolean isFaculty; //Whether the adapter is used to display faculty members in horizontal fashion
    private HashMap<Target,Object[]> targets = new HashMap<Target, Object[]>(); //HashMap for storing the objects associated with the Picasso Targets used for retrieving member pics from firebase storage

    class ViewHolder extends RecyclerView.ViewHolder
    {
        private CardView cardView; //The card view used for displaying the details

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            cardView = (CardView)itemView;
        }
    }

    public CommitteeAdapter(ArrayList<CommitteeMember> mems, boolean isFac, LinearLayoutManager lm)
    {
        members = mems;
        isFaculty = isFac;
        layoutManager = lm;
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

        if(members.get(i).getPhoto() == null)
        {
            Target picassoTarget = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from)
                {
                    //Getting the objects associated with the target
                    Object[] targetContext = targets.get(this);
                    if(targetContext != null)
                    {
                        CommitteeMember member = (CommitteeMember) targetContext[0];
                        Integer pos = (Integer) targetContext[1];
                        ImageView imageView = (ImageView) targetContext[2];

                        member.setPhoto(bitmap);
                        if (member.getYear() == currYear && pos >= CommitteeAdapter.this.layoutManager.findFirstVisibleItemPosition() && pos <= CommitteeAdapter.this.layoutManager.findLastVisibleItemPosition()) {
                            imageView.setImageBitmap(bitmap);
                        }
                    }

                    targets.remove(this);
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable)
                {
                    Log.e("DWLD_ERR",e.getMessage());
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            Picasso.get()
                    .load(members.get(i).getPicUrl())
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(picassoTarget);
            targets.put(picassoTarget, new Object[]{members.get(i), Integer.valueOf(i),cardViewLayout.findViewById(R.id.committee_member_photo) });

            //Displaying default pic while image loads
            ((ImageView)cardViewLayout.findViewById(R.id.committee_member_photo)).setImageResource(R.drawable.default_committee_profile_pic);
        }
        else {
            ((ImageView) cardViewLayout.findViewById(R.id.committee_member_photo)).setImageBitmap(members.get(i).getPhoto());
        }
    }

    @Override
    public int getItemCount()
    {
        return members.size();
    }

}
