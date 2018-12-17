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
                R.raw.horse_f_criollo,
                R.raw.horse_f_overo_azulejo
        });
        horsesList.add(new Horse(type, hairType, R.drawable.horse_criollo__overo_azulejo, map));

        // CRIOLLO PICASO
        type = "criollo";
        hairType = "picaso";
        map = new HashMap<>();
        map.put("f", new Integer[]{
                R.raw.horse_f_criollo,
                R.raw.horse_f_picaso
        });
        horsesList.add(new Horse(type, hairType, R.drawable.horse_criollo__picaso, map));

        // CUARTO DE MILLA BAYO
        type = "cuarto de milla";
        hairType = "bayo";
        map = new HashMap<>();
        map.put("f", new Integer[]{
                R.raw.horse_f_cuarto_de_milla,
                R.raw.horse_f_bayo
        });
        horsesList.add(new Horse(type, hairType, R.drawable.horse_cuarto_de_milla__bayo, map));

        // MESTIZO ARABE ALAZAN RUANO
        type = "mestizo cruza árabe";
        hairType = "alazán ruano";
        map = new HashMap<>();
        map.put("f", new Integer[]{
                R.raw.horse_f_mestizo_arabe,
                R.raw.horse_f_alazan_ruano
        });
        horsesList.add(new Horse(type, hairType, R.drawable.horse_mestizo_arabe__alazan_ruano, map));

        // MESTIZO TOBIANO
        type = "mestizo";
        hairType = "tobiano";
        map = new HashMap<>();
        map.put("f", new Integer[]{
                R.raw.horse_f_mestizo,
                R.raw.horse_f_tobiano
        });
        horsesList.add(new Horse(type, hairType, R.drawable.horse_mestizo__tobiano, map));

        // MESTIZO TORDILLO CANELA
        type = "mestizo";
        hairType = "tordillo canela";
        map = new HashMap<>();
        map.put("f", new Integer[]{
                R.raw.horse_f_mestizo,
                R.raw.horse_f_tordillo_canela
        });
        horsesList.add(new Horse(type, hairType, R.drawable.horse_mestizo__tordillo_canela, map));

        // SPC ZAINO COLORADO
        type = "spc";
        hairType = "zaino colorado";
        map = new HashMap<>();
        map.put("f", new Integer[]{
                R.raw.horse_f_spc,
                R.raw.horse_f_zaino_colorado
        });
        horsesList.add(new Horse(type, hairType, R.drawable.horse_spc__zaino_colorado, map));

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
