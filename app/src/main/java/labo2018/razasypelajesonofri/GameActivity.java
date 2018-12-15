package labo2018.razasypelajesonofri;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import labo2018.razasypelajesonofri.utils.AInteractionManager;
import labo2018.razasypelajesonofri.utils.BInteractionManager;
import labo2018.razasypelajesonofri.utils.HorseImgsProvider;
import labo2018.razasypelajesonofri.utils.InteractionManager;
import labo2018.razasypelajesonofri.utils.StringsManager;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{
// used by both interaction modes
    private int horseToFindId;
    private String answerHorseImgName, whatToLookFor;
    private ImageView homeImgView;
    private InteractionManager interactionManager;
// used only by interaction B
    private TextView horseToFindTextView;
    private List<ImageView> imgsViews;
    private ImageView soundImgView, selectedImageView;
    private Random random = new Random();

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


    public String getResourceNameById(int id){
        Resources resources = this.getResources();
        return resources.getResourceEntryName(id);
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
        String[] temp = StringsManager.splitString(answerHorseImgName,"_");
        return temp[random.nextInt(temp.length)];
    }
    private void determineHorseToFind(){
        horseToFindId = HorseImgsProvider.INSTANCE.randomHorseImgId();
        answerHorseImgName = getResourceNameById(horseToFindId);
        // si se trata del juego RPJ, pongo directamente como 'a buscar' al nombre de la foto del caballo
        // random, sino, digo bueno, vamos a buscar o bien la raza o el pelaje asociado a la foto
        if(playingRazasYPelajesJuntos()) {
            whatToLookFor = answerHorseImgName;
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
        interactionManager.informAboutAnswer(horseToFindId, answerHorseImgName, whatToLookFor);
        // show in ui
        interactionManager.showWhatToLookFor();
        // populate img views with random imgs
        interactionManager.showPossibleAnswers();
        // put a random img view with the answer img ONLY if it isn't shown yet
        interactionManager.putAnswerInGame();
    }

}
