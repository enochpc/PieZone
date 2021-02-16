package com.example.piezone.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.example.piezone.model.Recipe;
import com.example.piezone.R;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder>{

    private List<Recipe> mRecipes;
    private final RequestManager mGlide;
    private OnItemClickListener mOnItemClickListener;

    RecipeAdapter(RequestManager glide) {
        this.mGlide = glide;
    }

    void setRecipes(List<Recipe> recipes) {
        this.mRecipes = recipes;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recipe_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindView(mRecipes.get(position));
    }

    @Override
    public int getItemCount() {
        return mRecipes == null ? 0 : mRecipes.size();
    }

    interface OnItemClickListener {
        void onClick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_name)
        TextView mNameTextView;
        @BindView(R.id.iv_image)
        ImageView mImageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bindView(Recipe recipe) {
            mNameTextView.setText(recipe.getName());
            mGlide
                    .load(recipe.getImage())
                    .error(R.drawable.ic_recipe)
                    .into(mImageView);
        }

        @Override
        public void onClick(View v) {
            if(mOnItemClickListener!= null){
                mOnItemClickListener.onClick(getAdapterPosition());
            }
        }
    }




}
