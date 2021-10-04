package com.example.e_shop;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_shop.model.CartItem;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CartFragment extends Fragment {
    RecyclerView cartRv;
    Button placeOrderBTN;
    FirebaseFirestore mFirebaseFirestore;
    FirestoreRecyclerAdapter adapter;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        cartRv = view.findViewById(R.id.cartRV);
        placeOrderBTN = view.findViewById(R.id.placeOrder);


        Query query = mFirebaseFirestore.collection("users")
                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .collection("cart");

        final FirestoreRecyclerOptions<CartItem> options = new FirestoreRecyclerOptions.Builder<CartItem>()
                .setQuery(query, CartItem.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<CartItem, CartItemViewHolder>(options) {
            @NonNull
            @Override
            public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
                return new CartItemViewHolder(mView);
            }

            @Override
            protected void onBindViewHolder(@NonNull final CartItemViewHolder holder, final int position, @NonNull final CartItem model) {
                holder.title.setText(model.getTitle());
                holder.quantity.setText(model.getQuantity() + "");
                holder.price.setText(model.gettPrice() + " $ ( " + model.getPrice() + " )");
                holder.remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getSnapshots().getSnapshot(position).getReference().delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(getActivity(), "Item removed from cart", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                });
                Picasso.get().load(model.getImage()).into(holder.image, new Callback() {
                    @Override
                    public void onSuccess() {


                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });


            }

        };
        cartRv.setHasFixedSize(true);
        cartRv.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        cartRv.setAdapter(adapter);

        return view;
    }


    private static class CartItemViewHolder extends ViewHolder {
        TextView title, price, quantity;
        ImageView image;
        ImageButton remove;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.cart_title);
            price = itemView.findViewById(R.id.cart_totalprice);
            quantity = itemView.findViewById(R.id.cart_quantity);
            image = itemView.findViewById(R.id.cart_itemimage);
            remove = itemView.findViewById(R.id.removebtn);

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}