package labo2018.razasypelajesonofri.utils;

import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.List;

public class ResponsiveDesigner {
    public static void determineImgViewsSize(WindowManager windowManager, List<ImageView> imgsViews){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displaymetrics);
        // get screen size
        int screenHeight = displaymetrics.heightPixels;
        int screenWidth = displaymetrics.widthPixels;
        // calc img desidered size
        int imgHeight = (int) (screenHeight* 0.22); // 22% of screen.
        int imgWidth = (int) (screenWidth* 0.40); // 40% of screen.
        // set each img view size
        for (int i = 0; i < imgsViews.size(); i++) {
            ImageView imageView = imgsViews.get(i);
            imageView.getLayoutParams().height = imgHeight;
            imageView.getLayoutParams().width = imgWidth;
        }
    }
}
