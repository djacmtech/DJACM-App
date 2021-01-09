package com.imbuegen.alumniapp.Activity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.imbuegen.alumniapp.Adapters.AlumniListAdapter;
import com.imbuegen.alumniapp.Models.AlumniModel;
import com.imbuegen.alumniapp.Models.QuestionsModel;

import java.util.ArrayList;
import java.util.List;

import com.imbuegen.alumniapp.NestedFragmentListener;
import com.imbuegen.alumniapp.R;
import com.imbuegen.alumniapp.Service.SFHandler;

import static android.content.Context.MODE_PRIVATE;

public class AlumniFragment extends Fragment {

    //Displaying List of Alumnis
    NestedFragmentListener listener;
    ListView listViewAlumni;
    List<AlumniModel> AlumniModelList;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference deptRef ;
    //=database.getReference("Departments").child(fbDeptKey);
    SharedPreferences args;
SharedPreferences.Editor editor;
    //DatabaseReference companyRef = deptRef.child("Companies");

public AlumniFragment()
{}
@SuppressLint("ValidFragment")
public AlumniFragment(NestedFragmentListener listener)
{
    this.listener=listener;
}

    public void backPressed() {
        editor=getContext().getSharedPreferences("SwitchTo", Context.MODE_PRIVATE).edit();
        editor.putString("goto","Comp");
        editor.commit();

        listener.onSwitchToNextFragment();
    }
@Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_alumni_list, null);
        exitForAlumnis();
        listViewAlumni = v.findViewById(R.id.listViewAlumni);

        args=getActivity().getSharedPreferences("AlumniDet",MODE_PRIVATE);
        final String str1=args.getString("DeptName","EXTC");
        final String str2=args.getString("CompName","Infosys");


        AlumniModelList = new ArrayList<>();


        deptRef = database.getReference("Departments").child(str1);
        deptRef.keepSynced(true);
        DatabaseReference companyRef = deptRef.child("Companies");
        DatabaseReference alumniref=companyRef.child(str2);
        DatabaseReference alumniref2=alumniref.child("Alumnis");
        getActivity().setTitle(str2);



        alumniref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                AlumniModelList.clear();

                for (DataSnapshot alumniSnapshot : dataSnapshot.getChildren()) {

                    final AlumniModel alumni = alumniSnapshot.getValue(AlumniModel.class);
                    alumni.setCompany(str2);
                    alumni.setAlumniName(alumniSnapshot.child("Name").getValue().toString());
                    ArrayList<QuestionsModel> list = new ArrayList<>();
                    for(DataSnapshot qSnapshot: alumniSnapshot.child("questions").getChildren()) {
                        list.add(qSnapshot.getValue(QuestionsModel.class));
                    }

                    alumni.setDatabaseReferencePath(String.format("Departments/%s/Companies/%s/Alumnis/%s",str1,str2,alumniSnapshot.getKey()));
                    alumni.setQuestionsList(list);
                    AlumniModelList.add(alumni);
                }


                AlumniListAdapter adapter = new AlumniListAdapter(getActivity(),AlumniModelList,listener);
                listViewAlumni.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return v;
    }



    private void exitForAlumnis() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user == null ||  SFHandler.getString(getActivity().getSharedPreferences("Auth",MODE_PRIVATE),SFHandler.USER_KEY).equals("Alumni")) {

            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getActivity(),LoginActivity.class));
            //finish();

        }
    }
}
