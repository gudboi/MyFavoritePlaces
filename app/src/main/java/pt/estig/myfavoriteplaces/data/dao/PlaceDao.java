package pt.estig.myfavoriteplaces.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import pt.estig.myfavoriteplaces.data.Place;


@Dao
public interface PlaceDao {
    @Insert
    long insert(Place place);

    @Update
    int update(Place place);

    @Delete
    int delete(Place place);

    @Query("SELECT * FROM place")
    List<Place> getAllPlaces();

    @Query("SELECT * FROM place WHERE id_place = :user_id")
    List<Place> getAllPlacesOfUser(long user_id);

}
