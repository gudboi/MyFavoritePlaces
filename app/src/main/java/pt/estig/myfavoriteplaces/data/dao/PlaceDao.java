package pt.estig.myfavoriteplaces.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import pt.estig.myfavoriteplaces.data.Place;


/**
 * Creates the table "Place"
 */
@Dao
public interface PlaceDao {
    /**
     * Method to insert in the table Place
     * @param place the element to insert
     */
    @Insert
    long insert(Place place);

    /**
     * Method to update the table Place
     * @param place the element to update
     */
    @Update
    int update(Place place);

    /**
     * Method to delete elements from the table Place
     * @param place the element to delete
     */
    @Delete
    int delete(Place place);

    /**
     * Lists all the places
     * @return the list of all the places
     */
    @Query("SELECT * FROM place")
    List<Place> getAllPlaces();

    /**
     * Lists all the places registered by the user
     * @param user_id the ID of each user of the app
     * @return the list of all places registered by the user
     */
    @Query("SELECT * FROM place WHERE id_user = :user_id")
    List<Place> getAllPlacesOfUser(long user_id);

    /**
     * Gets the latitudinal value of the place
     * @param place_id the ID of each place registered
     * @return the latitude of the place
     */
    @Query("SELECT latitude FROM place WHERE id_place = :place_id")
    Double getPlaceLatitude(long place_id);

    /**
     * Gets the longitudinal value of the place
     * @param place_id the ID of each place registered
     * @return the longitude of the place
     */
    @Query("SELECT longitude FROM place WHERE id_place = :place_id")
    Double getPlaceLongitude(long place_id);
}
