package labo2018.razasypelajesonofri.utils.reco;


import java.util.ArrayList;
import java.util.List;
import labo2018.razasypelajesonofri.RecoActivity;
import labo2018.razasypelajesonofri.utils.horses.Horse;
import labo2018.razasypelajesonofri.utils.horses.HorseImgsProvider;
import labo2018.razasypelajesonofri.utils.horses.HorsesProvider;

public abstract class GenericRecoViewManager {
    protected RecoActivity context;
    protected List list;

    public GenericRecoViewManager(RecoActivity context) {
        this.context = context;
        this.list = new ArrayList();
    }

    protected void fulfillItems() {
        list = new ArrayList<>();
        HorsesProvider horsesProvider = new HorsesProvider(context);
        List<Horse> horses = horsesProvider.getHorsesList();
        for (int i = 0; i < horses.size(); i++) {
            Horse horse = horses.get(i);
            int img = HorseImgsProvider.INSTANCE.getImgAt(horse.getFullName());
            ArrayList<Integer> sounds;
            if (context.listeningToFemAudio()){
                sounds = horse.getFemSounds();
            } else {
                sounds = horse.getMaleSounds();
            }
            manageList(img, horse, sounds);
        }
    }

    protected abstract void manageList(int img, Horse horse, ArrayList<Integer> sounds);

    public abstract void prepareView();

}
