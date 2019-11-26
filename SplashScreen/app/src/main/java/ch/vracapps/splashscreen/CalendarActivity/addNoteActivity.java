package ch.vracapps.splashscreen.CalendarActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ch.vracapps.splashscreen.R;
import ch.vracapps.splashscreen.RecipeActivity.RecipeSearch;
import ch.vracapps.splashscreen.model.Edeman_Classes.Recipe;
import ch.vracapps.splashscreen.model.MyEventDay;

public class addNoteActivity extends AppCompatActivity {

    private static final Calendar myCalendar = Calendar.getInstance();
    private EditText datePicker;
    private EditText noteEditText;
    private Button addNoteButton;
    private Button addRecipeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        datePicker = (EditText) findViewById(R.id.datePicker);
        noteEditText = (EditText) findViewById(R.id.noteEditText);
        addNoteButton = (Button) findViewById(R.id.addNoteButton);
        addRecipeButton = (Button) findViewById(R.id.addRecipe);

        final Recipe recipe=new Recipe();

        dateNow();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDate();
            }
        };

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(addNoteActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();

                MyEventDay myEventDay = new MyEventDay(myCalendar,
                        R.drawable.ic_cercle_event, recipe);

                returnIntent.putExtra(CalendarActivity.RESULT, myEventDay);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RecipeSearch.class);
                intent.putExtra("date",myCalendar.getTime());
                startActivityForResult(intent,0);
            }
        });
    }

    private void dateNow(){
        String myFormat = "dd/MM/yy"; //Put format
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

        datePicker.setText(sdf.format(Calendar.getInstance().getTime()));

    }

    private void updateLabelDate() {
        String myFormat = "dd/MM/yy"; //Put format
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

        datePicker.setText(sdf.format(myCalendar.getTime()));
    }
}
