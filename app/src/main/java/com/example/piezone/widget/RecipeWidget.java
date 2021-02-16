package com.example.piezone.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.piezone.R;
import com.example.piezone.ui.MainActivity;
import com.example.piezone.ui.RecipeDetailActivity;
import com.example.piezone.model.Recipe;

public class RecipeWidget extends AppWidgetProvider{

    static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager,
                                 int[] appWidgetId) {

        Recipe recipe = WidgetPrefs.loadRecipe(context);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);

        if(recipe != null) {
            // Construct the RemoteViews object
            views.setTextViewText(R.id.widget_recipe_name, recipe.getName());
            views.setTextViewText(R.id.widget_recipe_servings, context.getString(R.string.servings_widget, recipe.getServings()));

            //Bind remote adapter
            Intent listIntent = new Intent(context, RecipeWidgetService.class);
            views.setRemoteAdapter(R.id.widget_ingredient_list, listIntent);

            //Set the pendingIntent to open the detailed Recipe on click
            Intent intent = new Intent(context, RecipeDetailActivity.class);
            intent.putExtra(RecipeDetailActivity.RECIPE_KEY, recipe);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);
            views.setPendingIntentTemplate(R.id.widget_ingredient_list, pendingIntent);

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_ingredient_list);
        } else {
            //if no recipe is available we open the MainActivity on widget click
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        updateAppWidgets(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}
