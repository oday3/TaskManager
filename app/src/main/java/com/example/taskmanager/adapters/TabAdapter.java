package com.example.taskmanager.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.taskmanager.fragments.MainFragment;
import com.example.taskmanager.fragments.TaskFragment;

public class TabAdapter extends FragmentPagerAdapter {


    public TabAdapter(FragmentManager fm) {

        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        if(position == 0){

            return new TaskFragment();

        } else if(position == 1) {

            return new MainFragment();

        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}


