package ch.vracapps.splashscreen.RecipeActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.vracapps.splashscreen.ApiInterface;
import ch.vracapps.splashscreen.R;
import ch.vracapps.splashscreen.model.Edeman_Classes.Digest;
import ch.vracapps.splashscreen.model.Edeman_Classes.Ingredient;
import ch.vracapps.splashscreen.model.Edeman_Classes.Recipe;
import ch.vracapps.splashscreen.model.Edeman_Classes.Sub;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;



public class RecipeSearchList extends AppCompatActivity {

    private GridView gridView;
    private ArrayList<Recipe> mRecipe;
    private String strDiet;
    private Date date;
    private Bundle extras;
    private EditText etSearch;
    private ImageView ivSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_search_list);

        gridView = findViewById(R.id.gvRecipeSearchList);
        etSearch = findViewById(R.id.etSearch);

        extras = getIntent().getExtras();
        if(extras != null){
            strDiet = extras.getString("diet");
            this.date = (Date)getIntent().getSerializableExtra("date");
        }

        ivSearch = findViewById(R.id.ivSearch);
        ivSearch.setOnClickListener(new ImageView.OnClickListener(){
            public void onClick (View v){
                if(etSearch.getText().toString()!=null){
                    getResponseRecipe(etSearch.getText().toString());
                }
            }
        });

        //Active event when the user is in the end of gridview
        gridView.setOnScrollListener(new AbsListView.OnScrollListener(){
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {
                if(firstVisibleItem + visibleItemCount >= totalItemCount){
                    // End has been reached
                    Log.d("onScroll","the end of grid view");
                }
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState){

            }
        });

        getResponse();
    }

    private Call<String> getRecipeRequest(String recipe){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.JSONURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);

        return api.getRecipes(recipe);
    }

    private Call<String> getDietRequest(String diet){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.JSONURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);

        return api.getDiet(diet.toLowerCase(),30);
    }

    private void getResponse(){

        Call<String> call = getDietRequest(strDiet);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("Response String", response.body().toString());
                if(response.isSuccessful()){
                    if(response.body() !=null){
                        Log.i("Success", response.body().toString());

                        String strJsonResponse = response.body().toString();
                        mRecipe = getRecipe(strJsonResponse);

                        //Init gridlayout
                        RecipeSearchList.CustomAdapter customAdapter = new RecipeSearchList.CustomAdapter();
                        gridView.setAdapter(customAdapter);
                        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Toast.makeText(getApplicationContext(),mRecipe.get(i).getLabel(),Toast.LENGTH_LONG).show();
                                //set new activity
                                Intent intent = new Intent(getApplicationContext(),RecipeActivity.class);
                                intent.putExtra("recipe",mRecipe.get(i));
                                intent.putExtra("dateFinal",date);
                                startActivity(intent);
                            }
                        });
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

    private void getResponseRecipe(String str){

        Call<String> call = getRecipeRequest(str);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("Response String", response.body().toString());
                if(response.isSuccessful()){
                    if(response.body() !=null){
                        Log.i("Success", response.body().toString());

                        String strJsonResponse = response.body().toString();
                        mRecipe = getRecipe(strJsonResponse);

                        //Init gridlayout
                        RecipeSearchList.CustomAdapter customAdapter = new RecipeSearchList.CustomAdapter();
                        gridView.setAdapter(customAdapter);
                        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Toast.makeText(getApplicationContext(),mRecipe.get(i).getLabel(),Toast.LENGTH_LONG).show();
                                //set new activity
                                Intent intent = new Intent(getApplicationContext(),RecipeActivity.class);
                                intent.putExtra("recipe",mRecipe.get(i));
                                intent.putExtra("dateFinal",date);
                                startActivity(intent);
                            }
                        });
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

    private ArrayList<Recipe> getRecipe (String strJsonResponse){
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

                //init object recipe with json data
                recipe.setUri(dataobj.getJSONObject("recipe").getString("uri"));
                recipe.setLabel(dataobj.getJSONObject("recipe").getString("label"));
                recipe.setImage(dataobj.getJSONObject("recipe").getString("image"));
                recipe.setSource(dataobj.getJSONObject("recipe").getString("source"));
                recipe.setUrl(dataobj.getJSONObject("recipe").getString("url"));
                recipe.setShareAs(dataobj.getJSONObject("recipe").getString("shareAs"));
                recipe.setYield(dataobj.getJSONObject("recipe").getDouble("yield"));
                recipe.setDietLabels(getListString(dataobj.getJSONObject("recipe").getJSONArray("dietLabels")));
                recipe.setHealthLabels(getListString(dataobj.getJSONObject("recipe").getJSONArray("healthLabels")));
                //recipe.setCautions(getListString(dataobj.getJSONObject("recipe").getJSONArray("cautions")));
                recipe.setIngredientLines(getListString(dataobj.getJSONObject("recipe").getJSONArray("ingredientLines")));
                recipe.setIngredients(getListIngredient(dataobj.getJSONObject("recipe").getJSONArray("ingredients")));
                recipe.setCalories(dataobj.getJSONObject("recipe").getDouble("calories"));
                recipe.setTotalWeight(dataobj.getJSONObject("recipe").getDouble("totalWeight"));
                recipe.setDigest(getListDigest(dataobj.getJSONObject("recipe").getJSONArray("digest")));
                recipeArrayList.add(recipe);

            }
            return recipeArrayList;
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<String> getListString(JSONArray jsonArray){
        try {
            List<String> stringList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                stringList.add(jsonArray.getString(i));
            }
            return stringList;
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    private List<Ingredient> getListIngredient(JSONArray jsonArray){
        try {
            List<Ingredient> ingredientList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                Ingredient ingredient = new Ingredient();
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                ingredient.setText(jsonObject.getString("text"));
                ingredient.setQuantity(jsonObject.getDouble("quantity"));
                ingredient.setMeasure(jsonObject.getString("measure"));
                ingredient.setFood(jsonObject.getString("food"));
                ingredient.setWeight(jsonObject.getDouble("weight"));
                ingredientList.add(ingredient);
            }
            return ingredientList;
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    private List<Digest> getListDigest(JSONArray jsonArray) { //TODO revoir parce-que null
        try {
            List<Digest> digestList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                Digest digest = new Digest();
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                digest.setLabel(jsonObject.getString("label"));
                digest.setTag(jsonObject.getString("tag"));
                digest.setSchemaOrgTag(jsonObject.get("schemaOrgTag"));
                digest.setTotal(jsonObject.getDouble("total"));
                digest.setHasRDI(jsonObject.getBoolean("hasRDI"));
                digest.setDaily(jsonObject.getDouble("daily"));
                digest.setUnit(jsonObject.getString("unit"));
                digest.setSub(getListSub(jsonObject.getJSONArray("sub")));
                digestList.add(digest);
            }
            return digestList;
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    private List<Sub> getListSub(JSONArray jsonArray){ //TODO revoir parce-que null
        try {
            List<Sub> subList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                Sub sub = new Sub();
                JSONObject jsonObjectSub = jsonArray.getJSONObject(i);
                sub.setLabel(jsonObjectSub.getString("label"));
                sub.setTag(jsonObjectSub.getString("tag"));
                sub.setSchemaOrgTag(jsonObjectSub.get("schemaOrgTag"));
                sub.setTotal(jsonObjectSub.getDouble("total"));
                sub.setHasRDI(jsonObjectSub.getBoolean("hasRDI"));
                sub.setDaily(jsonObjectSub.getDouble("daily"));
                sub.setUnit(jsonObjectSub.getString("unit"));
                subList.add(sub);
            }
            return subList;
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    //Grid layout
    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mRecipe.size();
        }

        @Override
        public Object getItem(int i) {
            return mRecipe.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View viewRecipeRowData = getLayoutInflater().inflate(R.layout.recipe_row_data,null);
            //getting view in row_data
            TextView tvRecipeName = viewRecipeRowData.findViewById(R.id.recipeName);
            ImageView ivRecipeImage = viewRecipeRowData.findViewById(R.id.recipeImage);

            tvRecipeName.setText(mRecipe.get(i).getLabel());
            Picasso.get().load(mRecipe.get(i).getImage()).into(ivRecipeImage);
            return viewRecipeRowData;
        }
    }
}
