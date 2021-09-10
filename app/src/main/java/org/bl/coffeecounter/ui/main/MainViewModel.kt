package org.bl.coffeecounter.ui.main

import androidx.arch.core.util.Function
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import org.bl.coffeecounter.db.CoffeeRepository
import org.bl.coffeecounter.db.entities.Coffee
import org.bl.coffeecounter.db.entities.Payment
import org.bl.coffeecounter.models.Balance
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.*

class MainViewModel(private val repository: CoffeeRepository) : ViewModel() {

    val allCoffee: LiveData<List<Coffee>> = repository.allCoffee.asLiveData()
    val allPayment: LiveData<List<Payment>> = repository.allPayment.asLiveData()


    val coffeeCountToday = Transformations.map(
        allCoffee,
        Function { all ->
            all.filter { coffee ->
                coffee.localDateTime.toLocalDate().isEqual(LocalDate.now())
            }.count()
        })

    val coffeeCountThisWeek = Transformations.map(allCoffee, Function { all ->
        all.filter { coffee ->
            val weekFields = WeekFields.ISO
            val weekNumber = coffee.localDateTime.get(weekFields.weekOfWeekBasedYear())
            val weekNumberNow = LocalDate.now().get(weekFields.weekOfWeekBasedYear())
            weekNumber == weekNumberNow
        }.count()
    })

    val coffeeCountThisMonth = Transformations.map(
        allCoffee,
        Function { all ->
            all.filter { coffee -> coffee.localDateTime.month == LocalDate.now().month }.count()
        })
    val coffeeCountThisYear = Transformations.map(
        allCoffee,
        Function { all ->
            all.filter { coffee -> coffee.localDateTime.year == LocalDate.now().year }.count()
        })

    private var lastNFCCoffeeAddedAt: Long = 0


    fun getBalance(): LiveData<Balance> {
        val balance: MediatorLiveData<Balance> = MediatorLiveData()
        balance.value = Balance(0, 0)
        balance.addSource(allCoffee) { all ->
            balance.value = Balance(balance.value?.income ?: 0, all.sumOf { it.cost.toInt() })
        }
        balance.addSource(allPayment) { all ->
            balance.value = Balance(all.sumOf { it.amount }, balance.value?.expenses ?: 0)
        }
        return balance
    }


    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun addACoffee(coffee: Coffee) = viewModelScope.launch {
        if (coffee.fromNFC) {
            if (lastNFCCoffeeAddedAt == 0L || (System.currentTimeMillis() - lastNFCCoffeeAddedAt >= 500)) {
                repository.insert(coffee)
                lastNFCCoffeeAddedAt = System.currentTimeMillis();
            }
        } else {
            repository.insert(coffee)
        }
    }

    fun addCoffeeManually() {
        viewModelScope.launch {
            repository.insert(Coffee(fromNFC = false))
        }
    }

    fun deleteLastCoffee() {
        viewModelScope.launch {
            repository.deleteLastCoffee()
        }
    }


}

class MainViewModelFactory(private val repository: CoffeeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}