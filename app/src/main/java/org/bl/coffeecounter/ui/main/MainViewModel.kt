package org.bl.coffeecounter.ui.main

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import org.bl.coffeecounter.db.CoffeeRepository
import org.bl.coffeecounter.db.entities.Coffee

class MainViewModel(private val repository: CoffeeRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allCoffee: LiveData<List<Coffee>> = repository.allCoffee.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(coffee: Coffee) = viewModelScope.launch {
        repository.insert(coffee)
    }

    fun reset() {
        viewModelScope.launch {
            repository.reset()
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