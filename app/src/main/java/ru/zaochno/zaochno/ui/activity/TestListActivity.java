package ru.zaochno.zaochno.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.ui.adapter.pager.TestListPagerAdapter;
import ru.zaochno.zaochno.ui.fragment.TestListActiveFragment;
import ru.zaochno.zaochno.ui.fragment.TestListDoneFragment;

public class TestListActivity extends BaseNavDrawerActivity {
    private static final String TAG = "TestListActivity";

    @BindView(R.id.tabs)
    public TabLayout tabLayout;

    @BindView(R.id.view_pager)
    public ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);
        ButterKnife.bind(this);

        setupDrawer();
        setupUi();
    }

    private void setupUi() {
        setupTabs();
    }

    private void setupTabs() {
        TestListPagerAdapter adapter = new TestListPagerAdapter(getSupportFragmentManager());
        adapter
                .addFragment(new TestListActiveFragment(), "Текущие тесты")
                .addFragment(new TestListDoneFragment(), "Пройденые тесты");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}