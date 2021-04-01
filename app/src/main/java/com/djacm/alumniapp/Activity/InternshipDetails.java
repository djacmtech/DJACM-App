package com.djacm.alumniapp.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.djacm.alumniapp.Models.InternshipCompanyModel;
import com.djacm.alumniapp.NestedFragmentListener;
import com.djacm.alumniapp.R;
import com.squareup.picasso.Picasso;

public class InternshipDetails extends Fragment {

    TextView companyTitle, companyDesc, companySkills, companyWebsite, job_desc, stipend, perks_offered;
    ImageView companyImage;
    //    Button applyBtn,submitBtn,backBtn;
//
//    String name,sap,branch,yr;
//    int coun,amnt;
    DatabaseReference databaseReference;
    //DatabaseReference databaseReference1,databaseReference2,databaseReference3,databaseReference4;

    NestedFragmentListener listener;
    SharedPreferences.Editor editor;
    SharedPreferences shpref;
    String website;

    public InternshipDetails() {
    }

    @SuppressLint("ValidFragment")
    public InternshipDetails(NestedFragmentListener listener) {
        this.listener = listener;
    }

    public void backPressed() {
        editor = getContext().getSharedPreferences("SwitchTo", Context.MODE_PRIVATE).edit();
        editor.putString("goto", "IfComp");
        editor.commit();

        listener.onSwitchToNextFragment();
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_internship_details, null);

        companyTitle = v.findViewById(R.id.company_title);
        companyDesc = v.findViewById(R.id.company_desc);
        companySkills = v.findViewById(R.id.company_skill);
        companyImage = v.findViewById(R.id.company_logo);
        companyWebsite = v.findViewById(R.id.company_website);
        job_desc = v.findViewById(R.id.job_desc);
        stipend = v.findViewById(R.id.stipend);
        perks_offered = v.findViewById(R.id.perks_offered);

        shpref = getContext().getSharedPreferences("IntDet", Context.MODE_PRIVATE);
        final InternshipCompanyModel companyModel = ((BaseActivity) getActivity()).selectedIFCompany;
        Toast.makeText(getContext(), companyModel.getName(), Toast.LENGTH_SHORT).show();
        companyTitle.setText(companyModel.getName());
        companyDesc.setText(companyModel.getCompanyDescription());
        job_desc.setText(companyModel.getJobDescription());
        website = companyModel.getWebsiteUrl();
        companyWebsite.setText(website);
        companyWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try
                {
                    Uri webpage = Uri.parse(website);
                    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                    startActivity(intent);
                }
                catch(ActivityNotFoundException exe)
                {
                    Log.e("RGR_ERR", exe.getMessage());
                    Toast.makeText(getActivity(), "Unable to open registration link", Toast.LENGTH_SHORT).show();
                }
            }
        });

        companySkills.setText(companyModel.getSkills());
        stipend.setText("Rs. " + companyModel.getStipend());
        perks_offered.setText(companyModel.getPerks());
        companyImage.setImageBitmap(companyModel.getLogoBmp());
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}


//        final String mTitle = shpref.getString("iTitle", "");
//        String mSkills = shpref.getString("iSkills", "");
//        String CompUrl = shpref.getString("iLogo", "");
        //getActivity().setTitle(mTitle);
//        companyTitle.setText(mTitle);
//        companySkills.setText(mSkills);
//        Picasso.get().load(CompUrl).into(companyImage);
//        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        databaseReference4=FirebaseDatabase.getInstance().getReference("Companies").child(mTitle);
//        databaseReference1=FirebaseDatabase.getInstance().getReference("Applications").child(user);
//        databaseReference1.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//               name=dataSnapshot.child("name").getValue().toString();
//                sap=dataSnapshot.child("sap").getValue().toString();
//                branch=dataSnapshot.child("department").getValue().toString();
//                yr=dataSnapshot.child("year").getValue().toString();
//                coun=Integer.parseInt(dataSnapshot.child("count").getValue().toString());
//                amnt=Integer.parseInt(dataSnapshot.child("amount").getValue().toString());
//            }

//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {

//            }
//        });

//        databaseReference4.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                InternshipCompanyModel internshipCompanyModel=dataSnapshot.getValue(InternshipCompanyModel.class);
//                //companyDesc.setText(internshipCompanyModel.getDescription());

//            }

//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {


//            }
//        });


//        submitBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {

//                String user2 = FirebaseAuth.getInstance().getCurrentUser().getUid();
//                if(coun==0)
//                {
//                    databaseReference3.child("Applications").child(user2).child("amount").setValue(amnt+100);
//                    databaseReference2.child("Applied_To").child(user2).child(mTitle).child("name").setValue(mTitle);
//                    databaseReference2.child("Applied_To").child(user2).child(mTitle).child("skills").setValue(mTitle);
//                    databaseReference.child("APC").child(mTitle).child(sap).child("name").setValue(name);
//                    databaseReference.child("APC").child(mTitle).child(sap).child("sap").setValue(sap);
//                    databaseReference.child("APC").child(mTitle).child(sap).child("dept").setValue(branch);
//                    databaseReference.child("APC").child(mTitle).child(sap).child("year").setValue(yr);
//                }
//                else{
//                    databaseReference2.child("Applied_To").child(user2).child(mTitle).child("name").setValue(mTitle);
//                    databaseReference2.child("Applied_To").child(user2).child(mTitle).child("skills").setValue(mTitle);
//                    databaseReference3.child("Applications").child(user2).child("count").setValue(coun-1);
//                    databaseReference.child("APC").child(mTitle).child(sap).child("name").setValue(name);
//                    databaseReference.child("APC").child(mTitle).child(sap).child("sap").setValue(sap);
//                    databaseReference.child("APC").child(mTitle).child(sap).child("dept").setValue(branch);
//                    databaseReference.child("APC").child(mTitle).child(sap).child("year").setValue(yr);

//                }

////                databaseReference= FirebaseDatabase.getInstance().getReference();
////                Application_Details application_details;
////               // application_details=
//                Toast.makeText(getContext(),"Applied Successfully",Toast.LENGTH_SHORT).show();
//                submitBtn.setVisibility(View.INVISIBLE);
//                applyBtn.setVisibility(View.INVISIBLE);
//                backBtn.setVisibility(View.INVISIBLE);
//            }
//        });

//        shpref=getContext().getSharedPreferences("IntDet", Context.MODE_PRIVATE);
//
//        final String mTitle=shpref.getString("iTitle","");
//        String mSkills=shpref.getString("iSkills","");
//        String CompUrl=shpref.getString("iLogo","");
//        //getActivity().setTitle(mTitle);
//        companyTitle.setText(mTitle);
//        companySkills.setText(mSkills);
//        Picasso.get().load(CompUrl).into(companyImage);
//        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        databaseReference4=FirebaseDatabase.getInstance().getReference("Companies").child(mTitle);
//        databaseReference1=FirebaseDatabase.getInstance().getReference("Applications").child(user);
//        databaseReference1.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//               name=dataSnapshot.child("name").getValue().toString();
//                sap=dataSnapshot.child("sap").getValue().toString();
//                branch=dataSnapshot.child("department").getValue().toString();
//                yr=dataSnapshot.child("year").getValue().toString();
//                coun=Integer.parseInt(dataSnapshot.child("count").getValue().toString());
//                amnt=Integer.parseInt(dataSnapshot.child("amount").getValue().toString());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//        databaseReference4.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                InternshipCompanyModel internshipCompanyModel=dataSnapshot.getValue(InternshipCompanyModel.class);
//                companyDesc.setText(internshipCompanyModel.getDescription());
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//
//            }
//        });
//        databaseReference2=FirebaseDatabase.getInstance().getReference();
//        databaseReference3=FirebaseDatabase.getInstance().getReference();
//        databaseReference=FirebaseDatabase.getInstance().getReference();

//        applyBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                applyBtn.setVisibility(View.INVISIBLE);
//                backBtn.setVisibility(View.VISIBLE);
//                submitBtn.setVisibility(View.VISIBLE);
//            }
//        });
//
//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                submitBtn.setVisibility(View.INVISIBLE);
//                applyBtn.setVisibility(View.VISIBLE);
//                backBtn.setVisibility(View.INVISIBLE);
//
//            }
//        });


//        submitBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String user2 = FirebaseAuth.getInstance().getCurrentUser().getUid();
//                if(coun==0)
//                {
//                    databaseReference3.child("Applications").child(user2).child("amount").setValue(amnt+100);
//                    databaseReference2.child("Applied_To").child(user2).child(mTitle).child("name").setValue(mTitle);
//                    databaseReference2.child("Applied_To").child(user2).child(mTitle).child("skills").setValue(mTitle);
//                    databaseReference.child("APC").child(mTitle).child(sap).child("name").setValue(name);
//                    databaseReference.child("APC").child(mTitle).child(sap).child("sap").setValue(sap);
//                    databaseReference.child("APC").child(mTitle).child(sap).child("dept").setValue(branch);
//                    databaseReference.child("APC").child(mTitle).child(sap).child("year").setValue(yr);
//                }
//                else{
//                    databaseReference2.child("Applied_To").child(user2).child(mTitle).child("name").setValue(mTitle);
//                    databaseReference2.child("Applied_To").child(user2).child(mTitle).child("skills").setValue(mTitle);
//                    databaseReference3.child("Applications").child(user2).child("count").setValue(coun-1);
//                    databaseReference.child("APC").child(mTitle).child(sap).child("name").setValue(name);
//                    databaseReference.child("APC").child(mTitle).child(sap).child("sap").setValue(sap);
//                    databaseReference.child("APC").child(mTitle).child(sap).child("dept").setValue(branch);
//                    databaseReference.child("APC").child(mTitle).child(sap).child("year").setValue(yr);
//
//                }
//
////                databaseReference= FirebaseDatabase.getInstance().getReference();
////                Application_Details application_details;
////               // application_details=
//                Toast.makeText(getContext(),"Applied Successfully",Toast.LENGTH_SHORT).show();
//                submitBtn.setVisibility(View.INVISIBLE);
//                applyBtn.setVisibility(View.INVISIBLE);
//                backBtn.setVisibility(View.INVISIBLE);
//            }
//        });

        //      actionBar.setTitle(mTitle);
//        companyTitle.setText(mTitle);
//        companyDesc.setText(mDesc);




