package com.djacm.alumniapp.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.djacm.alumniapp.IF_Fragment;
import com.djacm.alumniapp.Models.InternshipCompanyModel;
import com.google.firebase.auth.FirebaseAuth;
import com.djacm.alumniapp.Adapters.StudentViewPagerAdaptor;
import com.djacm.alumniapp.HomeFragment;
import com.djacm.alumniapp.Models.CommitteeMember;
import com.djacm.alumniapp.R;
import com.djacm.alumniapp.Screen2Fragment;

import java.util.ArrayList;
import java.util.HashMap;

public class BaseActivity extends AppCompatActivity {
    BottomNavigationView navigation;
    public static int itemID;
    ViewPager viewPager;
    Activity activity;
    MenuItem prevMenuItem;
    StudentViewPagerAdaptor adapter;
    public InternshipCompanyModel selectedIFCompany;

    public static HashMap<Integer, ArrayList<CommitteeMember>> committePhotoCache = new HashMap<Integer, ArrayList<CommitteeMember>>(); //A cache for storing the yearwise members of the committee
    public static ArrayList<CommitteeMember> facultyCache = new ArrayList<CommitteeMember>(); //Cache for storing the members of the faculty

    public static ArrayList<InternshipCompanyModel> techCompanies = new ArrayList<>(); //The IF tech companies
    public static ArrayList<InternshipCompanyModel> nonTechCompanies = new ArrayList<>(); //The IF non-tech companies
    public static int currIFCompanyPos = 0;

    public void setActivity(Activity a) {
        activity = a;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemID = R.id.navigation_home;
        navigation = findViewById(R.id.navigation);
        //setUpNavigation();
        viewPager = findViewById(R.id.studentviewpager);
        setupViewPager(viewPager);

        getSupportFragmentManager().beginTransaction().add(R.id.studentviewpager, new DepartmentsFragment()).commit();
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
                String title = navigation.getMenu().getItem(position).getTitle().toString();
                getSupportActionBar().setTitle(title);
                Log.e("Title", title);

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        setUpNavigation();
    }

    @Override
    public void onStart()
    {
        super.onStart();

        /*//Checking if no user is signed in
        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                if(firebaseAuth.getCurrentUser() == null)
                {
                    //Switching to the login page
                    Intent intent = new Intent(BaseActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });*/
    }

    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 3) {
            if (adapter.getItem(3) instanceof CompanyFragment) {
                ((CompanyFragment) adapter.getItem(3)).backPressed();

            } else if (adapter.getItem(3) instanceof AlumniFragment) {
                ((AlumniFragment) adapter.getItem(3)).backPressed();

            } else if (adapter.getItem(3) instanceof AlumniInfoFragment) {
                ((AlumniInfoFragment) adapter.getItem(3)).backPressed();

            } else if (adapter.getItem(3) instanceof DepartmentsFragment) {
                viewPager.setCurrentItem(0);

            }
        } else if (viewPager.getCurrentItem() == 1) {
            if (adapter.getItem(1) instanceof DetailedEventFragment) {
                ((DetailedEventFragment) adapter.getItem(1)).backPressed();

            } else if (adapter.getItem(1) instanceof EventsFragment) {
                viewPager.setCurrentItem(0);

            }
        } else if (viewPager.getCurrentItem() == 4) {
            if (adapter.getItem(4) instanceof ApplicationForm) {
                ((ApplicationForm) adapter.getItem(4)).backPressed();

            } else if (adapter.getItem(4) instanceof IF_Fragment) {
                viewPager.setCurrentItem(0);

            } else if (adapter.getItem(4) instanceof InternshipCompany) {
                ((InternshipCompany) adapter.getItem(4)).backPressed();

            } else if (adapter.getItem(4) instanceof InternshipDetails) {
                ((InternshipDetails) adapter.getItem(4)).backPressed();

            }
        }
        else if(viewPager.getCurrentItem() == 2)
        {
            viewPager.setCurrentItem(0);
        }
        else if (viewPager.getCurrentItem() == 0)
        {
            finishAffinity();
        }
    }

    //    private boolean loadFragment(Fragment fragment)
//    {
//        if (fragment != null)
//        {getSupportFragmentManager().beginTransaction().add(R.id.studentviewpager,fragment).commit();
//            return true;
//        }
//        return false;
//    }
    private void setupViewPager(ViewPager viewPager) {
        adapter = new StudentViewPagerAdaptor(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
    }

    void setUpNavigation() {
        if (navigation == null) {
            //Toast.makeText(activity, "NULL", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "setUpNavigation: NULL");
            return;
        }

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                
                Fragment fragment = null;
                if (menuItem.getItemId() == R.id.navigation_home)
                {
                    viewPager.setCurrentItem(0);
                    //fragment =adapter.getItem(0);
                }
                if (menuItem.getItemId() == R.id.navigation_events) {
                    //fragment = adapter.getItem(1);
                    viewPager.setCurrentItem(1);
                }
                if (menuItem.getItemId() == R.id.navigation_committee) {
                    //fragment = adapter.getItem(2);
                    viewPager.setCurrentItem(2);
                }

                if (menuItem.getItemId() == R.id.navigation_alumni) {

                    viewPager.setCurrentItem(3);
                }
                if(menuItem.getItemId()==R.id.if_fragment){
                    viewPager.setCurrentItem(4);
                }
                return false;            //loadFragment(fragment);
            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {

            //Displaying confirmation dialog
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.NewThemeDialog);
            dialogBuilder.setTitle("Log Out");
            dialogBuilder.setMessage("Are you sure you want to log out ?");
            dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    //Signing out
                    FirebaseAuth.getInstance().signOut();

                    Intent intent = new Intent(BaseActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                    finish();
                }
            });
            dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss(); //Closing the dialog box
                }
            });
            dialogBuilder.show();

        }
        return true;
    }

    private void setUpNotificationChannel()
    {
        /*Sets up notification channel and handles topic

        //Checking if android version is Oreo or above
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            //Creating the notifications channel
            NotificationChannel notifChannel = new NotificationChannel(NOTIF_CHANNEL_NAME, NOTIF_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notifManager = getSystemService(NotificationManager.class);
            notifManager.createNotificationChannel(notifChannel);
        }*/
    }
}
