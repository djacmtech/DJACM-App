package com.djacm.alumniapp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.djacm.alumniapp.R;

public class StudentLog extends AppCompatActivity {
    private static Button btn;
    private static EditText mail;
    private static EditText pass;
    private static FirebaseAuth firebaseAuth;
    private static ProgressDialog progressDialog1;
    public static final String SH_PRF="shared_prefs";
  //  SignInButton google_btn;
    private Button google_btn;
    private String buttontext ="Sign in with Google";
    private final static int RC_SIGN_IN=1;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_log);
        google_btn=findViewById(R.id.googleBtn);
        views();
        progressDialog1 = new ProgressDialog(this, R.style.NewThemeDialog);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("814508685135-l1kdpa9dth1dd9k8mdsdsjgspvvi18lg.apps.googleusercontent.com")
                .requestEmail()
                .build();
//        TextView textView = (TextView) google_btn.getChildAt(0);
//        textView.setText(buttontext);
        getSupportActionBar().hide();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso );
        google_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        TextView forgot_password_tv = findViewById(R.id.forgot_password_tv);
        forgot_password_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentLog.this,ResetPassword.class));
            }
        });
        if(user!=null){
            finish();
            switchToHomePage();
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                Toast.makeText(StudentLog.this,"Sign in Successful",Toast.LENGTH_SHORT).show();
                // ...
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
                switchToHomePage();
            } catch (ApiException e) {
                Toast.makeText(StudentLog.this,"SIGN IN UNSUCCESSFUL",Toast.LENGTH_SHORT).show();
                Log.v("Error", String.valueOf(e));
                // ...
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("CHECK ACCOUNT ID", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(StudentLog.this,"SUCCESSFUL",Toast.LENGTH_SHORT).show();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                           // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(StudentLog.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }

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
            Toast.makeText(StudentLog.this,"Sign in Successful",Toast.LENGTH_SHORT).show();
            switchToHomePage();
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

    private void switchToHomePage()
    {
        Intent intent = new Intent(StudentLog.this, BaseActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}




