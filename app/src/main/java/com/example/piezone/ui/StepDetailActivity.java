package com.example.piezone.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.piezone.R;
import com.example.piezone.model.Recipe;
import com.example.piezone.model.Step;

import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StepDetailActivity extends AppCompatActivity {

    public static final String RECIPE_KEY = "RECIPE_STEP_DETAIL_KEY";
    public static final String STEP_KEY = "STEP_KEY";

    @BindView(R.id.layout_tab)
    TabLayout mTabLayout;
    @BindView(R.id.vp_steps)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setElevation(0);
        }
        Recipe recipe = getIntent().getParcelableExtra(RECIPE_KEY);
        int stepIndex = getIntent().getIntExtra(STEP_KEY, 0);

        setTitle(recipe.getName());
        StepPagerAdapter adapter = new StepPagerAdapter(getSupportFragmentManager());
        adapter.setSteps(recipe.getSteps());
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(stepIndex);
    }
}
