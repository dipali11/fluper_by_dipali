package com.fluper.view_models;

public class ProductModel {

    // Creating database with model
    public static final String TABLE_PRODUCT = "product";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String PRODUCT_PHOTO = "product_photo";
    public static final String SALE_PRICE = "sale_price";
    public static final String REGULAR_PRICE = "regular_price";

    // model variables
    private int id;
    private String name;
    private String regular_price;
    private String sale_price;
    private String product_photo;
    private String description;

    //  Setter and getter methods  //
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getregular_price () {
        return regular_price ;
    }

    public void setregular_price (String regular_price ) {
        this.regular_price  = regular_price ;
    }

    public String getsale_price () {
        return sale_price ;
    }

    public void setsale_price (String sale_price ) {
        this.sale_price  = sale_price ;
    }

    public String getproduct_photo () {
        return product_photo ;
    }

    public void setproduct_photo (String product_photo ) {
        this.product_photo  = product_photo ;
    }

    public static final String SQL_TABLE_PRODUCT = " CREATE TABLE " + TABLE_PRODUCT
            + " ( "
            + ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + NAME + " varchar, "
            + DESCRIPTION + " varchar, "
            + PRODUCT_PHOTO + " varchar, "
            + SALE_PRICE + " varchar, "
            + REGULAR_PRICE + " varchar "
            + " ) ";
}
