package com.hackio.ychamp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class recycle extends RecyclerView.Adapter<recycle.HistoryViewHolder>{
    private final ArrayList<historylist> historylist;
    private final Context context;

    public recycle(Context context, ArrayList<historylist> histories) {
        this.historylist = histories;
        this.context = context;
    }



    public  class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView url,title_tv;
        int word_id;
        TextView tamil;
        int click;


        public HistoryViewHolder(View v) {
            super(v);
            url = v.findViewById(R.id.url);
            title_tv=v.findViewById(R.id.title_url);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    String text = historylist.get(position).getUrl();
                    Log.e("HISURL",text);
                    Pattern p1 = Pattern.compile("^[0-9]{2,20}");
                    Matcher m1 = p1.matcher(text);
                    Intent intent = new Intent(context, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("url", text);
                    intent.putExtras(bundle);
                    context.startActivity(intent);

                }
            });
        }
    }


    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.historyitem, parent, false);
        return new HistoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(HistoryViewHolder holder, final int position) {
        holder.url.setText(historylist.get(position).getUrl());
        holder.title_tv.setText(historylist.get(position).getUrl_title());
        holder.itemView.setTag(historylist.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return historylist.size();
    }
}
