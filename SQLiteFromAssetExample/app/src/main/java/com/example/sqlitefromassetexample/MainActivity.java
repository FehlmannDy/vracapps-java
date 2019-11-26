package com.example.sqlitefromassetexample;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sqlitefromassetexample.adapter.ListProductAdapter;
import com.example.sqlitefromassetexample.database.DatabaseHelper;
import com.example.sqlitefromassetexample.model.Product;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public ListView lvProduct;
    private ListProductAdapter adapter;
    private List<Product> mPruductList;
    private DatabaseHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        lvProduct= (ListView)findViewById(R.id.listview_product);
        mDBHelper = new DatabaseHelper(this);

        //Check exists database
        File database = getApplicationContext().getDatabasePath(DatabaseHelper.DBNAME);
        if(false == database.exists()){
            mDBHelper.getReadableDatabase();
            //Copy db
            if(copyDatabase(this)){
                Toast.makeText(this,"Copy database succes",Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(this,"Copy data error",Toast.LENGTH_SHORT).show();
                return;
            }
        }
        //Get product List in db exist
        mPruductList = mDBHelper.getListProduct();
        //Init adapter
        adapter = new ListProductAdapter(this,mPruductList);
        //Set adapter for listview
        lvProduct.setAdapter(adapter);

    }

    private boolean copyDatabase(Context context){
        try{
            InputStream inputStream = context.getAssets().open(DatabaseHelper.DBNAME);
            String outFileName = DatabaseHelper.DBLOCATION + DatabaseHelper.DBNAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int lenght = 0;
            while ((lenght= inputStream.read(buff))>0){
                outputStream.write(buff, 0 ,lenght);
            }
            outputStream.flush();
            outputStream.close();
            Log.w("MainActivity", "DB copied");
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
