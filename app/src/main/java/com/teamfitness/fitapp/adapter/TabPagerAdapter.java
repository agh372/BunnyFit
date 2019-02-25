package com.teamfitness.fitapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.teamfitness.fitapp.fragments.ARFragment;
import com.teamfitness.fitapp.fragments.HomeFragment;
import com.teamfitness.fitapp.fragments.LevelFragment;
import com.teamfitness.fitapp.fragments.RedeemFragment;

public class TabPagerAdapter extends FragmentPagerAdapter {

    int tabCount;

    public TabPagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.tabCount = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                HomeFragment tab1 = new HomeFragment();
                return tab1;
            case 1:
                LevelFragment tab2 = new LevelFragment();
                return tab2;
            case 2:
                RedeemFragment tab3 = new RedeemFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}