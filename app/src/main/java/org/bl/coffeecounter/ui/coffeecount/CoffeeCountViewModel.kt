package org.bl.coffeecounter.ui.coffeecount

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import org.bl.coffeecounter.db.CoffeeRepository
import org.bl.coffeecounter.db.entities.Coffee

class CoffeeCountViewModel(private val repository: CoffeeRepository) : ViewModel() {

    val allCoffee: LiveData<List<Coffee>> = repository.allCoffee.asLiveData()

    fun deleteLatestCoffee() = viewModelScope.launch {
        repository.deleteLastCoffee()
    }
}

class CoffeeCountViewModelFactory(private val repository: CoffeeRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CoffeeCountViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CoffeeCountViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}