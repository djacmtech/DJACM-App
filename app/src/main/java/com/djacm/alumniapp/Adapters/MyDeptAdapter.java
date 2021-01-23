package com.djacm.alumniapp.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.djacm.alumniapp.NestedFragmentListener;
import com.djacm.alumniapp.R;
import com.djacm.alumniapp.Models.Department;
import java.util.ArrayList;

public class MyDeptAdapter extends RecyclerView.Adapter<MyDeptAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Department> deptList;
    NestedFragmentListener listener;
    SharedPreferences.Editor editor;
    public MyDeptAdapter(Context context, ArrayList<Department> deptList,NestedFragmentListener listener) {
        this.context = context;
        this.listener=listener;
        this.deptList = deptList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new MyDeptAdapter.ViewHolder(inflater.inflate(R.layout.dept_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        Department dp = deptList.get(i);
        TextView nameView = viewHolder.itemView.findViewById(R.id.txt_name);
        ImageView icnView = viewHolder.itemView.findViewById(R.id.icon_dept);

        nameView.setText(dp.getName());
        icnView.setImageResource(dp.getIconPath());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedDeptName = deptList.get(i).getName();
//                Intent companyActivityIntent = new Intent(context,CompanyActivity.class);
//                companyActivityIntent.putExtra("deptName",selectedDeptName);
//                context.startActivity(companyActivityIntent);
                editor=context.getSharedPreferences("DeptAdaptorToCompanyFrag",Context.MODE_PRIVATE).edit();
                editor.clear();
                editor.putString("deptname",selectedDeptName);
                editor.commit();
                editor=context.getSharedPreferences("SwitchTo",Context.MODE_PRIVATE).edit();
                editor.putString("goto","Comp");
                editor.commit();
                listener.onSwitchToNextFragment();
//                Fragment fg=new CompanyFragment();
//                activity.getSupportFragmentManager().beginTransaction().add(R.id.studentviewpager,fg).commit();
//                Bundle args=new Bundle();
//                args.putString("deptName",selectedDeptName);
//                fg.setArguments(args);

            }
        });

    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return deptList.size();
    }

    class ViewHolder extends  RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
