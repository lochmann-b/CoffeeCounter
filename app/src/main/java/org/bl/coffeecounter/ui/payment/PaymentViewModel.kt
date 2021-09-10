package org.bl.coffeecounter.ui.payment

import android.text.method.TransformationMethod
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import org.bl.coffeecounter.db.CoffeeRepository
import org.bl.coffeecounter.db.entities.Payment
import java.time.LocalDateTime

class PaymentViewModel(private val repository: CoffeeRepository) : ViewModel() {

    val selectedAmount: MutableLiveData<String> = MutableLiveData()
    val isFive:LiveData<Boolean> = Transformations.map(selectedAmount, {a -> amountEquals(a, 5.0)})
    val isTen:LiveData<Boolean> = Transformations.map(selectedAmount, {a -> amountEquals(a, 10.0)})
    val isTwenty:LiveData<Boolean> = Transformations.map(selectedAmount, {a -> amountEquals(a, 20.0)})
    val isFifty:LiveData<Boolean> = Transformations.map(selectedAmount, {a -> amountEquals(a, 50.0)})
    val isValid:LiveData<Boolean> = Transformations.map(selectedAmount, {a -> isValidAmount(a)})

    fun onFive() {
        selectedAmount.value = "5"
    }

    fun onTen() {
        selectedAmount.value = "10"
    }

    fun onTwenty() {
        selectedAmount.value = "20"
    }

    fun onFifty() {
        selectedAmount.value = "50"
    }

    fun pay () = viewModelScope.launch {
        getAmountInCents()?.let{ it ->
            repository.insert(Payment(localDateTime = LocalDateTime.now(), amount = it))
        }
    }

    private fun getAmountInCents(): Int? {
        try {
            val asDouble = selectedAmount.value?.toDouble() ?: 0.0
            return (asDouble*100).toInt()
        }catch(e: Exception) {
            return null
        }
    }

    private fun isValidAmount(input: String?): Boolean {
        try {
            val asDouble = input?.toDouble() ?: 0.0
            return asDouble > 0.0
        }catch(e: Exception) {
            return false
        }
    }

    private fun amountEquals(input:String?, amount:Double): Boolean {
        try {
            return input?.toDouble()?.equals(amount) ?: false
        }catch(e: Exception) {
            return false
        }
    }

}

class PaymentViewModelFactory(private val repository: CoffeeRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PaymentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PaymentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}