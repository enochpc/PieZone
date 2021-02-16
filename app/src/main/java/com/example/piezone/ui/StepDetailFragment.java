package com.example.piezone.ui;

import android.net.Uri;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.piezone.R;
import com.example.piezone.model.Step;
import com.example.piezone.viewmodel.RecipeDetailViewModel;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import butterknife.BindView;
import butterknife.ButterKnife;


public class StepDetailFragment extends Fragment{
    public static final String STEP_KEY = "STEP_KEY";

    @BindView(R.id.tv_step_description)
    TextView mDescriptionTextView;
    @BindView(R.id.pv_step_video)
    PlayerView mVideoPlayerView;

    private RecipeDetailViewModel mViewModel;
    private ExoPlayer mExoPlayer;
    private Boolean mTwoPane;

    public static StepDetailFragment newInstance(Step step) {
        StepDetailFragment fragment = new StepDetailFragment();

        Bundle args = new Bundle();
        args.putParcelable(STEP_KEY, step);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_detail_fragment, container, false);
        ButterKnife.bind(this, view);
        mTwoPane = getResources().getBoolean(R.bool.two_pane);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mTwoPane)
            mViewModel = ViewModelProviders.of(getActivity()).get(RecipeDetailViewModel.class);
        else {
            mViewModel = ViewModelProviders.of(this).get(RecipeDetailViewModel.class);
            if (getArguments() != null) {
                Step step = getArguments().getParcelable(STEP_KEY);
                mViewModel.setStep(step);
            }
        }

        mViewModel.getStep().observe(getViewLifecycleOwner(), step -> {
            mDescriptionTextView.setText(step.getDescription());
            releaseVideoPlayer();
            initializeVideoPlayer();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeVideoPlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        releaseVideoPlayer();
    }

    /**
     * Initialize the exoplayer
     */
    private void initializeVideoPlayer() {
        Step step = mViewModel.getStep().getValue();
        if (step == null)
            return;
        if (mExoPlayer == null && step.getVideoURL().length() > 0) {
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext());
            mVideoPlayerView.setPlayer(mExoPlayer);

            Uri mediaUri = Uri.parse(step.getVideoURL());
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), getString(R.string.app_name)));
            MediaSource videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mediaUri);
            mExoPlayer.prepare(videoSource);
            mVideoPlayerView.setVisibility(View.VISIBLE);
        } else if (step.getVideoURL().length() == 0) {
            mVideoPlayerView.setVisibility(View.GONE);
        }
    }

    /**
     * Release ExoPlayer.
     */
    private void releaseVideoPlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }



}
