package pt.estig.myfavoriteplaces.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.arch.persistence.room.Insert;

import java.util.List;

import pt.estig.myfavoriteplaces.data.TypeOfPlace;

@Dao
public interface TypeOfPlaceDao {
    @Insert
    long insert(TypeOfPlace type_of_place);

    @Update
    int update(TypeOfPlace type_of_place);

    @Delete
    int delete(TypeOfPlace type_of_place);

    @Query("SELECT * FROM typeOfPlace")
    List<TypeOfPlace> getAllTypeOfPlace();
}
