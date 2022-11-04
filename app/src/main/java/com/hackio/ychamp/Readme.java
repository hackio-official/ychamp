package com.hackio.ychamp;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;


public class Readme extends Base_activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readme);
        TextView t=findViewById(R.id.insta_link);
        t.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/hackio_official/?hl=en"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setPackage("com.instagram.android");
            startActivity(intent);
        });

    }
}