package pt.estig.myfavoriteplaces.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Creates the attributes for the table TypeOfPlace
 */
@Entity(tableName = "typeOfPlace")
public class TypeOfPlace {
    @PrimaryKey(autoGenerate = true)
    private long id_type;
    private long id_place;
    private String typeOfPlace;

    /**
     * Constructor for the table TypeOfPlace
     * @param id_type ID for each element in the table
     * @param id_place ID for each place registered in the app
     * @param typeOfPlace the type given to each place registered in the app
     */
    public TypeOfPlace(long id_type, long id_place, String typeOfPlace){
        this.id_type = id_type;
        this.id_place = id_place;
        this.typeOfPlace = typeOfPlace;
    }

    /**
     * Gets the ID for each element in the table
     * @return the ID
     */
    public long getId_type() {
        return id_type;
    }

    /**
     * Sets the ID
     * @param id_type the ID for each element in the table
     */
    public void setId_type(long id_type) {
        this.id_type = id_type;
    }

    /**
     * Gets the ID for each place registered in the app
     * @return the ID
     */
    public long getId_place() {
        return id_place;
    }

    /**
     * Sets the ID
     * @param id_place the ID for each place registered in the app
     */
    public void setId_place(long id_place) {
        this.id_place = id_place;
    }

    /**
     * Gets the type given to each place registered in the app
     * @return the type of place
     */
    public String getTypeOfPlace() {
        return typeOfPlace;
    }

    /**
     * Sets the type of place
     * @param typeOfPlace the type given to each place registered in the app
     */
    public void setTypeOfPlace(String typeOfPlace) {
        this.typeOfPlace = typeOfPlace;
    }
}
