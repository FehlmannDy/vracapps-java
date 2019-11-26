package com.example.apirequest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.apirequest.Edeman_Classes.Recipe;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

//Gradle | implementation 'com.squareup.retrofit2:retrofit:2.5.0'
//Gradle | implementation 'com.squareup.retrofit2:converter-scalars:2.5.0'


public class MainActivity extends AppCompatActivity {

    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.tv);

        getResponse();
    }

    private void getResponse() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.JSONURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);

        Call<String> call = api.getString();

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

    private void writeTv(String response){

        try {
            //getting the whole json object from the response
            JSONObject obj = new JSONObject(response);

                //ArrayList<RetroModel> retroModelArrayList = new ArrayList<>();
                ArrayList<Recipe> recipeArrayList = new ArrayList<>();
                //JSONArray dataArray  = obj.getJSONArray("data");
            JSONArray dataArray  = obj.getJSONArray("hits");

                for (int i = 0; i < dataArray.length(); i++) {

                    //RetroModel retroModel = new RetroModel();
                    Recipe recipe = new Recipe();

                    JSONObject dataobj = dataArray.getJSONObject(i);

                    ObjectMapper mapper = new ObjectMapper();
                    String strJson = new String(dataobj.toString());

                    /*retroModel.setid(dataobj.getString("id"));
                    retroModel.setName(dataobj.getString("name"));
                    retroModel.setCountry(dataobj.getString("country"));
                    retroModel.setCity(dataobj.getString("city"));

                    retroModelArrayList.add(retroModel);
                    */


                    recipe.setLabel(dataobj.getJSONObject("recipe").getString("label"));//Cherche dans l'objet recipe  l'objet label
                    recipeArrayList.add(recipe);

                }

                /*for (int j = 0; j < retroModelArrayList.size(); j++){
                    textView.setText(textView.getText()+ retroModelArrayList.get(j).getid()+ " "+ retroModelArrayList.get(j).getName()
                            + " "+ retroModelArrayList.get(j).getCity() + " "+retroModelArrayList.get(j).getCountry()+" \n");
                }
                */
                for (int j = 0; j < recipeArrayList.size(); j++){
                    textView.setText(textView.getText()+ recipeArrayList.get(j).getLabel()+" \n");
                }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
