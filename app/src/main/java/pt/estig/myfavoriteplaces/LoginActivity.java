package pt.estig.myfavoriteplaces;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import pt.estig.myfavoriteplaces.data.DataBase;
import pt.estig.myfavoriteplaces.data.User;
import pt.estig.myfavoriteplaces.prefs.PreferencesHelper;

/**
 * The LoginActivity class implements an activity that create a login to get the access to the rest
 * of application.
 */
public class LoginActivity extends AppCompatActivity {

    //View
    private EditText usernameText;
    private EditText passwordText;
    private Intent intent;

    //Data
    private User user;

    /**
     * The onCreate void is used to start an activity
     * @param savedInstanceState: is used to save and recover state information.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  Lock portrait view
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login);
    }

    /**
     * The btnLoginClicked void is used to check if the user name and password is equal to the
     * DataBase, if is equal return a new activity if not return a toast whit the problem.
     * @param view: responsible for drawing and event handling.
     */
    public void btnLoginClicked(View view){
        this.usernameText = findViewById(R.id.editText_user);
        this.passwordText = findViewById(R.id.editText_pass);

        String username = this.usernameText.getText().toString();
        String password = this.passwordText.getText().toString();
        user = DataBase.getInstance(this).userDao().getUser(username);

        if(user != null){
            // Verify password.
            if(password.equals(user.getPassword())){
                //change activity if password is correct!
                long id = user.getId_user();

                intent = new Intent(this, PlacesActivity.class);

                PreferencesHelper.getPrefs(getApplicationContext()).edit()
                        .putString(PreferencesHelper.USERNAME,username).apply();
                PreferencesHelper.getPrefs(getApplicationContext()).edit()
                        .putLong(PreferencesHelper.USERID,id).apply();

                startActivity(intent);
            }
            else
            {
                String textToast = getString(R.string.toast_wrong_password);
                Toast.makeText(this, textToast, Toast.LENGTH_SHORT).show();
            }
        }
        else{
            String textToast = getString(R.string.toast_user_inexistent);
            Toast.makeText(this, textToast, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * The btnRegisterClicked void is used to create a new user on the Database, first verify is the
     * username exist, if not create the new user, if exist return a toast to user change the username.
     * @param view: responsible for drawing and event handling.
     */
    public void btnRegisterClicked(View view){
        this.usernameText = findViewById(R.id.editText_user);
        this.passwordText = findViewById(R.id.editText_pass);

        String username = this.usernameText.getText().toString();
        String password = this.passwordText.getText().toString();
        User user = DataBase.getInstance(this).userDao().getUser(username);

        //If user exist return a toast if not create a new user on Data Base
        if(user != null){
            String textToast = getString(R.string.toast_user_exist);
            Toast.makeText(this, textToast, Toast.LENGTH_SHORT).show();
        }
        else{
            User usr = new User(0,username, password);
            DataBase.getInstance(this).userDao().insert(usr);
            String textToast = getString(R.string.toast_user_created);
            Toast.makeText(this, textToast, Toast.LENGTH_SHORT).show();
        }
    }
}
