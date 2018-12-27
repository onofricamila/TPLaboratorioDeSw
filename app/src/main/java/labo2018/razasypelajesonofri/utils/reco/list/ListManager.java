package labo2018.razasypelajesonofri.utils.reco.list;

import android.widget.ListView;

import java.util.ArrayList;

import labo2018.razasypelajesonofri.R;
import labo2018.razasypelajesonofri.RecoActivity;
import labo2018.razasypelajesonofri.utils.TextsProvider;
import labo2018.razasypelajesonofri.utils.horses.Horse;
import labo2018.razasypelajesonofri.utils.reco.GenericRecoViewManager;

public class ListManager extends GenericRecoViewManager {
    public ListManager(RecoActivity context) {
        super(context);
    }

    @Override
    protected void manageList(int img, Horse horse, ArrayList<Integer> sounds) {
        TextsProvider textsProvider = new TextsProvider(context);
        String txt = textsProvider.getTextFor(horse.getFullName());
        list.add( new ListItem(
                img, horse.getFullName().toUpperCase(), sounds, txt
        ) );
    }

    public void prepareView() {
        // fulfill listItems
        fulfillItems();
        // get view and set custom adapter
        ListView listView = context.findViewById(R.id.listView);
        CustomListAdapter customListAdapter = new CustomListAdapter(context, R.layout.activity_reco_list_view_row, list);
        listView.setAdapter(customListAdapter);
    }
}
