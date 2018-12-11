package pt.estig.myfavoriteplaces.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import pt.estig.myfavoriteplaces.data.dao.PlaceDao;
import pt.estig.myfavoriteplaces.data.dao.TypeOfPlaceDao;
import pt.estig.myfavoriteplaces.data.dao.UserDao;

@Database(entities = {User.class, Place.class, TypeOfPlace.class}, version = 1, exportSchema = false)

public abstract class DataBase extends RoomDatabase {
    private static DataBase instance = null;

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


    public abstract UserDao userDao();
    public abstract PlaceDao placeDao();
    public abstract TypeOfPlaceDao typeOfPlaceDao();
}
