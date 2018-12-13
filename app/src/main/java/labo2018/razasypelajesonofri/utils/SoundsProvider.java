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
        soundsMap.put("alazan3ruano", R.raw.alazan3ruano);
        soundsMap.put("bayo", R.raw.bayo);
        soundsMap.put("criollo", R.raw.criollo);
        soundsMap.put("cuarto3de3milla", R.raw.cuarto3de3milla);
        soundsMap.put("mestizo", R.raw.mestizo);
        soundsMap.put("mestizo3arabe", R.raw.mestizo3arabe);
        soundsMap.put("picaso", R.raw.picaso);
        soundsMap.put("spc", R.raw.spc);
        soundsMap.put("tobiano", R.raw.tobiano);
        soundsMap.put("tordillo3canela", R.raw.tordillo3canela);
        soundsMap.put("zaino3colorado", R.raw.zaino3colorado);
        soundsMap.put("overo3azulejo", R.raw.overo3azulejo);
        // razas y pelajes juntos

    }

    public Integer getSoundAt(String key){
        return (Integer) soundsMap.get(key);
    }
}
