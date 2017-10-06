package ru.zaochno.zaochno.ui.adapter.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.LinkedList;
import java.util.List;

public class TestListPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments = new LinkedList<>();
    private List<String> titles = new LinkedList<>();

    public TestListPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public TestListPagerAdapter addFragment(Fragment fragment, String title) {
        fragments.add(fragment);
        titles.add(title);
        return this;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}