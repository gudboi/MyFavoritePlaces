package pt.estig.myfavoriteplaces.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "place")
public class Place {
    @PrimaryKey(autoGenerate = true)
    private long id_place;
    private long id_user;
    private String place_name;
    private String place_info;
    private Double latitude;
    private Double longitude;
    private byte[] photo;

    public Place(long id_place, long id_user, String place_name, String place_info, Double longitude, Double latitude, byte[] photo ){
        this.id_place = id_place;
        this.id_user = id_user;
        this.place_name = place_name;
        this.place_info = place_info;
        this.longitude = longitude;
        this.latitude = latitude;
        this.photo = photo;

    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public long getId_place() {
        return id_place;
    }

    public void setId_place(long id_place) {
        this.id_place = id_place;
    }

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public String getPlace_info() {
        return place_info;
    }

    public void setPlace_info(String place_info) {
        this.place_info = place_info;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }


}
