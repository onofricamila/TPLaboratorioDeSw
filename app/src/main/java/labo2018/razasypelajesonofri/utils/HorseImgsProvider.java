package labo2018.razasypelajesonofri.utils;

import java.util.ArrayList;
import java.util.List;

import labo2018.razasypelajesonofri.R;

public enum HorseImgsProvider {
    INSTANCE;

    private final List horseImages;

    HorseImgsProvider() {
        this.horseImages = new ArrayList();
        horseImages.add(R.drawable.azteca_blanco);
        horseImages.add(R.drawable.azteca_marron);
        horseImages.add(R.drawable.azteca_matizado);
        horseImages.add(R.drawable.azteca_negro);

        horseImages.add(R.drawable.criollo_blanco);
        horseImages.add(R.drawable.criollo_marron);
        horseImages.add(R.drawable.criollo_matizado);
        horseImages.add(R.drawable.criollo_negro);

        horseImages.add(R.drawable.falabella_blanco);
        horseImages.add(R.drawable.falabella_marron);
        horseImages.add(R.drawable.falabella_matizado);
        horseImages.add(R.drawable.falabella_negro);

        horseImages.add(R.drawable.percheron_blanco);
        horseImages.add(R.drawable.percheron_marron);
        horseImages.add(R.drawable.percheron_matizado);
        horseImages.add(R.drawable.percheron_negro);
    }

    public List getHorseImagesList() {
        return horseImages;
    }
}
