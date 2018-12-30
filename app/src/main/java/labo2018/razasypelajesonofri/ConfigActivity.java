package labo2018.razasypelajesonofri;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

public class ConfigActivity extends AppCompatActivity {
    Switch levelSwitch;
    Switch audioSwitch;
    RadioGroup minijuegoRadioGroup;
    RadioGroup recoViewModeRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_config);

        setValues();
    }

    private void setValues() {
        levelSwitch = findViewById(R.id.levelSwitch);
        audioSwitch = findViewById(R.id.audioSwitch);
        minijuegoRadioGroup = findViewById(R.id.minijuegoRadioGroup);
        recoViewModeRadioGroup = findViewById(R.id.recoViewModeRadioGroup);

        SharedPreferences enableGamesPreferences = getSharedPreferences(String.valueOf(R.string.enable_games_preferences),Context.MODE_PRIVATE);

        Boolean RPJenabled = enableGamesPreferences.getBoolean(getString(R.string.RPJenabled_pref_key), false);

        RadioButton radBtn = findViewById(R.id.RPJRadioBtn);
        if (!RPJenabled){
            radBtn.setEnabled(false);
            radBtn.setTextColor(Color.parseColor("#808080"));
        }else{
            radBtn.setTextColor(Color.WHITE);
        }

        SharedPreferences configPreferences = getSharedPreferences(getString(R.string.config_preferences),Context.MODE_PRIVATE);
        levelSwitch.setChecked(configPreferences
                .getBoolean(getString(R.string.level2_pref_key), false));
        audioSwitch.setChecked(configPreferences
                .getBoolean(getString(R.string.fem_audio_pref_key), false));
        minijuegoRadioGroup.check(configPreferences
                .getInt(getString(R.string.minijuego_pref_key), R.id.RPRadioBtn));
        recoViewModeRadioGroup.check(configPreferences
                .getInt(getString(R.string.reco_view_mode_pref_key), R.id.listRadioBtn));
    }

    public void onAccept(View view) {
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.config_preferences),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.fem_audio_pref_key), audioSwitch.isChecked());
        editor.putBoolean(getString(R.string.level2_pref_key), levelSwitch.isChecked());
        editor.putInt(getString(R.string.minijuego_pref_key), minijuegoRadioGroup.getCheckedRadioButtonId());
        editor.putInt(getString(R.string.reco_view_mode_pref_key), recoViewModeRadioGroup.getCheckedRadioButtonId());
        editor.apply();
        finish();
    }

}
