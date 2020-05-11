package net.dbjorge.calorisaur

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 */
class CalorieDisplayWidget : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        val persistentState = PersistentState(context)
        val calorieCountText = persistentState.calories.toString()

        for (appWidgetId in appWidgetIds) {
            val views = RemoteViews(context.packageName, R.layout.calorie_display_widget)

            // Update the text to the current calorie count
            views.setTextViewText(R.id.calorieDisplayButton, calorieCountText)

            // Set the widget to launch the main activity on press
            val launchActivityPendingIntent = Intent(context, MainActivity::class.java)
                .let { intent -> PendingIntent.getActivity(context, 0, intent, 0) }
            views.apply { setOnClickPendingIntent(R.id.calorieDisplayButton, launchActivityPendingIntent) }

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

