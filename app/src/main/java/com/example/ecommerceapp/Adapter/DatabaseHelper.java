package com.example.ecommerceapp.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ECommerceDB";
    private static final int DATABASE_VERSION = 2; // Increment version
    public static final String TABLE_PRODUCTS = "products";
    public static final String TABLE_PRODUCTS_FAVORITE = "favorite";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE products (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "size TEXT, " +
                "image BLOB, " +
                "rating TEXT, " +
                "ratingCount TEXT, " +
                "price TEXT)";
        db.execSQL(createTableQuery);

        String createTableQuery2 = "CREATE TABLE favorite (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "size TEXT, " +
                "image BLOB, " +
                "rating TEXT, " +
                "ratingCount TEXT, " +
                "price TEXT)";
        db.execSQL(createTableQuery2);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("ALTER TABLE products ADD COLUMN image BLOB");
            db.execSQL("ALTER TABLE favorite ADD COLUMN image BLOB");
        }
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS_FAVORITE);
        onCreate(db);
    }

    public boolean insertFavoriteProduct(byte[] image, String name, String size, String rating, String ratingCount, String price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("image", image);
        contentValues.put("name", name);
        contentValues.put("size", size);
        contentValues.put("rating", rating);
        contentValues.put("ratingCount", ratingCount);
        contentValues.put("price", price);

        long result = db.insert(TABLE_PRODUCTS_FAVORITE, null, contentValues);
        db.close();
        return result != -1;
    }
    public boolean insertProduct(byte[] image, String name, String size, String rating, String ratingCount, String price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("image", image);
        contentValues.put("name", name);
        contentValues.put("size", size);
        contentValues.put("rating", rating);
        contentValues.put("ratingCount", ratingCount);
        contentValues.put("price", price);

        long result = db.insert(TABLE_PRODUCTS, null, contentValues);
        db.close();
        return result != -1;
    }

    public void deleteProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }
    public void deleteProduct_favorite(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS_FAVORITE, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public Cursor viewData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS, null);
    }
    public Cursor viewFavoriteData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+TABLE_PRODUCTS_FAVORITE, null);
    }

    public Bitmap getBitmapFromBytes(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

}
