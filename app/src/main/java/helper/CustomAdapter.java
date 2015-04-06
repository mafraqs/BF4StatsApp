package helper;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marius.bf4statsapp.R;

public class CustomAdapter extends BaseAdapter {

    private Activity activity;
    private String[] data;
    private String[] titledata = null;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader;

    public CustomAdapter(Activity a, String[] d) {
        activity = a;
        data=d;
        titledata = null;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public CustomAdapter(Activity a, String[] d, String [] e) {
        activity = a;
        data=d;
        titledata = e;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.custom_list_view, null);

        TextView text=(TextView)vi.findViewById(R.id.textView1);;
        ImageView image=(ImageView)vi.findViewById(R.id.imageView1);
        if (titledata != null) {
            text.setText(titledata[position]);
        } else {
            text.setText("  item "+position);
        }

        imageLoader.DisplayImage(data[position], image);
        return vi;
    }
}