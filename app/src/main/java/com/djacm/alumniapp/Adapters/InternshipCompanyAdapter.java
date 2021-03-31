package com.djacm.alumniapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.djacm.alumniapp.Activity.BaseActivity;
import com.djacm.alumniapp.Activity.InternshipCompany;
import com.djacm.alumniapp.InternshipCompanyClickListener;
import com.djacm.alumniapp.InternshipHolder;
import com.djacm.alumniapp.Models.CommitteeMember;
import com.djacm.alumniapp.Models.InternshipCompanyModel;
import com.djacm.alumniapp.NestedFragmentListener;
import com.djacm.alumniapp.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.HashMap;

public class InternshipCompanyAdapter extends RecyclerView.Adapter<InternshipHolder>
{
    SharedPreferences.Editor editor;
    Activity mContext;

    public ArrayList<InternshipCompanyModel> models;
    NestedFragmentListener listener;

    private LinearLayoutManager layoutManager; //The layout manager associated with the recycler view
    private HashMap<Target,Object[]> targets = new java.util.HashMap<Target, Object[]>(); //HashMap for storing the objects associated with the Picasso Targets used for retrieving logo pics from firebase storage

    public InternshipCompanyAdapter(Activity mContext, ArrayList<InternshipCompanyModel> models,NestedFragmentListener listener, LinearLayoutManager lm) {
        this.mContext = mContext;
        this.models = models;
        this.listener=listener;
        this.layoutManager  = lm;
    }

    @NonNull
    @Override
    public InternshipHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.if_company_list,null);

        InternshipHolder holder = new InternshipHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final InternshipHolder internshipHolder, int i)
    {
        internshipHolder.mTitle.setText(models.get(i).getName());
        internshipHolder.mDescription.setText(models.get(i).getSkills());

        final int pos = i;
        internshipHolder.cardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ((BaseActivity)mContext).selectedIFCompany=models.get(pos);
                SharedPreferences.Editor editor = mContext.getSharedPreferences("SwitchTo",Context.MODE_PRIVATE).edit();
                editor.putString("goto","IntDet");
                editor.commit();listener.onSwitchToNextFragment();
            }
        });
        if(models.get(i).getLogoBmp() == null)
        {
            Target picassoTarget = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from)
                {
                    //Getting the objects associated with the target
                    Object[] targetContext = targets.get(this);
                    if(targetContext != null)
                    {
                        InternshipCompanyModel companyModel = (InternshipCompanyModel) targetContext[0];
                        Integer pos = (Integer) targetContext[1];
                        ImageView imageView = (ImageView) targetContext[2];

                        companyModel.setLogoBmp(bitmap);
                        if (pos >= InternshipCompanyAdapter.this.layoutManager.findFirstVisibleItemPosition() && pos <= InternshipCompanyAdapter.this.layoutManager.findLastVisibleItemPosition()) {
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
                    .load(models.get(i).getUrl())
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(picassoTarget);
            targets.put(picassoTarget, new Object[]{models.get(i), Integer.valueOf(i),internshipHolder.mLogo});

            //Displaying default pic while image loads
            internshipHolder.mLogo.setImageResource(R.drawable.default_committee_profile_pic);
        }
        else {
            internshipHolder.mLogo.setImageBitmap(models.get(i).getLogoBmp());
        }
    }

    @Override
    public int getItemCount() {
        return models.size();

    }
}

/*
internshipHolder.mTitle.setText(models.get(i).getName());
        internshipHolder.mDescription.setText(models.get(i).getSkills());
        Picasso.get().load(models.get(i).getUrl()).into(internshipHolder.mLogo);

        internshipHolder.setInternshipCompanyClickListener(new InternshipCompanyClickListener() {
            @Override
            public void onInternshipCompanyClickListener(View v, int position) {

                String gTitle = models.get(position).getName();
                String gSkills= models.get(position).getSkills();
                String gLogo=models.get(position).getUrl();

                editor=mContext.getSharedPreferences("IntDet", Context.MODE_PRIVATE).edit();
                editor.putString("iTitle",gTitle);
                editor.putString("iSkills",gSkills);
                editor.putString("iLogo",gLogo);
                editor.commit();
                editor=mContext.getSharedPreferences("SwitchTo", Context.MODE_PRIVATE).edit();
                editor.putString("goto","IntDet");
                editor.commit();
                listener.onSwitchToNextFragment();
//                Intent intent= new Intent(mContext, InternshipDetails.class);
//                intent.putExtra("iTitle",gTitle);
//                intent.putExtra("iDesc",gTitle);
                //intent.putExtra("iImage",bytes);

//                mContext.startActivity(intent);

            }
        });
 */
