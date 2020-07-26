package com.fluper.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fluper.R;
import com.fluper.data.DataBaseHelper;
import com.fluper.view_models.ProductModel;

public class ProductDetails extends AppCompatActivity implements View.OnClickListener {

    DataBaseHelper dataBaseHelper=null;
    TextView product_name,sale_price_tv,regular_price_tv,description,del_prod;
    int Prod_id=0;
    ImageView product_image,update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent()!= null){
            Prod_id = getIntent().getIntExtra("prod_id",0);
        }
        setContentView(R.layout.activity_product_details);
        dataBaseHelper=new DataBaseHelper(this);
        SetupUI();
        GetDataFromDb();
}
    private void GetDataFromDb() {
        Cursor cursor = dataBaseHelper.getproductListById(Prod_id);
        if (cursor!=null){
            if (cursor.moveToFirst()) {
                do {
                    product_name.setText(cursor.getString(cursor.getColumnIndex(ProductModel.NAME)));
                    sale_price_tv.setText("Sale Price : "+cursor.getString(cursor.getColumnIndex(ProductModel.SALE_PRICE)));
                    regular_price_tv.setText("Regular Price : "+cursor.getString(cursor.getColumnIndex(ProductModel.REGULAR_PRICE)));
                    description.setText(cursor.getString(cursor.getColumnIndex(ProductModel.DESCRIPTION)));
                    String image = cursor.getString(cursor.getColumnIndex(ProductModel.PRODUCT_PHOTO));
                    final int resourceId = getResources().getIdentifier(image, "drawable",
                            getPackageName());
                    Drawable drawable = getResources().getDrawable(resourceId);
                    product_image.setImageDrawable(drawable);

                } while (cursor.moveToNext());
            }
        }
    }
    private void SetupUI() {
        product_name = findViewById(R.id.product_name);
        sale_price_tv = findViewById(R.id.sale_price_tv);
        regular_price_tv = findViewById(R.id.regular_price_tv);
        description = findViewById(R.id.description);
        product_image = findViewById(R.id.product_image);
        update= findViewById(R.id.update);
        del_prod= findViewById(R.id.del_prod);
        del_prod.setOnClickListener(this);
        update.setOnClickListener(this);
    }
    public void back_func(View view) {
        finish();
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.del_prod:
                dataBaseHelper.deletedata(Prod_id);
                Toast.makeText(this,"Product Deleted.",Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.update:
                UpdateProd();
                break;
        }
    }
    public void UpdateProd() {
        // Create custom dialog object
        final Dialog dialog = new Dialog(ProductDetails.this);
        // Include dialog.xml file
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.update_prod_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final EditText prod_name_et = dialog.findViewById(R.id.prod_name_et);
        prod_name_et.setText(product_name.getText().toString());
        ImageView cancel = dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        TextView update_tv = dialog.findViewById(R.id.update_tv);
        update_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (prod_name_et.getText().length()>0) {
                    dialog.cancel();
                    dataBaseHelper.UpdateDb(prod_name_et.getText().toString());
                    Toast.makeText(ProductDetails.this, "Product Updated.", Toast.LENGTH_SHORT).show();
                    GetDataFromDb();
                }else {
                    Toast.makeText(ProductDetails.this, "Enter Product Name", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }
}