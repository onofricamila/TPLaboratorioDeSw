package labo2018.razasypelajesonofri.utils.reco.list;

import java.util.ArrayList;

import labo2018.razasypelajesonofri.utils.reco.grid.GridItem;

public class ListItem extends GridItem {

    String txt;

    public ListItem(int horseImgId, String horseName, ArrayList<Integer> sounds, String txt) {
        super(horseImgId, horseName, sounds);
        this.txt = txt;
    }

    public String getTxt() {
        return txt;
    }
}
