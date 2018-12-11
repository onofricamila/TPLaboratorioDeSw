package labo2018.razasypelajesonofri.utils;

import java.util.HashMap;
import java.util.Map;

import labo2018.razasypelajesonofri.R;

public enum SoundsProvider {
    INSTANCE;

    private final Map soundsMap;

    SoundsProvider() {
        this.soundsMap = new HashMap();
        soundsMap.put("tada", R.raw.tada);
        soundsMap.put("error", R.raw.error);
        soundsMap.put("a", R.raw.a);
        soundsMap.put("b", R.raw.b);
        soundsMap.put("c", R.raw.c);
        soundsMap.put("f", R.raw.f);
        soundsMap.put("m", R.raw.m);
        soundsMap.put("n", R.raw.n);
        soundsMap.put("p", R.raw.p);
    }

    public Map getSoundsMap(){
        return soundsMap;
    }

    public Integer getSoundAt(String key){
        return (Integer) soundsMap.get(key);
    }
}
