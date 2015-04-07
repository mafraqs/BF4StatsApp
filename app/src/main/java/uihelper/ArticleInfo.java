package uihelper;

import android.content.Context;

import com.example.marius.bf4statsapp.SingleArticle;

/**
 * Created by Marius on 06.04.2015.
 */
public class ArticleInfo {

    public String title;
    public String gameTitle;
    public String description;
    public String imageURL;
    public String pubDate;


    public int getImageResourceId(Context context)
    {
        try {
            return context.getResources().getIdentifier(this.imageURL, "drawable", context.getPackageName());

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

}
