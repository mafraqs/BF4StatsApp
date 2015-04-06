package uihelper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marius.bf4statsapp.R;

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
        ArticleInfo article = articles.get(i);
        articlesViewHolder.vArticleName.setText(article.title);
        articlesViewHolder.vArticleImage.setImageDrawable(mContext.getDrawable(article.getImageResourceId(mContext)));
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

    public static class ArticlesViewHolder extends RecyclerView.ViewHolder {

        protected TextView vArticleName;
        protected ImageView vArticleImage;

        public ArticlesViewHolder(View v) {
            super(v);
            vArticleName = (TextView) v.findViewById(R.id.articleTitle);
            vArticleImage = (ImageView) v.findViewById(R.id.articleImage);

        }
    }
}


