package com.djacm.alumniapp.Adapters;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;


import com.djacm.alumniapp.R;

import java.util.List;

//<<<<<<< HEAD

public class CompanyListAdapter extends ArrayAdapter<CompanyModel> implements Filterable{
//=======
//public class CompanyListAdapter extends ArrayAdapter<CompanyModel> implements Filterable {
//>>>>>>> 7f314d941a6338c5387ec60e3e78c7ade6107213
    private Activity context;
    private List<CompanyModel> companyList;
    private List<CompanyModel> companyListAll;

    public CompanyListAdapter(Activity context, List<CompanyModel> companyList) {
        super(context.getApplicationContext(), R.layout.company_list_item, companyList);
        this.context = context;
        this.companyList = companyList;
        companyListAll=new ArrayList<>(companyList);
    }

    @NonNull
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {


        LayoutInflater inflater = context.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.company_list_item,null,true);

        TextView companyNameTextView = (TextView) listItemView.findViewById(R.id.text_view_company_name);
        CompanyModel company = companyList.get(position);

        companyNameTextView.setText(company.getCompanyName());

        return listItemView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<CompanyModel>filteredList= new ArrayList<>();

            if(charSequence==NULL||charSequence.length()==0){

                filteredList.addAll(companyListAll);
            }else{
                String input=charSequence.toString().toLowerCase().trim();

                for(CompanyModel companyModel: companyListAll){
                    String company=companyModel.getCompanyName();

                    if(company.toLowerCase().contains(input)){
                        filteredList.add(companyModel);
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

        companyList.clear();
        companyList.addAll((List)filterResults.values);
        notifyDataSetChanged();

        }
    };


}

