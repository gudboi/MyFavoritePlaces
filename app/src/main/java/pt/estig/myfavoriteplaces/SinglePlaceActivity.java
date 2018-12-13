package pt.estig.myfavoriteplaces;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;

import pt.estig.myfavoriteplaces.prefs.PreferencesHelper;

public class SinglePlaceActivity extends AppCompatActivity {

    public static final String PLACE_ID = "placeID";

    // Views
    private TextView singlePlaceTextViewDescription;
    private ImageView singlePlaceImageView;

    //  Data
    private String place_name;
    private String place_description;
    private Double place_lat;
    private Double place_lng;
    private byte[] place_photo;

    public static void start(PlacesActivity placesActivity, long id_place) {
        Intent starter = new Intent(placesActivity, SinglePlaceActivity.class);
        starter.putExtra(PLACE_ID, id_place);
        placesActivity.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  Lock portrait view
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_single_place);

        this.singlePlaceImageView = findViewById(R.id.imageView_placeImage);
        this.singlePlaceTextViewDescription = findViewById(R.id.textView_singlePlace_description);

        this.place_name = getIntent().getStringExtra("PLACE_NAME");
        this.place_description = getIntent().getStringExtra("PLACE_DESCRIPTION");
        this.place_photo = getIntent().getByteArrayExtra("PLACE_PHOTO");
        this.place_lat = getIntent().getDoubleExtra("PLACE_LAT",0);
        this.place_lng = getIntent().getDoubleExtra("PLACE_LNG",0);

        Bitmap bitmap = BitmapFactory.decodeByteArray(this.place_photo, 0, this.place_photo.length);

        this.singlePlaceImageView.setImageBitmap(bitmap);
        setTitle(this.place_name);
        this.singlePlaceTextViewDescription.setText(this.place_description);
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

    public void btnBackOnClick(View view) {
        finish();
    }

    public void btnSeeOnMap(View view){
        Intent intent = new Intent(this, PlaceOnMapActivity.class);
        intent.putExtra("LAT", place_lat);
        intent.putExtra("LNG", place_lng);
        intent.putExtra("PLACE_NAME", place_name);
        startActivity(intent);

        Toast.makeText(this, "Lat: " + place_lat + " Lng: " + place_lng, Toast.LENGTH_SHORT).show();
    }
}