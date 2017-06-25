package com.driverhelper.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.driverhelper.R;
import com.driverhelper.config.Config;
import com.driverhelper.ui.activity.SettingActivity;
import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.baserx.RxBus;

import butterknife.Bind;
import butterknife.OnClick;

/**
 *
 *
 */
public class MarkFragment extends BaseFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.root)
    LinearLayout root;
    @Bind(R.id.setting)
    Button setting;
    @Bind(R.id.exit)
    Button exit;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MarkFragment() {
        // Required empty public constructor
    }

    /**
     */
    // TODO: Rename and change types and number of parameters
    public static MarkFragment newInstance(String param1, String param2) {
        MarkFragment fragment = new MarkFragment();
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
        return R.layout.fragment_main;
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


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.root, R.id.setting, R.id.exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.root:
                RxBus.getInstance().post(Config.Config_RxBus.RX_COACH_SIGN, "");
                break;
            case R.id.setting:
                getMyActivity().startActivity(new Intent(getMyActivity(), SettingActivity.class));
                break;
            case R.id.exit:
                getMyActivity().finish();
                break;
        }
    }

}
