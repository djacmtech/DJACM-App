package com.djacm.alumniapp;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.djacm.alumniapp.Adapters.StudentViewPagerAdaptor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


@SuppressLint("ValidFragment")
public class IF_Fragment extends Fragment
{
    private StudentViewPagerAdaptor.FragmentListener listener;

    @SuppressLint("ValidFragment")
    public IF_Fragment(StudentViewPagerAdaptor.FragmentListener listener) {
        // Required empty public constructor

        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
   private ImageView if_imageView;
   private TextView if_info1,if_info2;
    private String registrationLink;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_i_f_, container, false);

        if_imageView = v.findViewById(R.id.if_image);
        if_info1 = v.findViewById(R.id.if_info1);
        if_info2 = v.findViewById(R.id.if_info2);
        getIFInformation();

        AppCompatButton companyListBtn = v.findViewById(R.id.gotocompanyList_bt);
        companyListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SharedPreferences.Editor editor=getActivity().getSharedPreferences("SwitchTo", Context.MODE_PRIVATE).edit();
                editor.putString("goto","IfComp");
                editor.commit();
                listener.onSwitchToNextFragment();
            }
        });

        AppCompatButton registerBtn = v.findViewById(R.id.registerIF_bt);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try
                {
                    Uri webpage = Uri.parse(registrationLink);
                    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                    startActivity(intent);
                }
                catch(ActivityNotFoundException exe)
                {
                    Log.e("RGR_ERR", exe.getMessage());
                    Toast.makeText(getActivity(), "Unable to open registration link", Toast.LENGTH_SHORT).show();
                }
            }
        });


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
                registrationLink = dataSnapshot.child("RegistrationLink").getValue().toString();
                if(getActivity()!=null){
                    Glide.with(getActivity()).load(IF_imageuri).into(if_imageView);
                }
                if_info1.setText(IF_info1_string.replaceAll("\n","\n"));
                if_info2.setText(IF_info2_string.replaceAll("\n","\n"));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}