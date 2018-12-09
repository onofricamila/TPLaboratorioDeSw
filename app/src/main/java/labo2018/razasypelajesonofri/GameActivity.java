package labo2018.razasypelajesonofri;

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
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView wordShown;
    private EditText wordSelected;
    private Button newGame;
    private String wordToFind;
    private MediaPlayer mp;
    private List<ImageView> imgsViews;
    private List<String> razas, pelajes;
    private ImageView sound, selectedImageView;
    private Random random = new Random();
    private String lastWord;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        // get layout text views
        wordShown = (TextView) findViewById(R.id.wordTv);
        wordSelected = (EditText) findViewById(R.id.wordEnteredEt);
        // get newgame button and set listener
        newGame = (Button) findViewById(R.id.newGame);
        newGame.setOnClickListener(this);
        // get siguiente button and set listener
        sound = (ImageView) findViewById(R.id.sound);
        sound.setOnClickListener(this);
        // init list of razas n pelajes
        this.initRnP();
        // get imgviews from layout
        this.fillImgsViewsArray();
        // set imgviews sizes
        this.determineImgViewsSize();
        // let's play!
        newGame();
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
        int imgHeight = (int) (screenHeight* 0.22); // 12% of screen.
        int imgWidth = (int) (screenWidth* 0.40); // 30% of screen.
        // set each img view size
        for (int i = 0; i < imgsViews.size(); i++) {
            ImageView imageView = imgsViews.get(i);
            imageView.getLayoutParams().height = imgHeight;
            imageView.getLayoutParams().width = imgWidth;
        }
    }

    private void setTagsToEmpty(){
        for (int i = 0; i < imgsViews.size(); i++) {
            ImageView imageView = imgsViews.get(i);
            imageView.setTag("");
        }
    }

    private void initRnP() {
        razas = new ArrayList<String>();
        razas.add("falabella");
        razas.add("percheron");
        razas.add("criollo");
        razas.add("azteca");

        pelajes = new ArrayList<String>();
        pelajes.add("blanco");
        pelajes.add("marron");
        pelajes.add("negro");
        pelajes.add("matizado");
    }

    private String randomWord(){
        String randomRaza = razas.get( random.nextInt(razas.size()) );
        String randomPelaje = pelajes.get( random.nextInt(pelajes.size()) );
        return randomRaza + "_" + randomPelaje;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initImgViewsArray() {
        // fill each img view w a unique picture
        for (int i = 0; i < imgsViews.size(); i++) {
            String randomWord = this.randomWord();
            Log.d("!!!FILL-IMGS-VIEWS: ", randomWord);
            while(isAlreadyInImgViews(randomWord)){
                randomWord = this.randomWord();
            }
            String name = "@drawable/" + randomWord;
            ImageView imageView = imgsViews.get(i);
            imageView.setTag(randomWord);
            Log.d("!!!TAG: ", imageView.getTag().toString());
            imageView.setImageResource(getResources().getIdentifier(name, null, this.getPackageName()));
            imageView.setOnClickListener(this);
        }
        // DEBUG aca se ven las 'current' tags
        for (int i = 0; i < imgsViews.size(); i++) {
            Log.d("!!!DEBUG-TAGS: ", imgsViews.get(i).getTag().toString());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private boolean isAlreadyInImgViews(String randomWord) {
        Predicate<ImageView> p1 = iv -> iv.getTag().equals(randomWord);
        boolean cond = imgsViews.stream().anyMatch(p1);
        Log.d("!!!IS-USED: ", String.valueOf(cond));
        return cond;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        // newGame button was clicked
        if (view == newGame) {
            newGame();
        }else if(view == sound){
           // TODO: replace w wordToFindSound, like: String name = "@raw/" + wordToFind;
           String name = "@raw/tada";
           this.playSound( getResources().getIdentifier(name, null, this.getPackageName()) );
        }else{
            // an image view was clicked
            selectedImageView = (ImageView) view;
            // show the horse associated with the clicked image view
            wordSelected.setText((String)selectedImageView.getTag());
            this.validateImage();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void validateImage() {
        if (wordToFind.equals(selectedImageView.getTag())) {
            this.makeToast("¡Felicitaciones! Encontraste " + this.generateWordToShow());
            this.playSound(R.raw.tada);
            // play again
            newGame();
        } else {
            this.makeToast("Intenta nuevamente");
            this.playSound(R.raw.error);
        }
    }

    private void makeToast(String msj){
        Toast.makeText(this, msj, Toast.LENGTH_SHORT).show();
    }

    private void playSound(int sound){
        mp = MediaPlayer.create(this, sound);
        mp.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void newGame() {
        // reset tags
        this.setTagsToEmpty();
        // determine word to find
        wordToFind = this.randomWord();
        while(wordToFind == lastWord){
            wordToFind = this.randomWord();
        }
        lastWord = wordToFind;
        // show in ui
        wordShown.setText(generateWordToShow());
        wordSelected.setText("");
        // populate img views with imgs
        this.initImgViewsArray();
        // wordTofind used?
        Log.d("!!!WORD-TO-FIND: ", wordToFind);
        this.isAlreadyInImgViews(wordToFind);
        // show a random img view with the answer img ONLY if it isn't shown yet
        this.putAnswerInGame();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void putAnswerInGame() {
        if (!isAlreadyInImgViews(wordToFind)) {
            ImageView special = imgsViews.get(random.nextInt(imgsViews.size()));
            special.setTag(wordToFind);
            String name = "@drawable/" + wordToFind;
            special.setImageResource(getResources().getIdentifier(name, null, this.getPackageName()));
        }
    }

    private String generateWordToShow() {
        String[] array = wordToFind.split("\\_");
        return capitalize(array[0]) + " " + capitalize(array[1]);
    }

    private String capitalize(String word){
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }
}
