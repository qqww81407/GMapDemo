package tw.com.gmapdemo;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

import tw.com.gmapdemo.databinding.ActivityMaps3Binding;

public class MapsActivity3 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMaps3Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMaps3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng taipei = new LatLng(25.0336962,121.5643673);
        mMap.addMarker(new MarkerOptions().position(taipei).title("台北101"));

        Polyline polyline = googleMap.addPolyline(new PolylineOptions()
        .add(
                new LatLng(25.0336962,121.56434376),
                new LatLng(25.033755,121.565412),
                new LatLng(25.031985,121.565380),
                new LatLng(25.032083,121.561324)
        ));

        polyline.setEndCap(new RoundCap());
        polyline.setWidth(10);
        polyline.setColor(Color.GREEN);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(taipei,16);
        mMap.moveCamera(cameraUpdate);



    }
}