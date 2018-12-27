package labo2018.razasypelajesonofri;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import labo2018.razasypelajesonofri.utils.TextsProvider;

public class InfoActivity extends AppCompatActivity {
    private ImageView homeImgView;
    private TextView infoTextView;
    private TextsProvider textsProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        textsProvider = new TextsProvider(this);
        infoTextView = findViewById(R.id.infoTextView);
        infoTextView.setMovementMethod(new ScrollingMovementMethod());
        infoTextView.setText(textsProvider.getTextFor("info"));
    }

    public void toHome(View view){
        findViewById(R.id.homeImgView).setBackgroundResource(R.drawable.ic_home_click);
        finish();
    }
}
