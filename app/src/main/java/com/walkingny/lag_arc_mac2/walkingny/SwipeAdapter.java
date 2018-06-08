package com.walkingny.lag_arc_mac2.walkingny;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class SwipeAdapter extends FragmentStatePagerAdapter {

    public SwipeAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int i){
        Fragment fragment = new Image_Child_Fragment_Child();  //get images of the child fragment
        return fragment;
    }

    @Override
    public int getCount(){
        return 3; //number of images
    }
}
