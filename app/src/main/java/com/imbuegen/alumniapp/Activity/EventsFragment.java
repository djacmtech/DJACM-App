package com.imbuegen.alumniapp.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.imbuegen.alumniapp.Adapters.CompanyListAdapter;
import com.imbuegen.alumniapp.Adapters.CompanyModel;
import com.imbuegen.alumniapp.Adapters.EventListAdapter;
import com.imbuegen.alumniapp.Models.EventModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.imbuegen.alumniapp.NestedFragmentListener;
import com.imbuegen.alumniapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EventsFragment extends Fragment {


    ListView eventsListView;
    List<EventModel> eventModelList;
    NestedFragmentListener listener;
    SharedPreferences gt;
    String eventName ="";
    ImageView eventBg;
    StorageReference storageReference;
    ListView eventLists;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference eventsRef ;//= database.getReference("Departments").child(fbDeptKey);
    //DatabaseReference companyRef = deptRef.child("Companies");

   FirebaseStorage storage = FirebaseStorage.getInstance();

    public EventsFragment(){}
    @SuppressLint("ValidFragment")
    public EventsFragment(NestedFragmentListener listener){
    this.listener=listener;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v=inflater.inflate(R.layout.activity_events,null);
        eventsRef = database.getReference("notifications");

        eventsListView  = v.findViewById(R.id.list_events);

        eventModelList = new ArrayList<>();
        gt=getActivity().getSharedPreferences("EventInfo", Context.MODE_PRIVATE);
//        eventName =gt.getString("name","Placements 101");
        eventName = gt.getString("title","");
        eventBg = v.findViewById(R.id.eventBg);
        getImagedata();
        eventLists = v.findViewById(R.id.list_events);
        eventModelList = new ArrayList<>();




//        eventsRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                eventModelList.clear();
//                for (DataSnapshot s : dataSnapshot.getChildren()) {
//                    eventModelList.add(s.getValue(EventModel.class));
//                }
//                if(getActivity()!=null)
//                {
//                EventListAdapter adapter = new EventListAdapter(getActivity(),eventModelList,listener);
//                eventsListView.setAdapter(adapter);
//            }}

//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });

  //      BottomNavigationView navigation = v.findViewById(R.id.navigation);
//        navigation.getMenu().findItem(R.id.navigation_events).setCheckable(true).setChecked(true);
        return v;

        //putextra me key eventName variable se store karna ,mene getextra me eventName key use kiya hai

}

    private void getImagedata() {
        eventsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot di:dataSnapshot.getChildren()){
                    EventModel eventList =di.getValue(EventModel.class);
                    eventList.id = di.getKey();
                    eventModelList.add(eventList);
                }
                EventListAdapter adapter=new EventListAdapter(getActivity(),eventModelList,listener);
                eventLists.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
