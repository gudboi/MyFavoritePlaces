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

public class LoginActivity extends AppCompatActivity {
    private EditText usernameText;
    private EditText passwordText;
    private Intent intent;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  Lock portrait view
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login);
    }

    public void btnLoginClicked(View view){
        this.usernameText = findViewById(R.id.editText_user);
        this.passwordText = findViewById(R.id.editText_pass);

        String username = this.usernameText.getText().toString();
        String password = this.passwordText.getText().toString();
        user = DataBase.getInstance(this).userDao().getUser(username);

        if(user != null){
            // verificar password.
            if(password.equals(user.getPassword())){
                //Se password correta troca de Activity
                long id = user.getId_user();

                intent = new Intent(this, PlacesActivity.class);
                /*intent.putExtra("USER_ID", id);
                intent.putExtra("USERNAME", username);*/

                PreferencesHelper.getPrefs(getApplicationContext()).edit().putString(PreferencesHelper.USERNAME,username).apply();
                PreferencesHelper.getPrefs(getApplicationContext()).edit().putLong(PreferencesHelper.USERID,id).apply();

                startActivity(intent);
            }
            else
            {
                Toast.makeText(this, "Password Errada!", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Utilizador não existente!", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnRegisterClicked(View view){
        this.usernameText = findViewById(R.id.editText_user);
        this.passwordText = findViewById(R.id.editText_pass);

        String username = this.usernameText.getText().toString();
        String password = this.passwordText.getText().toString();
        User user = DataBase.getInstance(this).userDao().getUser(username);

        if(user != null){
            Toast.makeText(this, "O utilizador já existe!!", Toast.LENGTH_SHORT).show();
        }
        else{
            User usr = new User(0,username, password);
            DataBase.getInstance(this).userDao().insert(usr);
            Toast.makeText(this, "Utilizador criado!", Toast.LENGTH_SHORT).show();
        }
    }
}
