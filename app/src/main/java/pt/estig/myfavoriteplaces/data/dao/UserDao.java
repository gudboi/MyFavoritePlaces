package pt.estig.myfavoriteplaces.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import java.util.List;

import pt.estig.myfavoriteplaces.data.User;

/**
 * Creates the table "User"
 */
@Dao
public interface UserDao {
    /**
     * Method to insert in the table User
     * @param user the element to insert
     */
    @Insert
    long insert(User user);

    /**
     * Method to update the table User
     * @param user the element to update
     */
    @Update
    int update(User user);

    /**
     * Method to delete elements from the table User
     * @param user the element to delete
     */
    @Delete
    int delete(User user);

    /**
     * Lists all the users registered in the app
     * @return the list of all the users registered in the app
     */
    @Query("SELECT * FROM user")
    List<User> getAllUsers();

    /**
     * Gets eache user registered in the app
     * @param user each user registered in the app
     * @return the user registered in the app
     */
    @Query("SELECT * FROM user WHERE username = :user")
    User getUser(String user);
}
