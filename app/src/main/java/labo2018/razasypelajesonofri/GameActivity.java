package labo2018.razasypelajesonofri;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private AnimationDrawable confettiAnimation, trophyAnimation;
    private int rounds, assertions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // reset
        resetRoundsAndAssertions();
        // layout according to the chosen interaction mode
        prepareLayout();
        // let's play!
        newGame();
    }



    public void prepareLayout(){
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
    }

    public void resetRoundsAndAssertions() {
        rounds = 0;
        assertions = 0;
    }

    public void startConfettiAnimation(){
        // confetti
        ImageView confettiImgView = (ImageView) findViewById(R.id.confettiImageView);
        confettiImgView.setBackgroundResource(R.drawable.animation);
        confettiAnimation = (AnimationDrawable) confettiImgView.getBackground();
        confettiAnimation.setOneShot(true);
        confettiAnimation.start();
    }

    private void startAnimation(int imgViewId, int base, int top, String baseName) {
        final AnimationDrawable anim;
        ImageView img = findViewById(imgViewId);
        anim = new AnimationDrawable();
        for (int i = base; i <= top; i += 1) {
            String name = baseName + i;
            anim.addFrame(getResources().getDrawable(getResources().getIdentifier(name, "drawable", getPackageName())), 250);
            System.gc();
        }
        anim.setOneShot(false);
        img.setImageDrawable(anim);
        Runnable run = new Runnable() {
            @Override
            public void run() {
                anim.start();
            }
        };

        img.post(run);
    }

    public void startTrophyAnimation() {
        startAnimation(R.id.trophyImageView, 1, 8, "rsz_copa_rotando");
    }

    public void toHome(View view){
        Log.d("!!!!GAME-FLOW", "to home");
        finish();
    }

    public void retry(View view){
        Log.d("!!!!GAME-FLOW", "retry");
        prepareLayout();
        // play again
        newGame();
    }

    public void next(View view){
        Log.d("!!!!GAME-FLOW", "next");
        // -> select 'playing RPJ'
        playRazasYPelajesJuntos();
        prepareLayout();
        // play again
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

    public Boolean playingRazasYPelajesJuntos(){
        Integer minijuegoPref = getConfigSharedPrefs().getInt(getString(R.string.minijuego_pref_key), R.id.RPRadioBtn);
        return minijuegoPref == R.id.RPJRadioBtn;
    }

    private Boolean playingWithBInteraction(){
        Integer interactionPref = getConfigSharedPrefs().getInt(getString(R.string.interaction_pref_key), R.id.InteracARadBtn);
        Log.d("!!!!!!!!!!!!INTERACTION", "playingWithBInteraction? " + String.valueOf(interactionPref == R.id.InteracBRadBtn));
        return interactionPref == R.id.InteracBRadBtn;
    }

    public Boolean listeningToFemAudio(){
        Resources res = getResources();
        Boolean femAudioSwitchPref = getConfigSharedPrefs().getBoolean(getString(R.string.fem_audio_pref_key), res.getBoolean(R.bool.pref_default_audio));
        return  femAudioSwitchPref;
    }

    private Boolean playingLevel2(){
        Resources res = getResources();
        Boolean level2SwitchPref = getConfigSharedPrefs().getBoolean(getString(R.string.level2_pref_key), res.getBoolean(R.bool.pref_default_nivel));
        return  level2SwitchPref;
    }

    private void playGame(Integer game){
        SharedPreferences.Editor editor = getConfigSharedPrefs().edit();
        editor.putInt(getString(R.string.minijuego_pref_key), game);
        editor.commit();
    }

    public void playRazasYPelajesJuntos(){
        playGame(R.id.RPJRadioBtn);
    }

    private SharedPreferences getConfigSharedPrefs(){
        return getSharedPreferences(getString(R.string.config_preferences),Context.MODE_PRIVATE);
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

    public Boolean gameWon(){
        return assertions>=3 && rounds==5;
    }

    public Boolean isImpossibleToWin(){
        return assertions<3 && rounds==5;
    }

    public void logdGameFlow(){
        Log.d("!!!!GAME-FLOW", "ROUNDS:"+rounds+" ASSERTIONS:"+assertions);
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

    public void showRetryLayout() {
        setContentView(R.layout.activity_game_retry);
    }

    public void showNextLayout() {
        setContentView(R.layout.activity_game_next);
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

    private SharedPreferences getEnableGamesSharedPrefs(){
        return getSharedPreferences(String.valueOf(R.string.enable_games_preferences), Context.MODE_PRIVATE);
    }

    public void enableRPJ() {
        enableGame(getString(R.string.RPJenabled_pref_key));
    }

    private void enableGame(String key){
        Boolean RPJenabled = getEnableGamesSharedPrefs().getBoolean(getString(R.string.RPJenabled_pref_key), false);

        if (!RPJenabled) {
            Log.d("!!!!GAME-FLOW", "enableRPJ");
            SharedPreferences.Editor editor = getEnableGamesSharedPrefs().edit();
            editor.putBoolean(key, true);
            editor.apply();
            editor.commit();
        }
    }
}
