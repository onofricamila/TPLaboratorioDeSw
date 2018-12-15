package labo2018.razasypelajesonofri.utils;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import labo2018.razasypelajesonofri.GameActivity;
import labo2018.razasypelajesonofri.R;

public class BInteractionManager extends InteractionManager {
    private TextView horseToFindTextView;
    private List<ImageView> imageViews;
    private ImageView soundImgView, selectedImageView;

    public BInteractionManager(GameActivity context) {
        super(context);
        // get layout text view
        horseToFindTextView = this.context.findViewById(R.id.wordShown);
        // get Sound ImgView and set listener
        soundImgView = this.context.findViewById(R.id.soundImgView);
        soundImgView.setOnClickListener(this.context);
        // get horses imgviews from layout
        fillPossibleAnswersArray();

    }

    @Override
    protected void fillPossibleAnswersArray() {
        imageViews = new ArrayList<>();
        // add each img view to an imgViews array
        imageViews.add(this.context.findViewById(R.id.horseImageView1));
        imageViews.add(this.context.findViewById(R.id.horseImageView2));
        imageViews.add(this.context.findViewById(R.id.horseImageView3));
        imageViews.add(this.context.findViewById(R.id.horseImageView4));
    }

    @Override
    public void resetViewsTags(){
        for (int i = 0; i < imageViews.size(); i++) {
            ImageView imageView = imageViews.get(i);
            imageView.setTag("");
        }
    }

    @Override
    public void showWhatToLookFor() {
        horseToFindTextView.setText(StringsManager.generateWordToShow(whatToLookFor, "_"));

    }

    @Override
    public void showPossibleAnswers() {
        // fill each img view w a unique picture
        for (int i = 0; i < imageViews.size(); i++) {
            int randomHorseImgId = HorseImgsProvider.INSTANCE.randomHorseImgId();
            // we dont wanna have the same horse image twice
            while(this.isAlreadyInImgViews( this.getResourceNameById(randomHorseImgId), imageViews) ){
                randomHorseImgId = HorseImgsProvider.INSTANCE.randomHorseImgId();
            }
            ImageView imageView = imageViews.get(i);
            imageView.setTag( getResourceNameById(randomHorseImgId) );
            imageView.setImageResource(randomHorseImgId);
            imageView.setOnClickListener(this.context);
        }
        // DEBUG aca se ven las 'current' tags
        for (int i = 0; i < imageViews.size(); i++) {
            Log.d("!!!DEBUG-CURRENT-TAGS: ", imageViews.get(i).getTag().toString());
        }
    }

    @Override
    public void putAnswerInGame() {
        // si no hay nada que repreente a lo que estoy buscando, subo la foto elegida como respuesta
        if ( !isAlreadyInImgViews(whatToLookFor, imageViews) ){
            Collections.shuffle(imageViews);
            ImageView randomImgView = imageViews.get(0);
            randomImgView.setTag(answerHorseImgName);
            randomImgView.setImageResource(horseToFindId);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void playHorseToFindSound() {
        // obtener array de strings a partir de lo que busco
        String[] wordArray = StringsManager.splitString(whatToLookFor,"_");
        // sound chain
        ArrayList<Integer> sounds = new ArrayList<>();
        for (int i = 0; i < wordArray.length; i++) {
            sounds.add(SoundsProvider.INSTANCE.getSoundAt( wordArray[i] ));
        }
        SoundsPlayer.wannaPlaySound(sounds, this.context);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void validateImage() {
        ArrayList<Integer> sounds = new ArrayList<>();
        if ( ((String)selectedImageView.getTag()).contains(whatToLookFor) ){
            sounds.add(SoundsProvider.INSTANCE.getSoundAt("relincho"));
            // play again
            this.context.newGame();
        } else {
            sounds.add(SoundsProvider.INSTANCE.getSoundAt("resoplido"));
        }
        SoundsPlayer.wannaPlaySound(sounds, this.context);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void manageOnClick(View view) {
        if (view == soundImgView){
            this.context.findViewById(R.id.soundImgView).setBackgroundResource(R.drawable.ic_audio_click);
            playHorseToFindSound();
        }else{
            // an image view was clicked
            selectedImageView = (ImageView) view;
            validateImage();
        }
    }



}
