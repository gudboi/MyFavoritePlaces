package pt.estig.myfavoriteplaces;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayInputStream;

import pt.estig.myfavoriteplaces.data.DataBase;
import pt.estig.myfavoriteplaces.data.Place;

public class SinglePlaceActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public static final String PLACE_ID = "placeID";

    // Views
    private TextView singlePlaceTextViewDescription;
    private ImageView singlePlaceImageView;
    private TextView singlePlaceTextViewLat;
    private TextView singlePlaceTextViewLng;

    //  Data
    private String place_name;
    private String place_description;
    private Double place_lat;
    private Double place_lng;
    private byte[] place_photo;
<<<<<<< Updated upstream
    private String place_latitude;
    private String place_longitude;
=======
    private double lat;
    private double lon;
>>>>>>> Stashed changes



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_place1);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //  Lock portrait view
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_single_place);

        this.singlePlaceImageView = findViewById(R.id.imageView_placeImage);
        this.singlePlaceTextViewDescription = findViewById(R.id.textView_singlePlace_description);
        this.singlePlaceTextViewLat = findViewById(R.id.textView_singlePlace_lat);
        this.singlePlaceTextViewLng = findViewById(R.id.textView_singlePlace_lng);

        this.place_name = getIntent().getStringExtra("PLACE_NAME");
        this.place_description = getIntent().getStringExtra("PLACE_DESCRIPTION");
        this.place_photo = getIntent().getByteArrayExtra("PLACE_PHOTO");
        this.place_lat = getIntent().getDoubleExtra("PLACE_LAT",0);
        this.place_lng = getIntent().getDoubleExtra("PLACE_LNG",0);

        place_latitude = String.valueOf(place_lat);
        place_longitude = String.valueOf(place_lng);
        Bitmap bitmap = BitmapFactory.decodeByteArray(this.place_photo, 0, this.place_photo.length);

        this.singlePlaceImageView.setImageBitmap(bitmap);
        setTitle(this.place_name);
        this.singlePlaceTextViewDescription.setText(this.place_description);
        this.singlePlaceTextViewLat.setText(this.place_latitude);
        this.singlePlaceTextViewLng.setText(this.place_longitude);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private Bitmap bitmapFromBytes(byte[] photoBytes) {
        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(photoBytes);
        Bitmap bitmap = BitmapFactory.decodeStream(arrayInputStream);
        return bitmap;
    }

    public void btnBackOnClick(View view) { finish();}


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
        //lat = DataBase.getInstance(this).placeDao().getPlaceLatitude(/*entra o id do local*/);
        //lon = lat = DataBase.getInstance(this).placeDao().getPlaceLongitude(/*entra o id do local*/);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(sydney).title(this.place_name));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
