package com.imbuegen.alumniapp.Activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.imbuegen.alumniapp.Adapters.CommitteeAdapter;
import com.imbuegen.alumniapp.Models.CommitteeMember;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import su.j2e.rvjoiner.JoinableAdapter;
import su.j2e.rvjoiner.JoinableLayout;
import su.j2e.rvjoiner.RvJoiner;import com.imbuegen.alumniapp.R;

import static android.support.v7.widget.RecyclerView.HORIZONTAL;

public class CommiteeFragment extends Fragment
{
    private DatabaseReference dbRef; //A reference to the committee database

    private final int START_YEAR = 2018; //The year from which records start
    private final int CURR_YEAR = Calendar.getInstance().get(Calendar.YEAR); //The current year

    private ArrayList<CommitteeMember> committeeMembers; //List of the current year committee members
    private CommitteeAdapter adapter; //Adapter for the recycler view

    private Spinner.OnItemSelectedListener spinnerItemSelectedListener = new Spinner.OnItemSelectedListener()
    {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
        {
            String year = ((Integer)(START_YEAR + pos)).toString();

            //Displaying records for the selected year
            displayCommitteeMembers(year);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView)
        {
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View fragmentView =inflater.inflate(R.layout.activity_commitee, null);

        //Initializing the database reference
        dbRef = FirebaseDatabase.getInstance().getReference("Committee");

        //Initializing the faculty recycler view
        displayFaculty(fragmentView);

        //Initializing the recycler view
        committeeMembers = new ArrayList<CommitteeMember>();
        adapter = new CommitteeAdapter(committeeMembers);
        RecyclerView recyclerView = fragmentView.findViewById(R.id.committee_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        //Initialzing spinner
        ArrayAdapter<String> years = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item);
        years.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        //Adding the years to be displayed to the array adapter
        for(int a = START_YEAR; a <= CURR_YEAR; ++a)
        {
            years.add((a + "-" + (a - 2000 + 1)));
        }
        final Spinner yearSpinner = fragmentView.findViewById(R.id.committee_year_spinner);
        yearSpinner.setAdapter(years);
        yearSpinner.setOnItemSelectedListener(spinnerItemSelectedListener);

        return fragmentView;
    }

    private void displayCommitteeMembers(String year)
    {
        //Retrieving data from the database
        dbRef.child(year).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                //Clearing the committee members list
                committeeMembers.clear();

                //Adding the new members to the list
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    CommitteeMember member = new CommitteeMember(snapshot.child("PhotoUrl").getValue().toString(), snapshot.child("Name").getValue().toString(), snapshot.child("Position").getValue().toString());
                    committeeMembers.add(member);
                }

                //Updating the adapter
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                //Showing the error message
                Toast.makeText(getActivity(), "Unable to retrieve committee info : " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayFaculty(View fragmentView)
    {
        final ArrayList<CommitteeMember> facultyMembers = new ArrayList<CommitteeMember>(); //List of the faculty members
        final View parentView = fragmentView; //The parent view

        //Querying the database
        dbRef.child("Faculty").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                //Adding the faculty to the list
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    CommitteeMember faculty = new CommitteeMember(snapshot.child("PhotoUrl").getValue().toString(), snapshot.child("Name").getValue().toString(), snapshot.child("Position").getValue().toString());
                    facultyMembers.add(faculty);
                }

                //Creating the adapter
                CommitteeAdapter facultyAdapter = new CommitteeAdapter(facultyMembers);

                //Initialzing the recycler view
                RecyclerView facultyRecyclerView = parentView.findViewById(R.id.faculty_div).findViewById(R.id.faculty_recycler_view);
                facultyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                facultyRecyclerView.setAdapter(facultyAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                //Showing error message
                Toast.makeText(getActivity(), String.format("Unable to retrieve faculty info : %s", databaseError.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
