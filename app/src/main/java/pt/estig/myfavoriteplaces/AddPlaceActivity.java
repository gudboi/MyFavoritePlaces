package pt.estig.myfavoriteplaces;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import pt.estig.myfavoriteplaces.data.DataBase;
import pt.estig.myfavoriteplaces.data.Place;

public class AddPlaceActivity extends AppCompatActivity {

    // Request codes
    private static final int REQUEST_IMAGE_CAPTURE = 123;

    // Instance State Bundle keys
    private static final String LAT_LNG_KEY = "latlng";
    private static final String PHOTO_BITMAP_KEY = "photoBytes";

    // Views
    private ImageView imageView_addPhoto;
    private EditText editText_place_name;
    private EditText editText_place_description;

    // Bitmap
    private Bitmap photo;

    // invisible Data
    private long user_id;
    private Double latitude;
    private Double longitude;
    private String place_name;
    private String place_description;

    // Map related objects
    //private Marker contactMarker = null;
    //private LatLng currentLatLng = null;

    public static void start(Context context) {
        Intent starter = new Intent(context, AddPlaceActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        // Se a instance state não é null, terá alguma coisa lá guardada
        /*if(savedInstanceState != null) {
            // idêntico aos extras dos intents (de facto os Intents guardam um Bundle mas oferecem métodos de conveniência para acesso a estes campos)
            this.currentLatLng = savedInstanceState.getParcelable(LAT_LNG_KEY);
            this.contactPhotoBitmap = savedInstanceState.getParcelable(PHOTO_BITMAP_KEY);
        }*/
        imageView_addPhoto = findViewById(R.id.imageView_addPhoto);
        editText_place_name = findViewById(R.id.place_name);
        editText_place_description =findViewById(R.id.place_description);

        imageView_addPhoto.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                removePhoto();
                return true;
            }
        });

    }

    private void removePhoto() {
        photo = null;
        imageView_addPhoto.setImageBitmap(null);
        imageView_addPhoto.setVisibility(View.GONE);
    }

    public void btnCameraClicked(View view) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Para verificar que de facto existe uma aplicação que dê conta do nosso pedido
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Se sim, lançamos o Intent
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }else{
            //erro
        }
    }

    public void btnSaveClicked(View view) {
        this.user_id = getIntent().getLongExtra("USER_ID", 0);
        this.place_name = editText_place_name.toString();
        this.place_description = editText_place_description.toString();

        this.longitude = 1234.5;
        this.latitude = 5432.1;

        byte[] photoBytes = getBytesFromBitmap(photo);

        Place place = new Place(0,this.user_id, this.place_name, this.place_description, this.longitude, this.latitude, photoBytes);
        DataBase.getInstance(this).placeDao().insert(place);

        Intent intent = new Intent(this, PlacesActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            //Add photo to imageview
            Bundle extras = data.getExtras();
            photo = (Bitmap) extras.get("data");
            imageView_addPhoto.setImageBitmap(photo);

            //Save photo to external
            // To do
        }
    }

    /**
     * Decodes a Bitmap into an array of bytes
     * @param bmp The source Bitmap
     * @return An array of bytes or null if the Bitmap was null
     */
    private byte[] getBytesFromBitmap(Bitmap bmp) {
        if(bmp == null) return null;

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }


}
