package labo2018.razasypelajesonofri.utils.interaction;


import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;
import labo2018.razasypelajesonofri.GameActivity;
import labo2018.razasypelajesonofri.R;
import labo2018.razasypelajesonofri.utils.horses.Horse;
import labo2018.razasypelajesonofri.utils.horses.HorsesProvider;
import labo2018.razasypelajesonofri.utils.sounds.SoundsPlayer;
import labo2018.razasypelajesonofri.utils.sounds.SoundsProvider;

public abstract class InteractionManager {
    protected GameActivity context;
    protected String whatToLookFor;
    protected Horse horseToFind;
    protected Boolean searchingForType;
    protected Boolean searchingForHairType;
    protected Boolean searchingForFullName;
    protected HorsesProvider horsesProvider;
    protected Boolean listeningToFemAudio;
    protected Boolean playingLevel2;

    public InteractionManager(GameActivity context, Boolean playingLevel2) {
        this.context = context;
        this.horseToFind = new Horse();
        this.whatToLookFor = "";
        this.searchingForType = false;
        this.searchingForHairType = false;
        this.searchingForFullName = false;
        this.listeningToFemAudio = false;
        this.playingLevel2 = playingLevel2;
        this.horsesProvider = new HorsesProvider(this.context);
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
        Boolean searchingForHairType, Boolean searchingForFullName, Boolean listeningToFemAudio){
        this.horseToFind = horseToFind;
        this.whatToLookFor = whatToLookFor;
        this.searchingForType = searchingForType;
        this.searchingForHairType = searchingForHairType;
        this.searchingForFullName = searchingForFullName;
        this.listeningToFemAudio = listeningToFemAudio;
    }

    public void showWhatToLookFor(){
        //Log.d("!!!WHAT-TO-LOOK-FOR", whatToLookFor);
    }

    public void showPossibleAnswers(List<? extends View> views) {
        resetSoundImageToRegular();
        for (int i = 0; i < views.size(); i++) {
            Horse randomHorse = horsesProvider.randomHorse();
            // we dont wanna have the same horse attribute twice
            while(this.isAlreadyInViews( randomHorse, views) ){
                randomHorse = horsesProvider.randomHorse();
            }
            views.get(i).setTag(randomHorse);
            manageViewsListItem(randomHorse, i);
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
        if ( viewValidationCondition() ){
            sounds.add(SoundsProvider.INSTANCE.getSoundAt("success"));
            // assertions ++
            this.context.incrementAssertions();
        } else {
            sounds.add(SoundsProvider.INSTANCE.getSoundAt("error"));
        }
        SoundsPlayer.wannaPlaySound(sounds, this.context);
        this.context.logdGameFlow();
        // determine what to do
        determineWhatToDo();
    }

    protected void determineWhatToDo() {
        if (this.context.gameWon()){
            // inform the user
            this.context.makeToast("¡Ganaste!");
            // reset
            this.context.resetRoundsAndAssertions();
            // if RP
            if(!this.context.playingRazasYPelajesJuntos()){
                // TODO confetti
               // this.context.startAnimation();
                // enable RPJ in settings
                this.context.enableRPJ();
                // ask to retry game or play next
                this.context.showNextLayout();
                // TODO confetti
                this.context.startAnimation();
            }else{
                // was playing RPJ -> cup
                Log.d("!!!!GAME-FLOW", "trophy");
                this.context.showTrophy();
            }
        }else if (this.context.isImpossibleToWin()){
            // inform user
            this.context.makeToast("Obtuviste menos de 3 respuestas correctas en 5 intentos.");
            // reset
            this.context.resetRoundsAndAssertions();
            // ask to go home or retry game
            this.context.showRetryLayout();
        }else {
            // player has chances to win -> play again
            this.context.newGame();
        }
    }

    protected  Boolean viewValidationCondition( View view){
        return ( ((Horse)view.getTag()).getFullName() )
                .contains(whatToLookFor);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected void playHorseSound(Horse horse) {
        ArrayList<Integer> sounds = new ArrayList<>();

        if(listeningToFemAudio){
            if( searchingForType ){
                sounds.add( horse.getFemTypeSound()) ;
            }else if( searchingForHairType ){
                sounds.add( horse.getFemHairTypeSound() );
            }else if( searchingForFullName ){
                sounds = horse.getFemSounds() ;
            }
        }else{
            if( searchingForType ){
                sounds.add( horse.getMaleTypeSound()) ;
            }else if( searchingForHairType ){
                sounds.add( horse.getMaleHairTypeSound() );
            }else if( searchingForFullName ){
                sounds = horse.getMaleSounds() ;
            }
        }

        SoundsPlayer.wannaPlaySound(sounds, this.context);
    }


    protected abstract void initPossibleAnswersContainersArray();

    public abstract void resetViewsTags();

    public abstract void showPossibleAnswers();

    protected abstract void resetSoundImageToRegular();

    protected abstract void manageViewsListItem(Horse horseImgId, int randomHorseImgId);

    public abstract void putAnswerInGame();

    public abstract void manageOnClick(View view);

    protected abstract Boolean viewValidationCondition();

    // test
    protected void setImageResource(ImageView imageView, Integer img){
        if(img != null){
            imageView.setImageResource(img);
        }else{
            imageView.setImageResource(R.drawable.horse_no_image);
        }
    }

}
