package net.dbjorge.calorisaur

import java.lang.NumberFormatException

const val DEFAULT_STARTING_CALORIES = 2000

class CalorieTracker(val onChangeHandler: ()->Unit) {
    var startingCalories = DEFAULT_STARTING_CALORIES
        private set
    var calories = DEFAULT_STARTING_CALORIES
        private set

    fun set(newValue: Int) {
        if(calories != newValue) {
            calories = newValue
            onChangeHandler()
        }
    }

    fun reset() {
        set(startingCalories)
    }

    fun add(addend: Int) {
        set(calories + addend)
    }

    fun setFromText(text: CharSequence?) {
        try {
            set(text.toString().toInt())
        } catch (error: NumberFormatException) {
            reset()
        }
    }

    fun setStartingCalories(newStartingCalories: Int) {
        if(startingCalories != newStartingCalories) {
            startingCalories = newStartingCalories
            onChangeHandler()
        }
    }

    fun initialize(calories: Int, startingCalories: Int) {
        this.calories = calories
        this.startingCalories = startingCalories
        onChangeHandler()
    }
}