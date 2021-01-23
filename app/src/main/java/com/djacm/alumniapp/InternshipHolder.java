package com.djacm.alumniapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class InternshipHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

   public ImageView mLogo;
    public TextView mTitle,mDescription;
    public InternshipCompanyClickListener internshipCompanyClickListener;

    public InternshipHolder(@NonNull View itemView) {
        super(itemView);
        this.mLogo=itemView.findViewById(R.id.company_logo);
        this.mDescription=itemView.findViewById(R.id.company_description);
        this.mTitle=itemView.findViewById(R.id.company_name);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        this.internshipCompanyClickListener.onInternshipCompanyClickListener(view,getLayoutPosition());

    }

    public void setInternshipCompanyClickListener(InternshipCompanyClickListener ic){

        this.internshipCompanyClickListener=ic;
    }
}
