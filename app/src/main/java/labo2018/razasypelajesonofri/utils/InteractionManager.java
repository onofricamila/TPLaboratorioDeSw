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
        this.horseToFind = new Horse();
        this.whatToLookFor = "";
        searchingForType = false;
        searchingForHairType = false;
        searchingForFullName = false;
    }


    protected void setViewListItemsOnClickHandler(List<? extends View> list) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setOnClickListener(this.context);
        }
    }

    public void resetViewsTags(List<? extends View> list){
        for (int i = 0; i < list.size(); i++) {
            View view = list.get(i);
            view.setTag(new Horse());
        }
    }

    public void informAboutWhatToLookFor(Horse horseToFind, String whatToLookFor, Boolean searchingForType,
                                         Boolean searchingForHairType, Boolean searchingForFullName){
        this.horseToFind = horseToFind;
        this.whatToLookFor = whatToLookFor;
        this.searchingForType = searchingForType;
        this.searchingForHairType = searchingForHairType;
        this.searchingForFullName = searchingForFullName;
    }

    public  void showWhatToLookFor(){
        Log.d("!!!WHAT-TO-LOOK-FOR", whatToLookFor);
    }

    public void showPossibleAnswers(List<? extends View> views) {
        for (int i = 0; i < views.size(); i++) {
            Horse randomHorse = HorsesProvider.INSTANCE.randomHorse();
            // we dont wanna have the same horse attribute twice
            while(this.isAlreadyInViews( randomHorse, views) ){
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

    protected boolean isAlreadyInViews(Horse horse, List<? extends View> views) {
        boolean cond = false;
        for (View view : views) {
            if ( isAlreadyInViewsCondition(horse, view) ) {
                cond = true;
                break;
            }
        }
        return cond;
    }

    protected Boolean isAlreadyInViewsCondition(Horse horse, View view){
        if( searchingForType ){
            return (((Horse)view.getTag()).getType())
                                        .equals(horse.getType());
        }else if( searchingForHairType ){
            return (((Horse)view.getTag()).getHairType())
                                        .equals(horse.getHairType());
        }
        return (((Horse)view.getTag()).getFullName())
                                        .equals(horse.getFullName());
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected void validateView(){
        ArrayList<Integer> sounds = new ArrayList<>();
        if ( viewValidationCondition()){
            sounds.add(SoundsProvider.INSTANCE.getSoundAt("success"));
            // play again
            this.context.newGame();
        } else {
            sounds.add(SoundsProvider.INSTANCE.getSoundAt("error"));
        }
        SoundsPlayer.wannaPlaySound(sounds, this.context);
    }

    protected abstract void initPossibleAnswersContainersArray();

    public abstract void resetViewsTags();

    public abstract void showPossibleAnswers();

    protected abstract void manageViewsListItem(Horse horseImgId, int randomHorseImgId);

    public abstract void putAnswerInGame();

    public abstract void manageOnClick(View view);

    protected abstract Boolean viewValidationCondition();

}
