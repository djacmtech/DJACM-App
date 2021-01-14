package com.imbuegen.alumniapp.Activity;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.imbuegen.alumniapp.Models.ParticipantDetails;
import com.imbuegen.alumniapp.R;

import java.util.HashMap;
import java.util.Objects;

public class THRegistration extends AppCompatActivity {
    private String ev_dept1, event_year1, ev_name1, ev_email1, ev_phone1
            ,evdept2,evyear2,evname2,evemail2,evphone2,teamname;
    EditText name_et1, phone_et1, email_et1,name_et2,phone_et2,email_et2;
    private Spinner firstdepartmentspinner,seconddepartmentspinner;
   private Spinner firstyearspinner, secondyearspinner;
   FirebaseDatabase db;
   ParticipantDetails participant;
   DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_h_registration);

        Button ev_register = findViewById(R.id.ev_register_button);
        db= FirebaseDatabase.getInstance();
        dbref = db.getReference("Participants");
        final HashMap<String,ParticipantDetails> participantDetailsHashMap = new HashMap<>();

        ev_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDetails();
                Log.v("details",  ev_phone1 + " "+ev_email1 +" "+ev_name1+" "+ev_dept1+" "+event_year1);
                Log.v("details of second ",  evphone2 +" "+ evemail2 +" "+ evname2+" "+evdept2+ " " +evyear2);
//                participantDetailsHashMap.put("Member",new ParticipantDetails(teamname,ev_name1,ev_email1,ev_phone1,ev_dept1,event_year1,
//                        evname2,evemail2,evphone2,evdept2,evyear2));
                DatabaseReference member = dbref.child("participant");
                DatabaseReference newmember = dbref.push();
                newmember.setValue(new ParticipantDetails(teamname,ev_name1,ev_email1,ev_phone1,ev_dept1,event_year1,
                    evname2,evemail2,evphone2,evdept2,evyear2));
                member.setValue(participantDetailsHashMap);


            }
        });
         firstdepartmentspinner = findViewById(R.id.event_firstmember_dept);
         seconddepartmentspinner = findViewById(R.id.event_secondmember_dept);
        String[] depts = new String[]{"Computers", "IT", "EXTC", "Electronics", "Mechanical", "Production", "Chemical"};
        ArrayAdapter<String> deptAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, depts);
        firstdepartmentspinner.setAdapter(deptAdapter);
        seconddepartmentspinner.setAdapter(deptAdapter);
        firstdepartmentspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ev_dept1 = (String) parent.getItemAtPosition(position);
                Log.v("DEPTARTMENT", ev_dept1);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        seconddepartmentspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                evdept2 = (String) parent.getItemAtPosition(position);
                Log.v("DEPTARTMENT2", evdept2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        firstyearspinner = findViewById(R.id.event_firstmember_year);
        secondyearspinner = findViewById(R.id.event_secondmember_year);
        firstyearspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("Year", (String) parent.getItemAtPosition(position));
                event_year1 = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        secondyearspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("Year2", (String) parent.getItemAtPosition(position));
                evyear2 = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        String[] year = new String[]{"First", "Second", "Third", "Fourth"};
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, year);
        firstyearspinner.setAdapter(yearAdapter);
        secondyearspinner.setAdapter(yearAdapter);
    }
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private void getDetails(){
        EditText teamname_et = findViewById(R.id.event_teamname);
        teamname = teamname_et.getText().toString();
        name_et1 = findViewById(R.id.event_firstmember_name);
        ev_name1 = name_et1.getText().toString();
        email_et1 = findViewById(R.id.event_firstmember_email);
        ev_email1 = email_et1.getText().toString().trim();

        if(ev_email1.isEmpty() || ev_name1.isEmpty() || ev_phone1.isEmpty()){
            Toast.makeText(this,"Please enter all details",Toast.LENGTH_SHORT).show();
        }
        phone_et1 = findViewById(R.id.event_firstmember_phone);
        ev_phone1 = phone_et1.getText().toString().trim();
        name_et2 = findViewById(R.id.event_secondmember_name);
        evname2  = name_et2.getText().toString();
        email_et2 = findViewById(R.id.event_secondmember_email);
        evemail2 = email_et2.getText().toString().trim();
        phone_et2 = findViewById(R.id.event_secondmember_phone);
        evphone2 = phone_et2.getText().toString().trim();



    }
}