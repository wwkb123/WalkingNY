package com.walkingny.lag_arc_mac2.walkingny;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class Email_Fragment extends Fragment {
    private static final String TAG = "TAB3";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.email_fragment,container,false);
        Button composeEmailButton = view.findViewById(R.id.composeEmailButton);
        composeEmailButton.setTransformationMethod(null); //disable all caps text

        //Todo: add sending email code

        ImageButton facebookButton = view.findViewById(R.id.facebookButton);
        ImageButton twitterButton = view.findViewById(R.id.twitterButton);
        ImageButton instagramButton = view.findViewById(R.id.instagramButton);

        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new   Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/LaGuardiaWagnerArchives/"));
                startActivity(i);
            }
        });

        twitterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new   Intent(Intent.ACTION_VIEW, Uri.parse("https:/twitter.com/LaGuardiaWagner/"));
                startActivity(i);
            }
        });

        instagramButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new   Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/laguardiaandwagner/"));
                startActivity(i);
            }
        });

        return view;
    }
}
