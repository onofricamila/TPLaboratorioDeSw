package labo2018.razasypelajesonofri.utils;


import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.List;

import labo2018.razasypelajesonofri.GameActivity;

public abstract class InteractionManager {
    protected GameActivity context;
    protected String answerHorseImgName, whatToLookFor;
    protected Integer horseToFindId;

    public InteractionManager(GameActivity context) {
        this.context = context;
        this.answerHorseImgName = "";
        this.whatToLookFor = "";
        this.horseToFindId = 0;
    }

    public abstract void resetViewsTags();

    public abstract void showWhatToLookFor();

    public abstract void showPossibleAnswers();

    protected String getResourceNameById(int id){
        Resources resources = this.context.getResources();
        return resources.getResourceEntryName(id);
    }

    protected boolean isAlreadyInImgViews(String word, List<? extends View> array) {
        boolean cond = false;
        for (View view : array) {
            if ( ((String)view.getTag()) .contains(word) ) {
                cond = true;
                break;
            }
        }
        return cond;
    }

    public abstract void putAnswerInGame();

    public abstract void manageOnClick(View view);


    protected abstract void fillPossibleAnswersArray();

    public void informAboutAnswer(int horseToFindId, String answerHorseImgName, String whatToLookFor) {
        this.horseToFindId = horseToFindId;
        this.answerHorseImgName = answerHorseImgName;
        this.whatToLookFor = whatToLookFor;
    }

}
