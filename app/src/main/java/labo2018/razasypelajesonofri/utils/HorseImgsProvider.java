package labo2018.razasypelajesonofri.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import labo2018.razasypelajesonofri.GameActivity;
import labo2018.razasypelajesonofri.R;

public enum HorseImgsProvider {
    INSTANCE;

    private final List horseImagesIds;

    HorseImgsProvider() {
        this.horseImagesIds = new ArrayList();
        horseImagesIds.add(R.drawable.criollo_overo3azulejo);
        horseImagesIds.add(R.drawable.criollo_picaso);
        horseImagesIds.add(R.drawable.cuarto3de3milla_bayo);
        horseImagesIds.add(R.drawable.mestizo3arabe_alazan3ruano);
        horseImagesIds.add(R.drawable.mestizo_tobiano);
        horseImagesIds.add(R.drawable.mestizo_tordillo3canela);
        horseImagesIds.add(R.drawable.spc_zaino3colorado);
    }

    public int randomHorseImgId(){
        Collections.shuffle(horseImagesIds);
        return (int) horseImagesIds.get(0);
    }

    public int horseMatching(GameActivity context, String word){
        List<Integer> result = new ArrayList<>();
        for (Object horseId : horseImagesIds) {
            if ( context.getResourceNameById((int)horseId) .contains(word) ) {
                result.add((int)horseId);
            }
        }
        return result.get(0);
    }

    public Boolean isAHorseType(GameActivity context, String word){
        String result = "";
        for (Object horseId : horseImagesIds) {
            if ( context.getResourceNameById((int)horseId) .contains(word) ) {
                result = context.getResourceNameById((int)horseId);
            }
        }
         if ( StringsManager.splitString(result, "_")[0].equals(word) ){
            return true;
         }else return false;
    }
}
