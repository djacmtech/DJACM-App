package com.djacm.alumniapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Screen1Fragment extends Fragment {
    NestedFragmentListener listener;
    SharedPreferences.Editor editor;
    public void backPressed() {
        editor=getContext().getSharedPreferences("SwitchTo", Context.MODE_PRIVATE).edit();
        editor.putString("goto","IF");
        editor.commit();

        listener.onSwitchToNextFragment();
    }
    public Screen1Fragment()
    {}
    @SuppressLint("ValidFragment")
    public Screen1Fragment(NestedFragmentListener listener){this.listener=listener;}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
