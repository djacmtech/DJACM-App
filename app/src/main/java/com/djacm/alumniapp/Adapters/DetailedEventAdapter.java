package com.djacm.alumniapp.Adapters;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.djacm.alumniapp.Models.EventModel;
import com.djacm.alumniapp.NestedFragmentListener;
import com.djacm.alumniapp.R;

import java.util.List;

public class DetailedEventAdapter extends ArrayAdapter<EventModel> {

    private Activity context;
    private List<EventModel> data;
    NestedFragmentListener listener;
    SharedPreferences.Editor editor;
    ImageView image1;
    ImageView image2;
    ImageView image3;
    ImageView image4;
    public DetailedEventAdapter(Activity context, List<EventModel> alumniList, NestedFragmentListener listener) {
        super(context, R.layout.event_list,alumniList);
        this.context = context;
        this.data = alumniList;
        this.listener=listener;
    }


    @NonNull
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {


        LayoutInflater inflater = context.getLayoutInflater();

        View listItemView = inflater.inflate(R.layout.activity_detailed_event,null,true);
        TextView name = (TextView) listItemView.findViewById(R.id.et_name);
        name.setText(data.get(position).title);
        TextView body = (TextView)listItemView.findViewById(R.id.et_body);
        body.setText(data.get(position).body);
        image1 = listItemView.findViewById(R.id.image1);
        Glide.with(context).load(data.get(position).image1).into(image1);
        image2 = listItemView.findViewById(R.id.image2);
        Glide.with(context).load(data.get(position).image2).into(image2);
        image3 = listItemView.findViewById(R.id.image3);
        Glide.with(context).load(data.get(position).image3).into(image3);
        image4 = listItemView.findViewById(R.id.image4);
        Glide.with(context).load(data.get(position).image4).into(image4);





        return listItemView;
    }
}


