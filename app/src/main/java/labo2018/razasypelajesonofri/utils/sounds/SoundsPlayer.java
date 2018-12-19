package labo2018.razasypelajesonofri.utils.sounds;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SoundsPlayer {

    private static MediaPlayer[] mediaPlayers;

    // determines whether there are valid sounds to play
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void wannaPlaySound(ArrayList<Integer> sounds, Context context){
        List<Integer> result = new ArrayList<>();
        for (Integer sound : sounds) {
            if (sound != null) {
                result.add(sound);
            }
        }
        if(result.size() > 0){playSoundChain(result, context);}
    }

    // works with a single item sound array and a multiple items one
    // HICE ESTO PARA QUE FUNCIONE TANTO CUANDO QUERES REPRODUCIR UNA RAZA O UN PELAJE
    // COMO CUANDO NECESITAS REPRODUCIR AMBOS UNO DESPUES DEL OTRO EN EL JUEGO DE RyP JUNTOS!!
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private static void playSoundChain(List sounds, Context context){
        mediaPlayers = new MediaPlayer[sounds.size()];
        // create MP array with respective sounds
        for (int i = 0; i < mediaPlayers.length; i++) {
            mediaPlayers[i] = MediaPlayer.create( context, (Integer) sounds.get(i));
        }
        // create ic_sound chain only if there are more than one ic_sound to play
        if (mediaPlayers.length > 1) {
            for (int i = 0; i < mediaPlayers.length-1; i++) {
                mediaPlayers[i].setNextMediaPlayer( mediaPlayers[i+1] );
            }
        }
        // start playing ic_sound chain/single ic_sound
        mediaPlayers[0].start();
    }

}
