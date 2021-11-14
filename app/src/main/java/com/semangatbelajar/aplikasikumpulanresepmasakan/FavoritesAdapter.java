package com.semangatbelajar.aplikasikumpulanresepmasakan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.MyViewHolder>{
    List<String> title;

    public FavoritesAdapter( List <String> title
    ) {

        this.title = title;
    }


    public List<String> getData(){
        return title;
    }

    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_favorites, parent, false);
        MyViewHolder mViewHolder = new MyViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder (MyViewHolder holder, @SuppressLint("RecyclerView") final int position){
        String title_fix = title.toString().replace("-", " ")
                .replace("[","")
                .replace("]","");
        String words[] = title_fix.split("\\s");
        String capitalizeStr = "";
        for (String word : words){
            String firstLetter = word.substring(0,1);
            String remaining = word.substring(1);
            capitalizeStr += firstLetter.toUpperCase() + remaining + " ";
        }
        holder.mJudul.setText(capitalizeStr);
        Context context = holder.itemView.getContext();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", title.toString());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount () {
        return title.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mJudul;

        public MyViewHolder(View itemView) {
            super(itemView);
            mJudul = (TextView) itemView.findViewById(R.id.txtJudul);
        }
    }
}
