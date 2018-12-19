package labo2018.razasypelajesonofri.utils.interaction;

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
import labo2018.razasypelajesonofri.utils.horses.Horse;

public class BInteractionManager extends InteractionManager {
    private TextView horseToFindTextView;
    private List<ImageView> imageViews;
    private ImageView soundImgView, selectedImageView;

    public BInteractionManager(GameActivity context, Boolean playingLevel2) {
        super(context, playingLevel2);
        horseToFindTextView = this.context.findViewById(R.id.wordShown);
        soundImgView = this.context.findViewById(R.id.soundImgView);
        soundImgView.setOnClickListener(this.context);
        initPossibleAnswersContainersArray();
    }

    @Override
    protected void initPossibleAnswersContainersArray() {
        fillHorsesImageViewsArray();
        // set onclick listener
        setViewListItemsOnClickHandler(imageViews);

    }

    private void fillHorsesImageViewsArray() {
        if (playingLevel2){
            fillHorsesImgViewsArrayL2();
        }
        else{
            fillHorsesImgViewsArrayL1();
        }
    }

    private void fillHorsesImgViewsArrayL2() {
        imageViews = new ArrayList<>();
        imageViews.add(this.context.findViewById(R.id.horseImageView1));
        imageViews.add(this.context.findViewById(R.id.horseImageView2));
        imageViews.add(this.context.findViewById(R.id.horseImageView3));
        imageViews.add(this.context.findViewById(R.id.horseImageView4));
    }

    private void fillHorsesImgViewsArrayL1() {
        imageViews = new ArrayList<>();
        imageViews.add(this.context.findViewById(R.id.horseImageView2));
        imageViews.add(this.context.findViewById(R.id.horseImageView3));
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
    protected void resetSoundImageToRegular() {
        soundImgView.setImageResource(R.drawable.ic_audio_regular);
    }

    @Override
    protected void manageViewsListItem(Horse randomHorse, int i) {
        ImageView imageView = imageViews.get(i);
        setImageResource(imageView, randomHorse.getImage());
    }

    @Override
    public void putAnswerInGame() {
        // if there is nothing matching the answer, upload horseToFind
        if ( !isAlreadyInViews(horseToFind, imageViews) ){
            Collections.shuffle(imageViews);
            ImageView randomImgView = imageViews.get(0);
            randomImgView.setTag(horseToFind);
            setImageResource(randomImgView, horseToFind.getImage());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void playHorseToFindSound() {
        playHorseSound(horseToFind);
    }

    protected Boolean viewValidationCondition() {
        return viewValidationCondition(selectedImageView);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void manageOnClick(View view) {
        if (view == soundImgView){
            setImageResource( ((ImageView)this.context.findViewById(R.id.soundImgView))
                                    , R.drawable.ic_audio_click);
            playHorseToFindSound();
        }else{
            // an image view was clicked
            selectedImageView = (ImageView) view;
            validateView();
        }
    }



}
