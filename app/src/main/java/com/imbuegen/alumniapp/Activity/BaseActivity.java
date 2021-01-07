package com.imbuegen.alumniapp.Activity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.imbuegen.alumniapp.Adapters.AluminiViewPagerAdaptor;
import com.imbuegen.alumniapp.Adapters.StudentViewPagerAdaptor;
import com.imbuegen.alumniapp.IfFragment;
import com.imbuegen.alumniapp.Models.CommitteeMember;
import com.imbuegen.alumniapp.R;
import com.imbuegen.alumniapp.Screen1Fragment;
import com.imbuegen.alumniapp.Screen2Fragment;

public class BaseActivity extends AppCompatActivity {
    BottomNavigationView navigation;
    public static int itemID ;
    ViewPager viewPager;
    Activity activity;
    MenuItem prevMenuItem;
    StudentViewPagerAdaptor adapter;
public void setActivity(Activity a) {
        activity =a;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
     getMenuInflater().inflate(R.menu.base_menu,menu);
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemID = R.id.navigation_home;
        navigation = findViewById(R.id.navigation);
        //setUpNavigation();
        viewPager=findViewById(R.id.studentviewpager);
        setupViewPager(viewPager);

        getSupportFragmentManager().beginTransaction().add(R.id.studentviewpager,new DepartmentsFragment()).commit();
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
    setUpNavigation();

    }
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            if (adapter.getItem(0) instanceof CompanyFragment) {
                ((CompanyFragment) adapter.getItem(0)).backPressed();

            } else if (adapter.getItem(0) instanceof AlumniFragment) {
                ((AlumniFragment) adapter.getItem(0)).backPressed();

            } else if (adapter.getItem(0) instanceof AlumniInfoFragment) {
                ((AlumniInfoFragment) adapter.getItem(0)).backPressed();

            } else if (adapter.getItem(0) instanceof DepartmentsFragment) {
                finish();

            }
        } else if (viewPager.getCurrentItem() == 1) {
            if (adapter.getItem(1) instanceof DetailedEventFragment) {
                ((DetailedEventFragment) adapter.getItem(1)).backPressed();

            }
            else if (adapter.getItem(1) instanceof EventsFragment) {
                finish();

            }
        }
        else if (viewPager.getCurrentItem() == 3) {
            if (adapter.getItem(3) instanceof ApplicationForm) {
                ((ApplicationForm) adapter.getItem(3)).backPressed();

            }
           else if (adapter.getItem(3) instanceof Screen2Fragment ){
                ((Screen2Fragment) adapter.getItem(3)).backPressed();

            }
            else if (adapter.getItem(3) instanceof InternshipCompany ){
                ((InternshipCompany) adapter.getItem(3)).backPressed();

            }
            else if (adapter.getItem(3) instanceof InternshipDetails ){
                ((InternshipDetails) adapter.getItem(3)).backPressed();

            }
            else if (adapter.getItem(3) instanceof IfFragment) {
                finish();

            }
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
    private void setupViewPager(ViewPager viewPager)
    {
        adapter = new StudentViewPagerAdaptor(getSupportFragmentManager(),this);
        viewPager.setAdapter(adapter);
    }
    void setUpNavigation() {
        if(navigation == null) {
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
                return false;            //loadFragment(fragment);
            }
        });


    }


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
