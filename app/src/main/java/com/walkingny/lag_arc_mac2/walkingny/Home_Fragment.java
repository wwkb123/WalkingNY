package com.walkingny.lag_arc_mac2.walkingny;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Home_Fragment extends Fragment {
    private static final String TAG = "TAB1";
    public int numberOfImages = 0;
    public String arrayToPass = "";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        MyAsyncTask mAsyncTask = new MyAsyncTask();
        mAsyncTask.execute();
        return view;
    }

    class MyAsyncTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Boolean doInBackground(String... urls) {
                URL url;
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL("http://www.laguardiawagnerarchive.lagcc.cuny.edu/map_app/?command=nearby&lat=40.7439&long=-73.9347");

                    urlConnection = (HttpURLConnection) url
                            .openConnection();

                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    String result = inputStreamToString(in);
                    try {
                        JSONArray response_json_arr = new JSONArray(result);
                        numberOfImages = response_json_arr.length(); //get the number of images
                        arrayToPass = response_json_arr.toString();
                    }catch (JSONException e){
                        Log.e("My App", "Could not parse malformed JSON: \"" + result + "\"");
                    }

                    /** create a child fragment and pass the number of images */
                    Bundle bundle = new Bundle();
                    bundle.putString("num_of_images", numberOfImages+"");
                    bundle.putString("arrayToPass", arrayToPass);
                    Fragment childFragment = new Image_Child_Fragment();
                    childFragment.setArguments(bundle);
                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    transaction.replace(R.id.child_fragment_container, childFragment).commit();

            } catch (IOException e) {

                    /** create a child fragment and pass an empty child fragment */
                    Bundle bundle = new Bundle();
                    bundle.putString("num_of_images", numberOfImages+"");
                    bundle.putString("arrayToPass", arrayToPass);
                    Fragment childFragment = new Image_Child_Fragment();
                    childFragment.setArguments(bundle);
                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    transaction.replace(R.id.child_fragment_container, childFragment).commit();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {

        }
    }

    private String inputStreamToString(InputStream is) {
        String rLine = "";
        StringBuilder answer = new StringBuilder();

        InputStreamReader isr = new InputStreamReader(is);

        BufferedReader rd = new BufferedReader(isr);

        try {
            while ((rLine = rd.readLine()) != null) {
                answer.append(rLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answer.toString();
    }
}
