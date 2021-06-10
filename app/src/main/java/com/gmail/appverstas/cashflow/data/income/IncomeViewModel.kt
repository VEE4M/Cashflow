package com.gmail.appverstas.cashflow.data.income

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.gmail.appverstas.cashflow.data.CashflowDatabase
import com.gmail.appverstas.cashflow.data.income.models.IncomeItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 *Veli-Matti Tikkanen, 21.1.2021
 */
class IncomeViewModel(application: Application): AndroidViewModel(application) {

    private val incomeDao = CashflowDatabase.getDatabase(application).incomeDao()
    private val repository: IncomeRepository

    val getAllData: LiveData<List<IncomeItem>>

    init{
        repository = IncomeRepository(incomeDao)
        getAllData = repository.getAllIncomeData
    }

    fun insertIncome(incomeItem: IncomeItem){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertIncome(incomeItem)
        }
    }

    fun deleteIncome(incomeItem: IncomeItem){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteIncome(incomeItem)
        }
    }

    fun updateIncome(incomeItem: IncomeItem){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateIncome(incomeItem)
        }
    }

}