package com.djacm.alumniapp.Adapters;
import android.app.Activity;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.google.gson.Gson;
import com.djacm.alumniapp.Models.AlumniModel;
import com.djacm.alumniapp.NestedFragmentListener;
import com.djacm.alumniapp.R;
import java.util.List;
import static android.content.Context.MODE_PRIVATE;
public class AlumniListAdapter extends ArrayAdapter<AlumniModel> {
    private Activity context;
    private List<AlumniModel> alumniList;
    SharedPreferences.Editor editor;
    NestedFragmentListener listener;
    Gson gson=new Gson();
    public AlumniListAdapter(Activity context, List<AlumniModel> alumniList,NestedFragmentListener listener)
    {
        super(context,R.layout.alumni_list_item,alumniList);
        this.context = context;
        this.alumniList = alumniList;
        this.listener=listener;
    }


    @NonNull
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {


        LayoutInflater inflater = context.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.alumni_list_item,null,true);
        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor=context.getSharedPreferences("SwitchTo", MODE_PRIVATE).edit();
                editor.putString("goto","AlumniInfo");
                editor.commit();
                editor=  context.getSharedPreferences("Alumniinfo",MODE_PRIVATE).edit();
                editor.putString("ALUMNI_OBJ",gson.toJson(alumniList.get(position)));
                editor.commit();
                listener.onSwitchToNextFragment();

                //Intent i = new Intent(parent.getContext(),AlumniInfoActivity.class);
               // i.putExtra(AlumniInfoActivity.ALUMNI_OBJ,alumniList.get(position));

                //context.startActivity(i);

            }
        });
        TextView alumniNameTextView = (TextView) listItemView.findViewById(R.id.text_view_alumni_name);
        AlumniModel company = alumniList.get(position);
        alumniNameTextView.setText(company.getAlumniName());

        return listItemView;
    }
}
