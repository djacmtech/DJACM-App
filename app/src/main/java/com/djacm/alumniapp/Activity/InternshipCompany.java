package com.djacm.alumniapp.Activity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.djacm.alumniapp.Adapters.InternshipCompanyAdapter;
import com.djacm.alumniapp.Models.InternshipCompanyModel;
import com.djacm.alumniapp.NestedFragmentListener;
import com.djacm.alumniapp.R;

import java.util.ArrayList;


public class InternshipCompany extends Fragment {


    ArrayList<InternshipCompanyModel> internshipCompanyModels=new ArrayList<>();
    RecyclerView mRecyclerView;
    InternshipCompanyAdapter internshipCompanyAdapter;
    DatabaseReference databaseReference;
    NestedFragmentListener listener;
    SharedPreferences.Editor editor;

    public InternshipCompany(){}
    @SuppressLint("ValidFragment")
    public InternshipCompany(NestedFragmentListener listener){
    this.listener=listener;
    }

    public void backPressed() {
        editor=getContext().getSharedPreferences("SwitchTo", Context.MODE_PRIVATE).edit();
        editor.putString("goto","IF");
        editor.commit();

        listener.onSwitchToNextFragment();
    }
 @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.activity_internship_company,null);

        mRecyclerView=v.findViewById(R.id.internship_recyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

     databaseReference= FirebaseDatabase.getInstance().getReference().child("Companies");

     databaseReference.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

             if(dataSnapshot.exists())
             {   internshipCompanyModels.clear();

                 for(DataSnapshot companysnapshot:dataSnapshot.getChildren())
                 {
                     InternshipCompanyModel internshipCompanyModel=companysnapshot.getValue(InternshipCompanyModel.class);
                     Log.d("NAME",internshipCompanyModel.getName());

                     internshipCompanyModels.add(internshipCompanyModel);
                 }

                 internshipCompanyAdapter=new InternshipCompanyAdapter(getContext() ,internshipCompanyModels,listener);
                 mRecyclerView.setAdapter(internshipCompanyAdapter);
             }

             else
             {
                 Log.d("NAME2","Broooooooooooooooooooooooo");
             }
         }

         @Override
         public void onCancelled(@NonNull DatabaseError databaseError) {

         }
     });



return v;
    }

}


