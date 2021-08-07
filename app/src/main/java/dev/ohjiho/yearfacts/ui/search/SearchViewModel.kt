package dev.ohjiho.yearfacts.ui.search

import androidx.lifecycle.ViewModel
import java.util.Calendar
import kotlin.random.Random

class SearchViewModel : ViewModel() {

    private var year: Int = 0
    private var ad: Boolean = true
    private var isRandom: Boolean = false

    fun search(year: Int, ad: Boolean) {
        this.year = year
        this.ad = ad
        isRandom = false
    }

    fun random() {
        ad = Random.nextBoolean()
        year = if (ad) {
            val currentYear = Calendar.getInstance().get(Calendar.YEAR)
            (0..currentYear).random()
        } else {
            (1..100).random()
        }
        isRandom = true
    }

}