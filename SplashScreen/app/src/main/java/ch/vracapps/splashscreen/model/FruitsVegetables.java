package ch.vracapps.splashscreen.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class FruitsVegetables implements Parcelable {
    private int id;
    private String name;
    private int energy_kcal;
    private int energy_kJ;
    private double lipid;
    private double fatty_acid;
    private double glucides;
    private double carbonhydrate;
    private double dietary_fiber;
    private double protein;
    private Bitmap image;


    public FruitsVegetables(int id, String name, int energy_kcal, int energy_kJ, double lipid, double fatty_acid, double glucides, double carbonhydrate, double dietary_fiber, double protein, Bitmap image) {
        this.id = id;
        this.name = name;
        this.energy_kcal = energy_kcal;
        this.energy_kJ = energy_kJ;
        this.lipid = lipid;
        this.fatty_acid = fatty_acid;
        this.glucides = glucides;
        this.carbonhydrate = carbonhydrate;
        this.dietary_fiber = dietary_fiber;
        this.protein = protein;
        this.image = image;
    }

    protected FruitsVegetables(Parcel in) {
        id = in.readInt();
        name = in.readString();
        energy_kcal = in.readInt();
        energy_kJ = in.readInt();
        lipid = in.readDouble();
        fatty_acid = in.readDouble();
        glucides = in.readDouble();
        carbonhydrate = in.readDouble();
        dietary_fiber = in.readDouble();
        protein = in.readDouble();
        image = (Bitmap) in.readValue(Bitmap.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(energy_kcal);
        dest.writeInt(energy_kJ);
        dest.writeDouble(lipid);
        dest.writeDouble(fatty_acid);
        dest.writeDouble(glucides);
        dest.writeDouble(carbonhydrate);
        dest.writeDouble(dietary_fiber);
        dest.writeDouble(protein);
        dest.writeValue(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FruitsVegetables> CREATOR = new Creator<FruitsVegetables>() {
        @Override
        public FruitsVegetables createFromParcel(Parcel in) {
            return new FruitsVegetables(in);
        }

        @Override
        public FruitsVegetables[] newArray(int size) {
            return new FruitsVegetables[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEnergy_kcal() {
        return energy_kcal;
    }

    public void setEnergy_kcal(int energy_kcal) {
        this.energy_kcal = energy_kcal;
    }

    public int getEnergy_kJ() {
        return energy_kJ;
    }

    public void setEnergy_kJ(int energy_kJ) {
        this.energy_kJ = energy_kJ;
    }

    public double getLipid() {
        return lipid;
    }

    public void setLipid(double lipid) {
        this.lipid = lipid;
    }

    public double getFatty_acid() {
        return fatty_acid;
    }

    public void setFatty_acid(double fatty_acid) {
        this.fatty_acid = fatty_acid;
    }

    public double getGlucides() {
        return glucides;
    }

    public void setGlucides(double glucides) {
        this.glucides = glucides;
    }

    public double getCarbonhydrate() {
        return carbonhydrate;
    }

    public void setCarbonhydrate(double carbonhydrate) {
        this.carbonhydrate = carbonhydrate;
    }

    public double getDietary_fiber() {
        return dietary_fiber;
    }

    public void setDietary_fiber(double dietary_fiber) {
        this.dietary_fiber = dietary_fiber;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
