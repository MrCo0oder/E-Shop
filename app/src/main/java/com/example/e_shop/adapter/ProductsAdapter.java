package com.example.e_shop.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.example.e_shop.model.Product;
import com.example.e_shop.ui.ProductDetailsActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;


public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> {
    Context context;


    private  List<Product> productsList=new ArrayList<>();


    public ProductsAdapter(Context context) {
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductsViewHolder holder, final int position) {

        holder.productName.setText(productsList.get(position).getTitle());
        holder.productPrice.setText(productsList.get(position).getPrice() + " $");
        holder.productCat.setText(productsList.get(position).getCategory());
        holder.progressBar.setVisibility(View.VISIBLE);
        String url=productsList.get(position).getImage();
        Picasso.get().load(url).into(holder.productImage, new Callback() {
            @Override
            public void onSuccess() {
                if (holder.progressBar != null) {
                    holder.progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Exception e) {

                Picasso.get()
                        .load(productsList.get(position).getImage())
                        .into(holder.productImage, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                Log.v("Picasso", "Could not fetch image");
                                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        });
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("PRODUCT",productsList.get(position));
                context.startActivity(intent);
            }
        });
            }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public void setProductsList(List<Product> productsList) {
        this.productsList = productsList;
        notifyDataSetChanged();
    }

    public  class ProductsViewHolder extends RecyclerView.ViewHolder {

        TextView productName, productPrice, productCat;
        ImageView productImage;
        CardView cardView;
        ProgressBar progressBar;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.price_tv);
            productImage = itemView.findViewById(R.id.product_imgv);
            productCat = itemView.findViewById(R.id.catTV);
            cardView = itemView.findViewById(R.id.product_card);
            progressBar=itemView.findViewById(R.id.progressBar2);
        }

    }
}