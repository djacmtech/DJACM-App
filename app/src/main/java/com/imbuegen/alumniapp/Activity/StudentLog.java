package com.imbuegen.alumniapp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.imbuegen.alumniapp.R;

public class StudentLog extends AppCompatActivity {
    private static Button btn;
    private static EditText mail;
    private static EditText pass;
    private static FirebaseAuth firebaseAuth;
    private static ProgressDialog progressDialog1;
    public static final String SH_PRF="shared_prefs";
    SignInButton google_btn;
    private final static int RC_SIGN_IN=1;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_log);
        google_btn=findViewById(R.id.googleBtn);
        views();
        progressDialog1 = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();

//        mGoogleSignInClient = GoogleSignIn.getClient(this,gso );
//        google_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                signIn();
//            }
//        });


        if(user!=null){
            finish();
            startActivity(new Intent(StudentLog.this,BaseActivity.class));
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mail.getText().toString().isEmpty() || pass.getText().toString().isEmpty()){
                    Toast.makeText(StudentLog.this,"Enter all the details",Toast.LENGTH_SHORT).show();
                }else{
//                    SharedPreferences sharedPreferences=getSharedPreferences(SH_PRF,MODE_PRIVATE);
//                    if(sharedPreferences.getString("Reg_as","ALUMNI")=="STUDENT")
//                    {
                        validate(mail.getText().toString(),pass.getText().toString());
//                    }else{
//
//                        Toast.makeText(StudentLog.this,"NOT REGISTERED AS STUDENT",Toast.LENGTH_SHORT).show();
//                    }




                }
            }
        });

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                Toast.makeText(StudentLog.this,"SIGN IN SUCCESSFUL",Toast.LENGTH_SHORT).show();
//                // ...
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                firebaseAuthWithGoogle(account);
//            } catch (ApiException e) {
//                Toast.makeText(StudentLog.this,"SIGN IN UNSUCCESSFUL",Toast.LENGTH_SHORT).show();
//                // ...
//            }
//        }
//    }
//    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
//        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
//
//        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
//        firebaseAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Toast.makeText(StudentLog.this,"SUCCESSFUL",Toast.LENGTH_SHORT).show();
//                            FirebaseUser user = firebaseAuth.getCurrentUser();
//                            updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Toast.makeText(StudentLog.this,"UNSUCCESSFUL",Toast.LENGTH_SHORT).show();
//                            Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
//
//                        }
//
//                        // ...
//                    }
//                });
//    }

    public void views(){
        btn = (Button)findViewById(R.id.logins);
        mail=findViewById(R.id.etEmails);
        pass = (EditText)findViewById(R.id.passwords);
    }

    private void checkemailverification(){
        FirebaseUser firebaseUser=firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag=((FirebaseUser) firebaseUser).isEmailVerified();
        if(emailflag){
            finish();
            startActivity(new Intent(StudentLog.this, BaseActivity.class));
        }else{
            Toast.makeText(StudentLog.this,"Verify Your Email",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }

    }
    private void validate(String userName,String userPassword){

        progressDialog1.setMessage("Verifying your account");
        progressDialog1.show();

        firebaseAuth.signInWithEmailAndPassword(userName,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog1.dismiss();

                    checkemailverification();

                }else{

                    Toast.makeText(StudentLog.this,"Login Failed",Toast.LENGTH_SHORT).show();
                    progressDialog1.dismiss();

                }


            }
        });

    }
}




