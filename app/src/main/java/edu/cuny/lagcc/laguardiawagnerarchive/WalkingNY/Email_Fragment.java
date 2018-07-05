package edu.cuny.lagcc.laguardiawagnerarchive.WalkingNY;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.TextViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Email_Fragment extends Fragment implements FragmentLifecycle {
    private static final String TAG = "TAB3";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.email_fragment,container,false);

        ////////////Responsive text size////////////////

        TextView text1 = view.findViewById(R.id.emailTab_1);
        TextViewCompat.setAutoSizeTextTypeWithDefaults(text1,TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration( text1,  12, 24,2,2);

        TextView text2 = view.findViewById(R.id.emailTab2);
        TextViewCompat.setAutoSizeTextTypeWithDefaults(text2,TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration( text2,  12, 24,2,2);

        TextView text3 = view.findViewById(R.id.emailTab3);
        TextViewCompat.setAutoSizeTextTypeWithDefaults(text3,TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration( text3,  12, 24,2,2);

        TextView text4 = view.findViewById(R.id.textView5);
        TextViewCompat.setAutoSizeTextTypeWithDefaults(text4,TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration( text4,  12, 24,2,2);


        /////////////End of responsive text size/////////////


        Button composeEmailButton = view.findViewById(R.id.composeEmailButton);
        composeEmailButton.setTransformationMethod(null); //disable all caps text

        composeEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SENDTO);
                String subject = "Walking New York - General Question";
                String[] address = {"sweinstein@lagcc.cuny.edu"};

                i.setType("text/plain");
                i.setData(Uri.parse("mailto:"));
                i.putExtra(Intent.EXTRA_EMAIL, address);
                i.putExtra(Intent.EXTRA_SUBJECT, subject);

                startActivity(Intent.createChooser(i, "Send email through..."));
            }
        });

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

    @Override
    public void onPauseFragment() {
        Log.i(TAG, "onPauseFragment()");
        //Toast.makeText(getActivity(), "onPauseFragment():" + TAG, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResumeFragment() {
        Log.i(TAG, "onResumeFragment()");
        //Toast.makeText(getActivity(), "onResumeFragment():" + TAG, Toast.LENGTH_SHORT).show();
    }
}
