package helper;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.marius.bf4statsapp.R;

public class ImageAdapter extends BaseAdapter {

    private Activity activity;
    private String imgUrl = null;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader;

    public ImageAdapter(Activity a, String imgUrl) {
        activity = a;
        this.imgUrl = imgUrl;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return 1;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;

        ImageView image=(ImageView)vi.findViewById(R.id.NewImage);
        imageLoader.DisplayImage(imgUrl, image);
        return vi;
    }
}