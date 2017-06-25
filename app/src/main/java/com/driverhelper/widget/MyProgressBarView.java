package com.driverhelper.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.driverhelper.R;
import com.orhanobut.logger.Logger;

/**
 * Created by Administrator on 2017/5/31.
 */

public final class MyProgressBarView extends LinearLayout {

    final int maxIndex = 5;
    final int minIndex = 0;
    int index;
    View view0, view1, view2, view3, view4, view5;

    public MyProgressBarView(Context context) {
        super(context);
        initView(context);
    }

    public MyProgressBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MyProgressBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.myprogressbarview_layout, this);
        view0 = findViewById(R.id.view_0);
        view1 = findViewById(R.id.view_1);
        view2 = findViewById(R.id.view_2);
        view3 = findViewById(R.id.view_3);
        view4 = findViewById(R.id.view_4);
        view5 = findViewById(R.id.view_5);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//        ButterKnife.unbind(this);
    }

    public void setProgress(int index) {
        this.index = index;
        if (this.index > maxIndex || this.index < minIndex) {
            try {
                throw new Exception("进度不能大于" + maxIndex + "且进度不能小于" + minIndex);
            } catch (Exception e) {
                e.printStackTrace();
                Logger.e(e.getMessage());
            }
        } else {
            setViewColor(index);
        }
    }

    void setViewColor(int index) {
        view0.setBackgroundColor(getResources().getColor(R.color.white));
        view1.setBackgroundColor(getResources().getColor(R.color.white));
        view2.setBackgroundColor(getResources().getColor(R.color.white));
        view3.setBackgroundColor(getResources().getColor(R.color.white));
        view4.setBackgroundColor(getResources().getColor(R.color.white));
        view5.setBackgroundColor(getResources().getColor(R.color.white));
        switch (index) {
            case 0:
                view0.setBackgroundColor(getResources().getColor(R.color.day_two_text_color));
                break;
            case 1:
                view1.setBackgroundColor(getResources().getColor(R.color.day_two_text_color));
                break;
            case 2:
                view2.setBackgroundColor(getResources().getColor(R.color.day_two_text_color));
                break;
            case 3:
                view3.setBackgroundColor(getResources().getColor(R.color.day_two_text_color));
                break;
            case 4:
                view4.setBackgroundColor(getResources().getColor(R.color.day_two_text_color));
                break;
            case 5:
                view5.setBackgroundColor(getResources().getColor(R.color.day_two_text_color));
                break;
        }
    }


    public int getProgress() {
        return index;
    }
}
