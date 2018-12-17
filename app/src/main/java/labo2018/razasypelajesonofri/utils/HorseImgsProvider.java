package labo2018.razasypelajesonofri.utils;

import java.util.HashMap;
import java.util.Map;
import labo2018.razasypelajesonofri.R;

public enum HorseImgsProvider {
    INSTANCE;

    private final Map imgsMap;

    HorseImgsProvider() {
        this.imgsMap = new HashMap();
        imgsMap.put("spc zaino colorado", R.drawable.horse_spc__zaino_colorado);
        imgsMap.put("mestizo cruza árabe alazán ruano", R.drawable.horse_mestizo_arabe__alazan_ruano);
        imgsMap.put("mestizo tordillo canela", R.drawable.horse_mestizo__tordillo_canela);
        imgsMap.put("mestizo tobiano", R.drawable.horse_mestizo__tobiano);
        imgsMap.put("cuarto de milla bayo", R.drawable.horse_cuarto_de_milla__bayo);
        imgsMap.put("criollo picaso", R.drawable.horse_criollo__picaso);
        imgsMap.put("criollo overo azulejo", R.drawable.horse_criollo__overo_azulejo);
    }

    public Integer getImgAt(String key){
        return (Integer) imgsMap.get(key);
    }
}

