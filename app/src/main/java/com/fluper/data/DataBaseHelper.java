package com.fluper.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.fluper.view_models.ProductModel;
import java.io.File;
import static com.fluper.view_models.ProductModel.SQL_TABLE_PRODUCT;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static String TAG = "DataBaseHelper";
    public static String DB_NAME ="fluper.db"; // Database name
    public static int DB_VERSION = 1; // Database version
    public final File DB_FILE;
    public SQLiteDatabase mDataBase;
    public final Context mContext;

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        DB_FILE = context.getDatabasePath(DB_NAME);
        this.mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // create product table
        sqLiteDatabase.execSQL(SQL_TABLE_PRODUCT);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        DB_VERSION=DB_VERSION+1;
        onCreate(mDataBase);
    }
    @Override
    public synchronized void close() {
        if(mDataBase != null) {
            mDataBase.close();
        }
        super.close();
    }
    public void CreateProduct(ProductModel productModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(productModel.NAME, productModel.getName());
        values.put(productModel.DESCRIPTION, productModel.getDescription());
        values.put(productModel.PRODUCT_PHOTO,productModel.getproduct_photo());
        values.put(productModel.SALE_PRICE,productModel.getsale_price());
        values.put(productModel.REGULAR_PRICE,productModel.getregular_price());
        Log.e("VALUES",values+"---");
        // Inserting Row
        db.insert(productModel.TABLE_PRODUCT, null, values);
        db.close(); // Closing database connection
    }
    public Cursor getproductList(){
        String query = "SELECT * FROM product ORDER by id DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }
    public Cursor getproductListById(int id){
        String query = "SELECT * FROM product WHERE id = " +id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }
    public void deletedata(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ ProductModel.TABLE_PRODUCT+ " WHERE " + ProductModel.ID +" = " +id);
    }

    public void UpdateDb(String prod_name) {

      //  String query = "UPDATE listParticipant SET isLocalUpdate = 1 "' WHERE todoListID = " +todoID";
        String query = "UPDATE "+ ProductModel.TABLE_PRODUCT +" SET "+ ProductModel.NAME + " = '" + prod_name + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(query);
    }

}

