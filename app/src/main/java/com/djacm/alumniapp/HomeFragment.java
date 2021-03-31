package com.djacm.alumniapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.djacm.alumniapp.Activity.UpcomingEventKnowMore;

public class HomeFragment extends Fragment {
    NestedFragmentListener listener;
    Button screen1,screen2;
    Button eventregister,knowmore;
    RecyclerView homepage_eventList;
    ImageView upcoming_event_pic;
    TextView event_name,event_date,event_desc;
    SharedPreferences.Editor editor;
   static FirebaseDatabase db;
    TextView aboutus_firstpara,aboutus_secondpara,vision_msg,mission_para1,mission_para2,mission_para3,mission_para4,mission_para5;
    TextView roundno1,roundno2,status_upcoming,status_ongoing,round1_date,round2_date;
    View upcoming_circle_view,ongoing_circle_view;
    ImageView codebash_img;
    CardView event_cardview;
    Uri event_link;
    LinearLayout upcomingEventCardLayout;
    RelativeLayout codebash_layout;

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
        mission_para3 = v.findViewById(R.id.mission_para3);
        mission_para4 = v.findViewById(R.id.mission_para4);
        mission_para5 = v.findViewById(R.id.mission_para5);
        knowmore = v.findViewById(R.id.upcoming_events_knowmore);
        eventregister = v.findViewById(R.id.event_register_button);
        event_cardview = v.findViewById(R.id.event_cardview);
        upcomingEventCardLayout = v.findViewById(R.id.upcoming_events);
        roundno1 = v.findViewById(R.id.roundno1);
        roundno2 = v.findViewById(R.id.roundno2);
        round1_date =v.findViewById(R.id.round_date1);
        round2_date = v.findViewById(R.id.round_date2);
        upcoming_circle_view = v.findViewById(R.id.upcoming_circle_view);
        ongoing_circle_view = v.findViewById(R.id.ongoing_circle);
        status_upcoming = v.findViewById(R.id.round_status_upcoming);
        status_ongoing = v.findViewById(R.id.round_status_ongoing);
        codebash_img = v.findViewById(R.id.codebash_img);

        eventregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_VIEW,event_link);
                startActivity(intent);
            }
        });

        knowmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UpcomingEventKnowMore.class));
            }
        });

        codebash_layout = v.findViewById(R.id.codebash_layout);
        getData();
        event_name = v.findViewById(R.id.eventname);
        upcoming_event_pic = v.findViewById(R.id.upcoming_event_pic);
        event_date = v.findViewById(R.id.eventdate);
        event_desc = v.findViewById(R.id.eventdesc);
        getEventData();
        getCodebashData();
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
                String mission3 = dataSnapshot.child("missionpart3").getValue().toString();
                String mission4 = dataSnapshot.child("missionpart4").getValue().toString();
                String mission5 = dataSnapshot.child("missionpart5").getValue().toString();
                aboutus_firstpara.setText(aboutUs1);
                aboutus_secondpara.setText(aboutUs2);
                vision_msg.setText(visionmsg);
                mission_para1.setText(mission1);
                mission_para2.setText(mission2);
                mission_para3.setText(mission3);
                mission_para4.setText(mission4);
                mission_para5.setText(mission5);
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
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                int isActive = Integer.parseInt(dataSnapshot.child("isActive").getValue().toString());
                if(isActive == 1)
                {
                    String eventname = dataSnapshot.child("name").getValue().toString();
                    String eventdate = dataSnapshot.child("date").getValue().toString();
                    String eventdesc = dataSnapshot.child("description").getValue().toString();
                    String imageUri = dataSnapshot.child("eventpic").getValue().toString();
                    String formlink = dataSnapshot.child("eventformlink").getValue().toString();
                    event_link = Uri.parse(formlink);
                    event_name.setText(eventname);
                    event_date.setText(eventdate);
                    event_desc.setText(eventdesc);
                    if(getActivity() != null)
                        Glide.with(getActivity()).load(imageUri).into(upcoming_event_pic);
                }
                else
                    upcomingEventCardLayout.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void getCodebashData(){
        reference = db.getReference("Codebash");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int iscodebashActive =  Integer.parseInt(dataSnapshot.child("isActive").getValue().toString());
                int isOngoing = Integer.parseInt(dataSnapshot.child("isOngoing").getValue().toString());
                // ongoing ==1 text color and view color green
                // ongoing ==0 text and view color red
                if(iscodebashActive==1)
                {
                    codebash_layout.setVisibility(View.VISIBLE);
                    String date1 = dataSnapshot.child("roundno1date").getValue().toString();
                    String status1 = dataSnapshot.child("roundno1status").getValue().toString();
                    String roundnum1 = dataSnapshot.child("roundno1").getValue().toString();
                    String date2 = dataSnapshot.child("roundno2-date").getValue().toString();
                    String status2 = dataSnapshot.child("roundno2status").getValue().toString();
                    String roundnum2 = dataSnapshot.child("roundno2").getValue().toString();
                    String imageUri = dataSnapshot.child("codebashimg").getValue().toString();
                    /// checking context/fragment null before setting text

                    if(getContext()!=null) {
                        if (isOngoing == 1) {
                            status_ongoing.setText(status1);
                            status_ongoing.setTextColor(getContext().getResources().getColor(R.color.ongoingcolor));
                            ongoing_circle_view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ongoing_circle));
                        } else {
                            status_ongoing.setText(status1);
                            status_ongoing.setTextColor(getContext().getResources().getColor(R.color.upcomingcolor));
                            ongoing_circle_view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.upcoming_circle));
                        }
                    }
                    roundno1.setText(roundnum1);
                    roundno2.setText(roundnum2);

                    status_upcoming.setText(status2);
                    round1_date.setText(date1);
                    round2_date.setText(date2);
                    if (getActivity() != null)
                        Glide.with(getActivity()).load(imageUri).into(codebash_img);
                }else{
                    codebash_layout.setVisibility(View.GONE);
                }
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
