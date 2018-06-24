package com.singh.vikrant.test1;

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
        else return new Top_Rated_Fragment();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0){
            return "Popular";
        }else return "Top Rated";
    }
}
