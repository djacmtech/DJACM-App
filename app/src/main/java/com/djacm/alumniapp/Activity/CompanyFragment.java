package com.djacm.alumniapp.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.djacm.alumniapp.Adapters.CompanyListAdapter;
import com.djacm.alumniapp.Adapters.CompanyModel;

import java.util.ArrayList;
import java.util.List;

import com.djacm.alumniapp.NestedFragmentListener;
import com.djacm.alumniapp.R;

import static android.content.Context.MODE_PRIVATE;

public class CompanyFragment extends Fragment {

    //Displaying List of Companies
    static NestedFragmentListener listener;

    ListView listViewComapny;
    List<CompanyModel> companyModelList;

    String fbDeptKey;
    SharedPreferences deptname;
    SharedPreferences.Editor alumniargs;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference deptRef;//= database.getReference("Departments").child(fbDeptKey);
    String str;
    Menu menu;
    private CompanyListAdapter adapter;
    SharedPreferences.Editor editor;

    //DatabaseReference companyRef = deptRef.child("Companies");
    public CompanyFragment() {
    }

    @SuppressLint("ValidFragment")
    public CompanyFragment(NestedFragmentListener listener) {
        this.listener = listener;
    }

    public void backPressed() {
        editor = getContext().getSharedPreferences("SwitchTo", Context.MODE_PRIVATE).edit();
        editor.putString("goto", "Dept");
        editor.commit();

        listener.onSwitchToNextFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_company, null);
        listViewComapny = (ListView) v.findViewById(R.id.listViewCompany);
        setHasOptionsMenu(true);
        companyModelList = new ArrayList<>();
        deptname = getActivity().getSharedPreferences("DeptAdaptorToCompanyFrag", MODE_PRIVATE);
        str = deptname.getString("deptname", "EXTC");
        if (str.equals("Computer and IT")) {
            fbDeptKey = "Computers";
        }

        if (str.equals("EXTC")) {
            fbDeptKey = "EXTC";
        }
        if (str.equals("Electronics")) {
            fbDeptKey = "Electronics";
        }
        if (str.equals("Mechanical")) {
            fbDeptKey = "Mechanical";
        }
        if (str.equals("Production")) {
            fbDeptKey = "Production";
        }
        if (str.equals("Chemical")) {
            fbDeptKey = "Chemical";
        }

        deptRef = database.getReference("Departments").child(fbDeptKey);
        DatabaseReference companyRef = deptRef.child("Companies");
        deptRef.keepSynced(true);

        getActivity().setTitle(str);


        companyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                companyModelList.clear();

                for (DataSnapshot companySnapshot : dataSnapshot.getChildren()) {
                    String key = companySnapshot.getKey();
                    CompanyModel companyModel = new CompanyModel(key);
                    //companyList.add(key);
                    companyModelList.add(companyModel);
                }

                if (getActivity() != null) {
                    adapter = new CompanyListAdapter(getActivity(), companyModelList);


                    listViewComapny.setAdapter(adapter);
                }
                listViewComapny.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                        CompanyModel selectedCompany = (CompanyModel) listViewComapny.getItemAtPosition(i);

                        String selectedCompName = selectedCompany.getCompanyName();
                        alumniargs = getActivity().getSharedPreferences("AlumniDet", MODE_PRIVATE).edit();
                        alumniargs.clear();
                        alumniargs.putString("CompName", selectedCompName);
                        alumniargs.putString("DeptName", fbDeptKey);
                        alumniargs.commit();
                        alumniargs = getActivity().getSharedPreferences("SwitchTo", Context.MODE_PRIVATE).edit();
                        alumniargs.putString("goto", "Alumni");
                        alumniargs.commit();

                        listener.onSwitchToNextFragment();


                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return v;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //TODO:resolve this

        inflater.inflate(R.menu.search_toolbar, menu);
        MenuItem menuItem = menu.findItem(R.id.alumni_search);
        SearchView searchView = (SearchView) menuItem.getActionView();

    //Changing the search view icon
    int searchViewIconId = getResources().getIdentifier("android:id/search_button",null,null); //Identifier name obtained from searchView android xml layout (https://github.com/aosp-mirror/platform_frameworks_base/blob/master/core/res/res/layout/search_view.xml#L85)
    ((ImageView)searchView.findViewById(searchViewIconId)).setImageResource(R.drawable.search_icon);

    //Changing the search close icon
    int searchCloseIconId = getResources().getIdentifier("android:id/search_close_btn", null, null);
    ((ImageView)searchView.findViewById(searchCloseIconId)).setImageResource(R.drawable.close_icon);

    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            adapter.getFilter().filter(s);
            return true;
        }
    });
    }

}
