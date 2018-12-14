package pt.estig.myfavoriteplaces.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import pt.estig.myfavoriteplaces.data.dao.PlaceDao;
import pt.estig.myfavoriteplaces.data.dao.TypeOfPlaceDao;
import pt.estig.myfavoriteplaces.data.dao.UserDao;

/**
 * Creates the database to store the information required for the app
 */
@Database(entities = {User.class, Place.class, TypeOfPlace.class}, version = 1, exportSchema = false)

public abstract class DataBase extends RoomDatabase {
    private static DataBase instance = null;

    /**
     * Constructor for the database
     * @param context the context for the database of the application
     * @return instance
     */
    public static DataBase getInstance(Context context){

        context = context.getApplicationContext();

        if (instance == null){
            instance = Room.databaseBuilder(context, DataBase.class, "dataBase")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }


    /**
     * Inserts the table User in the database
     */
    public abstract UserDao userDao();

    /**
     * Inserts the table Place in the database
     */
    public abstract PlaceDao placeDao();

    /**
     * Inserts the table TypeOfPlace in the database
     */
    public abstract TypeOfPlaceDao typeOfPlaceDao();
}
