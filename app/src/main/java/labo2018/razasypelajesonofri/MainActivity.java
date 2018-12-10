package labo2018.razasypelajesonofri;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set defaults values -> false, tells the preferencemanager to only apply the default values
        // the first time the method is called
        PreferenceManager.setDefaultValues(this, R.xml.pref_ajustes, false);
        PreferenceManager.setDefaultValues(this, R.xml.pref_reconocimiento, false);
        PreferenceManager.setDefaultValues(this, R.xml.pref_interaccion, false);
        PreferenceManager.setDefaultValues(this, R.xml.pref_minijuego, false);

        // get the setting as a SharedPreferences object
        SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(this);

        // testing catching a prefs values TODO: implement logic according to prefs values

        String minijuegoPref = sharedPref.getString("minijuego", "RPJ");
        Log.d("!!!!MINIJUEGO-PREF : ", minijuegoPref);

        Boolean nivelSwitchPref = sharedPref.getBoolean("nivel_switch", true);
        Log.d("!!!!NIVEL-PREF : ", String.valueOf(nivelSwitchPref));

        Boolean audioSwitchPref = sharedPref.getBoolean("audio_switch", false);
        Log.d("!!!!AUDIO-PREF : ", String.valueOf(audioSwitchPref));

        String interaccionPref = sharedPref.getString("interaccion", "B");
        Log.d("!!!!INTERACCION-PREF : ", interaccionPref);

        String visualizacionPref = sharedPref.getString("visualizacion", "G");
        Log.d("!!!!VISUALIZAC-PREF : ", visualizacionPref);

        Boolean RPPref = sharedPref.getBoolean("RP", false);
        Log.d("!!!!RP-PREF : ", String.valueOf(RPPref));

        Boolean RPJPref = sharedPref.getBoolean("RPJ", true);
        Log.d("!!!!RPJ-PREF : ", String.valueOf(RPJPref));

        Boolean CPref = sharedPref.getBoolean("C", false);
        Log.d("!!!!C-PREF : ", String.valueOf(CPref));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /** Called when the user taps the Jugar button */
    public void play(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}
