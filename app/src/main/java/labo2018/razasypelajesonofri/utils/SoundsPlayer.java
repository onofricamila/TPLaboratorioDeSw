package labo2018.razasypelajesonofri.utils;

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
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void wannaPlaySound(ArrayList<Integer> sounds, Context context){
        Predicate<Integer> p1 = Objects::nonNull;
        List list =  sounds.stream().filter(p1).collect(Collectors.toList());;
        if(list.size() > 0){playSoundChain(list, context);}
    }

    // works with a single item sound array and a multiple items one
    // HICE ESTO PARA QUE FUNCIONE TANTO CUANDO QUERES REPRODUCIR UNA RAZA O UN PELAJE
    // COMO CUANDO NECESITAS REPRODUCIR AMBOS UNO DESPUES DEL OTRO EN EL JUEGO DE RyP JUNTOS!!
    @RequiresApi(api = Build.VERSION_CODES.N)
    private static void playSoundChain(List sounds, Context context){
        mediaPlayers = new MediaPlayer[(int) sounds.size()];
        // create MP array with respective sounds
        for (int i = 0; i < mediaPlayers.length; i++) {
            mediaPlayers[i] = MediaPlayer.create( context, (Integer) sounds.get(i));
        }
        // create icon_sound chain only if there are more than one icon_sound to play
        if (mediaPlayers.length > 1) {
            for (int i = 0; i < mediaPlayers.length-1; i++) {
                mediaPlayers[i].setNextMediaPlayer( mediaPlayers[i+1] );
            }
        }
        // start playing icon_sound chain/single icon_sound
        mediaPlayers[0].start();
    }

}
