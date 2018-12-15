package labo2018.razasypelajesonofri.utils;


import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import labo2018.razasypelajesonofri.GameActivity;

public abstract class InteractionManager {
    protected GameActivity context;
    protected String whatToLookFor;
    protected Horse horseToFind;
    protected Boolean searchingForType;
    protected Boolean searchingForHairType;
    protected Boolean searchingForFullName;

    public InteractionManager(GameActivity context) {
        this.context = context;
        this.whatToLookFor = "";
    }

    public void resetViewsTags(List<? extends View> list){
        for (int i = 0; i < list.size(); i++) {
            View view = list.get(i);
            view.setTag(new Horse());
        }
    }

    public abstract void showPossibleAnswers();

    public void showPossibleAnswers(List<? extends View> views) {
        // fill each img view w a unique picture
        for (int i = 0; i < views.size(); i++) {
            Horse randomHorse = HorsesProvider.INSTANCE.randomHorse();
            // we dont wanna have the same horse image twice
            // TODO: PARA INTERAC AyB) SE REPITEN RAZAS O PELAJES .. PORQUE AGARRA DE DISTINTAS IMGS ..
            while(this.isAlreadyInImgViews( randomHorse, views) ){
                randomHorse = HorsesProvider.INSTANCE.randomHorse();
            }
            views.get(i).setTag(randomHorse);
            manageViewsListItem(randomHorse, i);
        }
        // DEBUG aca se ven las 'current' tags
        for (int i = 0; i < views.size(); i++) {
            Log.d("!!!DEBUG-CURRENT-TAGS: ", views.get(i).getTag().toString());
        }
    }

    protected abstract void manageViewsListItem(Horse horseImgId, int randomHorseImgId);

    protected void setViewListItemsOnClickHandler(List<? extends View> list) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setOnClickListener(this.context);
        }
    }

    public abstract void resetViewsTags();

    public  void showWhatToLookFor(){
        Log.d("!!!WHAT-TO-LOOK-FOR", whatToLookFor);
    }

    protected boolean isAlreadyInImgViews(Horse horse, List<? extends View> array) {
        boolean cond = false;
        for (View view : array) {
            if ( isAlreadyInImgsViewsCondition(horse, view) ) {
                cond = true;
                break;
            }
        }
        return cond;
    }

    protected Boolean isAlreadyInImgsViewsCondition(Horse horse, View view){
        if( searchingForType ){
            return  (((Horse)view.getTag()).getType()) .equals(horse.getType());
        }else if( searchingForHairType ){
            return  (((Horse)view.getTag()).getHairType()) .equals(horse.getHairType());
        }
           return  (((Horse)view.getTag()).getFullName()) .equals(horse.getFullName());
    }

    public abstract void putAnswerInGame();

    public abstract void manageOnClick(View view);


    protected abstract void initPossibleAnswersContainersArray();

    public void informAboutAnswer(Horse horseToFind, String whatToLookFor) {
        this.horseToFind = horseToFind;
        this.whatToLookFor = whatToLookFor;
        searchingForType = false;
        searchingForHairType = false;
        searchingForFullName = false;

        if( HorsesProvider.INSTANCE.isAHorseType(whatToLookFor) ){
            searchingForType = true;
        }else if( HorsesProvider.INSTANCE.isAHorseHairType(whatToLookFor) ){
            searchingForHairType = true;
        }else{
            searchingForFullName = true;
        }
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
