package labo2018.razasypelajesonofri;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        // testing retrieving prefs values TODO: implement logic according to prefs values
        Resources res = getResources();

        String minijuegoPref = sharedPref.getString("minijuego", res.getString(R.string.pref_default_minijuego));
        Log.d("!!!!MINIJUEGO-PREF : ", minijuegoPref);

        Boolean nivelSwitchPref = sharedPref.getBoolean("nivel_switch", res.getBoolean(R.bool.pref_default_nivel));
        Log.d("!!!!NIVEL-PREF : ", String.valueOf(nivelSwitchPref));

        Boolean audioSwitchPref = sharedPref.getBoolean("audio_switch", res.getBoolean(R.bool.pref_default_audio));
        Log.d("!!!!AUDIO-PREF : ", String.valueOf(audioSwitchPref));

        String interaccionPref = sharedPref.getString("interaccion", res.getString(R.string.pref_default_interaccion));
        Log.d("!!!!INTERACCION-PREF : ", interaccionPref);

        String visualizacionPref = sharedPref.getString("visualizacion", res.getString(R.string.pref_default_visualizacion));
        Log.d("!!!!VISUALIZAC-PREF : ", visualizacionPref);

        Boolean RPPref = sharedPref.getBoolean("RP", res.getBoolean(R.bool.pref_default_RP));
        Log.d("!!!!RP-PREF : ", String.valueOf(RPPref));

        Boolean RPJPref = sharedPref.getBoolean("RPJ", res.getBoolean(R.bool.pref_default_RPJ));
        Log.d("!!!!RPJ-PREF : ", String.valueOf(RPJPref));

        Boolean CPref = sharedPref.getBoolean("C", res.getBoolean(R.bool.pref_default_C));
        Log.d("!!!!C-PREF : ", String.valueOf(CPref));
    }

    /** Called when the user taps the Jugar button */
    public void play(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void toSettings(View view) {
        findViewById(R.id.settingsButton).setBackgroundResource(R.drawable.ic_config_click);
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
