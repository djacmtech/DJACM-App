
package com.djacm.alumniapp.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.djacm.alumniapp.R;

public class ResetPassword extends AppCompatActivity {
    EditText reset_email_edit;
    Button reset_pass_button;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        reset_email_edit =  findViewById(R.id.reset_email_edit);
        reset_pass_button = findViewById(R.id.reset_pass_button);
        progressBar = findViewById(R.id.progress_bar);
        mAuth = FirebaseAuth.getInstance();
        reset_pass_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }
    private void resetPassword(){
        String email = reset_email_edit.getText().toString().trim();
        if(email.isEmpty()){
            reset_email_edit.setError("Email is required");
            reset_email_edit.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            reset_email_edit.setError("Provide valid email address");
            reset_email_edit.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ResetPassword.this,"Check your email to reset password",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ResetPassword.this,MainActivity.class));
                }
                else{
                    Toast.makeText(ResetPassword.this,"Try again! Something  wrong happened",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}