package labo2018.razasypelajesonofri;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import labo2018.razasypelajesonofri.utils.recoListView.CustomListAdapter;
import labo2018.razasypelajesonofri.utils.recoListView.ListItem;

import static android.app.PendingIntent.getActivity;

public class RecoActivity extends AppCompatActivity {

    List<ListItem> listItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reco);

        listItems = new ArrayList<>();
        listItems.add(new ListItem(R.drawable.horse_criollo__overo_azulejo, "Criollo overo azulejo" ));
        listItems.add(new ListItem(R.drawable.horse_criollo__overo_azulejo, "Criollo" ));
        listItems.add(new ListItem(R.drawable.horse_criollo__overo_azulejo, "Overo azulejo" ));

        listItems.add(new ListItem(R.drawable.horse_criollo__picaso, "Criollo picaso" ));
        listItems.add(new ListItem(R.drawable.horse_criollo__picaso, "Picaso" ));

        listItems.add(new ListItem(R.drawable.horse_cuarto_de_milla__bayo, "Cuarto de mila bayo" ));
        listItems.add(new ListItem(R.drawable.horse_cuarto_de_milla__bayo, "Cuarto de mila" ));
        listItems.add(new ListItem(R.drawable.horse_cuarto_de_milla__bayo, "Bayo" ));

        ListView listView = findViewById(R.id.listView);
        CustomListAdapter customListAdapter = new CustomListAdapter(this, R.layout.activity_reco_list_view_row, listItems);
        listView.setAdapter(customListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("!!!!!!!", "onItemClick: " + listItems.get(position).getHorseName()
                        + listItems.get(position).getHorseImgId());
            }
        });
    }

    public void toHome(View view){
        findViewById(R.id.homeImgView).setBackgroundResource(R.drawable.ic_home_click);
        finish();
    }
}
