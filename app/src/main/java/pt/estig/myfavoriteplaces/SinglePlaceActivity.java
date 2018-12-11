package pt.estig.myfavoriteplaces;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;

import pt.estig.myfavoriteplaces.data.DataBase;
import pt.estig.myfavoriteplaces.data.Place;

public class SinglePlaceActivity extends AppCompatActivity {

    public static final String PLACE_ID = "placeID";

    // Views
    private TextView singlePlaceTextView;
    private ImageView singlePlaceImageView;

    public static void start(PlacesActivity placesActivity, long id_place) {
        Intent starter = new Intent(placesActivity, SinglePlaceActivity.class);
        //starter.putExtra(PLACE_ID, id_place);

        placesActivity.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_place);

        singlePlaceImageView = findViewById(R.id.imageView_placeImage);
        singlePlaceTextView = findViewById(R.id.textView_singlePlace_name);

        //É aqui

        //singlePlace_name = getIntent().getStringExtra("PLACE_NAME",0);
        //singlePlace_photo.setImageBitmap(bitmapFromBytes(place.getPhoto()));


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
}
