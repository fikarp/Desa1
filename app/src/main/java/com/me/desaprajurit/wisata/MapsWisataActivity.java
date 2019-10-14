package com.me.desaprajurit.wisata;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.me.desaprajurit.R;

public class MapsWisataActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String sLongitude, sLatitude, sAlamat;
    private Double dLongitude, dLatitude;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_wisata);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        sLatitude = getIntent().getStringExtra("latitude");
        sLongitude = getIntent().getStringExtra("longitude");
        sAlamat = getIntent().getStringExtra("alamat");
//
        System.out.println("lokasi >>" + sLatitude + sLongitude);
        if(sLatitude !=null && sLongitude!= null){
            dLatitude = Double.valueOf(sLatitude);
            dLongitude = Double.valueOf(sLongitude);
        } else {
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(sLatitude !=null && sLongitude!= null) {
            // Add a marker in Sydney and move the camera
            LatLng lating = new LatLng(Double.valueOf(sLongitude), Double.valueOf(sLatitude));
            float zoomLevel = 16.0f; //This goes up to 21
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lating, zoomLevel));
            mMap.addMarker(new MarkerOptions().position(lating).title(sAlamat));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(lating));
        } else {
            Toast.makeText(getApplicationContext(), "Alamat tidak di temukan",Toast.LENGTH_LONG).show();
        }
    }
}
