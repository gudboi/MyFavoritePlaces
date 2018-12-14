package pt.estig.myfavoriteplaces;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class PlaceOnMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double lat;
    private double lng;
    private String place_name;
    private String place_description;
    byte[] place_photo;
    Intent intent;

    float zoom = 12.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lat = getIntent().getDoubleExtra("PLACE_LAT",0);
        lng = getIntent().getDoubleExtra("PLACE_LNG", 0);
        place_name = getIntent().getStringExtra("PLACE_NAME");
        //place_photo = getIntent().getByteArrayExtra("PLACE_PHOTO");
        place_description = getIntent().getStringExtra("PLACE_DESCRIPTION");

        setContentView(R.layout.activity_place_on_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        /*intent.putExtra("PLACE_LAT", lat);
        intent.putExtra("PLACE_LNG", lng);
        intent.putExtra("PLACE_NAME", place_name);
        intent.putExtra("PLACE_PHOTO", place_photo);
        intent.putExtra("PLACE_DESCRIPTION", place_description);
    */

    }

    @Override
    protected void onStart() {
        super.onStart();

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

        // Add a marker in Sydney and move the camera
        LatLng myplace = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(myplace).title(place_name));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myplace, zoom));
    }
}
