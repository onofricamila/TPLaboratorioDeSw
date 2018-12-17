package labo2018.razasypelajesonofri.utils;

import java.util.HashMap;
import java.util.Map;

import labo2018.razasypelajesonofri.R;

public enum SoundsProvider {
    INSTANCE;

    private final Map soundsMap;

    SoundsProvider() {
        this.soundsMap = new HashMap();
        soundsMap.put("success", R.raw.game_relincho);
        soundsMap.put("error", R.raw.game_resoplido);
        // razas y pelajes por separado
        soundsMap.put("alazán ruano", R.raw.horse_f_alazan_ruano);
        soundsMap.put("horse_f_bayo", R.raw.horse_f_bayo);
        soundsMap.put("horse_f_criollo", R.raw.horse_f_criollo);
        soundsMap.put("cuarto de milla", R.raw.horse_f_cuarto_de_milla);
        soundsMap.put("horse_f_mestizo", R.raw.horse_f_mestizo);
        soundsMap.put("horse_f_mestizo árabe", R.raw.horse_f_mestizo_arabe);
        soundsMap.put("horse_f_picaso", R.raw.horse_f_picaso);
        soundsMap.put("horse_f_spc", R.raw.horse_f_spc);
        soundsMap.put("horse_f_tobiano", R.raw.horse_f_tobiano);
        soundsMap.put("tordillo canela", R.raw.horse_f_tordillo_canela);
        soundsMap.put("zaino colorado", R.raw.horse_f_zaino_colorado);
        soundsMap.put("overo azulejo", R.raw.horse_f_overo_azulejo);
    }

    public Integer getSoundAt(String key){
        return (Integer) soundsMap.get(key);
    }
}
