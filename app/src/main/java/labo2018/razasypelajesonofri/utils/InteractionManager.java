package labo2018.razasypelajesonofri.utils;


import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
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

    public void resetViewsTags(List<? extends View> list){
        for (int i = 0; i < list.size(); i++) {
            View view = list.get(i);
            view.setTag("");
        }
    }

    public abstract void showPossibleAnswers();

    public void showPossibleAnswers(List<? extends View> list) {
        // fill each img view w a unique picture
        for (int i = 0; i < list.size(); i++) {
            int randomHorseImgId = HorseImgsProvider.INSTANCE.randomHorseImgId();
            // we dont wanna have the same horse image twice
            // TODO: PARA INTERAC A) SE REPITEN RAZAS O PELAJES .. PORQUE AGARRA DE DISTINTAS IMGS ..
            while(this.isAlreadyInImgViews( this.getResourceNameById(randomHorseImgId), list) ){
                randomHorseImgId = HorseImgsProvider.INSTANCE.randomHorseImgId();
            }
            manageViewsListItem(list.get(i), randomHorseImgId, i);
        }
        // DEBUG aca se ven las 'current' tags
        for (int i = 0; i < list.size(); i++) {
            Log.d("!!!DEBUG-CURRENT-TAGS: ", list.get(i).getTag().toString());
        }
    }

    protected abstract void manageViewsListItem(View view, int horseImgId, int randomHorseImgId);

    protected void setViewListItemsOnClickHandler(List<? extends View> list) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setOnClickListener(this.context);
        }
    }

    public abstract void resetViewsTags();

    public  void showWhatToLookFor(){
        Log.d("!!!WHAT-TO-LOOK-FOR", whatToLookFor);
    };

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


    protected abstract void initPossibleAnswersContainersArray();

    public void informAboutAnswer(int horseToFindId, String answerHorseImgName, String whatToLookFor) {
        this.horseToFindId = horseToFindId;
        this.answerHorseImgName = answerHorseImgName;
        this.whatToLookFor = whatToLookFor;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected void playHorseSound(String string) {
        // obtener array de strings a partir de lo que busco
        String[] wordArray = StringsManager.splitString(string,"_");
        // sound chain
        ArrayList<Integer> sounds = new ArrayList<>();
        for (int i = 0; i < wordArray.length; i++) {
            sounds.add(SoundsProvider.INSTANCE.getSoundAt( wordArray[i] ));
        }
        SoundsPlayer.wannaPlaySound(sounds, this.context);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected void validateView(){
        ArrayList<Integer> sounds = new ArrayList<>();
        if ( viewValidationCondition()){
            sounds.add(SoundsProvider.INSTANCE.getSoundAt("relincho"));
            // play again
            this.context.newGame();
        } else {
            sounds.add(SoundsProvider.INSTANCE.getSoundAt("resoplido"));
        }
        SoundsPlayer.wannaPlaySound(sounds, this.context);
    }

    protected abstract Boolean viewValidationCondition();
}
