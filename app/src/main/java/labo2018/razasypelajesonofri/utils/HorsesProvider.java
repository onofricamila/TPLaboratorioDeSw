package labo2018.razasypelajesonofri.utils;

import android.support.v7.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import labo2018.razasypelajesonofri.R;

public class HorsesProvider{

    private List<Horse> horsesList;
    private AppCompatActivity context;

    public HorsesProvider(AppCompatActivity context) {
        this.context = context;
        this.horsesList = new ArrayList();
        String json = JsonManager.INSTANCE.getJSONFromRaw(R.raw.horses, this.context);
        Horse horse;
        try {
            JSONArray horsesJSON = new JSONArray(json);
            for (int i = 0; i < horsesJSON.length(); i++) {
                JSONObject horseJSON = horsesJSON.getJSONObject(i);
                String type = horseJSON.getString("type");
                String hairType = horseJSON.getString("hairType");
                horse = new Horse(type, hairType);
                Integer image = HorseImgsProvider.INSTANCE.getImgAt(horse.getFullName());
                Map soundsMap = new HashMap();
                soundsMap.put("fem", new Integer[]{
                        SoundsProvider.INSTANCE.getFemSoundAt(type),
                        SoundsProvider.INSTANCE.getFemSoundAt(hairType)
                });
                horse.setImage(image);
                horse.setSoundsMap(soundsMap);
                horsesList.add(horse);
            }
        } catch (JSONException e) { e.printStackTrace(); }

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
