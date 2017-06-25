package com.driverhelper.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.aspsine.irecyclerview.IRecyclerView;
import com.driverhelper.R;
import com.driverhelper.ui.adapter.HistoryRecyvlerViewAdapter;
import com.jaydenxiao.common.base.BaseFragment;

import butterknife.Bind;

/**
 *
 */
public class Student_History_Fragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.irecyclerview)
    IRecyclerView iRecyclerview;

    HistoryRecyvlerViewAdapter historyRecyvlerViewAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Student_History_Fragment() {
        // Required empty public constructor
    }

    /**
     *
     */
    // TODO: Rename and change types and number of parameters
    public static Student_History_Fragment newInstance(String param1, String param2) {
        Student_History_Fragment fragment = new Student_History_Fragment();
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
        return R.layout.fragment_student__history_;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getMyActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        iRecyclerview.setLayoutManager(linearLayoutManager);
        historyRecyvlerViewAdapter = new HistoryRecyvlerViewAdapter(getMyActivity());
    }

    @Override
    protected void initEvent() {

    }
}
