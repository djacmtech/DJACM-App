package com.imbuegen.alumniapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.imbuegen.alumniapp.Activity.THRegistration;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {
    NestedFragmentListener listener;
    Button screen1,screen2;
    Button eventregister;
    RecyclerView homepage_eventList;
    ImageView upcoming_event_pic;
    TextView event_name,event_date,event_desc;
    SharedPreferences.Editor editor;
    FirebaseDatabase db;
    TextView aboutus_firstpara,aboutus_secondpara,vision_msg,mission_para1,mission_para2;

    DatabaseReference reference;
    public HomeFragment()
    {}
    @SuppressLint("ValidFragment")
    public HomeFragment(NestedFragmentListener listener)
    {this.listener=listener;}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.if_screen1,null);
//        homepage_eventList = v.findViewById(R.id.home_page_eventlist);
        db = FirebaseDatabase.getInstance();
        reference = db.getReference("HomePageText");
        aboutus_firstpara = v.findViewById(R.id.about_us_firstpara);
        aboutus_secondpara = v.findViewById(R.id.about_us_secondpara);
        vision_msg = v.findViewById(R.id.vision_msg);
        mission_para1 = v.findViewById(R.id.mission_para1);
        mission_para2 = v.findViewById(R.id.mission_para2);
        eventregister = v.findViewById(R.id.event_register_button);
        eventregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), THRegistration.class));
            }
        });
        getData();
        event_name = v.findViewById(R.id.eventname);
        upcoming_event_pic = v.findViewById(R.id.upcoming_event_pic);
        event_date = v.findViewById(R.id.eventdate);
        event_desc = v.findViewById(R.id.eventdesc);
        getEventData();
        return v;
    }
    private void getData(){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String aboutUs1 = dataSnapshot.child("Aboutuspara1").getValue().toString();
                String aboutUs2 = dataSnapshot.child("Aboutus2").getValue().toString();
                String visionmsg = dataSnapshot.child("Vision").getValue().toString();
                String mission1 = dataSnapshot.child("mission1").getValue().toString();
                String mission2 = dataSnapshot.child("missionpart2").getValue().toString();
                aboutus_firstpara.setText(aboutUs1);
                aboutus_secondpara.setText(aboutUs2);
                vision_msg.setText(visionmsg);
                mission_para1.setText(mission1);
                mission_para2.setText(mission2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), (CharSequence) databaseError,Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void getEventData(){
        reference = db.getReference("UpcomingEvent");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String eventname = dataSnapshot.child("name").getValue().toString();
                String eventdate = dataSnapshot.child("date").getValue().toString();
                String eventdesc = dataSnapshot.child("description").getValue().toString();
                String imageUri = dataSnapshot.child("eventpic").getValue().toString();
                event_name.setText(eventname);
                event_date.setText(eventdate);
                event_desc.setText(eventdesc);
                Picasso.get().load(imageUri).resize(251,114).centerCrop().into(upcoming_event_pic);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
//        screen1=v.findViewById(R.id.screen1);
//        screen2=v.findViewById(R.id.screen2);
//        editor=getActivity().getSharedPreferences("SwitchTo",MODE_PRIVATE).edit();
//
//        screen1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editor.putString("goto","screen1");
//                editor.commit();
//                listener.onSwitchToNextFragment();
//            }
//        });
//        screen2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editor.putString("goto","screen2");
//                editor.commit();
//                listener.onSwitchToNextFragment();
//            }
//        });