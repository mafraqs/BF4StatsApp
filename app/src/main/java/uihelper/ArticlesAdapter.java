package uihelper;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marius.bf4statsapp.R;
import com.example.marius.bf4statsapp.SingleArticle;

import java.util.List;

/**
 * Created by Marius on 06.04.2015.
 */
public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticlesViewHolder> {

    private List<ArticleInfo> articles;
    private int rowLayout;
    private Context mContext;

    public ArticlesAdapter(List<ArticleInfo> articles, int rowLayout, Context context) {
        this.articles = articles;
        this.rowLayout = rowLayout;
        this.mContext = context;
    }


    @Override
    public void onBindViewHolder(ArticlesViewHolder articlesViewHolder, int i) {
        ArticleInfo article1 = articles.get(i);
        articlesViewHolder.vArticleName.setText(article1.title);
        articlesViewHolder.vArticleImage.setImageDrawable(mContext.getDrawable(article1.getImageResourceId(mContext)));

        articlesViewHolder.currentArticle = article1;
    }

    @Override
    public int getItemCount() {
        return articles == null ? 0 : articles.size();
    }

    @Override
    public ArticlesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ArticlesViewHolder(v);
    }

    public class ArticlesViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ArticleInfo currentArticle;
        private Context context;

        protected TextView vArticleName;
        protected ImageView vArticleImage;

        public ArticlesViewHolder(View v) {
            super(v);

            vArticleName = (TextView) v.findViewById(R.id.articleTitle);
            vArticleImage = (ImageView) v.findViewById(R.id.articleImage);

            view = v;
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // item clicked
                    Toast.makeText(v.getContext(), currentArticle.title, Toast.LENGTH_LONG).show();
                    Log.w("XXX", "ERRORRRRRRR");

                    Intent intent = new Intent(v.getContext(), SingleArticle.class);
                    v.getContext().startActivity(intent);
                }
            });

        }


    }
}


