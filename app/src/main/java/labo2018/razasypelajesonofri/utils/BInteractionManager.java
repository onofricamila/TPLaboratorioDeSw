package labo2018.razasypelajesonofri.utils;

import android.os.Build;
import android.support.annotation.RequiresApi;
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
        initPossibleAnswersContainersArray();

    }

    @Override
    protected void initPossibleAnswersContainersArray() {
        fillHorsesImageViewsArray();
        // onclick listener
        setViewListItemsOnClickHandler(imageViews);

    }

    private void fillHorsesImageViewsArray() {
        imageViews = new ArrayList<>();
        // add each img view to an imgViews array
        imageViews.add(this.context.findViewById(R.id.horseImageView1));
        imageViews.add(this.context.findViewById(R.id.horseImageView2));
        imageViews.add(this.context.findViewById(R.id.horseImageView3));
        imageViews.add(this.context.findViewById(R.id.horseImageView4));
    }

    @Override
    public void resetViewsTags(){
        resetViewsTags(imageViews);
    }


    @Override
    public void showWhatToLookFor() {
        super.showWhatToLookFor();
        horseToFindTextView.setText(whatToLookFor.toUpperCase());

    }

    @Override
    public void showPossibleAnswers() {
        showPossibleAnswers(imageViews);
    }

    @Override
    protected void manageViewsListItem(Horse randomHorse, int i) {
        ImageView imageView = imageViews.get(i);
        imageView.setImageResource(randomHorse.getImage());
    }

    @Override
    public void putAnswerInGame() {
        // si no hay nada que repreente a lo que estoy buscando, subo la foto elegida como respuesta
        if ( !isAlreadyInImgViews(horseToFind, imageViews) ){
            Collections.shuffle(imageViews);
            ImageView randomImgView = imageViews.get(0);
            randomImgView.setTag(horseToFind);
            randomImgView.setImageResource(horseToFind.getImage());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void playHorseToFindSound() {
        ArrayList<Integer> sounds = new ArrayList<>();
        if( searchingForType ){
            sounds.add(horseToFind.getFemSpeciesSound());
        }else if( searchingForHairType ){
            sounds.add(horseToFind.getFemHairTypeSound());
        }else if( searchingForFullName ){
            sounds.add(horseToFind.getFemSpeciesSound());
            sounds.add(horseToFind.getFemHairTypeSound());
        }
        SoundsPlayer.wannaPlaySound(sounds, this.context);
    }

    protected Boolean viewValidationCondition() {
        return ( ((Horse)selectedImageView.getTag()).getFullName() ) .contains(whatToLookFor);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void manageOnClick(View view) {
        if (view == soundImgView){
            ((ImageView)this.context.findViewById(R.id.soundImgView))
                    .setImageResource(R.drawable.ic_audio_click);
            playHorseToFindSound();
        }else{
            // an image view was clicked
            selectedImageView = (ImageView) view;
            validateView();
        }
    }



}
