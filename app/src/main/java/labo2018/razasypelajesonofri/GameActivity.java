package labo2018.razasypelajesonofri;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

import labo2018.razasypelajesonofri.utils.interaction.AInteractionManager;
import labo2018.razasypelajesonofri.utils.interaction.BInteractionManager;
import labo2018.razasypelajesonofri.utils.horses.Horse;
import labo2018.razasypelajesonofri.utils.horses.HorsesProvider;
import labo2018.razasypelajesonofri.utils.interaction.InteractionManager;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{
    // used by both interaction modes
    private Horse horseToFind;
    private String whatToLookFor, lastLookedFor;
    private ImageView homeImgView;
    private InteractionManager interactionManager;
    private HorsesProvider horsesProvider;
    private AnimationDrawable confettiAnimation;
    private int rounds, assertions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if( playingWithBInteraction() ){
            setContentView(R.layout.activity_game_interaccion_b);
            interactionManager = new BInteractionManager(this, playingLevel2());
        }else{
            setContentView(R.layout.activity_game_interaccion_a);
            interactionManager = new AInteractionManager(this, playingLevel2());
        }
        // horses provider
        horsesProvider = new HorsesProvider(this);
        // home btn
        homeImgView = findViewById(R.id.homeImgView);
        homeImgView.setOnClickListener(this);
        // confetti
        ImageView confettiImgView = (ImageView) findViewById(R.id.confettiImageView);
        confettiImgView.setBackgroundResource(R.drawable.animation);
        confettiAnimation = (AnimationDrawable) confettiImgView.getBackground();
        confettiAnimation.setOneShot(true);
        // reset
        resetRoundsAndAssertions();
        // let's play!
        newGame();
    }

    public void resetRoundsAndAssertions() {
        rounds = 0;
        assertions = 0;
    }

    public void startAnimation(){
        confettiAnimation.start();
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
        Resources res = getResources();
        String minijuegoPref = getSharedPrefs().getString("interaccion", res.getString(R.string.pref_default_interaccion));
        return minijuegoPref.equals("B");
    }

    public Boolean playingRazasYPelajesJuntos(){
        Resources res = getResources();
        String minijuegoPref = getSharedPrefs().getString("minijuego", res.getString(R.string.pref_default_minijuego));
        return minijuegoPref.equals("RPJ");
    }

    private Boolean listeningToFemAudio(){
        Resources res = getResources();
        Boolean audioSwitchPref = getSharedPrefs().getBoolean("audio_switch", res.getBoolean(R.bool.pref_default_audio));
        return  audioSwitchPref;
    }

    private Boolean playingLevel2(){
        Resources res = getResources();
        Boolean nivelSwitchPref = getSharedPrefs().getBoolean("nivel_switch", res.getBoolean(R.bool.pref_default_nivel));
        return  nivelSwitchPref;
    }

    private void playGame(String gameString){
        SharedPreferences.Editor editor = getSharedPrefs().edit();
        editor.putString("minijuego", gameString);
        editor.commit();
    }

    public void playRazasYPelajes(){
        playGame("RP");
    }

    public void playRazasYPelajesJuntos(){
        playGame("RPJ");
    }

    private SharedPreferences getSharedPrefs(){
        return PreferenceManager.getDefaultSharedPreferences(this);
    }

    private String randomRazaOPelaje() {
        Random random = new Random();
        String[] temp = new String[]{horseToFind.getType(), horseToFind.getHairType()};
        return temp[random.nextInt(temp.length)];
    }

    private void horseToFind(){
        horseToFind = horsesProvider.randomHorse();
        // si se trata del juego RPJ, pongo directamente como 'a buscar' al nombre de la foto del caballo
        // random, sino, digo bueno, vamos a buscar o bien la raza o el pelaje asociado a la foto
        if(playingRazasYPelajesJuntos()) {
            whatToLookFor = horseToFind.getFullName();
        }else{
            whatToLookFor  = randomRazaOPelaje();
        }
    }

    private void determineHorseToFind(){
        horseToFind();
        while( whatToLookFor.equals(lastLookedFor) ){
            horseToFind();
        }
        lastLookedFor = whatToLookFor;
    }

    private Boolean searchingForType(){
        return horsesProvider.isAHorseType(whatToLookFor);
    }

    private Boolean searchingForHairType(){
        return horsesProvider.isAHorseHairType(whatToLookFor);
    }

    private Boolean searchingForFullName(){
        return (!searchingForType() && !searchingForHairType());
    }

    public void incrementAssertions(){
        assertions++;
    }

    public Boolean roundsLimitAchieved(){
        return rounds==5;
    }

    public Boolean gameWon(){
        return assertions>=3;
    }

    public Boolean isImpossibleToWin(){
        return (rounds - assertions)>=3;
    }

    public void logdGameFlow(){
        Log.d("!!!!GAME-FOW", "ROUNDS:"+rounds+" ASSERTIONS:"+assertions);

    }

    public void makeToast(String string){
        Toast toast = Toast.makeText(this, string, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 40);
        toast.show();
    }

    public void showTrophy() {
        setContentView(R.layout.activity_game_trophy);
        // home btn
        homeImgView = findViewById(R.id.homeImgView);
        homeImgView.setOnClickListener(this);
    }

    public void newGame() {
        // new round
        rounds++;
        // reset tags
        interactionManager.resetViewsTags();
        // determine horse to find
        determineHorseToFind();
        // tell the interactionM the answer data
        interactionManager.informAboutWhatToLookFor(horseToFind, whatToLookFor, searchingForType(),
                searchingForHairType(), searchingForFullName(), listeningToFemAudio());
        // show in ui
        interactionManager.showWhatToLookFor();
        // populate img views with random imgs
        interactionManager.showPossibleAnswers();
        // put a random img view with the answer img ONLY if it isn't shown yet
        interactionManager.putAnswerInGame();
    }

    private SharedPreferences getSharedPrefs2(){
        return getSharedPreferences("A",Context.MODE_PRIVATE);
    }

    public void enableRPJ() {
        Log.d("!!!!!!", "enableRPJ");
        SharedPreferences.Editor editor =  getSharedPrefs2().edit();
        editor.putBoolean("RPJenabled", true);
        editor.commit();
    }

    public void enableRP() {
        Log.d("!!!!!!", "enableRP");
        SharedPreferences.Editor editor =  getSharedPrefs2().edit();
        editor.putBoolean("RPenabled", true);
        editor.commit();
    }

    private void enableGame(String key){
        SharedPreferences.Editor editor =  getSharedPrefs2().edit();
        editor.putBoolean(key, true);
        editor.commit();
    }
}
