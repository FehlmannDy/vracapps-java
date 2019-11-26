package ch.vracapps.splashscreen.CalendarActivity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ch.vracapps.splashscreen.MainActivity;
import ch.vracapps.splashscreen.R;
import ch.vracapps.splashscreen.database.DatabaseHelper;
import ch.vracapps.splashscreen.model.Edeman_Classes.Recipe;
import ch.vracapps.splashscreen.model.MyEventDay;

public class CalendarActivity extends AppCompatActivity {

    public static final String RESULT = "result";
    public static final String EVENT = "event";
    private static final int ADD_NOTE = 44;

    private CalendarView mCalendarView;
    private List<EventDay> mEventDays = new ArrayList<>();
    private LinearLayout ll_home;
    private FloatingActionButton floatingActionButton;
    private Recipe recipe;
    private Date date;
    private Calendar cal= Calendar.getInstance();
    private int indexTime;
    private DatabaseHelper mDBHelper;
    private ArrayList<Recipe> mRecipe=new ArrayList<>();
    private ArrayList<Date> mDate = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        //Button home page
        ll_home = (LinearLayout) findViewById(R.id.ll_home);
        ll_home.setOnClickListener(new LinearLayout.OnClickListener(){

            public void onClick (View v){
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivityForResult(intent,0);
            }
        });

        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        mRecipe=initDatabase();


        if(mRecipe.size()!=0){
            try{
                mDate = initDatabaseDate();
                for (int i = 0; i<mRecipe.size();i++){
                    cal.setTime(mDate.get(i));
                    addEvent(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.HOUR),mRecipe.get(i));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });

        mCalendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                previewNote(eventDay);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_NOTE && resultCode == RESULT_OK) {
            MyEventDay myEventDay = data.getParcelableExtra(RESULT);
            //mCalendarView.setEvents((List<EventDay>) myEventDay.getCalendar());
            mEventDays.add(myEventDay);
            mCalendarView.setEvents(mEventDays);
        }
    }

    private void addNote() {
        Intent intent = new Intent(this, addNoteActivity.class);
        startActivityForResult(intent, ADD_NOTE);
    }

    private void previewNote(EventDay eventDay) {
        Intent intent = new Intent(this, NotePreviewActivity.class);
        if(eventDay instanceof MyEventDay){
            intent.putExtra(EVENT, (MyEventDay) eventDay);
        }
        startActivity(intent);
    }

    private void addEvent(int iYear, int iMonth, int iDay, int iHour, Recipe recipe){
        Calendar calendar = Calendar.getInstance();
        calendar.set(iYear,iMonth,iDay,iHour,0);
        try {
            mCalendarView.setDate(calendar);
        } catch (OutOfDateRangeException e) {
            e.printStackTrace();
        }

        MyEventDay myEventDay = new MyEventDay(calendar,R.drawable.ic_cercle_event,recipe);
        mEventDays.add(myEventDay);
        mCalendarView.setEvents(mEventDays);
    }

    private ArrayList<Recipe> initDatabase(){
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
        return mDBHelper.getRecipes();
    }

    private ArrayList<Date> initDatabaseDate() throws Exception{
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
        return mDBHelper.getDateRecipe();
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
