package ch.vracapps.splashscreen.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Shop implements Parcelable {
    private int id;
    private String name;
    private double longitude;
    private double latitude;
    private String service;
    private String horaire;
    private String imageUrl;
    private String webSite;

    public Shop(int id, String name, double longitude, double latitude, String service, String horaire, String imageUrl, String webSite) {
        this.id = id;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.service = service;
        this.horaire = horaire;
        this.imageUrl = imageUrl;
        this.webSite = webSite;
    }


    protected Shop(Parcel in) {
        id = in.readInt();
        name = in.readString();
        longitude = in.readDouble();
        latitude = in.readDouble();
        service = in.readString();
        horaire = in.readString();
        imageUrl = in.readString();
        webSite=in.readString();
    }

    public static final Creator<Shop> CREATOR = new Creator<Shop>() {
        @Override
        public Shop createFromParcel(Parcel in) {
            return new Shop(in);
        }

        @Override
        public Shop[] newArray(int size) {
            return new Shop[size];
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

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getHoraire() {
        return horaire;
    }
    public void setHoraire(String horaire) {
        this.horaire = horaire;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeDouble(longitude);
        parcel.writeDouble(latitude);
        parcel.writeString(service);
        parcel.writeString(horaire);
        parcel.writeString(imageUrl);
        parcel.writeString(webSite);
    }
}
