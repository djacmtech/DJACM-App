package com.djacm.alumniapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.djacm.alumniapp.Models.QuestionsModel;
import com.djacm.alumniapp.R;


import java.util.ArrayList;

public class QuestionsAdapter  extends ArrayAdapter<QuestionsModel> implements View.OnClickListener{

    private ArrayList<QuestionsModel> dataSet;
    Context mContext;



    @Override
    public void onClick(View v) {}


    public QuestionsAdapter(ArrayList<QuestionsModel> data, Context context) {
        super(context, R.layout.questions_info_item, data);
        this.dataSet = data;
        this.mContext=context;

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        QuestionsModel dataModel = getItem(position);



        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.questions_info_item, null, false);
        TextView questionTv = (TextView) convertView.findViewById(R.id.questions_tv);
        TextView answerTV = (TextView) convertView.findViewById(R.id.answer_tv);
        questionTv.setText(dataModel.getQuestions());
        answerTV.setText(dataModel.getAnswer());
        return convertView;
    }
}
