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

import java.io.ByteArrayInputStream;

import pt.estig.myfavoriteplaces.data.DataBase;
import pt.estig.myfavoriteplaces.data.Place;

public class SinglePlaceActivity extends AppCompatActivity {

    public static final String PLACE_ID = "placeID";

    // Views
    private TextView singlePlaceTextViewDescription;
    private ImageView singlePlaceImageView;
    private String place_name;
    private String place_description;
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
}
