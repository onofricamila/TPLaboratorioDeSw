package labo2018.razasypelajesonofri;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

import labo2018.razasypelajesonofri.utils.AInteractionManager;
import labo2018.razasypelajesonofri.utils.BInteractionManager;
import labo2018.razasypelajesonofri.utils.Horse;
import labo2018.razasypelajesonofri.utils.HorsesProvider;
import labo2018.razasypelajesonofri.utils.InteractionManager;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{
    // used by both interaction modes
    private Horse horseToFind;
    private String whatToLookFor;
    private ImageView homeImgView;
    private InteractionManager interactionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if( playingWithBInteraction() ){
            setContentView(R.layout.activity_game_interaccion_b);
            interactionManager = new BInteractionManager(this);
        }else{
            setContentView(R.layout.activity_game_interaccion_a);
            interactionManager = new AInteractionManager(this);
        }
        // home btn
        homeImgView = findViewById(R.id.homeImgView);
        homeImgView.setOnClickListener(this);
        // let's play!
        newGame();
    }

    @Override
    public void onClick(View view) {
        if(view == homeImgView){
            findViewById(R.id.homeImgView).setBackgroundResource(R.drawable.ic_home_click);
            finish();
        }
        else{
            interactionManager.manageOnClick(view);
        }
    }

    private Boolean playingWithBInteraction(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Resources res = getResources();
        String minijuegoPref = sharedPref.getString("interaccion", res.getString(R.string.pref_default_interaccion));
        return minijuegoPref.equals("B");
    }

    private Boolean playingRazasYPelajesJuntos(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Resources res = getResources();
        String minijuegoPref = sharedPref.getString("minijuego", res.getString(R.string.pref_default_minijuego));
        return minijuegoPref.equals("RPJ");
    }

    private String randomRazaOPelaje() {
        Random random = new Random();
        String[] temp = new String[]{horseToFind.getType(), horseToFind.getHairType()};
        return temp[random.nextInt(temp.length)];
    }
    private void determineHorseToFind(){
        horseToFind = HorsesProvider.INSTANCE.randomHorse();
        // si se trata del juego RPJ, pongo directamente como 'a buscar' al nombre de la foto del caballo
        // random, sino, digo bueno, vamos a buscar o bien la raza o el pelaje asociado a la foto
        if(playingRazasYPelajesJuntos()) {
            whatToLookFor = horseToFind.getFullName();
        }else{
            whatToLookFor  = randomRazaOPelaje();
        }

    }

    public void newGame() {
        // reset tags
        interactionManager.resetViewsTags();
        // determine horse to find
        determineHorseToFind();
        // tell the interactionM the answer data
        interactionManager.informAboutAnswer(horseToFind, whatToLookFor);
        // show in ui
        interactionManager.showWhatToLookFor();
        // populate img views with random imgs
        interactionManager.showPossibleAnswers();
        // put a random img view with the answer img ONLY if it isn't shown yet
        interactionManager.putAnswerInGame();
    }

}
