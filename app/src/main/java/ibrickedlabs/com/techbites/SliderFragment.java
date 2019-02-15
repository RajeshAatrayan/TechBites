package ibrickedlabs.com.techbites;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.jackandphantom.blurimage.BlurImage;
import com.wang.avi.AVLoadingIndicatorView;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


import static android.support.constraint.Constraints.TAG;
import static com.bumptech.glide.request.RequestOptions.bitmapTransform;


public class SliderFragment extends Fragment {
    private List<News> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.news_item, container, false);
        Bundle bundle = getArguments();
        list = (ArrayList<News>) bundle.getSerializable("object");
        News currentArticle = list.get(bundle.getInt("pos"));

        final ImageView imageView = v.findViewById(R.id.newsImage);
        TextView headLines = v.findViewById(R.id.headLines);
        TextView contentView = v.findViewById(R.id.content);
        final TextView bottomView = v.findViewById(R.id.short_news);
        final ImageView bottomImageView = (ImageView) v.findViewById(R.id.blurBottomImage);

        if (currentArticle.getImageUrl().equals("null")) {
            Glide.with(getContext()).load(R.drawable.rand_img).thumbnail(0.1f).into(imageView);
        } else {

            final AVLoadingIndicatorView avl = v.findViewById(R.id.avl_load);
            avl.setVisibility(View.VISIBLE);
            avl.show();
            Glide.with(getContext()).load(currentArticle.getImageUrl()).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    avl.hide();
                    avl.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    avl.hide();
                    avl.setVisibility(View.GONE);
                    return false;
                }
            }).thumbnail(0.1f).into(imageView);
            Glide.with(getContext()).load(R.drawable.bottom_blur).thumbnail(0.3f).into(bottomImageView);
        }


        headLines.setText(currentArticle.getTitle());
        contentView.setText(currentArticle.getContent());
        bottomView.setText(currentArticle.getSrcName());


        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}
