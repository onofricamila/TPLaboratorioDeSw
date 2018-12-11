package labo2018.razasypelajesonofri;

import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import labo2018.razasypelajesonofri.utils.HorseImgsProvider;
import labo2018.razasypelajesonofri.utils.ResponsiveDesigner;
import labo2018.razasypelajesonofri.utils.SoundsPlayer;
import labo2018.razasypelajesonofri.utils.SoundsProvider;
import labo2018.razasypelajesonofri.utils.StringsManager;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    private int horseToFindId;
    private int lastHorseId;
    private String horseToFindName;
    private TextView horseToFindNameShown;
    private EditText selectedHorseImgTag;
    private List<ImageView> imgsViews;
    private ImageView sound, selectedImageView;
    private Button newGame;
    private Random random = new Random();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        // get layout text views
        horseToFindNameShown = findViewById(R.id.wordTv);
        selectedHorseImgTag = findViewById(R.id.wordEnteredEt);
        // get Siguiente button and set listener
        newGame = findViewById(R.id.newGame);
        newGame.setOnClickListener(this);
        // get Sound ImgView and set listener
        sound = findViewById(R.id.sound);
        sound.setOnClickListener(this);
        // get imgviews from layout
        fillImgsViewsArray();
        // set imgviews sizes
        ResponsiveDesigner.determineImgViewsSize(getWindowManager(), imgsViews);
        // let's play!
        newGame();
    }

    private void makeToast(String msj){
        Toast.makeText(this, msj, Toast.LENGTH_SHORT).show();
    }

    private void makeFeedback(String msj, String soundKey, ArrayList<Integer> sounds){
        makeToast(msj);
        sounds.add(SoundsProvider.INSTANCE.getSoundAt(soundKey));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void validateImage() {
        /* TODO: para el razas y pelajes por separado aca en horsetofindname me viene la raza o
        el pelaje (haria un random entre las 2 partes del nombre de la foto agarrada como answer en newgame)
        y tipo para decir si está bien la eleccion tengo que fijarme si horsetofindname es substring
        del tag de la img seleccionada ... hay mas caballos que pueden coincidir en pelaje o raza!
        */
        ArrayList<Integer> sounds = new ArrayList<>();
        if (horseToFindName.equals(selectedImageView.getTag())) {
            makeFeedback("¡Felicitaciones!", "tada", sounds);
            // play again
            newGame();
        } else {
            makeFeedback("Intenta nuevamente", "error", sounds);
        }
        SoundsPlayer.wannaPlaySound(sounds, this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        // newGame button was clicked
        if (view == newGame) {
            newGame();
        }else if(view == sound){
            playHorseToFindSound();
        }else{
            // an image view was clicked
            selectedImageView = (ImageView) view;
            // DEBUG show the horse associated with the clicked image view
            selectedHorseImgTag.setText((String)selectedImageView.getTag());
            validateImage();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void playHorseToFindSound() {
        // obtener iniciales del caballo a reproducir
        String[] wordArray = StringsManager.splitString(horseToFindName,"_");
        // icon_sound chain w razaInit n pelajeInit
        ArrayList<Integer> sounds = new ArrayList<>();
        sounds.add(SoundsProvider.INSTANCE.getSoundAt( StringsManager.getFirstStringChar(wordArray[0])) );
        sounds.add(SoundsProvider.INSTANCE.getSoundAt( StringsManager.getFirstStringChar(wordArray[1])) );
        SoundsPlayer.wannaPlaySound(sounds, this);
    }


    private void fillImgsViewsArray() {
        imgsViews = new ArrayList<>();
        // add each img view to an imgViews array
        imgsViews.add(findViewById(R.id.imageView1));
        imgsViews.add(findViewById(R.id.imageView2));
        imgsViews.add(findViewById(R.id.imageView3));
        imgsViews.add(findViewById(R.id.imageView4));
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private boolean isAlreadyInImgViews(String word) {
        Predicate<ImageView> p1 = iv -> iv.getTag().equals(word);
        boolean cond = imgsViews.stream().anyMatch(p1);
        return cond;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initImgViewsArray() {
        // fill each img view w a unique picture
        for (int i = 0; i < imgsViews.size(); i++) {
            int randomHorseImgId = HorseImgsProvider.INSTANCE.randomHorseImgId();
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void putAnswerInGame() {
        if (!isAlreadyInImgViews(horseToFindName) ){
            ImageView special = imgsViews.get( random.nextInt(imgsViews.size()) );
            special.setTag(horseToFindName);
            special.setImageResource(horseToFindId);
        }
    }

    private void determineHorseToFind(){
        horseToFindId = HorseImgsProvider.INSTANCE.randomHorseImgId();
        while(horseToFindId == lastHorseId){
            horseToFindId = HorseImgsProvider.INSTANCE.randomHorseImgId();
        }
        lastHorseId = horseToFindId;
        horseToFindName = getResourceNameById(horseToFindId);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void newGame() {
        // reset tags
        resetImgViewsTags();
        // determine horse to find
        determineHorseToFind();
        // show in ui
        horseToFindNameShown.setText(StringsManager.generateWordToShow(horseToFindName, "_"));
        selectedHorseImgTag.setText("");
        // populate img views with random imgs
        initImgViewsArray();
        // put a random img view with the answer img ONLY if it isn't shown yet
        putAnswerInGame();
    }


}
