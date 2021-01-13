//<<<<<<< HEAD:app/src/main/java/com/imbuegen/alumniapp/Activity/DetailedEventActivity.java
//package com.imbuegen.alumniapp.Activity;
//
//import android.support.design.widget.BottomNavigationView;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.widget.TextView;
//import com.imbuegen.alumniapp.Adapters.DetailedEventAdapter;
//import com.imbuegen.alumniapp.Models.EventMember;
//import com.imbuegen.alumniapp.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import su.j2e.rvjoiner.RvJoiner;
//
//=======
package com.imbuegen.alumniapp.Activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.imbuegen.alumniapp.Adapters.DetailedEventAdapter;
import com.imbuegen.alumniapp.Adapters.EventListAdapter;
import com.imbuegen.alumniapp.Models.EventMember;
import com.imbuegen.alumniapp.Models.EventModel;
import com.imbuegen.alumniapp.NestedFragmentListener;
import com.imbuegen.alumniapp.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import su.j2e.rvjoiner.RvJoiner;



public class DetailedEventFragment extends Fragment {


    ListView eventsListView;
    List<EventModel> eventModelList;
    NestedFragmentListener listener;
    SharedPreferences gt;
    String eventName ="";
    private List<EventModel> data;
    ImageView image1;
    ImageView image2;
    ImageView image3;
    ImageView image4;
    String id;
    ListView eventLists;
    SharedPreferences.Editor editor;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference eventsRef ;//= database.getReference("Departments").child(fbDeptKey);
    //DatabaseReference companyRef = deptRef.child("Companies");

    FirebaseStorage storage = FirebaseStorage.getInstance();

    public DetailedEventFragment(){}
    @SuppressLint("ValidFragment")
    public DetailedEventFragment(NestedFragmentListener listener){
        this.listener=listener;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.activity_detailed_event,null);
        eventsRef = database.getReference("notifications");

        eventModelList = new ArrayList<>();


        gt=getActivity().getSharedPreferences("EventInfo", Context.MODE_PRIVATE);
        eventName =gt.getString("name","Placements 101");
        id = gt.getString("id",id);
        Log.v("event_id",id);
        getActivity().setTitle(eventName);
        TextView tv = v.findViewById(R.id.et_name);
        tv.setText(eventName.toString());
        TextView tv1 = v.findViewById(R.id.et_body);
        tv1.setText(gt.getString("body","body"));
        image1 = v.findViewById(R.id.image1);
        image2= v.findViewById(R.id.image2);
        image3 = v.findViewById(R.id.image3);
        image4 = v.findViewById(R.id.image4);
        getImagedata();
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
//                    EventListAdapter adapter = new EventListAdapter(getActivity(),eventModelList,listener);
//                    eventsListView.setAdapter(adapter);
//                }}

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
               String url1;
               String url2;
               String url3;
               String url4;
               url1 = dataSnapshot.child(id).child("image1").getValue().toString();
                Glide.with(getActivity()).load(url1).into(image1);
                url2 = dataSnapshot.child(id).child("image2").getValue().toString();
                Glide.with(getActivity()).load(url2).into(image2);
                url3 = dataSnapshot.child(id).child("image3").getValue().toString();
                Glide.with(getActivity()).load(url3).into(image3);
                url4 = dataSnapshot.child(id).child("image4").getValue().toString();
                Glide.with(getActivity()).load(url4).into(image4);


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
    public void backPressed() {
        editor=getContext().getSharedPreferences("SwitchTo", Context.MODE_PRIVATE).edit();
        editor.putString("goto","Event");
        editor.commit();
        listener.onSwitchToNextFragment();

   }


}

//>>>>>>> 7f314d941a6338c5387ec60e3e78c7ade6107213:app/src/main/java/com/imbuegen/alumniapp/Activity/DetailedEventFragment.java
