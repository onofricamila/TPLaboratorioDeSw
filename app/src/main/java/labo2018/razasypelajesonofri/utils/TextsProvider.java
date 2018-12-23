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
            JSONArray horsesJSON = new JSONArray(json);
            for (int i = 0; i < horsesJSON.length(); i++) {
                JSONObject JSONobject = horsesJSON.getJSONObject(i);
                String forElement = JSONobject.getString("for");
                String text = JSONobject.getString("text");
                Log.d("!!!!!", "for" + forElement);
                Log.d("!!!!!", "text" + text);
                textsMap.put(forElement, text);
            }
            Log.d("!!!!!!!!!!", "TextsProvider: " + textsMap);
        } catch (JSONException e) { e.printStackTrace(); }
    }

    public String getTextFor(String key){
        String tmp = (String) textsMap.get(key);
        if (tmp != null) {
            return (String) textsMap.get(key);
        }
        return "---";
    }

}
