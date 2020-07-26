package com.fluper.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fluper.R;
import com.fluper.adapter.ProductAdapter;
import com.fluper.data.DataBaseHelper;
import com.fluper.view_models.ProductModel;
import java.util.ArrayList;

public class ShowProductActivity extends AppCompatActivity {

    RecyclerView product_rv;
    ProductAdapter productAdapter;
    private ArrayList<ProductModel> productlist = new ArrayList<>();
    DataBaseHelper dataBaseHelper=null;
    TextView no_prod_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);
        dataBaseHelper=new DataBaseHelper(this);
        // setting up UI
        SetupUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // getting data from db
        getDataFromDb();
    }
    // Initialize view ID
    private void SetupUI() {

        no_prod_text = findViewById(R.id.no_prod_text);
        product_rv = findViewById(R.id.product_rv);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,2);
        product_rv.setLayoutManager(mLayoutManager);
        product_rv.setItemAnimator(new DefaultItemAnimator());
        productAdapter = new ProductAdapter(this, productlist);
        product_rv.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();
    }
    // Getting product data from db
    private void getDataFromDb() {
        productlist.clear();
        Cursor cursor = dataBaseHelper.getproductList();
        if (cursor!=null){
            if (cursor.moveToFirst()) {
                do {
                    ProductModel model = new ProductModel();

                    model.setId(cursor.getInt(cursor.getColumnIndex(ProductModel.ID)));
                    model.setName(cursor.getString(cursor.getColumnIndex(ProductModel.NAME)));
                    model.setproduct_photo(cursor.getString(cursor.getColumnIndex(ProductModel.PRODUCT_PHOTO)));
                    model.setDescription(cursor.getString(cursor.getColumnIndex(ProductModel.DESCRIPTION)));
                    model.setsale_price(cursor.getString(cursor.getColumnIndex(ProductModel.SALE_PRICE)));
                    model.setregular_price(cursor.getString(cursor.getColumnIndex(ProductModel.REGULAR_PRICE)));

                    productlist.add(model);
                } while (cursor.moveToNext());
            }
            productAdapter.notifyDataSetChanged();
        }
        if (productlist.size()>0){
            no_prod_text.setVisibility(View.GONE);
        }else {
            no_prod_text.setVisibility(View.VISIBLE);
        }
    }

    public void OnbackClick(View view) {

        finish();
    }
}