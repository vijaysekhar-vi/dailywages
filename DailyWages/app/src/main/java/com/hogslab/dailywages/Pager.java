package com.hogslab.dailywages;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class Pager extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public Pager(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

/*
    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", patientId);

        switch (position) {
            case 0:
                ViewEmployeeFragment viewPatientFragment = new ViewEmployeeFragment();
                viewPatientFragment.setArguments(bundle);
                return viewPatientFragment;
            case 1:
                ViewAttendanceFragment viewAttendanceFragment = new ViewAttendanceFragment();
                viewAttendanceFragment.setArguments(bundle);
                return viewAttendanceFragment;
            case 2:
                PaymentsFragments paymentsFragments = new PaymentsFragments();
                paymentsFragments.setArguments(bundle);
                return paymentsFragments;
            default:
                return null;
        }
    }
*/

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
