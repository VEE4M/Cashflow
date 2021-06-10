package com.gmail.appverstas.cashflow.data.saving

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.gmail.appverstas.cashflow.data.CashflowDatabase
import com.gmail.appverstas.cashflow.data.saving.models.SavingItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 *Veli-Matti Tikkanen, 8.2.2021
 */
class SavingViewModel(application: Application): AndroidViewModel(application) {

    private val savingsDao = CashflowDatabase.getDatabase(application).savingsDao()
    private val repository: SavingsRepository

    val getAllData: LiveData<List<SavingItem>>

    init {
        repository = SavingsRepository(savingsDao)
        getAllData = repository.getAllSavingsData
    }

    fun insertSaving(savingItem: SavingItem){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertSavingItem(savingItem)
        }
    }

    fun updateSaving(savingItem: SavingItem){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateSavingItem(savingItem)
        }
    }

    fun deleteSaving(savingItem: SavingItem){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteSavingItem(savingItem)
        }
    }

}