package labo2018.razasypelajesonofri;

import android.content.res.Resources;
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
import java.util.Random;
import java.util.function.Predicate;

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
    private MediaPlayer mp;
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
        this.initHorseImgsArray();
        // init sounds map
        this.initSoundsMap();
        // get imgviews from layout
        this.fillImgsViewsArray();
        // set imgviews sizes
        this.determineImgViewsSize();
        // let's play!
        newGame();
    }

    private void initSoundsMap() {
        soundsMap = new HashMap();
        soundsMap.put("tada", R.raw.tada);
        soundsMap.put("error", R.raw.error);
        soundsMap.put("a", R.raw.a);
        soundsMap.put("b", R.raw.b);
        soundsMap.put("c", R.raw.c);
        soundsMap.put("f", R.raw.f);
        soundsMap.put("m", R.raw.m);
        soundsMap.put("n", R.raw.n);
        soundsMap.put("p", R.raw.p);
    }

    private void makeToast(String msj){
        Toast.makeText(this, msj, Toast.LENGTH_SHORT).show();
    }

    private void playSound(int soundId){
        mp = MediaPlayer.create(this, soundId);
        mp.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void validateImage() {
        /* TODO: para el razas y pelajes por separado aca en horsetofindname me viene la raza o
        el pelaje (haria un random entre las 2 partes del nombre de la foto agarrada como answer en newgame)
        y tipo para decir si está bien la eleccion tengo que fijarme si horsetofindname es substring
        del tag de la img seleccionada ... hay mas caballos que pueden coincidir en pelaje o raza!
        */
        if (horseToFindName.equals(selectedImageView.getTag())) {
            this.makeToast("¡Felicitaciones! Encontraste a: " + this.generateWordToShow());
            this.playSound((Integer) soundsMap.get("tada"));
            // play again
            newGame();
        } else {
            this.makeToast("Intenta nuevamente");
            this.playSound((Integer) soundsMap.get("error"));
        }
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
            // sound chain w razaInit n pelajeInit
            int[] sounds = {
                    (int) soundsMap.get(getFirstStringChar(wordArray[0])),
                    (int) soundsMap.get(getFirstStringChar(wordArray[1]))
            };
            playSoundChain(sounds);
        }else{
            // an image view was clicked
            selectedImageView = (ImageView) view;
            // show the horse associated with the clicked image view
            selectedHorseImgTag.setText((String)selectedImageView.getTag());
            this.validateImage();
        }
    }
    
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void playSoundChain(int[] sounds){
        MediaPlayer[] mediaPlayers = new MediaPlayer[sounds.length];
        // create MP array with respective sounds
        for (int i = 0; i < mediaPlayers.length; i++) {
            mediaPlayers[i] = MediaPlayer.create( this, sounds[i] );
        }
        // create sound chain
        for (int i = 0; i < mediaPlayers.length-1; i++) {
            mediaPlayers[i].setNextMediaPlayer( mediaPlayers[i+1] );
        }
        // start sound chain
        mediaPlayers[0].start();
    }

    private void initHorseImgsArray() {
        horseImages = new ArrayList();
        horseImages.add(R.drawable.azteca_blanco);
        horseImages.add(R.drawable.azteca_marron);
        horseImages.add(R.drawable.azteca_matizado);
        horseImages.add(R.drawable.azteca_negro);

        horseImages.add(R.drawable.criollo_blanco);
        horseImages.add(R.drawable.criollo_marron);
        horseImages.add(R.drawable.criollo_matizado);
        horseImages.add(R.drawable.criollo_negro);

        horseImages.add(R.drawable.falabella_blanco);
        horseImages.add(R.drawable.falabella_marron);
        horseImages.add(R.drawable.falabella_matizado);
        horseImages.add(R.drawable.falabella_negro);

        horseImages.add(R.drawable.percheron_blanco);
        horseImages.add(R.drawable.percheron_marron);
        horseImages.add(R.drawable.percheron_matizado);
        horseImages.add(R.drawable.percheron_negro);
    }

    private void fillImgsViewsArray() {
        imgsViews = new ArrayList<>();
        // add each img view to an imgViews array
        imgsViews.add((ImageView)findViewById(R.id.imageView1));
        imgsViews.add((ImageView)findViewById(R.id.imageView2));
        imgsViews.add((ImageView)findViewById(R.id.imageView3));
        imgsViews.add((ImageView)findViewById(R.id.imageView4));
    }

    private void determineImgViewsSize() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        // get screen size
        int screenHeight = displaymetrics.heightPixels;
        int screenWidth = displaymetrics.widthPixels;
        // calc img desidered size
        int imgHeight = (int) (screenHeight* 0.22); // 22% of screen.
        int imgWidth = (int) (screenWidth* 0.40); // 40% of screen.
        // set each img view size
        for (int i = 0; i < imgsViews.size(); i++) {
            ImageView imageView = imgsViews.get(i);
            imageView.getLayoutParams().height = imgHeight;
            imageView.getLayoutParams().width = imgWidth;
        }
    }

    private void setTagsToEmptyString(){
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
        return capitalize(array[0]) + " " + capitalize(array[1]);
    }

    private String getFirstStringChar(String word){
        return word.substring(0, 1);
    }

    private String capitalize(String word){
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
        this.setTagsToEmptyString();
        // determine word to find
        horseToFindId = this.randomHorseImgId();
        while(horseToFindId == lastHorseId){
            horseToFindId = this.randomHorseImgId();
        }
        lastHorseId = horseToFindId;
        horseToFindName = getResourceNameById(horseToFindId);
        // show in ui
        horseToFindNameShown.setText(generateWordToShow());
        selectedHorseImgTag.setText("");
        // populate img views with imgs
        this.initImgViewsArray();
        // put a random img view with the answer img ONLY if it isn't shown yet
        this.putAnswerInGame();
    }


}
