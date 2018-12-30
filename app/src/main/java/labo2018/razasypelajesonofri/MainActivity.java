package labo2018.razasypelajesonofri;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
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
        PreferenceManager.setDefaultValues(this, R.xml.pref_minijuego, false);

        // get the setting as a SharedPreferences object
        SharedPreferences defaultSharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);

        // testing retrieving prefs values
        Resources res = getResources();

        String minijuegoPref = defaultSharedPreferences.getString("minijuego", res.getString(R.string.pref_default_minijuego));
        Log.d("!!!!MINIJUEGO-PREF : ", minijuegoPref);

        Boolean nivelSwitchPref = defaultSharedPreferences.getBoolean("nivel_switch", res.getBoolean(R.bool.pref_default_nivel));
        Log.d("!!!!NIVEL2-PREF : ", String.valueOf(nivelSwitchPref));

        Boolean audioSwitchPref = defaultSharedPreferences.getBoolean("audio_switch", res.getBoolean(R.bool.pref_default_audio));
        Log.d("!!!!AUDIO-PREF : ", String.valueOf(audioSwitchPref));

        String visualizacionPref = defaultSharedPreferences.getString("visualizacion", res.getString(R.string.pref_default_visualizacion));
        Log.d("!!!!RECO-VISUAL-PREF : ", visualizacionPref);



        // testing config_preferences
        Log.d("-----------------------", "-----------------------");

        SharedPreferences configPreferences = getSharedPreferences(getString(R.string.config_preferences),Context.MODE_PRIVATE);

        Boolean playinLevel2 = configPreferences.getBoolean(getString(R.string.level2_pref_key), false);
        Log.d("!!!!NIVEL2-PREF : ", String.valueOf(playinLevel2));

        Boolean femAudio = configPreferences.getBoolean(getString(R.string.fem_audio_pref_key), false);
        Log.d("!!!!FEM-AUDIO-PREF : ", String.valueOf(femAudio));

        Integer game = configPreferences.getInt(getString(R.string.minijuego_pref_key), R.id.RPRadioBtn);
        Log.d("!!!!RPJ? : ", String.valueOf(game == R.id.RPJRadioBtn));

        Integer viewMode = configPreferences.getInt(getString(R.string.reco_view_mode_pref_key), R.id.listRadioBtn);
        Log.d("!!!!grid? : ", String.valueOf(viewMode == R.id.gridRadioBtn));


        // testing enable_games_preferences
        Log.d("-----------------------", "-----------------------");
        SharedPreferences enableGamesPreferences = getSharedPreferences(String.valueOf(R.string.enable_games_preferences),Context.MODE_PRIVATE);

        Boolean RPJenabled = enableGamesPreferences.getBoolean(String.valueOf(R.string.RPJenabled_pref_key), false);

        Log.d("!!!!!!!!!!MAIN-ACT", "RPJenabled: " + RPJenabled);
    }

    /** Called when the user taps Play Button */
    public void play(View view) {
        findViewById(R.id.playButton).setBackgroundResource(R.drawable.ic_jugar_click);
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    /** Called when the user taps Recon Button */
    public void toReconocimiento(View view) {
        findViewById(R.id.reconocimientoButton).setBackgroundResource(R.drawable.ic_reconocimiento_click);
        Intent intent = new Intent(this, RecoActivity.class);
        startActivity(intent);
    }

    /** Called when the user taps Settings Button */
    public void toSettings(View view) {
//        findViewById(R.id.settingsButton).setBackgroundResource(R.drawable.ic_config_click);
//        Intent intent = new Intent(this, SettingsActivity.class);
//        startActivity(intent);
          findViewById(R.id.settingsButton).setBackgroundResource(R.drawable.ic_config_click);
          Intent intent = new Intent(this, ConfigActivity.class);
          startActivity(intent);

    }

    /** Called when the user taps Info Button */
    public void toInfo(View view) {
        findViewById(R.id.infoButton).setBackgroundResource(R.drawable.ic_info_click);
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }
}
