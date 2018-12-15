package labo2018.razasypelajesonofri.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import labo2018.razasypelajesonofri.R;

public enum HorsesProvider {
    INSTANCE;

    private final List<Horse> horsesList;

    HorsesProvider() {
        this.horsesList = new ArrayList();

        // CRIOLLO OVERO AZULEJO
        String type = "criollo";
        String hairType = "overo azulejo";
        Map<String, Integer[]> map = new HashMap<>();
        map.put("f", new Integer[]{
                R.raw.criollo,
                R.raw.overo3azulejo
        });
        horsesList.add(new Horse(type, hairType, R.drawable.criollo_overo3azulejo, map));

        // CRIOLLO PICASO
        type = "criollo";
        hairType = "picaso";
        map = new HashMap<>();
        map.put("f", new Integer[]{
                R.raw.criollo,
                R.raw.picaso
        });
        horsesList.add(new Horse(type, hairType, R.drawable.criollo_picaso, map));

        // CUARTO DE MILLA BAYO
        type = "cuarto de milla";
        hairType = "bayo";
        map = new HashMap<>();
        map.put("f", new Integer[]{
                R.raw.cuarto3de3milla,
                R.raw.bayo
        });
        horsesList.add(new Horse(type, hairType, R.drawable.cuarto3de3milla_bayo, map));

        // MESTIZO ARABE ALAZAN RUANO
        type = "mestizo cruza árabe";
        hairType = "alazán ruano";
        map = new HashMap<>();
        map.put("f", new Integer[]{
                R.raw.mestizo3arabe,
                R.raw.alazan3ruano
        });
        horsesList.add(new Horse(type, hairType, R.drawable.mestizo3arabe_alazan3ruano, map));

        // MESTIZO TOBIANO
        type = "mestizo";
        hairType = "tobiano";
        map = new HashMap<>();
        map.put("f", new Integer[]{
                R.raw.mestizo,
                R.raw.tobiano
        });
        horsesList.add(new Horse(type, hairType, R.drawable.mestizo_tobiano, map));

        // MESTIZO TORDILLO CANELA
        type = "mestizo";
        hairType = "tordillo canela";
        map = new HashMap<>();
        map.put("f", new Integer[]{
                R.raw.mestizo,
                R.raw.tordillo3canela
        });
        horsesList.add(new Horse(type, hairType, R.drawable.mestizo_tordillo3canela, map));

        // SPC ZAINO COLORADO
        type = "spc";
        hairType = "zaino colorado";
        map = new HashMap<>();
        map.put("f", new Integer[]{
                R.raw.spc,
                R.raw.zaino3colorado
        });
        horsesList.add(new Horse(type, hairType, R.drawable.spc_zaino3colorado, map));

    }

    public Horse randomHorse(){
        Collections.shuffle(horsesList);
        return horsesList.get(0);
    }

    public Boolean isAHorseType(String word){
        for (Horse horse : horsesList) {
            if ( horse.getType().equals(word) ) {
                return true;
            }
        }
        return false;
    }

    public Boolean isAHorseHairType(String word) {
        for (Horse horse : horsesList) {
            if ( horse.getHairType().equals(word) ) {
                return true;
            }
        }
        return false;
    }
}
