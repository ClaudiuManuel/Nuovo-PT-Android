package com.example.nuovo_pt.ui.login;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LoginAdapter extends FragmentPagerAdapter {
    private Context context;
    int tabsCount;

    public LoginAdapter(FragmentManager fragmentManager, Context context, int tabsCount) {
        super(fragmentManager);
        this.context = context;
        this.tabsCount = tabsCount;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                LoginFragment loginFragment = new LoginFragment();
                return loginFragment;
            case 1:
                RegisterFragment registerFragment = new RegisterFragment();
                return registerFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabsCount;
    }
}
