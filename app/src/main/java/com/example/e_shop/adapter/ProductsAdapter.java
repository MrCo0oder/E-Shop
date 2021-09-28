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

import com.example.e_shop.ProductDetailsActivity;
import com.example.e_shop.R;
import com.example.e_shop.model.Products;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


import java.util.List;

import static com.example.e_shop.R.id.product_imgv;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> {
    Context context;
    private final List<Products> productsList;


    public ProductsAdapter(Context context, List<Products> productsList) {
        this.context = context;
        this.productsList = productsList;
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
        String url=productsList.get(position).getImage();
        Picasso.get().load(url).networkPolicy(NetworkPolicy.OFFLINE).into(holder.productImage, new Callback() {
            @Override
            public void onSuccess() {

                holder.progressBar.setVisibility(View.VISIBLE);
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

                intent.putExtra("productId",productsList.get(position).getId());
                intent.putExtra("productName",productsList.get(position).getTitle());
                intent.putExtra("productCat",productsList.get(position).getCategory());
                intent.putExtra("productPrice",productsList.get(position).getPrice());
                intent.putExtra("productDescription",productsList.get(position).getDescription());
                intent.putExtra("imageUrl",productsList.get(position).getImage());
                context.startActivity(intent);
            }
        });
            }

    @Override
    public int getItemCount() {
        return productsList.size();
    }


    public static final class ProductsViewHolder extends RecyclerView.ViewHolder {

        TextView productName, productPrice, productCat;
        ImageView productImage;
        CardView cardView;
        ProgressBar progressBar;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.price_tv);
            productImage = itemView.findViewById(product_imgv);
            productCat = itemView.findViewById(R.id.catTV);
            cardView = itemView.findViewById(R.id.product_card);
            progressBar=itemView.findViewById(R.id.progressBar2);

        }

    }
}