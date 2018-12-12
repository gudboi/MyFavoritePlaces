package pt.estig.myfavoriteplaces;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
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
 *  This activity is here to list all places added by us,
 *  and for calling the AddPlacesActivity by pressing the add button,
 *  and calling the SingularPlaceActivity by pressing in one item.
 */
 public class PlacesActivity extends AppCompatActivity {

    //  Static


    private long user_id;
    private String username;

    //  Views
    private TextView usernameText;
    private View addPlaceHint;

    private Intent intent;

    private LinearLayoutManager linearLayoutManager;
    private RecyclerView placeList;
    private PlaceAdapter placeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        //this.usernameText = findViewById(R.id.textView_welcome);
        //usernameText.setText(username);

        //this.user_id = getIntent().getLongExtra("USER_ID", 0);
        this.user_id = PreferencesHelper.getPrefs(getApplicationContext()).getLong(PreferencesHelper.USERID,0);
        //this.username = getIntent().getStringExtra("USERNAME");
        this.username = PreferencesHelper.getPrefs(getApplicationContext()).getString(PreferencesHelper.USERNAME,"");

    }

    @Override
    protected void onStart() {
        super.onStart();

        placeList = findViewById(R.id.recyclerView);
        placeAdapter = new PlaceAdapter();
        linearLayoutManager = new LinearLayoutManager(this);

        placeList.setAdapter(placeAdapter);
        placeList.setLayoutManager(linearLayoutManager);

        //Lista todos os places:
        //List<Place> places = DataBase.getInstance(this).placeDao().getAllPlaces();

        //Lista filtrada ao user ID
        List<Place> places = DataBase.getInstance(this).placeDao().getAllPlacesOfUser(this.user_id);

        boolean sortedAz = PreferencesHelper.getPrefs(getApplicationContext()).getBoolean(PreferencesHelper.SORTING_PREF, true);

        placeAdapter.setData(places, sortedAz);
    }

    public void btn_logoutClicked(View view){
        finish();
    }

    public void btn_deleteClicked(Place place){
       //DataBase.getInstance(this).placeDao().delete(place);
    }


    /**
     * @param view
     * carregar alt + enter com o cursor em cima do metodo
     */
    public void btn_addPlaceClicked(View view){
        AddPlaceActivity.start(this, this.user_id);
    }

    private void startSinglePlaceActivity(Place place) {
        long id_place = place.getId_place();
        String place_name = place.getPlace_name();
        String place_description = place.getPlace_info();
        byte[] place_photo = place.getPhoto();

        intent = new Intent(this, SinglePlaceActivity.class);
        intent.putExtra("PLACE_NAME", place_name);
        intent.putExtra("PLACE_DESCRIPTION", place_description);
        intent.putExtra("PLACE_PHOTO", place_photo);

        startActivity(intent);
    }

    class PlaceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        Place place;

        final TextView name;
        final ImageView photo;

        private PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            name = itemView.findViewById(R.id.textView_placeName);
            photo = itemView.findViewById(R.id.imageView_placeImage);
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
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.place, viewGroup, false);
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
