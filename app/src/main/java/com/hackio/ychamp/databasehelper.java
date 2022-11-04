package com.hackio.ychamp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class databasehelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "sample_enc.db";
    int db_version= (int) 1.0;
    String table="history";
    String col_url="url";
    String ID_COL="id";
    String title_column="title";
    String DB_PATH;
    private Context context;
    private SQLiteDatabase mydatabase;


    public databasehelper(Context context) {
        super(context, DB_NAME, null, 1);

        this.context = context;
        this.DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        String q="CREATE TABLE " + table + " (" +ID_COL+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + col_url + " TEXT,"+title_column+" TEXT )";
        db.execSQL(q);
    }
    public void add_history(String url, String title) {
        if (!url.equals("")&&!title.equals(""))
        {
            Log.e("url", url);
        SQLiteDatabase db = this.getWritableDatabase();
           if(check(url)) {
                return;
            }
        db.execSQL("INSERT INTO " + table + " (" + col_url + "," + title_column + ") VALUES  ('" + url + "','" + title + "')");
        //    mydbhelp.execSQL("INSERT INTO saved(word_id) VALUES  ('" + word_id + "')");

        Log.e("qqq", url + " inserted");

    }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ table);
        onCreate(db);
    }

    public Cursor getHistory() {
        SQLiteDatabase database=this.getReadableDatabase();
        Cursor c=database.rawQuery("SELECT * FROM "+table,null);
        return c;
    }

    public void delete_history_word(int id) {
        mydatabase = this.getWritableDatabase();
        mydatabase.delete("history", "id=  "+ id  , null);
    }
    public void deletehistory() {
        mydatabase=this.getWritableDatabase();
        mydatabase.execSQL("DELETE FROM history");
    }
    public boolean check(String url){
        SQLiteDatabase database=this.getReadableDatabase();
            Cursor a=database.rawQuery("Select url from history where url='"+url+"'",null);

        if(a.getCount()==0){
            return false;
        }
        return true;
    }
}
