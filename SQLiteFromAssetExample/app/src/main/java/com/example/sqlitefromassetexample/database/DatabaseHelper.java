package com.example.sqlitefromassetexample.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sqlitefromassetexample.model.Product;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "bdd_vracapps.sqlite";
    public static final String DBLOCATION = "/data/data/com.example.sqlitefromassetexample/databases/";

    private Context mContext;
    private SQLiteDatabase mDatabases;

    public DatabaseHelper(Context context){
        super(context,DBNAME,null,1);
        this.mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void openDatabase(){
        String dbPath = mContext.getDatabasePath(DBNAME).getPath();
        if(mDatabases !=null && mDatabases.isOpen()){
            return;
        }
        mDatabases = SQLiteDatabase.openDatabase(dbPath,null,SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase(){
        if(mDatabases!=null){
            mDatabases.close();
        }
    }

    public List<Product> getListProduct (){
        Product product = null;
        List<Product> productList = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabases.rawQuery("SELECT * FROM TBL_MAGASINS",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            product= new Product(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
            productList.add(product);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return productList;
    }

}
