package net.dbjorge.calorisaur

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import androidx.core.view.accessibility.AccessibilityEventCompat.setAction
import android.content.Intent
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private val calorieTracker = CalorieTracker(::onCaloriesChanged)
    private var persistentState: PersistentState? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        persistentState = PersistentState(this)

        setContentView(R.layout.activity_main)
        editTextCalorieDisplay.addTextChangedListener(object: TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { calorieTracker.setFromText(p0) }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
        })

        calorieTracker.initialize(persistentState!!.calories, persistentState!!.startingCalories)
    }

    fun onReset(view: View) = calorieTracker.reset()
    fun onSetGoal(view: View) {
        calorieTracker.setStartingCalories(calorieTracker.calories)

        val toastText = resources.getString(R.string.updated_starting_calories, calorieTracker.calories)
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show()
    }

    fun onPlus50(view: View) = calorieTracker.add(50)
    fun onPlus100(view: View) = calorieTracker.add(100)
    fun onPlus200(view: View) = calorieTracker.add(200)
    fun onMinus50(view: View) = calorieTracker.add(-50)
    fun onMinus100(view: View) = calorieTracker.add(-100)
    fun onMinus200(view: View) = calorieTracker.add(-200)

    private fun onCaloriesChanged() {
        editTextCalorieDisplay.setText(calorieTracker.calories.toString())
        persistentState!!.calories = calorieTracker.calories
        persistentState!!.startingCalories = calorieTracker.startingCalories
        broadcastWidgetUpdateRequest()
    }

    private fun broadcastWidgetUpdateRequest() {
        val intent = Intent(this, CalorieDisplayWidget::class.java)
        intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        val ids = AppWidgetManager.getInstance(application)
            .getAppWidgetIds(ComponentName(application, CalorieDisplayWidget::class.java))
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        sendBroadcast(intent)
    }
}
