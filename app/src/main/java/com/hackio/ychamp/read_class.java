package com.hackio.ychamp;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


public class read_class extends Base_activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readme);
        TextView t=findViewById(R.id.insta_link);
        t.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/hackio_official/?hl=en"));

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try{
                intent.setPackage("com.instagram.android");
                if(intent.resolveActivity(getPackageManager()) == null){
                    intent.setPackage("com.android.chrome");
                }

                startActivity(intent);
            }catch (Exception e){
                Toast.makeText(context,"Install instagram to view my account or search in instagram" +
                        " for hackio_official",Toast.LENGTH_LONG).show();
            }

        });

    }
}