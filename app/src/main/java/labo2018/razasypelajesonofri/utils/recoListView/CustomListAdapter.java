package labo2018.razasypelajesonofri.utils.recoListView;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import labo2018.razasypelajesonofri.R;
import labo2018.razasypelajesonofri.utils.sounds.SoundsProvider;

public class CustomListAdapter extends ArrayAdapter<ListItem> {
    Context context;
    int layoutResourceId;
    List<ListItem> data;

    public CustomListAdapter(Context context, int resource, List<ListItem> objects) {
        super(context, resource, objects);

        this.context = context;
        this.layoutResourceId = resource;
        this.data = objects;
    }

    static class DataHolder{
        ImageView horseImageView, soundImgView;
        TextView horseTextView;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DataHolder dataHolder;

        if (convertView == null){
            LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
            convertView = layoutInflater.inflate(layoutResourceId, parent, false);
            dataHolder = new DataHolder();
            dataHolder.horseImageView = convertView.findViewById(R.id.horseImageView);
            dataHolder.soundImgView = convertView.findViewById(R.id.soundImgView);
            dataHolder.horseTextView = convertView.findViewById(R.id.horseTextView);
            convertView.setTag(dataHolder);
        }else{
            dataHolder = (DataHolder) convertView.getTag();
        }
        ListItem listItem = data.get(position);
        dataHolder.horseTextView.setText(listItem.horseName);
        dataHolder.horseImageView.setImageResource(listItem.horseImgId);
        dataHolder.horseImageView.setTag(listItem.horseImgId);
        dataHolder.soundImgView.setTag(listItem.sounds);
        return convertView;
    }
}
