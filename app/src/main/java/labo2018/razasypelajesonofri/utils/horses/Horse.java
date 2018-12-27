package labo2018.razasypelajesonofri.utils.horses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Horse {
    private String type, hairType;
    private Integer image;
    private Map<String, Integer[]> soundsMap;

    public Horse() {
        this.type = "";
        this.hairType = "";
        this.image = 0;
        this.soundsMap = new HashMap<>();
    }

    public Horse(String type, String hairType) {
        this.type = type;
        this.hairType = hairType;
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

    public Integer getFemTypeSound(){
        return soundsMap.get("f")[0];
    }

    public Integer getFemHairTypeSound(){
        return soundsMap.get("f")[1];
    }

    public ArrayList<Integer> getFemSounds(){
        ArrayList<Integer> res = new ArrayList<>();
        res.add(getFemTypeSound());
        res.add(getFemHairTypeSound());
        return res;
    }

    public Integer getMaleTypeSound(){
        return soundsMap.get("m")[0];
    }

    public Integer getMaleHairTypeSound(){
        return soundsMap.get("m")[1];
    }

    public ArrayList<Integer> getMaleSounds(){
        ArrayList<Integer> res = new ArrayList<>();
        res.add(getMaleTypeSound());
        res.add(getMaleHairTypeSound());
        return res;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public void setSoundsMap(Map<String, Integer[]> soundsMap) {
        this.soundsMap = soundsMap;
    }

    @Override
    public String toString() {
        return "Horse{" +
                "type='" + type + '\'' +
                ", hairType='" + hairType + '\'' +
                '}';
    }
}
