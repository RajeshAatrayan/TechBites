package ibrickedlabs.com.techbites;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.transition.Slide;

import java.io.Serializable;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    List<News> list;
    public ViewPagerAdapter(FragmentManager fm, List<News> list) {
        super(fm);
        this.list=list;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment sliderFragment=new SliderFragment();
        Bundle bundle=new Bundle();

      bundle.putInt("pos",position);
        bundle.putSerializable("object", (Serializable) list);
        sliderFragment.setArguments(bundle);
        return sliderFragment;
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
