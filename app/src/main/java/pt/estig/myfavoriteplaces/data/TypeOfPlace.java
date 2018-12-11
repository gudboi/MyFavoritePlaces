package pt.estig.myfavoriteplaces.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "typeOfPlace")
public class TypeOfPlace {
    @PrimaryKey(autoGenerate = true)
    private long id_type;
    private long id_place;
    private String typeOfPlace;

    public TypeOfPlace(long id_type, long id_place, String typeOfPlace){
        this.id_type = id_type;
        this.id_place = id_place;
        this.typeOfPlace = typeOfPlace;
    }

    public long getId_type() {
        return id_type;
    }

    public void setId_type(long id_type) {
        this.id_type = id_type;
    }

    public long getId_place() {
        return id_place;
    }

    public void setId_place(long id_place) {
        this.id_place = id_place;
    }

    public String getTypeOfPlace() {
        return typeOfPlace;
    }

    public void setTypeOfPlace(String typeOfPlace) {
        this.typeOfPlace = typeOfPlace;
    }
}
