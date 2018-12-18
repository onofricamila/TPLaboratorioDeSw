package labo2018.razasypelajesonofri.utils;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import labo2018.razasypelajesonofri.GameActivity;
import labo2018.razasypelajesonofri.R;

public class AInteractionManager extends InteractionManager {
    private ImageView horseToFindImgView;
    private TextView selectedTextView;
    private List<TextView> horsesTextViews;
    private List<ImageView> soundsImageViews;

    public AInteractionManager(GameActivity context, Boolean playingLevel2) {
        super(context, playingLevel2);
        horseToFindImgView = this.context.findViewById(R.id.horseImgShown);
        initPossibleAnswersContainersArray();
    }


    @Override
    protected void initPossibleAnswersContainersArray() {
        fillHorsesTextViewsArray();
        setViewListItemsOnClickHandler(horsesTextViews);
        fillSoundsImgViewsArray();
        setSoundsImgViewsBackgroundImage();
        setViewListItemsOnClickHandler(soundsImageViews);
    }


    private void fillSoundsImgViewsArray() {
        if (playingLevel2){
            fillSoundsImgViewsArrayL2();
        }
        else{
            fillSoundsImgViewsArrayL1();
        }
    }

   private void fillSoundsImgViewsArrayL2() {
        soundsImageViews = new ArrayList<>();
        soundsImageViews.add(this.context.findViewById(R.id.soundImgView1));
        soundsImageViews.add(this.context.findViewById(R.id.soundImgView2));
        soundsImageViews.add(this.context.findViewById(R.id.soundImgView3));
        soundsImageViews.add(this.context.findViewById(R.id.soundImgView4));
    }

    private void fillSoundsImgViewsArrayL1() {
        soundsImageViews = new ArrayList<>();
        soundsImageViews.add(this.context.findViewById(R.id.soundImgView2));
        soundsImageViews.add(this.context.findViewById(R.id.soundImgView3));
    }

    private void setSoundsImgViewsBackgroundImage() {
        for (int i = 0; i < soundsImageViews.size(); i++) {
            soundsImageViews.get(i).setImageResource(R.drawable.ic_audio_regular);
        }
    }

    private void fillHorsesTextViewsArray() {
        if (playingLevel2){
            fillHorsesTextViewsArrayL2();
        }
        else{
            fillHorsesTextViewsArrayL1();
        }
    }

    private void fillHorsesTextViewsArrayL2() {
        horsesTextViews = new ArrayList<>();
        horsesTextViews.add(this.context.findViewById(R.id.horseTextView1));
        horsesTextViews.add(this.context.findViewById(R.id.horseTextView2));
        horsesTextViews.add(this.context.findViewById(R.id.horseTextView3));
        horsesTextViews.add(this.context.findViewById(R.id.horseTextView4));
    }

    private void fillHorsesTextViewsArrayL1() {
        horsesTextViews = new ArrayList<>();
        horsesTextViews.add(this.context.findViewById(R.id.horseTextView2));
        horsesTextViews.add(this.context.findViewById(R.id.horseTextView3));
    }

    @Override
    public void resetViewsTags() {
        resetViewsTags(soundsImageViews);
        resetViewsTags(horsesTextViews);
    }

    @Override
    public void showWhatToLookFor() {
        super.showWhatToLookFor();
        setImageResource(horseToFindImgView, horseToFind.getImage());
    }

    @Override
    public void showPossibleAnswers() {
       showPossibleAnswers(horsesTextViews);
    }

    @Override
    protected void manageViewsListItem(Horse randomHorse, int i) {
        TextView textView = horsesTextViews.get(i);
        setText(textView, randomHorse);
        soundsImageViews.get(i).setTag( textView.getTag() );
    }

    private void setText(TextView textView, Horse horse) {
        if( searchingForType ){
            textView.setText(horse.getType().toUpperCase());
        }else if( searchingForHairType ){
            textView.setText(horse.getHairType().toUpperCase());
        }else if( searchingForFullName ){
            textView.setText(horse.getFullName().toUpperCase());
        }
    }

    @Override
    public void putAnswerInGame() {
        // if there is nothing matching the answer, upload horseToFind
        if ( !isAlreadyInViews(horseToFind, horsesTextViews) ){
            Random random = new Random();
            Integer randIndex = random.nextInt(horsesTextViews.size());
            TextView randomTextView = horsesTextViews.get(randIndex);
            randomTextView.setTag(horseToFind);
            setText(randomTextView, horseToFind);
            soundsImageViews.get(randIndex).setTag( horseToFind );
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void playSelectedHorseSound(View view) {
       playHorseSound( (Horse)view.getTag() );
    }

    protected Boolean viewValidationCondition() {
        return viewValidationCondition(selectedTextView);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void manageOnClick(View view) {
        Boolean wasASound = false;
        for (ImageView soundImgView: soundsImageViews) {
            if (view == soundImgView) {
                wasASound = true;
                ( (ImageView)this.context.findViewById(view.getId()) )
                        .setImageResource(R.drawable.ic_audio_click);
                playSelectedHorseSound(view);
            }
        }
        if(!wasASound){
            // an image view was clicked
            selectedTextView = (TextView) view;
            validateView();
        }
    }

}
