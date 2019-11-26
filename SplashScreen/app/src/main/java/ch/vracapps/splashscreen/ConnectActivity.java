package ch.vracapps.splashscreen;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class ConnectActivity extends AppCompatActivity {

    private LinearLayout ll_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        //Button home page
        ll_home = (LinearLayout) findViewById(R.id.ll_home);
        ll_home.setOnClickListener(new LinearLayout.OnClickListener(){

            public void onClick (View v){
                Intent intent = new Intent(v.getContext(),MainActivity.class);
                startActivityForResult(intent,0);
            }
        });
    }
}
