package com.example.piezone.ui;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.piezone.R;
import com.example.piezone.model.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.ViewHolder> {

    private List<Step> mSteps;
    private OnItemClickListener mOnItemClickListener;

    void setSteps(List<Step> steps){
        mSteps = steps;
        notifyDataSetChanged();
    }

    void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_steplist_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position){
        holder.bindView(mSteps.get(position));

    }

    @Override
    public int getItemCount(){ return mSteps == null ? 0 : mSteps.size(); }

    interface OnItemClickListener {
        void onClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_step_id)
        TextView mIdTextView;
        @BindView(R.id.tv_step_short_description)
        TextView mDescriptionView;

        public ViewHolder(View view){
            super(view);
            ButterKnife.bind(this,view);
            view.setOnClickListener(this);

        }

        void bindView(Step step){
            mIdTextView.setText(step.getId()+".");
            mDescriptionView.setText(step.getShortDescription());

        }
        @Override
        public void onClick(View v){
            if(mOnItemClickListener != null)
                mOnItemClickListener.onClick(getAdapterPosition());
        }
    }
}
