package com.walkingny.lag_arc_mac2.walkingny;

/**
 *
 *  Fragment’s onResume() is not called when fragment is actually resumed and showed to the user on screen.
 *  So in standard ViewPager it is impossible to update fragment when it is displayed,
 *  since there is no lifecycle method that is called when fragment is displayed.
 *
 *  This interface is designed for handling lifecycle between fragments
 *
 */


public interface FragmentLifecycle {
    public void onPauseFragment();
    public void onResumeFragment();
}
