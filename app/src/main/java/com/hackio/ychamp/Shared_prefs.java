package com.hackio.ychamp;


import android.content.Context;
import android.content.SharedPreferences;

public class Shared_prefs extends Base_activity{
    public Context context;
    SharedPreferences sharedPreferences;


    Shared_prefs(Context context){
    this.context=context;
        sharedPreferences = context.getSharedPreferences("data", MODE_PRIVATE);

    }
    public void set_data(String data){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("main_website",data );
        editor.putBoolean("first_run", true);
        editor.apply();
        weburl=data;
    }

    public String get_site_name(){
        return sharedPreferences.getString("main_website","" );
    }


    public boolean first_check(){
        return sharedPreferences.getBoolean("first_run",false);
    }

}
