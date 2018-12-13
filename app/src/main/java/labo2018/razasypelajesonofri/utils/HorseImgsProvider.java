package labo2018.razasypelajesonofri.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import labo2018.razasypelajesonofri.R;

public enum HorseImgsProvider {
    INSTANCE;

    private final List horseImages;

    HorseImgsProvider() {
        this.horseImages = new ArrayList();
        horseImages.add(R.drawable.criollo_overo3azulejo);
        horseImages.add(R.drawable.criollo_picaso);
        horseImages.add(R.drawable.cuarto3de3milla_bayo);
        horseImages.add(R.drawable.mestizo3arabe_alazan3ruano);
        horseImages.add(R.drawable.mestizo_tobiano);
        horseImages.add(R.drawable.mestizo_tordillo3canela);
        horseImages.add(R.drawable.spc_zaino3colorado);
    }

    public int randomHorseImgId(){
        Collections.shuffle(horseImages);
        return (int) horseImages.get(0);
    }
}
