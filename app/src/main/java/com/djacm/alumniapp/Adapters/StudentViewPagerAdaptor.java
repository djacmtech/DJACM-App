package com.djacm.alumniapp.Adapters;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.djacm.alumniapp.Activity.AlumniFragment;
import com.djacm.alumniapp.Activity.AlumniInfoFragment;
import com.djacm.alumniapp.Activity.ApplicationForm;
import com.djacm.alumniapp.Activity.CommiteeFragment;
import com.djacm.alumniapp.Activity.CompanyFragment;
import com.djacm.alumniapp.Activity.DepartmentsFragment;
import com.djacm.alumniapp.Activity.DetailedEventFragment;
import com.djacm.alumniapp.Activity.EventsFragment;
import com.djacm.alumniapp.Activity.InternshipCompany;
import com.djacm.alumniapp.Activity.InternshipDetails;
import com.djacm.alumniapp.HomeFragment;
import com.djacm.alumniapp.NestedFragmentListener;
import com.djacm.alumniapp.Screen2Fragment;

import static android.content.Context.MODE_PRIVATE;

public class StudentViewPagerAdaptor extends FragmentStatePagerAdapter {
    FragmentManager fm;
    Context context;
    Fragment Department,EventFrag,IF;
    FragmentListener listener = new FragmentListener();
    SharedPreferences switchTo;
    SharedPreferences.Editor editor;
    private final class FragmentListener implements NestedFragmentListener
    {
        @SuppressLint("ResourceType")
        public void onSwitchToNextFragment()
        {
            switchTo=context.getSharedPreferences("SwitchTo",MODE_PRIVATE);
            editor=context.getSharedPreferences("SwitchTo",MODE_PRIVATE).edit();
            switch (switchTo.getString("goto","dept"))
            {
                case "Comp":
                    fm.beginTransaction().remove(Department)
                            .commit();
                    editor.clear().commit();
                    Department = new CompanyFragment(listener);
                    break;
                case "Dept":
                    fm.beginTransaction().remove(Department)
                            .commit();
                    editor.clear().commit();
                    Department = new DepartmentsFragment(listener);
                    break;
                case "Alumni":
                    fm.beginTransaction().remove(Department)
                            .commit();
                    editor.clear().commit();
                        Department = new AlumniFragment(listener);
                  //      fm.beginTransaction().replace(1,Department).commit();
                        break;
                case "AlumniInfo":
                    fm.beginTransaction().remove(Department)
                            .commit();
                    editor.clear().commit();
                    Department = new AlumniInfoFragment(listener);

                    break;
                case "DetEvent":editor.clear().commit();
                    fm.beginTransaction().remove(EventFrag)
                            .commit();
                    EventFrag = new DetailedEventFragment(listener);
                    break;
                case "Event":editor.clear().commit();
                    fm.beginTransaction().remove(EventFrag)
                            .commit();
                    EventFrag = new EventsFragment(listener);
                    break;
                case "screen1":editor.clear().commit();
                    fm.beginTransaction().remove(IF)
                            .commit();
                    IF = new ApplicationForm(listener);
                    break;
                case "screen2":editor.clear().commit();
                    fm.beginTransaction().remove(IF)
                            .commit();
                    IF = new Screen2Fragment(listener);
                    break;
                case "IF":editor.clear().commit();
                    fm.beginTransaction().remove(IF)
                            .commit();
                    IF = new HomeFragment(listener);
                    break;
                case "IfComp":editor.clear().commit();
                    fm.beginTransaction().remove(IF)
                            .commit();
                    IF = new InternshipCompany(listener);
                    break;
                case "IntDet":editor.clear().commit();
                    fm.beginTransaction().remove(IF)
                            .commit();
                    IF = new InternshipDetails(listener);
                    break;
            }

            notifyDataSetChanged();
        }
    }


    public StudentViewPagerAdaptor(FragmentManager fm, Context context) {
        super(fm);
        this.fm=fm;
        this.context=context;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case 3:
                if(Department==null)
                Department= new DepartmentsFragment(listener);
                return Department;
            case 1:
                if(EventFrag==null)
                 EventFrag= new EventsFragment(listener);
                 return EventFrag;
            case 2:
                return new CommiteeFragment();
            case 0:
                if(IF==null)
                    IF= new HomeFragment(listener);
                return IF;
            default:return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
    @Override
    public int getItemPosition(Object object)
    {
//        if (object instanceof DepartmentsFragment &&
//                Department instanceof CompanyFragment) {
//            return POSITION_NONE;
//        }
//        else if (object instanceof CompanyFragment &&
//                Department instanceof DepartmentsFragment) {
//            return POSITION_NONE;
//        }
//        else if (object instanceof DetailedEventFragment &&
//                EventFrag instanceof EventsFragment) {
//            return POSITION_NONE;
//        }
//        else if (object instanceof EventsFragment &&
//                EventFrag instanceof DetailedEventFragment) {
//            return POSITION_NONE;
//        }
        return POSITION_NONE;
    }
}
