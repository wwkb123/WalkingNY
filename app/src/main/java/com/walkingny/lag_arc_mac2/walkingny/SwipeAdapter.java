package com.walkingny.lag_arc_mac2.walkingny;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class SwipeAdapter extends FragmentStatePagerAdapter {
    private int num_of_images = 0;
    public SwipeAdapter(FragmentManager fm, int num_of_images_input){
        super(fm);
        num_of_images = num_of_images_input;
    }

    @Override
    public Fragment getItem(int i){
        Fragment fragment = new Image_Child_Fragment_Child();  //get images of the child fragment
        return fragment;
    }

    @Override
    public int getCount(){
        return num_of_images; //number of image fragments
    }
}
