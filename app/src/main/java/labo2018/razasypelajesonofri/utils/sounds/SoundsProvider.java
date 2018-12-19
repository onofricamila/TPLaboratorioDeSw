package labo2018.razasypelajesonofri.utils.sounds;

import java.util.HashMap;
import java.util.Map;

import labo2018.razasypelajesonofri.R;

public enum SoundsProvider {
    INSTANCE;

    private final Map soundsMap;

    SoundsProvider() {
        this.soundsMap = new HashMap();
        Map femMap = new HashMap();
        Map maleMap = new HashMap();
        // audio fem razas y pelajes por separado
        femMap.put("alazán ruano", R.raw.horse_f_alazan_ruano);
        femMap.put("bayo", R.raw.horse_f_bayo);
        femMap.put("criollo", R.raw.horse_f_criollo);
        femMap.put("cuarto de milla", R.raw.horse_f_cuarto_de_milla);
        femMap.put("mestizo", R.raw.horse_f_mestizo);
        femMap.put("mestizo cruza árabe", R.raw.horse_f_mestizo_arabe);
        femMap.put("picaso", R.raw.horse_f_picaso);
        femMap.put("spc", R.raw.horse_f_spc);
        femMap.put("tobiano", R.raw.horse_f_tobiano);
        femMap.put("tordillo canela", R.raw.horse_f_tordillo_canela);
        femMap.put("zaino colorado", R.raw.horse_f_zaino_colorado);
        femMap.put("overo azulejo", R.raw.horse_f_overo_azulejo);
        // audio male razas y pelajes por separado
        maleMap.put("alazán ruano", R.raw.horse_m_alazan_ruano);
        maleMap.put("bayo", R.raw.horse_m_bayo);
        maleMap.put("criollo", R.raw.horse_m_criollo);
        maleMap.put("cuarto de milla", R.raw.horse_m_cuarto_de_milla);
        maleMap.put("mestizo", R.raw.horse_m_mestizo);
        maleMap.put("mestizo cruza árabe", R.raw.horse_m_mestizo_cruza_arabe);
        maleMap.put("picaso", R.raw.horse_m_picaso);
        maleMap.put("spc", R.raw.horse_m_spc);
        maleMap.put("tobiano", R.raw.horse_m_tobiano);
        maleMap.put("tordillo canela", R.raw.horse_m_tordillo_canela);
        maleMap.put("zaino colorado", R.raw.horse_m_zaino_colorado);
        maleMap.put("overo azulejo", R.raw.horse_m_overo_azulejo);
        // add
        soundsMap.put("success", R.raw.game_relincho);
        soundsMap.put("error", R.raw.game_resoplido);
        soundsMap.put("f", femMap);
        soundsMap.put("m", maleMap);
    }

    public Integer getSoundAt(String key){
        return (Integer) soundsMap.get(key);
    }

    public Integer getFemSoundAt(String key){
        return (Integer) ((Map)soundsMap.get("f")) .get(key);
    }

    public Integer getMaleSoundAt(String key){
        return (Integer) ((Map)soundsMap.get("m")) .get(key);
    }
}
