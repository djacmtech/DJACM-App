package com.djacm.alumniapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.djacm.alumniapp.Activity.Applicant_Details;
import com.djacm.alumniapp.Models.InternshipCompanyModel;

public class Screen2Fragment extends Fragment {
    NestedFragmentListener listener;
    SharedPreferences.Editor editor;
    private DatabaseReference mDatabase;
    Applicant_Details details;
    //List<Company> company_list;
    //DataSnapshot company1_list;
    InternshipCompanyModel company_list;

    public void backPressed() {
        editor=getContext().getSharedPreferences("SwitchTo", Context.MODE_PRIVATE).edit();
        editor.putString("goto","IF");
        editor.commit();

        listener.onSwitchToNextFragment();
    }
    public Screen2Fragment()
    {}
    @SuppressLint("ValidFragment")
    public Screen2Fragment(NestedFragmentListener listener){this.listener=listener;}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("Screen2Fragment","Screen2 opened");
        View v=inflater.inflate(R.layout.receipt,null);
        final GridLayout gridLayout=v.findViewById(R.id.student_details);
        final GridLayout companies=v.findViewById(R.id.receipt_companies);
        final GridLayout total_Price=v.findViewById(R.id.reciept_Price);
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //Toast.makeText(getContext(), currentuser, Toast.LENGTH_SHORT).show();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query=mDatabase.child("Applications").child(currentuser);
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                details = dataSnapshot.getValue(Applicant_Details.class);
                //Toast.makeText(getContext(), details.getName(), Toast.LENGTH_SHORT).show();
                populateUI(gridLayout,0,total_Price);
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                // ...
            }
        };
        query.addValueEventListener(postListener);

        Query query1=mDatabase.child("Applied_To").child(currentuser);
        ValueEventListener postListener1 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                int i=1;
                for(DataSnapshot company1_list:dataSnapshot.getChildren()) {
                    //If company_list is an array, this gave a syntax error
                    company_list=company1_list.getValue(InternshipCompanyModel.class);
                    //Toast.makeText(getContext(), company_list.getName(), Toast.LENGTH_SHORT).show();
                    companies.addView(generateTextView("1. "));
                    populateUI(companies, 1, null);
                    i++;
                }
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                // ...
            }
        };
        query1.addValueEventListener(postListener1);
        // TODO The above line keeps crashing the app

        //gridLayout.addView(generateTextView("Blah"));

        //gridLayout.addView(generateTextView("Bleh"));

        //return super.onCreateView(inflater, container, savedInstanceState);
        return v;
    }
    public TextView generateTextView(String s)
    {
        TextView t=new TextView(getContext());
        t.setText(s);
        int padding_in_dp = 8;  // 8 dps
        final float scale = getResources().getDisplayMetrics().density;
        int padding_in_px = (int) (padding_in_dp * scale + 0.5f);
        t.setPadding(padding_in_px,padding_in_px,padding_in_px,padding_in_px);
        t.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        return t;
    }
    public void populateUI(GridLayout gridLayout,int mode,GridLayout Price)
    {
        if(mode==0) {
            gridLayout.addView(generateTextView("Name : "));
            gridLayout.addView(generateTextView(details.getName()));
            gridLayout.addView(generateTextView("Sap Id : "));
            gridLayout.addView(generateTextView(details.getSap()));
            gridLayout.addView(generateTextView("Email : "));
            gridLayout.addView(generateTextView(details.getEmail()));
            gridLayout.addView(generateTextView("Department : "));
            gridLayout.addView(generateTextView(details.getDepartment()));
            Price.addView(generateTextView("Total Price : "));
            Price.addView(generateTextView(String.valueOf(details.getAmount())));
        }
        if(mode==1)
        {

            gridLayout.addView(generateTextView(company_list.getName()));
            gridLayout.addView(generateTextView(company_list.getSkills()));
        }

    }
}
