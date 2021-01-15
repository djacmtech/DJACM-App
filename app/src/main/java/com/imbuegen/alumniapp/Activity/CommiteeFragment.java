package com.imbuegen.alumniapp.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
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
import java.util.HashMap;
import java.util.HashSet;

import su.j2e.rvjoiner.JoinableAdapter;
import su.j2e.rvjoiner.JoinableLayout;
import su.j2e.rvjoiner.RvJoiner;

import com.imbuegen.alumniapp.NestedFragmentListener;
import com.imbuegen.alumniapp.R;
import com.imbuegen.alumniapp.Service.CommitteePhotoDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import static android.support.v7.widget.RecyclerView.HORIZONTAL;

public class CommiteeFragment extends Fragment
{
    private DatabaseReference dbRef; //A reference to the committee database

    private final int START_YEAR = 2018; //The year from which records start
    private final int CURR_YEAR = Calendar.getInstance().get(Calendar.YEAR); //The current year

    private ArrayList<CommitteeMember> committeeMembers; //List of the current year committee members
    private RecyclerView committeeRecyclerView; //The recycler view used for displaying the committee members
    private ProgressBar committeeLoadingBar; //The loading bar for the committee list
    private HashMap<Target, Integer> commPicassoDownloadTargets = new HashMap<Target, Integer>(); //The picasso targets being used for downloading committee member pics

    private ArrayList<CommitteeMember> facultyMembers = new ArrayList<CommitteeMember>(); //List of the faculty members
    private HashMap<Target,Integer> facPicassoDownloadTargets = new HashMap<Target, Integer>(); //The picasso targets being used for downloading faculty member pics
    private HashSet<CommitteeMember> loadedMembers = new HashSet<CommitteeMember>(); //Set of members whose pics have been loaded

    private long latestDownloadTimestamp; //The timestamp for the latest download session

    private Spinner yearSpinner; //The spinner for selecting the year

    private NestedFragmentListener fragmentListener; //The nested fragment listener for switching the fragment on back press

    private Spinner.OnItemSelectedListener spinnerItemSelectedListener = new Spinner.OnItemSelectedListener()
    {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
        {
            if(view != null)
            {
                String year = ((Integer) (START_YEAR + pos)).toString();

                //Displaying records for the selected year
                displayCommitteeMembers(year);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView)
        {
        }
    };

    /*
    public CommiteeFragment()
    {

    }

    @SuppressLint("ValidFragment")
    public CommiteeFragment(NestedFragmentListener listener)
    {
        this.fragmentListener = listener;
    }*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View fragmentView =inflater.inflate(R.layout.activity_commitee, null);

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
        committeeRecyclerView.setAdapter(new CommitteeAdapter(committeeMembers, false));
        committeeRecyclerView.setNestedScrollingEnabled(false);

        //Initialzing spinner
        ArrayAdapter<String> years = new ArrayAdapter<String>(this.getContext(),R.layout.spinner_item);
        years.setDropDownViewResource(R.layout.spinner_item);
        //Adding the years to be displayed to the array adapter
        for(int a = START_YEAR; a < CURR_YEAR; ++a)
        {
            years.add((a + "-" + (a - 2000 + 1)));
        }
        yearSpinner = fragmentView.findViewById(R.id.committee_year_spinner);
        yearSpinner.setOnItemSelectedListener(spinnerItemSelectedListener);
        yearSpinner.setAdapter(years);
        yearSpinner.setSelection(CURR_YEAR - START_YEAR - 1);

        return fragmentView;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        //Setting the initial year
        //((Spinner)getView().findViewById(R.id.committee_year_spinner)).setSelection(CURR_YEAR - START_YEAR - 1);
    }

    @Override
    public void onStop()
    {
        super.onStop();

        //Cancelling committee download requests
        for(Target target : commPicassoDownloadTargets.keySet())
        {
            Picasso.get().cancelRequest(target);
        }
        commPicassoDownloadTargets.clear();

        //Cancelling faculty download requests
        for(Target target : facPicassoDownloadTargets.keySet())
        {
            Picasso.get().cancelRequest(target);
        }
        facPicassoDownloadTargets.clear();
    }

    public void backPressed()
    {
        if(getContext() != null)
        {
            SharedPreferences.Editor sharedPref = getContext().getSharedPreferences("SwitchTo", Context.MODE_PRIVATE).edit();
            sharedPref.putString("goto", "Comp");
            sharedPref.commit();

            fragmentListener.onSwitchToNextFragment();
        }
    }

    private void displayCommitteeMembers(String year)
    {
        //Checking if cached copy exists
        BaseActivity baseActivity = (BaseActivity)getActivity();
        if(baseActivity.committePhotoCache.containsKey(Integer.parseInt(year)))
        {
            //Getting the cached members list
            ArrayList<CommitteeMember> cachedList = baseActivity.committePhotoCache.get(Integer.parseInt(year));

            //Updating the committee members list
            committeeMembers.clear();
            for(int a = 0; a < cachedList.size(); a++)
            {
                committeeMembers.add(cachedList.get(a));
            }

            //Updating the adapter
            committeeRecyclerView.getAdapter().notifyDataSetChanged();

            return;
        }

        //Displaying the progress bar
        committeeLoadingBar.setVisibility(View.VISIBLE);

        //Hiding the recycler view
        committeeRecyclerView.setVisibility(View.INVISIBLE);

        final ArrayList<String> photoUrls = new ArrayList<String>(); //The urls for the committee member pics

        //Setting the timestamps
        latestDownloadTimestamp = System.currentTimeMillis() / 1000;
        final long currentTimestamp = latestDownloadTimestamp;

        //Querying the database
        dbRef.child(year).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                //Clearing the committee members list
                committeeMembers.clear();

                //Adding the new members to the list
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    CommitteeMember member = new CommitteeMember(snapshot.child("Name").getValue().toString(), snapshot.child("Position").getValue().toString());
                    photoUrls.add(snapshot.child("PhotoUrl").getValue().toString());
                    committeeMembers.add(member);
                }

                //Downloading the committee pics
                loadedMembers.clear();
                for(int a = 0; a < photoUrls.size(); ++a)
                {
                    Target target = new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from)
                        {
                            CommiteeFragment.this.RecyclerViewUpdated(bitmap, this, false, currentTimestamp);
                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable)
                        {
                            Log.e("Picasso_ERROR", e.getMessage());

                            //Displaying the default profile pic
                            CommiteeFragment.this.RecyclerViewUpdated(BitmapFactory.decodeResource(CommiteeFragment.this.getResources(), R.drawable.default_committee_profile_pic),
                                    this, false, currentTimestamp);
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    };

                    commPicassoDownloadTargets.put(target, a);
                    Picasso.get().load(photoUrls.get(a)).into(target);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                //Showing error message
                Toast.makeText(getActivity(), String.format("Unable to retrieve member info : %s", databaseError.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayFaculty(View fragmentView)
    {
        //Displaying the progress bar
        ProgressBar pb = (ProgressBar)fragmentView.findViewById(R.id.faculty_loading_progressbar);
        pb.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getActivity(), R.color.White), PorterDuff.Mode.SRC_IN);
        pb.setVisibility(View.VISIBLE);

        //Hiding the recycler view
        ((RecyclerView)fragmentView.findViewById(R.id.faculty_recycler_view)).setVisibility(View.INVISIBLE);

        final ArrayList<String> photoUrls = new ArrayList<String>(); //The urls for the faculty pics
        final View parentView = fragmentView; //The parent view

        //Querying the database
        dbRef.child("Faculty").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                //Adding the faculty to the list
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    CommitteeMember faculty = new CommitteeMember(snapshot.child("Name").getValue().toString(), snapshot.child("Position").getValue().toString());
                    photoUrls.add(snapshot.child("PhotoUrl").getValue().toString());
                    facultyMembers.add(faculty);
                }

                //Creating the adapter
                CommitteeAdapter facultyAdapter = new CommitteeAdapter(facultyMembers, true);

                //Initialzing the recycler view
                RecyclerView facultyRecyclerView = parentView.findViewById(R.id.faculty_div).findViewById(R.id.faculty_recycler_view);
                facultyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                facultyRecyclerView.setAdapter(facultyAdapter);

                //Downloading the faculty pics
                for(int a = 0; a < facultyMembers.size(); ++a)
                {
                    Target target = new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from)
                        {
                            CommiteeFragment.this.RecyclerViewUpdated(bitmap, this, true, 0);
                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable)
                        {
                            Log.e("Picasso_ERROR", e.getMessage());

                            //Displaying the default profile pic
                            CommiteeFragment.this.RecyclerViewUpdated(BitmapFactory.decodeResource(CommiteeFragment.this.getResources(), R.drawable.default_committee_profile_pic),
                                    this, true,0);
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    };

                    facPicassoDownloadTargets.put(target, a);
                    Picasso.get().load(photoUrls.get(a)).into(target);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                //Showing error message
                Toast.makeText(getActivity(), String.format("Unable to retrieve faculty info : %s", databaseError.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public synchronized void RecyclerViewUpdated(Bitmap bmp, Target target, boolean isFaculty, long timestamp)
    {
        if(!isFaculty)
        {
            //Checking if the download belonged to the latest session
            if(timestamp == latestDownloadTimestamp)
            {
                CommitteeMember commMember = committeeMembers.get(commPicassoDownloadTargets.get(target));
                if(!loadedMembers.contains(commMember))
                {
                    commMember.setPhoto(bmp);
                    loadedMembers.add(commMember);
                    Log.e("DWLD", commMember.getName());
                }
            }

            if(timestamp == latestDownloadTimestamp && getView() != null && loadedMembers.size() == committeeMembers.size())
            {
                //Displaying the recycler view
                committeeRecyclerView.setVisibility(View.VISIBLE);

                //Hiding the loading bar
                committeeLoadingBar.setVisibility(View.INVISIBLE);

                //Updating the adapter
                committeeRecyclerView.getAdapter().notifyDataSetChanged();

                //Clearing the targets list
                commPicassoDownloadTargets.clear();

                //Caching the pics
                BaseActivity baseActivity = (BaseActivity)getActivity();
                int year = START_YEAR + yearSpinner.getSelectedItemPosition();
                baseActivity.committePhotoCache.put(year, new ArrayList<CommitteeMember>(committeeMembers));
            }
        }
        else
        {
            facultyMembers.get(facPicassoDownloadTargets.get(target)).setPhoto(bmp);
            facPicassoDownloadTargets.remove(target);

            if(facPicassoDownloadTargets.isEmpty() && getView() != null)
            {
                //Hiding the progress bar
                ((ProgressBar) getView().findViewById(R.id.faculty_loading_progressbar)).setVisibility(View.INVISIBLE);

                //Displaying the recycler view
                RecyclerView rv = (RecyclerView) getView().findViewById(R.id.faculty_recycler_view);
                rv.setVisibility(View.VISIBLE);

                //Updating the adapter
                rv.getAdapter().notifyDataSetChanged();
            }
        }
    }
}
