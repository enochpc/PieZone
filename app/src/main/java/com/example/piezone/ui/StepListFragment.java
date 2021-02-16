package com.example.piezone.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.piezone.R;
import com.example.piezone.model.Recipe;
import com.example.piezone.viewmodel.RecipeDetailViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StepListFragment extends Fragment{
    @BindView(R.id.tv_servings)
    TextView servingsTextView;
    @BindView(R.id.tv_ingredients)
    TextView ingredientsTextView;
    @BindView(R.id.rv_steps)
    RecyclerView stepListRecyclerView;

    private StepListAdapter mAdapter;
    private RecipeDetailViewModel mViewModel;
    private Boolean mTwoPane;

    //mandatory but unnecessary constructor
    public StepListFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState){ super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_steplist, container, false);
        ButterKnife.bind(this, view);
        mTwoPane = getResources().getBoolean(R.bool.two_pane);

        mAdapter = new StepListAdapter();
        mAdapter.setOnItemClickListener(position -> {
            Recipe recipe = mViewModel.getRecipe().getValue();

            if(recipe != null) {
                if(mTwoPane) {
                    mViewModel.setStep(recipe.getSteps().get(position));
                } else {
                    Intent stepDetailIntent = new Intent(getContext(), StepDetailActivity.class);
                    stepDetailIntent.putExtra(StepDetailActivity.RECIPE_KEY, recipe);
                    stepDetailIntent.putExtra(StepDetailActivity.STEP_KEY, position);
                    startActivity(stepDetailIntent);
                }
            }
        });
        stepListRecyclerView.setAdapter(mAdapter);

        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(RecipeDetailViewModel.class);

        mViewModel.getRecipe().observe(getViewLifecycleOwner(), recipe -> {
            servingsTextView.setText(getString(R.string.servings, recipe.getServings()));
            ingredientsTextView.setText(recipe.getIngredientString());
            mAdapter.setSteps(recipe.getSteps());
        });
    }
}
