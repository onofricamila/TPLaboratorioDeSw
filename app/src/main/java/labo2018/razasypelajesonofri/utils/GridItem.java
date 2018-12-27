package labo2018.razasypelajesonofri.utils;

import java.util.ArrayList;

public class GridItem {
    int horseImgId;
    String horseName;
    ArrayList<Integer> sounds;

    public GridItem(int horseImgId, String horseName, ArrayList<Integer> sounds) {
        this.horseImgId = horseImgId;
        this.horseName = horseName;
        this.sounds = sounds;
    }

    public int getHorseImgId() {
        return horseImgId;
    }

    public String getHorseName() {
        return horseName;
    }

}
