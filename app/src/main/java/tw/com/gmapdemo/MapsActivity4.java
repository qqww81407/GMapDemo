package tw.com.gmapdemo;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import tw.com.gmapdemo.databinding.ActivityMaps4Binding;

public class MapsActivity4 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMaps4Binding binding;

    private TextView gpsinfo;

    private LocationManager locationManager;

    private double lat,lng;

    private LatLng mylocation;

    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMaps4Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        gpsinfo = findViewById(R.id.gpsInfo);
        //定位服務
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        checkLocationPermission();
        getLocation();

    }
    @SuppressLint("MissingPermission")
    private void getLocation() {
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        lat = location.getLatitude();
        lng = location.getLongitude();//抓我現在的位置
        //位置改變時 做的事情
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                if (location != null){
                    StringBuffer sb =new StringBuffer();
                    sb.append("Lat:"+location.getLatitude()+"\n");
                    sb.append("Lng:"+location.getLongitude());
                    gpsinfo.setText(sb.toString());
                    lat = location.getLatitude();
                    lng = location.getLongitude();
                    markerMap();
                }
            }
        });
    }
    //取得位置權限
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            return;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    private void markerMap(){
        mylocation = new LatLng(lat, lng);
        if(marker != null){
            marker.remove();
        }

        marker = mMap.addMarker(new MarkerOptions().position(mylocation).title("I'm Here!!"));

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        markerMap();

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(mylocation,12);
        mMap.moveCamera(cameraUpdate);

    }
}