package com.example.e_shop.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_shop.R;
import com.example.e_shop.model.CartItem;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class CartAdapter extends FirestoreRecyclerAdapter<CartItem, com.example.e_shop.adapter.CartAdapter.CartItemViewHolder> {
    Context context;
    FirestoreRecyclerOptions<CartItem> options;

    public CartAdapter(@NonNull FirestoreRecyclerOptions<CartItem> options, Context c) {
        super(options);
        this.options = options;
        this.context = c;
    }

    @Override
    protected void onBindViewHolder(@NonNull CartItemViewHolder holder, int position, @NonNull CartItem model) {
        holder.title.setText((CharSequence) model.getTitle());
        holder.quantity.setText(String.valueOf(model.getQuantity()));
        holder.price.setText(String.format("%s $ ( %s$ )", model.getTotalPrice(), model.getPrice()));
        Picasso.get().load(model.getImage()).into(holder.itemImage, new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(Exception e) {
                Log.e("Cart", e.getLocalizedMessage());
            }
        });
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartItemViewHolder(view);
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                notifyItemRemoved(position);
            }
        });
    }

    static class CartItemViewHolder extends RecyclerView.ViewHolder {
        TextView title, price, quantity;
        ImageView itemImage;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.cart_title);
            price = itemView.findViewById(R.id.cart_totalprice);
            quantity = itemView.findViewById(R.id.cart_quantity);
            itemImage = itemView.findViewById(R.id.cart_itemimage);
        }
    }
}