package com.djacm.alumniapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class InternshipHolder extends RecyclerView.ViewHolder {

   public ImageView mLogo;
    public TextView mTitle,mDescription;
    public InternshipCompanyClickListener internshipCompanyClickListener;
    public RelativeLayout cardLayout;

    public InternshipHolder(@NonNull View itemView) {
        super(itemView);
        this.mLogo=itemView.findViewById(R.id.company_logo);
        this.mDescription=itemView.findViewById(R.id.company_description);
        this.mTitle=itemView.findViewById(R.id.company_name);
        this.cardLayout = itemView.findViewById(R.id.IF_company_card_layout);
    }
}
