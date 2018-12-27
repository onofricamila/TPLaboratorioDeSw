package labo2018.razasypelajesonofri.utils.recoListView;

import java.util.ArrayList;

public class ListItem {
    int horseImgId;
    String horseName;
    ArrayList<Integer> sounds;
    String txt;

    public ListItem(int horseImgId, String horseName, ArrayList<Integer> sounds, String txt) {
        this.horseImgId = horseImgId;
        this.horseName = horseName;
        this.sounds = sounds;
        this.txt = txt;
    }

    public int getHorseImgId() {
        return horseImgId;
    }

    public String getHorseName() {
        return horseName;
    }

    public  ArrayList<Integer> getSounds() {
        return sounds;
    }
}
