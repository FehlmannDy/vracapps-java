package ch.vracapps.splashscreen.RecipeActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;

import ch.vracapps.splashscreen.CalendarActivity.CalendarActivity;
import ch.vracapps.splashscreen.R;
import ch.vracapps.splashscreen.model.Edeman_Classes.Recipe;
import ch.vracapps.splashscreen.database.DatabaseHelper;

public class RecipeActivity extends AppCompatActivity {

    private Recipe recipe;
    private TextView tvRecipeName;
    private ImageView ivRecipeImage;
    private String[] listItems;
    private Date date;
    private DatabaseHelper mDBHelper;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        ivRecipeImage = (ImageView) findViewById(R.id.recipeImage);
        tvRecipeName = (TextView) findViewById(R.id.recipeLabel);

        listItems = getResources().getStringArray(R.array.RecipeTime);

        extras = getIntent().getExtras();
        if(extras != null){
            recipe = extras.getParcelable("recipe");
            this.date = (Date)getIntent().getSerializableExtra("dateFinal");
            tvRecipeName.setText(recipe.getLabel());
            Picasso.get().load(recipe.getImage()).into(ivRecipeImage);
        }


        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButtonAdd);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(RecipeActivity.this);
                mBuilder.setTitle("Choose an item");
                mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getCalendarActivity(recipe,listItems[i],i,date);
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
    }

    private void getCalendarActivity (Recipe recipe,String time,int index,Date date){
        Calendar cal = Calendar.getInstance();

        switch (index){
            case 0: cal.setTime(date);
                cal.set(Calendar.HOUR,7);
                break;
            case 1: cal.setTime(date);
                cal.set(Calendar.HOUR,10);
                break;
            case 2: cal.setTime(date);
                cal.set(Calendar.HOUR,12);
                break;
            case 3: cal.setTime(date);
                cal.set(Calendar.HOUR,16);
                break;
            case 4:cal.setTime(date);
                cal.set(Calendar.HOUR,19);
                break;
        }
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        date=cal.getTime();

        Toast.makeText(getApplicationContext(),recipe.getLabel() +"|"+time+"|"+date.toString(),Toast.LENGTH_LONG).show();

        initDatabase(recipe,date);

        Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
        startActivity(intent);
    }

    private boolean initDatabase(Recipe recipe , Date date){
        mDBHelper = new DatabaseHelper(this);
        //Check exists database
        File database = getApplicationContext().getDatabasePath(DatabaseHelper.DBNAME);
        if(false == database.exists()){
            mDBHelper.getReadableDatabase();
            //Copy db
            if(copyDatabase(this)){
                Log.d("MapsActivity","initListShopMaps:Copy database success");

            }else{
                Log.d("MapsActivity","initListShopMaps:Copy data error");
                return false;
            }
        }
        //Get Shop List in db exist
        return mDBHelper.setInsertRecipe(recipe,date);
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
}
