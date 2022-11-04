package com.hackio.ychamp;

import static com.hackio.ychamp.MainActivity.weburl;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class dialog_Class {
    Shared_prefs shared_prefs;
    AlertDialog alertDialog = null;
    public Context context;

    dialog_Class(Context context) {
        this.context = context;
        shared_prefs = new Shared_prefs(this.context);
    }

    public String user_input(Context context, Shared_prefs shared_prefs) {
        AlertDialog.Builder d = new AlertDialog.Builder(context, androidx.appcompat.R.style.AlertDialog_AppCompat);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View dialogView = layoutInflater.inflate(R.layout.user_input, null);
        EditText t = dialogView.findViewById(R.id.user_input_edit);
        Button a_btn = dialogView.findViewById(R.id.default_btn);
        d.setTitle("Enter URL");
        d.setView(dialogView);
        alertDialog = d.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        a_btn.setOnClickListener(v -> {
            weburl = t.getText().toString();
            check_url(weburl);
            write();
        });

        return weburl;
    }

    public void write() {
        shared_prefs.set_data(weburl);
    }

    public String check_url(String a) {
        if (a.equals("")) {
            Toast.makeText(context, "please input URL", Toast.LENGTH_LONG).show();

        } else if (a.startsWith("http")) {
            if (alertDialog != null) {
                alertDialog.dismiss();
            }
            Toast.makeText(context, "Default URL : " + a + "\n For first time press reload on top menu bar", Toast.LENGTH_LONG).show();

            weburl = a;
        } else {
            Toast.makeText(context, "please Enter valid URL", Toast.LENGTH_LONG).show();
        }
        return a;
    }
}
