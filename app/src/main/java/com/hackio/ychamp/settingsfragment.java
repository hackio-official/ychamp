package com.hackio.ychamp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import static com.hackio.ychamp.Base_activity.mydbhelp;
import static com.hackio.ychamp.MainActivity.weburl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import java.util.Objects;

public class settingsfragment extends PreferenceFragmentCompat {
    private Context context;
    EditTextPreference default_url;
    Preference delete_history;
    String data = "";

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        context = getActivity();
        delete_history=findPreference("delete_all_history");
        default_url = findPreference("default_url");

        assert default_url != null;
        default_url.setText(weburl);
        Objects.requireNonNull(default_url).setOnPreferenceChangeListener((preference, newValue) -> {
            data = String.valueOf(newValue);
            weburl = data;
            default_url.setText(data);
            dialog_Class dialog_class = new dialog_Class(context);

            if (data.equals("")) {
                return false;
            }
            dialog_class.check_url(data);
            dialog_class.write();
            return false;
        });

       delete_history.setOnPreferenceClickListener(preference -> {
           AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_Ychamp);
           builder.setTitle("Are you sure?");
           builder.setMessage("All the history will be deleted");

           String positiveText = "Yes";
           builder.setPositiveButton(positiveText, (dialog, which) -> {
               mydbhelp.deletehistory();
               Toast.makeText(context, "History List deleted successfully", Toast.LENGTH_SHORT).show();
           });

           String negativeText = "No";
           builder.setNegativeButton(negativeText,
                   (dialog, which) -> {

                   });

           AlertDialog dialog = builder.create();
           dialog.show();
           return false;
       });
    }
}
