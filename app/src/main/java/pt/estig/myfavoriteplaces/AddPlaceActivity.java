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
import android.location.LocationListener;
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
import java.util.Objects;

import pt.estig.myfavoriteplaces.data.DataBase;
import pt.estig.myfavoriteplaces.data.Place;
import pt.estig.myfavoriteplaces.prefs.PreferencesHelper;

import static pt.estig.myfavoriteplaces.prefs.PreferencesHelper.USERID;

/**
 *The AddPlaceActivity class implements an activity permit create a new place on the Data Base
 */
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

    private Double lat;
    private Double lng;

    static Location lastLocation = null;
    static double distanceInM;

    private boolean isGpsEnable;

    // Map related objects
    //private Marker contactMarker = null;
    //private LatLng currentLatLng = null;

    /**
     *
     * @param context
     * @param id
     */
    public static void start(Context context, long id) {
        Intent starter = new Intent(context, AddPlaceActivity.class);
        starter.putExtra("USER_ID", id);
        context.startActivity(starter);
    }

    /**
     * The onCreate void is used to start an activity
     * @param savedInstanceState: is used to save and recover state information.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  Lock portrait mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_add_place);

        addPhotoView = findViewById(R.id.imageView_addPhoto);
        editText_place_name = findViewById(R.id.editText_place_name);
        editText_place_description = findViewById(R.id.editText_place_description);

        LocationManager lm = (LocationManager) getApplicationContext().getSystemService(
                Context.LOCATION_SERVICE);
        isGpsEnable = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        LocationListener locationListener;

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (lastLocation == null) {
                    lastLocation = location;
                }
                //distanceInM += location.distanceTo((lastLocation));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        };


        //checkLocationPermission();

        Geocoder geocoder;
        String bestProvider;
        List<Address> user;

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1,1,locationListener);

        Criteria criteria = new Criteria();
       // assert lm != null;
        bestProvider = Objects.requireNonNull(lm).getBestProvider(criteria, true);


        @SuppressLint("MissingPermission") Location location = lm.getLastKnownLocation(
                LocationManager.GPS_PROVIDER);

        if (location == null){
            Toast.makeText(getApplicationContext(),"Location Not found",
                    Toast.LENGTH_LONG).show();
        }else{
            geocoder = new Geocoder(getApplicationContext());
            try {
                user = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),
                        1);
                lat=user.get(0).getLatitude();
                lng=user.get(0).getLongitude();
                //System.out.println(" DDD lat: " +lat+",  longitude: "+lng);

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        latitude = lat;
        longitude = lng;

        long id = getIntent().getLongExtra("USER_ID", 0);
        if (id == 0) {
            finish();
            return;
        }

        addPhotoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                removePhoto();
                return true;
            }
        });

    }

    /**
     *
     */
    private void removePhoto() {
        photo = null;
        addPhotoView.setImageBitmap(null);
        addPhotoView.setVisibility(View.GONE);
    }

    /**
     * The btnCameraClicked void is responsable to open the device camera to request a image capture
     * @param view: responsible for drawing and event handling.
     */
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

    /**
     * The btnSaveClicked save all data from editTexts to the table place of Data Base
     * @param view: responsible for drawing and event handling.
     */
    public void btnSaveClicked(View view) {
        if(isGpsEnable){

            this.user_id = PreferencesHelper.getPrefs(getApplicationContext()).getLong(USERID,
                    0);
            this.place_name = editText_place_name.getText().toString();
            this.place_description = editText_place_description.getText().toString();

            byte[] photoBytes = getBytesFromBitmap(photo);

            Toast.makeText(this, "Lat: " + latitude + " Lng: " + longitude + "Place:" +
                    place_name, Toast.LENGTH_SHORT).show();

            Place place = new Place(0, this.user_id, this.place_name, this.place_description,
                    this.longitude, this.latitude, photoBytes);

            DataBase.getInstance(this).placeDao().insert(place);

            finish();
            //Toast.makeText(this, "Utilizador id:" + this.user_id, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Ligue o GPS para poder submeter um local.",
                    Toast.LENGTH_LONG).show();
        }

    }

    /**
     * The onActivityResult void verify if the REQUEST_IMAGE_CAPTURE is equal to RESULT_OK to add the
     * photo to the Activity imageView.
     * @param requestCode: Code to verify whit the result code.
     * @param resultCode: Expected Result code.
     * @param data: Intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

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
        if (bmp == null) return null;

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    /**
     * The onRequestPermissionsResult Request the access of gps localization to the device user.
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    /**
     * The checkLocationPermission check if all permisons request on onRequestPermissionsResult are
     * grant.
     * @return PERMISSION_GRANTED
     */
    public boolean checkLocationPermission()
    {
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }
}
