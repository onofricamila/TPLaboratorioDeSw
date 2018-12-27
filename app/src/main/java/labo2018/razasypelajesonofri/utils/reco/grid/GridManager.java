package labo2018.razasypelajesonofri.utils.reco.grid;

import android.content.Context;
import android.widget.GridView;

import java.util.ArrayList;

import labo2018.razasypelajesonofri.R;
import labo2018.razasypelajesonofri.RecoActivity;
import labo2018.razasypelajesonofri.utils.horses.Horse;
import labo2018.razasypelajesonofri.utils.reco.GenericRecoViewManager;

public class GridManager extends GenericRecoViewManager {

    public GridManager(RecoActivity context) {
        super(context);
    }

    @Override
    protected void manageList(int img, Horse horse, ArrayList<Integer> sounds) {
        list.add( new GridItem(
                img, horse.getFullName().toUpperCase(), sounds
        ) );
    }

    public void prepareView() {
        // fulfill gridItems
        fulfillItems();
        // get view and set custom adapter
        GridView gridView = context.findViewById(R.id.gridView);
        CustomGridAdapter customGridAdapter = new CustomGridAdapter(context, R.layout.activity_reco_grid_view_item, list);
        gridView.setAdapter(customGridAdapter);
    }

}
