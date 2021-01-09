package com.imbuegen.alumniapp.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.imbuegen.alumniapp.Adapters.QuestionsAdapter;

import com.imbuegen.alumniapp.Models.AlumniModel;
import com.imbuegen.alumniapp.Models.QuestionsModel;
import com.imbuegen.alumniapp.NestedFragmentListener;
import com.squareup.picasso.Picasso;

import java.util.Iterator;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;import com.imbuegen.alumniapp.R;

import static android.content.Context.MODE_PRIVATE;


public class AlumniInfoFragment extends Fragment {
    public static final String ALUMNI_OBJ = "qwe";
    TextView nameTextView;
    TextView dateJoinTextView;
    TextView companyTextView;
   ImageView profileImage;
    FloatingTextButton askQuestionButton;
    ListView questionsListView;
    QuestionsAdapter adapter;
    AlumniModel mAlumni;
    Gson gson=new Gson();
    private StorageReference mStorage;
SharedPreferences prefs;
SharedPreferences.Editor editor;
    NestedFragmentListener listener;
    public void backPressed() {
        editor=getContext().getSharedPreferences("SwitchTo", Context.MODE_PRIVATE).edit();
        editor.putString("goto","Alumni");
        editor.commit();

        listener.onSwitchToNextFragment();
    }
    public AlumniInfoFragment()
    {}
    @SuppressLint("ValidFragment")
    public AlumniInfoFragment(NestedFragmentListener listener)
    {
        this.listener=listener;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTheme(R.style.mNoActionBar);

        View profileLayout    = inflater.inflate(R.layout.profile_view, null);
        View v = inflater.inflate(R.layout.activity_alumni_info, null);

        nameTextView = profileLayout.findViewById(R.id.alumniName_tv);
        companyTextView =  profileLayout.findViewById(R.id.companyName_tv);
        questionsListView = v.findViewById(R.id.list);
        askQuestionButton = v.findViewById(R.id.ask_qustion_bt);

       profileImage = profileLayout.findViewById(R.id.profile_image);


        prefs= getActivity().getSharedPreferences("Alumniinfo",MODE_PRIVATE);

        mAlumni= gson.fromJson(prefs.getString("ALUMNI_OBJ","xyz"),AlumniModel.class);
        nameTextView.setText(mAlumni.getAlumniName());
        companyTextView.setText(mAlumni.getCompany());

        adapter = new QuestionsAdapter(mAlumni.getQuestionsList(),getActivity());

        questionsListView.setAdapter(adapter);

        questionsListView.addHeaderView(profileLayout);




        removeQuestions();


        askQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });


        mStorage = FirebaseStorage.getInstance().getReference();
        getProfileImage();
return v;
    }

    private void showDialog() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity(),R.style.MyDialogTheme);
        final EditText edittext = new EditText(getActivity());
      //  alert.setMessage("Ask Here");
        alert.setTitle(Html.fromHtml("<font color='#FFFFFF'>Ask here</font>"));
        alert.setView(edittext);
        edittext.setTextColor(Color.parseColor("#ffffff"));
        edittext.setGravity(Gravity.CENTER);
        edittext.setMaxWidth(194);

        alert.setPositiveButton(Html.fromHtml("<font color='#FFFFFF'>Send</font>"), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                String question = edittext.getText().toString();
                Toast.makeText(getActivity(), "Question Asked", Toast.LENGTH_SHORT).show();
//
//                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(mAlumni.getDatabaseReferencePath());
//
//                databaseReference.child("questions").push().setValue(new QuestionsModel(question,""));
//              databaseReference.keepSynced(true);


            }
        });
        alert.setNegativeButton(Html.fromHtml("<font color='#FFFFFF'>Cancel</font>"), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
        Button positiveButton = alertDialog.getButton(Dialog.BUTTON_POSITIVE);
        Button negativeButton = alertDialog.getButton(Dialog.BUTTON_NEGATIVE);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
        layoutParams.weight = 10;
        FrameLayout.LayoutParams editlayoutParams = (FrameLayout.LayoutParams)edittext.getLayoutParams();
        editlayoutParams.setMargins(15,15,15,0);
        edittext.setLayoutParams(editlayoutParams);
        ((Button)alertDialog.findViewById(android.R.id.button1)).setBackgroundResource(R.drawable.qs_button_shape);
        ((Button)alertDialog.findViewById(android.R.id.button2)).setBackgroundResource(R.drawable.qs_button_shape);
        layoutParams.setMargins(15,0,15,0);
        positiveButton.setLayoutParams(layoutParams);
       negativeButton.setLayoutParams(layoutParams);
    //   alert.create();
//        alert.show();
    }

    private void removeQuestions() {
        Iterator<QuestionsModel> iter = mAlumni.getQuestionsList().iterator();
        while (iter.hasNext()) {
           QuestionsModel questionsModel = iter.next();
            if(questionsModel.getAnswer().trim().isEmpty()) {
                iter.remove();
            }

        }
    }


    public void getProfileImage() {

        Log.d("TAG", "getProfileImage: "+mAlumni.getUid());
        StorageReference profileReference = mStorage.child("Profile photos").child(mAlumni.getUid());
        if (profileReference!=null)  {
            profileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri)
                {
                    Picasso.get().load(uri).fit().centerCrop().into(profileImage);
                }
            });
        }
    }
}
