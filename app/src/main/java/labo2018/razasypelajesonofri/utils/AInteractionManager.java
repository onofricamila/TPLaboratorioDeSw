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
        horseToFindImgView.setImageResource(horseToFindId);
    }

    @Override
    public void showPossibleAnswers() {
       showPossibleAnswers(horsesTextViews);
    }

    @Override
    protected void manageViewsListItem(View view, int randomHorseImgId, int i) {
        TextView textView = (TextView) view;
        setText(textView, getResourceNameById(randomHorseImgId));
        soundsImageViews.get(i).setTag( textView.getTag() );
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
            validateView();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void playHorseSound(View view) {
        playHorseSound( (String)view.getTag() );
    }

    protected Boolean viewValidationCondition() {
        return ((String)selectedTextView.getTag()).contains(whatToLookFor);
    }



}
