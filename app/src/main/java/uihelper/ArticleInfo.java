package uihelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.marius.bf4statsapp.SingleArticle;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by Marius on 06.04.2015.
 */
public class ArticleInfo {

    public String title;
    public String gameTitle;
    public String description;
    public String imageURL;
    public String pubDate;

    Bitmap bitmap;

    public int getImageResourceId(Context context)
    {
        try {
            return context.getResources().getIdentifier(this.imageURL, "drawable", context.getPackageName());

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

//    public Bitmap getImageFromWeb(){
//        try {
//            bitmap = BitmapFactory.decodeStream((InputStream)new URL(this.imageURL).getContent());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return bitmap;
//    }


}
