package labo2018.razasypelajesonofri.utils;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import labo2018.razasypelajesonofri.R;

public class TextsProvider {
    private Map textsMap;
    private AppCompatActivity context;

    public TextsProvider(AppCompatActivity context) {
        this.context = context;
        this.textsMap = new HashMap();
        String json = JsonManager.INSTANCE.getJSONFromRaw(R.raw.texts, this.context);
        try {
            JSONArray textsFromJSON = new JSONArray(json);
            for (int i = 0; i < textsFromJSON.length()-1; i++) {
                JSONObject JSONobject = textsFromJSON.getJSONObject(i);
                String forElement = JSONobject.getString("for");
                String text = JSONobject.getString("text");
                textsMap.put(forElement, text);
            }
        } catch (JSONException e) { e.printStackTrace(); }
    }

    public String getTextFor(String key){
        String tmp = (String) textsMap.get(key);
        if (tmp != null) {
            return tmp;
        }
        return "---";
    }

}
