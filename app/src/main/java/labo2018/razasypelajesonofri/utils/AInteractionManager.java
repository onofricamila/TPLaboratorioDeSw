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
        fillPossibleAnswersArray();
    }

    @Override
    public void resetViewsTags() {
        for (int i = 0; i < soundsImageViews.size(); i++) {
            ImageView imgView = soundsImageViews.get(i);
            imgView.setTag("");
        }
        for (int i = 0; i < horsesTextViews.size(); i++) {
            TextView textView = horsesTextViews.get(i);
            textView.setTag("");
        }
    }

    @Override
    public void showWhatToLookFor() {
        Log.d("!!!!!!!!!", whatToLookFor);
        horseToFindImgView.setImageResource(horseToFindId);
    }

    @Override
    public void showPossibleAnswers() {
// fill each img view w a unique picture
        for (int i = 0; i < horsesTextViews.size(); i++) {
            int randomHorseImgId = HorseImgsProvider.INSTANCE.randomHorseImgId();
            // we dont wanna have the same horse image twice
            while(this.isAlreadyInImgViews( this.getResourceNameById(randomHorseImgId), horsesTextViews) ){
                randomHorseImgId = HorseImgsProvider.INSTANCE.randomHorseImgId();
            }
            TextView textView = horsesTextViews.get(i);
            setText(textView, getResourceNameById(randomHorseImgId));
            textView.setOnClickListener(this.context);
            soundsImageViews.get(i).setTag( textView.getTag() );
        }
        // DEBUG aca se ven las 'current' tags
        for (int i = 0; i < horsesTextViews.size(); i++) {
            Log.d("!!!DEBUG-CURRENT-TAGS: ", horsesTextViews.get(i).getTag().toString());
        }
    }

    private void setText(TextView textView, String completeName) {
        String[] lala = StringsManager.splitString(completeName, "_");
        if( HorseImgsProvider.INSTANCE.isAHorseType(this.context, whatToLookFor) ){
            textView.setTag(lala[0]);
            textView.setText(StringsManager.generateWordToShow(lala[0], "_"));
        }else{
            textView.setTag(lala[1]);
            textView.setText(StringsManager.generateWordToShow(lala[1], "_"));

        }
    }

    @Override
    public void putAnswerInGame() {
        // si no hay nada que repreente a lo que estoy buscando, subo la respuesta
        if ( !isAlreadyInImgViews(whatToLookFor, horsesTextViews) ){
            Random random = new Random();
            Integer randIndex = random.nextInt(horsesTextViews.size());
            TextView randomTextView = horsesTextViews.get(randIndex);
            setText(randomTextView, answerHorseImgName);
            soundsImageViews.get(randIndex).setTag( randomTextView.getTag() );
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void manageOnClick(View view) {
        Boolean wasASound = false;
        for (ImageView soundImgView: soundsImageViews) {
            if (view == soundImgView) {
                wasASound = true;
                playHorseSound(view);
            }
        }
        if(!wasASound){
            // an image view was clicked
            selectedTextView = (TextView) view;
            validateTextView();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void playHorseSound(View view) {
        // obtener array de strings a partir de lo que busco
        String[] wordArray = StringsManager.splitString( (String)view.getTag(),"_" );
        // sound chain
        ArrayList<Integer> sounds = new ArrayList<>();
        for (int i = 0; i < wordArray.length; i++) {
            sounds.add(SoundsProvider.INSTANCE.getSoundAt( wordArray[i] ));
        }
        SoundsPlayer.wannaPlaySound(sounds, this.context);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void validateTextView() {
        ArrayList<Integer> sounds = new ArrayList<>();
        if ( ((String)selectedTextView.getTag()).contains(whatToLookFor) ){
            sounds.add(SoundsProvider.INSTANCE.getSoundAt("relincho"));
            // play again
            this.context.newGame();
        } else {
            sounds.add(SoundsProvider.INSTANCE.getSoundAt("resoplido"));
        }
        SoundsPlayer.wannaPlaySound(sounds, this.context);
    }


    @Override
    protected void fillPossibleAnswersArray() {
        fillHorsesTextViewsArray();
        fillSoundsImgViewsArray();
        setSoundsImgViewsOnClickHandler();
    }

    private void setSoundsImgViewsOnClickHandler() {
        for (int i = 0; i < soundsImageViews.size(); i++) {
            soundsImageViews.get(i).setOnClickListener(this.context);
        }
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
}
