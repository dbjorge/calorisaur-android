package net.dbjorge.calorisaur

import android.content.Context

private const val SHARED_PREFERENCES_FILENAME = "net.dbjorge.calorisaur.persistentstate"

class PersistentState(context: Context) {
    private val backingSharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILENAME, Context.MODE_PRIVATE)

    private fun setInt(name: String, value: Int) {
        val editor = backingSharedPreferences.edit()
        editor.putInt(name, value)
        editor.apply()
    }

    var calories: Int
        get() = backingSharedPreferences.getInt("calories", DEFAULT_STARTING_CALORIES)
        set(value) { setInt("calories", value) }

    var startingCalories: Int
        get() = backingSharedPreferences.getInt("startingCalories", DEFAULT_STARTING_CALORIES)
        set(value) { setInt("startingCalories", value) }
}