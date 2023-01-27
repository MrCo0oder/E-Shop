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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_shop.R;
import com.example.e_shop.model.ProductCategory;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {
    Context context;
    private ArrayList<ProductCategory> itemsList = new ArrayList<>();
        public CategoriesAdapter(Context context) {
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoriesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_category_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoriesViewHolder holder, final int position) {
        holder.catName.setText(itemsList.get(position).getCategoryName());

        String url = itemsList.get(position).getImage();
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
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public void setItemsList(ArrayList<ProductCategory> itemsList) {
        this.itemsList = itemsList;
        notifyDataSetChanged();
    }
    public ProductCategory getItemAtPosition(int clickedItem){
            return itemsList.get(clickedItem);
    }

    public class CategoriesViewHolder extends RecyclerView.ViewHolder {
        TextView catName;
        ImageView catImage;
        ProgressBar progressBar;
        CardView cardView;
        public CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            catName=itemView.findViewById(R.id.cat_name);
            catImage=itemView.findViewById(R.id.cat_imageView);
            progressBar = itemView.findViewById(R.id.progressBar4);
            cardView = itemView.findViewById(R.id.cat_cardview);

        }

    }
}