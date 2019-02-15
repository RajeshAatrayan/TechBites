package ibrickedlabs.com.techbites;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    String url;
    Context context;

    public NewsLoader(Context context, String url) {
        super(context);
        this.url = url;
        this.context = context;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();//it will calls LoadInBackgroun
    }

    @Override
    public List<News> loadInBackground() {

        if (url == null) return null;
        List<News> list = QueryUtils.fetchNewsData(url);
        return list;//sends back the list


    }
}
