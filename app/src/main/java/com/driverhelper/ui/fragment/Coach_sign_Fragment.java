package com.driverhelper.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.driverhelper.R;
import com.driverhelper.config.Config;
import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.baserx.RxBus;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 教练签到
 */
public class Coach_sign_Fragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.button)
    Button button;
    @Bind(R.id.back)
    Button back;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Coach_sign_Fragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Coach_sign_Fragment newInstance(String param1, String param2) {
        Coach_sign_Fragment fragment = new Coach_sign_Fragment();
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
        return R.layout.fragment_coach_sign_;
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


    @OnClick({R.id.button, R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                RxBus.getInstance().post(Config.Config_RxBus.RX_COACH_SIGN_OK, "");
                break;
            case R.id.back:
                RxBus.getInstance().post(Config.Config_RxBus.RX_REBACK_MARKACTIVITY, "");
                break;
        }
    }
}
