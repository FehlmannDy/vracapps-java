package ch.vracapps.splashscreen.RecipeActivity;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import ch.vracapps.splashscreen.ApiInterface;
import ch.vracapps.splashscreen.R;
import ch.vracapps.splashscreen.model.Edeman_Classes.Recipe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RecipeSearch extends AppCompatActivity {

    private GridView gridView;
    private String[] nameHeahtl = {"Vegan","Vegetarian","balanced","high-protein","low-fat","low-carb","sugar-conscious","peanut-free","tree-nut-free","alcohol-free"};
    private Date date;
    private Bundle extras;
    private CustomAdapter customAdapter = new CustomAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_search);

        gridView = findViewById(R.id.gridview);

        extras = getIntent().getExtras();
        if(extras != null){
            this.date = (Date)getIntent().getSerializableExtra("date");
        }

        getResponse();

        gridView.setAdapter(customAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),nameHeahtl[i],Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(),RecipeSearchList.class);
                intent.putExtra("diet",nameHeahtl[i]);
                intent.putExtra("date",date);
                startActivity(intent);
            }
        });


    }

    private void getResponse(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.JSONURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);

        Call<String> call = api.getVegeQuery();

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("Response String", response.body().toString());
                if(response.isSuccessful()){
                    if(response.body() !=null){
                        Log.i("Success", response.body().toString());

                        String strJsonResponse = response.body().toString();
                        writeTv(strJsonResponse);
                    }else{
                        Log.i("Empty Response", "Returned empty response");
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //onFailure
            }
        });
    }

    private void writeTv(String strJsonResponse) {
        try {
            //getting the whole json object from the response
            JSONObject obj = new JSONObject(strJsonResponse);

            ArrayList<Recipe> recipeArrayList = new ArrayList<>();
            JSONArray dataArray  = obj.getJSONArray("hits");

            for (int i = 0; i < dataArray.length(); i++) {

                Recipe recipe = new Recipe();

                JSONObject dataobj = dataArray.getJSONObject(i);

                ObjectMapper mapper = new ObjectMapper();
                String strJson = new String(dataobj.toString());

                recipe.setLabel(dataobj.getJSONObject("recipe").getString("label"));//Cherche dans l'objet recipe  l'objet label
                recipeArrayList.add(recipe);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //Grid layout
    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return nameHeahtl.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View viewRecipeMenuRowData = getLayoutInflater().inflate(R.layout.recipe_menu_row_data,null);
            //getting view in row_data
            TextView name = viewRecipeMenuRowData.findViewById(R.id.nameHealth);
            ImageView image = viewRecipeMenuRowData.findViewById(R.id.images);

            name.setText(nameHeahtl[i]);
            //image.setImageResource(fruitImages[i]);
            return viewRecipeMenuRowData;
        }
    }
}
