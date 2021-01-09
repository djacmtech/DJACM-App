package com.imbuegen.alumniapp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.imbuegen.alumniapp.R;

public class Register extends AppCompatActivity {

    private static Button create_stud;
  //  private static Button create_alumini;
    private static EditText pas;
    private static EditText email;
    private static ProgressDialog progressDialog;
    private  FirebaseAuth firebaseAuth;
    public static final String SH_PRF="shared_prefs";
    public static final String Reg_as="REG_AS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        diffviews();
        progressDialog = new ProgressDialog(Register.this);
        firebaseAuth=FirebaseAuth.getInstance();
//
//        create_alumini.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (validate()){
//
//
//                    String useremail = email.getText().toString().trim();
//                    String passme = pas.getText().toString().trim();
//                    progressDialog.setMessage("Creating Your Account");
//                    progressDialog.show();
//                    firebaseAuth.createUserWithEmailAndPassword(useremail,passme).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if(task.isSuccessful()){
//                                progressDialog.dismiss();
//                                sendemailverification();
//                                    SharedPreferences sharedPreferences=getSharedPreferences(SH_PRF,MODE_PRIVATE);
//                                    SharedPreferences.Editor editor=sharedPreferences.edit();
//                                    editor.putString(Reg_as,"ALUMNI");
//                                    editor.apply();
//
//                            }
//                            else{
//                                progressDialog.dismiss();
//                                Toast.makeText(Register.this,"Registration Failed",Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                }
//
//            }
//        });
        create_stud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()){


                    String useremail = email.getText().toString().trim();
                    String passme = pas.getText().toString().trim();
                    progressDialog.setMessage("Creating Your Account");
                    progressDialog.show();
                    firebaseAuth.createUserWithEmailAndPassword(useremail,passme).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                sendemailverification();
                                Toast.makeText(Register.this,
                                        "Please check your email for verification",Toast.LENGTH_SHORT).show();
//                                SharedPreferences sharedPreferences=getSharedPreferences(SH_PRF,MODE_PRIVATE);
//                                SharedPreferences.Editor editor=sharedPreferences.edit();
//                                editor.putString(Reg_as,"STUDENT");
//                                editor.apply();
                            }
                            if(!task.isSuccessful()){
                                Log.v("FaiLED",task.getException().toString());
                                progressDialog.dismiss();
                                Toast.makeText(Register.this,"Registration Failed",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }

            }
        });

    }



    public void diffviews(){
        create_stud = (Button)findViewById(R.id.sign_stud);
      //  create_alumini=findViewById(R.id.sign_alu);
        email = (EditText)findViewById(R.id.etEmails);
        pas = (EditText)findViewById(R.id.pass);

    };
    public Boolean validate(){
        Boolean value = false;
        String passme = pas.getText().toString();

        String useremail = email.getText().toString();

        if( passme.isEmpty() || useremail.isEmpty())
        {
            Toast.makeText(this,"Please Enter All The Details",Toast.LENGTH_SHORT).show();


        }else if(passme.length()<=5){
            Toast.makeText(Register.this,"Password should contain atleast 6 characters",Toast.LENGTH_LONG).show();
        }else if(useremail.indexOf("@")<=0 || useremail.charAt(useremail.length()-1)!='m' || useremail.charAt(useremail.length()-2)!='o'|| useremail.charAt(useremail.length()-3)!='c' || useremail.charAt(useremail.length()-4)!='.') {
            Toast.makeText(Register.this,"Invalid Email Id",Toast.LENGTH_LONG).show();

        }
        else{
            value = true;

        }
        return value;
    };
    public void sendemailverification(){

        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Register.this,"Registration Successful: Email verification sent",Toast.LENGTH_LONG).show();
                        finish();
                        firebaseAuth.signOut();
                        startActivity(new Intent(Register.this,MainActivity.class));

                    }else
                        {
                        Toast.makeText(Register.this," Email verification not sent",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
