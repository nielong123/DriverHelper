package com.driverhelper.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Button;

import com.driverhelper.R;
import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.baserx.RxBus;

import butterknife.Bind;
import butterknife.OnClick;

import static com.driverhelper.config.Config.Config_RxBus.RX_COACH_SIGN_OUT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Coach_sign_out_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Coach_sign_out_Fragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.sign_out_ok)
    Button signOutOk;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Coach_sign_out_Fragment() {
        // Required empty public constructor
    }

    /**
     *
     */
    // TODO: Rename and change types and number of parameters
    public static Coach_sign_out_Fragment newInstance(String param1, String param2) {
        Coach_sign_out_Fragment fragment = new Coach_sign_out_Fragment();
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
        return R.layout.fragment_coach_sign_out_;
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

    @OnClick(R.id.sign_out_ok)
    public void onClick() {
        RxBus.getInstance().post(RX_COACH_SIGN_OUT_OK, "");
    }
}
