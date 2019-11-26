package ch.vracapps.splashscreen.CalendarActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.applandeo.materialcalendarview.EventDay;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import ch.vracapps.splashscreen.ApiInterface;
import ch.vracapps.splashscreen.R;
import ch.vracapps.splashscreen.model.Edeman_Classes.Recipe;
import ch.vracapps.splashscreen.model.MyEventDay;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class NotePreviewActivity extends AppCompatActivity{

    private Recipe recipe;
    private TextView note;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_preview);

        intent = getIntent();

        note = findViewById(R.id.note);

        if (intent != null) {
            Object event = intent.getParcelableExtra(CalendarActivity.EVENT);

            if(event instanceof MyEventDay){
                MyEventDay myEventDay = (MyEventDay)event;
                //getSupportActionBar().setTitle(getFormattedDate(myEventDay.getCalendar().getTime())); //Change la bar du haut avec la date de l'evenement
                //note.setText(myEventDay.getNote());
                //getResponse(myEventDay.getRecipe().getUri());
                note.setText(myEventDay.getRecipe().getLabel());
                return;
            }

            if(event instanceof EventDay){
                EventDay eventDay = (EventDay)event;
                getSupportActionBar().setTitle(getFormattedDate(eventDay.getCalendar().getTime()));
            }
        }
    }

    public static String getFormattedDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        return simpleDateFormat.format(date);
    }

    private void getResponse(String strUri){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.JSONURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);

        Call<String> call = api.getRecipe(strUri);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("Response String", response.body());
                if(response.isSuccessful()){
                    if(response.body() !=null){
                        Log.i("Success", response.body());

                        String strJsonResponse = response.body();
                        recipe = getRecipe(strJsonResponse);
                        note.setText(recipe.getLabel());

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

    private Recipe getRecipe (String strJsonResponse){
        try {
            //getting the whole json object from the response
            strJsonResponse = "{\"recipe\" : "+strJsonResponse+"}";
            JSONObject obj = new JSONObject(strJsonResponse);

            ArrayList<Recipe> recipeArrayList = new ArrayList<>();
            JSONArray dataArray  = obj.getJSONArray("recipe");

                Recipe recipe = new Recipe();

                JSONObject dataobj = dataArray.getJSONObject(0);
                ObjectMapper mapper = new ObjectMapper();
                String strJson = new String(obj.toString());

                //init object recipe with json data
                recipe.setLabel(dataobj.getString("label"));
                recipe.setImage(dataobj.getString("image"));
                recipe.setUri(dataobj.getString("uri"));
                return recipe;
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to encode a string value using `UTF-8` encoding scheme
    private static String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }
}
