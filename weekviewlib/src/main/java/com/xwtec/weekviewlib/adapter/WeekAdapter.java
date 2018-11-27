package com.xwtec.weekviewlib.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @Author ayy
 * @Date 2018/11/26.
 * Describe:xxx
 */

public class WeekAdapter extends FragmentPagerAdapter {
    private List<? extends Fragment> fragmentList;

    public WeekAdapter(FragmentManager fm, List<? extends Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
