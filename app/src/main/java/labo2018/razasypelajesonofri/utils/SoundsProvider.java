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
        soundsMap.put("a", R.raw.a);
        soundsMap.put("b", R.raw.b);
        soundsMap.put("c", R.raw.c);
        soundsMap.put("f", R.raw.f);
        soundsMap.put("m", R.raw.m);
        soundsMap.put("n", R.raw.n);
        soundsMap.put("p", R.raw.p);
    }

    public Integer getSoundAt(String key){
        return (Integer) soundsMap.get(key);
    }
}
