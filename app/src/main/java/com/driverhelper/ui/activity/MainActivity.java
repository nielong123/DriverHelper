package com.driverhelper.ui.activity;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.driverhelper.R;
import com.driverhelper.config.Config;
import com.driverhelper.helper.WriteSettingHelper;
import com.driverhelper.server.MyService;
import com.driverhelper.ui.fragment.Coach_Info_Fragment;
import com.driverhelper.ui.fragment.Coach_sign_Fragment;
import com.driverhelper.ui.fragment.Coach_sign_out_Fragment;
import com.driverhelper.ui.fragment.MarkFragment;
import com.driverhelper.ui.fragment.Student_Info_Fragment;
import com.driverhelper.ui.fragment.Student_Sign_Fragment;
import com.driverhelper.widget.MyProgressBarView;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.jaydenxiao.common.commonutils.VersionUtil;

import butterknife.Bind;
import rx.functions.Action1;

import static com.driverhelper.config.Config.Config_RxBus.RX_COACH_SIGN_OK;
import static com.driverhelper.config.Config.Config_RxBus.RX_REBACK_MARKACTIVITY;
import static com.driverhelper.config.Config.Config_RxBus.RX_STUDENT_SIGN_OUT;
import static com.driverhelper.config.Config.RxBusIndex.COACH_OK;
import static com.driverhelper.config.Config.RxBusIndex.COACH_SIGN;
import static com.driverhelper.config.Config.RxBusIndex.COACH_SIGN_OUT;
import static com.driverhelper.config.Config.RxBusIndex.SHOW_COACH_INFO;
import static com.driverhelper.config.Config.RxBusIndex.SHOW_MARKACTIVITY;
import static com.driverhelper.config.Config.RxBusIndex.STUDENT_SIGN;
import static com.driverhelper.config.Config.RxBusIndex.STUDENT_SIGN_OK;

public class MainActivity extends BaseActivity {

    int index;
    Coach_sign_Fragment coach_sign_fragment;        //教练签到界面
    Coach_Info_Fragment coach_info_fragment;        //教练信息界面
    MarkFragment markfragment;                  //标志界面
    Student_Sign_Fragment student_sign_fragment;        //学生签到界面
    Coach_sign_out_Fragment coach_sign_out_fragment;        //交流安签退界面
    Student_Info_Fragment student_info_fragment;    //显示学员信息


    @Bind(R.id.FrameLayout)
    android.widget.FrameLayout FrameLayout;
    @Bind(R.id.myprogressbarview)
    MyProgressBarView myprogressbarview;
    @Bind(R.id.vehicle_number)
    TextView vehicleNumber;
    @Bind(R.id.vehicle_state)
    ImageView vehicleState;
    @Bind(R.id.location_state)
    ImageView locationState;
    @Bind(R.id.net_state)
    ImageView netState;
    @Bind(R.id.camera_state)
    ImageView cameraState;
    @Bind(R.id.version_code)
    TextView versionCode;


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        versionCode.setText(VersionUtil.getVersion(this));
        vehicleNumber.setText(WriteSettingHelper.getVEHICLE_NUMBER());
        switchFragment(SHOW_MARKACTIVITY);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        mRxManager.on(Config.Config_RxBus.RX_COACH_SIGN, new Action1<String>() {

            @Override
            public void call(String id) {
                switchFragment(COACH_SIGN);
                ToastUitl.show("教练员签到", Toast.LENGTH_SHORT);
            }
        });
        mRxManager.on(RX_COACH_SIGN_OK, new Action1<String>() {

            @Override
            public void call(String id) {
                switchFragment(SHOW_COACH_INFO);
                ToastUitl.show("显示教练信息", Toast.LENGTH_SHORT);
            }
        });
        mRxManager.on(Config.Config_RxBus.RX_COACH_SIGN_OUT, new Action1<String>() {

            @Override
            public void call(String id) {
                switchFragment(COACH_SIGN_OUT);
                ToastUitl.show("教练签退", Toast.LENGTH_SHORT);
            }
        });
        mRxManager.on(Config.Config_RxBus.RX_COACH_SIGN_OUT_OK, new Action1<String>() {

            @Override
            public void call(String id) {
                switchFragment(SHOW_MARKACTIVITY);
                ToastUitl.show("教练签退ok", Toast.LENGTH_SHORT);
            }
        });
        mRxManager.on(Config.Config_RxBus.RX_STUDENT_SIGN, new Action1<String>() {

            @Override
            public void call(String id) {
                switchFragment(STUDENT_SIGN);
                ToastUitl.show("学员签到", Toast.LENGTH_SHORT);
            }
        });
        mRxManager.on(Config.Config_RxBus.RX_STUDENT_SIGN_OK, new Action1<String>() {

            @Override
            public void call(String id) {
                switchFragment(STUDENT_SIGN_OK);
                ToastUitl.show("学员签到ok,显示学员信息", Toast.LENGTH_SHORT);
            }
        });
        mRxManager.on(RX_STUDENT_SIGN_OUT, new Action1<String>() {

            @Override
            public void call(String id) {
                switchFragment(SHOW_COACH_INFO);
                ToastUitl.show("学员信息界面", Toast.LENGTH_SHORT);
            }
        });
        mRxManager.on(RX_REBACK_MARKACTIVITY, new Action1<String>() {

            @Override
            public void call(String id) {
                switchFragment(SHOW_MARKACTIVITY);
                ToastUitl.show("返回主选单", Toast.LENGTH_SHORT);
            }
        });
    }

    private void switchFragment(int id) {
        index = id;
        myprogressbarview.setProgress(index);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        hideFragment(ft);
        switch (id) {
            case SHOW_MARKACTIVITY:                //标记界面    1
                if (markfragment != null) {
                    ft.show(markfragment);
                } else {
                    markfragment = markfragment.newInstance("", "");
                    ft.add(R.id.FrameLayout, markfragment);
                    ft.show(markfragment);
                }
                break;
            case COACH_SIGN:              //教练签到
                if (coach_sign_fragment != null) {             //          2
                    ft.show(coach_sign_fragment);
                } else {
                    coach_sign_fragment = Coach_sign_Fragment.newInstance("", "");
                    ft.add(R.id.FrameLayout, coach_sign_fragment);
                    ft.show(coach_sign_fragment);
                }
                break;
            case COACH_OK:              //教练签到ok
                if (coach_info_fragment != null) {
                    ft.show(coach_info_fragment);
                } else {
                    coach_info_fragment = Coach_Info_Fragment.newInstance("", "");
                    ft.add(R.id.FrameLayout, coach_info_fragment);
                    ft.show(coach_info_fragment);
                }
                break;
            case SHOW_COACH_INFO:       //显示教练信息
                if (coach_info_fragment != null) {
                    ft.show(coach_info_fragment);
                } else {
                    coach_info_fragment = Coach_Info_Fragment.newInstance("", "");
                    ft.add(R.id.FrameLayout, coach_info_fragment);
                    ft.show(coach_info_fragment);
                }
                break;
            case STUDENT_SIGN:              //学院签到
                if (student_sign_fragment != null) {
                    ft.show(student_sign_fragment);
                } else {
                    student_sign_fragment = Student_Sign_Fragment.newInstance("", "");
                    ft.add(R.id.FrameLayout, student_sign_fragment);
                    ft.show(student_sign_fragment);
                }
                break;
            case STUDENT_SIGN_OK:            //学员签到ok,显示学员信息
                if (student_info_fragment != null) {
                    ft.show(student_info_fragment);
                } else {
                    student_info_fragment = Student_Info_Fragment.newInstance("", "");
                    ft.add(R.id.FrameLayout, student_info_fragment);
                    ft.show(student_info_fragment);
                }
                break;
            case COACH_SIGN_OUT:            //教练签退
                if (coach_sign_out_fragment != null) {
                    ft.show(coach_sign_out_fragment);
                } else {
                    coach_sign_out_fragment = Coach_sign_out_Fragment.newInstance("", "");
                    ft.add(R.id.FrameLayout, coach_sign_out_fragment);
                    ft.show(coach_sign_out_fragment);
                }
                break;
        }
        ft.commit();
    }

    private void hideFragment(FragmentTransaction ft) {
        if (coach_sign_fragment != null) {
            ft.remove(coach_sign_fragment);
            coach_sign_fragment = null;
            return;
        }
        if (coach_info_fragment != null) {
            ft.remove(coach_info_fragment);
            coach_info_fragment = null;
            return;
        }
        if (markfragment != null) {
            ft.remove(markfragment);
            markfragment = null;
            return;
        }
        if (student_sign_fragment != null) {
            ft.remove(student_sign_fragment);
            student_sign_fragment = null;
            return;
        }
        if (coach_sign_out_fragment != null) {
            ft.remove(coach_sign_out_fragment);
            coach_sign_out_fragment = null;
            return;
        }
        if (markfragment != null) {
            ft.remove(markfragment);
            markfragment = null;
            return;
        }
        if (student_info_fragment != null) {
            ft.remove(student_info_fragment);
            student_info_fragment = null;
            return;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Intent intent = new Intent();
//        intent.setClass(this, MyService.class);
//        stopService(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        Intent intent = new Intent();
//        intent.setClass(this, MyService.class);
//        startService(intent);
    }
}
