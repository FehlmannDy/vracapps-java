package ch.vracapps.splashscreen.ShoppingListActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

import ch.vracapps.splashscreen.R;
import ch.vracapps.splashscreen.database.DatabaseHelper;
import ch.vracapps.splashscreen.model.Edeman_Classes.Ingredient;

public class ShoppingListActivity extends AppCompatActivity {

    private static DecimalFormat decimalFormat = new DecimalFormat("#.##g");


    private DatabaseHelper mDBHelper;
    private ArrayList<Ingredient> mIngredient;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        mIngredient=initDatabaseIngredient();

        gridView=findViewById(R.id.gridviewShoppingList);

        //Init gridlayout
        CustomAdapter customAdapter = new CustomAdapter();
        gridView.setAdapter(customAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),mIngredient.get(i).getFood(),Toast.LENGTH_LONG).show();
                //Do something
                setShoppingList(mIngredient.get(i));
                Intent intent = getIntent();
                finish();
                startActivity(intent);

            }
        });
    }


    private ArrayList<Ingredient> initDatabaseIngredient(){
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
        return mDBHelper.getShoppingList();
    }

    private void setShoppingList(Ingredient ingredient){
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
            }
        }
        //set ingredient List in db to true
        mDBHelper.setShoppingList(ingredient);
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
            return mIngredient.size();
        }

        @Override
        public Object getItem(int i) {
            return mIngredient.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View viewRecipeRowData = getLayoutInflater().inflate(R.layout.shopping_list_row_data,null);
            //getting view in row_data
            TextView tvIngredienrName = viewRecipeRowData.findViewById(R.id.IngredientName);
            TextView tvIngredientTotal = viewRecipeRowData.findViewById(R.id.IngredientTotal);
            TextView tvIngredientGrammes = viewRecipeRowData.findViewById(R.id.IngredientGrammes);


            tvIngredienrName.setText(StringUtils.capitalize(mIngredient.get(i).getFood()));
            tvIngredientTotal.setText(mIngredient.get(i).getQuantity().toString());
            tvIngredientGrammes.setText(decimalFormat.format(mIngredient.get(i).getWeight()));
            return viewRecipeRowData;
        }
    }

}
