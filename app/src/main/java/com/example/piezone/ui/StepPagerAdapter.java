package com.example.piezone.ui;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.piezone.model.Step;

import java.util.List;

public class StepPagerAdapter extends FragmentPagerAdapter{
    private List<Step> mSteps;

    public StepPagerAdapter(FragmentManager fm) { super(fm);}

    public void setSteps(List<Step> steps){
        mSteps = steps;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return StepDetailFragment.newInstance(mSteps.get(position));
    }

    @Override
    public int getCount() { return mSteps == null ? 0 : mSteps.size(); }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) { return "Step " + position;}
}
