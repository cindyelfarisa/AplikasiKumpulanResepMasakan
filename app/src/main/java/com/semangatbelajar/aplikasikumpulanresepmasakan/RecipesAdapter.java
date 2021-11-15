package com.semangatbelajar.aplikasikumpulanresepmasakan;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import retrofit2.Callback;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.MyViewHolder>{
    List<ModelRecipes> modelRecipesList;

    public RecipesAdapter( List <ModelRecipes> modelRecipesList) {

        this.modelRecipesList = modelRecipesList;
    }


    public List<ModelRecipes> getData(){
        return modelRecipesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        MyViewHolder mViewHolder = new MyViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder (MyViewHolder holder, @SuppressLint("RecyclerView") final int position){
        holder.mJudul.setText(modelRecipesList.get(position).getTitle());
        Context context = holder.itemView.getContext();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Menampilkan Resep", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", modelRecipesList.get(position).getKey());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount () {
        return modelRecipesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mJudul;

        public MyViewHolder(View itemView) {
            super(itemView);
            mJudul = (TextView) itemView.findViewById(R.id.txtJudul);
        }
    }
}
