package com.djacm.alumniapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.djacm.alumniapp.Adapters.AluminiViewPagerAdaptor;
import com.djacm.alumniapp.R;
public class RegistrationActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mAlumniReference;
    private static final String TAG = "XYZ";

    BottomNavigationView navigation ;
    MenuItem prevMenuItem;
    private TextView uidTv;
    SharedPreferences.Editor editor;
    AluminiViewPagerAdaptor adapter;
    String alumniPath;
    String dbPath;
    String uid;
    String xyz = "";
    ViewPager viewPager;

    public String getDbPath() {
        return dbPath;
    }

    public void setDbPath(String dbPath) {
        this.dbPath = dbPath;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base_menu,menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registeration);
         navigation = (BottomNavigationView) findViewById(R.id.navigation);

        viewPager=findViewById(R.id.aluminiviewpager);
        setupViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float v, int i1) {
            }


            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null)
                    prevMenuItem.setChecked(false);
                else
                    navigation.getMenu().getItem(0).setChecked(false);

                navigation.getMenu().getItem(position).setChecked(true);
                prevMenuItem = navigation.getMenu().getItem(position);
                setTitle(navigation.getMenu().getItem(position).getTitle());


            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        init();

        final String uid = mAuth.getUid();
        DatabaseReference userIndexRef = mDatabase.getReference("userIndex").child(uid);
        userIndexRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                xyz = alumniPath = (String) dataSnapshot.getValue();
//                Bundle bundle = new Bundle();
//                bundle.putString("path ", xyz);
//                bundle.putString("uid",uid);
//                Fragment fragment = null;
//
//                fragment=adapter.getItem(0);
//                fragment.setArguments(bundle);

               // loadFragment(fragment);
                editor=getSharedPreferences("fragmentargs",MODE_PRIVATE).edit();
                editor.clear();
                editor.putString("path",uid);
                editor.putString("uid",uid);
                editor.apply();
                viewPager.setCurrentItem(0);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        Bundle bundle = new Bundle();
//        bundle.putString("path", xyz);
//        bundle.putString("uid",uid);

//        Fragment profileFragment = adapter.getItem(0);
//        profileFragment.setArguments(bundle);
//        loadFragment(profileFragment);
        editor=getSharedPreferences("fragmentargs",MODE_PRIVATE).edit();
        editor.clear();
        editor.putString("path",uid);
        editor.putString("uid",uid);
        editor.apply();
        viewPager.setCurrentItem(0);

        getTheAlumni();


    }
    private void setupViewPager(ViewPager viewPager)
    {
             adapter = new AluminiViewPagerAdaptor(getSupportFragmentManager());
             viewPager.setAdapter(adapter);
    }

    private void getTheAlumni() {
        final String uid = mAuth.getUid();

        DatabaseReference userIndexRef = mDatabase.getReference("userIndex").child(uid);
        userIndexRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               xyz = alumniPath = (String) dataSnapshot.getValue();


                 navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                     @Override
                     public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                         Fragment fragment = null;
                         if (menuItem.getItemId() == R.id.navigation_profile)
                         {


//                             Bundle bundle = new Bundle();
//                             bundle.putString("path", xyz);
//                             bundle.putString("uid",uid);
//
//
//                             fragment =adapter.getItem(0);
//                             fragment.setArguments(bundle);
                             editor=getSharedPreferences("fragmentargs",MODE_PRIVATE).edit();
                             editor.clear();
                             editor.putString("path",uid);
                             editor.putString("uid",uid);
                             editor.apply();
                                viewPager.setCurrentItem(0);



                         }

                         if (menuItem.getItemId() == R.id.navigation_answer) {

//                             Bundle bundle = new Bundle();
//                             bundle.putString("path", xyz);
//                             bundle.putString("uid", uid);
//                             fragment = adapter.getItem(1);
//                             fragment.setArguments(bundle);
                             editor=getSharedPreferences("fragmentargs",MODE_PRIVATE).edit();
                             editor.clear();
                             editor.putString("path",uid);
                             editor.putString("uid",uid);
                             editor.apply();
                             viewPager.setCurrentItem(1);

                         }
                         return false;//loadFragment(fragment);
                     }
                 });



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init()
    {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
    }



//    private boolean loadFragment(Fragment fragment)
//    {
//        if (fragment != null)
//        {getSupportFragmentManager().beginTransaction().add(R.id.aluminiviewpager,fragment).commit();
//        return true;
//        }
//        return false;
//    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
        return true;
    }
}
