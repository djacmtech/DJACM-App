package com.djacm.alumniapp.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.djacm.alumniapp.NestedFragmentListener;
import com.djacm.alumniapp.R;

import java.util.HashSet;
import java.util.Set;

import static android.app.Activity.RESULT_OK;

public class ApplicationForm extends Fragment implements OnItemSelectedListener {

    public String ap_year;
    private static EditText name,sap,email;
    public  static Button send_d,pdf;
    DatabaseReference reference;
    StorageReference storageReference;


    public String ap_name,ap_sap,ap_email,ap_dept;
    public TextView urll;
    public Uri url1,url2;
    public int c=0;
    Set<String> hash_Set = new HashSet<String>();
    boolean flag=false;
    NestedFragmentListener listener;
    SharedPreferences.Editor editor;
    public void backPressed() {
        editor=getContext().getSharedPreferences("SwitchTo", Context.MODE_PRIVATE).edit();
        editor.putString("goto","IF");
        editor.commit();

        listener.onSwitchToNextFragment();
    }
    public ApplicationForm()
    {}
    @SuppressLint("ValidFragment")
    public ApplicationForm(NestedFragmentListener listener){this.listener=listener;}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v=inflater.inflate(R.layout.activity_application_form,null);
        Spinner year=v.findViewById(R.id.Appli_Year);
        Spinner dept=v.findViewById(R.id.departments);


        hash_Set.add("tejasghone73@gmail.com");
        hash_Set.add("rajshah@gmail.com");
        hash_Set.add("Geeks");
        hash_Set.add("Example");
        hash_Set.add("Set");

        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(
                getContext(),
                R.array.Applicant_Dept,
                android.R.layout.simple_spinner_item

        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dept.setAdapter(adapter);
        dept.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ap_dept=adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        year.setOnItemSelectedListener(this);
        name=v.findViewById(R.id.Appli_Name);
        sap=v.findViewById(R.id.Appli_Sap);
        email=v.findViewById(R.id.Appli_Email);
        send_d=v.findViewById(R.id.Appli_Details);
        urll=v.findViewById(R.id.Pdf_url);
        pdf=v.findViewById(R.id.pdf_btn);
        storageReference= FirebaseStorage.getInstance().getReference();

        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPDF();
            }
        });



        send_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            ap_name=name.getText().toString();
            ap_sap=sap.getText().toString();
            ap_email=email.getText().toString();
           uploadPDFfile(url1);



            }
        });
return v;
    }
    private void selectPDF(){
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"SELECT PDF FILE"),1);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
//            uploadPDFfile(data.getData());
            urll.setText(data.getDataString());
            url1=data.getData();
        }
    }

    private void uploadPDFfile(Uri data) {


        final ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading your Application");
        progressDialog.show();
        StorageReference refer=storageReference.child("Applicant_CVs/"+ap_dept+"/"+ap_year+"/"+ap_name);
        refer.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        boolean f=hash_Set.contains(ap_email);
                        if(f)
                        {c=3;}
                        Applicant_Details details=new Applicant_Details(ap_name,ap_email,ap_dept,ap_year,ap_sap,0,c);
                        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        reference= FirebaseDatabase.getInstance().getReference().child("Applications");
                        reference.child(user).setValue(details);

                        Toast.makeText(getActivity(),"Application Uploaded",Toast.LENGTH_SHORT).show();
                        editor=getActivity().getSharedPreferences("SwitchTo", Context.MODE_PRIVATE).edit();
                        editor.putString("goto","IfComp");
                        editor.commit();

                        listener.onSwitchToNextFragment();

                        //backPressed();
                       // startActivity(new Intent(getActivity(),InternshipCompany.class));

                    }


                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.setMessage("Uploading..");


            }
        });


    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ap_year=adapterView.getSelectedItem().toString();


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
