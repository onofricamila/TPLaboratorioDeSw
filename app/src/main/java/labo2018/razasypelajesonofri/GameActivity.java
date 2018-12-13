package labo2018.razasypelajesonofri;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import labo2018.razasypelajesonofri.utils.HorseImgsProvider;
import labo2018.razasypelajesonofri.utils.SoundsPlayer;
import labo2018.razasypelajesonofri.utils.SoundsProvider;
import labo2018.razasypelajesonofri.utils.StringsManager;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    private int horseToFindId;
    private String answerHorseImgName, whatToLookFor;
    private TextView horseToFindShown;
    private List<ImageView> imgsViews;
    private ImageView soundImgView, selectedImageView;
    private Button newGameBtn;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        // get layout text view
        horseToFindShown = findViewById(R.id.wordShown);
        // get Sound ImgView and set listener
        soundImgView = findViewById(R.id.soundImgView);
        soundImgView.setOnClickListener(this);
        // get horses imgviews from layout
        fillImgsViewsArray();
        // let's play!
        newGame();
    }

    private void makeToast(String msj){
        Toast.makeText(this, msj, Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void validateImage() {
        ArrayList<Integer> sounds = new ArrayList<>();
        if ( ((String)selectedImageView.getTag()).contains(whatToLookFor) ){
            sounds.add(SoundsProvider.INSTANCE.getSoundAt("relincho"));
            // play again
            newGame();
        } else {
            sounds.add(SoundsProvider.INSTANCE.getSoundAt("resoplido"));
        }
        SoundsPlayer.wannaPlaySound(sounds, this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View view) {
        if (view == soundImgView){
            findViewById(R.id.soundImgView).setBackgroundResource(R.drawable.ic_audio_click);
            playHorseToFindSound();
        }else{
            // an image view was clicked
            selectedImageView = (ImageView) view;
            validateImage();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void playHorseToFindSound() {
        // obtener array de strings a partir de lo que busco
        String[] wordArray = StringsManager.splitString(whatToLookFor,"_");
        // sound chain
        ArrayList<Integer> sounds = new ArrayList<>();
        for (int i = 0; i < wordArray.length; i++) {
            sounds.add(SoundsProvider.INSTANCE.getSoundAt( StringsManager.getFirstStringChar(wordArray[i])) );
        }
        SoundsPlayer.wannaPlaySound(sounds, this);
    }


    private void fillImgsViewsArray() {
        imgsViews = new ArrayList<>();
        // add each img view to an imgViews array
        imgsViews.add(findViewById(R.id.horseImageView1));
        imgsViews.add(findViewById(R.id.horseImageView2));
        imgsViews.add(findViewById(R.id.horseImageView3));
        imgsViews.add(findViewById(R.id.horseImageView4));
    }

    private void resetImgViewsTags(){
        for (int i = 0; i < imgsViews.size(); i++) {
            ImageView imageView = imgsViews.get(i);
            imageView.setTag("");
        }
    }

    private String getResourceNameById(int id){
        Resources resources = this.getResources();
        return resources.getResourceEntryName(id);
    }

    private boolean isAlreadyInImgViews(String word) {
        boolean cond = false;
        for (ImageView imgsView : imgsViews) {
            if ( ((String)imgsView.getTag()) .contains(word) ) {
                cond = true;
                break;
            }
        }
        return cond;
    }

    private void initImgViewsArray() {
        // fill each img view w a unique picture
        for (int i = 0; i < imgsViews.size(); i++) {
            int randomHorseImgId = HorseImgsProvider.INSTANCE.randomHorseImgId();
            // we dont wanna have the same horse image twice
            while(isAlreadyInImgViews( getResourceNameById(randomHorseImgId)) ){
                randomHorseImgId = HorseImgsProvider.INSTANCE.randomHorseImgId();
            }
            ImageView imageView = imgsViews.get(i);
            imageView.setTag( getResourceNameById(randomHorseImgId) );
            imageView.setImageResource(randomHorseImgId);
            imageView.setOnClickListener(this);
        }
        // DEBUG aca se ven las 'current' tags
        for (int i = 0; i < imgsViews.size(); i++) {
            Log.d("!!!DEBUG-CURRENT-TAGS: ", imgsViews.get(i).getTag().toString());
        }
    }

    private void putAnswerInGame() {
        // si no hay nada que repreente a lo que estoy buscando, subo la foto elegida como respuesta
        if ( !isAlreadyInImgViews(whatToLookFor) ){
            ImageView randomImgView = imgsViews.get( random.nextInt(imgsViews.size()) );
            randomImgView.setTag(answerHorseImgName);
            randomImgView.setImageResource(horseToFindId);
        }
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

    private void newGame() {
        // reset tags
        resetImgViewsTags();
        // determine horse to find
        determineHorseToFind();
        // show in ui
        horseToFindShown.setText(StringsManager.generateWordToShow(whatToLookFor, "_"));
        // populate img views with random imgs
        initImgViewsArray();
        // put a random img view with the answer img ONLY if it isn't shown yet
        putAnswerInGame();
    }

}
