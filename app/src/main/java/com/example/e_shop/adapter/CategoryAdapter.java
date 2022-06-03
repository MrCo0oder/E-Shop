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

import com.example.e_shop.MainActivity;
import com.example.e_shop.R;
import com.example.e_shop.api.ApiInterface;
import com.example.e_shop.model.ProductCategory;
import com.example.e_shop.model.Products;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private final ArrayList<ProductCategory> postsList;
    Context context;

    public CategoryAdapter(Context context, ArrayList<ProductCategory> productCategoryList) {
        this.postsList = productCategoryList;
        this.context = context;
        postsList.add(0,
                new ProductCategory("All",
                        "https://firebasestorage.googleapis.com/v0/b/e-shop-5de8d.appspot.com/o/explore_icon.png?alt=media&token=8df78b2c-25ef-4147-998c-417f1d693b9e"));
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_category_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryViewHolder holder, final int position) {
        holder.catName.setText(postsList.get(position).getCategoryName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://fakestoreapi.com/").addConverterFactory(GsonConverterFactory.create()).build();
                ApiInterface apiInterface = retrofit.create(ApiInterface.class);
                switch (position) {
                    case 0:
                        Call<List<Products>> allProducts = apiInterface.getAllProducts();
                        allProducts.enqueue(new retrofit2.Callback<List<Products>>() {
                            @Override
                            public void onResponse(@NotNull Call<List<Products>> call, @NotNull Response<List<Products>> response) {
                                holder.progressBar.setVisibility(View.VISIBLE);
                                if (holder.progressBar != null) {
                                    holder.progressBar.setVisibility(View.GONE);
                                }
                                MainActivity.setProductsRecycler(response.body());
                            }

                            @Override
                            public void onFailure(@NotNull Call<List<Products>> call, @NotNull Throwable t) {
                                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                                holder.progressBar.setVisibility(View.GONE);

                            }
                        });
                        notifyDataSetChanged();

                        break;
                    case 1:
                        Call<List<Products>> electronicsCall = apiInterface.getElectronicsCategory();
                        electronicsCall.enqueue(new retrofit2.Callback<List<Products>>() {
                            @Override
                            public void onResponse(@NotNull Call<List<Products>> call, @NotNull Response<List<Products>> response) {
                                holder.progressBar.setVisibility(View.VISIBLE);
                                if (holder.progressBar != null) {
                                    holder.progressBar.setVisibility(View.GONE);
                                }
                                MainActivity.setProductsRecycler(response.body());
                            }

                            @Override
                            public void onFailure(@NotNull Call<List<Products>> call, @NotNull Throwable t) {
                                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                                holder.progressBar.setVisibility(View.GONE);

                            }
                        });
                        notifyDataSetChanged();

                        break;
                    case 2:
                        Call<List<Products>> jewelryCall = apiInterface.getJeweleryCategory();
                        jewelryCall.enqueue(new retrofit2.Callback<List<Products>>() {
                            @Override
                            public void onResponse(@NotNull Call<List<Products>> call, @NotNull Response<List<Products>> response) {
                                holder.progressBar.setVisibility(View.VISIBLE);
                                if (holder.progressBar != null) {
                                    holder.progressBar.setVisibility(View.GONE);
                                }
                                MainActivity.setProductsRecycler(response.body());
                            }

                            @Override
                            public void onFailure(@NotNull Call<List<Products>> call, @NotNull Throwable t) {
                                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                                holder.progressBar.setVisibility(View.GONE);

                            }
                        });
                        notifyDataSetChanged();

                        break;
                    case 3:
                        Call<List<Products>> menCall = apiInterface.getMenClothingCategory();
                        menCall.enqueue(new retrofit2.Callback<List<Products>>() {
                            @Override
                            public void onResponse(@NotNull Call<List<Products>> call, @NotNull Response<List<Products>> response) {
                                holder.progressBar.setVisibility(View.VISIBLE);
                                if (holder.progressBar != null) {
                                    holder.progressBar.setVisibility(View.GONE);
                                }
                                MainActivity.setProductsRecycler(response.body());
                            }

                            @Override
                            public void onFailure(@NotNull Call<List<Products>> call, @NotNull Throwable t) {
                                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                                holder.progressBar.setVisibility(View.GONE);

                            }
                        });
                        notifyDataSetChanged();

                        break;
                    case 4:
                        Call<List<Products>> womenCall = apiInterface.getWomenClothingCategory();
                        womenCall.enqueue(new retrofit2.Callback<List<Products>>() {
                            @Override
                            public void onResponse(@NotNull Call<List<Products>> call, @NotNull Response<List<Products>> response) {
                                holder.progressBar.setVisibility(View.VISIBLE);
                                if (holder.progressBar != null) {
                                    holder.progressBar.setVisibility(View.GONE);
                                }
                                MainActivity.setProductsRecycler(response.body());
                            }

                            @Override
                            public void onFailure(@NotNull Call<List<Products>> call, @NotNull Throwable t) {
                                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                                holder.progressBar.setVisibility(View.GONE);

                            }
                        });
                        notifyDataSetChanged();
                        break;
                }

            }
        });
        String url = postsList.get(position).getImage();
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
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                holder.progressBar.setVisibility(View.GONE);

            }
        });
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }


    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView catName;
        ImageView catImage;
        ProgressBar progressBar;
        CardView cardView;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            catName=itemView.findViewById(R.id.cat_name);
            catImage=itemView.findViewById(R.id.cat_imageView);
            progressBar = itemView.findViewById(R.id.progressBar4);
            cardView = itemView.findViewById(R.id.cat_cardview);
        }
    }
}