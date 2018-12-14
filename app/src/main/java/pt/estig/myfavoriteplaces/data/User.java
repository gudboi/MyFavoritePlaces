package pt.estig.myfavoriteplaces.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Creates the attributes for the table User
 */
@Entity(tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = true)
    private long id_user;
    private String username;
    private String password;

    /**
     * Constructor for the table User
     * @param id_user ID for each element in the table
     * @param username the name of each user registered in the app
     * @param password the password of each user registered in the app
     */
    public User(long id_user, String username, String password){
        this.id_user = id_user;
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the ID for each element in the table
     * @return the ID
     */
    public long getId_user() {
        return id_user;
    }

    /**
     * Sets the ID
     * @param id_user the ID for each element in the table
     */
    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    /**
     * Gets the name of each user registered in the app
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username
     * @param username the name of each user registered in the app
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password of each user registered in the app
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password
     * @param password the password of each user registered in the app
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
