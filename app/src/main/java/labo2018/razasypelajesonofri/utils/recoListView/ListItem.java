package labo2018.razasypelajesonofri.utils.recoListView;

public class ListItem {
    int horseImgId, soundId;
    String horseName;

    public ListItem(int horseImgId, String horseName, int soundId) {
        this.horseImgId = horseImgId;
        this.horseName = horseName;
        this.soundId = soundId;
    }

    public int getHorseImgId() {
        return horseImgId;
    }

    public String getHorseName() {
        return horseName;
    }

    public int getSoundId() {
        return soundId;
    }
}
