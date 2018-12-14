package pt.estig.myfavoriteplaces.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.arch.persistence.room.Insert;

import java.util.List;

import pt.estig.myfavoriteplaces.data.TypeOfPlace;

/**
 * Creates the table "TypeOfPlace"
 */
@Dao
public interface TypeOfPlaceDao {
    /**
     * Method to insert in the table TypeOfPlace
     * @param type_of_place the element to insert
     */
    @Insert
    long insert(TypeOfPlace type_of_place);

    /**
     * Method to update the table TypeOfPlace
     * @param type_of_place the element to update
     */
    @Update
    int update(TypeOfPlace type_of_place);

    /**
     * Method to delete elements from the table TypeOfPlace
     * @param type_of_place  the element to delete
     */
    @Delete
    int delete(TypeOfPlace type_of_place);

    /**
     * Lists all the types of places registered in the app
     * @return the list of all the types of places registered in the app
     */
    @Query("SELECT * FROM typeOfPlace")
    List<TypeOfPlace> getAllTypeOfPlace();
}
