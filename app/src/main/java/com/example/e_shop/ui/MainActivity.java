package com.example.e_shop.ui;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.e_shop.R;
import com.example.e_shop.adapter.CategoriesAdapter;
import com.example.e_shop.adapter.ItemClickListener;
import com.example.e_shop.adapter.ProductsAdapter;
import com.example.e_shop.model.Product;
import com.example.e_shop.model.ProductCategory;
import com.example.e_shop.viewmodels.ProductsViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    CategoriesAdapter categoryAdapter;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ProductsViewModel viewModel;
    DrawerLayout drawerLayout;
    String[] emoji = new String[]{"Welcome 😎😍", "😚🥳🛒", "🎃🎉✨", "🤩😉🤗", "🤑🤑🤑"};
    FirebaseAuth mAuth;
    RecyclerView productCategoryRV;
    RecyclerView productsRV;
    ProductsAdapter productsAdapter;
    ArrayList<ProductCategory> categoriesList;
    ArrayList<Product> productsList;
    ImageButton gridBTN, menuImageButton;
    ImageView feelingsImageView, profilePic;
    TextView exploreTV;
    LottieAnimationView lottieAnimationView, lottieAnimationView1;
    Boolean isGrid = false;
    int flag = 0;
    Context context;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        Picasso.get().load(mAuth.getCurrentUser().getPhotoUrl()).placeholder(R.drawable.profilepicph).into(profilePic, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(Exception e) {
                Log.e("MainActivity: ", e.getMessage());
            }
        });

        gridBTN.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGrid) {
                    gridBTN.setImageResource(R.drawable.ic_grid);
                    productsRV.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
                    productsRV.setAdapter(productsAdapter);
                    isGrid = false;
                } else {
                    gridBTN.setImageResource(R.drawable.ic_list);
                    productsRV.setLayoutManager(new GridLayoutManager(context, 2));
                    productsRV.setAdapter(productsAdapter);
                    isGrid = true;
                }
            }
        });

        menuImageButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        gridBTN.setEnabled(false);
        gridBTN.setImageResource(R.drawable.ic_grid);
        feelingsImageView.setOnClickListener(v -> Toast.makeText(context,
                MainActivity.this.emoji[new Random().nextInt(MainActivity.this.emoji.length)],
                Toast.LENGTH_SHORT).show());
        productCategoryRV.addOnItemTouchListener(new ItemClickListener(this, productCategoryRV, new ItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {
                String tappedCategory = categoryAdapter.getItemAtPosition(position).getCategoryName();
                switch (tappedCategory) {
                    case "electronics":
                        viewModel.getElectronics();
                        viewModel.getElectronicsList().observe(MainActivity.this, new Observer<ArrayList<Product>>() {
                            @Override
                            public void onChanged(ArrayList<Product> products) {
                                productsList = products;
                                productsAdapter.setProductsList(productsList);
                            }
                        });
                        Log.d("TAG", "onItemClick: " + tappedCategory);
                        break;
                    case "jewelery":
                        viewModel.getJeweleries();
                        viewModel.getJeweleryList().observe(MainActivity.this, new Observer<ArrayList<Product>>() {
                            @Override
                            public void onChanged(ArrayList<Product> products) {
                                productsList = products;
                                productsAdapter.setProductsList(productsList);
                            }
                        });
                        Log.d("TAG", "onItemClick: " + tappedCategory);
                        break;
                    case "men's clothing":
                        viewModel.getMenClothing();
                        viewModel.getMenProductsList().observe(MainActivity.this, products -> {
                            productsList = products;
                            productsAdapter.setProductsList(productsList);
                        });
                        Log.d("TAG", "onItemClick: " + tappedCategory);

                        break;
                    case "women's clothing":
                        viewModel.getWomenClothing();
                        viewModel.getWomenProductsList().observe(MainActivity.this, products -> {
                            productsList = products;
                            productsAdapter.setProductsList(productsList);
                        });
                        Log.d("TAG", "onItemClick: " + tappedCategory);
                        break;
                    default:
                        viewModel.getProductsList().observe(MainActivity.this, products -> {
                            productsList = products;
                            productsAdapter.setProductsList(productsList);
                        });
                        Log.d("TAG", "onItemClick: " + tappedCategory);

                        break;
                }
                exploreTV.setText(tappedCategory);
                if (lottieAnimationView != null) {
                    lottieAnimationView.setVisibility(View.GONE);
                    gridBTN.setEnabled(true);
                }
            }
        }));

    }

    private void init() {
        drawerLayout = findViewById(R.id.my_drawer_layout);
        mAuth = FirebaseAuth.getInstance();
        lottieAnimationView = findViewById(R.id.animationView);
        lottieAnimationView1 = findViewById(R.id.animationView1);
        menuImageButton = findViewById(R.id.menuImageView);
        feelingsImageView = findViewById(R.id.feelingsImageView);
        exploreTV = findViewById(R.id.exploreTV);
        gridBTN = findViewById(R.id.gridBTN);
        profilePic = findViewById(R.id.profile_iv);
        lottieAnimationView.setAnimation(R.raw.load);
        lottieAnimationView1.setAnimation(R.raw.load);
        productsList = new ArrayList();
        categoriesList = new ArrayList();
        profilePic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAccount();
            }
        });
        context = this;
        setProductsRecycler();
        setProductCategoryAdapterRecycler();

        viewModel = new ViewModelProvider(this).get(ProductsViewModel.class);
        viewModel.getProducts();
        viewModel.getProductsList().observe(this, products -> {
            productsList = products;
            productsAdapter.setProductsList(productsList);
            if (lottieAnimationView != null) {
                lottieAnimationView.setVisibility(View.GONE);
                gridBTN.setEnabled(true);
            }
        });
        viewModel.getCategories();
        viewModel.getCategoriesList().observe(this, new Observer<ArrayList<ProductCategory>>() {
            @Override
            public void onChanged(ArrayList<ProductCategory> productCategories) {
                categoriesList = productCategories;
                categoryAdapter.setItemsList(categoriesList);
                if (lottieAnimationView1 != null) {
                    lottieAnimationView1.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setProductCategoryAdapterRecycler() {
        productCategoryRV = findViewById(R.id.cat_rv);
        productCategoryRV.setHasFixedSize(true);
        productCategoryRV.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        categoryAdapter = new CategoriesAdapter(this);
        productCategoryRV.setAdapter(categoryAdapter);

    }

    private void setProductsRecycler() {
        productsRV = findViewById(R.id.product_rv);
        productsRV.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        productsAdapter = new ProductsAdapter(this);
        productsRV.setAdapter(productsAdapter);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            drawerLayout.closeDrawers();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void onBackPressed() {
        drawerLayout.closeDrawers();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit Application?");
        String str = "No";
        alertDialogBuilder.setCancelable(false).setPositiveButton("Yes", (dialog, id) -> {
            moveTaskToBack(true);
            Process.killProcess(Process.myPid());
            System.exit(1);
        }).setNegativeButton(str, (dialog, id) -> dialog.cancel());
        alertDialogBuilder.create().show();
    }

    public void Logout(MenuItem item) {
        Toast.makeText(this, "Bye 👋", Toast.LENGTH_SHORT).show();

        mAuth.signOut();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        drawerLayout.closeDrawers();
        finish();
    }

    public void gotoCart(MenuItem item) {
        flag = 2;
        Intent intent = new Intent(MainActivity.this, AccountActivity.class);
        intent.putExtra("FLAG", flag);
        startActivity(intent);
        if (drawerLayout.isOpen()) {
            drawerLayout.closeDrawers();
        }

    }

    public void gotoAccount() {
        flag = 1;
        Intent intent = new Intent(MainActivity.this, AccountActivity.class);
        intent.putExtra("FLAG", flag);
        startActivity(intent);
    }

    public void gotoAccount(MenuItem item) {
        gotoAccount();
        if (drawerLayout.isOpen()) {
            drawerLayout.closeDrawers();
        }
    }


}
