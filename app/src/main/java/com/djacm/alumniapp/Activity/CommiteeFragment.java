package com.djacm.alumniapp.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.djacm.alumniapp.Adapters.CommitteeAdapter;
import com.djacm.alumniapp.Models.CommitteeMember;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;

import com.djacm.alumniapp.NestedFragmentListener;
import com.djacm.alumniapp.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class CommiteeFragment extends Fragment {
    private DatabaseReference dbRef; //A reference to the committee database

    private final int START_YEAR = 2018; //The year from which records start
    private final int CURR_YEAR = Calendar.getInstance().get(Calendar.YEAR); //The current year

    private ArrayList<CommitteeMember> committeeMembers; //List of the current year committee members
    private RecyclerView committeeRecyclerView; //The recycler view used for displaying the committee members
    private ProgressBar committeeLoadingBar; //The loading bar for the committee list

    private Spinner yearSpinner; //The spinner for selecting the year

    private NestedFragmentListener fragmentListener; //The nested fragment listener for switching the fragment on back press

    private Spinner.OnItemSelectedListener spinnerItemSelectedListener = new Spinner.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
            if (view != null) {
                String year = ((Integer) (START_YEAR + pos)).toString();

                //Displaying records for the selected year
                displayCommitteeMembers(year);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.activity_commitee, null);

        //Initializing the database reference
        dbRef = FirebaseDatabase.getInstance().getReference("Committee");

        //Initializing the faculty recycler view
        displayFaculty(fragmentView);

        //Initializing the loading bar
        committeeLoadingBar = fragmentView.findViewById(R.id.committee_loading_progressbar);
        committeeLoadingBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getActivity(), R.color.White), PorterDuff.Mode.SRC_IN);

        //Initializing the recycler view
        committeeMembers = new ArrayList<CommitteeMember>();
        committeeRecyclerView = fragmentView.findViewById(R.id.committee_recycler_view);
        committeeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        committeeRecyclerView.setAdapter(new CommitteeAdapter(committeeMembers, false, (LinearLayoutManager) committeeRecyclerView.getLayoutManager()));
        committeeRecyclerView.setNestedScrollingEnabled(false);

        //Initialzing spinner
        ArrayAdapter<String> years = new ArrayAdapter<String>(this.getContext(), R.layout.spinner_item);
        years.setDropDownViewResource(R.layout.spinner_item);
        //Adding the years to be displayed to the array adapter
        for (int a = START_YEAR; a < CURR_YEAR; ++a) {
            years.add((a + "-" + (a - 2000 + 1)));
        }
        yearSpinner = fragmentView.findViewById(R.id.committee_year_spinner);
        yearSpinner.setOnItemSelectedListener(spinnerItemSelectedListener);
        yearSpinner.setAdapter(years);

        return fragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();

        //Setting the spinner default selection
        ((Spinner) getView().findViewById(R.id.committee_year_spinner)).setSelection(CURR_YEAR - START_YEAR - 1);
    }

    @Override
    public void onStop()
    {
        super.onStop();
    }

    public void backPressed()
    {
        if (getContext() != null) {
            SharedPreferences.Editor sharedPref = getContext().getSharedPreferences("SwitchTo", Context.MODE_PRIVATE).edit();
            sharedPref.putString("goto", "Comp");
            sharedPref.commit();

            fragmentListener.onSwitchToNextFragment();
        }
    }

    private void displayCommitteeMembers(String year)
    {
        /*Loads and displays the details of the faculty memebers for the given year*/

        final int yearInt = Integer.parseInt(year);
        if (BaseActivity.committePhotoCache.containsKey(yearInt)) {
            //Getting the cached members list
            ArrayList<CommitteeMember> cachedList = BaseActivity.committePhotoCache.get(Integer.parseInt(year));

            //Updating the committee members list
            committeeMembers.clear();
            for (int a = 0; a < cachedList.size(); a++) {
                committeeMembers.add(cachedList.get(a));
            }

            //Updating the adapter
            ((CommitteeAdapter) committeeRecyclerView.getAdapter()).currYear = yearInt;
            committeeRecyclerView.getAdapter().notifyDataSetChanged();

            return;
        }

        //Showing loading bar
        committeeLoadingBar.setVisibility(View.VISIBLE);
        //Hiding the recycler view
        committeeRecyclerView.setVisibility(View.INVISIBLE);

        dbRef.child(year).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                //Hiding the loading bar
                committeeLoadingBar.setVisibility(View.INVISIBLE);
                //Showing the recycler view
                committeeRecyclerView.setVisibility(View.VISIBLE);

                committeeMembers.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CommitteeMember member = new CommitteeMember(snapshot.child("Name").getValue().toString(), snapshot.child("Position").getValue().toString(), yearInt, snapshot.child("PhotoUrl").getValue().toString());
                    committeeMembers.add(member);
                }

                //Updating the adapter
                ((CommitteeAdapter) committeeRecyclerView.getAdapter()).currYear = yearInt;
                committeeRecyclerView.getAdapter().notifyDataSetChanged();

                //Caching the results
                ArrayList<CommitteeMember> cachedList = new ArrayList<CommitteeMember>(committeeMembers);
                BaseActivity.committePhotoCache.put(yearInt, cachedList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }

    private void displayFaculty(View fragmentView)
    {
        /*Loads and displays the details of the faculty members*/

        //Initializing the recycler view used for displaying the faculty members
        final RecyclerView facultyRecyclerView = fragmentView.findViewById(R.id.faculty_recycler_view);
        facultyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        final ArrayList<CommitteeMember> facultyMembers = new ArrayList<CommitteeMember>(); //List of the faculty memebers
        facultyRecyclerView.setAdapter(new CommitteeAdapter(facultyMembers, true, (LinearLayoutManager)facultyRecyclerView.getLayoutManager()));

        if(!BaseActivity.facultyCache.isEmpty())
        {
            //Loading the members from the cache

            facultyMembers.clear();

            for(int a  = 0; a < BaseActivity.facultyCache.size(); ++a)
            {
                facultyMembers.add(BaseActivity.facultyCache.get(a));
            }

            //Updating the adapter
            ((CommitteeAdapter)facultyRecyclerView.getAdapter()).currYear = 0;
            facultyRecyclerView.getAdapter().notifyDataSetChanged();

            return;
        }

        //Showing the loading bar
        final ProgressBar pb = (ProgressBar)fragmentView.findViewById(R.id.faculty_loading_progressbar);
        pb.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getActivity(), R.color.White), PorterDuff.Mode.SRC_IN);
        pb.setVisibility(View.VISIBLE);

        //Getting the faculty info from firebase
        dbRef.child("Faculty").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                //Hiding the loading bar
                pb.setVisibility(View.INVISIBLE);

                //Adding the faculty to the list
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    CommitteeMember faculty = new CommitteeMember(snapshot.child("Name").getValue().toString(), snapshot.child("Position").getValue().toString(), 0, snapshot.child("PhotoUrl").getValue().toString());
                    facultyMembers.add(faculty);
                }

                //Caching the data
                BaseActivity.facultyCache = new ArrayList<CommitteeMember>(facultyMembers);

                //Updating the adapter
                ((CommitteeAdapter)facultyRecyclerView.getAdapter()).currYear = 0;
                facultyRecyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

    }
}
