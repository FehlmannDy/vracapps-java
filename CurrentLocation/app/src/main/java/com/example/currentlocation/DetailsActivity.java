package com.example.currentlocation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    private String strName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView tvName = findViewById(R.id.tvName);

        Bundle extras = getIntent().getExtras();
        if(extras !=null){
           strName = extras.getString("nameMagasins");
        }

        tvName.setText(strName);
    }
}
