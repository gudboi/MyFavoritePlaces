package pt.estig.myfavoriteplaces;

import android.arch.persistence.room.Database;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import pt.estig.myfavoriteplaces.data.DataBase;
import pt.estig.myfavoriteplaces.data.Place;
import pt.estig.myfavoriteplaces.prefs.PreferencesHelper;

/**
 *  This activity is here to list all places added by us, and for calling the AddPlacesActivity by
 *  pressing the add button, and calling the SingularPlaceActivity by pressing in one item.
 */
 public class PlacesActivity extends AppCompatActivity {

    private long user_id;
    //  Views
    //private TextView usernameText;
    private View addPlaceHint;

    private Intent intent;

    private LinearLayoutManager linearLayoutManager;
    private RecyclerView placeList;
    private PlaceAdapter placeAdapter;

    /**
     * The onCreate void is used to start an activity
     * @param savedInstanceState: is used to save and recover state information.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  Lock portrait view
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_places);

        //this.usernameText = findViewById(R.id.textView_welcome);
        //usernameText.setText(username);

        //  Get extras through intent
        //this.user_id = getIntent().getLongExtra("USER_ID", 0);
        //this.username = getIntent().getStringExtra("USERNAME");

        //  Get data through preferences
        //this.username = PreferencesHelper.getPrefs(getApplicationContext()).getString(PreferencesHelper.USERNAME,"");
        this.user_id = PreferencesHelper.getPrefs(getApplicationContext()).getLong(PreferencesHelper.USERID,0);

    }

    /**
     * Called when the activity is becoming visible to the user.
     */
    @Override
    protected void onStart() {
        super.onStart();

        placeList = findViewById(R.id.recyclerView);
        placeAdapter = new PlaceAdapter();
        linearLayoutManager = new LinearLayoutManager(this);

        placeList.setAdapter(placeAdapter);
        placeList.setLayoutManager(linearLayoutManager);

        List<Place> places = DataBase.getInstance(this).placeDao().getAllPlacesOfUser(this.user_id);

        //boolean sortedAz = PreferencesHelper.getPrefs(getApplicationContext()).getBoolean(PreferencesHelper.SORTING_PREF, true);

        // For now it's cool being hardcoded true because we didn't set the flag sortedAz to false anywhere
        placeAdapter.setData(places,true );
    }

    /** Triggered at the Logout button closing the activity, and getting back to Login/Reg
     * @param view
     */
    public void btnLogoutClicked(View view){
        finish();
    }

    /** Triggered at the delete item
     * @param place
     */
    public void btnDeleteClicked(Place place){


        DataBase.getInstance(this).placeDao().delete(place);
        placeAdapter.remove(place);
        placeAdapter.notifyDataSetChanged();
        // Ainda por fazer meter a hint quando os places sao zero, nicles, niente, nada, rien
        //setAddPlaceHintVisible(placeAdapter.getItemCount() == 0);
    }

    /** Triggered at Add Place button, moving us to the AddPlaceActivity,
     * taking user_id in the extras, but anyway we have it in SharedPrefs
     * @param view
     *
     */
    public void btnAddPlaceClicked(View view){ AddPlaceActivity.start(this, this.user_id);}

    /*private void showDeletePlaceDialog(final Place place) {

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.delete_place_dialog_title)
                //  Precisa de ser corrigido
                //.setMessage(getString(R.string.delete_place_dialog_message, place.getPlace_name()))
                .setPositiveButton(R.string.delete_place_afirmative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        btnDeleteClicked(place);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();

        dialog.show();
    }*/

    /** Triggered clicking an item at the recycleview and opening a new
     * activity called SinglePlaceActivity, passing place data in extras
     * @param place
     */
    private void startSinglePlaceActivity(Place place) {
        //long id_place = place.getId_place();
        String place_name = place.getPlace_name();
        String place_description = place.getPlace_info();
        Double place_lat = place.getLatitude();
        Double place_lng = place.getLongitude();
        byte[] place_photo = place.getPhoto();

        intent = new Intent(this, SinglePlaceActivity.class);
        intent.putExtra("PLACE_NAME", place_name);
        intent.putExtra("PLACE_DESCRIPTION", place_description);
        intent.putExtra("PLACE_PHOTO", place_photo);
        intent.putExtra("PLACE_LNG", place_lng);
        intent.putExtra("PLACE_LAT", place_lat);


        startActivity(intent);
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {
        Place place;

        final TextView name;
        final ImageView photo;
        final ImageView delete;

        private PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            name = itemView.findViewById(R.id.textView_placeName);
            photo = itemView.findViewById(R.id.imageView_placeImage);
            delete = itemView.findViewById(R.id.deleteImageButton);

            this.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    placeAdapter.delete(position);
                }
            });
        }

        private void bind(Place place) {
            this.place = place;
            name.setText(place.getPlace_name());
            if(place.getPhoto() != null && place.getPhoto().length != 0) {
                photo.setImageBitmap(bitmapFromBytes(place.getPhoto()));
                photo.setVisibility(View.VISIBLE);
            }
            else {
                photo.setVisibility(View.INVISIBLE);
            }
        }

        private Bitmap bitmapFromBytes(byte[] photoBytes) {
            ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(photoBytes);
            Bitmap bitmap = BitmapFactory.decodeStream(arrayInputStream);
            return bitmap;
        }

        @Override
        public void onClick(View view) {
            startSinglePlaceActivity(place);
        }

        @Override
        public boolean onLongClick(View view) {
            return true;
        }
    }

    class PlaceAdapter extends RecyclerView.Adapter<PlaceViewHolder> {
        private List<Place> data = new ArrayList<>();

        public void delete(int position) {
            Place place = data.get(position);
            DataBase.getInstance(PlacesActivity.this).placeDao().delete(place);
            data.remove(position);
            notifyItemRemoved(position);
        }

            private void setData(List<Place> data, boolean sort) {
                this.data = data;
                sort(sort);
            }

        private void sort(final boolean asc) {
            Collections.sort(data, new Comparator<Place>() {
                @Override
                public int compare(Place o1, Place o2) {
                    int sort = o1.getPlace_name().compareTo(o2.getPlace_name());
                    if(asc) {
                        return sort;
                    } else {
                        return -sort;
                    }
                }
            });
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.place,
                    viewGroup, false);
            return new PlaceViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PlaceViewHolder placeViewHolder, int i) {
            Place place = data.get(i);
            placeViewHolder.bind(place);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        private void remove(Place place) {
            int index = data.indexOf(place);
            if(index != -1) {
                data.remove(index);
                notifyItemRemoved(index);
            }
        }

        private void removeAll() {
            int count = data.size();
            if(count > 0) {
                data.clear();
                notifyItemRangeRemoved(0, count);
            }
        }
    }
 }
