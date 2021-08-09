package com.example.e_shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_shop.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<String> postsList = new ArrayList<>();
    Context context;
    public CategoryAdapter(Context context, List<String> productCategoryList) {
        this.postsList = productCategoryList;
        this.context=context;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_category_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.catName.setText(postsList.get(position));
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    public void setList(List<String> moviesList) {
        this.postsList = moviesList;
        notifyDataSetChanged();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView catName;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            catName=itemView.findViewById(R.id.cat_name);
        }
    }
}