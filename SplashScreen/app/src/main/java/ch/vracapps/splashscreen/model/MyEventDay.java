package ch.vracapps.splashscreen.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.applandeo.materialcalendarview.EventDay;

import java.util.Calendar;

import ch.vracapps.splashscreen.model.Edeman_Classes.Recipe;

public class MyEventDay extends EventDay implements Parcelable {
    private Recipe recipe;

    public MyEventDay(Calendar day, int imageResource, Recipe recipe) {
        super(day, imageResource);
        this.recipe = recipe;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public MyEventDay(Parcel in) {
        super((Calendar) in.readSerializable(), in.readInt());
        recipe = (Recipe) in.readValue(Recipe.class.getClassLoader());
    }

    public static final Creator<MyEventDay> CREATOR = new Creator<MyEventDay>() {
        @Override
        public MyEventDay createFromParcel(Parcel in) {
            return new MyEventDay(in);
        }

        @Override
        public MyEventDay[] newArray(int size) {
            return new MyEventDay[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable(getCalendar());
        parcel.writeInt(getImageResource());
        parcel.writeValue(recipe);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}