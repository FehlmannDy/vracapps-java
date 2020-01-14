package ch.vracapps.splashscreen;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ch.vracapps.splashscreen.database.DatabaseHelper;
import ch.vracapps.splashscreen.model.FruitsVegetables;

public class FruitsActivity extends AppCompatActivity {

    private LinearLayout ll_home;
    private TextView tvMonth;
    private DatabaseHelper mDBHelper;
    private ArrayList<FruitsVegetables> mFruitsVegetables;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM");
    private String currentMonth = simpleDateFormat.format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruits);

        tvMonth = findViewById(R.id.tvMonth);
        tvMonth.setText(currentMonth);

        //Button home page
        ll_home = (LinearLayout) findViewById(R.id.ll_home);
        ll_home.setOnClickListener(new LinearLayout.OnClickListener(){

            public void onClick (View v){
                Intent intent = new Intent(v.getContext(),MainActivity.class);
                startActivityForResult(intent,0);
            }
        });
    }

    private ArrayList<FruitsVegetables> initDatabaseIngredient(){
        mDBHelper = new DatabaseHelper(this);
        //Check exists database
        File database = getApplicationContext().getDatabasePath(DatabaseHelper.DBNAME);
        if(false == database.exists()){
            mDBHelper.getReadableDatabase();
            //Copy db
            if(copyDatabase(this)){
                Log.d("MapsActivity","initListRecipe:Copy database success");

            }else{
                Log.d("MapsActivity","initListRecipe:Copy data error");
                return null;
            }
        }
        //Get Recipe List in db exist
        return mDBHelper.getFruitsVegatableList();
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
            Log.w("MapsActivity", "DB copied");
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //Grid layout
    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mFruitsVegetables.size();
        }

        @Override
        public Object getItem(int i) {
            return mFruitsVegetables.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View viewFruitsRowData = getLayoutInflater().inflate(R.layout.activity_fruits_row_data,null);
            //getting view in row_data
            TextView tvIngredienrName = viewFruitsRowData.findViewById(R.id.nameFruitsVegetable);
            ImageView ivFruitsVegatable = viewFruitsRowData.findViewById(R.id.ivFruitsVegetables);


            //set tv and iv

            return viewFruitsRowData;
        }
    }
}
