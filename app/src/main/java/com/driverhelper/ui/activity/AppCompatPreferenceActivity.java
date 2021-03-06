package com.driverhelper.ui.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/7/10.
 */

public abstract class AppCompatPreferenceActivity extends PreferenceActivity {

    private AppCompatDelegate mDelegate;

    private AppCompatDelegate getDelegate() {
        if (this.mDelegate == null)
            this.mDelegate = AppCompatDelegate.create(this, null);
        return this.mDelegate;
    }

    @Override
    public void addContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams) {
        getDelegate().addContentView(paramView, paramLayoutParams);
    }

    @Override
    public MenuInflater getMenuInflater() {
        return getDelegate().getMenuInflater();
    }

    public ActionBar getSupportActionBar() {
        return getDelegate().getSupportActionBar();
    }

    @Override
    public void invalidateOptionsMenu() {
        getDelegate().invalidateOptionsMenu();
    }

    @Override
    public void onConfigurationChanged(Configuration paramConfiguration) {
        super.onConfigurationChanged(paramConfiguration);
        getDelegate().onConfigurationChanged(paramConfiguration);
    }

    @Override
    protected void onCreate(Bundle paramBundle) {
        getDelegate().installViewFactory();
        getDelegate().onCreate(paramBundle);
        super.onCreate(paramBundle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getDelegate().onDestroy();
    }

    @Override
    protected void onPostCreate(Bundle paramBundle) {
        super.onPostCreate(paramBundle);
        getDelegate().onPostCreate(paramBundle);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getDelegate().onPostResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getDelegate().onStop();
    }

    @Override
    protected void onTitleChanged(CharSequence paramCharSequence, int paramInt) {
        super.onTitleChanged(paramCharSequence, paramInt);
        getDelegate().setTitle(paramCharSequence);
    }

    @Override
    public void setContentView(@LayoutRes int paramInt) {
        getDelegate().setContentView(paramInt);
    }

    @Override
    public void setContentView(View paramView) {
        getDelegate().setContentView(paramView);
    }

    @Override
    public void setContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams) {
        getDelegate().setContentView(paramView, paramLayoutParams);
    }

    public void setSupportActionBar(@Nullable Toolbar paramToolbar) {
        getDelegate().setSupportActionBar(paramToolbar);
    }
}
