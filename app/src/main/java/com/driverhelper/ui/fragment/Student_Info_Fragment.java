package com.driverhelper.ui.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.TextView;

import com.driverhelper.R;
import com.driverhelper.config.Config;
import com.driverhelper.ui.fragment.fragments.Student_DriveHistoryFragment;
import com.driverhelper.ui.fragment.fragments.Student_TimedTrainingFragment;
import com.driverhelper.ui.fragment.fragments.Student_TrainingHistoryFragment;
import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.baserx.RxBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Student_Info_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Student_Info_Fragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.sex)
    TextView sex;
    @Bind(R.id.school)
    TextView school;
    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.student_sign_out)
    Button studentSignOut;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<Fragment> mFragments = new ArrayList<>();
    PageViewAdapter pageViewAdapter;

    public Student_Info_Fragment() {
        // Required empty public constructor
    }

    /**
     *
     */
    // TODO: Rename and change types and number of parameters
    public static Student_Info_Fragment newInstance(String param1, String param2) {
        Student_Info_Fragment fragment = new Student_Info_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_student__info_;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        Student_TimedTrainingFragment timedTrainingFragment = Student_TimedTrainingFragment.newInstance("", "");
        Student_TrainingHistoryFragment trainingHistoryFragment = Student_TrainingHistoryFragment.newInstance("", "");
        Student_DriveHistoryFragment driveHistoryFragment = Student_DriveHistoryFragment.newInstance("", "");
        mFragments.add(timedTrainingFragment);
        mFragments.add(trainingHistoryFragment);
        mFragments.add(driveHistoryFragment);


        pageViewAdapter = new PageViewAdapter(this.getChildFragmentManager());
        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        viewpager.setAdapter(pageViewAdapter);
        viewpager.setCurrentItem(0);
        tablayout.setupWithViewPager(viewpager);
    }

    @Override
    protected void initEvent() {

    }

    @OnClick(R.id.student_sign_out)
    public void onClick() {
        RxBus.getInstance().post(Config.Config_RxBus.RX_STUDENT_SIGN_OUT, "");
    }


    private class PageViewAdapter extends FragmentPagerAdapter {

        private String[] mTitles = {"计时培训", "培训记录", "行车记录"};

        public PageViewAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }
}
