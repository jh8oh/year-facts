package dev.ohjiho.yearfacts.ui

import android.util.Log
import androidx.lifecycle.*
import dev.ohjiho.yearfacts.data.model.YearFact
import dev.ohjiho.yearfacts.data.network.NumbersEndpoint
import dev.ohjiho.yearfacts.data.network.ServiceBuilder
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar
import kotlin.random.Random

class YearFactsViewModel : ViewModel() {

    // SearchFragment
    private val year = MutableLiveData(0)
    private val ad = MutableLiveData(true)
    private var isRandom: Boolean = false

    // ResultFragment
    val displayYear = MediatorLiveData<String>().apply {
        fun updateValue(year: Int?, ad: Boolean?) {
            if (year != null && ad != null) {
                val era = if (ad) "AD" else "BC"
                value = "$year $era"
            }
        }

        addSource(year) { updateValue(year.value, ad.value) }
        addSource(ad) { updateValue(year.value, ad.value) }
    }
    private val yearFactString = MutableLiveData<String>()
    val displayYearFactString: LiveData<String> = Transformations.map(yearFactString) {
        it.replaceFirstChar(Char::uppercase)
    }

    // Network
    private val request = ServiceBuilder.buildService(NumbersEndpoint::class.java)
    val error = MutableLiveData(false)
    val errorMessage = MutableLiveData<String>()

    fun search(year: Int, ad: Boolean) {
        this.year.value = year
        this.ad.value = ad
        isRandom = false

        callGetYear()
    }

    fun reroll() = callGetYear()

    private fun callGetYear() = viewModelScope.launch {
        val call = request.getYear(getSignedYear().toString())

        call.enqueue(object : Callback<YearFact> {
            override fun onResponse(call: Call<YearFact>, response: Response<YearFact>) {
                if (response.isSuccessful) {
                    yearFactString.value = response.body()?.text
                }
            }

            override fun onFailure(call: Call<YearFact>, t: Throwable) {
                error.value = true
                errorMessage.value = t.message
            }
        })
    }

    private fun getSignedYear() = year.value?.let { if (ad.value == true) it else -it }

    fun random() {
        ad.value = Random.nextBoolean()
        year.value = if (ad.value == true) {
            val currentYear = Calendar.getInstance().get(Calendar.YEAR)
            (0..currentYear).random()
        } else {
            (1..100).random()
        }
        isRandom = true

        viewModelScope.launch {
            val call = request.getRandom()

            call.enqueue(object : Callback<YearFact> {
                override fun onResponse(call: Call<YearFact>, response: Response<YearFact>) {
                    if (response.isSuccessful) {
                        yearFactString.value = response.body()?.text
                    }
                }

                override fun onFailure(call: Call<YearFact>, t: Throwable) {
                    error.value = true
                    errorMessage.value = t.message
                }
            })
        }
    }

}