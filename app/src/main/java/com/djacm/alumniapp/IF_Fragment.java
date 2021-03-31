package com.djacm.alumniapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.djacm.alumniapp.Adapters.StudentViewPagerAdaptor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


@SuppressLint("ValidFragment")
public class IF_Fragment extends Fragment {



    @SuppressLint("ValidFragment")
    public IF_Fragment(StudentViewPagerAdaptor.FragmentListener listener) {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
   private ImageView if_imageView;
   private TextView if_info1,if_info2;
    private Button goto_company_list_bt,register_if;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_i_f_, container, false);
        if_imageView = v.findViewById(R.id.if_image);
        if_info1 = v.findViewById(R.id.if_info1);
        if_info2 = v.findViewById(R.id.if_info2);
        goto_company_list_bt = v.findViewById(R.id.gotocompanyList_bt);
        register_if = v.findViewById(R.id.registerIF_bt);
        getIFInformation();
        return v;
    }

    private void getIFInformation() {
        final DatabaseReference databaseReference = HomeFragment.db.getReference("IFInformation");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String IF_info1_string = dataSnapshot.child("ifInfo1").getValue().toString();
                String IF_info2_string = dataSnapshot.child("ifInfo2").getValue().toString();
                String IF_imageuri = dataSnapshot.child("ifImage").getValue().toString();
                if(getActivity()!=null){
                    Glide.with(getActivity()).load(IF_imageuri).into(if_imageView);
                }
                if_info1.setText(IF_info1_string);
                if_info2.setText(IF_info2_string);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}