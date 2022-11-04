package com.hackio.ychamp;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class file_Activity {
    public Context context;
    public final String Name = "server_name.txt";
   static List<String> url_container = new ArrayList<>();

    public file_Activity(Context context){
        this.context=context;
    }

    public void copy_File() throws IOException {
         final String path = context.getFilesDir().getPath();

        OutputStream myOutput = new FileOutputStream(path + Name);
        byte[] buffer = new byte[1024];
        int length;
        InputStream myInput = context.getAssets().open("server_name.txt");
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myInput.close();
        myOutput.flush();
        myOutput.close();
    }

    public void read_Data(){
        try {
            InputStream in= context.getAssets().open("server_name.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;

            while ((line = reader.readLine()) != null)
                url_container.add(line);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
