package ch.vracapps.splashscreen;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import ch.vracapps.splashscreen.CalendarActivity.CalendarActivity;
import ch.vracapps.splashscreen.MapsActivity.MapsActivity;
import ch.vracapps.splashscreen.ShoppingListActivity.ShoppingListActivity;

public class MainActivity extends AppCompatActivity {

    private LinearLayout ll_fruits;
    private LinearLayout ll_about;
    private LinearLayout ll_maps;
    private LinearLayout ll_calendar;
    private LinearLayout ll_lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ll_fruits = (LinearLayout) findViewById(R.id.ll_fruits);
        ll_fruits.setOnClickListener(new LinearLayout.OnClickListener(){

            public void onClick (View v){
                Intent intent = new Intent(v.getContext(),FruitsActivity.class);
                startActivityForResult(intent,0);
            }
        });

        ll_about = (LinearLayout) findViewById(R.id.ll_about);
        ll_about.setOnClickListener(new LinearLayout.OnClickListener(){

            public void onClick (View v){
                Intent intent = new Intent(v.getContext(),AboutActivity.class);
                startActivityForResult(intent,0);
            }
        });

        ll_maps = (LinearLayout) findViewById(R.id.ll_maps);
        ll_maps.setOnClickListener(new LinearLayout.OnClickListener(){

            public void onClick (View v){
                Intent intent = new Intent(v.getContext(), MapsActivity.class);
                startActivityForResult(intent,0);
            }
        });

        ll_calendar = (LinearLayout) findViewById(R.id.ll_plan);
        ll_calendar.setOnClickListener(new LinearLayout.OnClickListener(){

            public void onClick (View v){
                Intent intent = new Intent(v.getContext(), CalendarActivity.class);
                startActivityForResult(intent,0);
            }
        });

        ll_lists = (LinearLayout) findViewById(R.id.ll_lists);
        ll_lists.setOnClickListener(new LinearLayout.OnClickListener(){

            public void onClick (View v){
                Intent intent = new Intent(v.getContext(), ShoppingListActivity.class);
                startActivityForResult(intent,0);
            }
        });

    }
}
