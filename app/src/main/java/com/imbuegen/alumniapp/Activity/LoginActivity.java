package com.imbuegen.alumniapp.Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
//import com.imbuegen.alumniapp.Service.SFHandler;

import java.util.Arrays;
import java.util.List;import com.imbuegen.alumniapp.R;
import com.squareup.picasso.Picasso;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static Button sgnup;
    private static Button b1;
    private static Button b2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        sgnup=findViewById(R.id.signupbtn);
        b1=findViewById(R.id.studentLogin);
        b2=findViewById(R.id.AL);
        sgnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,Register.class);
                startActivity(intent);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,StudentLog.class);
                startActivity(intent);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,AlumniLoginpg.class);
                startActivity(intent);
            }
        });

        ImageView img=(ImageView)findViewById(R.id.logo);
        Drawable myDrawable = getResources().getDrawable(R.drawable.ic_acm);
        img.setImageDrawable(myDrawable);

    }

//<<<<<<< HEAD
}
//=======
//
//    public void signInStudent(View v) {
//                List<AuthUI.IdpConfig> providers = Arrays.asList(
//                        new AuthUI.IdpConfig.PhoneBuilder().build(),
//                        new AuthUI.IdpConfig.GoogleBuilder().build());
//                startActivityForResult(
//                        AuthUI.getInstance()
//                                .createSignInIntentBuilder()
//                                .setTheme(R.style.CustomTheme)
//                                .setAvailableProviders(providers)
//                                .build(),
//                        RC_SIGN_IN);
//    }
//    public void signInAlumni(View v) {
//        startActivity(new Intent(this,AlumniLoginActivity.class));
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == RC_SIGN_IN) {
//            IdpResponse response = IdpResponse.fromResultIntent(data);
//            if (resultCode == RESULT_OK) {
//                // Successful sign in
//                setContentView(R.layout.loading_screen);
//                SFHandler.setString(getSharedPreferences("Auth",MODE_PRIVATE),SFHandler.USER_KEY,"Student");
//                goToStudentScreen();
//            } else {
//                Toast.makeText(this, "Student Sign in Uncessfull", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    private void goToStudentScreen() {
//        Intent i = new Intent(this,BaseActivity.class);
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(i);
//        finish();
//    }
//}
//>>>>>>> 7f314d941a6338c5387ec60e3e78c7ade6107213
