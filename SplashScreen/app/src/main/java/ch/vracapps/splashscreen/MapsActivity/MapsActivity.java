package ch.vracapps.splashscreen.MapsActivity;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import ch.vracapps.splashscreen.MainActivity;
import ch.vracapps.splashscreen.R;
import ch.vracapps.splashscreen.database.DatabaseHelper;
import ch.vracapps.splashscreen.model.Shop;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    //Marker marker_1;

    private static final int REQUEST_CODE = 101;
    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;


    private List<Shop> mShopList;
    private DatabaseHelper mDBHelper;
    private LinearLayout ll_home;

    private TextView tvBottom_Sheet_name_shops;
    private BottomSheetBehavior mBottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        initListShopMaps();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        fetchLasLocation();

        ll_home = findViewById(R.id.ll_home);
        ll_home.setOnClickListener(new LinearLayout.OnClickListener(){

            public void onClick (View v){
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivityForResult(intent,0);
            }
        });

        View bottomSheet = findViewById(R.id.bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        tvBottom_Sheet_name_shops = findViewById(R.id.tvBottom_sheet_name_shops);

    }

    private List<Shop> initListShopMaps(){
        mDBHelper = new DatabaseHelper(this);
        //Check exists database
        File database = getApplicationContext().getDatabasePath(DatabaseHelper.DBNAME);
        if(false == database.exists()){
            mDBHelper.getReadableDatabase();
            //Copy db
            if(copyDatabase(this)){
                Log.d("MapsActivity","initListShopMaps:Copy database success");
                //Toast.makeText(this,"Copy database succes",Toast.LENGTH_SHORT).show();

            }else{
                Log.d("MapsActivity","initListShopMaps:Copy data error");
                //Toast.makeText(this,"Copy data error",Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        //Get Shop List in db exist
        return mDBHelper.getListShopMaps();
    }

    private void fetchLasLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    Toast.makeText(getApplicationContext(), currentLocation.getLatitude() + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
                    supportMapFragment.getMapAsync(MapsActivity.this);
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        //LatLng latMagasins = new LatLng(46.984094, 6.878396);

        mShopList=initListShopMaps();
        if(mShopList!=null){
            for (int i=0;i<mShopList.size();i++){
                googleMap.addMarker(new MarkerOptions().position(new LatLng(mShopList.get(i).getLongitude(),mShopList.get(i).getLatitude())).title(mShopList.get(i).getName()));
                //MarkerOptions markerCurrentPosition = new MarkerOptions().position(latLng).title("I am here");
                //MarkerOptions markerMagasins = new MarkerOptions().position(latMagasins).title("Chez Mamie Bio - Vrac");
                //googleMap.addMarker(markerCurrentPosition);
                //marker_1 = googleMap.addMarker(markerMagasins);
            }
        }

        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));

        //Get blue marker currentlocation
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }

        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    fetchLasLocation();
                }
                break;
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        for (int i=0;i<mShopList.size();i++){
            if(marker.getTitle().equals(mShopList.get(i).getName())){
                Log.w("Click", "test");
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                tvBottom_Sheet_name_shops.setText(mShopList.get(i).getName());
                return true;
            }
        }
        return false;
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
