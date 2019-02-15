package ibrickedlabs.com.techbites;


import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {
    //the external vertical viewpager we created
    private VerticalViewPager mViewPager;
    //viewpger adapter
    private ViewPagerAdapter mViewPagerAdapter;

    //List
    private List<News> list;

    private RelativeLayout noInternetLayout;
    private ImageView noInternetImage;
    private AVLoadingIndicatorView mAvLoadingIndicatorView;



    private static final String NEWS_API_URL = "https://newsapi.org/v2/everything?q=technology&language=en&sortBy=publishedAt&apiKey=b89bf59c24774f4ca69358298f0805da";

    private static final int NEW_LOADER_ID = 121;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (VerticalViewPager) findViewById(R.id.pager);

        list = new ArrayList<>();
        noInternetLayout = (RelativeLayout) findViewById(R.id.no_internet_layout);
        noInternetImage=(ImageView) findViewById(R.id.noInternetImageView);
        //noInternetLayout.setVisifvbbility(View.GONE);
        mAvLoadingIndicatorView=(AVLoadingIndicatorView)findViewById(R.id.avl_in_main);
        mAvLoadingIndicatorView.setVisibility(View.VISIBLE);
        mAvLoadingIndicatorView.show();

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            //we have active internet connection
            //here we have to call our loader
            LoaderManager manager = getLoaderManager();

            //it will call onCreateLoader
            manager.initLoader(NEW_LOADER_ID, null, this);


        } else {

            noInternetLayout.setVisibility(View.VISIBLE);
            Glide.with(this).load(R.drawable.astronaut).into(noInternetImage);
            mViewPager.setVisibility(View.GONE);

        }


    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {


        return new NewsLoader(this, NEWS_API_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newsObtained) {
        list.clear();


        if (newsObtained != null && !newsObtained.isEmpty()) {

            mAvLoadingIndicatorView.setVisibility(View.GONE);
            mAvLoadingIndicatorView.hide();
            //add into adapter
            list = newsObtained;
            mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), list);
            mViewPager.setAdapter(mViewPagerAdapter);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        list.clear();
    }


    @Override
    protected void onPause() {
        super.onPause();

    }



}
