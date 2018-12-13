package pt.estig.myfavoriteplaces;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.List;

import pt.estig.myfavoriteplaces.data.DataBase;
import pt.estig.myfavoriteplaces.data.Place;
import pt.estig.myfavoriteplaces.prefs.PreferencesHelper;

import static pt.estig.myfavoriteplaces.prefs.PreferencesHelper.USERID;

public class AddPlaceActivity extends AppCompatActivity {

    // Request codes
    private static final int REQUEST_IMAGE_CAPTURE = 123;

    // Instance State Bundle keys
    private static final String LAT_LNG_KEY = "latlng";
    private static final String PHOTO_BITMAP_KEY = "photoBytes";

    // Views
    private ImageView addPhotoView;
    private EditText editText_place_name;
    private EditText editText_place_description;

    // Bitmap
    private Bitmap photo;

    // Data
    private long user_id;
    private Double latitude;
    private Double longitude;
    private String place_name;
    private String place_description;

    // Map related objects
    //private Marker contactMarker = null;
    //private LatLng currentLatLng = null;

    public static void start(Context context, long id) {
        Intent starter = new Intent(context, AddPlaceActivity.class);
        starter.putExtra("USER_ID", id);
        context.startActivity(starter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  Lock portrait mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_add_place);

        long id = getIntent().getLongExtra("USER_ID", 0);
        if (id == 0) {
            finish();
            return;
        }

        addPhotoView = findViewById(R.id.imageView_addPhoto);
        editText_place_name = findViewById(R.id.editText_place_name);
        editText_place_description = findViewById(R.id.editText_place_description);


        addPhotoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                removePhoto();
                return true;
            }
        });

    }

    private void removePhoto() {
        photo = null;
        addPhotoView.setImageBitmap(null);
        addPhotoView.setVisibility(View.GONE);
    }

    public void btnCameraClicked(View view) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Para verificar que de facto existe uma aplicação que dê conta do nosso pedido
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Se sim, lançamos o Intent
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        } else {
            //erro
        }
    }

    public void btnSaveClicked(View view) {
        this.user_id = PreferencesHelper.getPrefs(getApplicationContext()).getLong(USERID, 0);
        this.place_name = editText_place_name.getText().toString();
        this.place_description = editText_place_description.getText().toString();

        this.longitude = 1234.5;
        this.latitude = 5432.1;

        byte[] photoBytes = getBytesFromBitmap(photo);

        //  coordinates try
        Geocoder geocoder;
        String bestProvider;
        List<Address> user = null;
        double lat = 0;
        double lng = 0;

        LocationManager lm = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        bestProvider = lm.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        @SuppressLint("MissingPermission") Location location = lm.getLastKnownLocation(bestProvider);

        if (location == null){
            Toast.makeText(getApplicationContext(),"Location Not found",Toast.LENGTH_LONG).show();
        }else{
            geocoder = new Geocoder(getApplicationContext());
            try {
                user = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                lat=(double)user.get(0).getLatitude();
                lng=(double)user.get(0).getLongitude();
                System.out.println(" DDD lat: " +lat+",  longitude: "+lng);

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        latitude = lat;
        longitude = lng;

        finish();

        Place place = new Place(0, this.user_id, this.place_name, this.place_description,
                this.longitude, this.latitude, photoBytes);
        DataBase.getInstance(this).placeDao().insert(place);

        Toast.makeText(this, "Utilizador id:" + this.user_id, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            //Add photo to imageview
            Bundle extras = data.getExtras();
            photo = (Bitmap) extras.get("data");
            addPhotoView.setImageBitmap(photo);

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
