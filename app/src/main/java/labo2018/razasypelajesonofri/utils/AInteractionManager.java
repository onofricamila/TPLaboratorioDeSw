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

    public AInteractionManager(GameActivity context) {
        super(context);
        // get layout text view
        horseToFindImgView = this.context.findViewById(R.id.horseImgShown);
        // get horses text views from layout
        initPossibleAnswersContainersArray();
    }


    @Override
    protected void initPossibleAnswersContainersArray() {
        fillHorsesTextViewsArray();
        setViewListItemsOnClickHandler(horsesTextViews);
        fillSoundsImgViewsArray();
        setViewListItemsOnClickHandler(soundsImageViews);
    }

    private void fillSoundsImgViewsArray() {
        soundsImageViews = new ArrayList<>();
        // add each txt view to an txtViews array
        soundsImageViews.add(this.context.findViewById(R.id.soundImgView1));
        soundsImageViews.add(this.context.findViewById(R.id.soundImgView2));
        soundsImageViews.add(this.context.findViewById(R.id.soundImgView3));
        soundsImageViews.add(this.context.findViewById(R.id.soundImgView4));
    }

    private void fillHorsesTextViewsArray() {
        horsesTextViews = new ArrayList<>();
        // add each txt view to an txtViews array
        horsesTextViews.add(this.context.findViewById(R.id.horseTextView1));
        horsesTextViews.add(this.context.findViewById(R.id.horseTextView2));
        horsesTextViews.add(this.context.findViewById(R.id.horseTextView3));
        horsesTextViews.add(this.context.findViewById(R.id.horseTextView4));
        // DEBUG aca se ven las 'current' tags
        for (int i = 0; i < horsesTextViews.size(); i++) {
            Log.d("!!!DEBUG-CURRENT-TAGS: ", horsesTextViews.get(i).toString());
        }
    }

    @Override
    public void resetViewsTags() {
        resetViewsTags(soundsImageViews);
        resetViewsTags(horsesTextViews);
    }

    @Override
    public void showWhatToLookFor() {
        super.showWhatToLookFor();
        horseToFindImgView.setImageResource(horseToFind.getImage());
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
        // si no hay nada que repreente a lo que estoy buscando, subo la respuesta
        if ( !isAlreadyInImgViews(horseToFind, horsesTextViews) ){
            Random random = new Random();
            Integer randIndex = random.nextInt(horsesTextViews.size());
            TextView randomTextView = horsesTextViews.get(randIndex);
            randomTextView.setTag(horseToFind);
            setText(randomTextView, horseToFind);
            soundsImageViews.get(randIndex).setTag( horseToFind );
        }
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
                playHorseSound(view);
            }
        }
        if(!wasASound){
            // an image view was clicked
            selectedTextView = (TextView) view;
            validateView();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void playHorseSound(View view) {
        ArrayList<Integer> sounds = new ArrayList<>();
        if( searchingForType ){
            sounds.add( ((Horse)view.getTag()).getFemSpeciesSound()) ;
        }else if( searchingForHairType ){
            sounds.add( ((Horse)view.getTag()).getFemHairTypeSound() );
        }else if( searchingForFullName ){
            sounds.add( ((Horse)view.getTag()).getFemSpeciesSound()) ;
            sounds.add( ((Horse)view.getTag()).getFemHairTypeSound() );
        }
        SoundsPlayer.wannaPlaySound(sounds, this.context);
    }

    protected Boolean viewValidationCondition() {
        if( searchingForType ){
            return ( ((Horse)selectedTextView.getTag()).getType().equals(whatToLookFor) );
        }else if( searchingForHairType ){
            return ( ((Horse)selectedTextView.getTag()).getHairType().equals(whatToLookFor) );
        }else if( searchingForFullName ){
            return ( ((Horse)selectedTextView.getTag()).getFullName().equals(whatToLookFor) );
        }
        return ( selectedTextView.getTag().equals(whatToLookFor) );
    }



}
