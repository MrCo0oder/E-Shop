package com.example.e_shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_shop.MainActivity;
import com.example.e_shop.R;
import com.example.e_shop.model.ProductCategory;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private ArrayList<ProductCategory> postsList = new ArrayList<>();
    Context context;
    public CategoryAdapter(Context context, ArrayList<ProductCategory> productCategoryList) {
        this.postsList = productCategoryList;
        this.context=context;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_category_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryViewHolder holder, int position) {
        holder.catName.setText(postsList.get(position).getCategoryName());
        String url=postsList.get(position).getImage();
        Picasso.get().load(url).into(holder.catImage, new Callback() {
            @Override
            public void onSuccess() {
                holder.progressBar.setVisibility(View.VISIBLE);
                if (holder.progressBar != null) {
                    holder.progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        System.out.println(postsList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }


    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView catName;
        ImageView catImage;
        ProgressBar progressBar;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            catName=itemView.findViewById(R.id.cat_name);
            catImage=itemView.findViewById(R.id.cat_imageView);
            progressBar=itemView.findViewById(R.id.progressBar4);
        }
    }
}