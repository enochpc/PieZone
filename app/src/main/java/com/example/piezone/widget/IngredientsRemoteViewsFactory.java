package com.example.piezone.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.piezone.R;
import com.example.piezone.model.Ingredient;
import com.example.piezone.model.Recipe;
import com.example.piezone.ui.MainActivity;

import java.util.List;

public class IngredientsRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private List<Ingredient> mIngredients;

    public IngredientsRemoteViewsFactory(Context context) {this.mContext = context; }

    @Override
    public void onCreate(){
        //nothing to see here, move along
    }

    @Override
    public void onDataSetChanged() {
        Recipe recipe = WidgetPrefs.loadRecipe(mContext);
        if(recipe != null){
            mIngredients = recipe.getIngredients();
        }
    }

    @Override
    public void onDestroy() {
        //these are not the droids you are looking for
    }

    @Override
    public int getCount() {
        return mIngredients == null ? 0 : mIngredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position){
        Ingredient ingredient = mIngredients.get(position);

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.recipe_widget_list_item);

        rv.setTextViewText(R.id.tv_widget_ingredient_name, ingredient.toString());

        /**
         * Set a fillIn Intent to open the PendingIntent set in {@link RecipeWidget#updateAppWidgets(Context, AppWidgetManager, int[])}
         */
        rv.setOnClickFillInIntent(R.id.tv_widget_ingredient_name, new Intent());
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

}
