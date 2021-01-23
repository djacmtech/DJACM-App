package com.djacm.alumniapp.Activity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.djacm.alumniapp.R;

public class AlumniLoginpg extends AppCompatActivity {
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
        setContentView(R.layout.activity_alumni_loginpg);
        views();
        progressDialog1 = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user!=null){
            finish();
            startActivity(new Intent(AlumniLoginpg.this,RegistrationActivity.class));
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mail.getText().toString().isEmpty() || pass.getText().toString().isEmpty()){
                    Toast.makeText(AlumniLoginpg.this,"Enter all the details",Toast.LENGTH_SHORT).show();
                }else{
                    SharedPreferences sharedPreferences=getSharedPreferences(SH_PRF,MODE_PRIVATE);
                    if(sharedPreferences.getString("Reg_as","STUDENT")=="ALUMNI")
                    {
                        validate(mail.getText().toString(),pass.getText().toString());
                    }else{

                        Toast.makeText(AlumniLoginpg.this,"NOT REGISTERED AS ALUMNI",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        TextView forgot_password_tv =findViewById(R.id.forgot_password_tv);
        forgot_password_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AlumniLoginpg.this,ResetPassword.class));
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("814508685135-l1kdpa9dth1dd9k8mdsdsjgspvvi18lg.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso );
        google_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
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
                Toast.makeText(AlumniLoginpg.this,"SIGN IN SUCCESSFUL",Toast.LENGTH_SHORT).show();
                // ...
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
                startActivity(new Intent(AlumniLoginpg.this, BaseActivity.class));
            } catch (ApiException e) {
                Toast.makeText(AlumniLoginpg.this,"SIGN IN UNSUCCESSFUL",Toast.LENGTH_SHORT).show();
                Log.v("Error", String.valueOf(e));
                // ...
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("CHECK", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(AlumniLoginpg.this,"SUCCESSFUL",Toast.LENGTH_SHORT).show();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(AlumniLoginpg.this,"UNSUCCESSFUL",Toast.LENGTH_SHORT).show();
                            //     Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();

                        }

                        // ...
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
            startActivity(new Intent(AlumniLoginpg.this, RegistrationActivity.class));
        }else{
            Toast.makeText(AlumniLoginpg.this,"Verify Your Email",Toast.LENGTH_SHORT).show();
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

                    Toast.makeText(AlumniLoginpg.this,"Login Failed",Toast.LENGTH_SHORT).show();
                    progressDialog1.dismiss();

                }


            }
        });

    }
}
