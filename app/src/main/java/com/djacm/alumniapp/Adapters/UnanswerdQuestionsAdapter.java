package com.djacm.alumniapp.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.djacm.alumniapp.Models.QuestionsModel;
import com.djacm.alumniapp.R;

import java.util.ArrayList;
import java.util.List;

public class UnanswerdQuestionsAdapter extends RecyclerView.Adapter<UnanswerdQuestionsAdapter.MyViewHolder> {

    Context context;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference deptRef;
    String path;


    //Dept Name
    String str1;

    //Company name
    String str2;

    //Alumni Name
    String str3;


    String alumniParKey = "";
    String questionParKey = "";


    private List<QuestionsModel> unanswerdQuestionsModelList;
    private List<QuestionsModel> answeredList = new ArrayList<>();


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, ansTextView, year, genre;
        public ImageView arrowImage;
        public Button saveItemButton;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            arrowImage = view.findViewById(R.id.arrowImage);
            ansTextView = view.findViewById(R.id.ansTextView);
            saveItemButton = view.findViewById(R.id.saveItemButton);
            //genre = (TextView) view.findViewById(R.id.genre);
            //year = (TextView) view.findViewById(R.id.year);
        }
    }


    public UnanswerdQuestionsAdapter(List<QuestionsModel> moviesList, Context context, String path) {
        this.unanswerdQuestionsModelList = moviesList;
        this.context = context;
        this.path = path;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.unanswered_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        QuestionsModel unanswerdQuestionsModel = unanswerdQuestionsModelList.get(position);
        holder.title.setText(unanswerdQuestionsModel.getQuestions());
        holder.ansTextView.setVisibility(View.GONE);
        holder.saveItemButton.setVisibility(View.GONE);
        /*if ((unanswerdQuestionsModel.getAnswer()).equals(""))
        {
            holder.ansTextView.setVisibility(View.GONE);
        }*/
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context,"Clicked",Toast.LENGTH_SHORT).show();

                displayPrompt(holder.title.getText().toString(), holder.ansTextView, holder.saveItemButton, position);
                //holder.itemView.setVisibility(View.GONE);
            }
        });

        holder.saveItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData(holder.title.getText().toString(),
                        holder.ansTextView.getText().toString(),
                        position);

                removeItem(position);
            }
        });

        //holder.genre.setText(movie.getGenre());
        // holder.year.setText(movie.getYear());
    }

    @Override
    public int getItemCount() {
        return unanswerdQuestionsModelList.size();
    }


    public void displayPrompt(final String question, final TextView textView, final Button saveItemButton, final int pos) {
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.prompts, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text
                                //result.setText(userInput.getText());

                                textView.setVisibility(View.VISIBLE);
                                textView.setText(userInput.getText().toString());
                                saveItemButton.setVisibility(View.VISIBLE);


                                //answeredList.add(new UnanswerdQuestionsModel(question,userInput.getText().toString()));

                                //sendDataToFirebase(question,textView.getText().toString(),pos);
                                //notifyDataSetChanged();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }



    public List<QuestionsModel> getAnsweredList() {
        return answeredList;
    }


    public void removeItem(int position) {
        unanswerdQuestionsModelList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, unanswerdQuestionsModelList.size());
    }


    public void sendData(final String currentQuestion, final String currentAnswer, final int pos) {


        DatabaseReference questionRef = database.getReference(path).child("questions").child(unanswerdQuestionsModelList.get(pos).getQuestionKey()).child("answer");
        questionRef.setValue(currentAnswer);
        notifyDataSetChanged();
    }
}
