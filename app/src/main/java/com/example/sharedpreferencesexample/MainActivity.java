package com.example.sharedpreferencesexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String COUNT_KEY = "COUNT_KEY";
    public static final String COLOR_KEY = "COLOR_KEY";

    public static final String sharedPrefFileName =
            "com.example.sharedpreferencesexample";

    private SharedPreferences mSharedPreferences;

    //arbitrary data
    private int mCount;
    private int mCurrentColor;

    private Button mClickableButton;
    private SharedPreferences.OnSharedPreferenceChangeListener mSharedPreferenceListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize sharedPreferences by passing the name and the MODE to getSharedPreferences
        mSharedPreferences = getSharedPreferences(sharedPrefFileName, MODE_PRIVATE);
        //retrieve data from SharedPreferences in onCreate
        if (mSharedPreferences != null) {
            mCount = mSharedPreferences.getInt(COUNT_KEY, 1);
            Log.d("count_after_shared", mCount+"");

            mCurrentColor = mSharedPreferences.getInt(COLOR_KEY, mCurrentColor);
            Log.d("color_after_shared", mCurrentColor+"");

        } else {
            mCount = 0;
            mCurrentColor = 0;
            Log.d("count_after_shared", mCount+"");
            Log.d("color_after_shared", mCurrentColor+"");
        }


        //Testing for the listener:
        mClickableButton = findViewById(R.id.clickable_button);
        mClickableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //let's change count here
                SharedPreferences.Editor prefsEditor = mSharedPreferences.edit();
                prefsEditor.putInt(COUNT_KEY, 3);
                prefsEditor.apply();

                Intent intent = new Intent(MainActivity.this, SharedPreferencesListnerActivity.class);
                startActivity(intent);
            }
        });

        //what if i want to update my view based on whenever data changes in my sharedPreferences?
        mSharedPreferenceListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                //you can update view here depending on what you want to do when someone/thing changes
                //or adds to shared preferences
                Toast.makeText(MainActivity.this,
                        "Shared preferences has been updated",
                        Toast.LENGTH_LONG).show();
            }
        };
        mSharedPreferences.registerOnSharedPreferenceChangeListener(mSharedPreferenceListener);

        /**
         * You can do this all in one step as well
         *
        mSharedPreferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

            }
        });
        */

    }

    //save sharedPreferences data in onPause
    @Override
    protected void onPause() {
        super.onPause();
        //when you're about to edit/add/remove to shared preferences, you use
        //the Editor
        SharedPreferences.Editor preferencesEditor = mSharedPreferences.edit();
        mCount = 2;
        mCurrentColor = 2;
        preferencesEditor.putInt(COUNT_KEY, mCount);
        preferencesEditor.putInt(COLOR_KEY, mCurrentColor);
        //once you're done with adding to sharedprefs, you call apply()
        preferencesEditor.apply();

        //if you want to clear the data in sharedPrefs:
//        preferencesEditor.clear();

        //unregister listener here:
        mSharedPreferences.unregisterOnSharedPreferenceChangeListener(mSharedPreferenceListener);

    }
}