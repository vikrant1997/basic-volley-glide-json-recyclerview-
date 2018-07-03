package com.singh.vikrant.test1.Fragmets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class Fragment_Adapter extends FragmentPagerAdapter {
    private Context mContext;

    public Fragment_Adapter(Context context, FragmentManager fm){
        super(fm);
        mContext=context;
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0) return new PopularFragment();
        else if(position==1) return new Top_Rated_Fragment();
        else return new BookMarks_Activity();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0){
            return "Popular";
        }else if(position==1) return "Top Rated";
        else return "BookMarks";
    }
}
