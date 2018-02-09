package com.xiongxh.cryptocoin.coindetails;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SectionPagerAdapter extends FragmentPagerAdapter{

    String mSymbol;
    CoinDetailFragment mCoinDetailFragment;
    NewsFragment mNewsFragment;

    public SectionPagerAdapter(FragmentManager fm, String symbol){
        super(fm);
        this.mSymbol = symbol;
        mCoinDetailFragment = CoinDetailFragment.newInstance(symbol);
        mNewsFragment = NewsFragment.newInstance(symbol);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return mCoinDetailFragment;
            case 1:
                return mNewsFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return "MARKET";
            case 1:
                return "NEWS";
        }
        return null;
    }
}
