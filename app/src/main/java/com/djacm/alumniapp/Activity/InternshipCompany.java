package com.djacm.alumniapp.Activity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.djacm.alumniapp.Adapters.CompanyListAdapter;
import com.djacm.alumniapp.Adapters.CompanyModel;
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


public class InternshipCompany extends Fragment
{

    NestedFragmentListener listener;
    SharedPreferences.Editor editor;

    private DatabaseReference dbRef;
    private InternshipCompanyAdapter companiesAdapter;

    private ArrayList<InternshipCompanyModel> techCompanies = new ArrayList<>();
    private ArrayList<InternshipCompanyModel> nonTechCompanies = new ArrayList<>();

    public InternshipCompany(){}
    @SuppressLint("ValidFragment")
    public InternshipCompany(NestedFragmentListener listener){
    this.listener=listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View fragmentView =inflater.inflate(R.layout.activity_internship_company,null);

        //Initializing firebase reference
        dbRef = FirebaseDatabase.getInstance().getReference("Companies");

        //Initializing recycler view
        RecyclerView companiesRv = fragmentView.findViewById(R.id.IF_companies_rv);
        companiesRv.setLayoutManager(new LinearLayoutManager(getContext()));
        companiesAdapter = new InternshipCompanyAdapter(getActivity(),techCompanies, listener,(LinearLayoutManager)companiesRv.getLayoutManager());
        companiesRv.setAdapter(companiesAdapter);

        //Initializing tab layout listener
        TabLayout tabLayout = fragmentView.findViewById(R.id.IF_companies_tab);
        TabLayout.Tab techTab = tabLayout.newTab().setText("Tech")
                                                .setTag("TECH");
        TabLayout.Tab nonTechTab = tabLayout.newTab().setText("Non-Tech")
                .setTag("N_TECH");
        tabLayout.addTab(techTab);
        tabLayout.addTab(nonTechTab);
        tabLayout.setTabTextColors(ContextCompat.getColor(getContext(), R.color.White),ContextCompat.getColor(getContext(), R.color.White));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), R.color.nav_selected_color));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                switch(tab.getTag().toString())
                {
                    case "TECH" : loadTechCompanies(); break;
                    case "N_TECH" : loadNonTechCompanies(); break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });

        //Initializing the progress bar
        ProgressBar progressBar = fragmentView.findViewById(R.id.IF_companies_pb);
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getActivity(), R.color.White), PorterDuff.Mode.SRC_IN);

        return fragmentView;
    }

    public void backPressed()
    {
        editor=getContext().getSharedPreferences("SwitchTo", Context.MODE_PRIVATE).edit();
        editor.putString("goto","IF");
        editor.commit();

        listener.onSwitchToNextFragment();
    }

    @Override
    public void onStart()
    {
        super.onStart();

        //Setting the default tab
        loadTechCompanies();
    }

    private void loadTechCompanies()
    {
        /*Loads and displays the list of tech companies*/

        if(techCompanies.size() != 0)
        {
            companiesAdapter.models = techCompanies;
            companiesAdapter.notifyDataSetChanged();
            return;
        }

        //Displaying the progress bar
        final ProgressBar progressBar = getView().findViewById(R.id.IF_companies_pb);
        progressBar.setVisibility(View.VISIBLE);

        dbRef.child("Tech").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                companiesAdapter.models = techCompanies;

                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    companiesAdapter.models.add(new InternshipCompanyModel(snapshot.child("Skills").getValue().toString(), snapshot.child("CmpDscr").getValue().toString(),snapshot.child("Name").getValue().toString(),
                            snapshot.child("LogoUrl").getValue().toString(),snapshot.child("JobDscr").getValue().toString(),snapshot.child("Perks").getValue().toString(),
                            Integer.parseInt(snapshot.child("Stipend").getValue().toString()),snapshot.child("WebsiteUrl").getValue().toString().trim()));
                }

                companiesAdapter.notifyDataSetChanged();

                //Hiding the progress bar
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadNonTechCompanies()
    {
        /*Loads and displays the list of non-tech companies*/

        if(nonTechCompanies.size() != 0)
        {
            companiesAdapter.models = nonTechCompanies;
            companiesAdapter.notifyDataSetChanged();
            return;
        }

        //Displaying the progress bar
        final ProgressBar progressBar = getView().findViewById(R.id.IF_companies_pb);
        progressBar.setVisibility(View.VISIBLE);

        dbRef.child("Non-Tech").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                companiesAdapter.models = nonTechCompanies;

                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    companiesAdapter.models.add(new InternshipCompanyModel(snapshot.child("Skills").getValue().toString(), snapshot.child("CmpDscr").getValue().toString(),snapshot.child("Name").getValue().toString(),
                            snapshot.child("LogoUrl").getValue().toString(),snapshot.child("JobDscr").getValue().toString(),snapshot.child("Perks").getValue().toString(),
                            Integer.parseInt(snapshot.child("Stipend").getValue().toString()),snapshot.child("WebsiteUrl").getValue().toString().trim()));
                }

                companiesAdapter.notifyDataSetChanged();

                //Hiding the progress bar
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

/*
ArrayList<InternshipCompanyModel> internshipCompanyModels=new ArrayList<>();
    RecyclerView mRecyclerView;
    InternshipCompanyAdapter internshipCompanyAdapter;
    DatabaseReference databaseReference;

mRecyclerView=v.findViewById(R.id.internship_recyclerView);



        /*mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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
     });*/



