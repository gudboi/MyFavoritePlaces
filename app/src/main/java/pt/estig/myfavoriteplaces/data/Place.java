package pt.estig.myfavoriteplaces.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Creates the attributes for the table Place
 */
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

    /**
     * Constructor for the table Place
     * @param id_place ID for each element in the table
     * @param id_user ID for each user of the app
     * @param place_name name of the place inserted by the user
     * @param place_info description of the place inserted by the user
     * @param longitude longitudinal value for positioning
     * @param latitude latitudinal value for positioning
     * @param photo photo taken by the user
     */
    public Place(long id_place, long id_user, String place_name, String place_info, Double longitude, Double latitude, byte[] photo ){
        this.id_place = id_place;
        this.id_user = id_user;
        this.place_name = place_name;
        this.place_info = place_info;
        this.longitude = longitude;
        this.latitude = latitude;
        this.photo = photo;

    }

    /**
     * Gets the latitudinal value
     * @return the latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * Sets the latitude
     * @param latitude the latitudinal value
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * Gets the longitudinal value
     * @return the longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * Sets the longitude
     * @param longitude the longitudinal value
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * Gets the ID for each element of the table
     * @return the ID
     */
    public long getId_place() {
        return id_place;
    }

    /**
     * Sets the ID
     * @param id_place the ID for each element of the table
     */
    public void setId_place(long id_place) {
        this.id_place = id_place;
    }

    /**
     * Gets the ID for each user of the app
     * @return the ID
     */
    public long getId_user() {
        return id_user;
    }

    /**
     * Sets the ID
     * @param id_user the ID for each user of the app
     */
    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    /**
     * Gets the name of the place inserted by the user
     * @return the name of the place
     */
    public String getPlace_name() {
        return place_name;
    }

    /**
     * Sets the name of the place
     * @param place_name the name of the place inserted by the user
     */
    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    /**
     * Gets the description of the place inserted by the user
     * @return the description
     */
    public String getPlace_info() {
        return place_info;
    }

    /**
     * Sets the description
     * @param place_info the description of the place inserted by the user
     */
    public void setPlace_info(String place_info) {
        this.place_info = place_info;
    }

    /**
     * Gets the photo taken by the user
     * @return the photo
     */
    public byte[] getPhoto() {
        return photo;
    }

    /**
     * Sets the photo
     * @param photo the photo taken by the user
     */
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }


}
