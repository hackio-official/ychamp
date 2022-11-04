package com.hackio.ychamp;

import android.os.Bundle;

public class Settings_Activity extends Base_activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fram, new settingsfragment())
                .commit();
    }
}