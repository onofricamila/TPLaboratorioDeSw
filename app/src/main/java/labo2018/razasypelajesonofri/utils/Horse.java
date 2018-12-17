package labo2018.razasypelajesonofri.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Horse {
    private String type, hairType;
    private Integer image;
    private Map<String, Integer[]> soundsMap;

    public Horse(String type, String hairType, Integer image, Map<String, Integer[]> soundsMap) {
        this.type = type;
        this.hairType = hairType;
        this.image = image;
        this.soundsMap = soundsMap;
    }

    public Horse() {
        this.type = "";
        this.hairType = "";
        this.image = 0;
        this.soundsMap = new HashMap<>();
    }

    public String getType() {
        return type;
    }

    public String getHairType() {
        return hairType;
    }

    public String getFullName(){
        return type + " " + hairType;
    }

    public Integer getImage() {
        return image;
    }

    public Integer getFemSpeciesSound(){
        return soundsMap.get("f")[0];
    }

    public Integer getFemHairTypeSound(){
        return soundsMap.get("f")[1];
    }

    public ArrayList<Integer> getFemSounds(){
        ArrayList<Integer> res = new ArrayList<>();
        res.add(getFemSpeciesSound());
        res.add(getFemHairTypeSound());
        return res;
    }

    public Integer getMascSpeciesSound(){
        return soundsMap.get("m")[0];
    }

    public Integer getMascHairTypeSound(){
        return soundsMap.get("m")[1];
    }

    public ArrayList<Integer> getMascSounds(){
        ArrayList<Integer> res = new ArrayList<>();
        res.add(getMascSpeciesSound());
        res.add(getMascHairTypeSound());
        return res;
    }

    @Override
    public String toString() {
        return "Horse{" +
                "type='" + type + '\'' +
                ", hairType='" + hairType + '\'' +
                '}';
    }
}