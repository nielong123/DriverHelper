package com.driverhelper.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.driverhelper.R;
import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.baserx.RxBus;

import butterknife.Bind;
import butterknife.OnClick;

import static com.driverhelper.config.Config.Config_RxBus.RX_COACH_SIGN_OK;
import static com.driverhelper.config.Config.Config_RxBus.RX_STUDENT_SIGN_OK;

/**
 * 学生登录界面
 */
public class Student_Sign_Fragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.student_sign_ok)
    Button studentSignOk;
    @Bind(R.id.reback)
    Button reback;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Student_Sign_Fragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Student_Sign_Fragment newInstance(String param1, String param2) {
        Student_Sign_Fragment fragment = new Student_Sign_Fragment();
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
        return R.layout.fragment_login_;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.student_sign_ok, R.id.reback})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.student_sign_ok:
                RxBus.getInstance().post(RX_STUDENT_SIGN_OK, "");
                break;
            case R.id.reback:
                RxBus.getInstance().post(RX_COACH_SIGN_OK, "");
                break;
        }
    }
}
