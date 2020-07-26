package com.fluper.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.fluper.R;
import com.fluper.data.DataBaseHelper;
import com.fluper.view_models.ProductModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import static com.fluper.utils.Constant.NUM;

public class ProductActivity extends AppCompatActivity {
    // create json object
    JSONArray array =  null;
    DataBaseHelper dataBaseHelper=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        dataBaseHelper=new DataBaseHelper(this);
        try {
            array = new JSONArray(readJSONFromAsset());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    // call product list page
    public void Show_product_func(View view) {
        Intent intent = new Intent(this,ShowProductActivity.class);
        startActivity(intent);
    }
    // Read json data from asset
    public String readJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("product.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
   // call create production function
    public void Create_product_func(View view) {

        InsertOneProductInDb();
    }
    // inserting product data into db from json
    private void InsertOneProductInDb() {

        if (NUM>5){

            NUM = 0;
        }
        try {
            JSONObject object = array.getJSONObject(NUM);
            ProductModel model = new ProductModel();

            model.setName(object.getString(ProductModel.NAME));
            model.setDescription(object.getString(ProductModel.DESCRIPTION));
            model.setsale_price(object.getString(ProductModel.SALE_PRICE));
            model.setregular_price(object.getString(ProductModel.REGULAR_PRICE));
            model.setproduct_photo(object.getString(ProductModel.PRODUCT_PHOTO));

            dataBaseHelper.CreateProduct(model);
            Toast.makeText(this,"New Product Created.",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,ShowProductActivity.class);
            startActivity(intent);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        NUM++;
    }
}