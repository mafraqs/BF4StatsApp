package uihelper;

import android.content.Context;

/**
 * Created by Marius on 06.04.2015.
 */
public class ArticleInfo {

    public String title;
    public String description;
    public String imageName;


    public int getImageResourceId(Context context)
    {
        try {
            return context.getResources().getIdentifier(this.imageName, "drawable", context.getPackageName());

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

}
