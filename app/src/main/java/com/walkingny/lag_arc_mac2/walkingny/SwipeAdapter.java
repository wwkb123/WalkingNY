package com.walkingny.lag_arc_mac2.walkingny;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class SwipeAdapter extends FragmentStatePagerAdapter {

    public SwipeAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int i){
        Fragment fragment = new Image_Child_Fragment_Child();
        return fragment;
    }

    @Override
    public int getCount(){
        return 3;
    }
}
