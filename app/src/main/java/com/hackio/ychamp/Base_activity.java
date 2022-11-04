package com.hackio.ychamp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Base_activity extends AppCompatActivity {
    public Context context;
   static databasehelper mydbhelp;
    public static String weburl = "";
    static boolean startup = false, run = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
         mydbhelp=new databasehelper(context);

        Shared_prefs shared_prefs = new Shared_prefs(context);
        boolean check = shared_prefs.first_check();
        if (!check) {
            dialog_Class d = new dialog_Class(context);
            file_Activity f = new file_Activity(context);
            f.read_Data();

            weburl = d.user_input(context, shared_prefs);
            run = true;
        } else {
            weburl = shared_prefs.get_site_name();
        }

    }
}
