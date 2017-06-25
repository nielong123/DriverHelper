package com.driverhelper.ui.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.driverhelper.R;
import com.driverhelper.config.Config;
import com.driverhelper.ui.fragment.fragments.Coach_AppointmenOrdertFragment;
import com.driverhelper.ui.fragment.fragments.Coach_TeachHistoryFragment;
import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.baserx.RxBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Coach_Info_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Coach_Info_Fragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.teach_code)
    TextView teachCode;
    @Bind(R.id.teach_type)
    TextView teachType;
    @Bind(R.id.school)
    TextView school;
    @Bind(R.id.coach_sign_out)
    Button coachSignOut;
    @Bind(R.id.student_sign)
    Button studentSign;
    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<Fragment> mFragments = new ArrayList<>();
    PageViewAdapter pageViewAdapter;


    public Coach_Info_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Coach_Info_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Coach_Info_Fragment newInstance(String param1, String param2) {
        Coach_Info_Fragment fragment = new Coach_Info_Fragment();
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
        return R.layout.fragment_coach__info_;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        Coach_TeachHistoryFragment coach_TeachHistoryFragment = Coach_TeachHistoryFragment.newInstance("", "");
        Coach_AppointmenOrdertFragment coach_AppointmenOrdertFragment = Coach_AppointmenOrdertFragment.newInstance("", "");
        mFragments.add(coach_TeachHistoryFragment);
        mFragments.add(coach_AppointmenOrdertFragment);


        pageViewAdapter = new PageViewAdapter(this.getChildFragmentManager());
        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        viewpager.setAdapter(pageViewAdapter);
        viewpager.setCurrentItem(0);
        tablayout.setupWithViewPager(viewpager);
    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.coach_sign_out, R.id.student_sign})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.coach_sign_out:
                RxBus.getInstance().post(Config.Config_RxBus.RX_COACH_SIGN_OUT, "");
                break;
            case R.id.student_sign:
                RxBus.getInstance().post(Config.Config_RxBus.RX_STUDENT_SIGN, "");
                break;
        }
    }


    private class PageViewAdapter extends FragmentPagerAdapter {

        private String[] mTitles = {"带教记录", "预约订单"};

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
