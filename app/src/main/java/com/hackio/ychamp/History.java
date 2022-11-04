package com.hackio.ychamp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class History extends AppCompatActivity {
    static databasehelper mydbhelp;
    static boolean databaseOpened = true;
    historylist h;
    ProgressDialog progressDialog;
    SimpleCursorAdapter simpleCursorAdapter;

    ArrayList<historylist> historylist;
    RecyclerView recycler_view_history;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter historyAdapter;

    RelativeLayout emptyHistory;
    Cursor c;
    private String en_word;
    private String definition;
    private String tamil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mydbhelp = new databasehelper(this);
        progressDialog = new ProgressDialog(this);
        fetch_history();

    }


    private void fetch_history() {

        emptyHistory = findViewById(R.id.empty_history);

        recycler_view_history = findViewById(R.id.recycler_view_history);
        layoutManager = new LinearLayoutManager(History.this);

        recycler_view_history.setLayoutManager(layoutManager);
        historylist = new ArrayList<historylist>();
        historyAdapter = new recycle(this, historylist);
        recycler_view_history.setAdapter(historyAdapter);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                remove_history_word((int) viewHolder.itemView.getTag(),position);
            }
        }).attachToRecyclerView(recycler_view_history);


        class async extends AsyncTask {


            public async(History history) {

            }

            @Override
            protected void onPreExecute() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        progressDialog.setTitle("loading....");
                        progressDialog.setMessage("Please wait");
                        progressDialog.setCancelable(true);
                        progressDialog.show();
                    }
                });
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Object o) {
                display();
                super.onPostExecute(o);
            }

            @Override
            protected Object doInBackground(Object[] objects) {
                loaddata();
                return null;
            }
        }


        async a = new async(History.this);
        a.execute();
    }

    private void display() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                progressDialog.dismiss();
                historyAdapter.notifyDataSetChanged();


                if (historyAdapter.getItemCount() == 0) {
                    emptyHistory.setVisibility(View.VISIBLE);
                } else {
                    emptyHistory.setVisibility(View.GONE);
                }
            }
        });
    }


    @SuppressLint("Range")
    private void loaddata() {
        if (databaseOpened) {

            c = mydbhelp.getHistory();
            if (c.moveToLast()) {
                do {
                    h = new historylist(c.getString(c.getColumnIndex("title")),c.getString(c.getColumnIndex("url")), c.getInt(c.getColumnIndex("id")));
                    historylist.add(h);
                }
                while (c.moveToPrevious());
            }

        }
    }

    private void remove_history_word(int id, int position) {
        historylist.remove(position);
        historyAdapter.notifyItemRemoved(position);

        mydbhelp.delete_history_word(id);
        Toast.makeText(getApplicationContext(), "Swiped  deleted from History List successfully", Toast.LENGTH_SHORT).show();

        //fetch_history();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

}
