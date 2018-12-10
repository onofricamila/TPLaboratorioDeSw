package labo2018.razasypelajesonofri;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import labo2018.razasypelajesonofri.utils.HorseImgsProvider;
import labo2018.razasypelajesonofri.utils.ResponsiveDesigner;
import labo2018.razasypelajesonofri.utils.SoundsPlayer;
import labo2018.razasypelajesonofri.utils.SoundsProvider;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    private List horseImages;
    private int horseToFindId;
    private int lastHorseId;
    private String horseToFindName;
    private TextView horseToFindNameShown;
    private EditText selectedHorseImgTag;
    private List<ImageView> imgsViews;
    private ImageView sound, selectedImageView;
    private Button newGame;
    private Random random = new Random();
    private Map soundsMap;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        // get layout text views
        horseToFindNameShown = (TextView) findViewById(R.id.wordTv);
        selectedHorseImgTag = (EditText) findViewById(R.id.wordEnteredEt);
        // get Siguiente button and set listener
        newGame = (Button) findViewById(R.id.newGame);
        newGame.setOnClickListener(this);
        // get Sound ImgView and set listener
        sound = (ImageView) findViewById(R.id.sound);
        sound.setOnClickListener(this);
        // init list of horse imgs
        initHorseImgsArray();
        // init sounds map
        initSoundsMap();
        // get imgviews from layout
        fillImgsViewsArray();
        // set imgviews sizes
        ResponsiveDesigner.determineImgViewsSize(getWindowManager(), imgsViews);
        // let's play!
        newGame();
    }

    private void initHorseImgsArray() {
        horseImages = HorseImgsProvider.INSTANCE.getHorseImagesList();
    }

    private void initSoundsMap() {
        soundsMap = SoundsProvider.INSTANCE.getSoundsMap();
    }

    private void makeToast(String msj){
        Toast.makeText(this, msj, Toast.LENGTH_SHORT).show();
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
            makeToast("¡Felicitaciones! Encontraste a: " + generateWordToShow());
            sounds.add((Integer) soundsMap.get("tada"));
            // play again
            newGame();
        } else {
            this.makeToast("Intenta nuevamente");
            sounds.add((Integer) soundsMap.get("error"));
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
            // obtener iniciales del caballo a reproducir
            String[] wordArray = splitString("_", horseToFindName);
            // icon_sound chain w razaInit n pelajeInit
            ArrayList<Integer> sounds = new ArrayList<>();
            sounds.add((Integer)soundsMap.get( getFirstStringChar(wordArray[0])) );
            sounds.add((Integer)soundsMap.get( getFirstStringChar(wordArray[1])) );
            SoundsPlayer.wannaPlaySound(sounds, this);
        }else{
            // an image view was clicked
            selectedImageView = (ImageView) view;
            // show the horse associated with the clicked image view
            selectedHorseImgTag.setText((String)selectedImageView.getTag());
            this.validateImage();
        }
    }


    private void fillImgsViewsArray() {
        imgsViews = new ArrayList<>();
        // add each img view to an imgViews array
        imgsViews.add((ImageView)findViewById(R.id.imageView1));
        imgsViews.add((ImageView)findViewById(R.id.imageView2));
        imgsViews.add((ImageView)findViewById(R.id.imageView3));
        imgsViews.add((ImageView)findViewById(R.id.imageView4));
    }

    private void resetImgViewsTags(){
        for (int i = 0; i < imgsViews.size(); i++) {
            ImageView imageView = imgsViews.get(i);
            imageView.setTag("");
        }
    }

    private int randomHorseImgId(){
        return (int) horseImages.get( random.nextInt(horseImages.size()) );
    }

    private String getResourceNameById(int id){
        Resources resources = this.getResources();
        return resources.getResourceEntryName(id);
    }

    private String[] splitString(String delimiter, String word){
        return word.split(new StringBuilder().append("\\").append(delimiter).toString());
    }

    private String generateWordToShow() {
        String[] array = splitString("_", horseToFindName);
        return capitalizeString(array[0]) + " " + capitalizeString(array[1]);
    }

    private String getFirstStringChar(String word){
        return word.substring(0, 1);
    }

    private String capitalizeString(String word){
        return getFirstStringChar(word).toUpperCase() + word.substring(1);
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
            int randomHorseImgId = this.randomHorseImgId();
            while(isAlreadyInImgViews( getResourceNameById(randomHorseImgId)) ){
                randomHorseImgId = this.randomHorseImgId();
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void newGame() {
        // reset tags
        resetImgViewsTags();
        // determine hose to find
        horseToFindId = this.randomHorseImgId();
        while(horseToFindId == lastHorseId){
            horseToFindId = this.randomHorseImgId();
        }
        lastHorseId = horseToFindId;
        horseToFindName = getResourceNameById(horseToFindId);
        // show in ui
        horseToFindNameShown.setText(generateWordToShow());
        selectedHorseImgTag.setText("");
        // populate img views with random imgs
        initImgViewsArray();
        // put a random img view with the answer img ONLY if it isn't shown yet
        putAnswerInGame();
    }


}
