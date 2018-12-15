package labo2018.razasypelajesonofri.utils;

import java.util.HashMap;
import java.util.Map;

import labo2018.razasypelajesonofri.R;

public enum SoundsProvider {
    INSTANCE;

    private final Map soundsMap;

    SoundsProvider() {
        this.soundsMap = new HashMap();
        soundsMap.put("relincho", R.raw.relincho);
        soundsMap.put("resoplido", R.raw.resoplido);
        // razas y pelajes por separado
        soundsMap.put("alazán ruano", R.raw.alazan3ruano);
        soundsMap.put("bayo", R.raw.bayo);
        soundsMap.put("criollo", R.raw.criollo);
        soundsMap.put("cuarto de milla", R.raw.cuarto3de3milla);
        soundsMap.put("mestizo", R.raw.mestizo);
        soundsMap.put("mestizo árabe", R.raw.mestizo3arabe);
        soundsMap.put("picaso", R.raw.picaso);
        soundsMap.put("spc", R.raw.spc);
        soundsMap.put("tobiano", R.raw.tobiano);
        soundsMap.put("tordillo canela", R.raw.tordillo3canela);
        soundsMap.put("zaino colorado", R.raw.zaino3colorado);
        soundsMap.put("overo azulejo", R.raw.overo3azulejo);
        // razas y pelajes juntos

    }

    public Integer getSoundAt(String key){
        return (Integer) soundsMap.get(key);
    }
}
