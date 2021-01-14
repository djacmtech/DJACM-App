package com.imbuegen.alumniapp.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.imbuegen.alumniapp.Activity.BaseActivity;
import com.imbuegen.alumniapp.Adapters.MyDeptAdapter;
import com.imbuegen.alumniapp.Models.Department;

import java.util.ArrayList;

import com.imbuegen.alumniapp.NestedFragmentListener;
import com.imbuegen.alumniapp.R;

public class DepartmentsFragment extends Fragment// implements NestedFragmentListener {
{
    static NestedFragmentListener listener;
    ArrayList<Department> deptList;
    RecyclerView deptListView;
    GridView deptGridLayout;
    MyDeptAdapter myDeptAdapter;

    public DepartmentsFragment() {
    }

    @SuppressLint("ValidFragment")
    public DepartmentsFragment(NestedFragmentListener listener) {
        this.listener = listener;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // fragment layout === list of departments on alumni page
        View v = inflater.inflate(R.layout.fragmentlayout, null);
        deptList = new ArrayList<>();
        deptListView = v.findViewById(R.id.list_depts);
     //   deptGridLayout = v.findViewById(R.id.list_depts);

        instantiateDeptList();
        loadData();
        return v;
    }


    private void loadData() {
        deptList.clear();
        deptList.add(new Department("Computer and IT", R.mipmap.comps));
        deptList.add(new Department("EXTC", R.mipmap.extc));
        deptList.add(new Department("Electronics", R.mipmap.electronics));
        deptList.add(new Department("Mechanical", R.mipmap.mechanical));
        deptList.add(new Department("Production", R.mipmap.prod));
        deptList.add(new Department("Chemical", R.mipmap.chem));
        myDeptAdapter.notifyDataSetChanged();
    }

    private void instantiateDeptList() {

        myDeptAdapter = new MyDeptAdapter(getContext(), deptList, listener);
//        deptGridLayout.setAdapter((ListAdapter) myDeptAdapter);


        deptListView.setAdapter(myDeptAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        deptListView.setLayoutManager(gridLayoutManager);
     //   deptListView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}


