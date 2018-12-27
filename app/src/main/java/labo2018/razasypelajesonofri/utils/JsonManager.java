package labo2018.razasypelajesonofri.utils;

import android.support.v7.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

public enum JsonManager {
    INSTANCE;

    public String getJSONFromRaw(int res, AppCompatActivity context) {
        InputStream is = context.getResources().openRawResource(res);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is));
            int n;
            while ((n = reader.read(buffer)) != -1) writer.write(buffer, 0, n);
            is.close();
        } catch (IOException e) { e.printStackTrace(); }
        return writer.toString();
    }
}
