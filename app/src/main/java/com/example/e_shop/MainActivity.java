package com.example.e_shop;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.example.e_shop.adapter.CategoryAdapter;
import com.example.e_shop.adapter.ProductsAdapter;
import com.example.e_shop.api.ApiInterface;
import com.example.e_shop.model.ProductCategory;
import com.example.e_shop.model.Products;
import com.google.firebase.auth.FirebaseAuth;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    CategoryAdapter CategoryAdapter;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    String[] emoj = new String[]{"Welcome 😎😍", "😚🥳🛒", "🎃🎉✨", "🤩😉🤗", "🤑🤑🤑"};
    FirebaseAuth mAuth;
    RecyclerView productCategoryRV;
    static RecyclerView productsRV;
    static ProductsAdapter productsAdapter;
    List<ProductCategory> productsCategoriesList = new ArrayList();
    public static List<Products> productsList = new ArrayList();
    ImageButton gridBTN;
    ImageView feelingsImageView, menuImageView;
    LottieAnimationView lottieAnimationView;
    Boolean isGrid = false;
    static Context context;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        gridBTN.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGrid) {
                    gridBTN.setImageResource(R.drawable.ic_grid);
                    productsRV.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
                    productsAdapter = new ProductsAdapter(context, productsList);
                    productsRV.setAdapter(productsAdapter);
                    isGrid = false;
                } else {
                    gridBTN.setImageResource(R.drawable.ic_list);
                    productsRV.setLayoutManager(new GridLayoutManager(context, 2));
                    productsAdapter = new ProductsAdapter(context, productsList);
                    productsRV.setAdapter(productsAdapter);
                    isGrid = true;
                }
            }
        });
        productsRV = findViewById(R.id.product_rv);
        context = this;
        menuImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        gridBTN.setVisibility(View.GONE);
        gridBTN.setImageResource(R.drawable.ic_grid);


        feelingsImageView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MainActivity.this.getApplicationContext(), MainActivity.this.emoj[new Random().nextInt(MainActivity.this.emoj.length)], Toast.LENGTH_SHORT).show();
            }
        });
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://fakestoreapi.com/").addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        final Call<List<Products>> productsCall = apiInterface.getAllProducts();
        productsCall.enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(@NotNull Call<List<Products>> call, @NotNull Response<List<Products>> response) {
                if (lottieAnimationView != null) {

                    lottieAnimationView.setVisibility(View.GONE);
                    gridBTN.setVisibility(View.VISIBLE);
                }
                productsList = response.body();
                setProductsRecycler(productsList);

            }

            @Override
            public void onFailure(@NotNull Call<List<Products>> call, @NotNull Throwable t) {
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        final Call<List<String>> productsCategoriesCall = apiInterface.getAllProductsCategories();
        productsCategoriesCall.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(@NotNull Call<List<String>> call, @NotNull Response<List<String>> response) {
                assert response.body() != null;
                productsCategoriesList.add(new ProductCategory(response.body().get(0),
                        "https://pngimg.com/uploads/computer_pc/computer_pc_PNG17486.png"));
                productsCategoriesList.add(new ProductCategory(response.body().get(1),
                        "https://pngimg.com/uploads/jewelry/jewelry_PNG6792.png"));
                productsCategoriesList.add(new ProductCategory(response.body().get(2),
                        "https://pngimg.com/uploads/jacket/jacket_PNG8043.png"));
                productsCategoriesList.add(new ProductCategory(response.body().get(3),
                        "https://pngimg.com/uploads/dress/dress_PNG147.png"));

                setProductCategoryAdapterRecycler((ArrayList<ProductCategory>) productsCategoriesList);
            }

            @Override
            public void onFailure(@NotNull Call<List<String>> call, @NotNull Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        {
            drawerLayout = findViewById(R.id.my_drawer_layout);
            mAuth = FirebaseAuth.getInstance();
            lottieAnimationView = findViewById(R.id.animationView);
            menuImageView = findViewById(R.id.menuImageView);
            feelingsImageView = findViewById(R.id.feelingsImageView);
            gridBTN = findViewById(R.id.gridBTN);
        }
    }

    private void setProductCategoryAdapterRecycler(ArrayList<ProductCategory> productCategoryList) {
        productCategoryRV = findViewById(R.id.cat_rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        productCategoryRV.setHasFixedSize(true);
        productCategoryRV.setLayoutManager(layoutManager);
        CategoryAdapter = new CategoryAdapter(this, productCategoryList);
        productCategoryRV.setAdapter(CategoryAdapter);
    }

    public static void setProductsRecycler(List<Products> productsList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        productsRV.setLayoutManager(layoutManager);
        productsAdapter = new ProductsAdapter(context, productsList);
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
        alertDialogBuilder.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                moveTaskToBack(true);
                Process.killProcess(Process.myPid());
                System.exit(1);
            }
        }).setNegativeButton(str, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        alertDialogBuilder.create().show();
    }

    public void Logoutt(MenuItem item) {
        mAuth.signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        drawerLayout.closeDrawers();
        finish();
    }

    public void gotoCart(MenuItem item) {
        startActivity(new Intent(getApplicationContext(), AccountActivity.class));
        drawerLayout.closeDrawers();
    }




}
