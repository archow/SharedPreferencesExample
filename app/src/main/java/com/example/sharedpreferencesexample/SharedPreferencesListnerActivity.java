package com.example.sharedpreferencesexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class SharedPreferencesListnerActivity extends AppCompatActivity
    implements SharedPreferences.OnSharedPreferenceChangeListener {

    private TextView mListenerTextView;
    private SharedPreferences.OnSharedPreferenceChangeListener mListener;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences_listner);
        mListenerTextView = findViewById(R.id.listener_tv);

        mSharedPreferences = getSharedPreferences(MainActivity.sharedPrefFileName, MODE_PRIVATE);

        mListener = this;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(MainActivity.COLOR_KEY)) {
            mListenerTextView.setText("Color Changed");
        }
        if (key.equals(MainActivity.COUNT_KEY)) {
            mListenerTextView.setText("Count Changed");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        getSharedPreferences().registerOnSharedPreferenceChangeListener();
        mSharedPreferences.registerOnSharedPreferenceChangeListener(mListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //to unregister listener:
        mSharedPreferences.unregisterOnSharedPreferenceChangeListener(mListener);
    }
}